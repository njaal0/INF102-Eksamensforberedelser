import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class NameComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName()); // Compare by name
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));

        // Sort using a custom comparator (name)
        Collections.sort(people, new NameComparator());

        System.out.println("Sorted by name (custom comparator):");
        for (Person person : people) {
            System.out.println(person);
        }
    }
}

