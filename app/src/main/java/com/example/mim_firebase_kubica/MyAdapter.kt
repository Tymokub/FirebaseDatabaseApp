package com.example.mim_firebase_kubica


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList



class MyAdapter(var context: Context, list: ArrayList<Game>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var list: ArrayList<Game>
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item, parent, false)


        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //showing games data on recyclerview
        val game: Game = list[position]
        holder.title.setText(game.title)
        holder.genre.setText(game.genre)
        holder.studio.setText(game.studio)
        //TODO
        holder.played.setText(game.played)



        holder.delete.setOnClickListener{

            removeItem(position)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    //Remove game from database and recyclerview
    fun removeItem(position: Int) {
        var auth = Firebase.auth
        var database = Firebase.database.reference
        val game: Game = list[position]
        database.child("Users").child(auth.uid.toString()).child(game.title).removeValue()
        list.removeAt(position)







    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var genre: TextView
        var studio: TextView
        var played:TextView
        var delete:ImageButton



        init {
            title = itemView.findViewById(R.id.tvtitle)
            genre = itemView.findViewById(R.id.tvgenre)
            studio = itemView.findViewById(R.id.tvstudio)
            played=itemView.findViewById(R.id.tvplayed)
            delete=itemView.findViewById(R.id.imageButton2)
        }
    }

    init {
        this.list = list
    }
}