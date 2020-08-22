package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private val wordToDefn = HashMap<String, String>()
    private val words = ArrayList<String>()

    private val defns = ArrayList<String>()
    private lateinit var myAdapter: ArrayAdapter<String> // must be set late init and type of adapter

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        readDictionaryFile()
        setUplistView()

        definitions_list.setOnItemClickListener { _, _, index, _ ->
            defns.removeAt(index)
            myAdapter.notifyDataSetChanged()
        }

    }

    // today we learn how to write list view in android kotlin
    private fun setUplistView() {
        // create content for listview
//        val list = ArrayList<String>()
//        list.add("Hello")
//        list.add("Goodbye")
//        list.add("Marty")

        // pick a random word
        val rand = java.util.Random()
        val index = rand.nextInt(words.size)
        val word = words[index]

        the_word.text = word

        // pick random definitions for the word
        defns.clear()
        defns.add(wordToDefn[word]!!)
        words.shuffle()
        for (otherWord in words.subList(0, 4)) {
            if (otherWord == word || defns.size == 5 ) {
                continue
            }
            defns.add(wordToDefn[otherWord]!!)
        }

        defns.add("a greeting")
        defns.add("something you say when you are sone talking")
        defns.add("a dude")
        defns.add("another name for a duck")
        defns.add("the president")
        defns.add("Nothing at all")

        defns.shuffle()

        // still do not undestands -> why use adapter to hold data, after that pull data to lst view?
        // rn is better

        // The difference between the two is android.R.layout.simple_list_item_1 has only one TextView inside it, while android.R.layout.simple_list_item_2 has two TextView.
        myAdapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, defns)

        definitions_list.adapter = myAdapter
    }

    // call storage in android?? -> read text = Scanner or BufferReader? android wtf
    private fun readDictionaryFile() {
        val reader = Scanner(resources.openRawResource(R.raw.sample))

        while (reader.hasNextLine()) {
            val line = reader.nextLine()
            Log.wtf(TAG, line)

            val pieces = line.split("\t")
            if (pieces.size >= 2) {
                words.add(pieces[0])
                wordToDefn.put(pieces[0], pieces[1])
            }

        }
    }


}