package com.example.softball_scout;

public class Record {


    private Integer id;
    private String name;
    private String position;
    private String duration;
    private String action;
    private Integer strike;
    private  Integer ball;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getStrike() {
        return strike;
    }

    public void setStrike(Integer strike) {
        this.strike = strike;
    }

    public Integer getBall() {
        return ball;
    }

    public void setBall(Integer ball) {
        this.ball = ball;
    }

    public String getState()
    {
        return (ball + "--" + strike);
    }


    public Record(Integer id, String name, String position, String duration, String action, Integer strike, Integer ball) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.duration = duration;
        this.action = action;
        this.strike = strike;
        this.ball = ball;
    }

    public Record(String name, String position, String duration, String action,Integer strike, Integer ball) {
        this.name = name;
        this.position = position;
        this.duration = duration;
        this.action = action;
        this.strike = strike;
        this.ball = ball;
    }

}
