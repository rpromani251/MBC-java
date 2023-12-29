import java.util.*;

public class MBC {
    // Instance Variables:
    private double alpha; // Laplace Smoothing
    private List<Double> classProbabilities;
    private HashMap<Integer, Double> wordProbabilities;
    private int[][] encodedDoc;
    private String[] labels;
    

    // Class Constructor --> Laplace Smoothing Factor as param
    public MBC(double alpha, int[][] encodedDoc, String[] labels) {
        // Instantiate Instance Variables:
        this.alpha = alpha;
        this.classProbabilities = new ArrayList<>();
        this.wordProbabilities = new HashMap<>();
        this.encodedDoc = encodedDoc;
        this.labels = labels;
    }

    // Fitting the Model: Calculate Class Probabilities and Word Probabilities (with Laplace Smoothing)
    public void fit() {
        // Initialize a CountVectorizer to find number of instances of each class
        CountVectorizer vectorizer = new CountVectorizer();
        vectorizer.fit(labels);
        HashMap<String, Integer> vocab = vectorizer.getVocab();
        double total = vocab.values().stream().mapToInt(Integer::intValue).sum();

        // Calculate Class Probabilities:
        for (String label : new HashSet<>(Arrays.asList(labels))) {
            double classCount = vocab.getOrDefault(label, 0);
            double probability = (classCount + alpha) / (total + alpha);
            classProbabilities.add(probability);
        }

        // Calculate Word Probabilities:
        
    }

    // Getters
    public List<Double> getClassProbs() {
        return classProbabilities;
    }

}
