package com.example.notetorenginozgunakbor

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.widget.Button;

class DialogShowNote : DialogFragment() {

    private var note: Note? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//Following lines we initialize our properties we created in our xml file so we can use them in our code
        val builder = AlertDialog.Builder(this.requireActivity()) //Alert Dialog prompts a small dialog window to make a decision or action or displays a layout
        //With intents, we carry our data between activities
        val inflater = requireActivity().layoutInflater

        val dialogView = inflater.inflate(R.layout.dialog_show_note, null)
        val txtTitle = dialogView.findViewById(R.id.txtTitle) as TextView

        val txtDescription = dialogView.findViewById(R.id.txtDescription) as TextView

        txtTitle.text = note!!.title
        txtDescription.text = note!!.description

        val txtImportant =
                dialogView.findViewById(R.id.textViewImportant) as TextView

        val txtTodo =
                dialogView.findViewById(R.id.textViewTodo) as TextView

        val txtIdea =
                dialogView.findViewById(R.id.textViewIdea) as TextView
        if (!note!!.important) {
            txtImportant.visibility = View.GONE
        }

        if (!note!!.todo) {
            txtTodo.visibility = View.GONE
        }

        if (!note!!.idea) {
            txtIdea.visibility = View.GONE
        }
        val btnOK = dialogView.findViewById(R.id.btnOK) as Button

        builder.setView(dialogView).setMessage(resources.getString(R.string.your_note))  // This line refers to string resources and uses the language based on which local device app runs at


        btnOK.setOnClickListener({ dismiss() })

        return builder.create()
    }


    // Receive a note from the MainActivity class
    fun sendNoteSelected(noteSelected: Note) {
        note = noteSelected
    }

}

