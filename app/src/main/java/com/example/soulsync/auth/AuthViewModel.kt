package com.example.soulsync.auth

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

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
                            _loginState.value = LoginState.Success
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
            object Initial : RegisterState() // Initial state when no registration attempt is in progress

            object Loading : RegisterState() // State indicating that the registration process is in progress

            data class Error(
                val message: String,
            ) : RegisterState() // State representing an error with the registration process

            object Success : RegisterState() // State indicating a successful registration
        }

        // State to represent the registration process, set to prevent external modification
        private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Initial)
        val registerState: StateFlow<RegisterState> = _registerState

        @OptIn(ExperimentalCoroutinesApi::class)
        private suspend fun registerUserWithFirebase(
            email: String,
            password: String,
        ): Result<Unit> =
            suspendCancellableCoroutine { continuation ->
                auth
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(Result.success(Unit), null)
                        } else {
                            val errorMessage =
                                when (task.exception) {
                                    is FirebaseAuthWeakPasswordException -> "Password is too weak.\n *Must be at least 8 characters"
                                    is FirebaseAuthInvalidCredentialsException -> "Incorrect email format"
                                    is FirebaseAuthUserCollisionException -> "Account already exists with this email"
                                    else -> "Registration failed. Please try again"
                                }
                            _registerState.value = RegisterState.Error(errorMessage)
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
                            _registerState.value = RegisterState.Error(exception.message ?: "Registration failed")
                        },
                    )
                } catch (e: Exception) {
                    RegisterState.Error(e.message ?: "Registration failed")
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
    }
