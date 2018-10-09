package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"ConstantConditions", "unused"})
class Exercise2 {

    @Test
    void personHasNotEmptyLastNameAndFirstName() {
        Predicate<Person> validate = p-> !(p.getFirstName().isEmpty() || p.getLastName().isEmpty());

        assertThat(validate.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(validate.test(new Person("Николай", "", 30)), is(false));
        assertThat(validate.test(new Person("", "Мельников", 20)), is(false));
    }

    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate)
    {
        return pers -> !predicate.test(pers);
    }

   private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return pers -> left.test(pers) && right.test(pers);

    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = p->p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p->p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName =
                negateUsingLogicalOperator(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName =
                negateUsingLogicalOperator(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = andUsingLogicalOperator(
                personHasNotEmptyFirstName, personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }

    private <T> Predicate<T> negate(Predicate<T> predicate)
    {
        return (T t) -> !predicate.test(t);
    }

    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right)
    {
        return (T t) -> left.test(t) && right.test(t);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = p->p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p->p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = and(
                personHasNotEmptyFirstName, personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = p->p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p->p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName = personHasEmptyLastName.negate();

        // использовать Predicate.and
        Predicate<Person> personHasNotEmptyLastNameAndFirstName = personHasNotEmptyFirstName.and(
                personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }
}
