import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int wordCount = 0;
        String current = "";
        String champion = "";
        String words = "";
        
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (!str.endsWith(" "))
                str += " ";
            words += str;
        }
        words = words.trim();

        for (int i = 0; i <= words.length(); ++i) {
            if (i == words.length() || words.charAt(i) == ' ') {
                wordCount++;
                if (StdRandom.bernoulli(1d / wordCount)) {
                    champion = current;
                }
                current = "";
            } else {
                current += words.charAt(i);
            }
        }
        StdOut.println(champion);
    }
}
