package com.example.assassinapp;

public class Player {
    private String name;
    private String status;
    private int score;
    private int uid;
    private Player target;

    public Player(){
        name = new String();
        status = "alive";
        score = 0;
        uid = 0;
        target = new Player();
    }

    public void setName(String s){
        name = s;
    }
    public String getName(){
        return name;
    }

    public void setStatus(String s){
        status = s;
    }
    public String getStatus(){
        return status;
    }

    public void setScore(int i){
        score = i;
    }
    public int getScore(){
        return score;
    }
    public void addScore(int i){
        score+=i;
    }

    public void setUid(int i){
        uid = i;
    }
    public int getUid(){
        return uid;
    }

    public void setTarget(Player p){
        target = p;
    }
    public Player getTarget(){
        return target;
    }
}
