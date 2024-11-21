import java.util.HashMap;

public class LongestUniqueSubarray {
    public static int findLongestUniqueSubarray(int[] numbers) {
        // HashMap to store the last index of each number
        HashMap<Integer, Integer> lastSeen = new HashMap<>();
        int maxLength = 0; // Maximum length of a unique subarray
        int start = 0; // Left boundary of the sliding window

        // Iterate through the array with the `end` pointer
        for (int end = 0; end < numbers.length; end++) {
            // If the number is already in the window, update the `start` pointer
            if (lastSeen.containsKey(numbers[end])) {
                start = Math.max(start, lastSeen.get(numbers[end]) + 1);
            }

            // Update the last seen index of the current number
            lastSeen.put(numbers[end], end);

            // Update the maximum length of the subarray
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        // Example input
        int[] numbers = {1, 4, 7, 3, 4, 5, 2, 1, 7};

        // Compute and print the result
        int result = findLongestUniqueSubarray(numbers);
        System.out.println("Length of the longest subarray with unique elements: " + result);
    }
}
