package com.example.mynote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// here View Modal is use to retain the data when activity is recreated i.e when orientation or keyboard shows up
// android recreate activity so View Modal is used
// The ViewModel will transform the data from the Repository,
// from Flow to LiveData and expose the list of words as LiveData to the UI.
// This ensures that every time the data changes in the database, your UI is automatically updated.


//----------------------------- As this is a ViewModal class we don't have to put view references in this class as it can cause memory leakage----------------
// ViewModel class does not care about how we use data , it is only focus on storing data
// Memory leaks occur when an application allocates memory for an object, but then fails to release the memory when the object is no longer being used ,
// in this if memory for view is allocated then view will not use it as a result there will be memory leakage



class NoteviewModal (application: Application) : AndroidViewModel(application){


    // The Application class in Android is the base class within an Android app that contains all other components such as activities and services.
    // The Application class, or any subclass of the Application class,
    // is instantiated before any other class when the process for your application/package is created.


   val allnotes: LiveData<List<note>>
    val repository: noterepository

    //The code inside the init block is the first to be executed when the class is instantiated. The init block is run every time the class is instantiated,
    // with any kind of constructor as we shall see next. Multiple initializer blocks can be written in a class.

    init {
        val dao = Notedatabase.getDatabase(application).getNotesDoa()
        repository = noterepository(dao)
        allnotes = repository.allnotes

        //  The code starts by creating a new instance of the Notedatabase class.
        // This is done with the keyword "new".
        // The database name is notedatabase and it has been created in the application context.
        // The next line creates an instance of noterepository, which can be used to retrieve all notes from a given database.
        // It's important to note that this code does not create any notes yet; it just retrieves them from the database.
        // The last line uses repository.allnotes() to get all notes in the current database and store them into a list called allnotes
        // The code attempts to retrieve all notes from the database.
    }

    // This scope will be canceled when ViewModel will be cleared, i.e ViewModel.onCleared is called

    fun deletenote(Note : note) = viewModelScope.launch (Dispatchers.IO){
        repository.delete(Note)
    }

    // Dispatchers help coroutines in deciding the thread on which the work has to be done.
    // Dispatchers are passed as the arguments to the GlobalScope by mentioning which type of dispatchers we can use depending on the work that we want the coroutine to do.

    // The difference is that Dispatchers.Default is limited to the number of CPU cores (with a minimum of 2) so only N (where N == cpu cores) tasks can run in parallel in this dispatcher.
    //
    //On the IO dispatcher there are by default 64 threads, so there could be up to 64 parallel tasks running on that dispatcher.
    //
    //The idea is that the IO dispatcher spends a lot of time waiting (IO blocked), while the Default dispatcher is intended for CPU intensive tasks, where there is little or no sleep.
    fun updatenote(Note : note) = viewModelScope.launch (Dispatchers.IO){
        repository.update(Note)
    }

    fun insertnote(Note : note) = viewModelScope.launch (Dispatchers.IO){
        repository.insert(Note)
    }
}