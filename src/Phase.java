/*
 * Phase
 *
 * The "Phase" class will contain the different methods that are needed to run in each different phase.
 *
 * Josep Segarra and Sergi Vives
 *
 * Version 11/12/2020
 */

//Import package java.util
import java.util.*;

/**
 * The "Phase" class will contain the different methods that are needed to run in each different phase.
 */
public class Phase {
    private float budget;
    private String country;

    public Phase() {
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sorts the rappers depending on its score.
     * @param rappers List of rappers.
     */
    public void sortScore (LinkedList<Rapper> rappers) {
        class ScoreComparator implements Comparator<Rapper> {
            @Override
            public int compare(Rapper student1, Rapper student2)
            {
                if(student1.getScore()==student2.getScore()) //Comparing the score.
                    return 0;
                else if(student1.getScore() > student2.getScore()) //Comparing the score.
                    return -1;
                else
                    return 1;
            }
        }
        ((List<Rapper>) rappers).sort(new ScoreComparator()); //Sorting the rappers depending on the score.
    }

    /**
     * Sorts the Linked List of rappers by the scores the have, in descending order. Moreover, it eliminates the worst depending
     * on the number we want for that phase
     * @param rappers List of rappers.
     * @param number Size of the rappers array we want to get. Will be lower than the maximum number of rappers
     * @param competition Current competition.
     */
    public void getBestScore(LinkedList<Rapper> rappers, int number, Competition competition) {
        LinkedList<Rapper> notSorted = (LinkedList<Rapper>) rappers.clone(); //Clones the list of rappers into an auxiliar list.
        sortScore (rappers); //Sorting the list of rappers depending on their scores.
        LinkedList<Rapper> battlers = (LinkedList<Rapper>) rappers.clone(); //Clones the list of rappers into an auxiliar list.
        rappers.clear();
        int previous = competition.getExit();
        competition.setExit(1);
        int found = -1;

        for (int i = 0; i < number; i++) {
            rappers.add(battlers.get(i)); //We get every battler ordered to know if it is correct or not.
            if (notSorted.getLast().getStageName().equals(rappers.get(i).getStageName())) {
                competition.setExit(0); // if we find our rapper inside the number given, then we shall continue the competition
                found = i;
            }
        }

        if (found != -1) {
            rappers.addLast(rappers.get(found)); // if we find our rapper, we add it in the last position
            rappers.remove(found);
        }
        else {
            if (previous != 1) {
                competition.lobby(notSorted.getLast(),competition, notSorted.getFirst(), notSorted,4);
            }
        }
    }

    /**
     * If there is an odd number of rappers eliminates a random one different from the users rapper.
     * @param competition Current competition with its rappers.
     */
    public void eliminateRapper(ReadCompetition competition) {
        if (competition.getRappers().size()%2 == 1) { //Checks if there is an odd number of rappers.
            Random rand = new Random();
            competition.getRappers().remove(rand.nextInt(competition.getRappers().size()-1)); //Removes a random rapper.
        }

    }

    /**
     * Generates the pairings of the battles randomly.
     * @param rappers List of rappers.
     */
    public void generatePairs(LinkedList<Rapper> rappers) {
        Random rand = new Random();
        LinkedList<Rapper> battlers = (LinkedList<Rapper>) rappers.clone(); //Clones the list of rappers into an auxiliar list.

        int size = rappers.size();
        rappers.clear();
        for (int i = 0; i < size - 1; i++) {
            int number = rand.nextInt(battlers.size()-1);
            rappers.add(battlers.get(number));
            battlers.remove(number);
            //Reorders randomly the rappers list in order to generate random pairs.
        }
        rappers.addLast(battlers.getLast());
    }

    /**
     * We are going to generate all the battles for every rapper. If it is our battle, we are going to call the lobby.
     * @param rappers List of rappers.
     * @param compet Current competition.
     */
    public void generateBattle(LinkedList<Rapper> rappers, Competition compet) {
        int i = 0;
        Random rand = new Random();
        int battle_type = rand.nextInt(3);

        while (i < rappers.size()) {
            Battle battle = new Battle();
            if (i + 2 == rappers.size()) {     //our rappers battle
                if (compet.getExit() != 1) {
                    compet.lobby(rappers.get(i + 1), compet, rappers.get(i), rappers, battle_type); //Generates the lobby.
                }
                else {
                    battle.whichBattle(rappers.get(i), rappers.get(i + 1), 0, battle_type);
                    compet.setCurrentbattle(compet.getCurrentbattle() + 1);
                }
            } else {
                battle.whichBattle(rappers.get(i), rappers.get(i + 1), 0, battle_type);
            }
            i += 2;
        }
    }

    /**
     * The functionalities for the first phase.
     * @param competition Current competition with its rappers.
     */
    public void phase1Functions(ReadCompetition competition) {
        if (competition.getCompetition().getExit() != 1) {
            competition.getCompetition().setCurrentphase(1);
        }

        while (competition.getCompetition().getCurrentbattle() != 3) {
            eliminateRapper(competition); //Eliminates a rapper if there is an odd number of rappers.
            generatePairs(competition.getRappers()); //Generates the random pairings.
            //System.out.println(competition.getRappers().size());
            generateBattle(competition.getRappers(), competition.getCompetition()); //Generates the battle.
        }

        competition.getCompetition().setCurrentbattle(1);
    }

    /**
     * The functionalities for the second phase.
     * @param competition Current competition with its rappers.
     */
    public void phase2Functions(ReadCompetition competition) {

        if (competition.getCompetition().getExit() != 1) {
            competition.getCompetition().setCurrentphase(2);
        }

        //We get the linked list which has the half of the size we want. Orders by score and deletes up until the number we say
        getBestScore(competition.getRappers(), (competition.getRappers().size())/ 2, competition.getCompetition());

        while (competition.getCompetition().getCurrentbattle() != 3) {
            eliminateRapper(competition); //Eliminates a rapper if there is an odd number of rappers.
            generatePairs(competition.getRappers()); //Generates the random pairings.
            generateBattle(competition.getRappers(), competition.getCompetition()); //Generates the battle.
        }
        competition.getCompetition().setCurrentbattle(1);
    }

    /**
     * The functionalities for the third phase.
     * @param competition Current competition with its rappers.
     */
    public void phase3Functions(ReadCompetition competition) {

        if (competition.getCompetition().getExit() != 1) {
            if (competition.getCompetition().getCurrentphase() == 2) {
                competition.getCompetition().setCurrentphase(3);
            }
            else {
                competition.getCompetition().setCurrentphase(2);
            }

        }

        //We get the linked list which has the half of the size we want. Orders by score and deletes up until the number we say
        getBestScore(competition.getRappers(), 2, competition.getCompetition());

        while (competition.getCompetition().getCurrentbattle() != 3) {
            eliminateRapper(competition); //Eliminates a rapper if there is an odd number of rappers.
            generatePairs(competition.getRappers()); //Generates the random pairings.
            generateBattle(competition.getRappers(), competition.getCompetition()); //Generates the battle.
        }
        competition.getCompetition().setCurrentbattle(1);
    }
}