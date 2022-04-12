package com.example.mynote

import androidx.lifecycle.LiveData
// The code is a NoteDAO that has methods for inserting, deleting, and updating notes.
// The code also creates a LiveData> called allnotes which is the list of all notes in the database.
// The code is an example of a simple note-taking application.
// It uses the NoteDAO class to store notes and retrieve them later on.
// The first method, insert(), allows users to add new notes.
// The second method, delete(), removes old notes from the database.
// The third method, update(), updates existing notes in the database with new information.

//!!!!!!!!!!! here repository means  ==========  A repository is commonly regarded as the single source of truth in an Android application. In other words, it acts as an abstraction over a particular data source. A repository enables an application to consume data without worrying about its origin.
//
//Some of the common sources of data include local databases, cache, and online servers. Using a repository allows developers to manage data more effectively. It is also much easier to identify bugs or errors since there is the separation of business logic from the UI.


class noterepository( private val notesDAO: NoteDAO) {

    val allnotes : LiveData<List<note>> = notesDAO.getAllNotes()
    // livedata execute on backgroung thread ,, all get method in room use LiveData method


    // suspend function is used to put the process in backend
    // we use it to achieve coroutines


    suspend fun insert(note : note){
        notesDAO.insert(note)
    }
    suspend fun delete(note : note){
        notesDAO.delete(note)
    }
    suspend fun update(note : note){
        notesDAO.udpate(note)
    }
}