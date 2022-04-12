package com.example.mynote

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao  // DAO stands for data access object
interface NoteDAO {

    // suspend function is used to put the process in backend
    // we use it to achieve coroutines

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insert(note: note)

    @Update
    suspend fun udpate(note: note)


    @Delete
    suspend fun delete(note: note)

    @Query("Select * From notestable order by id ASC")
    fun getAllNotes() : LiveData<List<note>>

    // livedata execute on backgroung thread ,, all get method in room use LiveData method

}