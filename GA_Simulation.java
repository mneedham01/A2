import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class GA_Simulation {
    // number of individuals in a population
    int n;
    // number of winners
    int k;
    // number of evolution rounds to run
    int r = 100;

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

    /*
     * Selects winners and then randomly selects two parents
     * for each member of the new generation
     */
    public ArrayList<Individual> evolve(ArrayList<Individual> pop) {
        ArrayList<Individual> newGen = new ArrayList<Individual>();
        // selects winners of first population
        ArrayList<Individual> winners = new ArrayList<Individual>();
        for (int i = 0; i < this.k; i++) {
            winners.add(pop.get(i));
        }
        // loop for each new individual
        for (int i = 0; i < this.n; i++) {
            // randomly selects two parents
            int num1 = ThreadLocalRandom.current().nextInt(0, this.k);
            Individual p1 = winners.get(num1);
            int num2 = ThreadLocalRandom.current().nextInt(0, this.k);
            // ensure individual cannot breed with themselves
            while (num1 == num2){
                num2 = ThreadLocalRandom.current().nextInt(0, this.k);
            }
            Individual p2 = winners.get(num2);
            // create new individual and add to ArrayList
            Individual offspring = new Individual(p1.chromosome, p2.chromosome);
            newGen.add(offspring);
        }
        return newGen;
    }

    /*
     * Prints statistics about the current generation
     */
    public void describeGeneration(ArrayList<Individual> pop) {
        Individual top = pop.get(0);
        Individual bottom = pop.get(0);
        for (int i = 1; i < pop.size(); i++) {
            if (pop.get(i).getFitness() > top.getFitness()) {
                top = pop.get(i);
            }
            if (pop.get(i).getFitness() < bottom.getFitness()) {
                bottom = pop.get(i);
            }
        }
        System.out.println("Highest fitness score: "+ top.getFitness());
        System.out.println("Highest fitness chromosome: "+ top.toString());
        System.out.println("Lowest fitness score: "+ bottom.getFitness());
    }

    public static void main(String[] args) {
        GA_Simulation experiment = new GA_Simulation();
        ArrayList<Individual> generation = experiment.init();
        for (int i = 0; i < 100; i++) {
            System.out.println("\nGeneration: "+i);
            generation = experiment.evolve(generation);
            experiment.describeGeneration(generation);
        }
    }

}