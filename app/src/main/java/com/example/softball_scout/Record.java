package com.example.softball_scout;

public class Record {


    private Integer id;
    private String name;
    private String position;
    private String duration;
    private String action;
    private Integer strike;
    private  Integer ball;
    private String baseSituation;
    private String pitcherName;
    private String pitcherSide;
    private String hitterName;
    private String hitterSide;




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


    public String getBaseSituation() {
        return baseSituation;
    }

    public void setBaseSituation(String baseSituation) {
        this.baseSituation = baseSituation;
    }

    public String getPitcherName() {
        return pitcherName;
    }

    public void setPitcherName(String pitcherName) {
        this.pitcherName = pitcherName;
    }

    public String getPitcherSide() {
        return pitcherSide;
    }

    public void setPitcherSide(String pitcherSide) {
        this.pitcherSide = pitcherSide;
    }

    public String getHitterName() {
        return hitterName;
    }

    public void setHitterName(String hitterName) {
        this.hitterName = hitterName;
    }

    public String getHitterSide() {
        return hitterSide;
    }

    public void setHitterSide(String hitterSide) {
        this.hitterSide = hitterSide;
    }

    public Record(Integer id, String name, String position, String duration, String action, Integer strike, Integer ball, String baseSituation, String pitcherName, String pitcherSide, String hitterName, String hitterSide) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.duration = duration;
        this.action = action;
        this.strike = strike;
        this.ball = ball;
        this.baseSituation = baseSituation;
        this.pitcherName = pitcherName;
        this.pitcherSide = pitcherSide;
        this.hitterName = hitterName;
        this.hitterSide = hitterSide;
    }

    public Record(String name, String position, String duration, String action, Integer strike, Integer ball, String baseSituation, String pitcherName, String pitcherSide, String hitterName, String hitterSide) {
        this.name = name;
        this.position = position;
        this.duration = duration;
        this.action = action;
        this.strike = strike;
        this.ball = ball;
        this.baseSituation = baseSituation;
        this.pitcherName = pitcherName;
        this.pitcherSide = pitcherSide;
        this.hitterName = hitterName;
        this.hitterSide = hitterSide;
    }
}
