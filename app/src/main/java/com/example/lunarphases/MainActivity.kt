package com.example.lunarphases

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var algorithmName: String = "Trig2"
    val REQUEST_ALLFULLMOON_CODE = 12321
    val REQUEST_MENU_CODE = 12345

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showTodayInformation()
    }

    fun showTodayInformation(){
        val myCl : MoonPhaseCalculator = MoonPhaseCalculator()
        val now : Calendar = Calendar.getInstance()
        var today = myCl.givePercentForDay(now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1,now.get(Calendar.DAY_OF_MONTH),algorithmName)
        val last = myCl.lastNewMoon(now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1, now.get(Calendar.DAY_OF_MONTH),algorithmName)
        val next = myCl.nextFullMoon(now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1, now.get(Calendar.DAY_OF_MONTH),algorithmName)

        todayInf.text="Dzisiaj: ".plus(today.toString()).plus("%")
        lastMoon.text="Poprzedni nów: ".plus(myCl.calToStr(last)).plus(" r.")
        nextFullM.text="Następna pełnia: ".plus(myCl.calToStr(next)).plus(" r.")
        imageButton.setImageResource(R.drawable.settings)
        when {
            today < 15 -> imageView.setImageResource(R.drawable.newmoon)
            today < 35 -> imageView.setImageResource(R.drawable.dppr)
            today < 65 -> imageView.setImageResource(R.drawable.fiftypr)
            today < 80 -> imageView.setImageResource(R.drawable.sevfivpr)
            else -> imageView.setImageResource(R.drawable.fullmoon)
        }

    }

    fun showFullM(v: View){
        showAllFullMoonActivity()
    }



    fun showAllFullMoonActivity(){
        val i = Intent(this,AllFullMoonActivity::class.java)
        i.putExtra("AlgorithmName",algorithmName)
        startActivityForResult(i,REQUEST_ALLFULLMOON_CODE)
    }

    fun showSettingsActivity(){
        val i = Intent(this,SettingsActivity::class.java)
        i.putExtra("AlgorithmName",algorithmName)
        startActivityForResult(i,REQUEST_MENU_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if((requestCode==REQUEST_ALLFULLMOON_CODE) && (resultCode == Activity.RESULT_OK)){
            if(data!=null){
                if(data.hasExtra("rAlgorithmName")){
//                    val name  = data.extras.getString("rAlgorithmName")
                    val name  = data.getStringExtra("rAlgorithmName")
                    if(name != null){
                        algorithmName = name.toString()
                    }
                }
            }
        }else if((requestCode==REQUEST_MENU_CODE) && (resultCode == Activity.RESULT_OK)){
            if(data!=null){
                if(data.hasExtra("rAlgorithmName")){
                    val name  = data.getStringExtra("rAlgorithmName")
                    if(name != null){
                        algorithmName = name.toString()
                        showTodayInformation()
                    }
                }
            }

        }
    }

    fun onSettingsClick(v: View){
        showSettingsActivity()
    }
}
