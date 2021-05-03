import java.util.*;

public class SearchEngineController {
    private final List<List<String>> database;

    public SearchEngineController() {
        database = new ArrayList<>();
        database.add(List.of("soup", "tomato", "cream", "salt"));
        database.add(List.of("cake", "sugar", "eggs", "flour", "cocoa", "cream", "butter"));
        database.add(List.of("bread", "butter", "salt"));
        database.add(List.of("soup", "fish", "potato", "salt", "pepper"));
    }

    public List<List<String>> startQuery(String inputQuery) {
        var queryList = parse(inputQuery);
        var result = getQueryResult(queryList, 0, queryList.size());

        return result;
    }

    private List<String> parse(String query) {
        ArrayList<String> out = new ArrayList<>();

        for (int i = 0; i < query.length(); ++i) {
            char letter = query.charAt(i);
            switch (letter) {
                case '|' -> out.add("|");
                case '&' -> out.add("&");
                case '(' -> out.add("(");
                case ')' -> out.add(")");
                default -> {
                    for (int j = i + 1; ; ++j) {
                        if (j == query.length()) {
                            out.add(query.substring(i, j));
                            i = j;
                            break;
                        }
                        if (!Character.isAlphabetic(query.charAt(j))) {
                            out.add(query.substring(i, j));
                            i = j - 1;
                            break;
                        }
                    }
                }
            }
        }
        return out;
    }

    private List<List<String>> getQueryResult(List<String> query, int start, int end) {
        List<Query> queryList = new ArrayList<>();
        List<QueryOperation> tokenList = new ArrayList<>();

        processQuery(query, start, end, queryList, tokenList);
        return mergeQueryResult(queryList, tokenList);
    }

    private void processQuery(
            List<String> query,
            int start,
            int end,
            List<Query> queryList,
            List<QueryOperation> tokenList
    ) {
        QueryOperation and = new AndQueryOperation();
        QueryOperation or = new OrQueryOperation();

        for (int i = start; i < end; ++i) {
            String el = query.get(i);
            switch (el) {
                case "|" -> tokenList.add(or);
                case "&" -> tokenList.add(and);
                case "(" -> {
                    int j = getClosingBracket(query, i + 1, end);
                    queryList.add(new Query(getQueryResult(query, i + 1, j)));
                    i = j;
                }
                case ")" -> {
                }
                default -> queryList.add(new Query(database).query(el));
            }
        }
    }

    private List<List<String>> mergeQueryResult(
            List<Query> queryList,
            List<QueryOperation> tokenList
    ) {
        if (queryList.size() == 0) {
            return database;
        }

        Query result = queryList.get(0);
        for (int i = 1; i < queryList.size(); ++i) {
            result = tokenList.get(i - 1).query(result, queryList.get(i));
        }
        return result.database();
    }

    private int getClosingBracket(List<String> query, int start, int end) {
        int delta = 1;

        for (int i = start; i < end; ++i) {
            if (query.get(i).equals("(")) {
                ++delta;
            }
            if (query.get(i).equals(")")) {
                --delta;
                if (delta == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
}
