package com.example.mim_firebase_kubica

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddGameFragment : Fragment() {

    companion object {
        fun newInstance() = AddGameFragment()
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        database = Firebase.database.reference
        return inflater.inflate(R.layout.add_game_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_add_game).setOnClickListener {

            addGame()

        }

    }

    fun addGame()
    {
        var user_key= auth.currentUser?.uid

        var title= view?.findViewById<TextView>(R.id.title_edit)?.text.toString()
        var genre= view?.findViewById<TextView>(R.id.genre_edit)?.text.toString()
        var studio= view?.findViewById<TextView>(R.id.studio_edit)?.text.toString()
        var played= view?.findViewById<Switch>(R.id.played_switch)?.isChecked.toString()

        if(played=="false"){played="No"}
        else{played="Yes"}



        if (user_key != null && title!="") {
            database.child("Users").child(user_key).child(title)
            database.child("Users").child(user_key).child(title).child("genre").setValue(genre)
            database.child("Users").child(user_key).child(title).child("studio").setValue(studio)
            database.child("Users").child(user_key).child(title).child("played").setValue(played)
            database.child("Users").child(user_key).child(title).child("title").setValue(title)
            title=""
            genre=""
            studio=""
            played=""
            view?.findNavController()?.navigate(R.id.action_addGameFragment_to_listviewFragment)

        }
        else{

            Toast.makeText(context, "Input Title",
                Toast.LENGTH_SHORT).show()

        }


    }

}

