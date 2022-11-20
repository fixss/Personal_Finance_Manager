import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CategoriesCalculationTest {

    final String textJsonOther = "{\"title\": \"тапочки\", \"date\": \"2022.02.08\", \"sum\": 500}";
    final String textJsonCategory = "{\"title\": \"мыло\", \"date\": \"2022.02.08\", \"sum\": 100}";
    CategoriesСalculation categoriesСalculation = new CategoriesСalculation();
    Gson gsonTest = new Gson();

    @Mock
    private Main mainServer;

    @BeforeEach
    void setUp() {
        mainServer = Mockito.mock(Main.class);
    }

    @Test
    public void testLoadOtherFromTSV() throws IOException {
        Store storeTest = gsonTest.fromJson(textJsonOther, Store.class);
        categoriesСalculation.basket.add(storeTest);
        Map actualResult = (categoriesСalculation.loadFromTSV(new File("categories.tsv")));
        JSONObject expectedResult = new JSONObject();
        expectedResult.put("categories", "другое");
        expectedResult.put("sum", 500);
        System.out.println(expectedResult.get("categories"));
        System.out.println(actualResult.get("categories"));
        Assertions.assertEquals(expectedResult.get("categories"), actualResult.get("categories"));
    }

    @Test
    public void testLoadCategoryFromTSV() throws IOException {
        Store basketTestCategory = gsonTest.fromJson(textJsonCategory, Store.class);
        categoriesСalculation.basket.add(basketTestCategory);
        Map actualResult = (categoriesСalculation.loadFromTSV(new File("categories.tsv")));
        JSONObject expectedResult = new JSONObject();
        expectedResult.put("categories", "быт");
        expectedResult.put("sum", 100);
        System.out.println(expectedResult.get("sum"));
        System.out.println(actualResult.get("sum"));
        Assertions.assertEquals(expectedResult.get("sum"), actualResult.get("sum"));
    }
}
