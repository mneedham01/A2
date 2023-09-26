import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {
    // number of individuals in a population
    int n;
    // number of winners
    int k;
    // number of evolution rounds to run
    int r;

    /*
     * Constructor stores experimental parameters in field
     */
    public GA_Simulation() {
        this.n = 100;
        this.k = 15;
        this.r= 100;
    }

    /*
     * Intializes a population of the desired size
     */
    public ArrayList<Individual> init() {
        ArrayList<Individual> list = new ArrayList<Individual>();
        for (int i= 0; i < this.n + 1; i++) {
            Individual ind = new Individual();
            list.add(ind);
        }
        return list;
    }

    /** Sorts population by fitness score, best first */
    public void rankPopulation(ArrayList<Individual> pop) {
      // sort population by fitness
      Comparator<Individual> ranker = new Comparator<>() {
        // this order will sort higher scores at the front
        public int compare(Individual c1, Individual c2) {
          return (int)Math.signum(c2.getFitness()-c1.getFitness());
        }
      };
      pop.sort(ranker);
    }


}