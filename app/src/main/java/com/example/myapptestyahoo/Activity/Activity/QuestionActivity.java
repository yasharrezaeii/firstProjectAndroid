package com.example.myapptestyahoo.Activity.Activity;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.myapptestyahoo.Activity.model.Questions;
import com.example.myapptestyahoo.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class QuestionActivity extends AppCompatActivity {
    private RadioGroup rgAnswer;
    private Questions[] questions;
    private Button btnPreviousQuestion, btnNetQuestion, btnEndTest;
    private TextView txtTime, txtCountQuestion, txtCounter, txtTextQuestion;
    private CircularProgressBar circularProgressBar;
    private int time, timeTookTest;
    private answer[] answers;
    private ProgressBar prgCount;
    private byte countQuestion, counter = 0, mood = 2, nineTeen, wrongNumber, correctNumber;
    private boolean isEndExam = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setupView();
        setData();

        if (mood == 1) {
            exam((byte) 10); // get time question

        } else if (mood == 2) {
            btnEndTest.setText("");
            circularProgressBar.setVisibility(View.GONE);
            txtTime.setVisibility(View.GONE);
            enableRadioGroup(false);
            changeGreenColorRadioButton(questions[counter].getTrueAnswer()); //only set color radioButton true answer
        }

        btnPreviousQuestion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
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
            }
        });

        btnNetQuestion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (counter < countQuestion - 1) {
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
            }
        });

        rgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (answers != null && checkedId != -1 && !isEndExam)
                    answers[counter].answer = (byte) group.indexOfChild(findViewById(checkedId));
            }
        });

        btnEndTest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (isEndExam || mood == 2)
                    finish();
                else
                    dialogEndTimeExam();
            }
        });
    }

    private void setData() {
        countQuestion = 50; //get size questions
        questions = setDataQuestion();
        txtCountQuestion.setText(String.format("%s/", countQuestion));
        prgCount.setMax(countQuestion);
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

    private void exam(byte _time) {
        time = _time * 60;
        circularProgressBar.setProgressMax(time);
        final Handler handler = new Handler();

        final Timer timer = new Timer();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time > 0) {
                            circularProgressBar.setProgress(--time);
                            txtTime.setText(String.format("%s%s:%s%s", (time / 60) <= 9 ? "0" : "", time / 60, (time % 60) <= 9 ? "0" : "", time % 60));
                            timeTookTest++;
                        } else if (time == 0) {
                            for (int i = 0; i < answers.length; i++) { //calculate the exam result
                                if (answers[i].answer == -1)
                                    nineTeen++;
                                else if (answers[i].answer == questions[i].getTrueAnswer() - 1)
                                    correctNumber++;
                                else wrongNumber++;
                            }
                            dialogEndExam();
                            time--;
                        }
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
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        time = 1;
                    }
                })
                .setNegativeButton("خیر", null)
                .setCancelable(false)
                .show();
    }

    private void dialogEndExam() {
        new AlertDialog.Builder(QuestionActivity.this)
                .setTitle("اتمام آزمون")
                .setMessage("تعداد پاسخ صحیح: " + correctNumber + "\nتعداد پاسخ غلط: " + wrongNumber + "\nتعداد بی پاسخ: " + nineTeen + "\nمدت زمان آزمون: "
                        + String.format("%s%s:%s%s", (timeTookTest / 60) <= 9 ? "0" : "", timeTookTest / 60, (timeTookTest % 60) <= 9 ? "0" : "", timeTookTest % 60))
                .setCancelable(false)
                .setPositiveButton("مشاهده جواب ها", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enableRadioGroup(false);
                        isEndExam = true;
                        btnEndTest.setText("");
                        changeColorRadioEndTimeExam();
                    }
                })
                .show();
    }

    private Questions[] setDataQuestion() {
        Questions[] questions = new Questions[countQuestion];
        questions[0] = new Questions(0, "سوال یک", "الف", "ب", "پ", "چ", (byte) 3, true, 0);
        questions[1] = new Questions(1, "سوال دو", "جواب1", "جواب2", "جواب3", "جواب4", (byte) 1, true, 0);
        questions[2] = new Questions(2, "سوال سه", "شسیب", "شبیسب", "زرذز", "ابلای", (byte) 4, true, 0);
        questions[3] = new Questions(3, "سوال چهار", "A", "B", "C", "D", (byte) 2, true, 0);
        questions[4] = new Questions(4, "سوال پنجم", "جواب1", "جواب2", "جواب3", "جواب4", (byte) 4, true, 0);
        questions[5] = new Questions(5, "سوال ششم", "بمنشسیبکمن", "بهشتسیمبنتشمینب", "ئرذئمذنررئمذن", "شسیب", (byte) 2, true, 0);
        questions[6] = new Questions(6, "سوال هفتم", "AAAA", "BBBBBBB", "CCCCCCCCCC", "DDDDDDDDDDDD", (byte) 1, true, 0);
        questions[7] = new Questions(7, "سوال هشتم", "گزینه اول ", "بشیتمبن شمسینبمشسیب سشیب", "هتهتحهتحهتهح", "مبنلمنسیمبنلسیمبنلتیمبنتمن", (byte) 1, true, 0);
        questions[8] = new Questions(8, "سوال نهم", "جواب1", "جواب2", "جواب3", "جواب4", (byte) 2, false, 0);
        questions[9] = new Questions(9, "دهم", "یک", "دو", "سه", "چهار", (byte) 3, false, 0);
        for (int i = 10; i < countQuestion; i++) {
            questions[i] = new Questions(i, "title", "1", "2", "3", "4", (byte) 2, true, 0);
        }
        return questions;
    }

    private void setupView() {
        rgAnswer = findViewById(R.id.activity_question_rgAnswer);
        btnNetQuestion = findViewById(R.id.activity_question_btnNextQuestion);
        btnPreviousQuestion = findViewById(R.id.activity_question_btnPreviousQuestion);
        circularProgressBar = findViewById(R.id.activity_question_circularProgressbar);
        txtTime = findViewById(R.id.activity_question_txtTime);
        prgCount = findViewById(R.id.activity_question_prgCount);
        txtCounter = findViewById(R.id.activity_question_txtCounter);
        txtCountQuestion = findViewById(R.id.activity_question_txtCounQuestion);
        txtTextQuestion = findViewById(R.id.activity_question_txtTextQuestion);
        btnEndTest = findViewById(R.id.activity_question_btnEndTest);
        setupToolbar();
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