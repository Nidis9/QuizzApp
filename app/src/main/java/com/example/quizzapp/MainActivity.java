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


        totalQuestions = QuestionandAnswers.question.length;

        totalQuestionsTextView.setText("Total Questions: " + totalQuestions);


        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {
        if (view == answerA || view == answerB || view == answerC || view == answerD) {
            selectAnswer((Button) view);
            checkAnswer();
        }
    }


    private void selectAnswer(Button button) {
        deselectAllAnswers();
        button.setBackgroundColor(Color.BLUE);
        selectedAnswer = button.getText().toString();
    }


    private void deselectAllAnswers() {
        answerA.setBackgroundColor(Color.WHITE);
        answerB.setBackgroundColor(Color.WHITE);
        answerC.setBackgroundColor(Color.WHITE);
        answerD.setBackgroundColor(Color.WHITE);
    }

    private void checkAnswer() {
        if (selectedAnswer.equals(QuestionandAnswers.correctAnswers[currentQuestionIndex])) {
            score++;
        }
        currentQuestionIndex++;
        loadNewQuestion();
    }

    void loadNewQuestion() {
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