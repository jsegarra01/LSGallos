/*
 * Controller
 *
 * The "Controller" class is the responsible to make the program work, being the organitzator of it.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

/*
 * Import package java.util
 * Import package java.text
 */

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The "Controller" class is the responsible to make the program work, being the organitzator of it.
 */
public class Controller {
    /**
     * Depending on the date we are currently, the start date and the end date of the competition, selects which
     * menu show to the user.
     * @param competition The competition from which we will take the dates to determine which menu to select.
     * @return depending on which menu we will show to the user, returns 1, 2, or 3.
     */
    public int selectMenu (ReadCompetition competition) {

        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);

        Date today = java.util.Calendar.getInstance().getTime(); //Gets todays date.
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdfrmt.parse(competition.getCompetition().getStartDate()); //Gets the start date.
            endDate = sdfrmt.parse(competition.getCompetition().getEndDate()); //Gets the end date.
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (today.before(startDate)) return 1; //Depending on our current date decides which menu we show to the user.
        else if (today.after(endDate)) return 3;
        else return 2;
    }

    /**
     * The menu to register a new rapper to the competition and introduce its attributes.
     * @param logic logic class in order to be able to use the information stored in logic.
     */
    public void register (Logic logic) {
        int option = 0;

        while (option != 2) {
        System.out.println("\nCompetition hasn't started yet. Do you want to:");
        System.out.println("\n1. Register");
        System.out.println("2. Leave");
        System.out.println("\nChoose an option: ");
        Scanner sc = new Scanner(System.in);

        option = Logic.atoi(sc.nextLine());
            if (option == 1) {
                Rapper rapper = new Rapper();
                MenuUI ui = new MenuUI();

                System.out.println("-------------------------------------------------");
                System.out.println("Please, enter your personal information:");
                do {
                    System.out.println("- Full name: ");
                    String name = sc.nextLine();
                    if (name.equals("")) {
                        System.out.println("Error, please enter a name\n");
                    }
                    else {
                        option = 0;
                        rapper.setRealName(name);
                    }
                } while (option != 0);

                logic.checkStageName(rapper); //Checking the introduced Stage name.
                logic.checkBirthDate(rapper); //Checking the introduced birth date.
                logic.checkCountry(rapper); //Checking the introduced country.
                logic.checkLevel(rapper); //Checking the introduced level.
                System.out.println("- Photo URL: ");
                rapper.setPhoto(sc.nextLine());

                ui.showRapper(rapper); //Showing the attributes of the new rapper.

                logic.getReadCompetition().getRappers().add(rapper); //adding the rapper to the competition.
                logic.writeJSONcomp(rapper); //Writing the new rapper in the Json file.

                System.out.println("\nRegistration completed!");
                System.out.println("-------------------------------------------------");
            }
            else if (option == 2) {
                System.out.println("Leaving the registration...");
            } else {
                System.out.println("\nError, enter a correct value. ");
            }
        }
    }

    /**
     * The menu to log into the competition as a rapper using an existing stageName, if we don't find the stageName
     * introduced by the user we ask to enter a new stageName. The user can also decide to leave the competition.
     * @param logic logic class in order to be able to use the information stored in logic.
     * @return returns 1 if we found the stageName introduced by the user and 0 if we didn't.
     */
    public int login (Logic logic) {
        MenuUI ui = new MenuUI();
        Scanner sc = new Scanner(System.in);

        int option = 0;
        int found = 0;

        while (option != 2 && found == 0) {
            ui.uiLogin();
            option = Logic.atoi(sc.nextLine());
            if (option == 1) {
                while (found == 0) {
                    Rapper myRapper = new Rapper();
                    System.out.println("Enter your stage name: ");
                    myRapper.setStageName(sc.nextLine()); //Scanning the stage name introduced by the user.

                    for (Rapper rapper : logic.getReadCompetition().getRappers()) {
                        if(rapper.getStageName().equals(myRapper.getStageName())) {
                            myRapper = rapper; //Setting the rapper with that stage name as our rapper.
                            logic.getReadCompetition().getRappers().remove(rapper);
                            logic.getReadCompetition().getRappers().addLast(myRapper); //Adding our rapper in the last position of the array.
                            found = 1; //The stage name exists.
                            System.out.println("\nWe found you, little brat!\n");
                            break;
                        }
                    }
                    if (found == 0) System.out.println("Yo' bro, there's no " + myRapper.getStageName() + " in ma' list.");
                }
            }
            else if (option != 2) {
                System.out.println("\nError, enter a correct value. ");
            }
        }

        return found;
    }

    /**
     *
     * @param competition The current competition from which we will take the necessary information
     * @param phase The phase we are in.
     */
    public void startPhase (ReadCompetition competition, int phase) {
        MenuUI ui = new MenuUI();

        switch (competition.getCompetition().getPh().size()) {
            //Depending on how many phases are on the competition runs phase 2 or not.
            case 2:
                competition.getCompetition().getPh().get(0).phase1Functions(competition); //Phase 1.
                competition.getCompetition().getPh().get(1).phase3Functions(competition); //Phase 3.
                break;
            case 3:
                competition.getCompetition().getPh().get(0).phase1Functions(competition); //Phase 1.
                competition.getCompetition().getPh().get(1).phase2Functions(competition); //Phase 2.
                competition.getCompetition().getPh().get(2).phase3Functions(competition); //Phase 3.
        }
        if (phase == 1) {
            ui.showLoser(competition.getRappers(), competition.getCompetition().getCurrentphase()); //Shows if you have won.
        }
        ui.showWinner (competition.getRappers()); //Shows the winner.

    }


    /**
     * Runs the menu, chooses which one to show to the user depending on if the competition has started or not and if
     * the competition is already over, checks if there is a winner in the winner Json file, and if there is, shows it
     * to the user, but if there is not, simulates the competition, shows the winner to the user and writes it in the
     * winner Json file.
     */
    public void runMenu () {
        Logic logic = new Logic();
        MenuUI ui = new MenuUI();

        logic.readJSONbat();
        logic.readJSONcomp();

        ui.mainMenu(logic.getReadCompetition());

        switch (selectMenu(logic.getReadCompetition())) {
            //Depending on the dates decides which menu to run, or if the competition is already over.
            case 1: register(logic);
                break;
            case 2: int login = login(logic);
                    if (login == 1) {
                        startPhase(logic.getReadCompetition(), 1);
                    }
                    logic.writeWinner(logic.getReadCompetition().getRappers()); //Writes the winner in the winner Json file.
                break;
            case 3:
                    try {
                        Gson gggson = new Gson();
                        JsonReader reader = null;

                        try {
                            reader = new JsonReader(new FileReader("files/winner.json"));
                            Rapper rapper = new Rapper();
                            rapper.setStageName(gggson.fromJson(reader, String.class));
                            char aux = rapper.getStageName().charAt(1);
                            System.out.println("The winner is " + rapper.getStageName());
                            //If we can read something in the Json means that the competition has been runned and we have a winner.
                        } catch (FileNotFoundException e) {
                            System.out.println("\nError opening the file");
                            System.exit(1);
                        }
                    } catch (Exception e) {
                    //If we can't read anything, that means that we have to simulate the competition to get the winner.
                    logic.getReadCompetition().getCompetition().setCurrentphase(4);
                    startPhase(logic.getReadCompetition(), 0);
                    logic.writeWinner(logic.getReadCompetition().getRappers());
                    }
                break;
        }
    }

}
