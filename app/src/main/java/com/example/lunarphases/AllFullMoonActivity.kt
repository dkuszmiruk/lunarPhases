package com.example.lunarphases

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_all_full_moon.*

class AllFullMoonActivity : AppCompatActivity() {

    private var algorithmName: String = ""
    val myCl : MoonPhaseCalculator = MoonPhaseCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras ?: return
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_full_moon)
        val name = extras.getString("AlgorithmName")
        if(name != null){
            algorithmName = name.toString()
        }
        imagePlus.setImageResource(R.drawable.plus)
        imageMinus.setImageResource(R.drawable.minus)
        editText.clearFocus()
        editText.setText("2019")
        showResults(myCl.allFullMoon(2019, algorithmName))

        editText.setOnClickListener() {

            val inputValue: String = editText.text.toString()
            if (inputValue.toInt() > 1900 && inputValue.toInt() <= 2200) {
                val data = myCl.allFullMoon(inputValue.toInt(), algorithmName)
                showResults(data)
            }
        }
//
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.)
//            }
//        })
    }

    override fun finish() {
        val data = Intent()

        data.putExtra("rAlgorithmName",algorithmName)
        setResult(Activity.RESULT_OK,data)

        super.finish()
    }

    fun plusClick(v: View) {
        var value = editText.text.toString().toInt()
        if (value < 2200) {
            value += 1
            editText.setText(value.toString())
            val data = myCl.allFullMoon(value, algorithmName)
            showResults(data)
        }
    }

    fun minusClick(v:View){
        var value = editText.text.toString().toInt()

        if(value>1900){
            value-=1
            editText.setText(value.toString())
            val data = myCl.allFullMoon(value, algorithmName)
            showResults(data)
        }
    }

    fun showResults(data: ArrayList<String>){
        val listItem = findViewById<ListView>(R.id.listItem)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listItem.adapter = adapter
    }




}
