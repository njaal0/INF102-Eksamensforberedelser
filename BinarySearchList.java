import java.util.*;

class BinarySearchList {
    public static int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // Avoid overflow

            if (list.get(mid) == target) {
                return mid; // Target found
            }

            if (list.get(mid) < target) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }

        return -1; // Target not found
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 5, 7, 9, 11); // Example sorted list
        int target = 7;

        int result = binarySearch(list, target);
        if (result != -1) {
            System.out.println("Target found at index: " + result);
        } else {
            System.out.println("Target not found.");
        }
    }
}
