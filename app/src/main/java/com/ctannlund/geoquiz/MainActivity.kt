package com.ctannlund.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.ctannlund.geoquiz.databinding.ActivityMainBinding

private const val TAG="MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {

            quizViewModel.moveToNext()

            updateQuestion()

        }

        binding.previousButton.setOnClickListener {

            quizViewModel.moveToPrev()

            updateQuestion()

        }

        updateQuestion() // updates to first question on start

    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText

        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        }
        else {
            R.string.incorrect_toast
        }

        val toast = Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        )

        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()

    }

}