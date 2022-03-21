import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class Dictionary {
    private final HashMap<String, String> data = new HashMap<>();
    private final HashMap<String, String> searchHistory = new HashMap<>();

    private final String originalDatabaseFileName = "slang.txt";
    private final String databaseFileName = "data.bin";
    private final String searchHistoryFileName = "history.bin";
    private final String SPLIT_CHARACTER = "`";

    public Dictionary() {
        loadData();
    }

    public String findDefinitionOfSlangWord(String slangWord) {
        return data.get(slangWord.toUpperCase());
    }

    public HashMap<String, String> findSlangWordsByKeyword(String keyword) {
        HashMap<String, String> slangWords = new HashMap<>();

        Set<String> keySet = data.keySet();
        for (String slangWord : keySet) {
            String definition = data.get(slangWord).toLowerCase(Locale.ROOT);
            if (definition.contains(keyword.toLowerCase(Locale.ROOT)))
                slangWords.put(slangWord, definition);
        }

        return slangWords;
    }

    public void addSearchHistory(String slangWord, String definition) {
        if (definition != null)
            searchHistory.put(slangWord, definition);
    }

    public void clearSearchHistory() {
        searchHistory.clear();
    }

    public boolean addNewSlangWord(String slangWord, String definition) {
        if (data.get(slangWord.toUpperCase()) != null)
            return false;
        data.put(slangWord.toUpperCase(), definition);
        return true;
    }

    public boolean updateSlangWord(String slangWord, String definition) {
        if (data.get(slangWord) == null)
            return false;
        data.put(slangWord, definition);
        return true;
    }

    public String deleteSlangWord(String slangWord) {
        return data.remove(slangWord.toUpperCase());
    }

    public boolean isSlangWordExist(String slangWord) {
        return data.get(slangWord.toUpperCase()) != null;
    }


    public void loadData() {
        File dataFile = new File(databaseFileName);
        if (dataFile.isFile())
            readDatabase();
        else
            readOriginalDatabase();
        readSearchHistory();
    }

    public void storeData() {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(databaseFileName));

            int size = data.size();
            byte[] bytes = ByteBuffer.allocate(4).putInt(size).array();
            bos.write(bytes);

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

    public void readDatabase() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(databaseFileName));
            data.clear();

            byte[] sizeBytesArray = new byte[4];
            bis.read(sizeBytesArray);
            int size = ByteBuffer.wrap(sizeBytesArray).getInt();

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

    public void readOriginalDatabase() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(originalDatabaseFileName));

            data.clear();

            Path path = Paths.get(originalDatabaseFileName);
            long size = Files.lines(path).count();

            br.readLine();

            for (int i = 0; i < size - 1; i++) {
                String line = br.readLine();
                String[] parts = line.split(SPLIT_CHARACTER);

                data.put(parts[0], parts[1]);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSearchHistory() {
        try {
            File file = new File(searchHistoryFileName);
            if (!file.isFile())
                return;

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(searchHistoryFileName));
            searchHistory.clear();

            byte[] bytes = new byte[4];
            bis.read(bytes);
            int size = ByteBuffer.wrap(bytes).getInt();

            for (int i = 0; i < size; i++) {
                int keywordSize = bis.read();
                byte[] keywordBytesArray = new byte[keywordSize];
                bis.read(keywordBytesArray);
                String keyword = new String(keywordBytesArray);

                int definitionSize = bis.read();
                byte[] definitionBytesArray = new byte[definitionSize];
                bis.read(definitionBytesArray);
                String definition = new String(definitionBytesArray);

                searchHistory.put(keyword, definition);
            }

            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSearchHistory() {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(searchHistoryFileName));

            int size = searchHistory.size();
            byte[] bytes = ByteBuffer.allocate(4).putInt(size).array();
            bos.write(bytes);

            Set<String> keySet = searchHistory.keySet();
            for (String slangWord : keySet) {
                int slangWordSize = slangWord.length();
                bos.write(slangWordSize);
                byte[] slangWordBytesArray = slangWord.getBytes();
                bos.write(slangWordBytesArray);

                String definition = searchHistory.get(slangWord);
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

    public HashMap<String, String> getSearchHistory() {
        return searchHistory;
    }
}
