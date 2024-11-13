import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomList {
    private List<Integer> list;

    public RandomList() {
        list = new ArrayList<>(1000000);
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            list.add(random.nextInt());
        }
    }

    public List<Integer> getList() {
        return list;
    }

    public void quickSort() {
        quickSort(0, list.size() - 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (list.get(j) < pivot) {
                i++;
                int temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        int temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }
}

