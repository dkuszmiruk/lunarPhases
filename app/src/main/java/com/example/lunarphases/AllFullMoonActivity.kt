package com.example.lunarphases

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AllFullMoonActivity : AppCompatActivity() {

    private var algorithmName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras ?: return
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_full_moon)
        val name = extras.getString("Algorithm")
        if(name != null){
            algorithmName = name.toString()
        }

    }

    override fun finish() {
        val data = Intent()

        data.putExtra("rAlgorithmName",algorithmName)
        setResult(Activity.RESULT_OK,data)

        super.finish()
    }
}
