package com.learn.growthsystemdemo.bean;

/**
 * Created by fucc
 * Date: 2019-12-06 16:43
 */
public class DataBean {
    private String lessonName;
    private int own;
    private int average;

    public DataBean(String lessonName, int own, int average) {
        this.lessonName = lessonName;
        this.own = own;
        this.average = average;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getOwn() {
        return own;
    }

    public void setOwn(int own) {
        this.own = own;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }
}
