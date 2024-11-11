package store.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class FileDataLoader<T> implements DataLoader<T> {
    public static String SEPARATOR = ",";
    public List<T> loadData(String filePath, Function<String[], T> creator) throws FileNotFoundException {
        List<T> list = new ArrayList<>();
        Scanner sc = new Scanner(new File(filePath));
        sc.nextLine();  //헤더
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(SEPARATOR);
            list.add(creator.apply(tokens));
        }
        sc.close(); // Scanner 닫기
        return list;
    }
}
