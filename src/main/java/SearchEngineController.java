import java.util.*;
import java.util.stream.Collectors;

public class SearchEngineController {
    private List<List<String>> database;
    private List<String> commands;
    private List<Character> tokens;

    public SearchEngineController() {
        database = new ArrayList<>();
        database.add(List.of("soup", "tomato", "cream", "salt"));
        database.add(List.of("cake", "sugar", "eggs", "flour", "cocoa", "cream", "butter"));
        database.add(List.of("bread", "butter", "salt"));
        database.add(List.of("soup", "fish", "potato", "salt", "pepper"));
    }

    public List<List<String>> startQuery(String inputQuery) {
        var result = getQueryResult(parse(inputQuery));

//        for (var list : result) {
//            for (var el : list) {
//                System.out.print(el + " ");
//            }
//            System.out.println();
//        }
        return result;
    }

    private List<List<String>> getQueryResult(List<String> query) {
        return __getQueryResult(query, 0, query.size());
    }

    private List<List<String>> __getQueryResult(List<String> query, int start, int end) {
        List<Query> queryList = new ArrayList<>();
        List<QueryOperation> tokenList = new ArrayList<>();
        QueryOperation and = new AndQueryOperation();
        QueryOperation or = new OrQueryOperation();

        for (int i = start; i < end; ++i) {
            String el = query.get(i);
            switch (el) {
                case "|" -> tokenList.add(or);
                case "&" -> tokenList.add(and);
                case "(" -> {
                    int j = getClosingBracket(query, i + 1, end);
                    queryList.add(new Query(__getQueryResult(query, i + 1, j)));
                    i = j;
                }
                case ")" -> {}
                default -> queryList.add(new Query(database).query(el));
            }
        }

        if (queryList.size() == 0) {
            return database;
        }
        Query result = queryList.get(0);
        for (int i = 1; i < queryList.size(); ++i) {
            result = tokenList.get(i - 1).query(result, queryList.get(i));
        }
        return result.database();
    }

    private List<List<String>> reduce(
            String command1,
            char token,
            String command2,
            List<List<String>> list
    ) {
        if (token == '|') {
            return list.stream()
                       .filter(el -> el.contains(command1) || el.contains(command2))
                       .collect(Collectors.toList());
        }
        return list.stream()
                   .filter(el -> el.contains(command1) && el.contains(command2))
                   .collect(Collectors.toList());
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
