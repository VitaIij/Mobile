package com.example.lab2

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils.isEmpty
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_one.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var textNewMessage: TextView
    val colors : Map<String, String> = mapOf("Black" to "#000000", "Blue" to "#0000ff", "Green" to "#00ff00", "Red" to "#ff0000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxBus.listen(Event::class.java).subscribe {
            if (it.text.isEmpty()) {
                textNewMessage.text = getString(R.string.no_text)
                return@subscribe
            }

            textNewMessage.text = "${it.text}"

            if (it.color != "") {
                textNewMessage.setTextColor(Color.parseColor(colors[it.color]))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textNewMessage = view.findViewById(R.id.textNewMessage)

    }
}
