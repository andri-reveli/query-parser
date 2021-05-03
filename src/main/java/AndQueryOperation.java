import java.util.stream.Collectors;

public class AndQueryOperation implements QueryOperation {
    @Override
    public Query query(Query first, Query second) {
        return new Query(
                first.database()
                     .stream()
                     .filter(list -> second.database().contains(list))
                     .collect(Collectors.toList())
        );
    }
}
