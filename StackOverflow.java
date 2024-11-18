public class StackOverflow {

    public static int stackOverflow(int n) {
        if (n == 1) {
            return 1;
        }
        for (int i = 0; i < n; i++) {
            System.out.println(i);
        }

        return stackOverflow(n - 1) + stackOverflow(n - 1);
    }
    
    public static void main(String[] args) {
        
        stackOverflow(100);
    }
}
