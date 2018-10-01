package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings({"unused", "ConstantConditions"})
class Exercise4 {

    private static class LazyCollectionHelper<T, R> {

        private final List<T> list;
        private final Function<List<T>, List<R>> mapet;

//        private LazyCollectionHelper(List<T> list, Function<T, R> mapping){
//            this.list = list;
//            this.A = null;
//            //this.A = {listT -> ;//mapping ;
//        }
//
        private  LazyCollectionHelper(List<T> list, Function<List<T>, List<R>> mapet){
            this.list = list;
            this.mapet = mapet;//mapping ;
        }

        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {
            return new LazyCollectionHelper<> (list, x->x);
        }

        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            Function<List<R>, List<U>> nextMapet = listR-> {
                List<U> listU = new ArrayList<>();
                listR.forEach(r->listU.addAll(flatMapping.apply(r)));
                return listU;
            };
            return new LazyCollectionHelper<>(list, mapet.andThen(nextMapet));
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> mapping)
        {
            Function<List<R>, List<U>> nextMapet = listR-> {
                List<U> listU = new ArrayList<>();
                listR.forEach(r -> listU.add(mapping.apply(r)));
                return listU;
            };
            return new LazyCollectionHelper<>(list, mapet.andThen(nextMapet));
        }

        public List<R> force()
        {
            return mapet.apply(list);
        }
    }

    @Test
    void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = getEmployees();

        List<Integer> codes = LazyCollectionHelper.from(employees)
                                    .flatMap(Employee::getJobHistory)
                                    .map(JobHistoryEntry::getPosition)
                                    .flatMap(s->Arrays.asList(s.split("")))//   new ArrayList<Char>(s.toCharArray()))
                                    .map(s->(int)s.charAt(0))
                                    .force();
        assertThat(codes, Matchers.contains(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev").toArray()));
    }

    private static List<Integer> calcCodes(String...strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }

    private static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(
                        new Person("Иван", "Мельников", 30),
                        Arrays.asList(
                                new JobHistoryEntry(2, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Александр", "Дементьев", 28),
                        Arrays.asList(
                                new JobHistoryEntry(1, "tester", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Дмитрий", "Осинов", 40),
                        Arrays.asList(
                                new JobHistoryEntry(3, "QA", "yandex"),
                                new JobHistoryEntry(1, "QA", "mail.ru"),
                                new JobHistoryEntry(1, "dev", "mail.ru")
                        )),
                new Employee(
                        new Person("Анна", "Светличная", 21),
                        Collections.singletonList(
                                new JobHistoryEntry(1, "tester", "T-Systems")
                        )),
                new Employee(
                        new Person("Игорь", "Толмачёв", 50),
                        Arrays.asList(
                                new JobHistoryEntry(5, "tester", "EPAM"),
                                new JobHistoryEntry(6, "QA", "EPAM")
                        )),
                new Employee(
                        new Person("Иван", "Александров", 33),
                        Arrays.asList(
                                new JobHistoryEntry(2, "QA", "T-Systems"),
                                new JobHistoryEntry(3, "QA", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM")
                        ))
        );
    }

}
