/*
 * Theme
 *
 * The "Theme" class contains the different attributes of the themes, as well as a functionality to select rhymes.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

// Import package java.util
import java.util.LinkedList;

/**
 * The "Theme" class contains the different attributes of the themes, as well as a functionality to select rhymes.
 */
public class Theme {
    private String name;
    private LinkedList<Rhyme> rhymes;

    public Theme() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Rhyme> getRhymes() {
        return rhymes;
    }

    public void setRhymes(LinkedList<Rhyme> rhymes) {
        this.rhymes = rhymes;
    }

    /**
     * Obtain the number of Rhymes each rapper has done during the battle
     * @param rapper1 first rapper of the battle
     * @param rapper2 second rapper of the battle
     * @param rhyme which rhyme is it
     * @param myRapper flag to know if it is my rapper or not
     */
    public void obtainRhymes(Rapper rapper1, Rapper rapper2, Rhyme rhyme, int myRapper) {
        MenuUI menu = new MenuUI();
        String string = "";
        Logic logic = new Logic ();
        int rhymes = 1;

        rapper1.setRhymesDone(0);
        rapper2.setRhymesDone(0);

        for (int round = 0; round < 2; round++) {
            if (myRapper == 1) {
                menu.rhymeInput(rapper1);
            }
            else {
                string = rhyme.getLevelRhyme(rhyme, rapper1.getLevel(), round, myRapper);
                if (!string.equals("Ha ha ha! The rapper did not say anything, making a fool of himself!\n")) {
                    rapper1.setRhymesDone(logic.numberRhymes(rhymes, string) + rapper1.getRhymesDone());
                }
            }

            if (myRapper == 2) {
                menu.rhymeInput(rapper2);
            }
            else {
                string = rhyme.getLevelRhyme(rhyme, rapper2.getLevel(), round, myRapper);
                if (!string.equals("Ha ha ha! The rapper did not say anything, making a fool of himself!\n")) {
                    rapper2.setRhymesDone(logic.numberRhymes(rhymes, string) + rapper2.getRhymesDone());
                }
            }
        }
    }
}
