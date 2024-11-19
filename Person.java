import java.util.ArrayList;
import java.util.Collections;

class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Define natural ordering (sort by age)
    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age); // Compare by age
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

abstract class ComparableExample {
    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));

        // Sort using natural ordering (age)
        Collections.sort(people);

        System.out.println("Sorted by age (natural ordering):");
        for (Person person : people) {
            System.out.println(person);
        }
    }
}

