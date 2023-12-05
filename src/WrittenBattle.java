/*
 * WrittenBattle
 *
 * The "WrittenBattle" class will overwrite the Battle class with the functions of an Written one.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 9/12/2020
 */

/**
 * The "WrittenBattle" class will overwrite the Battle class with the functions of an Written one.
 */
public class WrittenBattle extends Battle {
    /**
     * Sets the rappers score depending on its rhymes.
     * @param rapper1 The rapper we want to set the score to.
     */
    @Override
    public void setRappersScore(Rapper rapper1) {
        rapper1.setScore(rapper1.getScore() + rapper1.getRhymesDone()*3 + 1); //Sets the rappers score depending on its rhymes.
    }
}


