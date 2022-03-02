package com.ramgdeveloper.shoppingapp.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ramgdeveloper.shoppingapp.R
import com.ramgdeveloper.shoppingapp.databinding.FragmentLogInBinding
import timber.log.Timber

class LogInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progress: ProgressBar
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: FragmentLogInBinding

    companion object{
        private const val RC_SIGN_IN = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.root
        progress = binding.progressBar

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        //FirebaseAuth instance
        auth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            progress.visibility = VISIBLE
            signIn()
        }
        return view
    }

    private fun signIn() {
        val signIn = googleSignInClient.signInIntent
        startActivityForResult(signIn, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Timber.d("firebaseAuthWithGoogle:%s", account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Timber.tag("LogInFragment").w(e, "Google sign in failed")
                }
            }else {
                Timber.d(exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Task Successful", Toast.LENGTH_SHORT).show()
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithCredential:success")
                        findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                        progress.visibility = INVISIBLE
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.tag(task.exception.toString())
                    }
                }
    }


}