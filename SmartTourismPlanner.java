import java.io.*;
import java.util.*;

class Place {
    String name, taluk, category, season, crowd;
    int fee, visitors;

    Place(String name, String taluk, String category,
          String season, String crowd, int fee, int visitors) {
        this.name = name;
        this.taluk = taluk;
        this.category = category;
        this.season = season;
        this.crowd = crowd;
        this.fee = fee;
        this.visitors = visitors;
    }
}

public class SmartTourismPlanner {

    static List<Place> places = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        loadDataFromCSV("uttara_kannada_tourism_data.csv");

        while (true) {
            System.out.println("\n========================================");
            System.out.println("|| SMART UTTARA KANNADA TOURISM PLANNER ||");
            System.out.println("========================================");
            System.out.println("1. Sort places by popularity");
            System.out.println("2. Plan trip within budget (Greedy)");
            System.out.println("3. Show Top-K popular places");
            System.out.println("4. Group places by Taluk");
            System.out.println("5. Search place by name");
            System.out.println("6. Binary search by entry fee");
            System.out.println("7. Search places by filters");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> sortByPopularity();
                case 2 -> greedyBudget();
                case 3 -> topKPlaces();
                case 4 -> groupByTaluk();
                case 5 -> searchByName();
                case 6 -> binarySearchByFee();
                case 7 -> filterSearch();
                case 0 -> {
                    System.out.println("\nThank you for using Smart Uttara Kannada Tourism Planner !");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= CSV LOADER =================
    static void loadDataFromCSV(String fileName) {
        places.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                String name = d[0];
                String taluk = d[1];
                String category = d[2];
                String season = d[3];
                int visitors = Integer.parseInt(d[4]);
                String crowd = d[5];
                int fee = Integer.parseInt(d[6]);

                places.add(new Place(name, taluk, category, season, crowd, fee, visitors));
            }
            System.out.println("✔ Data loaded from CSV successfully");

        } catch (Exception e) {
            System.out.println("404! Error reading CSV file");
            e.printStackTrace();
        }
    }

    // ================= OPTION 1 =================
    static void sortByPopularity() {
        places.sort((a, b) -> b.visitors - a.visitors);
        System.out.println("\nPlaces Sorted by Popularity:");
        for (Place p : places) {
            System.out.println(p.name + " | Visitors: " + p.visitors);
        }
    }

    // ================= OPTION 2 =================
    static void greedyBudget() {
        System.out.print("Enter your total budget (Rs): ");
        int budget = sc.nextInt();

        places.sort(Comparator.comparingInt(p -> p.fee));
        int spent = 0;

        System.out.println("\nSelected Places using Greedy Strategy:");
        for (Place p : places) {
            if (spent + p.fee <= budget) {
                spent += p.fee;
                System.out.println(">" + p.name + " (" + p.fee + " Rs)");
            }
        }
        System.out.println("Total Cost: Rs" + spent);
    }

    // ================= OPTION 3 =================
    static void topKPlaces() {
        System.out.print("Enter value of K: ");
        int k = sc.nextInt();

        places.sort((a, b) -> b.visitors - a.visitors);

        System.out.println("\nTop " + k + " Popular Places:");
        for (int i = 0; i < k && i < places.size(); i++) {
            Place p = places.get(i);
            System.out.println("Rank " + (i + 1) + ": " + p.name +
                    " (" + p.visitors + " visitors)");
        }
    }

    // ================= OPTION 4 =================
    static void groupByTaluk() {
        Map<String, List<Place>> map = new HashMap<>();

        for (Place p : places) {
            map.computeIfAbsent(p.taluk, k -> new ArrayList<>()).add(p);
        }

        System.out.println("\nPlaces Grouped by Taluk:");
        for (String taluk : map.keySet()) {
            System.out.println("\n" + taluk + ":");
            for (Place p : map.get(taluk)) {
                System.out.println(" - " + p.name);
            }
        }
    }

    // ================= OPTION 5 =================
    static void searchByName() {
        sc.nextLine(); // clear buffer
        System.out.print("Enter place name: ");
        String name = sc.nextLine();

        for (Place p : places) {
            if (p.name.equalsIgnoreCase(name)) {
                System.out.println("\n Place Found!");
                printPlace(p);
                return;
            }
        }
        System.out.println("404! Place not found!");
    }

    // ================= OPTION 6 =================
    static void binarySearchByFee() {
        System.out.print("Enter entry fee to search (Rs): ");
        int target = sc.nextInt();

        places.sort(Comparator.comparingInt(p -> p.fee));

        int low = 0, high = places.size() - 1;
        boolean found = false;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (places.get(mid).fee == target) {
                System.out.println("> " + places.get(mid).name);
                found = true;
                break;
            } else if (places.get(mid).fee < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (!found)
            System.out.println("No place found with entry fee Rs" + target);
    }

    // ================= OPTION 7 =================
    static void filterSearch() {

        System.out.println("\nSelect Season:");
        System.out.println("1. SUMMER  2. MONSOON  3. WINTER");
        int s = sc.nextInt();
        String season = (s == 1) ? "Summer" : (s == 2) ? "Monsoon" : "Winter";

        System.out.println("\nSelect Category:");
        System.out.println("1. BEACH 2. TEMPLE 3. WATERFALL 4. ADVENTURE 5. HERITAGE");
        int c = sc.nextInt();
        String category = switch (c) {
            case 1 -> "Beach";
            case 2 -> "Temple";
            case 3 -> "Waterfall";
            case 4 -> "Adventure";
            default -> "Heritage";
        };

        System.out.print("\nEnter maximum entry fee (Rs): ");
        int maxFee = sc.nextInt();

        System.out.println("\nPreferred Crowd Level:");
        System.out.println("1. LOW 2. MEDIUM 3. HIGH");
        int cr = sc.nextInt();
        String crowd = (cr == 1) ? "Low" : (cr == 2) ? "Medium" : "High";

        List<Place> result = new ArrayList<>();

        for (Place p : places) {
            if ((p.season.equalsIgnoreCase(season) || p.season.equalsIgnoreCase("All"))
                    && p.category.equalsIgnoreCase(category)
                    && p.fee <= maxFee
                    && p.crowd.equalsIgnoreCase(crowd)) {
                result.add(p);
            }
        }

        result.sort((a, b) -> b.visitors - a.visitors);

        System.out.println("\nRecommended Places for You");
        System.out.println("(based on season, interest, budget & crowd):");

        if (result.isEmpty()) {
            System.out.println("No matching places found.");
            return;
        }

        int rank = 1;
        for (Place p : result) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("Rank " + rank++);
            printPlace(p);
        }
        System.out.println("\n--------------------------------------------------");
    }

    // ================= COMMON PRINT =================
    static void printPlace(Place p) {
        System.out.println("> " + p.name);
        System.out.println("   Taluk        : " + p.taluk);
        System.out.println("   Category     : " + p.category);
        System.out.println("   Best Season  : " + p.season);
        System.out.println("   Crowd Level  : " + p.crowd);
        System.out.println("   Entry Fee    : Rs" + p.fee);
        System.out.println("   Popularity   : " + p.visitors + " visitors/year");
    }
}
