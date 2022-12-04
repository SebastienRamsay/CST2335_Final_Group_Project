package com.example.cst2335finalgroupproject;

public class SoccerObject {
    private String title;
    private String side1;
    private String side2;
    private String competition;

    public SoccerObject(String title, String side1, String side2, String competition){
        setTitle(title);
        setside1(side1);
        setside2(side2);
        setCompetition(competition);
    }

    public SoccerObject(String key, String string, String side2) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSide1() {
        return side1;
    }

    public void setside1(String side1) {
        this.side1 = side1;
    }

    public String getSide2() {
        return side2;
    }

    public void setside2(String side2) {
        this.side2 = side2;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }
}
