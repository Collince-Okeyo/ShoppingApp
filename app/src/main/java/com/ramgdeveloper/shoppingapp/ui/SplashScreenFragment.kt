package com.ramgdeveloper.shoppingapp.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ramgdeveloper.shoppingapp.R
import timber.log.Timber

class SplashScreenFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler().postDelayed({
            if (user != null){
                Timber.d("User Found")
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }else{
                Timber.d("User Not Found")
                findNavController().navigate(R.id.action_splashScreenFragment_to_logInFragment)
            }
        }, 3000)

        return view
    }
}