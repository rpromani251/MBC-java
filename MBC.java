import java.util.*;

public class MBC {
    // Instance Variables:
    private double alpha; // Laplace Smoothing
    private List<Double> classProbabilities;
    private double[][] wordProbabilities;

    // Labels and encodedDoc are same length, with each index correspnding to one pair -> document, label
    private int[][] encodedDoc;
    private String[] labels;
    private List<String> uniqueLabels;
    

    // Class Constructor --> Laplace Smoothing Factor as param
    public MBC(double alpha, int[][] encodedDoc, String[] labels) {
        // Instantiate Instance Variables:
        this.alpha = alpha;
        this.classProbabilities = new ArrayList<>();
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
        System.out.println(total);

        // Calculate Class Probabilities: 
        this.uniqueLabels = new ArrayList<String>(new HashSet<>(Arrays.asList(labels)));

        for (String label : uniqueLabels) {
            double classCount = vocab.getOrDefault(label, 0);
            double probability = (classCount + alpha) / (total + alpha * uniqueLabels.size());
            classProbabilities.add(probability);
        }

        // Calculate Word Probabilities:
        this.wordProbabilities = new double[uniqueLabels.size()][vocab.size()];
        
        Map<String, Integer> labelToIndex = new HashMap<>();
        for (int i = 0; i < uniqueLabels.size(); i++) {
            labelToIndex.put(uniqueLabels.get(i), i);
        }

        // Iterate in 2D through encodedDoc to put the word count into wordProbabilities
        for (int i = 0; i < encodedDoc.length; i++) {
            int labelIndex = labelToIndex.get(labels[i]); 
            for (int j = 0; j < encodedDoc[i].length; j++) {
                // Check if 'j' is within the bounds of 'wordProbabilities[labelIndex]'.
                if (j < wordProbabilities[labelIndex].length) {
                    wordProbabilities[labelIndex][j] += encodedDoc[i][j];
                }
            }
            
        }

        // Apply Laplace Smoothing and Convert the Counts to Probabilities
        for (int i = 0; i < wordProbabilities.length; i++) { 
            // Find total word count for each class:
            double sum = Arrays.stream(wordProbabilities[i]).sum();
            // Iterate through each document's vocabulary representation:
            for (int j = 0; j < wordProbabilities[i].length; j++) {
                wordProbabilities[i][j] = (wordProbabilities[i][j] + alpha) / (sum + alpha * wordProbabilities[i].length);
            }
        }
        
        



    }
    

    // Getters
    public List<Double> getClassProbs() {
        return classProbabilities;
    }
    public double[][] getWordProbs() {
        return wordProbabilities;
    }

}
