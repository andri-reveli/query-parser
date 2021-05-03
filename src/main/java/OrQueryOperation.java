import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrQueryOperation implements QueryOperation {
    @Override
    public Query query(Query first, Query second) {
        HashSet<List<String>> set1 = new HashSet<>(first.database());
        HashSet<List<String>> set2 = new HashSet<>(second.database());

        return new Query(
                Stream.concat(set1.stream(), set2.stream())
                      .distinct()
                      .collect(Collectors.toList())
        );
    }
}
