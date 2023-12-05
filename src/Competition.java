/*
 * Competition
 *
 * The "Competition" class will run all the competition, showing the different options such as the lobby ones.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

//Import package java.util
import java.util.LinkedList;

/**
 * The "Competition" class will run all the competition, showing the different options such as the lobby ones.
 */
public class Competition {
    private String name;
    private String startDate;
    private String endDate;
    private LinkedList<Phase> phases;
    private LinkedList<Country> countries;
    private int currentphase = 1;
    private int currentbattle = 1;
    private int exit = 0;

    public Competition() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public LinkedList<Phase> getPh() {
        return phases;
    }

    public void setPh(LinkedList<Phase> ph) {
        this.phases = ph;
    }

    public void setCurrentphase(int currentphase) {
        this.currentphase = currentphase;
    }

    public int getCurrentphase() {
        return currentphase;
    }

    public int getCurrentbattle() {
        return currentbattle;
    }

    public void setCurrentbattle(int currentbattle) {
        this.currentbattle = currentbattle;
    }

    public LinkedList<Country> getCountries() {
        return countries;
    }

    public void setCountries(LinkedList<Country> countries) {
        this.countries = countries;
    }



    /**
     * Depending on the option introduced by the user, calls its corresponding functionalities.
     * @param myrapper Flag to know whether if it is our rapper or not.
     * @param compet The competition with its information.
     * @param rapper1 Rapper to which we will run the function.
     * @param rappers List of all the rappers.
     * @param battle_type The type of battle (Acapella, Blood, Written).
     */
    public void lobby(Rapper myrapper, Competition compet, Rapper rapper1, LinkedList<Rapper> rappers, int battle_type){
        MenuUI menu = new MenuUI();
        Battle battle = new Battle();
        Logic logic = new Logic();

        int option;
        do {
            option = menu.lobbyInformation(myrapper, rapper1, compet, battle_type);//Prints the menu and gets the option

            switch (option) {
                case 1:
                    if (compet.exit != 1) {
                        battle.whichBattle(rapper1, myrapper, 1, battle_type);
                        compet.setCurrentbattle(compet.getCurrentbattle() + 1); //Starts the battle
                    }
                    else {
                        System.out.println("Competition Ended. You cannot battle anyone else!");
                    }
                    break;
                case 2:
                    menu.showRanking(rappers); //Shows the ranking
                    break;
                case 3:
                    logic.askrapper(rappers);
                    break;
                case 4:
                    compet.setExit(compet.getExit() + 1);
                    myrapper.setLevel(0);
                    if (compet.getExit() == 1) {
                        battle.whichBattle(rapper1, myrapper, 0, battle_type);
                        lobby(myrapper, compet, rapper1, rappers, battle_type);
                        compet.setCurrentbattle(compet.getCurrentbattle() + 1);
                    }
                    compet.setExit(1);
                    myrapper.setScore(0); //Simulates the competition, shows the winner and leaves the competition.
                    break;
            }
        } while (option == 2 || option == 3 || (option == 1 && compet.exit == 1));
    }

    public int getExit() {
        return exit;
    }

    public void setExit(int exit) {
        this.exit = exit;
    }
}
