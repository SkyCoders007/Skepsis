package com.mxicoders.skepci.model;

/**
 * Created by mxicoders on 1/8/17.
 */

public class QuestionnariPyschologistData {

    public String name;
    public String id;
    public String count_question;
    public String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCount_question() {
        return count_question;
    }

    public void setCount_question(String count_question) {
        this.count_question = count_question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
