package lambda.part1.exercise;

import com.google.common.collect.FluentIterable;
import com.google.common.base.Predicate;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.arrayContaining;

//
class Exercise1 {

    @Test
    void sortPersonsByAgeUsingArraysSortLocalComparator() {
        Person[] persons = getPersons();

        // TODO use Arrays.sort
        class PersonAgeComparator implements Comparator<Person> {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getAge() - p2.getAge();
            }
        }

        Arrays.sort(persons, new PersonAgeComparator());

        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new Person("Николай", "Зимов", 30),
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    void sortPersonsByAgeUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        // TODO use Arrays.sort

        Arrays.sort
                (persons, new Comparator<Person>() {
                            @Override
                            public int compare(Person p1, Person p2) {
                                return p1.getAge() - p2.getAge();
                            }
                        }
                );
        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new

                        Person("Николай", "Зимов", 30),
                new

                        Person("Алексей", "Доренко", 40),
                new

                        Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    void sortPersonsByLastNameThenFirstNameUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        // TODO use Arrays.sort

        Arrays.sort
                (persons, new Comparator<Person>() {
                            @Override
                            public int compare(Person p1, Person p2) {
                                int ln =  p1.getLastName().compareTo(p2.getLastName());
                                if (!(ln==0)) return ln; else
                                    return p1.getFirstName().compareTo(p2.getFirstName());

                            }
                        }
                );

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }

    @Test
    void findFirstWithAge30UsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        // TODO use FluentIterable
        Predicate<Person> personHasAge30 = p -> p.getAge()==30;
        Person person = FluentIterable.from(persons).firstMatch(personHasAge30).get();

        assertThat(person, is(new Person("Николай", "Зимов", 30)));
    }

    @Test
    void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        // TODO use FluentIterable

        Person person = FluentIterable.from(persons).firstMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input.getAge()==30;
            }
        }).get();

        assertThat(person, is(new Person("Николай", "Зимов", 30)));
    }

    private Person[] getPersons() {
        return new Person[]{
                new Person("Иван", "Мельников", 20),
                new Person("Алексей", "Доренко", 40),
                new Person("Николай", "Зимов", 30),
                new Person("Артем", "Зимов", 45)
        };
    }
}
