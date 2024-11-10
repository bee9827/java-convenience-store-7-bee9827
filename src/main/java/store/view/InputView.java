package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public final static String PROMOTION_OUT_OF_STOCK_INSTRUCTION = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?  (Y/N)";
    public final static String PROMOTION_APPLICATION_INSTRUCTION = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public final static String MEMBERSHIP_INSTRUCTION = "멤버십 할인을 받으시겠습니까? (Y/N)";



    public String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public boolean readYesOrNo(String instruction){
        System.out.println(STR."\{instruction} (Y/N)");
        return check(Console.readLine());
    }

    private boolean check(String input){
        if(input.equals("Y")) return true;
        if(input.equals("N")) return false;
        throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
    }



}
