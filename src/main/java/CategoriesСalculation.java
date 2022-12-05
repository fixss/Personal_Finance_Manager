import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;


public class CategoriesСalculation {
    protected List<Store> basket = new ArrayList<>();

    public void addToBasket(BufferedReader in) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String inRead = in.readLine();
        Store store = gson.fromJson(inRead, Store.class);
        basket.add(store);
    }


    public Map loadFromTSV(File file) throws IOException {

        List<String[]> categories = new ArrayList<>();
        Map<String, Integer> postServer = new HashMap<>();
        categories = Files.lines(file.toPath())
                .map(line -> line.split("\t"))
                .collect(Collectors.toList());
        Map<String, String> resultsMap = new HashMap<>();
        for (String[] s : categories) {
            resultsMap.put(s[0], s[1]);
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
                    int sum = postServer.getOrDefault(resultsMap.get(index.title), 0);
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
