package com.example.lunarphases

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private var algorithmName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val extras = intent.extras ?: return
        val name = extras.getString("AlgorithmName")
        if(name != null){
            algorithmName = name.toString()
        }
          radioGroup.check(findRadioId(algorithmName))
    }

    override fun finish() {
        val data = Intent()
        algorithmName = findRadioName(radioGroup.checkedRadioButtonId)
        data.putExtra("rAlgorithmName",algorithmName)
        setResult(Activity.RESULT_OK,data)
        super.finish()
    }

    fun okClick(v: View){
//        onBackPressed()
        this.finish()
    }

    fun findRadioId(algorithmName:String): Int {
        when{
            algorithmName.equals("Simple") -> return radioSimple.id
            algorithmName.equals("Conway") -> return radioConway.id
            algorithmName.equals("Trig1") -> return radioTrig1.id
            algorithmName.equals("Trig2") -> return radioTrig2.id
            else -> return radioTrig2.id
        }
    }

    fun findRadioName(id:Int): String {
        when{
            id.equals(radioSimple.id) -> return radioSimple.text.toString()
            id.equals(radioConway.id) -> return radioConway.text.toString()
            id.equals(radioTrig1.id) -> return radioTrig1.text.toString()
            id.equals(radioTrig2.id) -> return radioTrig2.text.toString()
            else -> return radioTrig2.text.toString()
        }
    }

}
