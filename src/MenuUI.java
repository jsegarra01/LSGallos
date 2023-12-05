/*
 * MenuUI
 *
 * This "MenuUI" class will print in the screen all the operations the user will need to see. Will be the frontpage of our program
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */


//Import package java.util
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This "MenuUI" class will print in the screen all the operations the user will need to see. Will be the frontpage of our program
 */
public class MenuUI {

    /**
     * The main menu shown to the user.
     * @param competition The competition from which we take the values we show to the user.
     */
    public void mainMenu (ReadCompetition competition) {
        System.out.println("---------------------------------------------");
        System.out.println("Welcome to competition: " + competition.getCompetition().getName());
        System.out.println("\nStarts on "+ competition.getCompetition().getStartDate());
        System.out.println("Ends on: " + competition.getCompetition().getEndDate());
        System.out.println("Phases: " + competition.getCompetition().getPh().size());
        System.out.println("Currently: " + competition.getRappers().size() + " participants");
        System.out.println("---------------------------------------------");
    }

    /**
     * We show the attributes of the rapper registered by the user.
     * @param rapper The rapper registered by the user.
     */
    public void showRapper (Rapper rapper) {
        System.out.println("Name: " + rapper.getRealName());
        System.out.println("stageName: " + rapper.getStageName());
        System.out.println("birthDate: " + rapper.getBirth());
        System.out.println("Country: " + rapper.getNationality());
        System.out.println("Level: " + rapper.getLevel());
        System.out.println("Photo: " + rapper.getPhoto());
    }

    /**
     * Login menu we show to the user.
     */
    public void uiLogin () {
        System.out.println("\nCompetition has started. Do you want to:");
        System.out.println("\n1. Log in");
        System.out.println("2. Leave");
        System.out.println("\nChoose an option: ");
    }

    /**
     * Interface the user sees previous to entering its rhymes.
     * @param topic The theme which the battle is about.
     * @param rapper The rapper the user has logged in.
     */
    public void battleStart (Theme topic, Rapper rapper) {
        System.out.println("---------------------------------------------");
        System.out.println("\nTheme: " +  topic.getName());
        System.out.println("\nA coin is tossed in the air and... ");
        System.out.println(rapper.getStageName() + ", your turn! Drop it!\n");
    }

    /**
     * We tell the user its his turn and set the rhymes done depending on what the user introduces.
     * @param rapper The users rapper.
     */
    public void rhymeInput (Rapper rapper) {
        Logic logic = new Logic();

        System.out.println("\nYour turn!\n");
        rapper.setRhymesDone(logic.numberRhymesMyRapper() + rapper.getRhymesDone()); //Calculates the rhymes done.
        System.out.println("\n");
    }

    /**
     * We show the user the information of the lobby: current phase, score, type of battle... And we ask him to
     * choose an option.
     * @param myRapper Users rapper.
     * @param enemy Rivals rapper.
     * @param compet Current competition
     * @param battleType Type of battle (acapella, blood or written).
     * @return The option the user chose.
     */
    public int lobbyInformation(Rapper myRapper, Rapper enemy, Competition compet, int battleType) {
        Scanner sc = new Scanner(System.in);
        int option = 0;

        System.out.println("\n-------------------------------------------------------------------");
        if (battleType == 4) {
            System.out.print("Phase: " + (compet.getCurrentphase() - 1) + "/" + compet.getPh().size() + " | ");
            System.out.print("Score: " + myRapper.getScore());
        }
        else {
            System.out.print("Phase: " + compet.getCurrentphase() + "/" + compet.getPh().size() + " | ");
            System.out.print("Score: " + myRapper.getScore());
        }
        if (compet.getExit() == 1) {
            System.out.print(" | You have lost, kid. I'm sure you'll do better next time");
        }
        else {
            System.out.print(" | " + "Battle " + compet.getCurrentbattle() + "/2: ");
            switch (battleType) { //Shows the type of battle.
                case 0:
                    System.out.print("acapella");
                    break;
                case 1:
                    System.out.print("blood");
                    break;
                case 2:
                    System.out.print("written");
                    break;
            }

            System.out.print(" | Rival: " + enemy.getStageName());
        }
        System.out.print("\n-------------------------------------------------------------------\n");

        do {
            if (compet.getExit() == 0) {
                System.out.println("\n1. Start the battle");
            }
            else {
                System.out.println("\n1. Start the battle (Deactivated)");
            }
            System.out.println("2. Show ranking");
            System.out.println("3. Create profile");
            System.out.println("4. Leave competition");
            System.out.println("\nChoose an option:");

            option = Logic.atoi(sc.nextLine()); //Scans the option chosen by the user.
        } while (option < 1 || option > 4);

        return option;
    }

    /**
     * Shows the ranking of the rappers and their scores.
     * @param rappers List of rappers.
     */
    public void showRanking (LinkedList<Rapper> rappers) {
        int i = 0;
        Phase phase = new Phase();

        LinkedList<Rapper> notSorted = (LinkedList<Rapper>) rappers.clone(); //Clones the list of rappers.
        phase.sortScore(notSorted); //Sorts the list depending on the scores of the rappers.

        for (Rapper rapper: rappers) { //Prints the rappers ranking with their respective scores.
            if (notSorted.get(i).getStageName().equals(rappers.getLast().getStageName())) {
                System.out.println((i+1) + " " + notSorted.get(i).getStageName() + " - " + notSorted.get(i).getScore() + " <-- You");
            }
            else {
                System.out.println((i+1) + " " + notSorted.get(i).getStageName() + " - " + notSorted.get(i).getScore());
            }
            i++;
        }
    }

    /**
     * Sorts the rappers by its score and shows the winner of the competition and a trophy.
     * @param rappers List of rappers.
     */
    public void showWinner (LinkedList<Rapper> rappers) {
        Phase phase = new Phase();

        phase.sortScore(rappers); //Sorts the list of rappers depending on their scores.
        System.out.println("\nThe winner is: " + rappers.get(0).getStageName());
        System.out.println("    ___________");
        System.out.println("   '._==_==_=_.'");
        System.out.println("   .-\\:      /-.");
        System.out.println("  | (|:.     |) |");
        System.out.println("   '-|:.     |-'");
        System.out.println("     \\::.    /");
        System.out.println("      '::. .'");
        System.out.println("        ) (");
        System.out.println("      _.' '._");
        System.out.println("     `\"\"\"\"\"\"\"`");
    }

    /**
     * Shows whether if the users rapper has won or not.
     * @param rappers List of rappers.
     * @param phases The phase we are in.
     */
    public void showLoser (LinkedList<Rapper> rappers, int phases) {
        System.out.println("\n\n\n--------------------------------------------------------------------------------");
        if (phases != 4) {
            System.out.println("Well done finishing the finals brat, let's see who won... he he he\n");
            if (rappers.getLast().getScore() > rappers.get(0).getScore()) {
                //if our rappers score is bigger than the best score (the first one in the sorted array) ...
                System.out.println("I can't believe it... this newbie has won!");
            }
            else {
                System.out.println("You have lost... I did not expect anything else");
            }
        }
        else {
            System.out.println("You have lost... I did not expect anything else");
        }
    }
}
