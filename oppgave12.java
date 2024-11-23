import java.util.ArrayList;
import java.util.HashSet;

public class oppgave12 {
        static int findLongestUnique3(ArrayList<Integer> numbers) { //O(n^2)
        int bestNumElements=1;
        
        //try all start values i
        for(int i=0; i<numbers.size(); i++) {
            int j=i;
            HashSet<Integer> inCurrentInterval = new HashSet<>();
            while(j<numbers.size() && !inCurrentInterval.contains(numbers.get(j))) {
                inCurrentInterval.add(numbers.get(j));
                j++;
            }

            int numElements = inCurrentInterval.size();
            if(numElements>bestNumElements) {
                bestNumElements = numElements;
            }
        }
        return bestNumElements;
    }

    static int findLongestUnique4(ArrayList<Integer> numbers) { //O(n)
        int bestNumElements=1;

        //contains all values where i <= index < j
        HashSet<Integer> inCurrentInterval = new HashSet<>();
        int i=0, j=0;
        
        while(j<numbers.size()) {
            if(!inCurrentInterval.contains(numbers.get(j))) {
                inCurrentInterval.add(numbers.get(j));
                j++;
            }
            else {
                inCurrentInterval.remove(numbers.get(i));
                i++;
            }
            int numElements = inCurrentInterval.size();
            if(numElements>bestNumElements) {
                bestNumElements = numElements;
            }
        }
        return bestNumElements;
    }
}
