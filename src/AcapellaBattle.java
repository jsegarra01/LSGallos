/*
 * AcapellaBattle
 *
 * The "AcapellaBattle" class will overwrite the Battle class with the functions of an Acapella one.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 9/12/2020
 */

/**
 * The "AcapellaBattle" class will overwrite the Battle class with the functions of an Acapella one.
 */
public class AcapellaBattle extends Battle {
    /**
     * Sets the rappers score depending on its rhymes.
     * @param rapper1 The rapper we want to set the score to.
     */
    @Override
    public void setRappersScore(Rapper rapper1) {
        rapper1.setScore((int) (rapper1.getScore() + (6*Math.sqrt(rapper1.getRhymesDone()) + 3)/2)); //Sets the rappers score depending on its rhymes.
    }
}
