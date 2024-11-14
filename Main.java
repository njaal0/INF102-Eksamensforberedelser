import java.util.List;

public class Main {
    public static void main(String[] args) {
        RandomList randomList = new RandomList();

        System.out.println("Original List:");
        System.out.println(randomList.getList().subList(0, 10)); // Print first 10 elements for readability

        // Example: Quick Sort
        List<Integer> quickSorted = RandomListSorter.quickSort(randomList);
        System.out.println("\nSorted List (Quick Sort):");
        System.out.println(quickSorted.subList(0, 10)); // Print first 10 elements for readability

        // Selection Sort
        List<Integer> selectionSorted = RandomListSorter.selectionSort(randomList);
        System.out.println("\nSorted List (Selection Sort):");
        System.out.println(selectionSorted.subList(0, 10)); // Print first 10 elements for readability
    }
}
