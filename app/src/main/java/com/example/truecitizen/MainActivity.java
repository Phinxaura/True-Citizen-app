package com.example.truecitizen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.truecitizen.databinding.ActivityMainBinding;
import com.example.truecitizen.model.Question;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
//service not found error comes  of freeze emulator for solving this use cold boot inside avd manager
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private Question[] questionBank = new Question[]{
     //create question objects
     new Question(R.string.question_declaration,true),
     new Question(R.string.question_amendments,true),
     new Question(R.string.question_constitution,false),
     new Question(R.string.question_independence_rights,true),
     new Question(R.string.question_religion,true),
     new Question(R.string.question_government,false),
     new Question(R.string.question_government_feds,false),
     new Question(R.string.question_government_senators,false),
     //and add more

};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView( this,R.layout.activity_main);
        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
        binding.trueButton.setOnClickListener(view -> checkAnswer(true));
        binding.falseButton.setOnClickListener(view -> checkAnswer(false));
        binding.nextButton.setOnClickListener(view -> {
           // Log.d("Main","onCreate: " + questionBank[currentQuestionIndex++].getAnswerResId());
            currentQuestionIndex = (currentQuestionIndex + 1)%questionBank.length;//currentQuestionIndex + 1
           //1modulo8 = remainder is 0.
            updateQuestion();
        });
        binding.prevButton.setOnClickListener(view -> {
            if(currentQuestionIndex > 0){
                currentQuestionIndex = (currentQuestionIndex - 1)%questionBank.length;
                updateQuestion();
            }
        });
    }

    private void checkAnswer(boolean userChoseCorrect){
        boolean answerIsCorrect = questionBank[currentQuestionIndex].isAnswerTrue();
        int messageId;
        if(answerIsCorrect == userChoseCorrect){
            messageId = R.string.correct_answer;
        }else{
            messageId = R.string.wrong_answer;
        }

        Snackbar.make(binding.imageView,messageId,Snackbar.LENGTH_SHORT)
                .show();
    }
    private void updateQuestion() {
        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }


}