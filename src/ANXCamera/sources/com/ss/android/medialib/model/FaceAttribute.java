package com.ss.android.medialib.model;

import androidx.annotation.Keep;

@Keep
public class FaceAttribute {
    public static final int EXPRESSION_NUM = 7;
    public static final int RACIAL_NUM = 4;
    float age;
    float attractive;
    float boyProb;
    float[] expProbs;
    int expType;
    float happyScore;
    float[] racialProbs;
    int racialType;

    public interface ExpressionType {
        public static final int ANGRY = 0;
        public static final int DISGUST = 1;
        public static final int FEAR = 2;
        public static final int HAPPY = 3;
        public static final int NEUTRAL = 6;
        public static final int SAD = 4;
        public static final int SURPRISE = 5;
    }

    public interface RacialType {
        public static final int BLAC = 3;
        public static final int INDIAN = 2;
        public static final int WHITE = 0;
        public static final int YELLOW = 1;
    }

    public float getAge() {
        return this.age;
    }

    public float getAttractive() {
        return this.attractive;
    }

    public float getBoyProb() {
        return this.boyProb;
    }

    public float[] getExpProbs() {
        return this.expProbs;
    }

    public int getExpType() {
        return this.expType;
    }

    public float getHappyScore() {
        return this.happyScore;
    }

    public int getRacialType() {
        return this.racialType;
    }

    public float[] getRacialrobs() {
        return this.racialProbs;
    }

    public void setAge(float f) {
        this.age = f;
    }

    public void setAttractive(float f) {
        this.attractive = f;
    }

    public void setBoyProb(float f) {
        this.boyProb = f;
    }

    public void setExpProbs(float[] fArr) {
        this.expProbs = fArr;
    }

    public void setExpType(int i) {
        this.expType = i;
    }

    public void setHappyScore(float f) {
        this.happyScore = f;
    }

    public void setRacialType(int i) {
        this.racialType = i;
    }

    public void setRacialrobs(float[] fArr) {
        this.racialProbs = fArr;
    }
}
