package com.example.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
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

    Button submitButton;

    private String selectedAnswer = "";
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int totalQuestions = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        totalQuestionsTextView = findViewById(R.id.totalQuestions);
        questionTextView = findViewById(R.id.question);

        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);

        submitButton = findViewById(R.id.submitButton);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestions = QuestionandAnswers.question.length;

        totalQuestionsTextView.setText("Total Questions: " + totalQuestions);


        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {
        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        Button clickedButton = (Button) view;

        if(clickedButton.getId()==R.id.submitButton){
            if(selectedAnswer.equals(QuestionandAnswers.correctAnswers[currentQuestionIndex])){
                score++;
                currentQuestionIndex++;
                loadNewQuestion();
            }
        }
        else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.BLUE);
            answerA.setBackgroundColor(Color.WHITE);
            answerB.setBackgroundColor(Color.WHITE);
            answerC.setBackgroundColor(Color.WHITE);
            answerD.setBackgroundColor(Color.WHITE);


        }
    }

    void loadNewQuestion(){
        questionTextView.setText(QuestionandAnswers.question[currentQuestionIndex]);
        answerA.setText(QuestionandAnswers.choices[currentQuestionIndex][0]);
        answerB.setText(QuestionandAnswers.choices[currentQuestionIndex][0]);
        answerC.setText(QuestionandAnswers.choices[currentQuestionIndex][0]);
        answerD.setText(QuestionandAnswers.choices[currentQuestionIndex][0]);

        if(currentQuestionIndex==totalQuestions){
            finishQuiz();
            return;
        }
    }
    void finishQuiz() {
        String results = "";

        if (score > totalQuestions * 0.60) {
            results = "You did well!";
        } else {
            results = "You can always try again and do better!";
        }

        new AlertDialog.Builder(this)
                .setTitle(results)
                .setMessage("Your score is " + score + " out of " + totalQuestions)
                .setPositiveButton("Restart", (DialogInterface dialog, int i) -> restartQuiz())
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}
