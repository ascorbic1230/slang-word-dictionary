import java.util.*;

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

    public static int selectAnswer() {
        String[] ans = { "A", "B", "C", "D"};
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nDap an cua ban la: ");
        while (true) {
            String input = scanner.nextLine().toUpperCase();
            for (int i = 0; i < ans.length; i++)
                if (input.equals(ans[i]))
                    return i;
        }
    }

    public static boolean deleteConfirmation(String statement) {
        System.out.println("\n" + statement);
        System.out.println("1. Co");
        System.out.println("2. Khong");
        int choice = selectMenu(2);
        return choice == 1;
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
                    if (subChoice == 1) {
                        if (deleteConfirmation("Ban co chac chan muon xoa hay khong?"))
                            dict.clearSearchHistory();
                    }
                }
                case 4 -> {
                    slangWordManagerMenuController();
                }
                case 5 -> {
                    Utils.clearConsole();
                    String slangWord = dict.randomSlangWord();
                    String definition = dict.findDefinitionOfSlangWord(slangWord);
                    dict.addSearchHistory(slangWord, definition);
                    AppView.randomSlangWordView(slangWord, definition);
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
                        String definition = dict.findDefinitionOfSlangWord(slangWord);
                        System.out.println("\n" + slangWord + ": " + definition);
                        if (deleteConfirmation("Ban co chac chan muon xoa hay khong?")) {
                            dict.deleteSlangWord(slangWord);
                            System.out.println("\n=> Xoa slang word thanh cong");
                        }
                        else
                            System.out.println("\n=> Xoa slang word khong thanh cong");
                    }
                    else
                        System.out.println("\n=> Slang word khong ton tai trong co so du lieu");
                    Utils.pauseConsole();
                    choice = 0;
                }
                case 4 -> {
                    Utils.clearConsole();
                    boolean bool = AppView.resetSlangWordListView();
                    if (bool) {
                        dict.resetDatabase();
                        System.out.println("\n=> Reset danh sach slang words goc thanh cong");
                    }
                    else
                        System.out.println("\n=> Reset danh sach slang words goc khong thanh cong");
                    Utils.pauseConsole();
                    choice = 0;
                }
            }
        } while (choice != 0);
    }

    public static void slangWordQuizMenuController() {
        int choice = -1;
        HashMap<String, String> randomSlangWords = dict.random4SlangWords();
        do {
            Utils.clearConsole();
            int maxChoice = AppView.slangWordQuizMenuView();
            choice = selectMenu(maxChoice);
            switch (choice) {
                case 1 -> {
                    Utils.clearConsole();
                    // Hien thi giao dien
                    AppView.slangWordQuiz1View();
                    // Khoi tao bien
                    ArrayList<String> answers = new ArrayList<>();
                    Random random = new Random();
                    int randomNum = random.nextInt(4);
                    ArrayList<String> slangWords = new ArrayList<>(randomSlangWords.keySet());
                    String question = slangWords.get(randomNum);
                    for (int i = 0; i < 4; i++) {
                        answers.add(randomSlangWords.get(slangWords.get(i)));
                    }
                    // In cau hoi
                    System.out.println("\t" + question);
                    // In cau tra loi
                    AppView.answersView(answers);
                    // Doc cau tra loi
                    int ans = selectAnswer();
                    // Kiem tra cau tra loi
                    if (ans == randomNum)
                        System.out.println("\n=> Chuc mung ban da tra loi chinh xac");
                    else {
                        System.out.println("\n=> Cau tra loi cua ban chua chinh xac");
                        System.out.println("\nDap an cua cau hoi la " + question + ": " + randomSlangWords.get(question));
                    }
                    Utils.pauseConsole();
                    choice = 0;
                }
                case 2 -> {
                    Utils.clearConsole();
                    // Hien thi giao dien
                    AppView.slangWordQuiz2View();
                    // Khoi tao bien
                    Random random = new Random();
                    int randomNum = random.nextInt(4);
                    ArrayList<String> slangWords = new ArrayList<>(randomSlangWords.keySet()); // Danh sach slang words cung la danh sach dap an
                    String question = randomSlangWords.get(slangWords.get(randomNum));
                    // In cau hoi
                    System.out.println("\t" + question);
                    // In cau tra loi
                    AppView.answersView(slangWords);
                    // Doc cau tra loi
                    int ans = selectAnswer();
                    // Kiem tra cau tra loi
                    if (ans == randomNum)
                        System.out.println("\n=> Chuc mung ban da tra loi chinh xac");
                    else {
                        System.out.println("\n=> Cau tra loi cua ban chua chinh xac");
                        System.out.println("\nDap an cua cau hoi la " + slangWords.get(randomNum) + ": " + question);
                    }
                    Utils.pauseConsole();
                    choice = 0;
                }
            }
        } while(choice != 0);
    }
}
