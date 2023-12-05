/*
 * Rapper
 *
 * The "Rapper" class will contain the information of each individual that will participate in the competition
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

import edu.salleurl.profile.Profileable;

/**
 * The "Rapper" class will contain the information of each individual that will participate in the competition
 */
public class Rapper implements Profileable {
    private String realName;
    private String stageName;
    private String birth;
    private String nationality;
    private int level;
    private String photo;
    private int score = 0;
    private int rhymesDone = 0;

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRhymesDone() {
        return rhymesDone;
    }

    public void setRhymesDone(int rhymesDone) {
        this.rhymesDone = rhymesDone;
    }

    @Override
    public String getName() {
        return realName;
    }

    @Override
    public String getNickname() {
        return stageName;
    }

    @Override
    public String getBirthdate() {
        return birth;
    }

    @Override
    public String getPictureUrl() {
        return photo;
    }
}