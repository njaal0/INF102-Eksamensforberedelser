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

}

