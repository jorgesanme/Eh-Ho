package io.keepcoding.eh_ho.login

import android.media.MediaCodec
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.keepcoding.eh_ho.common.TextChangedWatcher
import io.keepcoding.eh_ho.databinding.ActivityLoginBinding
import io.keepcoding.eh_ho.databinding.FragmentSignUpBinding
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    private val vm: LoginViewModel by activityViewModels()
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignUpBinding.inflate(inflater, container, false).apply {
        labelSignIn.setOnClickListener {
            vm.moveToSignIn()
        }
        vm.signUpData.observe(viewLifecycleOwner) {
            inputEmail.apply {
                setText(it.email)
                setSelection(it.email.length)
            }
            inputUsername.apply {
                setText(it.userName)
                setSelection(it.userName.length)
            }
            inputPassword.apply {
                setText(it.password)
                setSelection(it.password.length)
            }
            inputConfirmPassword.apply {
                setText(it.confirmPassword)
                setSelection(it.confirmPassword.length)
            }
        }
        vm.signUpEnabled.observe(viewLifecycleOwner) {
            buttonSignUp.isEnabled = it
        }
        inputEmail.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpEmail))
        }
        inputUsername.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpUserName))
        }
        inputPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpPassword))
        }
        inputConfirmPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpConfirmPassword))
        }

        buttonSignUp.setOnClickListener {
            val email = inputEmail.text.toString()
            val pass = inputPassword.text.toString()
            val user = inputUsername.text.toString()
            val confirPass = inputConfirmPassword.text.toString()
            if (!checkSignUpPattern(email, pass, user, confirPass)) {
                inputEmail.error = "Please enter a valid email Address"
                inputPassword.error ="Please enter a valid password Patter"
                inputPassword.error = "Please enter a valid password Patter"
                inputUsername.error = "Please enter a valid User Name"
// TODO: 25/5/21 create a String value for the text error
// TODO: 26/5/21 separar la deteccion a cada uno de los campos
            } else {
                Toast.makeText(binding.root.context, "SignUp Succes", Toast.LENGTH_SHORT).show()
                println("JcLog: clicking signup button")
                vm.signUp()
            }
        }
    }.root

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }


    private fun checkSignUpPattern(
        userName: String, 
        passWord: String, 
        email: String,
        confirPass: String
    ): Boolean {
        return vm.isValidEmail(email)
                ||vm.isValidPassword(passWord)
                ||vm.isValidUserName(userName) 
                ||vm.isValidPassword(confirPass)
        

    }
}
/*


    //Validating signUp
    fun validateEmail(): Boolean {
        var isValid: Boolean
        val email = inputEmail.text.toString()
        if (email.isEmpty()) {
            inputEmail.error = "Field can not be EMPTY"
            isValid = false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.error = "Please enter a valid email Address"
            isValid = false
        } else {
            inputEmail.error = "Bien hecho"
            isValid = true
        }
        return isValid
    }

    fun validatePassword(): Boolean {
        var isvalid: Boolean
        var passwordRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +   // al least 1 number
                    "(?=.*[a-z])" +   // al least 1 lower case letter
                    "(?=.*[A-Z])" +   // al least 1 upper case letter
                    "(?=.*[@#%&/+_!¡?¿])" +    // al least 1 special character
                    "(?=\\s+$)" +      // no white spaces
                    ".{8,})" +          // al least 8 characters
                    "$"
        )
        val password = inputPassword.text.toString()
        val passwordConfirm = inputConfirmPassword.text.toString()

        if (password.isEmpty() || passwordConfirm.isEmpty()) {
            inputPassword.error = "Field can not be EMPTY"
            inputConfirmPassword.error = "Field can not be EMPTY"
            isvalid = false
        } else if (!passwordRegex.matcher(password).matches() ||
            !passwordRegex.matcher(passwordConfirm).matches()
        ) {
            inputPassword.error =
                "Password must content (8 char min, [0-9],[a-Z,[@#%&/+_!¡?¿], no white space"
            inputConfirmPassword.error =
                "Password must content (8 char min, [0-9],[a-Z,[@#%&/+_!¡?¿], no white space"
            isvalid = false
        }else{
            inputPassword.error = null
            inputConfirmPassword.error = null
            isvalid = true
        }
        return isvalid
    }




*/



