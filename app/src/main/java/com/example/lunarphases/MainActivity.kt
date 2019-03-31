package com.example.lunarphases

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import com.example.lunarphases.MoonPhaseCalculator

class MainActivity : AppCompatActivity() {

//    var abc = MyClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todayInf.text=""
        lastMoon.text=""
        nextFullM.text=""
        imageView.setImageResource(R.drawable.moon)
    }

    fun showFullM(v: View){
        val myCl : MoonPhaseCalculator = MoonPhaseCalculator()
        val x  = myCl.Trig1(2019,3,3)
        val y = myCl.Trig2(2019,3,3)
        val z = myCl.Conway(2019,3,3)
        val ex = myCl.Simple(2019,3,3)
        todayInf.text="Dzisiaj".plus(myCl.lastNewMoon())
        lastMoon.text = "Tr1 ".plus(x.toString()).plus(" Tr2 ").plus(y.toString())
        nextFullM.text= "Simp ".plus(ex.toString()).plus(" Con: ").plus(z.toString())
        imageView.setImageResource(R.drawable.moon)
    }
}




/*class MyClass{
    var a: Int = 5
}*/
