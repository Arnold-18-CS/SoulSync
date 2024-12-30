package com.example.soulsync.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Manage login and registration logic using Firebase Authentication
class AuthViewModel : ViewModel() {
    // Initialize Firebase Authentication instance
    private val auth = FirebaseAuth.getInstance()

    // Sealed class to represent the different states of the login process
    sealed class LoginState {
        object Initial : LoginState() // Initial state when no login attempt is in progress
        object Loading : LoginState() // State indicating that the login process is in progress
        data class Error(val message: String) : LoginState() // State representing an error with the login process
        object Success : LoginState() // State indicating a successful login
    }

    // State to represent the login process, set to prevent external modification
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState

    // Run the login process as a coroutine using viewModelScope
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            // Set the login state to loading before the login process; notify UI that the process is starting
            _loginState.value = LoginState.Loading
            // Using Firebase's signInWithEmailAndPassword method to authenticate the user
            auth.signInWithEmailAndPassword(email, password)
                // Callback to handle the result of the login process
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Login", "Login successful")
                        _loginState.value = LoginState.Success
                    } else {
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthInvalidUserException -> "No account found with this email"
                            is FirebaseAuthInvalidCredentialsException -> "Incorrect password"
                            else -> "Login failed. Please try again"
                        }
                        _loginState.value =
                            LoginState.Error(errorMessage)
                    }
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
        data class Error(val message: String) : RegisterState() // State representing an error with the registration process
        object Success : RegisterState() // State indicating a successful registration
    }

    // State to represent the registration process, set to prevent external modification
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Initial)
    val registerState: StateFlow<RegisterState> = _registerState

    // Run the registration process as a coroutine using viewModelScope
    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _registerState.value = RegisterState.Success
                    } else {
                        _registerState.value =
                            RegisterState.Error(task.exception?.message ?: "Registration failed")
                    }
                }
        }
    }

    // Gives a way to manually set the registration state to an error state with a custom message
    fun setRegisterState(message: String) {
        _registerState.value = RegisterState.Error(message)
    }

}



