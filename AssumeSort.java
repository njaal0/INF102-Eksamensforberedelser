import java.util.ArrayList;

public class AssumeSort {
    
    public static ArrayList<Integer> assumeSort(ArrayList<Integer> list) {
        if (list.get(0) < list.get(1)) {
            return list; // List must definitely already be sorted
        }
        else {
            return null; // This is too hard
        }
    }
}
