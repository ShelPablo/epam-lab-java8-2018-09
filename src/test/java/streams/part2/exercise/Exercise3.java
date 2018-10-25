package streams.part2.exercise;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"unused", "ConstantConditions"})
class Exercise3 {

    @Test
    void createLimitedStringWithOddNumbersSeparatedBySpaces() {
        int countNumbers = 10;

        String result = Stream.iterate(1, i->i+2).limit(10).map(Object::toString).collect(Collectors.joining(" "));

        assertThat(result, is("1 3 5 7 9 11 13 15 17 19"));
    }
    @Test
    void extractEvenNumberedCharactersToNewString() {
        String source = "abcdefghijklm";
        String result = Stream.iterate(0, i->i+2).limit(7).map(i->String.valueOf(source.charAt(i))).collect(Collectors.joining());

        assertThat(result, is("acegikm"));
    }
}
