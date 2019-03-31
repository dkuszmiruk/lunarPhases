package com.example.lunarphases

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import com.example.lunarphases.MoonPhaseCalculator
import java.util.*

class MainActivity : AppCompatActivity() {
    var algorithm: String = "Trig2"

//    var abc = MyClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showTodayInformation()
    }

    fun showTodayInformation(){
        val myCl : MoonPhaseCalculator = MoonPhaseCalculator()
        val now : Calendar = Calendar.getInstance()
        val today = myCl.givePercentForDay(now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1,now.get(Calendar.DAY_OF_MONTH),algorithm)
        val last = myCl.lastNewMoon(now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1, now.get(Calendar.DAY_OF_MONTH),algorithm)
        val next = myCl.nextFullMoon(now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1, now.get(Calendar.DAY_OF_MONTH),algorithm)

        todayInf.text="Dzisiaj: ".plus(today.toString()).plus("%")  //Pierwsze info w widoku!
        lastMoon.text="Poprzedni nów: ".plus(calToStr(last)).plus(" r.")
        nextFullM.text="Następna pełnia: ".plus(calToStr(next)).plus(" r.")
        imageView.setImageResource(R.drawable.moon)
    }

    fun showFullM(v: View){
//        val myCl : MoonPhaseCalculator = MoonPhaseCalculator()
//        val list = myCl.allFullMoon(2019,algorithm)
//        todayInf.text = calToStr(list.get(0))
//        lastMoon.text = list.size.toString()
//        nextFullM.text= calToStr(list.get(2))


    }

    fun calToStr(cal: Calendar): String {
        if(cal.get(Calendar.MONTH)+1<10)
            return cal.get(Calendar.DAY_OF_MONTH).toString().plus(".0").plus((cal.get(Calendar.MONTH)+1).toString()).plus(".").plus(cal.get(Calendar.YEAR))
        else
            return cal.get(Calendar.DAY_OF_MONTH).toString().plus(".").plus((cal.get(Calendar.MONTH)+1).toString()).plus(".").plus(cal.get(Calendar.YEAR))
        //wyswietlanie daty
        //val format : DateFormat = DateFormat.getDateInstance("yyy MM dd")
    }
}
