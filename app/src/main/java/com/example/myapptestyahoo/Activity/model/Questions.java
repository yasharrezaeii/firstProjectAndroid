package com.example.myapptestyahoo.Activity.model;

public class Questions {

    private int questionId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private byte trueAnswer;
    private boolean isActive;
    private int termId;

    public Questions() {
    }

    public Questions(int questionId, String questionText, String option1, String option2, String option3, String option4, byte trueAnswer, boolean isActive, int termId) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.trueAnswer = trueAnswer;
        this.isActive = isActive;
        this.termId = termId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public byte getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(byte trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
