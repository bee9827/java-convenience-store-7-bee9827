package store.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class OutputView {
    public static String HEADER = "[ERROR] ";
    public void printlnError(String errorMessage){
        System.out.println(HEADER + errorMessage);
    }

    public void printlnString(String string){
        System.out.println(string);
    }

    public void printList(List<String> infos){
        System.out.println("안녕하세요. W편의점입니다.\n" +
                "현재 보유하고 있는 상품입니다.\n");
        for (String info : infos) {
            System.out.println("- " + info);
        }
        System.out.println();
    }
}
