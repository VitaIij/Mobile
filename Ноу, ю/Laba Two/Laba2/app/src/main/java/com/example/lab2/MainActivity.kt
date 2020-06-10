package com.example.lab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        val fragment1 = Fragment1()
        val fragment2 = Fragment2()
        transaction.add(R.id.fragment_one, fragment1)
        transaction.add(R.id.fragment_two, fragment2)
        transaction.commit()
    }
}