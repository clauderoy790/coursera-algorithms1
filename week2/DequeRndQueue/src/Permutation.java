import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		 RandomizedQueue<String> qu = new RandomizedQueue<>();
		 int nb = Integer.parseInt(args[0]);
		 
		 while (!StdIn.isEmpty()) {
			 String str = StdIn.readString();
			 qu.enqueue(str);
		 }
		 
		 for (int i = 0; i< nb;++i)
		 {
			 if (!qu.isEmpty()) {
				 String deq = qu.dequeue();
				 StdOut.println(deq);
			 }
		 }
	}
}
