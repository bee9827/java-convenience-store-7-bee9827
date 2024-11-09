package store;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Function;

public interface DataLoader<T> {
    List<T> loadData(String filePath, Function<String[], T> creator) throws FileNotFoundException;
}
