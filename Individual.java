import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Individual class stores information on
 * the chromosome and fitness level.
 */
public class Individual {

    ArrayList<Character> chromosome = new ArrayList<Character>();
    int c_0 = 8;
    int c_max = 20;
    int g = 4;
    Double m = 0.01;

    /*
     * Constructor #1: Generates a random 8-letter sequence
     * with combinations of 4 different letters
     */
    public Individual () {
        for (int i = 0; i < c_0; i ++) {
            Character let = randomLetter(g);
            this.chromosome.add(let);
        }
    }
    /*
     * Constructor #2 : Generates the offspring's chromosome
     * Given the chromosomes of its two parents
     */
    public Individual(ArrayList<Character> p1, ArrayList<Character> p2) {
        // finding size of inherited chromosome from each parent
        int p1Length = ThreadLocalRandom.current().nextInt(1, p1.size()+1);
        int p2Length = ThreadLocalRandom.current().nextInt(1, p2.size()+1);

        System.out.println("p1Length= "+ p1Length + " p2Length= "+p2Length);

        // add the excerpt from each parent
        // working backwards in the case of the second parent
        for (int i = 0; i < p1Length; i++) {
            this.chromosome.add(p1.get(i));
        }
        for (int i = p1Length; i < p1Length + p2Length; i++) {
            this.chromosome.add(p2.get(i - p1Length - p2Length + p2.size()));
        }
        // if the chromosome is longer than the max length,
        // truncate letters from the end
        if (this.chromosome.size() > c_max) {
            this.chromosome.subList(c_max, this.chromosome.size()).clear();
        }
        // random mutations
        for (int i = 0; i < this.chromosome.size(); i++) {
            Double rand = ThreadLocalRandom.current().nextDouble();
            if (rand < m) {
                this.chromosome.set(i, randomLetter(g));
            }
        }
    }

    /*
     * Returns the fitness score of an Individual
     */
    public int getFitness() {
        int fitness = 0;
        int size = this.chromosome.size();
        // Compare each letter with its mirror partner
        for (int i = 0; i < Math.round(size / 2); i++ ){
            if (this.chromosome.get(i) == this.chromosome.get(size - 1 - i)) {
                fitness += 1;
            } else {
                fitness -= 1;
            }
        }
        // Compare each letter with the preceding letter
        for (int i = 1; i < this.chromosome.size(); i++) {
            if (this.chromosome.get(i) == this.chromosome.get(i-1)){
                fitness -= 1;
            }
        }
        return fitness;
    }


    /** Choose a letter at random, in the range
     *  from A to the number of letters indicated */
    private Character randomLetter(int num_dna_letters) {
        return Character.valueOf((char)(65+ThreadLocalRandom.current().nextInt(num_dna_letters)));
    }

    /** Expresses the individual's chromosome
     *  as a String, for display purposes */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
          builder.append(ch);
        }
        return builder.toString();
        //return chromosome.stream().map(e->e.toString()).collect(Collectors.joining());
    }
    /*
     * Testing main method
     */
    public static void main(String[] args) {
        Individual p1 = new Individual();
        System.out.println(p1.toString());

        Individual p2 = new Individual();
        System.out.println(p2.toString());

        Individual child = new Individual(p1.chromosome, p2.chromosome);
        System.out.println(child.toString());
        System.out.println(child.getFitness());
    }

}