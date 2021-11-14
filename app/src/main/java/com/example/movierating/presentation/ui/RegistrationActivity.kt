package com.example.movierating.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movierating.R
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity: AppCompatActivity() {

    private lateinit var viewModel: RegistrationViewModel


    private lateinit var tilEMail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEMail: EditText
    private lateinit var etPassword: EditText
    private lateinit var registrationButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regestration)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        initViews()
        addTextChangeListeners()
        observeViewModel()

        registrationButton.setOnClickListener {
            if(viewModel.addUserToData(etEMail.text.toString(), etPassword.text.toString(),this)) {
                finish()
            }
        }
    }

    private fun initViews() {
        tilEMail = findViewById(R.id.tilEMailRegistrationActivity)
        tilPassword = findViewById(R.id.tilPasswordRegistrationActivity)
        etEMail = findViewById(R.id.etEMailRegistrationActivity)
        etPassword = findViewById(R.id.etPasswordRegistrationActivity)
        registrationButton = findViewById(R.id.registrationButtonRegistrationActivity)
    }

    private fun addTextChangeListeners() {
        etEMail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputEMail()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observeViewModel() {
        viewModel.errorInputEMail.observe(this) {
            val message = if(it) {
                resources.getString(R.string.invalid_eMail)
            } else {
                null
            }
            tilEMail.error = message
        }
        viewModel.errorInputPassword.observe(this) {
            val message = if(it) {
                resources.getString(R.string.invalid_password)
            } else {
                null
            }
            tilPassword.error = message
        }
    }
}