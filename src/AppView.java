import java.util.*;

public class AppView {
    public static int mainMenuView() {

        System.out.println("======= Slang Word Dictionary ======\n");
        System.out.println("1. Tim nghia slang word.");
        System.out.println("2. Tim slang word theo dinh nghia.");
        System.out.println("3. Lich su tim kiem.");
        System.out.println("4. Quan ly danh sach slang word.");
        System.out.println("5. Slang word ngau nhien.");
        System.out.println("6. Slang word quiz.");
        System.out.println("0. Thoat.");

        return 6;
    }

    public static int slangWordManagerMenuView() {

        System.out.println("====== Quan ly danh sach slang word ======\n");
        System.out.println("1. Them slang word moi.");
        System.out.println("2. Chinh sua slang word.");
        System.out.println("3. Xoa slang word.");
        System.out.println("4. Reset danh sach slang words.");
        System.out.println("0. Tro lai.");

        return 4;
    }

    public static int slangWordQuizMenuView() {

        System.out.println("====== Slang word quiz ======\n");
        System.out.println("1. Tim nghia cua slang word.");
        System.out.println("2. Tim slang word theo dinh nghia.");
        System.out.println("0. Tro lai.");

        return 2;
    }

    public static String searchSlangWordMeaningView() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("====== Tim nghia slang word =====\n");
        System.out.print("Nhap tu can tim: ");

        return scanner.nextLine();
    }

    public static String searchSlangWordView() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("====== Tim slang word theo dinh nghia =====\n");
        System.out.print("Nhap tu khoa: ");

        return scanner.nextLine();
    }

    public static void searchHistoryView(HashMap<String, String> searchHistory) {
        System.out.println("====== Lich su tim kiem ======\n");
        if (searchHistory.size() == 0) {
            System.out.println("=> Lich su tim kiem trong");
            return;
        }
        Set<String> keySet = searchHistory.keySet();
        int i = 0;
        for (String slangWord : keySet) {
            System.out.println("" + ++i + ". " + slangWord + ": " + searchHistory.get(slangWord));
        }
    }

    public static ArrayList<String> addSlangWordView() {
        ArrayList<String> res = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("====== Them slang word moi =====\n");
        System.out.print("Slang word: ");
        String slangWord = scanner.nextLine().toUpperCase();
        res.add(slangWord);
        System.out.print("Dinh nghia: ");
        String definition = scanner.nextLine();
        res.add(definition);

        return res;
    }

    public static String updateSlangWordView() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("====== Chinh sua slang word =====\n");
        System.out.print("Slang word: ");

        return scanner.nextLine();
    }

    public static String deleteSlangWordView() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("====== Xoa slang word =====\n");
        System.out.print("Slang word: ");

        return scanner.nextLine();
    }

    public static boolean resetSlangWordListView() {
        System.out.println("====== Reset danh sach slang word ======");
        return AppController.deleteConfirmation("Ban co chac chan muon khoi phuc danh sach slang word goc?");
    }

    public static void randomSlangWordView(String slangWord, String definition) {
        System.out.println("====== Slang word ngau nhien ======\n");
        System.out.println(slangWord + ": " + definition);
    }

}
