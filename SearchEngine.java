package search;
import java.util.*;
import java.io.*;
 
public class SearchEngine {
    private ArrayList<String> data = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);
    private Map<String, ArrayList<Integer>> map = new HashMap<>();
 
    private void searchStrategy() {
        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        String strategy = scan.nextLine();
        System.out.println("\nEnter a name or email to search all suitable people.");
        String dataPerson = scan.nextLine().toLowerCase();
        List<List<Integer>> lists = searchPerson(dataPerson);
        List<Integer> list = new ArrayList<>();
        switch(strategy) {
            case "ALL":
                list = allStrategy(lists);
                break;
            case  "ANY":
                list = anyStrategy(lists);
                break;
            case "NONE":
                list = noneStrategy(lists);
        }
        System.out.println("\n" + list.size() + " persons found:");
        list.forEach(pos -> System.out.println(data.get(pos)));
    }
 
    private List<Integer> allStrategy(List<List<Integer>> lists) {
        Set<Integer> set = new HashSet<>();
        set.addAll(lists.get(0));
        lists.forEach(list -> set.retainAll(list));
        List<Integer> list = new ArrayList<>();
        set.forEach(num -> list.add(num));
        return list;
    }
 
    private List<Integer> anyStrategy(List<List<Integer>> lists) {
        Set<Integer> set = new  HashSet<>();
        lists.forEach(list -> set.addAll(list));
        List<Integer> list = new ArrayList<>();
        set.forEach(num -> list.add(num));
        return list;
    }
 
    private List<Integer> noneStrategy(List<List<Integer>> lists) {
        Set<Integer> set = new  HashSet<>();
        lists.forEach(list -> set.addAll(list));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(i);
        }
        list.removeAll(set);
        return list;
    }
 
    private List<List<Integer>> searchPerson(String data) {
        List<List<Integer>> lists = new ArrayList<>();
        for (String dates : data.split("\\s")){
            lists.add(map.getOrDefault(dates, new ArrayList<>()));
        }
        return lists;
    }
 
    private void printAll() {
        System.out.println("\n=== List of people ===");
        for (String person : data) {
            System.out.println(person);
        }
    }
 
    private void getAllPeople(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            Scanner scanFis = new Scanner(fis);
            int i = 0;
            while (scanFis.hasNextLine()) {
                String person = scanFis.nextLine();
                data.add(person);
                for  (String personData : person.split("\\s")) {
                    ArrayList<Integer> list = map.getOrDefault(personData.toLowerCase(), new ArrayList<>());
                    list.add(i);
                    map.put(personData.toLowerCase(), list);
                }
                i++;
            }
            scanFis.close();
        } catch (Exception e) {
 
        }
    }
 
    private String getArg(String[] args, String argName, String defaultValue) {
        for (int i = 0; i < args.length; i =+ 2) {
            if (args[i].equals(argName)) {
                return args[++i];
            }
        }
        return  defaultValue;
    }
 
    public void start(String[] args) {
        String dataName = getArg(args, "--data", "");
        getAllPeople(dataName);
        boolean active = true;
        do {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");
            int choise = Integer.parseInt(scan.nextLine());
            switch (choise) {
                case 1:
                    searchStrategy();
                    break;
                case 2:
                    printAll();
                    break;
                case 0:
                    active = false;
                    break;
                default:
                    System.out.println("\nIncorrect option! Try again.");
                    break;
            }
        } while (active);
        System.out.println("\nBye!");
    }
}
