package com.example.notetorenginozgunakbor

import androidx.fragment.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText


class DialogNewNote : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Following lines we initialize buttons and text views we created so we can use them in our code
        val builder = AlertDialog.Builder(requireActivity()) //Alert Dialog prompts a small dialog window to make a decision or action or displays a layout

        val inflater = requireActivity().layoutInflater

        val dialogView = inflater.inflate(R.layout.dialog_new_note, null)

        val editTitle = dialogView.findViewById(R.id.editTitle) as EditText

        val editDescription = dialogView.findViewById(R.id.editDescription) as EditText

        val checkBoxIdea = dialogView.findViewById(R.id.checkBoxIdea) as CheckBox

        val checkBoxTodo = dialogView.findViewById(R.id.checkBoxTodo) as CheckBox

        val checkBoxImportant = dialogView.findViewById(R.id.checkBoxImportant) as CheckBox

        val btnCancel = dialogView.findViewById(R.id.btnCancel) as Button

        val btnOK = dialogView.findViewById(R.id.btnOK) as Button

        builder.setView(dialogView).setMessage(resources.getString(R.string.add_new_note)) // This line refers to string resources and uses the language on which local device app runs

        // Handle the cancel button
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnOK.setOnClickListener {
            // Create a new note
            val newNote = Note()

            // Set properties to user's entered properties
            newNote.title = editTitle.text.toString()

            newNote.description = editDescription.text.toString()

            newNote.idea = checkBoxIdea.isChecked
            newNote.todo = checkBoxTodo.isChecked
            newNote.important = checkBoxImportant.isChecked

            // Get a reference to MainActivity
            val callingActivity = activity as MainActivity?

            // Pass newNote back to MainActivity
            callingActivity!!.createNewNote(newNote)
            // Quit the dialog
            dismiss()
        }

        return builder.create()


    }
}

