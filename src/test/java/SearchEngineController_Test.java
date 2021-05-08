import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchEngineController_Test {
    SearchEngineController searchEngine = new SearchEngineController();
    List<String> r1 = List.of("soup", "tomato", "cream", "salt");
    List<String> r2 = List.of("cake", "sugar", "eggs", "flour", "cocoa", "cream", "butter");
    List<String> r3 = List.of("bread", "butter", "salt");
    List<String> r4 = List.of("soup", "fish", "potato", "salt", "pepper");

    @Test
    public void test1() {
        var expected = List.of(r1, r4);

        assertEquals(searchEngine.startQuery("(((soup)))"), expected);
        assertEquals(searchEngine.startQuery("((soup))"), expected);
        assertEquals(searchEngine.startQuery("(soup)"), expected);
        assertEquals(searchEngine.startQuery("soup"), expected);
    }

    @Test
    public void test2() {
        var expected = List.of(r4);

        assertEquals(searchEngine.startQuery("(((soup&fish)))"), expected);
        assertEquals(searchEngine.startQuery("((soup&fish))"), expected);
        assertEquals(searchEngine.startQuery("(soup&fish)"), expected);
        assertEquals(searchEngine.startQuery("soup&fish"), expected);
    }

    @Test
    public void test3() {
        var expected = List.of(r1, r4, r3);

        assertEquals(searchEngine.startQuery("(((soup|salt)))"), expected);
        assertEquals(searchEngine.startQuery("((soup|salt))"), expected);
        assertEquals(searchEngine.startQuery("(soup|salt)"), expected);
        assertEquals(searchEngine.startQuery("soup|salt"), expected);
    }

    @Test
    public void test4() {
        var expected1 = List.of(r2, r4);
        var expected2 = List.of(r4, r2);

        assertEquals(searchEngine.startQuery("(((cake|(salt&fish))))"), expected1);
        assertEquals(searchEngine.startQuery("((cake|(salt&fish)))"), expected1);
        assertEquals(searchEngine.startQuery("(cake|(salt&fish))"), expected1);
        assertEquals(searchEngine.startQuery("cake|(salt&fish)"), expected1);

        assertEquals(searchEngine.startQuery("((((fish&salt)|cake)))"), expected2);
        assertEquals(searchEngine.startQuery("(((fish&salt)|cake))"), expected2);
        assertEquals(searchEngine.startQuery("((fish&salt)|cake)"), expected2);
        assertEquals(searchEngine.startQuery("(fish&salt)|cake"), expected2);
    }

    @Test
    public void test5() {
        var expected = List.of(r4);

        assertEquals(searchEngine.startQuery("((((cake|fish)&(salt&fish))))"), expected);
        assertEquals(searchEngine.startQuery("(((cake|fish)&(salt&fish)))"), expected);
        assertEquals(searchEngine.startQuery("((cake|fish)&(salt&fish))"), expected);
        assertEquals(searchEngine.startQuery("(cake|fish)&(salt&fish)"), expected);

        assertEquals(searchEngine.startQuery("((((salt&fish)&(fish|cake))))"), expected);
        assertEquals(searchEngine.startQuery("(((salt&fish)&(fish|cake)))"), expected);
        assertEquals(searchEngine.startQuery("((salt&fish)&(fish|cake))"), expected);
        assertEquals(searchEngine.startQuery("(salt&fish)&(fish|cake)"), expected);
    }

    @Test
    public void test6() {
        var expected1 = List.of(r1, r2, r4, r3);
        var expected2 = List.of(r2, r4, r3, r1);

        assertEquals(searchEngine.startQuery(
                "(((((cream|cake)&salt)|(fish|butter))))"), expected1)
        ;
        assertEquals(searchEngine.startQuery(
                "((((cream|cake)&salt)|(fish|butter)))"), expected1
        );
        assertEquals(searchEngine.startQuery(
                "(((cream|cake)&salt)|(fish|butter))"), expected1
        );
        assertEquals(searchEngine.startQuery(
                "((cream|cake)&salt)|(fish|butter)"), expected1
        );

        assertEquals(searchEngine.startQuery(
                "((((butter|fish)|(salt&(cream|cake)))))"), expected2
        );
        assertEquals(searchEngine.startQuery(
                "(((butter|fish)|(salt&(cream|cake))))"), expected2
        );
        assertEquals(searchEngine.startQuery(
                "((butter|fish)|(salt&(cream|cake)))"), expected2
        );
        assertEquals(searchEngine.startQuery(
                "(butter|fish)|(salt&(cream|cake))"), expected2
        );
    }

    @Test
    public void test7() {
        var expected = List.of(r4, r3);

        assertEquals(searchEngine.startQuery("((((butter|potato)&salt)))"), expected);
        assertEquals(searchEngine.startQuery("(((butter|potato)&salt))"), expected);
        assertEquals(searchEngine.startQuery("((butter|potato)&salt)"), expected);
        assertEquals(searchEngine.startQuery("(butter|potato)&salt"), expected);

        assertEquals(searchEngine.startQuery("(((salt&(potato|butter))))"), expected);
        assertEquals(searchEngine.startQuery("((salt&(potato|butter)))"), expected);
        assertEquals(searchEngine.startQuery("(salt&(potato|butter))"), expected);
        assertEquals(searchEngine.startQuery("salt&(potato|butter)"), expected);
    }

    @Test
    public void test8() {
        var expected = List.of(r3);

        assertEquals(searchEngine.startQuery("(((bread|bread)))"), expected);
        assertEquals(searchEngine.startQuery("((bread|bread))"), expected);
        assertEquals(searchEngine.startQuery("(bread|bread)"), expected);
        assertEquals(searchEngine.startQuery("bread|bread"), expected);

        assertEquals(searchEngine.startQuery("(((bread&bread)))"), expected);
        assertEquals(searchEngine.startQuery("((bread&bread))"), expected);
        assertEquals(searchEngine.startQuery("(bread&bread)"), expected);
        assertEquals(searchEngine.startQuery("bread&bread"), expected);
    }

    @Test
    public void test9() {
        var expected = List.of();

        assertEquals(searchEngine.startQuery("((()))"), expected);
        assertEquals(searchEngine.startQuery("(())"), expected);
        assertEquals(searchEngine.startQuery("()"), expected);
        assertEquals(searchEngine.startQuery(""), expected);
    }
}