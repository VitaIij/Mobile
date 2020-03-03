package com.example.lab1

import android.app.Application
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.RadioGroup
import kotlinx.android.synthetic.*

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var buttonCancel: Button
    lateinit var buttonOK: Button
    lateinit var textNewMessage: TextView
    lateinit var radiogroup: RadioGroup
    var color: String = ""
    val colors : Map<String, String> = mapOf("Black" to "#000000", "Blue" to "#0000ff", "Green" to "#00ff00", "Red" to "#ff0000")


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonOK = findViewById(R.id.buttonOK)
        textNewMessage = findViewById(R.id.textNewMessage)
        radiogroup = findViewById(R.id.radiogroup)

        buttonOK.setOnClickListener {

            val name = editTextName.text
            textNewMessage.text = "$name"

            if (color != "") {
                textNewMessage.setTextColor(Color.parseColor(colors[color]))
            }
        }

        buttonCancel.setOnClickListener{
            finish();
            startActivity(getIntent());
        }

        radiogroup.setOnCheckedChangeListener {
                group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            color = radio.text as String
        }
    }


}