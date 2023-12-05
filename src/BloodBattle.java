/*
 * BloodBattle
 *
 * The "BloodBattle" class will overwrite the Battle class with the functions of an Blood one.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 9/12/2020
 */

/**
 * The "BloodBattle" class will overwrite the Battle class with the functions of an Blood one.
 */
public class BloodBattle extends Battle {
    /**
     * Sets the rappers score depending on its rhymes.
     * @param rapper1 The rapper we want to set the score to.
     */
    @Override
    public void setRappersScore(Rapper rapper1) {
        rapper1.setScore((int) ((Math.PI * Math.pow( rapper1.getRhymesDone(), 2))/4) + rapper1.getScore()); //Sets the rappers score depending on its rhymes.
    }
}
