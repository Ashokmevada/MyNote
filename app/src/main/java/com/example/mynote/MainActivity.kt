package com.example.mynote

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

 class MainActivity : AppCompatActivity() ,NoteClickDeleteinterface , NoteClickInterface {

    lateinit var noteRV : RecyclerView
    lateinit var addFloat : FloatingActionButton
    lateinit var viewModal: NoteviewModal
    //The “lateinit” keyword in Kotlin as the name suggests is used to declare those variables that are guaranteed to be initialized in the future.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor= Color.parseColor("#ffcdd2")


        noteRV = findViewById(R.id.recyclerView)
        addFloat = findViewById(R.id.floatingActionButton)

        //A LayoutManager is responsible for measuring and positioning item views within a RecyclerView
        // as well as determining the policy for when to recycle item views that are no longer visible to the user.

        noteRV.layoutManager = LinearLayoutManager(this)

        val noteadapter = Notesadapter(this, this, this)
        noteRV.adapter = noteadapter

        // --- As we know viewmodel is only destroy when the owner of viewmodel is destroy i.e. when activity is completely destroys---
        // so in viewModelprovider we give owner. and in viewModelprovider get method is use to create the viewmodel of following class

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteviewModal::class.java)

        // -----------------here observe method is part of liveData which nofity if the data in database changed to particular activity so we passed mainactivity as this as owner
        // and Observer is a interface and we can write interface like this ---Observer { list ->
        //          list?.let {
        //              noteadapter.updateList(it)
        //          }
        //        }--- if there is only one fun is interface it is a feature of kotlin
        viewModal.allnotes.observe(this, Observer { list ->

            //let() is a Kotlin "scope function". In particular, using let() with the safe call (?.) only executes the lambda supplied to let() if the receiver (value) is not null.
            //Lambda expression or simply lambda is an anonymous function; a function without name. These functions are passed immediately as an expression without declaration.
            //https://www.programiz.com/kotlin-programming/lambdas#:~:text=Lambda%20expression%20or%20simply%20lambda,%2F%2F%20invoking%20function%20greeting()%20%7D

            list?.let {
                noteadapter.updateList(it)
            }
        })

        addFloat.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNote::class.java)
            startActivity(intent)


        }
    }



        @Override
        override fun onClickiconDelete(note: note){
            viewModal.deletenote(note)
            Toast.makeText(this, " ${note.note_title} is deleted",Toast.LENGTH_SHORT).show()
        }
        @Override
        override fun onNoteClick(note: note){

            val intent = Intent(this@MainActivity , AddEditNote::class.java)
            intent.putExtra("NoteType" , "edit")
            intent.putExtra("NoteTitle",note.note_title)
            intent.putExtra("NoteDiscription" , note.discription)
            intent.putExtra("Noteid",note.id)
            startActivity(intent)


        }



    }
