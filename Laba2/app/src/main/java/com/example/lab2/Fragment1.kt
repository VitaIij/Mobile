package com.example.lab2

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_one.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var color: String = ""
    lateinit var editTextName: EditText
    lateinit var buttonCancel: Button
    lateinit var buttonOK: Button

    lateinit var radiogroup: RadioGroup
    lateinit var blackButton: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextName = view.findViewById(R.id.editTextName)
        buttonCancel = view.findViewById(R.id.buttonCancel)
        buttonOK = view.findViewById(R.id.buttonOK)
        radiogroup = view.findViewById(R.id.radiogroup)
        blackButton = view.findViewById(R.id.BlackButton)

        radiogroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            color = radio.text as String
        }

        buttonCancel.setOnClickListener{
            radiogroup.check(blackButton.id)
            editTextName.setText("")
            RxBus.publish(Event("", blackButton.text.toString()))
        }

        buttonOK.setOnClickListener {
            if (editTextName.text.toString().isNotEmpty()) {
                RxBus.publish(Event(editTextName.text.toString(), color))
            } else {
                Toast.makeText(context, "Please enter text", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
