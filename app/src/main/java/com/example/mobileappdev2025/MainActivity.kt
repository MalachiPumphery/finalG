package com.example.mobileappdev2025

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.mobileappdev2025.fragments.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Scanner

data class WordDefinition(val word: String, val definition: String)

class MainActivity : AppCompatActivity() {
    private val ADD_WORD_CODE = 1234
    private lateinit var myAdapter: ArrayAdapter<String>
    private var dataDefList = ArrayList<String>()
    private var wordDefinition = mutableListOf<WordDefinition>()
    private var score: Int = 0
    private var totalCorrect: Int = 0
    private var totalWrong: Int = 0
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            loadWordsFromDisk()
            pickNewWordAndLoadDataList()
            setupList()
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing app", e)
            Toast.makeText(this, "Error loading data", Toast.LENGTH_LONG).show()
        }

        val defList = findViewById<ListView>(R.id.dynamic_def_list)
        defList.setOnItemClickListener { _, _, index, _ ->
            val selectedDefinition = dataDefList[index]
            val correctDefinition = wordDefinition[0].definition
            
            if (selectedDefinition == correctDefinition) {
                score++
                totalCorrect++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                totalWrong++
                Toast.makeText(this, "Incorrect. The correct definition was: $correctDefinition", Toast.LENGTH_LONG).show()
            }
            
            pickNewWordAndLoadDataList()
            myAdapter.notifyDataSetChanged()
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_courses -> loadFragment(CoursesFragment())
                R.id.nav_study_groups -> loadFragment(StudyGroupsFragment())
                R.id.nav_schedule -> loadFragment(ScheduleFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }

        // Set default fragment
        if (supportFragmentManager.fragments.isEmpty()) {
            loadFragment(CoursesFragment())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_WORD_CODE && resultCode == RESULT_OK && data != null) {
            val word = data.getStringExtra("word") ?: ""
            val def = data.getStringExtra("def") ?: ""

            if (word.isBlank() || def.isBlank()) {
                Toast.makeText(this, "Invalid word or definition", Toast.LENGTH_SHORT).show()
                return
            }

            wordDefinition.add(WordDefinition(word, def))
            saveWordToDisk(word, def)
            pickNewWordAndLoadDataList()
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun loadWordsFromDisk() {
        val file = File(applicationContext.filesDir, "user_data.csv")

        if (file.exists()) {
            try {
                FileInputStream(file).use { inputStream ->
                    Scanner(inputStream).use { scanner ->
                        while (scanner.hasNextLine()) {
                            val line = scanner.nextLine()
                            val parts = line.split("|")
                            if (parts.size == 2) {
                                wordDefinition.add(WordDefinition(parts[0], parts[1]))
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, "Error reading words from disk", e)
                throw e
            }
        }

        // Load default data if no user data exists
        if (wordDefinition.isEmpty()) {
            try {
                Scanner(resources.openRawResource(R.raw.default_words)).use { reader ->
                    while (reader.hasNextLine()) {
                        val line = reader.nextLine()
                        val parts = line.split("|")
                        if (parts.size == 2) {
                            wordDefinition.add(WordDefinition(parts[0], parts[1]))
                            saveWordToDisk(parts[0], parts[1])
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading default words", e)
                throw e
            }
        }
    }

    private fun saveWordToDisk(word: String, definition: String) {
        try {
            val file = File(applicationContext.filesDir, "user_data.csv")
            file.appendText("$word|$definition\n")
        } catch (e: IOException) {
            Log.e(TAG, "Error saving word to disk", e)
            Toast.makeText(this, "Error saving word", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickNewWordAndLoadDataList() {
        if (wordDefinition.isEmpty()) {
            Toast.makeText(this, "No words available", Toast.LENGTH_SHORT).show()
            return
        }

        wordDefinition.shuffle()
        dataDefList.clear()
        dataDefList.addAll(wordDefinition.map { it.definition })
        findViewById<TextView>(R.id.word).text = wordDefinition[0].word
        dataDefList.shuffle()
    }

    private fun setupList() {
        myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataDefList)
        findViewById<ListView>(R.id.dynamic_def_list).adapter = myAdapter
    }

    fun openStats(view: View) {
        val intent = Intent(this, StatsActivity::class.java).apply {
            putExtra("score", score.toString())
            putExtra("totalCorrect", totalCorrect.toString())
            putExtra("totalWrong", totalWrong.toString())
        }
        startActivity(intent)
    }

    fun openAddWord(view: View) {
        startActivityForResult(Intent(this, AddWordActivity::class.java), ADD_WORD_CODE)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}