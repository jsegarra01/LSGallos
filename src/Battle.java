/*
 * Battle
 *
 * The "Battle" class will generate a battle and all the necessary functions in order to be able to complete it.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

//Import package java.util
import java.util.Random;

/**
 * The "Battle" class will generate a battle and all the necessary functions in order to be able to complete it.
 */
public class Battle {
    private Rapper rapper1;
    private Rapper rapper2;

    public void setRapper1(Rapper rapper1) {
        this.rapper1 = rapper1;
    }

    public void setRapper2(Rapper rapper2) {
        this.rapper2 = rapper2;
    }

    public Rapper getRapper1() {
        return rapper1;
    }

    public Rapper getRapper2() {
        return rapper2;
    }

    /**
     * Sets the order of the rappers in the battle.
     * @param Frapper First rapper of the battle.
     * @param Srapper Second rapper of the battle.
     * @param mine 2 if it is our rapper in position 1, 1 if it is in position 2, 0 if it is not.
     * @return we will return which rapper is ours with the flag mine
     */
    public int setRandomRappers (Rapper Frapper, Rapper Srapper, int mine) {
        Random rand = new Random();
        int selector = rand.nextInt(2); //Randomly selects between 0 or 1.

        if (selector == 0) {
            setRapper1(Frapper); // First rapper first.
            setRapper2(Srapper); // Second rapper second.
            if (mine == 1) {
                return 2;
            }
        }
        else {
            setRapper1(Srapper); // Second rapper first.
            setRapper2(Frapper); // First rapper second.
        }
        return mine;
    }

    /**
     * Function that will be overriden by the inheriting battle classes. Used to calculate the rappers score.
     * @param rapper1 The rapper whose score will be calculated.
     */
    public void setRappersScore (Rapper rapper1) {
        //It is empty because we are overriding it.
    }

    /**
     * Creates the battle, gets the different rappers of it, and sets its score.
     * @param Frapper First rapper of the battle.
     * @param Srapper Second rapper of the battle
     * @param mine 1 if it is our rapper, 0 if it is not.
     */
    public void createBattle(Rapper Frapper, Rapper Srapper, int mine) {
        Logic logic = new Logic();
        MenuUI ui = new MenuUI();

        logic.readJSONbat();
        ReadBattles battles = (logic.getReadBatalles());

        Theme theme = battles.randomTheme(battles.getThemes());
        mine = setRandomRappers(Frapper, Srapper, mine); //We randomize who will start the battle

        if (mine != 0) {ui.battleStart(theme, rapper1);}
        theme.obtainRhymes(rapper1, rapper2, theme.getRhymes().get(0), mine); //Obtaining the rhymes

        setRappersScore(rapper1); //Setting the scores
        setRappersScore(rapper2);

        if (rapper1.getRealName().equals(Frapper.getRealName())) { //Setting each rapper with its corresponding values.
            Frapper = rapper1;
            Srapper = rapper2;
        }
        else {
            Frapper = rapper2;
            Srapper = rapper1;
        }
    }

    /**
     * Depending on which battle it is, creates its respective battle.
     * @param Frapper First rapper of the battle.
     * @param Srapper Second rapper of the battle.
     * @param mine 1 if it is our rapper, 0 if it is not.
     * @param battletype 0 if acapella battle, 1 if blood battle, 2 if written battle.
     */
    public void whichBattle(Rapper Frapper, Rapper Srapper, int mine, int battletype) {
        switch (battletype) { //Depending on the type of battle.
            case 0:
                AcapellaBattle acab = new AcapellaBattle();
                acab.createBattle(Frapper, Srapper, mine); //Creating acapella battle.
                break;
            case 1:
                BloodBattle blood = new BloodBattle();
                blood.createBattle(Frapper, Srapper, mine); //Creating blood battle.
                break;
            case 2:
                WrittenBattle written = new WrittenBattle();
                written.createBattle(Frapper, Srapper, mine); //Creating written battle.
                break;
        }
    }
}
