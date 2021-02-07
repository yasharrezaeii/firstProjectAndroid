package com.example.azmon;

import android.content.Context;

import com.example.azmon.Model.Levels;
import com.example.azmon.Model.Questions;

public class Data {
    private Context context;

    public Data(Context context) {
        this.context = context;
    }

    public void getQuestions(int termId, int level, OnResult onResult) {
        Questions[] questions = new Questions[50];
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
        for (int i = 10; i < 50; i++)
            questions[i] = new Questions(i, "title", "1", "2", "3", "4", (byte) 2, true, 0);

        onResult.success((Object) questions);
    }

    public void setLevel(Levels level, OnResult onResult) {
        onResult.success();
    }
}
