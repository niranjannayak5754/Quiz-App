package com.example.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.models.Quiz
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    //declaring the required variables in the code
    private lateinit var binding: ActivityMainBinding
    private val questions = ArrayList<Quiz>()
    var currScore = 0   // each correct answer adds 5 points in currScore
    private var currQuestion = 0 // current number of  question indexing to the arrayList
    var selectedOption = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setting the hard-coded questions(i.e, 10 questions) for the quiz
        setQuizQuestions()

//        disabling the submit button until all questions are attempted
        binding.btnSubmit.visibility = View.GONE

//      to make the current question visible on the screen
        setDataToViews()

//        handling the clicks in the given options
        binding.btnOption1.setOnClickListener {
            setOptionsView()    // to handle more than one clicks in the same question before confirming answer
            binding.btnOption1.setBackgroundColor(getColor(R.color.transparent))
            selectedOption = 1
        }
        binding.btnOption2.setOnClickListener {
            setOptionsView()    // to handle more than one clicks in the same question before confirming answer
            binding.btnOption2.setBackgroundColor(getColor(R.color.transparent))
            selectedOption = 2
        }
        binding.btnOption3.setOnClickListener {
            setOptionsView()     // to handle more than one clicks in the same question before confirming answer
            binding.btnOption3.setBackgroundColor(getColor(R.color.transparent))
            selectedOption = 3
        }
        binding.btnOption4.setOnClickListener {
            setOptionsView()     // to handle more than one clicks in the same question before confirming answer
            binding.btnOption4.setBackgroundColor(getColor(R.color.transparent))
            selectedOption = 4
        }

//    To check the answer is correct or not ( Green color for right answer and red for the wrong)
        binding.btnConfirm.setOnClickListener {
            if (selectedOption == 0)
                Toast.makeText(this, "Select Answer First", Toast.LENGTH_SHORT).show()
            else {
                if (questions[currQuestion].corrAnswer == selectedOption) {
                    currScore += 5
                    when (selectedOption) {
                        1 -> binding.btnOption1.setBackgroundColor(getColor(R.color.green))
                        2 -> binding.btnOption2.setBackgroundColor(getColor(R.color.green))
                        3 -> binding.btnOption3.setBackgroundColor(getColor(R.color.green))
                        4 -> binding.btnOption4.setBackgroundColor(getColor(R.color.green))
                    }
                } else {
                    when (selectedOption) {
                        1 -> binding.btnOption1.setBackgroundColor(getColor(R.color.red))
                        2 -> binding.btnOption2.setBackgroundColor(getColor(R.color.red))
                        3 -> binding.btnOption3.setBackgroundColor(getColor(R.color.red))
                        4 -> binding.btnOption4.setBackgroundColor(getColor(R.color.red))
                    }
                    Toast.makeText(
                        this,
                        "Wrong! The correct answer is option ${questions[currQuestion].corrAnswer}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                currQuestion++
                disableViews()
            }
        }

//        loading the next question in the screen
        binding.btnNext.setOnClickListener {
            if (binding.btnConfirm.visibility == View.VISIBLE)
                Toast.makeText(this, "Confirm answer first", Toast.LENGTH_SHORT).show()
            else {
                setOptionsView()
                enableViews()
                if (currQuestion != 9) {
                    setDataToViews()
                } else {
                    binding.btnNext.visibility = View.GONE
                    setDataToViews()
                    binding.btnSubmit.visibility = View.VISIBLE
                }
            }
        }

        //    to submit the quiz and view scores
        binding.btnSubmit.setOnClickListener {
            showBottomSheet()
        }
    }

    //    bottomSheet view to view scores and restart quiz
    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        val bottomSheetView = LayoutInflater.from(applicationContext)
            .inflate(R.layout.bottomsheet_view_score, findViewById(R.id.idLL_bottomSheet))

        val tvScore = bottomSheetView.findViewById<TextView>(R.id.tvScore)
        val btnRestartQuiz = bottomSheetView.findViewById<Button>(R.id.btnRestartQuiz)

        tvScore.text = "Your Score is \n $currScore/50"

        btnRestartQuiz.setOnClickListener {
            currScore = 0
            currQuestion = 0

            setOptionsView()
            enableViews()

            binding.btnSubmit.visibility = View.GONE
            binding.btnNext.visibility = View.VISIBLE
            setDataToViews()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

    }


    //    disable all the buttons to make it easy to choose single answer at one time
    private fun disableViews() {
        binding.btnConfirm.visibility = View.GONE

        binding.btnOption1.isEnabled = false
        binding.btnOption2.isEnabled = false
        binding.btnOption3.isEnabled = false
        binding.btnOption4.isEnabled = false

    }

    //    enables the buttons before the next questions shows on the screen
    private fun enableViews() {
        binding.btnConfirm.visibility = View.VISIBLE
        selectedOption = 0

        binding.btnOption1.isEnabled = true
        binding.btnOption2.isEnabled = true
        binding.btnOption3.isEnabled = true
        binding.btnOption4.isEnabled = true
    }

    //    this function handles the more than one clicks in options for same question, sets to initial states
    private fun setOptionsView() {
        binding.btnOption1.background = getDrawable(R.drawable.rounded_borders)
        binding.btnOption2.background = getDrawable(R.drawable.rounded_borders)
        binding.btnOption3.background = getDrawable(R.drawable.rounded_borders)
        binding.btnOption4.background = getDrawable(R.drawable.rounded_borders)
    }

    private fun setDataToViews() {
        binding.tvCounter.text = "${currQuestion + 1}/10"
        binding.tvQuestion.text = questions[currQuestion].question
        binding.ivImage.setImageDrawable(questions[currQuestion].drawableImageName)
        binding.btnOption1.text = questions[currQuestion].option_A
        binding.btnOption2.text = questions[currQuestion].option_B
        binding.btnOption3.text = questions[currQuestion].option_C
        binding.btnOption4.text = questions[currQuestion].option_D
    }

    private fun setQuizQuestions() {
        questions.add(
            Quiz(
                "Which country has given flag?",
                getDrawable(R.drawable.flag),
                "Nepal",
                "India",
                "China",
                "USA",
                1
            )
        )
        questions.add(
            Quiz(
                "Identify the logo:",
                getDrawable(R.drawable.insta),
                "Facebook",
                "Google",
                "Instagram",
                "Whatsapp",
                3
            )
        )
        questions.add(
            Quiz(
                "The Cat in the github's logo is called:",
                getDrawable(R.drawable.github),
                "Syrus",
                "Meow",
                "NeoCat",
                "OctaCat",
                4
            )
        )

        questions.add(
            Quiz(
                "Who is the founder of Amazon?",
                getDrawable(R.drawable.amazon),
                "Jeff Bezos",
                "Elon Musk",
                "Mark Zuckerberg",
                "None of the above",
                1
            )
        )
        questions.add(
            Quiz(
                "Mona Lisa is painted by:",
                getDrawable(R.drawable.monalisa),
                "Picasso",
                "Leonardo da Vinci",
                "Socrates",
                "Shakespeare",
                2
            )
        )

        questions.add(
            Quiz(
                "Who is the father of Computer?",
                getDrawable(R.drawable.computer),
                "Charles Babbage",
                "Lady Augusta Ada",
                "Sir Isaac Newton",
                "Albert Einstein",
                1
            )
        )

        questions.add(
            Quiz(
                "Who is he?",
                getDrawable(R.drawable.google),
                "Founder of Facebook",
                "Founder of GFG",
                "Google CEO",
                "Apple CEO",
                3
            )
        )

        questions.add(
            Quiz(
                "Who is the CEO of Tesla Motors?",
                getDrawable(R.drawable.tesla),
                "Bill Gates",
                "Mukesh Ambani",
                "Vladimir Putin",
                "Elon Musk",
                4
            )
        )

        questions.add(
            Quiz(
                "Who founds Dream11?",
                getDrawable(R.drawable.dream),
                "Bhavit Sheth & Harsh Jain",
                "Ashneer Grover",
                "Ratan Tata",
                "Mahendra Singh Dhoni",
                1
            )
        )

        questions.add(
            Quiz(
                "Who won MOM award in 2011 ICC world cup final?",
                getDrawable(R.drawable.computer),
                "Sachin Tendulkar",
                "Gautam Gambhir",
                "MS Dhoni",
                "Yuvraj Singh",
                3
            )
        )
    }
}