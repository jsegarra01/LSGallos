/*
 * Logic
 *
 * The "Logic" class will do all the operations of our system. Will read and write the JSON documents, check if the introuced strings are correct, etc
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

/*
 * Import package com.google.gson
 * Import package java.util
 * Import package java.text
 * Import package java.io
 * Import package java.nio
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The "Logic" class will do all the operations of our system. Will read and write the JSON documents, check if the introuced strings are correct, etc
 */
public class Logic {

    private ReadBattles ReadBatalles = new ReadBattles();
    private ReadCompetition ReadCompetition = new ReadCompetition();

    /**
     * Reads the battles Json file and stores its values in the corresponding attributes of the corresponding classes.
     */
    public void readJSONbat() {
        Gson gson = new Gson();
        JsonReader reader = null;

        try {
            reader = new JsonReader(new FileReader("files/batalles.json"));
            ReadBatalles = gson.fromJson(reader, ReadBattles.class);
            //Saving the values of the Json file in the attributes of each of the corresponding classes.
        } catch (FileNotFoundException e) {
            System.out.println("\nError opening the file");
            System.exit(1);
        }

        try {
            reader.close(); //Closing the reader.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the competition Json file and stores its values in the corresponding attributes of the corresponding
     * classes.
     */
    public void readJSONcomp () {
        Gson gson = new Gson();
        JsonReader reader = null;

        try {
            reader = new JsonReader(new FileReader("files/competicio.json"));
            ReadCompetition = gson.fromJson(reader, ReadCompetition.class);
            //Saving the values of the Json file in the attributes of each of the corresponding classes.
        } catch (FileNotFoundException e) {
            System.out.println("\nError opening the file");
            System.exit(1);
        }
        System.out.println("\nThe file batalles.json was read correctly");
        System.out.println("The file competicio.json was read correctly\n");

        try {
            reader.close(); //Closing the reader.

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Writes the new rapper that the user has introduced in the competition Json file.
     * @param rapper The rapper registered by the user.
     */
    public void writeJSONcomp (Rapper rapper) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = null;

        try {
            writer = Files.newBufferedWriter(Paths.get("files/competicio.json"));
            //Defining to which file we will write.
        } catch (IOException e) {
            e.printStackTrace();
        }

        gson.toJson(ReadCompetition, writer); //Writing the content of readCompetition in the file.

        try {
            writer.close(); //Closing the writer.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the rapper with the highest score (the winner) and writes it in the Winner Json file.
     * @param rappers The linkedList of all the rappers of the competition with its corresponding values, like their
     * competition score.
     */
    public void writeWinner (LinkedList<Rapper> rappers) {
        Gson ggson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = null;
        Phase phase = new Phase();
        phase.sortScore(rappers); //Looking for the highest score, sorting the rappers by their score.

        try {
            writer = Files.newBufferedWriter(Paths.get("files/winner.json")); //Defining to which file we will write.
        } catch (IOException e) {
            e.printStackTrace();
        }

        ggson.toJson(rappers.get(0).getStageName(), writer); //Writing the stage name of the winner in the Json file.

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ascii to double function, transforms an inputted string to double.
     * @param sDob String introduced by the user.
     * @return The inputted String transformed to double.
     */
    public static double atod (String sDob) {
        double reto = 0.;

        try {
            reto = Double.parseDouble(sDob); //Parsing the String.
        }
        catch (NullPointerException | NumberFormatException e) {
            return 0;
        }

        return reto;
    }

    /**
     * Ascii to integer function, transforms an inputted String to integer, using the atod function.
     * @param sInt String introduced by the user.
     * @return The inputted String transformed to integer.
     */
    public static int atoi (String sInt) {
        return (int) (atod (sInt)); //Casting the float of the atod into an integer.
    }

    /**
     * Compares the stage name introduced by the user when registering a new rapper to all of the existing rappers
     * stage name to see if the stage name is already in use. If it is not, adds it to the rappers stage name attribute.
     * @param rapper The rapper that the user is registering.
     */
    public void checkStageName (Rapper rapper) {
        int found = 0;
        int correct = 0;
        Scanner sc = new Scanner(System.in);
        while (correct == 0) {
            found = 0;
            System.out.println("- Artistic name: ");
            String stageName = sc.nextLine(); //Scanning the introduced stage name.

            if (stageName.equals("")) {
                System.out.println("Error, please enter a name\n");
            }
            else {
                for (int i = 0; i < ReadCompetition.getRappers().size(); i++) {
                    if(ReadCompetition.getRappers().get(i).getStageName().equals(stageName)) {
                        found = 1; //The stage name is already in use.
                    }
                }
                if (found == 0) {
                    rapper.setStageName(stageName); //The stage name can be used since it is not in use.
                    correct = 1;
              }
                else {
                    System.out.println("Error, the stage name is already in use.\n");
                }
            }
        }
    }

    /**
     * Gets the birthdate introduced by the user and checks whether if it is valid or not.
     * @param birthDate The birthdate introduced by the user.
     * @return Returns True if the birthdate is valid, False if its not.
     */
    public boolean checkBD (String birthDate) {
        if (birthDate.trim().equals("")) {
            return false; //The date is null
        }
        else {

            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
            sdfrmt.setLenient(false);

            try {
                Date javaDate = sdfrmt.parse(birthDate); //Looking if the introduced date is in the correct format.
                Date date = java.util.Calendar.getInstance().getTime(); //Getting todays date.
                if(javaDate.after(date)) {
                    System.out.println("Error, " + birthDate + " is an invalid date, introduce a valid date.");
                    return false; //The introduced date is after our current day (in the future).
                }
            }
            catch (ParseException e) {
                System.out.println("Error, " + birthDate + " is an invalid date, introduce a valid date.");
                return false;
            }
            return true;
        }
    }

    /**
     * Scans the introduced birthdate and if it is valid adds it to the rappers birthdate attribute.
     * @param rapper The rapper that the user is registering.
     */
    public void checkBirthDate(Rapper rapper) {
        Scanner sc = new Scanner(System.in);
        int correct = 0;
        while (correct == 0) {
            System.out.println("- Birth date (dd/MM/YYYY): ");
            String birthDate = sc.nextLine();

            if(checkBD(birthDate)) { //The introduced birth date is valid.
                SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
                sdfrmt.setLenient(false);
                Date javaDate = null;
                try {
                    javaDate = sdfrmt.parse(birthDate);
                    //Setting as a Date the introduced String in order to change its format.
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
                birthDate = DATE_FORMAT. format(javaDate);
                //Changing the format of the date to write it in the same way as it is on the Json file.
                rapper.setBirth(birthDate); //Setting the birth date of the new rapper.
                correct = 1;
            }
        }
    }

    /**
     * Gets the country introduced by the user and checks whether if it exists or not.
     * @param country The country introduced by the user.
     * @return Returns True if the country exists, False if not.
     */
    public boolean checkC (String country) {
        int found = 0;
        String[] locales = Locale.getISOCountries();
        for (String locale : locales) {
            Locale obj = new Locale("", locale);
            if (country.equals(obj.getDisplayCountry(Locale.ENGLISH))) {
                found = 1;
                //Compares the introduced country to all of the existing countries in order to see if it exists.
                break;
            }
        }
        return found == 1;
    }

    /**
     * Scans the country introduced by the user and if it exists adds it to the rappers country attribute.
     * @param rapper The rapper that the user is registering.
     */
    public void checkCountry (Rapper rapper) {
        Scanner sc = new Scanner(System.in);
        int correct = 0;
        while (correct == 0) {
            System.out.println("- Country: ");
            String country = sc.nextLine(); //Scanning the introduced country.

            if(checkC(country)) { //Checking if the country exists.
                rapper.setNationality(country); //Setting the new rappers country.
                correct = 1;
            } else {
                System.out.println("Error introduce a valid country");
            }
        }
    }

    /**
     * Gets the value introduced by the user and checks whether if it is a number or not.
     * @param strNum The value introduced by the user.
     * @return Returns True if it is a number, False if not.
     */
    public static boolean isNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum); //Parsing the introduced value to see if it is a number.
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Scans the value introduced by the user and if it is a number adds it to the rappers level attribute.
     * @param rapper The rapper that the user is registering.
     */
    public void checkLevel (Rapper rapper) {
        Scanner sc = new Scanner(System.in);
        int correct = 0;
        while (correct == 0) {
            System.out.println("- Level: ");
            String level = (sc.nextLine()); //Scanning the introduced value.

            if (isNumber(level)) { //Checking if the value is a number.
                rapper.setLevel(atoi(level)); //Setting the level of the new rapper.
                if (rapper.getLevel() > 0 && rapper.getLevel() < 4) {
                    correct = 1;
                }
            } else {
                System.out.println("Error: There only exists level 1, 2 and 3");
            }
        }
    }

    public ReadBattles getReadBatalles() {
        return ReadBatalles;
    }

    public ReadCompetition getReadCompetition () {
        return ReadCompetition;
    }

    /**
     * Gets the last 2 letters from each phrase of the rhymes and compares them in order to see if there is any rhyme.
     * @param rhymes current number of rhymes.
     * @param phrase1 the phrase the rapper said.
     * @return The number of rhymes there is.
     */
    public int numberRhymes(int rhymes, String phrase1) {
        StringBuilder yey = new StringBuilder(); //Auxiliar String
        StringBuilder buff = new StringBuilder(); //Auxiliar String
        StringBuilder new1 = new StringBuilder(); //Auxiliar String
        String rhyme = new String(); //Auxiliar String
        boolean found;
        int index = 0;
        yey.append("\n");

        while (index != -1) {
            index = phrase1.indexOf("\n", index + 1); //Looking for the "/n".
            if (index != -1) {
                buff.append(phrase1.charAt(index - 3)); //Moving the second-to-last letter of the phrase to the auxiliar String.
                buff.append(phrase1.charAt(index - 2)); //Moving the last letter of the phrase to the auxiliar String.
                found = Boolean.FALSE;
                for (int j = 0; j+1 <= rhyme.length() && !found; j++) {
                    new1.insert(0, rhyme.charAt(j));
                    new1.insert(1, rhyme.charAt(j+1));
                    //Moving the words from the previous phrases to an auxiliar String.
                    if (buff.compareTo(new1) == 0) { //Comparing the letters to see if there is a rhyme.
                        found = Boolean.TRUE;
                    }
                    new1.deleteCharAt(1);
                    new1.deleteCharAt(0);
                    j++;
                }
                if (!found) {
                    rhyme = rhyme.concat(buff.toString());
                    //In the case there isn't a rhyme, concatenates the 2 letters to compare them to the following phrases.
                }
                else {
                    rhymes++;
                }
                buff.deleteCharAt(1);
                buff.deleteCharAt(0);
            }
        }

        buff.append(phrase1.charAt(phrase1.length()-3)); //Moving the second-to-last letter of the phrase to the auxiliar String.
        buff.append(phrase1.charAt(phrase1.length()-2)); //Moving the last letter of the phrase to the auxiliar String.
        found = Boolean.FALSE;
        for (int j = 0; j+1 <= rhyme.length() && !found; j++) {
            new1.insert(0, rhyme.charAt(j));
            new1.insert(1, rhyme.charAt(j+1));
            //Moving the words from the previous phrases to an auxiliar String.
            if (buff.compareTo(new1) == 0) { //Comparing the letters to see if there is a rhyme.
                found = Boolean.TRUE;

            }
            new1.deleteCharAt(1);
            new1.deleteCharAt(0);
            j++;
        }
        if (!found) {
            rhyme = rhyme.concat(buff.toString());
            //In the case there isn't a rhyme, concatenates the 2 letters to compare them to the following phrases.
        } else {
            rhymes++;
        }
        buff.deleteCharAt(1);
        buff.deleteCharAt(0);

        if (rhymes == 1) {
            rhymes = 0;
        }

        return rhymes;
    }

    /**
     * Scans the phrases entered by the user and gets the last 2 letters of each of them and compares them in order
     * to see if there any a rhyme.
     * @return The number of rhymes the user did.
     */
    public int numberRhymesMyRapper() {
        Scanner sc = new Scanner(System.in);
        String rhyme = new String();
        boolean found;
        int number = 1;

        for (int i = 0; i < 4; i++) {
            StringBuilder yey = new StringBuilder(); //Auxiliar String.
            StringBuilder buff = new StringBuilder(); //Auxiliar String.
            int k = 0;
            found = Boolean.FALSE;
            do {
                buff.delete(0,buff.lastIndexOf(buff.toString()));
                String phrase1 = sc.nextLine();
                try {
                    buff.insert(0, phrase1.charAt(phrase1.length() - 2));
                    buff.insert(1, phrase1.charAt(phrase1.length() - 1));
                    //Moving the last letters of the phrase to the auxiliar String.
                    k = 1;
                }catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Please, introduce a correct verse");
                }
            }while (k == 0);

            for (int j = 0; j+1 <= rhyme.length() && !found; j++) {
                yey.insert(0, rhyme.charAt(j));
                yey.insert(1, rhyme.charAt(j+1));
                //Moving the words from the previous phrases to an auxiliar String.
                if (buff.compareTo(yey) == 0) { //Comparing the letters to see if there is a rhyme.
                    found = Boolean.TRUE;
                }
                yey.deleteCharAt(1);
                yey.deleteCharAt(0);
                j++;

            }
            if (!found) {
                rhyme = rhyme.concat(buff.toString());
                //In the case there isn't a rhyme, concatenates the 2 letters to compare them to the following phrases.
            } else {
                number++;
            }
        }
        if (number == 1) {
            number = 0;
        }
        return number;
    }

    /**
     * This function asks about which rapper wants the user to make the profile about, makes it, and
     * opens it in html format.
     * @param rappers The list of rappers of the competition.
     */
    public void askrapper (LinkedList<Rapper> rappers){
        Country country = new Country();
        Scanner sc = new Scanner(System.in);
        int found = 0;

        while (found == 0) {
            Rapper myRapper = new Rapper();
            System.out.println("\nEnter 'EXIT' to exit this menu.");
            System.out.println("Enter the name of the rapper: \n");
            myRapper.setStageName(sc.nextLine()); //Scanning the stage name introduced by the user.

            for (Rapper rapper : rappers) {
                if(rapper.getStageName().equals(myRapper.getStageName()) || rapper.getRealName().equals(myRapper.getStageName())) {
                    myRapper = rapper; //Setting the rapper with that name as our rapper.
                    System.out.println("\nWe found you, little brat!\n");

                    try {
                        country.setInfoRapper(myRapper);
                    }
                    catch (Exception e) {
                        System.out.println("\nThere has been a problem generating the file. Please check again.\n");
                    }

                    found = 1;
                    break;
                }
            }
            if (found == 0 && !myRapper.getStageName().equals("EXIT")) System.out.println("Yo' bro, there's no " + myRapper.getStageName() + " in ma' list.");
            else found = 1;
        }
    }
}