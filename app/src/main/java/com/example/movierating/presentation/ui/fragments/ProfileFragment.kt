package com.example.movierating.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movierating.databinding.FragmentProfileBinding
import com.example.movierating.presentation.ui.activitys.loginActivity.LoginActivity
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    @Inject
    lateinit var userFromLoginActivity: String

    @Inject
    lateinit var userFromRegistrationActivity: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userFromLoginActivity = requireArguments()[LoginActivity.USER_LOGIN_ACTIVITY].toString()

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            if (userFromLoginActivity == "null") {
                it.textViewProfile.text = userFromRegistrationActivity
            } else {
                it.textViewProfile.text = userFromLoginActivity
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun newInstance(intent: Intent): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()

            val userFromLoginActivity: String =
                intent.getStringExtra(LoginActivity.USER_LOGIN_ACTIVITY).toString()

            args.putString(LoginActivity.USER_LOGIN_ACTIVITY, userFromLoginActivity)

            fragment.arguments = args
            return fragment
        }
    }
}