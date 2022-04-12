package com.example.mynote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

//The code starts by defining a class called Notedatabase.
// This is an abstract class that extends RoomDatabase, which means it must implement the methods of RoomDatabase.
// The code then defines a companion object for this class and declares two private variables: INSTANCE, which will be null until the database is created; and getDatabase(), which will return a new instance of Notedatabase if it exists or else use synchronized to create one.
// The next line creates an instance of RoomDatabase using the application context from the current thread and calls note_database as its name.
// Note_database is defined in another file called note_database.java
// The code attempts to create a database object that can be used in the Room application.
// The code above creates an instance of Notedatabase and stores it in a private variable called INSTANCE.
// The getDatabase method uses the Room.databaseBuilder method to create a new database object with the specified class, name, and version number.

@Database(entities = arrayOf(note::class) , version = 1, exportSchema = false)
 abstract class Notedatabase : RoomDatabase() {

     abstract fun getNotesDoa() : NoteDAO

     companion object{

         //In Kotlin, if you want to write a function or any member of the class that can be called without having the instance of the class then you can write the same as a member of a companion object inside the class.
         // So, by declaring the companion object, you can access the members of the class by class name only(which is without explicitly creating the instance of the class).

         @Volatile
         private var INSTANCE : Notedatabase?  = null

         fun getDatabase(context: Context) : Notedatabase{
             return INSTANCE ?: synchronized(this){
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     Notedatabase::class.java,
                     "note_Database"
                 ).build()
                 INSTANCE = instance
                 instance


             }

         }
     }
}