import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
    private final HashMap<String, String> data = new HashMap<>();
    private final ArrayList<String> searchHistory = new ArrayList<>();
    private HashMap<String, String> slangWordOfDay = null;


    public HashMap<String, String> getData() {
        return data;
    }

    public ArrayList<String> getSearchHistory() {
        return searchHistory;
    }
}
