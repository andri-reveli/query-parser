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

        assertEquals(expected, searchEngine.startQuery("(((soup)))"));
        assertEquals(expected, searchEngine.startQuery("((soup))"));
        assertEquals(expected, searchEngine.startQuery("(soup)"));
        assertEquals(expected, searchEngine.startQuery("soup"));
    }

    @Test
    public void test2() {
        var expected = List.of(r4);

        assertEquals(expected, searchEngine.startQuery("(((soup&fish)))"));
        assertEquals(expected, searchEngine.startQuery("((soup&fish))"));
        assertEquals(expected, searchEngine.startQuery("(soup&fish)"));
        assertEquals(expected, searchEngine.startQuery("soup&fish"));
    }

    @Test
    public void test3() {
        var expected = List.of(r1, r4, r3);

        assertEquals(expected, searchEngine.startQuery("(((soup|salt)))"));
        assertEquals(expected, searchEngine.startQuery("((soup|salt))"));
        assertEquals(expected, searchEngine.startQuery("(soup|salt)"));
        assertEquals(expected, searchEngine.startQuery("soup|salt"));
    }

    @Test
    public void test4() {
        var expected1 = List.of(r2, r4);
        var expected2 = List.of(r4, r2);

        assertEquals(expected1, searchEngine.startQuery("(((cake|(salt&fish))))"));
        assertEquals(expected1, searchEngine.startQuery("((cake|(salt&fish)))"));
        assertEquals(expected1, searchEngine.startQuery("(cake|(salt&fish))"));
        assertEquals(expected1, searchEngine.startQuery("cake|(salt&fish)"));

        assertEquals(expected2, searchEngine.startQuery("((((fish&salt)|cake)))"));
        assertEquals(expected2, searchEngine.startQuery("(((fish&salt)|cake))"));
        assertEquals(expected2, searchEngine.startQuery("((fish&salt)|cake)"));
        assertEquals(expected2, searchEngine.startQuery("(fish&salt)|cake"));
    }

    @Test
    public void test5() {
        var expected = List.of(r4);

        assertEquals(expected, searchEngine.startQuery("((((cake|fish)&(salt&fish))))"));
        assertEquals(expected, searchEngine.startQuery("(((cake|fish)&(salt&fish)))"));
        assertEquals(expected, searchEngine.startQuery("((cake|fish)&(salt&fish))"));
        assertEquals(expected, searchEngine.startQuery("(cake|fish)&(salt&fish)"));

        assertEquals(expected, searchEngine.startQuery("((((salt&fish)&(fish|cake))))"));
        assertEquals(expected, searchEngine.startQuery("(((salt&fish)&(fish|cake)))"));
        assertEquals(expected, searchEngine.startQuery("((salt&fish)&(fish|cake))"));
        assertEquals(expected, searchEngine.startQuery("(salt&fish)&(fish|cake)"));
    }

    @Test
    public void test6() {
        var expected1 = List.of(r1, r2, r4, r3);
        var expected2 = List.of(r2, r4, r3, r1);

        assertEquals(
                expected1,
                searchEngine.startQuery("(((((cream|cake)&salt)|(fish|butter))))")
        );
        assertEquals(
                expected1,
                searchEngine.startQuery("((((cream|cake)&salt)|(fish|butter)))")
        );
        assertEquals(
                expected1,
                searchEngine.startQuery("(((cream|cake)&salt)|(fish|butter))")
        );
        assertEquals(
                expected1,
                searchEngine.startQuery("((cream|cake)&salt)|(fish|butter)")
        );


        assertEquals(
                expected2,
                searchEngine.startQuery("((((butter|fish)|(salt&(cream|cake)))))")
        );
        assertEquals(
                expected2,
                searchEngine.startQuery("(((butter|fish)|(salt&(cream|cake))))")
        );
        assertEquals(
                expected2,
                searchEngine.startQuery("((butter|fish)|(salt&(cream|cake)))")
        );
        assertEquals(
                expected2,
                searchEngine.startQuery("(butter|fish)|(salt&(cream|cake))")
        );
    }

    @Test
    public void test7() {
        var expected = List.of(r4, r3);

        assertEquals(expected, searchEngine.startQuery("((((butter|potato)&salt)))"));
        assertEquals(expected, searchEngine.startQuery("(((butter|potato)&salt))"));
        assertEquals(expected, searchEngine.startQuery("((butter|potato)&salt)"));
        assertEquals(expected, searchEngine.startQuery("(butter|potato)&salt"));

        assertEquals(expected, searchEngine.startQuery("(((salt&(potato|butter))))"));
        assertEquals(expected, searchEngine.startQuery("((salt&(potato|butter)))"));
        assertEquals(expected, searchEngine.startQuery("(salt&(potato|butter))"));
        assertEquals(expected, searchEngine.startQuery("salt&(potato|butter)"));
    }

    @Test
    public void test8() {
        var expected = List.of(r3);

        assertEquals(expected, searchEngine.startQuery("(((bread|bread)))"));
        assertEquals(expected, searchEngine.startQuery("((bread|bread))"));
        assertEquals(expected, searchEngine.startQuery("(bread|bread)"));
        assertEquals(expected, searchEngine.startQuery("bread|bread"));

        assertEquals(expected, searchEngine.startQuery("(((bread&bread)))"));
        assertEquals(expected, searchEngine.startQuery("((bread&bread))"));
        assertEquals(expected, searchEngine.startQuery("(bread&bread)"));
        assertEquals(expected, searchEngine.startQuery("bread&bread"));
    }

    @Test
    public void test9() {
        var expected = List.of();

        assertEquals(expected, searchEngine.startQuery("((()))"));
        assertEquals(expected, searchEngine.startQuery("(())"));
        assertEquals(expected, searchEngine.startQuery("()"));
        assertEquals(expected, searchEngine.startQuery(""));
    }
}