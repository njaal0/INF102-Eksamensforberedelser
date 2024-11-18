
public class MyGeniusGuesser implements IGuesser {

    @Override
    public int findNumber(RandomNumber number) {
        int l = number.getLowerbound();
        int r = number.getUpperbound();
        
        while (l <= r) {
            int mid = l + (r - l) / 2;

            int guessResult = number.guess(mid);

            if (guessResult == 0) {
                return mid;
            } 
            else if (guessResult == -1) {
                l = mid + 1;
            }  
            else {
                r = mid - 1;
            }   
        }
        throw new IllegalArgumentException("Number to guess is not within bounds.");
    }
}
