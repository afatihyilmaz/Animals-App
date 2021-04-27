package com.example.animals


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList


class GameActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    var tts : TextToSpeech? = null
    var animalList = ArrayList<String>()
    var imageArray = ArrayList<ImageView>()
    var score = 0
    var currentIndex = 0
    var currentLevel = 1
    val animalsImage = arrayOf(R.drawable.bird,R.drawable.cat,R.drawable.chicken,R.drawable.cock, R.drawable.cow,
            R.drawable.dog, R.drawable.donkey, R.drawable.duck, R.drawable.elephant, R.drawable.frog, R.drawable.horse,
            R.drawable.lion, R.drawable.monkey, R.drawable.owl, R.drawable.sheep, R.drawable.wolf)

    val animalSounds = arrayOf(R.raw.birdd,R.raw.catt,R.raw.chickenn,R.raw.cockk, R.raw.coww, R.raw.dogg, R.raw.donkeyy,
            R.raw.duckk, R.raw.elephantt, R.raw.frogg, R.raw.horsee, R.raw.lionn, R.raw.monkeyy, R.raw.owll, R.raw.sheepp, R.raw.wolff)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        addImagestoImageArray()


        hideImages(currentLevel)

         animalList = read_file_content()
        val animalText = findViewById<TextView>(R.id.textViewFindAnimal)
        currentIndex = generateIndexNumber()
        for(index in animalList){
            if(animalList[currentIndex] == index){
                animalText.text = animalList[currentIndex]
                imageArray[3].setImageResource(animalsImage[currentIndex])

            }
        }

        tts = TextToSpeech(this, this)


        timerCountDown()

      /*  val txt_file = findViewById<TextView>(R.id.textViewFindAnimal)
        txt_file.setText(animalList[5])*/



    }


    private fun generateIndexNumber():Int{
        val random = Random()
        val randomIndex = random.nextInt(16)

        return randomIndex
    }


    fun read_file(view: View){
        val str = read_file_content()
        tts!!.speak(str[0], TextToSpeech.QUEUE_ADD, null, "")
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
                    val intent = Intent(this@GameActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                alert.show()

            }

        }.start()

    }




    fun sayName(view: View){
        val str = animalList[currentIndex]
        tts!!.speak(animalList[currentIndex], TextToSpeech.QUEUE_ADD, null, "")

    }

    fun animalVoice(view: View){
        val mp: MediaPlayer = MediaPlayer.create(this, animalSounds[currentIndex])
        mp.start()
    }

    fun levelDesigner(level:Int) {


        if(level == 1){
            hideImages(level)
        }
        else if(level == 2){
            hideImages(level)
        }
        else if(level == 3){
            hideImages(level)
        }
        else if(level == 4){
            hideImages(level)
        }
        else if(level == 5){
            hideImages(level)
        }
        else{
            hideImages(level)
        }



    }

    fun increaseCurrentLevel(score:Int): Int{
        if(score  >= 3 ){
            currentLevel = 2
        }
        else if(score  >= 6 ){
            currentLevel = 3
        }
        else if(score  >= 9 ){
            currentLevel = 4
        }
        else if(score  >= 12 ){
            currentLevel = 5
        }
        else
            currentLevel = 1

        return currentLevel
    }
    fun increaseScore(view: View){
        score++
        val scoreText = findViewById<TextView>(R.id.textViewScore)
        scoreText.text = "Score: $score"
        increaseCurrentLevel(score)
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
        if(level == 1) {
            for (image in imageArray) {
                if (image == findViewById(R.id.imageView10) || image == findViewById(R.id.imageView12) ) {
                    image.visibility = View.VISIBLE
                }
                else
                    image.visibility = View.INVISIBLE
            }
        }

        else if(level == 2) {
            for (image in imageArray) {
                if (image == findViewById(R.id.imageView10) || image == findViewById(R.id.imageView12) || image == findViewById(R.id.imageView01) ) {
                    image.visibility = View.VISIBLE
                }
                else
                    image.visibility = View.INVISIBLE
            }
        }
        else if(level == 3) {
            for (image in imageArray) {
                if (image == findViewById(R.id.imageView10) || image == findViewById(R.id.imageView12)
                        || image == findViewById(R.id.imageView01)   || image == findViewById(R.id.imageView21) ){
                    image.visibility = View.VISIBLE
                }
                else
                    image.visibility = View.INVISIBLE
            }
        }
        else if(level == 4) {
            for (image in imageArray) {
                if (image == findViewById(R.id.imageView00) || image == findViewById(R.id.imageView02)
                        ||image == findViewById(R.id.imageView11) || image == findViewById(R.id.imageView21)
                        ||image == findViewById(R.id.imageView30) || image == findViewById(R.id.imageView32)) {
                    image.visibility = View.VISIBLE
                }
                else
                    image.visibility = View.INVISIBLE
            }
        }
        else if(level == 5) {
            for (image in imageArray) {
                if (image == findViewById(R.id.imageView00) || image == findViewById(R.id.imageView02)
                        ||image == findViewById(R.id.imageView30) || image == findViewById(R.id.imageView32)) {
                    image.visibility = View.INVISIBLE
                }
                else
                    image.visibility = View.VISIBLE
            }
        }
        else{
            for (image in imageArray) {
                    image.visibility = View.VISIBLE
                }
        }

    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                val buttonName = findViewById<Button>(R.id.buttonSoundAnimalName)
                buttonName!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
           // Toast.makeText(this, "TTS is ready!", Toast.LENGTH_SHORT).show()
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