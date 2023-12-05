/*
 * Rhyme
 *
 * The "Rhyme" class is the one that stores the different rhymes for each theme and gets the rhyme the rappers will say.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */


/*
 * Import package com.google.gson
 * Import package java.util
 */

import com.google.gson.annotations.SerializedName;
import java.util.LinkedList;

/**
 * The "Rhyme" class is the one that stores the different rhymes for each theme and gets the rhyme the rappers will say.
 */
public class Rhyme {

    @SerializedName("1")
    private LinkedList<String> one;
    @SerializedName("2")
    private LinkedList<String> two;
    @SerializedName("3")
    private LinkedList<String> three;

    public Rhyme() {
    }

    public LinkedList<String> getOne() {
        return one;
    }

    public void setOne(LinkedList<String> one) {
        this.one = one;
    }

    public LinkedList<String> getTwo() {
        return two;
    }

    public void setTwo(LinkedList<String> two) {
        this.two = two;
    }

    public LinkedList<String> getThree() {
        return three;
    }

    public void setThree(LinkedList<String> three) {
        this.three = three;
    }

    /**
     * Gets a rhyme depending on the level and the round.
     * @param rhyme the rhyme from we must take the things the rapper will say.
     * @param level the level of the rapper.
     * @param round the round of the battle we are in.
     * @param main 1 if is our rapper, 0 if it is not.
     * @return returns the rhyme the rapper will say.
     */
    public String getLevelRhyme(Rhyme rhyme, int level, int round, int main) {
        String string = "";

        if (level == 1) {
            try {
                string = rhyme.one.get(round); //Gets a level one rhyme.
            } catch (Exception e) {
                string = "Ha ha ha! The rapper did not say anything, making a fool of himself!\n";
            }
        }
        else if (level == 2) {
            try {
                string = rhyme.two.get(round); //Gets a level 2 rhyme.
            }catch (Exception e) {
                string = "Ha ha ha! The rapper did not say anything, making a fool of himself!\n";
            }
        }
        else if (level == 3) {
            try {
                string = rhyme.three.get(round); //Gets a level 2 rhyme.
            }catch (Exception e) {
                string = "Ha ha ha! The rapper did not say anything, making a fool of himself!\n";
            }
        }
        else {
            string = "Ha ha ha! The rapper did not say anything, making a fool of himself!\n";
        }

        if (main != 0) {
            System.out.println(string); //Shows the rhyme.
        }

        return string; //Returns the rhyme.
    }

}