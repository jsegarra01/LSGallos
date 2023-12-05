/*
 * ReadBattles
 *
 * The "Read Battles" class has the structure of the battles Json file and has some functionalities about the themes of
 * the battles.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

// Import package java.util
import java.util.ArrayList;
import java.util.Random;

/**
 * The "Read Battles" class has the structure of the battles Json file and has some functionalities about the themes of the battles.
 */
public class ReadBattles {
    private ArrayList<Theme> themes;

    public ReadBattles() {
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }

    /**
     * Gets a random theme between all the themes of the competition.
     * @param themes The ArrayList of themes of the competition.
     * @return The theme that has been selected randomly.
     */
    public Theme randomTheme(ArrayList<Theme> themes) {
        Random rand = new Random();
        return themes.get(rand.nextInt(themes.size())); //Gets a random theme between all the themes of the competition.
    }
}