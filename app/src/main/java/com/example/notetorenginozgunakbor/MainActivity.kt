package com.example.notetorenginozgunakbor

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.provider.Settings
import android.util.Log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var noteList: ArrayList<Note>? = null  // Here we define our note list of Note(s)
    private var jsonserializer: JSONSerializer? = null //Here we define our Json serializer
    private var recyclerView: RecyclerView? = null  // Here we define our Recyler View
    private var adapter: NoteAdapter? = null // Here we define adapter from our NoteAdapter class
    private var showDividers: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val dialog = DialogNewNote()     //Activates DialogNewNote class we coded when we push the button
            dialog.show(supportFragmentManager, "")
        }
        jsonserializer = JSONSerializer("NoteToSelf", applicationContext) //retrieve data from the Json file

        try {                                          //We create Notelist after reading the file
            noteList = jsonserializer!!.load()
        } catch (e: Exception) {
            noteList = ArrayList()

        }


        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter = NoteAdapter(this, noteList!!)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        // Adds dividing to note list but we will make it dependent on the switch button.
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL)) //Linear Layout Manager is a component of Recycler View and places items in linear order
        // set the adapter
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("Note to Self", Context.MODE_PRIVATE)  //preferences from Note to Self file
        showDividers = prefs.getBoolean("dividers", true)  //Default value is true if there is none
        if (showDividers) {  //If user wants the dividers, add to recyleview
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        } else {
            // If user doesn't want divider we will remove it but first we need to check if they exist or our app might crash
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }

    fun createNewNote(n: Note) {
        noteList!!.add(n)
        adapter!!.notifyDataSetChanged()
    }

    fun showNote(noteToShow: Int) {
        val dialog = DialogShowNote()

        dialog.show(supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val b = when (id) {
            R.id.action_settings -> {
                val intentToSettings = Intent(this, SettingsActivity::class.java)
                startActivity(intentToSettings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        return b
    }


    private fun saveNotes() {
        try {  //we save the notes json object but if we can't save it, catch the error message
            jsonserializer!!.save(this.noteList!!)
        } catch (e: Exception) {
            Log.e(TAG, "Error Saving Notes")
        }
    }

    override fun onPause() {  //Save the current state, its important.
        super.onPause()
        saveNotes()
    }


}