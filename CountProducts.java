import java.util.HashMap;

public class CountProducts {
    
    public static int countProducts(int[] a) { // O(n^2)
        int total = 0;

        HashMap<Integer, Integer> numCount = new HashMap<>();

        for (int i = 0; i < a.length; i++) {
            numCount.put(a[i], i);
        }

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                int product = a[i] * a[j];

                if (numCount.containsKey(product)) {
                    total++;
                }
            }
        }

        return total;
    }

    public static void main(String[] args) {
        int[] list = {1, 4, 9, 3, 8, 5, 2, 6, 7, 7};

        System.out.println(countProducts(list));
    }
}
