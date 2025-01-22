package com.example.soulsync.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "AuthViewModel"

/**
 * AuthViewModel handles the authentication process.
 * Defines functions to login and register users using Firebase by implementing coroutines for efficiency
 */
@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val auth: FirebaseAuth,
    ) : ViewModel() {
        // Sealed class to represent the different states of the login process
        sealed class LoginState {
            object Initial : LoginState() // Initial state when no login attempt is in progress

            object Loading : LoginState() // State indicating that the login process is in progress

            data class Error(
                val message: String,
            ) : LoginState() // State representing an error with the login process

            object Success : LoginState() // State indicating a successful login
        }

        // State to represent the login process, set to prevent external modification
        private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
        val loginState: StateFlow<LoginState> = _loginState

        @OptIn(ExperimentalCoroutinesApi::class)
        private suspend fun signInWithFirebase(
            email: String,
            password: String,
        ): Result<Unit> =
            suspendCancellableCoroutine { continuation ->
                auth
                    .signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        // Additional verification that user exists
                        if (auth.currentUser != null) {
                            continuation.resume(Result.success(Unit), null)
                        } else {
                            continuation.resume(
                                Result.failure(Exception("Authentication failed")),
                                null,
                            )
                        }
                    }.addOnFailureListener { exception ->
                        val errorMessage =
                            when (exception) {
                                is FirebaseAuthInvalidUserException -> "No account found with this email"
                                is FirebaseAuthInvalidCredentialsException -> "Incorrect password"
                                is FirebaseNetworkException -> "Network error. Please check your connection"
                                else -> "Login failed: ${exception.message}"
                            }
                        continuation.resume(Result.failure(Exception(errorMessage)), null)
                    }.addOnCanceledListener {
                        continuation.resume(
                            Result.failure(Exception("Login process was cancelled")),
                            null,
                        )
                    }
            }

        // Run the login process as a coroutine using viewModelScope
        fun loginUser(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _loginState.value = LoginState.Loading
                try {
                    val result = signInWithFirebase(email, password)
                    result.fold(
                        onSuccess = {
                            if (auth.currentUser?.isEmailVerified == true) {
                                _loginState.value = LoginState.Success
                            } else {
                                _loginState.value =
                                    LoginState.Error("Email not verified, verification email is being resent.")
                                auth.currentUser?.sendEmailVerification()
                                logout()
                            }
                        },
                        onFailure = { exception ->
                            _loginState.value = LoginState.Error(exception.message ?: "Login failed")
                        },
                    )
                } catch (e: Exception) {
                    _loginState.value = LoginState.Error(e.message ?: "Login failed")
                }
            }
        }

        fun setLoginError(message: String) {
            _loginState.value = LoginState.Error(message)
        }

        // Sealed class to represent the different states of the registration process
        sealed class RegisterState {
            object Initial :
                RegisterState() // Initial state when no registration attempt is in progress

            object Loading :
                RegisterState() // State indicating that the registration process is in progress

            object EmailVerificationPending :
                RegisterState() // State indicating that an email has been sent to verify a new user

            data class Error(
                val message: String,
            ) : RegisterState() // State representing an error with the registration process

            object Success : RegisterState() // State indicating a successful registration
        }

        // State to represent the registration process, set to prevent external modification
        private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Initial)
        val registerState: StateFlow<RegisterState> = _registerState

        private val _isEmailVerified = MutableStateFlow(false)
        val isEmailVerified: StateFlow<Boolean> = _isEmailVerified

        @OptIn(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
        private suspend fun registerUserWithFirebase(
            email: String,
            password: String,
        ): Result<Unit> =
            suspendCancellableCoroutine { continuation ->
                auth
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Send verification email
                            auth.currentUser
                                ?.sendEmailVerification()
                                ?.addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        _registerState.value = RegisterState.EmailVerificationPending
                                        val resumeSuccess = continuation.tryResume(Result.success(Unit))
                                        if (resumeSuccess != null) {
                                            continuation.completeResume(resumeSuccess)
                                        }
                                    } else {
                                        val errorMessage = "Failed to send verification email."
                                        _registerState.value = RegisterState.Error(errorMessage)
                                        val resumeFailure =
                                            continuation.tryResume(Result.failure(Exception(errorMessage)))
                                        if (resumeFailure != null) {
                                            continuation.completeResume(resumeFailure)
                                        }
                                    }
                                }
                        } else {
                            val errorMessage =
                                when (task.exception) {
                                    is FirebaseAuthWeakPasswordException -> "Password is too weak.\n *Must be at least 8 characters."
                                    is FirebaseAuthInvalidCredentialsException -> "Incorrect email format."
                                    is FirebaseAuthUserCollisionException -> "An account already exists with this email."
                                    else -> "Registration failed. Please try again."
                                }
                            _registerState.value = RegisterState.Error(errorMessage)
                            val resumeFailure =
                                continuation.tryResume(Result.failure(Exception(errorMessage)))
                            if (resumeFailure != null) {
                                continuation.completeResume(resumeFailure)
                            }
                        }
                    }
            }

        // Run the registration process as a coroutine using viewModelScope
        fun registerUser(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _registerState.value = RegisterState.Loading
                try {
                    val result = registerUserWithFirebase(email, password)
                    result.fold(
                        onSuccess = {
                            _registerState.value = RegisterState.Success
                        },
                        onFailure = { exception ->
                            _registerState.value =
                                RegisterState.Error(exception.message ?: "Registration failed")
                        },
                    )
                } catch (e: Exception) {
                    RegisterState.Error(e.message ?: "Registration failed")
                }
            }
        }

        private var isCheckingVerification = false

        fun checkEmailVerification() {
            if (isCheckingVerification) return
            isCheckingVerification = true

            viewModelScope.launch {
                // Ensure currentUser is not null
                val currentUser = auth.currentUser
                if (currentUser == null) {
                    _registerState.value = RegisterState.Error("User not logged in")
                    return@launch
                }

                try {
                    // Reload user info
                    currentUser.reload().await()

                    // Check email verification status
                    val isVerified = currentUser.isEmailVerified
                    _isEmailVerified.value = isVerified
                    Log.d(TAG, "Checking email verification...")
                    Log.d(TAG, "Email verified: $isVerified")
                    if (isVerified) {
                        // Email verified
                        _registerState.value = RegisterState.Success
                    } else {
                        // Email not yet verified
                        if (_registerState.value != RegisterState.EmailVerificationPending) {
                            _registerState.value = RegisterState.EmailVerificationPending
                        }
                    }
                } catch (e: Exception) {
                    // Handle errors gracefully
                    _registerState.value =
                        RegisterState.Error(
                            e.message ?: "Failed to check email verification status",
                        )
                } finally {
                    isCheckingVerification = false // Reset flag
                }
            }
        }

        fun resendVerificationEmail() {
            viewModelScope.launch {
                try {
                    _registerState.value = RegisterState.Loading
                    auth.currentUser?.sendEmailVerification()?.await()
                    _registerState.value = RegisterState.EmailVerificationPending
                } catch (e: Exception) {
                    _registerState.value = RegisterState.Error(e.message ?: "Email verification failed")
                }
            }
        }

        // Gives a way to manually set the registration state to an error state with a custom message
        fun setRegisterState(message: String) {
            _registerState.value = RegisterState.Error(message)
        }

        fun logout() {
            FirebaseAuth.getInstance().signOut()
        }

        sealed class PasswordResetState {
            object Initial : PasswordResetState()

            object Loading : PasswordResetState()

            data class Error(
                val message: String,
            ) : PasswordResetState()

            object Success : PasswordResetState()
        }

        private val _resetState = MutableStateFlow<PasswordResetState>(PasswordResetState.Initial)
        val resetState: StateFlow<PasswordResetState> = _resetState

        @OptIn(ExperimentalCoroutinesApi::class)
        private suspend fun resetPasswordWithFirebase(email: String): Result<Unit> =
            suspendCancellableCoroutine { continuation ->
                auth
                    .sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        continuation.resume(Result.success(Unit), null)
                    }.addOnFailureListener { exception ->
                        val errorMessage =
                            when (exception) {
                                is FirebaseAuthInvalidUserException -> {
                                    when (exception.errorCode) {
                                        "ERROR_USER_NOT_FOUND" -> "No account found with this email"
                                        "ERROR_USER_DISABLED" -> "This account has been disabled"
                                        else -> "Invalid user account"
                                    }
                                }
                                is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
                                is FirebaseNetworkException -> "Network error. Please check your connection"
                                else -> "Failed to send reset email: ${exception.message}"
                            }
                        continuation.resume(
                            Result.failure(Exception(errorMessage)),
                            null,
                        )
                    }.addOnCanceledListener {
                        continuation.resume(
                            Result.failure(Exception("Password reset request was cancelled")),
                            null,
                        )
                    }
            }

        fun resetPassword(email: String) {
            viewModelScope.launch {
                _resetState.value = PasswordResetState.Loading
                try {
                    val result = resetPasswordWithFirebase(email)
                    result.fold(
                        onSuccess = {
                            _resetState.value = PasswordResetState.Success
                        },
                        onFailure = { exception ->
                            _resetState.value = PasswordResetState.Error(exception.message ?: "Password reset failed")
                        },
                    )
                } catch (e: Exception) {
                    _resetState.value = PasswordResetState.Error(e.message ?: "Password reset failed")
                }
            }
        }
    }
