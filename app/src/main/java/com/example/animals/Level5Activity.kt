package com.example.animals

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_level1.*
import kotlinx.android.synthetic.main.activity_level1.imageView
import kotlinx.android.synthetic.main.activity_level1.imageView2
import kotlinx.android.synthetic.main.activity_level1.textViewFindAnimal
import kotlinx.android.synthetic.main.activity_level1.textViewScore
import kotlinx.android.synthetic.main.activity_level1.textViewTimer
import kotlinx.android.synthetic.main.activity_level2.*
import kotlinx.android.synthetic.main.activity_level2.imageView3
import kotlinx.android.synthetic.main.activity_level3.*
import kotlinx.android.synthetic.main.activity_level3.imageView4
import kotlinx.android.synthetic.main.activity_level4.*
import kotlinx.android.synthetic.main.activity_level4.imageView5
import kotlinx.android.synthetic.main.activity_level4.imageView6
import kotlinx.android.synthetic.main.activity_level5.*
import java.util.*
import kotlin.collections.ArrayList

class Level5Activity : AppCompatActivity() , TextToSpeech.OnInitListener {

    private lateinit var sharedPreferences: SharedPreferences

    private var tts: TextToSpeech? = null
    private var animalList = ArrayList<String>()
    private var imageArray = ArrayList<ImageView>()
    private var score = 12
    private lateinit var timer: CountDownTimer
    private var currentAnimalIndex = 0
    private var randomAnimalIndex1 = 0
    private var randomAnimalIndex2 = 0
    private var randomAnimalIndex3 = 0
    private var randomAnimalIndex4 = 0
    private var randomAnimalIndex5 = 0
    private var randomAnimalIndex6 = 0
    private var randomAnimalIndex7 = 0
    private var animalImageIndex = 0
    private var randomImageIndex1 = 0
    private var randomImageIndex2 = 0
    private var randomImageIndex3 = 0
    private var randomImageIndex4 = 0
    private var randomImageIndex5 = 0
    private var randomImageIndex6 = 0
    private var randomImageIndex7 = 0
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
        setContentView(R.layout.activity_level5)
        this.sharedPreferences =
            this.getSharedPreferences("com.example.animals", Context.MODE_PRIVATE)


        addImageViewstoImageViewsArray()
        animalList = readFileContent()
        timerCountDown()
        timer.cancel()
        changeQuestion()
        tts = TextToSpeech(this, this)
    }

    private fun changeQuestion() {
        var oldAnimalIndex1 = 0
        var oldAnimalIndex2 = 0
        if (score == 12) {
            generateIndexNumber()
            generateLevelIndexNumber()
            generateTrueAnimal()
            generateWrongAnimal()
            timer.start()

        } else if (score == 13) {
            oldAnimalIndex1 = currentAnimalIndex
            generateIndexNumber()
            generateLevelIndexNumber()
            while (oldAnimalIndex1 == currentAnimalIndex) {
                generateIndexNumber()
            }
            generateTrueAnimal()
            generateWrongAnimal()
            sayQuestion()

        } else if (score == 14) {
            oldAnimalIndex2 = currentAnimalIndex
            generateIndexNumber()
            generateLevelIndexNumber()
            while (oldAnimalIndex1 == currentAnimalIndex || oldAnimalIndex2 == currentAnimalIndex) {
                generateIndexNumber()
            }
            generateTrueAnimal()
            generateWrongAnimal()
            sayQuestion()

        } else if (score == 15) {
            val alert = AlertDialog.Builder(this@Level5Activity)
            alert.setTitle("Congratulations!")
            alert.setMessage("You finished the game.Your score is $score. Restart The Game?")
            alert.setPositiveButton("Yes") { dialog, which ->
                //Restart
                val intent = Intent(this@Level5Activity, Level1Activity::class.java)
                finish()
                startActivity(intent)
            }
            alert.setNegativeButton("No") { dialog, which ->
                Toast.makeText(this@Level5Activity, "Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@Level5Activity, MainActivity::class.java)
                saveBestScore()
                finish()
                startActivity(intent)
            }
            alert.show()
        }
    }

    private fun generateIndexNumber() {
        currentAnimalIndex = random.nextInt(23)
        randomAnimalIndex1 = random.nextInt(23)
        randomAnimalIndex2 = random.nextInt(23)
        randomAnimalIndex3 = random.nextInt(23)
        randomAnimalIndex4 = random.nextInt(23)
        randomAnimalIndex5 = random.nextInt(23)
        randomAnimalIndex6 = random.nextInt(23)
        randomAnimalIndex7 = random.nextInt(23)

        while (currentAnimalIndex == randomAnimalIndex1 || currentAnimalIndex == randomAnimalIndex2
            || currentAnimalIndex == randomAnimalIndex3 || currentAnimalIndex == randomAnimalIndex4
            || currentAnimalIndex == randomAnimalIndex5 || currentAnimalIndex == randomAnimalIndex6
            || currentAnimalIndex == randomAnimalIndex7 || randomAnimalIndex1 == randomAnimalIndex2
            || randomAnimalIndex1 == randomAnimalIndex3 || randomAnimalIndex1 == randomAnimalIndex4
            || randomAnimalIndex1 == randomAnimalIndex5 || randomAnimalIndex1 == randomAnimalIndex6
            || randomAnimalIndex1 == randomAnimalIndex7 || randomAnimalIndex2 == randomAnimalIndex3
            || randomAnimalIndex2 == randomAnimalIndex4 || randomAnimalIndex2 == randomAnimalIndex5
            || randomAnimalIndex2 == randomAnimalIndex6 || randomAnimalIndex2 == randomAnimalIndex7
            || randomAnimalIndex3 == randomAnimalIndex4 || randomAnimalIndex3 == randomAnimalIndex5
            || randomAnimalIndex3 == randomAnimalIndex6 || randomAnimalIndex3 == randomAnimalIndex7
            || randomAnimalIndex4 == randomAnimalIndex5 || randomAnimalIndex4 == randomAnimalIndex6
            || randomAnimalIndex4 == randomAnimalIndex7 || randomAnimalIndex5 == randomAnimalIndex6
            || randomAnimalIndex5 == randomAnimalIndex7 || randomAnimalIndex6 == randomAnimalIndex7
        ) {
            if (currentAnimalIndex == randomAnimalIndex1)
                randomAnimalIndex1 = random.nextInt(23)
            if (currentAnimalIndex == randomAnimalIndex2)
                randomAnimalIndex2 = random.nextInt(23)
            if (currentAnimalIndex == randomAnimalIndex3)
                randomAnimalIndex3 = random.nextInt(23)
            if (currentAnimalIndex == randomAnimalIndex4)
                randomAnimalIndex4 = random.nextInt(23)
            if (currentAnimalIndex == randomAnimalIndex5)
                randomAnimalIndex5 = random.nextInt(23)
            if (currentAnimalIndex == randomAnimalIndex6)
                randomAnimalIndex6 = random.nextInt(23)
            if (currentAnimalIndex == randomAnimalIndex7)
                randomAnimalIndex7 = random.nextInt(23)
            if (randomAnimalIndex1 == randomAnimalIndex2)
                randomAnimalIndex2 = random.nextInt(23)
            if (randomAnimalIndex1 == randomAnimalIndex3)
                randomAnimalIndex3 = random.nextInt(23)
            if (randomAnimalIndex1 == randomAnimalIndex4)
                randomAnimalIndex4 = random.nextInt(23)
            if (randomAnimalIndex1 == randomAnimalIndex5)
                randomAnimalIndex5 = random.nextInt(23)
            if (randomAnimalIndex1 == randomAnimalIndex6)
                randomAnimalIndex6 = random.nextInt(23)
            if (randomAnimalIndex1 == randomAnimalIndex7)
                randomAnimalIndex7 = random.nextInt(23)
            if (randomAnimalIndex2 == randomAnimalIndex3)
                randomAnimalIndex3 = random.nextInt(23)
            if (randomAnimalIndex2 == randomAnimalIndex4)
                randomAnimalIndex4 = random.nextInt(23)
            if (randomAnimalIndex2 == randomAnimalIndex5)
                randomAnimalIndex5 = random.nextInt(23)
            if (randomAnimalIndex2 == randomAnimalIndex6)
                randomAnimalIndex6 = random.nextInt(23)
            if (randomAnimalIndex2 == randomAnimalIndex7)
                randomAnimalIndex7 = random.nextInt(23)
            if (randomAnimalIndex3 == randomAnimalIndex4)
                randomAnimalIndex4 = random.nextInt(23)
            if (randomAnimalIndex3 == randomAnimalIndex5)
                randomAnimalIndex5 = random.nextInt(23)
            if (randomAnimalIndex3 == randomAnimalIndex6)
                randomAnimalIndex6 = random.nextInt(23)
            if (randomAnimalIndex3 == randomAnimalIndex7)
                randomAnimalIndex7 = random.nextInt(23)
            if (randomAnimalIndex4 == randomAnimalIndex5)
                randomAnimalIndex5 = random.nextInt(23)
            if (randomAnimalIndex4 == randomAnimalIndex6)
                randomAnimalIndex6 = random.nextInt(23)
            if (randomAnimalIndex4 == randomAnimalIndex7)
                randomAnimalIndex7 = random.nextInt(23)
            if (randomAnimalIndex5 == randomAnimalIndex6)
                randomAnimalIndex6 = random.nextInt(23)
            if (randomAnimalIndex5 == randomAnimalIndex7)
                randomAnimalIndex7 = random.nextInt(23)
            if (randomAnimalIndex6 == randomAnimalIndex7)
                randomAnimalIndex6 = random.nextInt(23)
        }
    }


    private fun generateLevelIndexNumber() {
        animalImageIndex = random.nextInt(8)
        randomImageIndex1 = random.nextInt(8)
        randomImageIndex2 = random.nextInt(8)
        randomImageIndex3 = random.nextInt(8)
        randomImageIndex4 = random.nextInt(8)
        randomImageIndex5 = random.nextInt(8)
        randomImageIndex6 = random.nextInt(8)
        randomImageIndex7 = random.nextInt(8)

        while (animalImageIndex == randomImageIndex1 || animalImageIndex == randomImageIndex2
            || animalImageIndex == randomImageIndex3 || animalImageIndex == randomImageIndex4
            || animalImageIndex == randomImageIndex5 || animalImageIndex == randomImageIndex6
            || animalImageIndex == randomImageIndex7 || randomImageIndex1 == randomImageIndex2
            || randomImageIndex1 == randomImageIndex3 || randomImageIndex1 == randomImageIndex4
            || randomImageIndex1 == randomImageIndex5 || randomImageIndex1 == randomImageIndex6
            || randomImageIndex1 == randomImageIndex7 || randomImageIndex2 == randomImageIndex3
            || randomImageIndex2 == randomImageIndex4 || randomImageIndex2 == randomImageIndex5
            || randomImageIndex2 == randomImageIndex6 || randomImageIndex2 == randomImageIndex7
            || randomImageIndex3 == randomImageIndex4 || randomImageIndex3 == randomImageIndex5
            || randomImageIndex3 == randomImageIndex6 || randomImageIndex3 == randomImageIndex7
            || randomImageIndex4 == randomImageIndex5 || randomImageIndex4 == randomImageIndex6
            || randomImageIndex4 == randomImageIndex7 || randomImageIndex5 == randomImageIndex6
            || randomImageIndex5 == randomImageIndex7 || randomImageIndex6 == randomImageIndex7
        ) {
            if (animalImageIndex == randomImageIndex1)
                randomImageIndex1 = random.nextInt(8)
            if (animalImageIndex == randomImageIndex2)
                randomImageIndex2 = random.nextInt(8)
            if (animalImageIndex == randomImageIndex3)
                randomImageIndex3 = random.nextInt(8)
            if (animalImageIndex == randomImageIndex4)
                randomImageIndex4 = random.nextInt(8)
            if (animalImageIndex == randomImageIndex5)
                randomImageIndex5 = random.nextInt(8)
            if (animalImageIndex == randomImageIndex6)
                randomImageIndex6 = random.nextInt(8)
            if (animalImageIndex == randomImageIndex7)
                randomImageIndex7 = random.nextInt(8)
            if (randomImageIndex1 == randomImageIndex2)
                randomImageIndex2 = random.nextInt(8)
            if (randomImageIndex1 == randomImageIndex3)
                randomImageIndex3 = random.nextInt(8)
            if (randomImageIndex1 == randomImageIndex4)
                randomImageIndex4 = random.nextInt(8)
            if (randomImageIndex1 == randomImageIndex5)
                randomImageIndex5 = random.nextInt(8)
            if (randomImageIndex1 == randomImageIndex6)
                randomImageIndex6 = random.nextInt(8)
            if (randomImageIndex1 == randomImageIndex7)
                randomImageIndex7 = random.nextInt(8)
            if (randomImageIndex2 == randomImageIndex3)
                randomImageIndex3 = random.nextInt(8)
            if (randomImageIndex2 == randomImageIndex4)
                randomImageIndex4 = random.nextInt(8)
            if (randomImageIndex2 == randomImageIndex5)
                randomImageIndex5 = random.nextInt(8)
            if (randomImageIndex2 == randomImageIndex6)
                randomImageIndex6 = random.nextInt(8)
            if (randomImageIndex2 == randomImageIndex7)
                randomImageIndex7 = random.nextInt(8)
            if (randomImageIndex3 == randomImageIndex4)
                randomImageIndex4 = random.nextInt(8)
            if (randomImageIndex3 == randomImageIndex5)
                randomImageIndex5 = random.nextInt(8)
            if (randomImageIndex3 == randomImageIndex6)
                randomImageIndex6 = random.nextInt(8)
            if (randomImageIndex3 == randomImageIndex7)
                randomImageIndex7 = random.nextInt(8)
            if (randomImageIndex4 == randomImageIndex5)
                randomImageIndex5 = random.nextInt(8)
            if (randomImageIndex4 == randomImageIndex6)
                randomImageIndex6 = random.nextInt(8)
            if (randomImageIndex4 == randomImageIndex7)
                randomImageIndex7 = random.nextInt(8)
            if (randomImageIndex5 == randomImageIndex6)
                randomImageIndex6 = random.nextInt(8)
            if (randomImageIndex5 == randomImageIndex7)
                randomImageIndex7 = random.nextInt(8)
            if (randomImageIndex6 == randomImageIndex7)
                randomImageIndex7 = random.nextInt(8)
        }
    }

    private fun readFileContent(): ArrayList<String> {
        val scanner = Scanner(resources.openRawResource(R.raw.animals))
        val animalList = ArrayList<String>()
        while (scanner.hasNext()) {
            animalList.add(scanner.nextLine())
        }
        scanner.close()
        return animalList
    }

    private fun timerCountDown() {
        timer = object : CountDownTimer(24500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textViewTimer.text = "Time: " + millisUntilFinished / 1000
            }
            override fun onFinish() {
                textViewTimer.text = "Time: 0"
                gameOverMessage()
            }
        }.start()

    }


    private fun sayQuestion() {
        val animal : String = animalList[currentAnimalIndex]
        currentMp = MediaPlayer.create(this, animalSounds[currentAnimalIndex])
        val str = "Find $animal , $animal makes sound"
        tts!!.speak(str , TextToSpeech.QUEUE_FLUSH, null, "")
        val handler = Handler()
        handler.postDelayed({currentMp.start()}, 2000)
    }


    fun sayName(view: View) {
        tts!!.stop()
        currentMp.stop()
        tts!!.speak(animalList[currentAnimalIndex], TextToSpeech.QUEUE_ADD, null, "")
    }

    fun animalVoice(view: View) {
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


    private fun generateTrueAnimal() {
        for (index in animalList) {
            if (animalList[currentAnimalIndex] == index) {
                textViewFindAnimal.text = animalList[currentAnimalIndex]
                imageArray[animalImageIndex].setImageResource(animalsImage[currentAnimalIndex])
            }
        }
        imageArray[animalImageIndex].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            score++
            val handler = Handler()
            tts!!.speak("Well Done!", TextToSpeech.QUEUE_ADD, null, "")
            if (score != 15) {
                val mp: MediaPlayer = MediaPlayer.create(this, R.raw.correct)
                mp.start()
                handler.postDelayed({ changeQuestion() }, 1500)
                textViewScore.text = "Score: $score"
                timer.cancel()
                timer.start()
            } else {
                val mp: MediaPlayer = MediaPlayer.create(this, R.raw.complete)
                mp.start()
                handler.postDelayed({ changeQuestion() }, 2000)
                textViewScore.text = "Score: $score"
                timer.cancel()
            }
        }
    }

    private fun generateWrongAnimal() {
        for (index in animalList) {
            if (animalList[randomAnimalIndex1] == index) {
                imageArray[randomImageIndex1].setImageResource(animalsImage[randomAnimalIndex1])
            }
            if (animalList[randomAnimalIndex2] == index) {
                imageArray[randomImageIndex2].setImageResource(animalsImage[randomAnimalIndex2])
            }
            if (animalList[randomAnimalIndex3] == index) {
                imageArray[randomImageIndex3].setImageResource(animalsImage[randomAnimalIndex3])
            }
            if (animalList[randomAnimalIndex4] == index) {
                imageArray[randomImageIndex4].setImageResource(animalsImage[randomAnimalIndex4])
            }
            if (animalList[randomAnimalIndex5] == index) {
                imageArray[randomImageIndex5].setImageResource(animalsImage[randomAnimalIndex5])
            }
            if (animalList[randomAnimalIndex6] == index) {
                imageArray[randomImageIndex6].setImageResource(animalsImage[randomAnimalIndex6])
            }
            if (animalList[randomAnimalIndex7] == index) {
                imageArray[randomImageIndex7].setImageResource(animalsImage[randomAnimalIndex7])
            }
        }
        imageArray[randomImageIndex1].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            timer.cancel()
            gameOverMessage()
        }
        imageArray[randomImageIndex2].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            timer.cancel()
            gameOverMessage()
        }
        imageArray[randomImageIndex3].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            timer.cancel()
            gameOverMessage()
        }
        imageArray[randomImageIndex4].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            timer.cancel()
            gameOverMessage()
        }
        imageArray[randomImageIndex5].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            timer.cancel()
            gameOverMessage()
        }
        imageArray[randomImageIndex6].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            timer.cancel()
            gameOverMessage()
        }
        imageArray[randomImageIndex7].setOnClickListener {
            tts!!.stop()
            currentMp.stop()
            timer.cancel()
            gameOverMessage()
        }

    }

    private fun addImageViewstoImageViewsArray() {
        imageArray.add(imageView)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add((imageView8))
    }

    private fun gameOverMessage(){
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.lose)
        mp.start()
        val alert = AlertDialog.Builder(this@Level5Activity)
        alert.setTitle("Game Over")
        alert.setMessage("Your score is $score. Restart The Game?")
        alert.setPositiveButton("Yes") {dialog, which ->
            //Restart
            val intent = Intent(this@Level5Activity, Level1Activity::class.java)
            saveBestScore()
            finish()
            startActivity(intent)
        }
        alert.setNegativeButton("No"){dialog, which ->

            Toast.makeText(this@Level5Activity,"Game Over", Toast.LENGTH_LONG).show()
            val intent = Intent(this@Level5Activity, MainActivity::class.java)
            saveBestScore()
            finish()
            startActivity(intent)
        }
        alert.show()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                sayQuestion()
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }
}