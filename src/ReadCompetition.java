/*
 * ReadCompetition
 *
 * The "ReadCompetition" class contains the structure of the competition Json file as well as the setters and getters of the
 * different attributes of the class.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

//import java.util
import java.util.LinkedList;

/**
 * The "ReadCompetition" class contains the structure of the competition Json file as well as the setters and getters of the
 * different attributes of the class.
 */
public class ReadCompetition {
    private Competition competition;
    private LinkedList<String> countries;
    private LinkedList<Rapper> rappers;

    public ReadCompetition() {
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public LinkedList<Rapper> getRappers() {
        return rappers;
    }

    public void setRappers(LinkedList<Rapper> rappers) {
        this.rappers = rappers;
    }

    public LinkedList<String> getCountries() {
        return countries;
    }

    public void setCountries(LinkedList<String> countries) {
        this.countries = countries;
    }
}