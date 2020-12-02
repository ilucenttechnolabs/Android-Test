package com.example.androidtest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.androidtest.helper.Status
import com.example.androidtest.models.LoginRequestModel
import com.example.androidtest.models.UserModel
import com.example.androidtest.viewmodel.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_login)

        initViews()
        login_btn_Login.setOnClickListener {
            login()
        }

    }

    private fun initViews() {
        login_tl_username.editText?.doAfterTextChanged {
            if(it!!.isNotEmpty()) {
                login_tl_username.error = null
            }
        }

        login_tl_password.editText?.doAfterTextChanged {
            if(it!!.isNotEmpty()) {
                login_tl_password.error = null
            }
        }
    }

    private fun login() {

        val userName = login_tl_username.editText?.text.toString()
        val password = login_tl_password.editText?.text.toString()


        if(userName.isEmpty()) {
            login_tl_username.error = getString(R.string.empty_username_msg)
            return
        }
        if(password.isEmpty()) {
            login_tl_password.error = getString(R.string.empty_password_msg)
            return
        }

        viewModel.login(LoginRequestModel(userName, password)).observe(this, Observer {
            if(it == null) return@Observer

            when(it.status) {
                Status.LOADING -> {
                    showLoadingBar(true)
                }
                Status.SUCCESS -> {
                    showLoadingBar(false)
                    showAlertMessage(getString(R.string.success_login_message) + "  \nUserID : ${it.data?.userId} and " +
                            " Username : ${it.data?.userName}")
                    insertUserData(it.data)
                }
                Status.ERROR -> {
                    it.message?.let { it1 -> showAlertMessage(it1) }
                    showLoadingBar(false)
                }
            }
        })

    }

    private fun insertUserData(data: UserModel?) {
        if(data == null) return

        viewModel.insertUser(data).observe(this, Observer {
            if (it == null) return@Observer

            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    Toast.makeText(applicationContext, "Save User data successfully in local db.", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    it.message?.let { it1 -> Toast.makeText(applicationContext, it1, Toast.LENGTH_SHORT).show() }
                }
            }
        })
    }

    private fun showLoadingBar(b: Boolean) {
        if(b) {
            login_btn_Login.visibility = View.GONE
            login_progressBar.visibility = View.VISIBLE
        } else {
            login_btn_Login.visibility = View.VISIBLE
            login_progressBar.visibility = View.GONE
        }
    }

    private fun showAlertMessage(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.alert_label))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok_label)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}