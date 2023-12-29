import java.util.*;
import java.util.stream.Collectors;

public class CountVectorizer {

    // Instance Variables:
    private HashMap<String, Integer> vocabulary;
    private String[] vocabKeys;
    private int[][] encodedDoc;

    // Constructor to initialize the vocabulary HashMap
    public CountVectorizer() {
        vocabulary = new HashMap<>();
    }

    // Process Document and Create Vocabulary | Precondition: Document does not have characters such as , . ! ? etc.
    public void fit(String[] document) {

        // Traverse document by line
        for (String line : document) {

            // Create a list containing words split at each space; all words are lowercase
            List<String> lineList =  Arrays.asList(line.split(" "));
            lineList = lineList.stream().map(String::toLowerCase).collect(Collectors.toList());

            //  For each string in list, either put the word into the vocabulary, or increment its value:
            for (String str : lineList) {
                if (vocabulary.containsKey(str)) {
                    vocabulary.put(str, vocabulary.get(str)+1);
                }
                else {
                    vocabulary.put(str, 1);
                }
            }
        }
        // 
        vocabKeys = vocabulary.keySet().toArray(new String[vocabulary.keySet().size()]);
    }

    // Encode the Document
    public int[][] transform(String[] document) {

        // Create 2d Array of Shape (document.length, vocabulary.keys.length)
        encodedDoc = new int[document.length][vocabKeys.length];

        // For document[i] i --> [0, n]
        for (int i = 0; i < document.length; i++) {
            // Split the document into words
            String[] words = document[i].toLowerCase().split(" ");

            // Iterate over each word
            for (String word : words) {
                // Find the index of word in vocabKeys
                int index = Arrays.asList(vocabKeys).indexOf(word);
                if (index != -1) {
                    // Increment the count in encodedDoc
                    encodedDoc[i][index]++;
                }
            }
        }
        return encodedDoc;
    }

    public static void main(String[] args) {
        String[] document = {"One Geek helps Two Geeks", "Two Geeks help Four Geeks", "Each Geek helps many other Geeks at GeeksforGeeks"};
        CountVectorizer vectorizer = new CountVectorizer();
        vectorizer.fit(document);

        System.out.println(vectorizer.vocabulary);

        int[][] vector = vectorizer.transform(document);
        System.out.println(Arrays.deepToString(vector));
    }


}