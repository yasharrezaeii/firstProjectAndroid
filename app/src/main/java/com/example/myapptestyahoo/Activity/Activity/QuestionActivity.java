package com.example.myapptestyahoo.Activity.Activity;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapptestyahoo.Activity.Data;
import com.example.myapptestyahoo.Activity.Utils;
import com.example.myapptestyahoo.Activity.model.Levels;
import com.example.myapptestyahoo.Activity.model.Questions;
import com.example.myapptestyahoo.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class QuestionActivity extends AppCompatActivity {
    private RadioGroup rgAnswer;
    private Questions[] questions;
    private Button btnPreviousQuestion, btnNetQuestion, btnEndTest;
    private TextView txtTime, txtnumberQuestionOfLevel, txtCounter, txtTextQuestion;
    private CircularProgressBar circularProgressBar;
    private int time, timeTookTest;
    private answer[] answers;
    private ProgressBar prgCount;
    private Data data;
    private byte numberQuestionOfLevel, counter = 0, mood = 1, nineteen, wrongNumber, correctNumber;
    private boolean isEndExam = false;
    private Bundle bundle;
    private Utils utils;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setupView();
        setData();
        bundle = getIntent().getExtras();
        utils = new Utils(this);
        if (mood == 1) {
            exam(10); // get time question from TestActivity
//            exam(bundle.getInt("testTime"));

        } else if (mood == 2) {
            btnEndTest.setText("");
            circularProgressBar.setVisibility(View.GONE);
            txtTime.setVisibility(View.GONE);
            enableRadioGroup(false);
            changeGreenColorRadioButton(questions[counter].getTrueAnswer()); //only set color radioButton true answer
        }

        btnPreviousQuestion.setOnClickListener(v -> {
            if (counter >= 1) {
                rgAnswer.clearCheck();
                txtCounter.setText(String.format("%s%s", (--counter < 9) ? "0" : "", counter + 1));
                prgCount.setProgress(counter + 1, true);
                radioButtonSetText(counter);
                if (mood == 1) {
                    if (answers[counter].answer != -1)
                        ((RadioButton) rgAnswer.getChildAt(answers[counter].answer)).setChecked(true);
                    if (isEndExam) //end time exam
                        changeColorRadioEndTimeExam();  // set color radioButton true answer and false answer
                } else
                    changeGreenColorRadioButton(questions[counter].getTrueAnswer()); //only set color radioButton true answer
            }
        });

        btnNetQuestion.setOnClickListener(v -> {
            if (counter < numberQuestionOfLevel - 1) {
                rgAnswer.clearCheck();
                txtCounter.setText(String.format("%s%s", (++counter < 9) ? "0" : "", counter + 1));
                prgCount.setProgress(counter + 1, true);
                radioButtonSetText(counter);
                if (mood == 1) {
                    if (answers[counter].answer != -1)
                        ((RadioButton) rgAnswer.getChildAt(answers[counter].answer)).setChecked(true);
                    if (isEndExam) //end time exam
                        changeColorRadioEndTimeExam();  // set color radioButton true answer and false answer
                } else
                    changeGreenColorRadioButton(questions[counter].getTrueAnswer()); //only set color radioButton true answer
            }
        });

        rgAnswer.setOnCheckedChangeListener((group, checkedId) -> {
            if (answers != null && checkedId != -1 && !isEndExam)
                answers[counter].answer = (byte) group.indexOfChild(findViewById(checkedId));
        });

        btnEndTest.setOnClickListener(v -> {
            if (isEndExam || mood == 2)
                finish();
            else {
                dialogEndTimeExam();
                data.setLevel(new Levels((byte) 0, bundle.getByte("levelCount"),
                                nineteen, wrongNumber, correctNumber, timeTookTest, bundle.getInt("termId"),
                                (Integer) utils.getSharedPreferences("userId", "0", Utils.NAME_SHARED_PREFERENCES)),
                        objects -> Log.i("status", (String) objects[0]));
            }
        });
    }

    private void setData() {
        numberQuestionOfLevel = 50; //get size questions from TestActivity
//        numberQuestionOfLevel = bundle.getByte("numberQuestionOfLevel");
        data.GetQuestions(0, 1, objects -> questions = (Questions[]) objects[0]);// get TermId and Leve from TestActivity
//        data.GetQuestions(bundle.getInt("termId"), bundle.getByte("levelCount"), objects -> questions = (Questions[]) objects[0]);
        txtnumberQuestionOfLevel.setText(String.format("%s/", numberQuestionOfLevel));
        prgCount.setMax(numberQuestionOfLevel);
        radioButtonSetText((byte) 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeGreenColorRadioButton(int index) {
        ((RadioButton) rgAnswer.getChildAt(index)).setChecked(true);
        rgAnswer.getChildAt(index).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.color_green)));
        ((RadioButton) rgAnswer.getChildAt(index)).setButtonDrawable(R.drawable.bg_ic_check);
        ((RadioButton) rgAnswer.getChildAt(index)).setButtonTintList(ColorStateList.valueOf(getColor(R.color.color_green)));
        ((RadioButton) rgAnswer.getChildAt(index)).setTextColor(ColorStateList.valueOf(getColor(R.color.color_green)));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeColorRadioEndTimeExam() {
        restColorRadioButton();
        if (answers[counter].answer == -1) {
            changeGreenColorRadioButton(questions[counter].getTrueAnswer() - 1);
        } else if (questions[counter].getTrueAnswer() - 1 == answers[counter].answer) {
            changeGreenColorRadioButton(answers[counter].answer);
        } else {
            rgAnswer.getChildAt(answers[counter].answer).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.color_red)));
            ((RadioButton) rgAnswer.getChildAt(answers[counter].answer)).setButtonTintList(ColorStateList.valueOf(getColor(R.color.color_red)));
            ((RadioButton) rgAnswer.getChildAt(answers[counter].answer)).setTextColor(ColorStateList.valueOf(getColor(R.color.color_red)));
            ((RadioButton) rgAnswer.getChildAt(answers[counter].answer)).setChecked(false);
            ((RadioButton) rgAnswer.getChildAt(answers[counter].answer)).setButtonDrawable(R.drawable.bg_ic_cancel);

            //true answer change color
            changeGreenColorRadioButton(questions[counter].getTrueAnswer() - 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void restColorRadioButton() {
        for (int i = 0; i < rgAnswer.getChildCount(); i++) {    //reset textColor and background and buttonTint radioButton
            rgAnswer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(getColor(android.R.color.darker_gray)));
            ((RadioButton) rgAnswer.getChildAt(i)).setButtonTintList(ColorStateList.valueOf(getColor(android.R.color.darker_gray)));
            ((RadioButton) rgAnswer.getChildAt(i)).setTextColor(getColor(R.color.color_gray_dark));
            ((RadioButton) rgAnswer.getChildAt(i)).setButtonDrawable(R.drawable.bg_ic_radio_button);
        }
    }

    private void enableRadioGroup(boolean isEnable) {
        for (int i = 0; i < rgAnswer.getChildCount(); i++)
            rgAnswer.getChildAt(i).setEnabled(isEnable);
    }

    private void radioButtonSetText(byte b) {
        txtTextQuestion.setText(questions[b].getQuestionText());
        ((RadioButton) rgAnswer.getChildAt(0)).setText(questions[b].getOption1());
        ((RadioButton) rgAnswer.getChildAt(1)).setText(questions[b].getOption2());
        ((RadioButton) rgAnswer.getChildAt(2)).setText(questions[b].getOption3());
        ((RadioButton) rgAnswer.getChildAt(3)).setText(questions[b].getOption4());
    }

    private void exam(int _time) {
        time = _time * 60;
        circularProgressBar.setProgressMax(time);
        final Handler handler = new Handler();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                handler.post(() -> {
                    if (time > 0) {
                        circularProgressBar.setProgress(--time);
                        txtTime.setText(String.format("%s%s:%s%s", (time / 60) <= 9 ? "0" : "", time / 60, (time % 60) <= 9 ? "0" : "", time % 60));
                        timeTookTest++;
                    } else if (time == 0) {
                        for (int i = 0; i < answers.length; i++) { //calculate the exam result
                            if (answers[i].answer == -1)
                                nineteen++;
                            else if (answers[i].answer == questions[i].getTrueAnswer() - 1)
                                correctNumber++;
                            else wrongNumber++;
                        }
                        dialogEndExam();
                        time--;
                    }
                });
            }
        }, 0, 500);

        answers = new answer[questions.length];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = new answer(questions[i].getQuestionId(), (byte) -1);
        }
    }

    private void dialogEndTimeExam() {
        new AlertDialog.Builder(QuestionActivity.this)
                .setTitle("خاتمه آزمون")
                .setMessage("آیا قصد خاتمه آزمون را دارید؟")
                .setPositiveButton("بله", (dialog, which) -> time = 1)
                .setNegativeButton("خیر", null)
                .setCancelable(false)
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void dialogEndExam() {
        new AlertDialog.Builder(QuestionActivity.this)
                .setTitle("اتمام آزمون")
                .setMessage("تعداد پاسخ صحیح: " + correctNumber + "\nتعداد پاسخ غلط: " + wrongNumber + "\nتعداد بی پاسخ: " + nineteen + "\nمدت زمان آزمون: "
                        + String.format("%s%s:%s%s", (timeTookTest / 60) <= 9 ? "0" : "", timeTookTest / 60, (timeTookTest % 60) <= 9 ? "0" : "", timeTookTest % 60))
                .setCancelable(false)
                .setPositiveButton("مشاهده جواب ها", (dialog, which) -> {
                    enableRadioGroup(false);
                    isEndExam = true;
                    btnEndTest.setText("");
                    changeColorRadioEndTimeExam();
                })
                .show();
    }

    private void setupView() {
        rgAnswer = findViewById(R.id.activity_question_rgAnswer);
        btnNetQuestion = findViewById(R.id.activity_question_btnNextQuestion);
        btnPreviousQuestion = findViewById(R.id.activity_question_btnPreviousQuestion);
        circularProgressBar = findViewById(R.id.activity_question_circularProgressbar);
        txtTime = findViewById(R.id.activity_question_txtTime);
        prgCount = findViewById(R.id.activity_question_prgCount);
        txtCounter = findViewById(R.id.activity_question_txtCounter);
        txtnumberQuestionOfLevel = findViewById(R.id.activity_question_txtNumberQuestionOfLevel);
        txtTextQuestion = findViewById(R.id.activity_question_txtTextQuestion);
        btnEndTest = findViewById(R.id.activity_question_btnEndTest);
        setupToolbar();
        data = new Data(getApplicationContext());
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.activity_question_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (isEndExam || mood == 2) {
            dialogExit();
        } else dialogEndTimeExam();
    }

    private void dialogExit() {
        new AlertDialog.Builder(QuestionActivity.this)
                .setTitle("ترک صفحه")
                .setMessage("آیا قصد ترک صفحه را دارید؟")
                .setPositiveButton("بلی", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("خیر", null)
                .show();
    }

    private class answer {
        private int questionId;
        private byte answer;

        public answer(int questionId, byte answer) {
            this.questionId = questionId;
            this.answer = answer;
        }
    }
}