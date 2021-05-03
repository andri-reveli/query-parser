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
        assertEquals(searchEngine.startQuery("(((soup)))"), List.of(r1, r4));
        assertEquals(searchEngine.startQuery("((soup))"), List.of(r1, r4));
        assertEquals(searchEngine.startQuery("(soup)"), List.of(r1, r4));
        assertEquals(searchEngine.startQuery("soup"), List.of(r1, r4));
    }

    @Test
    public void test2() {
        assertEquals(searchEngine.startQuery("(((soup&fish)))"), List.of(r4));
        assertEquals(searchEngine.startQuery("((soup&fish))"), List.of(r4));
        assertEquals(searchEngine.startQuery("(soup&fish)"), List.of(r4));
        assertEquals(searchEngine.startQuery("soup&fish"), List.of(r4));
    }

    @Test
    public void test3() {
        assertEquals(searchEngine.startQuery("(((soup|salt)))"), List.of(r1, r4, r3));
        assertEquals(searchEngine.startQuery("((soup|salt))"), List.of(r1, r4, r3));
        assertEquals(searchEngine.startQuery("(soup|salt)"), List.of(r1, r4, r3));
        assertEquals(searchEngine.startQuery("soup|salt"), List.of(r1, r4, r3));
    }

    @Test
    public void test4() {
        assertEquals(searchEngine.startQuery(
                "(((cake|(salt&fish))))"), List.of(r2, r4)
        );
        assertEquals(searchEngine.startQuery(
                "((cake|(salt&fish)))"), List.of(r2, r4)
        );
        assertEquals(searchEngine.startQuery(
                "(cake|(salt&fish))"), List.of(r2, r4)
        );
        assertEquals(searchEngine.startQuery(
                "cake|(salt&fish)"), List.of(r2, r4)
        );
    }
}