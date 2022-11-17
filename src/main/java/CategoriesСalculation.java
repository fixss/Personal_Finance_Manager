import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;


public class CategoriesСalculation {
    private Map<String, Integer> postServer = new HashMap<>();
    protected List<Store> basket = new ArrayList<>();


    public Map loadFromTSV(File file) throws IOException {

        List<String[]> categories = new ArrayList<>();
        Map<String, Integer> postServer = new HashMap<>();
        categories = Files.lines(file.toPath())
                .map(line -> line.split("\t"))
                .collect(Collectors.toList());
        Map<String, String> resultsMap = new HashMap<String, String>();
        for (String[] s : categories) {
            resultsMap.put((String) s[0], (String) s[1]);
        }

        for (Store index : basket) {
            if (!resultsMap.containsKey(index.title)) {
                if (!postServer.containsKey("другое")) {
                    postServer.put("другое", index.sum);
                } else {
                    int sum = postServer.get("другое");
                    sum += index.sum;
                    postServer.put("другое", sum);
                }
            }
            if (resultsMap.containsKey(index.title)) {
                if (postServer.isEmpty()) {
                    postServer.put(resultsMap.get(index.title), index.sum);
                } else {
                    int sum = postServer.containsKey(resultsMap.get(index.title)) ? postServer.get(resultsMap.get(index.title)) : 0;
                    sum += index.sum;
                    postServer.put(resultsMap.get(index.title), sum);
                }
            }
        }
        String maxFinCategory = Collections.max(postServer.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();
        int maxFinSum = postServer.get(maxFinCategory);
        JSONObject jsonMaxSum = new JSONObject();
        jsonMaxSum.put("categories", maxFinCategory);
        jsonMaxSum.put("sum", maxFinSum);
        return jsonMaxSum;
    }
}
