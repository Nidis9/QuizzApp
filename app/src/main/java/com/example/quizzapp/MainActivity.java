package com.example.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;

    Button answerA;
    Button answerB;
    Button answerC;
    Button answerD;


    private String selectedAnswer = "";
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int totalQuestions = 0;
    private static final int ANSWER_BUTTON_COLOR = Color.parseColor("#45C84B");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.totalQuestions);
        questionTextView = findViewById(R.id.question);

        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);


        String totalQuestionsText = getString(R.string.total_questions) + totalQuestions;
        totalQuestionsTextView.setText(totalQuestionsText);

        setAnswerButtonColors();

        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);

        loadNewQuestion();
    }




    @Override
    public void onClick(View view) {
        if (view == answerA || view == answerB || view == answerC || view == answerD) {
            selectAnswer((Button) view);
            checkAnswer();
            loadNewQuestion();
        }
    }


    private void setAnswerButtonColors() {
        answerA.setBackgroundColor(ANSWER_BUTTON_COLOR);
        answerB.setBackgroundColor(ANSWER_BUTTON_COLOR);
        answerC.setBackgroundColor(ANSWER_BUTTON_COLOR);
        answerD.setBackgroundColor(ANSWER_BUTTON_COLOR);

        answerA.setTextColor(Color.WHITE);
        answerB.setTextColor(Color.WHITE);
        answerC.setTextColor(Color.WHITE);
        answerD.setTextColor(Color.WHITE);
    }



    private void selectAnswer(Button button) {
        deselectAllAnswers();
        selectedAnswer = button.getText().toString();
    }

    private void deselectAllAnswers() {
        answerA.setBackgroundColor(ANSWER_BUTTON_COLOR);
        answerB.setBackgroundColor(ANSWER_BUTTON_COLOR);
        answerC.setBackgroundColor(ANSWER_BUTTON_COLOR);
        answerD.setBackgroundColor(ANSWER_BUTTON_COLOR);
    }

    private void checkAnswer() {
        String correctAnswer = QuestionandAnswers.correctAnswers[currentQuestionIndex];
        if (selectedAnswer.equalsIgnoreCase(correctAnswer)) {
            score++;
        }
        currentQuestionIndex++;
    }




    void loadNewQuestion() {
        totalQuestions = QuestionandAnswers.question.length;

        if (currentQuestionIndex < totalQuestions) {
            questionTextView.setText(QuestionandAnswers.question[currentQuestionIndex]);
            answerA.setText(QuestionandAnswers.choices[currentQuestionIndex][0]);
            answerB.setText(QuestionandAnswers.choices[currentQuestionIndex][1]);
            answerC.setText(QuestionandAnswers.choices[currentQuestionIndex][2]);
            answerD.setText(QuestionandAnswers.choices[currentQuestionIndex][3]);
        } else {
            finishQuiz();
        }
    }


    void finishQuiz() {
        String results;
        int correctAnswers = score; // Only count the number of correct answers

        if (correctAnswers > totalQuestions * 0.60) {
            results = "You did well!";
        } else {
            results = "You can always try again and do better!";
        }

        new AlertDialog.Builder(this)
                .setTitle(results)
                .setMessage("Your score is " + correctAnswers + " out of " + totalQuestions)
                .setPositiveButton("Restart", (DialogInterface dialog, int i) -> restartQuiz())
                .show();
    }


    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}