import java.util.List;
import java.util.stream.Collectors;

public record Query(List<List<String>> database) {
    public Query query(String queryParameter) {
        return new Query(
                database.stream()
                        .filter(list -> list.contains(queryParameter))
                        .collect(Collectors.toList())
        );
    }
}
