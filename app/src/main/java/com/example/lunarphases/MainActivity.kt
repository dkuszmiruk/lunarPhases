package com.example.lunarphases

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todayInf.text=""
        lastMoon.text=""
        nextFullM.text=""
        imageView.setImageResource(R.drawable.moon)
    }

    fun showFullM(v: View){
        todayInf.text="Dzisiaj jest blab bla"
        imageView.setImageResource(R.drawable.moon)
    }
}
