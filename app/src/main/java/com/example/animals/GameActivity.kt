package com.example.animals


import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.animals.fragments.Level1
import java.util.*
import kotlin.collections.ArrayList


class GameActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    var tts : TextToSpeech? = null
    var animalList = ArrayList<String>()
    var imageArray = ArrayList<ImageView>()
    var score = 0
    var currentAnimal = ""
    var currentIndex = 0
    var currentLevel = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        addImagestoImageArray()




         animalList = read_file_content()
        val animalText = findViewById<TextView>(R.id.textViewFindAnimal)
        val random = Random()
        val randomIndex = random.nextInt(16)



        timerCountDown()

      /*  val txt_file = findViewById<TextView>(R.id.textViewFindAnimal)
        txt_file.setText(animalList[5])*/

       // tts = TextToSpeech(this, this)

    }


    fun read_file(view: View){
        val str = read_file_content()
        tts!!.speak(str[0], TextToSpeech.QUEUE_FLUSH, null, "")
    }


    private fun read_file_content() : ArrayList<String>{
        val scanner: Scanner = Scanner(resources.openRawResource(R.raw.animals))
        val animalList = ArrayList<String>()
      //  val stringbuilder: StringBuilder = StringBuilder()
        while (scanner.hasNext()){
            animalList.add(scanner.nextLine())
        }
        scanner.close()

        return animalList
    }

    fun timerCountDown(){
        object : CountDownTimer(20500,1000){
            val timeText = findViewById<TextView>(R.id.textViewTimer)
            override fun onTick(millisUntilFinished: Long) {

                timeText.text = "Time: " + millisUntilFinished/1000
            }

            override fun onFinish() {
                timeText.text = "Time: 0"

                //Alert
                val alert = AlertDialog.Builder(this@GameActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Your score is $score. Restart The Game?")
                alert.setPositiveButton("Yes") {dialog, which ->
                    //Restart
                    val intent = intent
                    finish()
                    startActivity(intent)

                }
                alert.setNegativeButton("No"){dialog, which ->
                    Toast.makeText(this@GameActivity,"Game Over", Toast.LENGTH_LONG).show()
                }
                alert.show()

            }

        }.start()

    }




    fun sayName(view: View){

    }

    fun animalVoice(view: View){

    }

    fun levelDesigner() {

    }
    fun increaseScore(view: View){
        score++
        val scoreText = findViewById<TextView>(R.id.textViewScore)
        scoreText.text = "Score: $score"
    }

    fun addImagestoImageArray(){
        var image = findViewById<ImageView>(R.id.imageView00)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView01)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView02)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView10)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView11)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView12)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView20)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView21)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView22)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView30)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView31)
        imageArray.add(image)
        image = findViewById<ImageView>(R.id.imageView32)
        imageArray.add(image)

    }

    fun hideImages(level:Int){
        //levele göre imageları hideliyoruz
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            tts!!.setLanguage(Locale.US)
            Toast.makeText(this, "TTS is ready!", Toast.LENGTH_SHORT).show()
        }
    }
}


/*
// val homeFragment = HomeFragment()
    val level1Fragment = Level1()

      // makeCurrentFragment(level1Fragment)

private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
      replace(R.id.fl_wrapper, fragment)
      addToBackStack(null)
      commit()
  }*/