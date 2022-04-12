package com.example.mynote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// entity means table

@Entity(tableName = "Notestable")
class note ( @ColumnInfo(name = "Title") val note_title: String ,
             @ColumnInfo(name = "Discription") val discription : String,
             @ColumnInfo(name = "Time_stamp") val time_stamp: String)
{
    @PrimaryKey(autoGenerate = true) var id = 0


}