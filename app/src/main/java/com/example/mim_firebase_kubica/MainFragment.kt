package com.example.mim_firebase_kubica


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        database = Firebase.database.reference
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //logic for create account button
        view?.findViewById<Button>(R.id.btn_signin)?.setOnClickListener {

            var email=view.findViewById<TextView>(R.id.txt_mail).text.toString()
            var password=view.findViewById<TextView>(R.id.txt_password).text.toString()
            createAccount(email, password)
            //view.findNavController().navigate(R.id.action_mainFragment_to_listviewFragment)



        }
        //logic for log in button

        view?.findViewById<Button>(R.id.btn_login)?.setOnClickListener {

            var email=view.findViewById<TextView>(R.id.txt_mail).text.toString()
            var password=view.findViewById<TextView>(R.id.txt_password).text.toString()
            signIn(email, password)



        }
    }

    //creating new account
    private fun createAccount(email: String, password: String) {


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(context, "Creation succesfull.",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    view?.findNavController()?.navigate(R.id.action_mainFragment_to_listviewFragment)


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Creation failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
    }

        //Loging in 2 existing account
    private fun signIn(email: String, password: String) {




        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(context, "Authentication success.",
                        Toast.LENGTH_SHORT).show()
                    view?.findNavController()?.navigate(R.id.action_mainFragment_to_listviewFragment)


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }


}



