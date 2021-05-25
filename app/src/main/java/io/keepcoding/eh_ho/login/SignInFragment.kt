package io.keepcoding.eh_ho.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.keepcoding.eh_ho.common.TextChangedWatcher
import io.keepcoding.eh_ho.databinding.ActivityLoginBinding
import io.keepcoding.eh_ho.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private val vm: LoginViewModel by activityViewModels()
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSignInBinding.inflate(inflater, container, false).apply {
        labelCreateAccount.setOnClickListener {
            vm.moveToSignUp()

        }
        vm.signInData.observe(viewLifecycleOwner) {
            inputUsername.apply {
                setText(it.userName)
                setSelection(it.userName.length)

            }
            inputPassword.apply {
                setText(it.password)
                setSelection(it.password.length)
            }
        }
        vm.signInEnabled.observe(viewLifecycleOwner) {
            buttonLogin.isEnabled = it
        }
        inputUsername.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignInUserName))
        }
        inputPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignInPassword))
        }
        buttonLogin.setOnClickListener {
            val userName = inputUsername.text.toString()
            val password = inputPassword.text.toString()

            if (!checkUserPattern(userName)) {
                inputUsername.error = "Please enter a valid User Name Patter"
            } else if (!checkPassWordPattern(password)) {
                inputPassword.error = "Please enter a valid password Patter"
                // TODO: 25/5/21 create a String value for the text error
            } else {
                Toast.makeText(binding.root.context, "ok. Pase usted pa'ntro", Toast.LENGTH_LONG).show()
                vm.signIn()
            }


        }
    }.root

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }

    private fun checkUserPattern(userName: String): Boolean {
        return vm.isValidUserName(userName)
    }

    private fun checkPassWordPattern(password: String): Boolean {
        return vm.isValidPassword(password)
    }
}