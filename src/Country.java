import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.salleurl.profile.Profile;
import edu.salleurl.profile.ProfileFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

/**
 * The class country is the one that reads the API and manages its information.
 */
public class Country {

    private String name;
    private LinkedList<Language> languages;
    private String flag;

    Profile profile;
    List<Country> country;

    public Country() {

    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getCountryName() {
        return name;
    }

    public void setCountryName(String countryName) {
        this.name = countryName;
    }

    public LinkedList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(LinkedList<Language> languages) {
        this.languages = languages;
    }

    public String getFlagUrl() {
        return flag;
    }

    public void setFlagUrl(String flagUrl) {
        this.flag = flagUrl;
    }

    /**
     * This function reads from the api REST countries and stores its values.
     * @param rapper The rapper from who we will take the country we want to get information from.
     * @return Returns the values we read stored in a list of countries of size 1.
     * @throws Exception
     */
    public List<Country> readApi (Rapper rapper) throws Exception {
        URL oracle = new URL("https://restcountries.eu/rest/v2/name/" + rapper.getNationality()); //opens the API.
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));


        Gson gson = new Gson();

        try {
            Type collectionType = new TypeToken<List<Country>>(){}.getType();
            country = (gson.fromJson(in.readLine(), collectionType)); //saves the information from the api.
            return country; //returns the information.
        } catch (FileNotFoundException e) {
            System.out.println("\nError opening the file");
            System.exit(1);
        }
        return null;
    }

    /**
     * We set the different information about the profile we will display.
     * @param myRapper The rapper whose profile we will display.
     * @throws Exception
     */
    public void setInfoRapper (Rapper myRapper) throws Exception {
        country = readApi(myRapper); //reads the information from the API.
        String fileName = toCamelCase(myRapper.getRealName()); //sets the name of the html file to lowerCamelCase.
        profile = ProfileFactory.createProfile("C:\\Users\\svive\\Desktop\\Universitat\\2n Carrera\\Eng Informàtica\\Object Oriented Programming Design\\Projecte 1\\fase4_Josep.Segarra_Sergi.Vives\\Intellij_LS_Gallos_Josep.Segarra_Sergi.Vives\\files\\" + fileName +".html", myRapper);
        profile.setProfileable(myRapper);
        profile.addExtra("Level", String.valueOf(myRapper.getLevel()));
        profile.addExtra("Points", String.valueOf(myRapper.getScore()));
        profile.setCountry(myRapper.getNationality());
        for (int i = 0; i < country.get(0).getLanguages().size(); i++) {
            profile.addLanguage(country.get(0).getLanguages().get(i).getName());
        }
        profile.setFlagUrl(country.get(0).getFlagUrl());
        //Creating the profile and setting its information.
        profile.writeAndOpen(); //writes on the html file and opens it in your default browser.
    }

    /**
     * This function coverts an string to lowerCamelCase format.
     * @param s String to be converted.
     * @return Returns the converted String.
     */
    static String toCamelCase(String s){

        // create a StringBuilder to create our output string
        StringBuilder sb = new StringBuilder();

        //Set the first letter to lowercase.
        sb.append(Character.toLowerCase(s.charAt(0)));

        // determine when the next capital letter will be
        boolean nextCapital = false;

        // loop through the string
        for (int i=1; i<s.length(); i++) {

            // if the current character is a letter
            if (Character.isLetter(s.charAt(i))) {

                // get the current character
                char tmp = s.charAt(i);

                // make it a capital if required
                if (nextCapital) tmp = Character.toUpperCase(tmp);

                // add it to our output string
                sb.append(tmp);

                // make sure the next character isn't a capital
                nextCapital = false;

            } else {
                // otherwise the next letter should be a capital
                nextCapital = true;
            }
        }

        // return our output string
        return sb.toString();
    }
}
