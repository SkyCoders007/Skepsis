package com.mxicoders.skepci.model;

/**
 * Created by mxicoders on 14/7/17.
 */

public class QuestionariesClickData {

    public String name;
    public String email;
    public String que_id;
    public String emotion;
    public int omg;

    public String getSemotion() {
        return emotion;
    }

    public void setSemotion(String situation) {
        this.emotion = situation;
    }



    public String getQue_id() {
        return que_id;
    }

    public void setQue_id(String que_id) {
        this.que_id = que_id;
    }


    public QuestionariesClickData(String name, String email, int omg) {
        this.name = name;
        this.email = email;
        this.omg = omg;
    }

    public QuestionariesClickData() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOmg() {
        return omg;
    }

    public void setOmg(int omg) {
        this.omg = omg;
    }



}
