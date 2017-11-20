package ca.jfmcode.mymangalibrary.System;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

/**
 * Created by ONi on 05/11/2017.
 */


public class Manga implements java.io.Serializable{
    //region private variables
    private int id;
    private String title; //x
    private String english; //x
    private String synonyms;
    private int chapters; //x
    private int volumes;
    private double score; //x
    private String type;
    private String status; //x
    private String start_date;
    private String end_date;
    private String synopsis; //x
    private String image; //x

    private String URL;
    private boolean unread = false;
    //endregion

    //region Manga constructor method

    public Manga(String id, String title, String english, String synonyms, String chapters, String volumes, String score, String type, String status, String start_date, String end_date, String synopsis, String image) {
        this.id = Integer.parseInt(id);
        this.title = title;
        this.english = english;
        this.synonyms = synonyms;
        this.chapters = Integer.parseInt(chapters);
        this.volumes = Integer.parseInt(volumes);
        this.score = Double.parseDouble(score);
        this.type = type;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.synopsis = synopsis;
        this.image = image;
        this.URL = "";
    }

    public Manga(int id, String title, String english, String synonyms, int chapters, int volumes, double score, String type, String status, String start_date, String end_date, String synopsis, String image, String URL) {
        this.id = id;
        this.title = title;
        this.english = english;
        this.synonyms = synonyms;
        this.chapters = chapters;
        this.volumes = volumes;
        this.score = score;
        this.type = type;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.synopsis = synopsis;
        this.image = image;
        this.URL = URL;
    }

    //endregion

    //region Getter methods

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEnglish() {
        return english;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public int getChapters() {
        return chapters;
    }

    public int getVolumes() {
        return volumes;
    }

    public double getScore() {
        return score;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImage() {
        return image;
    }

    public String getURL() {
        return URL;
    }

    public boolean isUnread(){
        return unread;
    }

    //endregion

    //region Setter methods

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manga manga = (Manga) o;

        return id == manga.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void hasNewChapter(Object o){
        if (o == null || getClass() != o.getClass()) return;

        Manga manga = (Manga) o;

        if (this.chapters < manga.chapters)
            unread=true;

        unread=false;
    }
}