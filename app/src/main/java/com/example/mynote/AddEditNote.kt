package com.example.mynote

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNote : AppCompatActivity() {
    lateinit var noteTitle : EditText
    lateinit var noteDiscription: EditText
    lateinit var savebutton : Button
    lateinit var viewModel : NoteviewModal
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note)

        window.statusBarColor= Color.parseColor("#263238")





        noteTitle = findViewById(R.id.note_titlel)
        noteDiscription = findViewById(R.id.note_discriptionl)
        savebutton = findViewById(R.id.save)

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteviewModal::class.java)

        val notetype = intent.getStringExtra("NoteType")
        if(notetype.equals("edit")){
            val Notetit = intent.getStringExtra("NoteTitle")
            val NoteDisc = intent.getStringExtra("NoteDiscription")
             noteID = intent.getIntExtra("noteid" , -1)
            savebutton.setText("Update note")
            noteTitle.setText(Notetit)
            noteDiscription.setText(NoteDisc)
        }else{
            savebutton.setText("Create Note")
        }


        savebutton.setOnClickListener {
            val nt = noteTitle.text.toString()
            val nd = noteDiscription.text.toString()

            if(notetype.equals("edit")){
                if(nt.isNotEmpty() && nd.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd , MM, yyyy - HH:mm")
                    val currentDate:String = sdf.format((Date()))
                    val updateNote = note(nt,nd,currentDate)
                    updateNote.id = noteID
                    viewModel.updatenote(updateNote)
                    Toast.makeText(this,"Note Updated..",Toast.LENGTH_SHORT).show()
                }
            }else{
                if(nt.isNotEmpty() && nd.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd, MM, yyyy - HH:mm")
                    val currentDate:String = sdf.format((Date()))
                    viewModel.insertnote(note(nt,nd,currentDate))
                    Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show()

                }
            }


            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }



    }
}
