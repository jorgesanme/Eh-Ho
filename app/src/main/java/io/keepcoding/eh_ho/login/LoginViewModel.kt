package io.keepcoding.eh_ho.login

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.lifecycle.*
import io.keepcoding.eh_ho.model.LogIn
import io.keepcoding.eh_ho.repository.Repository
import kotlinx.coroutines.withContext
import okhttp3.internal.userAgent
import java.util.regex.Pattern

class LoginViewModel(private val repository: Repository) : ViewModel() {



    private val _state: MutableLiveData<State> =
        MutableLiveData<State>().apply { postValue(State.SignIn) }
    private val _signInData = MutableLiveData<SignInData>().apply { postValue(SignInData("", "")) }
    private val _signUpData =
        MutableLiveData<SignUpData>().apply { postValue(SignUpData("", "", "", "")) }
    val state: LiveData<State> = _state
    val signInData: LiveData<SignInData> = _signInData
    val signUpData: LiveData<SignUpData> = _signUpData
    val signInEnabled: LiveData<Boolean> =
        Transformations.map(_signInData) { it?.isValid() ?: false }
    val signUpEnabled: LiveData<Boolean> =
        Transformations.map(_signUpData) { it?.isValid() ?: false }
    val loading: LiveData<Boolean> = Transformations.map(_state) {
        when (it) {
            State.SignIn,
            State.SignedIn-> false
            State.SignUp,
            State.SignedUp -> false
            State.SigningIn -> true
            State.SigningUp -> true
        }
    }

    fun onNewSignInUserName(userName: String) {
        onNewSignInData(_signInData.value?.copy(userName = userName))
    }

    fun onNewSignInPassword(password: String) {
        onNewSignInData(_signInData.value?.copy(password = password))
    }

    fun onNewSignUpUserName(userName: String) {
        onNewSignUpData(_signUpData.value?.copy(userName = userName))
    }

    fun onNewSignUpEmail(email: String) {
        onNewSignUpData(_signUpData.value?.copy(email = email))
    }

    fun onNewSignUpPassword(password: String) {
        onNewSignUpData(_signUpData.value?.copy(password = password))
    }

    fun onNewSignUpConfirmPassword(confirmPassword: String) {
        onNewSignUpData(_signUpData.value?.copy(confirmPassword = confirmPassword))
    }

    private fun onNewSignInData(signInData: SignInData?) {
        signInData?.takeUnless { it == _signInData.value }?.let(_signInData::postValue)
    }

    private fun onNewSignUpData(signUpData: SignUpData?) {
        signUpData?.takeUnless { it == _signUpData.value }?.let(_signUpData::postValue)
    }

    fun moveToSignIn() {
        _state.postValue(State.SignIn)
    }

    fun moveToSignUp() {
        _state.postValue(State.SignUp)
    }

    fun signIn() {
        signInData.value?.takeIf { it.isValid() }?.let {
            _state.postValue(State.SigningIn)
            repository.signIn(it.userName, it.password) {
                if (it is LogIn.Success) {
                    _state.postValue(State.SignedIn)
                } else {
                    _state.postValue(State.SignIn)
                }
            }
        }
    }

    fun signUp() {
        signUpData.value?.takeIf { it.isValid() }?.let {
            _state.postValue(State.SigningIn)
            repository.signup(it.userName, it.email, it.password) {
                //
            }
        }
    }

    sealed class State {
        object SignIn : State()
        object SigningIn : State()
        object SignedIn : State()
        object SignUp : State()
        object SigningUp : State()
        object SignedUp : State()
    }

    data class SignInData(
        val userName: String,
        val password: String,
    )

    data class SignUpData(
        val email: String,
        val userName: String,
        val password: String,
        val confirmPassword: String,
    )

    class LoginViewModelProviderFactory(private val repository: Repository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(repository) as T
            else -> throw IllegalArgumentException("LoginViewModelFactory can only create instances of the LoginViewModel")
        }
    }
}


private fun LoginViewModel.SignInData.isValid(): Boolean {
    return userName.isNotBlank() && password.isNotBlank()
}

private fun LoginViewModel.SignUpData.isValid(): Boolean = userName.isNotBlank() &&
        email.isNotBlank() &&
        password == confirmPassword &&
        password.isNotBlank()

fun LoginViewModel.isValidEmail(email: String): Boolean {
    return if (email.isEmpty()) {
        false
    } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
        false
    } else {
        true
    }
}

fun LoginViewModel.isValidPassword(password: String): Boolean {
    var passwordRegex = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
//        "^" +
//                "(?=.*[0-9])" +   // al least 1 number
//                "(?=.*[a-z])" +   // al least 1 lower case letter
//                "(?=.*[A-Z])" +   // al least 1 upper case letter
//                "(?=.*[@#%&/+_!?????])" +    // al least 1 special character
//                "(?=\\s+$)" +      // no white spaces
//                ".{8,})" +          // al least 8 characters
//                "$"
    )
    return if (password.isEmpty()) {
        false
    } else if (!passwordRegex.matcher(password).matches()) {
        false
    } else {
        true
    }
}

fun LoginViewModel.isValidUserName(userName: String): Boolean {
    var userNameRegex = Pattern.compile(
        "[a-zA-Z0-9].{5,}"
    )
    return if (!userNameRegex.matcher(userName).matches()) {
        false
    } else {
        true
    }
}

