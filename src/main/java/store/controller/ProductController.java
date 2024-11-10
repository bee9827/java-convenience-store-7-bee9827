package store.controller;

import store.converter.ProductPromotionMaker;
import store.model.*;
import store.view.InputView;
import store.view.OutputView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductController {
    private final Pattern pattern = Pattern.compile("\\[([가-힣]*)-([0-9]+)]");
    private final InputView inputView;
    private final OutputView outputView;
    private final Inventory inventory;


    public ProductController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        inventory = new Inventory(new ProductPromotionMaker().makeProdcutPromotions());
    }


    public void run() {
        outputView.printList(new StockList(inventory).getStocks());
        List<OrderedInventory> orderedInventories = askOrders();
        MemberShip memberShip = askMembership(getTotalPrice(orderedInventories));
        outputView.printlnString(new Reciept(orderedInventories, memberShip).make());
        askContinue();
    }

    private Long getTotalPrice(List<OrderedInventory> orderedInventories) {
        return getNonPromotionPrice(orderedInventories) - getPromotionPrice(orderedInventories);
    }

    private Long getNonPromotionPrice(List<OrderedInventory> orderedInventories) {
        return orderedInventories.stream()
                .map(OrderedInventory::getPrice)
                .reduce(Long::sum)
                .get();
    }

    private Long getPromotionPrice(List<OrderedInventory> orderedInventories) {
        return orderedInventories.stream()
                .map(OrderedInventory::getDiscountPrice)
                .reduce(Long::sum)
                .get();
    }

    private List<OrderedInventory> askOrders() {
        try {
            return orders(inputView.readItem());
        } catch (IllegalArgumentException e) {
            outputView.printlnError(e.getMessage());
            return askOrders();
        }
    }

    private void askContinue() {
        try {
            if (inputView.readYesOrNo("감사합니다. 구매하고 싶은 다른 상품이 있나요?")) {
                run();
            }
        } catch (IllegalArgumentException e) {
            outputView.printlnError(e.getMessage());
        }
    }

    private List<OrderedInventory> orders(String input) {
        Matcher matcher = pattern.matcher(input);

        List<OrderedInventory> orderedInventories = new ArrayList<>();
        while (matcher.find()) {
            orderedInventories.add(order(matcher.group(1), Integer.parseInt(matcher.group(2))));
        }
        return orderedInventories;
    }

    private OrderedInventory order(String name, Integer quantity) {
        inventory.validate(name,quantity);
        Integer promotionNotAppliedQuantity = inventory.getPromotionOutOfStock(name, quantity); //재고없어서 못받은 개수 체크하여 구매할건지 ask
        if (promotionNotAppliedQuantity != 0) {
            return askPromotionNotAppliedAndGetItems(name, quantity, promotionNotAppliedQuantity);
        }
        if (inventory.checkPromotionApplicableQuantity(name, quantity + 1)) { //프로모션 적용가능하면 1개 더 받을건지 ask
            return askPromotionApplyAndGetItems(name, quantity);
        }
        return inventory.order(name, quantity);
    }

    private OrderedInventory askPromotionApplyAndGetItems(String name, Integer quantity) {
        try {
            if (inputView.readYesOrNo("현재 " + name + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까?"))
                return inventory.order(name, quantity + 1);
            return inventory.order(name, quantity);
        } catch (IllegalArgumentException e) {
            outputView.printlnError(e.getMessage());
            return askPromotionApplyAndGetItems(name, quantity);
        }
    }

    private OrderedInventory askPromotionNotAppliedAndGetItems(String name, Integer quantity, Integer promotionNotAppliedQuantity) {
        try {
            if (inputView.readYesOrNo(String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?",name,promotionNotAppliedQuantity)))
                return inventory.order(name, quantity);
            return inventory.order(name, quantity - promotionNotAppliedQuantity);
        } catch (IllegalArgumentException e) {
            outputView.printlnError(e.getMessage());
            return askPromotionNotAppliedAndGetItems(name, quantity, promotionNotAppliedQuantity);
        }
    }

    private MemberShip askMembership(Long price) {
        try {
            return new MemberShip(inputView.readYesOrNo("멤버십 할인을 받으시겠습니까?"), price);
        } catch (IllegalArgumentException e) {
            outputView.printlnError(e.getMessage());
            return askMembership(price);
        }
    }

}
