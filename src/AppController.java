import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AppController {
    private static Dictionary dict;

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
                    String slangWord = AppView.searchSlangWordMeaningView();
                    // TODO: complete code here
                    Utils.pauseConsole();
                }
                case 2 -> {
                    Utils.clearConsole();
                    String keyWord = AppView.searchSlangWordView();
                    // TODO: complete code here
                    Utils.pauseConsole();
                }
                case 3 -> {
                    Utils.clearConsole();
                    AppView.searchHistoryView(dict.getSearchHistory());
                    // TODO: complete code here
                    Utils.pauseConsole();
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
                    // TODO: complete code here
                    Utils.pauseConsole();
                }
                case 2 -> {
                    Utils.clearConsole();
                    String slangWord = AppView.updateSlangWordView();
                    // TODO: complete code here

                    Utils.pauseConsole();
                }
                case 3 -> {
                    Utils.clearConsole();
                    String slangWord = AppView.deleteSlangWordView();
                    // TODO: complete code here
                    Utils.pauseConsole();
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