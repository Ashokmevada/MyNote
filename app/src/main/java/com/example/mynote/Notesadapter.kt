package com.example.mynote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class Notesadapter (val context: Context, val noteClickInterface : NoteClickInterface, val onDelete: NoteClickDeleteinterface)  :
    RecyclerView.Adapter<Notesadapter.ViewHolder>() {


    private val allnotes = ArrayList<note>()


    // Inner class is a class which is created inside another class with keyword inner. In other words, we can say that a nested class which is marked as "inner" is called inner class.
    //Inner class cannot be declared inside interfaces or non-inner nested classes.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val notetv = itemView.findViewById<TextView>(R.id.titleofnote)
        val timetv = itemView.findViewById<TextView>(R.id.timeid)
        val imagetv = itemView.findViewById<ImageView>(R.id.imageid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val  itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_list , parent , false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.notetv.setText(allnotes.get(position).note_title)
        holder.timetv.setText("Last Updated: " + allnotes.get(position).time_stamp)

        holder.imagetv.setOnClickListener{
            onDelete.onClickiconDelete((allnotes.get(position)))
        }

        holder.itemView.setOnClickListener{
            noteClickInterface.onNoteClick(allnotes.get(position))
        }

    }

    override fun getItemCount(): Int {
        return allnotes.size

    }

    fun updateList(newList : List<note>){
        allnotes.clear()
        allnotes.addAll(newList)
        notifyDataSetChanged()
    }


}


interface NoteClickDeleteinterface{
    fun onClickiconDelete(Note : note)
}

interface NoteClickInterface{
    fun onNoteClick(Note : note)
}


