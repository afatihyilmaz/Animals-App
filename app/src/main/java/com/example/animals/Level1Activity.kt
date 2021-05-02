package com.example.animals


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_level1.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class Level1Activity : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var sharedPreferences : SharedPreferences

    private val WELL_DONE = "Well done!"
    private var tts : TextToSpeech? = null
    private var animalList = ArrayList<String>()
    var imageArray = ArrayList<ImageView>()
    var score = 0
    private lateinit var timer: CountDownTimer
    private var currentAnimalIndex = 0
    private  var randomAnimalIndex = 0
    private var animalImageIndex = 0
    private var randomImageIndex = 0
    private var random = Random()
    private lateinit var currentMp : MediaPlayer
    private val animalsImage = arrayOf(
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.bird,
            R.drawable.cat,
            R.drawable.chicken,
            R.drawable.cow,
            R.drawable.dog,
            R.drawable.dolphin,
            R.drawable.donkey,
            R.drawable.duck,
            R.drawable.eagle,
            R.drawable.elephant,
            R.drawable.frog,
            R.drawable.horse,
            R.drawable.lion,
            R.drawable.monkey,
            R.drawable.musquito,
            R.drawable.owl,
            R.drawable.rooster,
            R.drawable.sheep,
            R.drawable.stork,
            R.drawable.tiger,
            R.drawable.wolf)

    private val animalSounds = arrayOf(
            R.raw.bear,
            R.raw.bee,
            R.raw.bird,
            R.raw.cat,
            R.raw.chicken,
            R.raw.cow,
            R.raw.dog,
            R.raw.dolphin,
            R.raw.donkey,
            R.raw.duck,
            R.raw.eagle,
            R.raw.elephant,
            R.raw.frog,
            R.raw.horse,
            R.raw.lion,
            R.raw.monkey,
            R.raw.musquito,
            R.raw.owl,
            R.raw.rooster,
            R.raw.sheep,
            R.raw.stork,
            R.raw.tiger,
            R.raw.wolf)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        sharedPreferences = this.getSharedPreferences("com.example.animals", Context.MODE_PRIVATE)
        addImageViewstoImageViewsArray()
         animalList = readFileContent()
        timerCountDown()
        timer.cancel()
        changeQuestion()
        tts = TextToSpeech(this, this)
    }

    private fun changeQuestion(){
        var oldAnimalIndex1 = 0
        var oldAnimalIndex2 = 0
        if(score == 0){
            generateIndexNumber()
            generateLevelIndexNumber()
            generateTrueAnimal()
            generateWrongAnimal()
            timer.start()
        }
        else if(score == 1){
            oldAnimalIndex1 = currentAnimalIndex
            generateIndexNumber()
            generateLevelIndexNumber()
            while(oldAnimalIndex1 == currentAnimalIndex){
                generateIndexNumber()
            }
            generateTrueAnimal()
            generateWrongAnimal()
            sayQuestion()
        }
        else if(score == 2){
            oldAnimalIndex2 = currentAnimalIndex
            generateIndexNumber()
            generateLevelIndexNumber()
            while(oldAnimalIndex1 == currentAnimalIndex || oldAnimalIndex2 == currentAnimalIndex){
                generateIndexNumber()
            }
            generateTrueAnimal()
            generateWrongAnimal()
            sayQuestion()
        }
        else if(score == 3){
            val intent = Intent(this, Level2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun generateIndexNumber(){
        currentAnimalIndex = random.nextInt(23)
        randomAnimalIndex = random.nextInt(23)
        while (currentAnimalIndex == randomAnimalIndex){
            randomAnimalIndex = random.nextInt(23)
        }
    }

    private fun generateLevelIndexNumber(){
        animalImageIndex = random.nextInt(2)
        randomImageIndex = random.nextInt(2)
        while(animalImageIndex == randomImageIndex){
            randomImageIndex = random.nextInt(2)
        }
    }

    private fun readFileContent() : ArrayList<String>{
        val scanner = Scanner(resources.openRawResource(R.raw.animals))
        val animalList = ArrayList<String>()
        while (scanner.hasNext()){
            animalList.add(scanner.nextLine())
        }
        scanner.close()
        return animalList
    }

    private fun timerCountDown(){
        timer = object : CountDownTimer(30500,1000){
            override fun onTick(millisUntilFinished: Long) {
                textViewTimer.text = "Time: " + millisUntilFinished/1000
            }
            override fun onFinish() {
                textViewTimer.text = "Time: 0"
                gameOverMessage()
            }
        }.start()
    }

    private fun sayQuestion(){
        val animal : String = animalList[currentAnimalIndex]
        currentMp = MediaPlayer.create(this, animalSounds[currentAnimalIndex])
        val str = "Find $animal , $animal makes sound"
        tts!!.speak(str , TextToSpeech.QUEUE_FLUSH, null, "")
        val handler = Handler()
        handler.postDelayed({currentMp.start()}, 2000)
    }

    fun sayName(view: View){
        tts!!.stop()
        currentMp.stop()
        tts!!.speak(animalList[currentAnimalIndex], TextToSpeech.QUEUE_ADD, null, "")
    }

    fun animalVoice(view: View){
        tts!!.stop()
        currentMp.stop()
        val mp: MediaPlayer = MediaPlayer.create(this, animalSounds[currentAnimalIndex])
        mp.start()
    }

    fun saveBestScore() {
    val getFromPreferences = sharedPreferences.getInt("bestScore", 0)
    if (score > getFromPreferences)
        sharedPreferences.edit().putInt("bestScore", score).apply()
    }

    fun generateTrueAnimal(){
            for (index in animalList) {
                if (animalList[currentAnimalIndex] == index) {
                    textViewFindAnimal.text = animalList[currentAnimalIndex]
                    imageArray[animalImageIndex].setImageResource(animalsImage[currentAnimalIndex])
                }
            }
        imageArray[animalImageIndex].setOnClickListener{
            tts!!.stop()
            currentMp.stop()
            score++
            val handler = Handler()
            tts!!.speak(WELL_DONE, TextToSpeech.QUEUE_ADD, null, "")
            if(score!=3){
                val mp: MediaPlayer = MediaPlayer.create(this, R.raw.correct)
                mp.start()
                handler.postDelayed({changeQuestion()}, 1500)
                textViewScore.text = "Score: $score"
                timer.cancel()
                timer.start()
            }
            else{
                val mp: MediaPlayer = MediaPlayer.create(this, R.raw.complete)
                mp.start()
                timer.cancel()
                handler.postDelayed({changeQuestion()}, 2000)
                textViewScore.text = "Score: $score"
            }

        }
    }

    fun generateWrongAnimal(){
        for (index in animalList) {
            if (animalList[randomAnimalIndex] == index) {
                imageArray[randomImageIndex].setImageResource(animalsImage[randomAnimalIndex])
            }
        }
        imageArray[randomImageIndex].setOnClickListener{
            tts!!.stop()
            currentMp.stop()
           gameOverMessage()
            timer.cancel()
        }
    }

    private fun addImageViewstoImageViewsArray(){
        imageArray.add(imageView)
        imageArray.add(imageView2)
    }

    private fun gameOverMessage(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.lose)
        mp.start()
        val alert = AlertDialog.Builder(this@Level1Activity)
        alert.setTitle("Game Over")
        alert.setMessage("Your score is $score. Restart The Game?")
        alert.setPositiveButton("Yes") {dialog, which ->
            //Restart
            val intent = intent
            finish()
            startActivity(intent)
        }
        alert.setNegativeButton("No"){dialog, which ->
            textViewTimer.text = "Time: 0"
            Toast.makeText(this@Level1Activity,"Game Over", Toast.LENGTH_LONG).show()
            val intent = Intent(this@Level1Activity, MainActivity::class.java)
            saveBestScore()
            finish()
            startActivity(intent)
        }
        alert.show()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }else{
                sayQuestion()
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }


}