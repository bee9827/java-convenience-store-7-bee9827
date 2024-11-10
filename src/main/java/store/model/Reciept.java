package store.model;

import java.text.DecimalFormat;
import java.util.List;

public class Reciept {
    private DecimalFormat df = new DecimalFormat("###,###");
    List<OrderedInventory> orderedInventories;
    MemberShip memberShip;

    public Reciept(List<OrderedInventory> orderedInventories, MemberShip memberShip) {
        this.orderedInventories = orderedInventories;
        this.memberShip = memberShip;
    }

    public String make(){
        return makeTop().append(makeMiddle()).append(makeBottom()).toString();
    }

    private StringBuilder makeBottom() {
        StringBuilder bottomSB = new StringBuilder("====================================\n");
        Integer quantity = 0;
        Long price = 0L;
        Long discountPrice = 0L;

        for(OrderedInventory orderedInventory : orderedInventories){
            String name = orderedInventory.getName();
            quantity += orderedInventory.getQuantity();
            price += orderedInventory.getPrice();
            discountPrice += orderedInventory.getDiscountPrice();
        }
        bottomSB.append("총구매액\t\t").append(df.format(quantity)).append("\t").append(df.format(price)).append("\n");
        bottomSB.append("행사할인\t\t\t-").append(df.format(discountPrice)).append("\n");
        bottomSB.append("멤버십할인\t\t\t-").append(df.format(memberShip.getDiscountPrice())).append("\n");
        bottomSB.append("내실돈\t\t\t ").append(df.format(memberShip.getAfterDiscountPrice())).append("\n");

        return bottomSB;
    }

    public StringBuilder makeTop() {
        StringBuilder topSB = new StringBuilder("==============W 편의점================\n");
        for (OrderedInventory orderedInventory : orderedInventories) {
            String name = orderedInventory.getName();
            Integer quantity = orderedInventory.getQuantity();
            Long price = orderedInventory.getPrice();

            addProduct(name, quantity, price, topSB);
        }
        return topSB;
    }

    public StringBuilder makeMiddle() {
        StringBuilder middleSb = new StringBuilder("=============증\t정===============\n");
        for(OrderedInventory orderedInventory : orderedInventories){
            String name = orderedInventory.getName();
            Integer discountQuantity = orderedInventory.getDiscountQuantity();

            if(discountQuantity > 0){
                addPromotion(name, discountQuantity,middleSb);
            }
        }
        return middleSb;
    }

    public void addProduct(String name, Integer quantity, Long price, StringBuilder sb){
        sb.append(name)
                .append("\t\t")
                .append(df.format(quantity))
                .append("\t")
                .append(df.format(price))
                .append("\n");
    }

    public void addPromotion(String name, Integer quantity, StringBuilder sb){
        sb.append(name)
                .append("\t\t")
                .append(df.format(quantity))
                .append("\n");
    }

}
