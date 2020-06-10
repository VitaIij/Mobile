package com.example.lab1

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.RadioGroup
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var editFile: EditText
    lateinit var editData: EditText
    lateinit var buttonCancel: Button
    lateinit var buttonOK: Button
    lateinit var btnSave: Button
    lateinit var btnView: Button
    lateinit var btnClear: Button
    lateinit var textNewMessage: TextView
    lateinit var radiogroup: RadioGroup
    lateinit var blackButton: RadioButton
    var color: String = ""
    val colors: Map<String, String> =
        mapOf("Black" to "#000000", "Blue" to "#0000ff", "Green" to "#00ff00", "Red" to "#ff0000")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editFile = findViewById(R.id.editFile)
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonOK = findViewById(R.id.buttonOK)
        btnSave = findViewById(R.id.btnSave)
        btnView = findViewById(R.id.btnView)
        btnClear = findViewById(R.id.btnClear)
        textNewMessage = findViewById(R.id.textNewMessage)
        radiogroup = findViewById(R.id.radiogroup)
        blackButton = findViewById(R.id.BlackButton)


        buttonOK.setOnClickListener {
            if (editTextName.text.isEmpty()) {
                showToast("Please, enter text")
                return@setOnClickListener
            }

            val name = editTextName.text
            textNewMessage.text = "$name"

            if (color != "") {
                textNewMessage.setTextColor(Color.parseColor(colors[color]))
            }
        }

        buttonCancel.setOnClickListener {
            radiogroup.check(blackButton.id)
            editTextName.setText("")
            textNewMessage.setText("")
        }

        radiogroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            color = radio.text as String
        }

        btnSave.setOnClickListener {

            val file = editFile.text.toString()
            val data = textNewMessage.text.toString()
            val colour = color.toString()
            val fileOutputStream: FileOutputStream
            val output = "$data $colour "

            try {
                fileOutputStream = openFileOutput(file, Context.MODE_APPEND)
                fileOutputStream.write(output.toByteArray())
                fileOutputStream.close()
                //fileOutputStream.write(colour.toByteArray())
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (editFile.text.isEmpty()) {
                showToast("Please, enter name of a file")
            }
            else
            showToast("Saved to file ")

        }

        btnView.setOnClickListener {
            val filename = editFile.text.toString()
            if (filename.toString() != null && filename.trim() != "") {
                val intent = Intent(this, ViewActivity::class.java)
                intent.putExtra("filename", filename)
                startActivity(intent)
            }
            else
                showToast("Name of the file can`t be blanked")

        }

        /*btnView.setOnClickListener {
            val intent = Intent(this, ViewActivity::class.java)
            startActivity(intent)
            val filename = editFile.text.toString()
            if (filename.toString() != null && filename.trim() != "") {

                var fileInputStream: FileInputStream? = null
                fileInputStream = openFileInput(filename)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null

                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }

                editData.setText(stringBuilder.toString()).toString()
            } else

                showToast("Name of the file can`t be blanked")
        }*/

        btnClear.setOnClickListener {

            try {
                val file = editFile.text.toString()
                val fileOutputStream: FileOutputStream
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write("".toByteArray())
                fileOutputStream.close()
            }
            catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (editFile.text.isEmpty()) {
                showToast("Please, enter name of a file")
            }
            else
                showToast("Successfully cleared ")






        }


        }
    fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }
}