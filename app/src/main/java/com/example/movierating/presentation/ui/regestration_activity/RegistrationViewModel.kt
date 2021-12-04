package com.example.movierating.presentation.ui.regestration_activity

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movierating.data.repositorys_impl.MovieRatingRepositoryImpl
import com.example.movierating.data.database.AppDataBase
import com.example.movierating.data.database.UsersDatabase
import com.example.movierating.domain.use_cases.CheckEmailOnValidUseCase
import com.example.movierating.domain.use_cases.CheckPasswordOnValidUseCase
import com.example.movierating.domain.MovieRatingRepositiry


class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDataBase.getInstance(application)
    private val usersDataBaseDao = db.usersDatabaseDao()

    private val checkPasswordOnValidUseCase = CheckPasswordOnValidUseCase(MovieRatingRepositoryImpl)
    private val checkEmailOnValidUseCase = CheckEmailOnValidUseCase(MovieRatingRepositoryImpl)

    private val _errorInputEMail = MutableLiveData<Boolean>()
    val errorInputEMail: LiveData<Boolean>
        get() = _errorInputEMail

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword


    fun addUserToData(eMail: String, password: String, context: Context): Boolean {
        Log.d("TestInput", "$eMail\n$password")
        var result = false

        val fieldsValid = validateInput(eMail, password)
        if (fieldsValid) {
            try {
                val user = UsersDatabase(eMail, password)
                usersDataBaseDao.addUser(user)
                val toast = Toast.makeText(
                    context,
                    "Користувач успішно зареєстрований",
                    Toast.LENGTH_SHORT
                )
                toast.show()
                result = true
            } catch (e: SQLiteConstraintException) {
                val toast = Toast.makeText(
                    context,
                    "Користувач з таким eMail уже зареєстрований",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
        return result
    }


    private fun setEMileError(eMail: String): Boolean {
        var result = false
        if (checkEmailOnValidUseCase.checkEmailOnValid(eMail)) {
            result = true
        } else {
            _errorInputEMail.value = true
        }
        return result
    }

    private fun setPasswordError(password: String): Boolean {
        var result = false
        if (checkPasswordOnValidUseCase.checkPasswordOnValid(password)) {
            result = true
        } else {
            _errorInputPassword.value = true
        }
        return result
    }

    private fun validateInput(eMail: String, password: String): Boolean {
        var result: Int = MovieRatingRepositiry.RETURN_FALSE_IF_FIELDS_INVALID

        if (setEMileError(eMail)) {
            result++
        }
        if (setPasswordError(password)) {
            result++
        }
        return result == MovieRatingRepositiry.RETURN_TRUE_IF_FIELDS_VALID
    }

    fun resetErrorInputEMail() {
        _errorInputEMail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }
}