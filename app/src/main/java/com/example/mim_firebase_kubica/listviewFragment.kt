package com.example.mim_firebase_kubica

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError



import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList


class listviewFragment : Fragment() {

    companion object {
        fun newInstance() = listviewFragment()
    }

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var list:ArrayList<Game>
    private lateinit var search_list:ArrayList<Game>
    private lateinit var recyclerView:RecyclerView

// ...



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth
        database = Firebase.database.reference

        return inflater.inflate(R.layout.listview_fragment, container, false )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        recyclerView= view.findViewById(R.id.games_list)
        database=FirebaseDatabase.getInstance().getReference("Users").child(auth.uid.toString())
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context));




        list = ArrayList()
        search_list= ArrayList()
        var myAdapter = context?.let { MyAdapter(it, search_list) }
        recyclerView.adapter = myAdapter


        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                search_list.clear()
                list.clear()
                for (dataSnapshot in snapshot.children) {
                    val game: Game? = dataSnapshot.getValue(Game::class.java)

                    if (game != null) {
                        if(game.title!=null&&game.title!="") {
                            list.add(game!!)
                        }
                    }



                }
                search_list.clear()
                search_list.addAll(list)
                myAdapter?.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {}
        })


        view?.findViewById<Button>(R.id.button)?.setOnClickListener {

            view.findNavController().navigate(R.id.action_listviewFragment_to_addGameFragment)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_item, menu)

        val item=menu.findItem(R.id.search_action)
        val searchView=item.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                //SearchView implementation
                search_list.clear()
                val searchText= p0?.toLowerCase(Locale.getDefault())
                if(searchText?.isNotEmpty()!!){


                    for (game in list) {

                        if(game.title!="" && game.title!=null) {
                            if (game.title.toLowerCase(Locale.getDefault()).contains(searchText)) {

                                search_list.add(game)

                            } else if (game.studio.toLowerCase(Locale.getDefault())
                                    .contains(searchText)
                            ) {

                                search_list.add(game)

                            } else if (game.genre.toLowerCase(Locale.getDefault())
                                    .contains(searchText)
                            ) {

                                search_list.add(game)

                            } else if (game.played.toLowerCase(Locale.getDefault())
                                    .contains(searchText)
                            ) {

                                search_list.add(game)

                            }
                        }
                        recyclerView.adapter?.notifyDataSetChanged()
                    }




                }
                else{

                    search_list.clear()
                    search_list.addAll(list)
                    recyclerView.adapter?.notifyDataSetChanged()

                }

                return false
            }


        })

        return super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item: MenuItem = menu.findItem(R.id.search_action)
        item.isVisible = true
    }






}