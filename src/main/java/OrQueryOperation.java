import java.util.ArrayList;
import java.util.List;

public class OrQueryOperation implements QueryOperation {
    @Override
    public Query query(Query first, Query second) {
        List<List<String>> union = new ArrayList<>();

        first.database().forEach(list -> {
            if (!union.contains(list)) {
                union.add(list);
            }
        });
        second.database().forEach(list -> {
            if (!union.contains(list)) {
                union.add(list);
            }
        });
        return new Query(union);
    }
}
