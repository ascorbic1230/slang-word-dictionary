import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class AppController {
    private static Dictionary dict = new Dictionary();

    public static int selectMenu(int maxChoice) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.print("\nLua chon cua ban la: ");
        while (true) {
            choice = scanner.nextInt();
            if (choice < 0 || choice > maxChoice)
                continue;
            break;
        }
        return choice;
    }

    public static void mainMenuController() {
        int choice = -1;
        do {
            Utils.clearConsole();
            int maxChoice = AppView.mainMenuView();
            choice = AppController.selectMenu(maxChoice);
            switch (choice) {
                case 1 -> {
                    Utils.clearConsole();
                    String slangWord = AppView.searchSlangWordMeaningView().toUpperCase();
                    String definition = dict.findDefinitionOfSlangWord(slangWord);
                    if (definition == null)
                        System.out.println("\n=> Khong tim thay ket qua");
                    else {
                        dict.addSearchHistory(slangWord, definition);
                        System.out.println("\n=> " + slangWord + ": " + definition);
                    }
                    Utils.pauseConsole();
                }
                case 2 -> {
                    Utils.clearConsole();
                    String keyword = AppView.searchSlangWordView();
                    HashMap<String, String> slangWords = dict.findSlangWordsByKeyword(keyword);
                    if (slangWords.size() == 0)
                        System.out.println("\n=> Khong tim thay ket qua");
                    else {
                        System.out.println("\n=> Ket qua tim kiem:");
                        Set<String> keySet = slangWords.keySet();
                        int i = 1;
                        for (String slangWord : keySet) {
                            String definition = slangWords.get(slangWord);
                            dict.addSearchHistory(slangWord, definition);
                            System.out.println("\n" + i++ + ". " + slangWord + ": " + definition);
                        }
                    }
                    Utils.pauseConsole();
                }
                case 3 -> {
                    Utils.clearConsole();
                    AppView.searchHistoryView(dict.getSearchHistory());
                    System.out.println("\nTuy chon");
                    System.out.println("1. Xoa lich su tim kiem");
                    System.out.println("2. Tro lai");
                    int subChoice = selectMenu(2);
                    if (subChoice == 1)
                        dict.clearSearchHistory();
                }
                case 4 -> {
                    slangWordManagerMenuController();
                }
                case 5 -> {
                    Utils.clearConsole();
                    // TODO: clear demo and complete code here
                    HashMap<String, String> randomSlangWord = new HashMap<>();
                    randomSlangWord.put("+_+", "Dead man");
                    AppView.randomSlangWordView(randomSlangWord);

                    Utils.pauseConsole();
                }
                case 6 -> {
                    slangWordQuizMenuController();
                }
            }
        } while (choice != 0);
        dict.storeData();
        dict.writeSearchHistory();
    }

    public static void slangWordManagerMenuController() {
        int choice = -1;
        do {
            Utils.clearConsole();
            int maxChoice = AppView.slangWordManagerMenuView();
            choice = AppController.selectMenu(maxChoice);
            switch (choice) {
                case 1 -> {
                    Utils.clearConsole();
                    ArrayList<String> res = AppView.addSlangWordView();
                    if (dict.addNewSlangWord(res.get(0), res.get(1)))
                        System.out.println("\n=> Them slang word moi thanh cong");
                    else
                        System.out.println("\n=> Them slang word moi khong thanh cong");
                    Utils.pauseConsole();
                    choice = 0;
                }
                case 2 -> {
                    Utils.clearConsole();
                    String slangWord = AppView.updateSlangWordView();
                    String definition = dict.findDefinitionOfSlangWord(slangWord);
                    if (definition != null) {
                        System.out.println(slangWord + ": " + definition);
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("\nDinh nghia moi: ");
                        String newDefinition = scanner.nextLine();
                        if (dict.updateSlangWord(slangWord, newDefinition))
                            System.out.println("\n=> Sua slang word thanh cong");
                        else
                            System.out.println("\n=> Sua slang word khong thanh cong");
                    }
                    else
                        System.out.println("\n=> Slang word khong ton tai trong co so du lieu");
                    Utils.pauseConsole();
                    choice = 0;
                }
                case 3 -> {
                    Utils.clearConsole();
                    String slangWord = AppView.deleteSlangWordView();
                    if (dict.isSlangWordExist(slangWord)) {
                        String definition = dict.deleteSlangWord(slangWord);
                        if (definition != null) {
                            System.out.println("\n" + slangWord + ": " + definition);
                            System.out.println("\n=> Xoa slang word thanh cong");
                        }
                        else
                            System.out.println("\n=> Xoa slang word khong than cong");
                    }
                    else
                        System.out.println("\n=> Slang word khong ton tai trong co so du lieu");
                    Utils.pauseConsole();
                    choice = 0;
                }
                case 4 -> {
                    Utils.clearConsole();
                    boolean res = AppView.resetSlangWordListView();
                    // TODO: complete code here

                    Utils.pauseConsole();
                }
            }
        } while (choice != 0);
    }

    public static void slangWordQuizMenuController() {
        int choice = -1;
        do {
            Utils.clearConsole();
            int maxChoice = AppView.slangWordQuizMenuView();
            choice = selectMenu(maxChoice);
            switch (choice) {
                case 1 -> {
                    Utils.clearConsole();
                    System.out.println("Hello World 1");
                    Utils.pauseConsole();
                }
                case 2 -> {
                    Utils.clearConsole();
                    System.out.println("Hello World 2");
                    Utils.pauseConsole();
                }
            }
        } while(choice != 0);
    }
}
