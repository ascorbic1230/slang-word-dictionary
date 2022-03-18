import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Dictionary {
    private final HashMap<String, String> data = new HashMap<>();
    private final ArrayList<String> searchHistory = new ArrayList<>();

    private final String originalDatabaseFileName = "slang.txt";
    private final String databaseFileName = "data.bin";
    private final String searchHistoryFileName = "history.bin";
    private final String SPLIT_CHARACTER = "`";

    public Dictionary() {
        loadData();
    }

    void loadData() {
        File dataFile = new File(databaseFileName);
        if (dataFile.isFile())
            readDatabase();
        else
            readOriginalDatabase();
        readSearchHistory();
    }

    void storeData() {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(databaseFileName));

            int size = data.size();
            bos.write(size);

            Set<String> slangWords = data.keySet();
            for (String slangWord : slangWords) {
                int slangWordSize = slangWord.length();
                bos.write(slangWordSize);
                byte[] sw = slangWord.getBytes();
                bos.write(sw);

                String definition = data.get(slangWord);
                int definitionSize = definition.length();
                bos.write(definitionSize);
                byte[] d = definition.getBytes();
                bos.write(d);
            }

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readDatabase() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(databaseFileName));

            int size = bis.read();

            for (int i = 0; i < size; i++) {
                int slangWordSize = bis.read();
                byte[] slangWordBytesArray = new byte[slangWordSize];
                bis.read(slangWordBytesArray);
                String slangWord = new String(slangWordBytesArray);

                int definitionSize = bis.read();
                byte[] definitionBytesArray = new byte[definitionSize];
                bis.read(definitionBytesArray);
                String definition = new String(definitionBytesArray);

                data.put(slangWord, definition);
            }

            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readOriginalDatabase() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(originalDatabaseFileName));

            data.clear();

            Path path = Paths.get(originalDatabaseFileName);
            long size = Files.lines(path).count();

            for (int i = 0; i < size; i++) {
                String line = br.readLine();
                String[] parts = line.split(SPLIT_CHARACTER);

                data.put(parts[0], parts[1]);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readSearchHistory() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(searchHistoryFileName));
            searchHistory.clear();
            int size = bis.read();
            for (int i = 0; i < size; i++) {
                int keywordSize = bis.read();
                byte[] keywordBytesArray = new byte[keywordSize];
                bis.read(keywordBytesArray);
                String keyword = new String(keywordBytesArray);

                searchHistory.add(keyword);
            }

            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeSearchHistory() {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(searchHistoryFileName));

            int size = searchHistory.size();
            bos.write(size);

            for (String keyword : searchHistory) {
                int keywordSize = keyword.length();
                bos.write(keywordSize);
                byte[] keywordBytesArray = keyword.getBytes();
                bos.write(keywordBytesArray);
            }

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public ArrayList<String> getSearchHistory() {
        return searchHistory;
    }
}
