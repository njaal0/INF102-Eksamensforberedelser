import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomList {
    private List<Integer> list;

    public RandomList() {
        list = new ArrayList<>(100);
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            list.add(random.nextInt(0, 1000));
        }
    }

    public List<Integer> getList() {
        return list;
    }

}

