import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomListSorter {

    // Selection Sort
    public static List<Integer> selectionSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j) < list.get(minIndex)) {
                    minIndex = j;
                }
            }
            Collections.swap(list, i, minIndex);
        }
        return list;
    }

    // Insertion Sort
    public static List<Integer> insertionSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        int n = list.size();
        for (int i = 1; i < n; i++) {
            int key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
        return list;
    }

    // Bubble Sort
    public static List<Integer> bubbleSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }
        return list;
    }

    // Shell Sort
    public static List<Integer> shellSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        int n = list.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = list.get(i);
                int j;
                for (j = i; j >= gap && list.get(j - gap) > temp; j -= gap) {
                    list.set(j, list.get(j - gap));
                }
                list.set(j, temp);
            }
        }
        return list;
    }

    // Quick Sort
    public static List<Integer> quickSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        quickSortHelper(list, 0, list.size() - 1);
        return list;
    }

    private static void quickSortHelper(List<Integer> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSortHelper(list, low, pivotIndex - 1);
            quickSortHelper(list, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j) < pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    // Merge Sort
    public static List<Integer> mergeSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        mergeSortHelper(list, 0, list.size() - 1);
        return list;
    }

    private static void mergeSortHelper(List<Integer> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(list, left, mid);
            mergeSortHelper(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }

    private static void merge(List<Integer> list, int left, int mid, int right) {
        List<Integer> temp = new ArrayList<>(list);
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (temp.get(i) <= temp.get(j)) {
                list.set(k++, temp.get(i++));
            } else {
                list.set(k++, temp.get(j++));
            }
        }

        while (i <= mid) list.set(k++, temp.get(i++));
        while (j <= right) list.set(k++, temp.get(j++));
    }

    // Bucket Sort
    public static List<Integer> bucketSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        int max = Collections.max(list);
        int n = list.size();
        List<List<Integer>> buckets = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int value : list) {
            int bucketIndex = (n * value) / (max + 1);
            buckets.get(bucketIndex).add(value);
        }

        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int value : bucket) {
                list.set(index++, value);
            }
        }
        return list;
    }

    // Radix Sort
    public static List<Integer> radixSort(RandomList randomList) {
        List<Integer> list = randomList.getList();
        int max = Collections.max(list);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(list, exp);
        }
        return list;
    }

    private static void countingSort(List<Integer> list, int exp) {
        int n = list.size();
        int[] output = new int[n];
        int[] count = new int[10];

        for (int value : list) {
            count[(value / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int value = list.get(i);
            output[count[(value / exp) % 10] - 1] = value;
            count[(value / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {
            list.set(i, output[i]);
        }
    }
}


