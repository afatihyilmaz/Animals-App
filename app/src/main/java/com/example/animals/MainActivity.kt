package com.example.animals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val buttonPlay = findViewById<Button>(R.id.buttonMainPlay)

        buttonPlay.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, GameActivity::class.java)

                startActivity(intent)
                finish()
            }
        })

    }






}