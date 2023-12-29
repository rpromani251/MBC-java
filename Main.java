import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String[] document =  {
            "One Geek helps Two Geeks",
             "Two Geeks help Four Geeks", 
             "Each Geek hels many other Geeks at GeeksforGeeks."
        };
        CountVectorizer vectorizer = new CountVectorizer();
        vectorizer.fit(document);
        int[][] encodedDoc = vectorizer.transform(document);
        System.out.println(Arrays.deepToString(encodedDoc));

        String[] labels = new String[] {"one", "two", "one"};
        MBC mbc = new MBC(1, encodedDoc, labels);
        mbc.fit();

        System.out.println(Arrays.deepToString(mbc.getWordProbs()));
        System.out.println(mbc.getClassProbs());


    }


}