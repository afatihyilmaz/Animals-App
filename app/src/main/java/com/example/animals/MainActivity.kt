package com.example.animals

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = this.getSharedPreferences("com.example.animals", Context.MODE_PRIVATE)


        val getFromPreferences = sharedPreferences.getInt("bestScore", 0)
        if(getFromPreferences == 0 ){
            textViewBestScore.text = "Best Score: 0"
        }
        else{
         textViewBestScore.text = "Best Score: $getFromPreferences"
        }
        buttonMainPlay.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, Level1Activity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

}