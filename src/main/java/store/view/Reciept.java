package store.view;

import store.model.MemberShip;
import store.model.OrderedInventory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Reciept {
    private DecimalFormat df = new DecimalFormat("###,###");
    private List<OrderedInventory> orderedInventories;
    private MemberShip memberShip;

    public Reciept(){
        orderedInventories = new ArrayList<OrderedInventory>();
    }
    public Reciept(List<OrderedInventory> orderedInventories, MemberShip memberShip) {
        this.orderedInventories = orderedInventories;
        this.memberShip = memberShip;
    }

    public String make(List<OrderedInventory> orderedInventory, MemberShip memberShip) {
        this.orderedInventories = orderedInventory;
        this.memberShip = memberShip;
        return makeTop().append(makeMiddle()).append(makeBottom()).toString();
    }

    private StringBuilder makeTop() {
        StringBuilder topSB = new StringBuilder("===============W 편의점===============\n");
        topSB.append(format("상품","수량","금액"));
        for (OrderedInventory orderedInventory : orderedInventories) {
            addProduct(orderedInventory.getName(), orderedInventory.getQuantity(), orderedInventory.getPrice(), topSB);
        }
        return topSB;
    }

    private StringBuilder makeMiddle() {
        StringBuilder middleSb = new StringBuilder("===============증   정===============\n");

        for (OrderedInventory orderedInventory : orderedInventories) {
            Integer discountQuantity = orderedInventory.getDiscountQuantity();
            if (discountQuantity > 0) {
                addPromotion(orderedInventory.getName(), discountQuantity, middleSb);
            }
        }
        return middleSb;
    }

    private StringBuilder makeBottom() {
        StringBuilder bottomSB = new StringBuilder("====================================\n");
        bottomSB.append(format("총구매액", getTotalQuantity(), getTotalPrice()));
        bottomSB.append(format("행사할인", getTotalDiscountPrice()));
        bottomSB.append(format("멤버십할인", df.format(memberShip.getDiscountPrice())));
        bottomSB.append(format("내실돈", df.format(memberShip.getAfterDiscountPrice())));

        return bottomSB;
    }

    private String format(String a, String b) {
        return String.format("%-15s%16s\n", a, b);
    }

    private String format(String a, String b, String c) {
        return String.format("%-15s%-3s%13s\n", a, b, c);
    }

    private String getTotalPrice() {
        return df.format(
                orderedInventories.stream()
                        .map(OrderedInventory::getPrice)
                        .reduce(Long::sum)
                        .orElse(0L)
        );
    }

    private String getTotalQuantity() {
        return df.format(
                orderedInventories.stream()
                        .map(OrderedInventory::getQuantity)
                        .reduce(Integer::sum)
                        .orElse(0)
        );
    }

    private String getTotalDiscountPrice() {
        return df.format(
                orderedInventories.stream()
                        .map(OrderedInventory::getDiscountPrice)
                        .reduce(Long::sum)
                        .orElse(0L)
        );
    }

    private void addProduct(String name, Integer quantity, Long price, StringBuilder sb) {
        sb.append(format(name, df.format(quantity), df.format(price)));
    }

    private void addPromotion(String name, Integer quantity, StringBuilder sb) {
        sb.append(String.format("%-15s%-3s\n",name, df.format(quantity)));
    }

}
