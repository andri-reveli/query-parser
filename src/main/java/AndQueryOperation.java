import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AndQueryOperation implements QueryOperation {
    @Override
    public Query query(Query first, Query second) {
        HashSet<List<String>> set1 = new HashSet<>(first.database());
        HashSet<List<String>> set2 = new HashSet<>(second.database());

        return new Query(
                set1.stream()
                    .filter(set2::contains)
                    .collect(Collectors.toList())
        );
    }
}
