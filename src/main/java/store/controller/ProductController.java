package store.controller;

import store.converter.ProductPromotionMaker;
import store.model.*;
import store.view.InputView;
import store.view.OutputView;
import store.view.Reciept;
import store.view.StockList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductController {
    private final Pattern pattern = Pattern.compile("^\\[([가-힣A-z]+)-([0-9]+)]$");
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

        // TODO
        // 잘된입력, 잘못된 입력 순서로 들어오면 되돌려야하지만(Transaction)
        // 안된다. 하.... 언제하지
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
            return;
        } catch (IllegalArgumentException e) {
            outputView.printlnError(e.getMessage());
            askContinue();
        }
    }

    private List<OrderedInventory> orders(String input) {
        List<OrderedInventory> orderedInventories = new ArrayList<>();
        List<String> names = getNameList(input);
        List<Integer> quantities = getQuantityList(input);
        validateInput(names, quantities);
        for (int i = 0; i < names.size(); i++) {
            orderedInventories.add(order(names.get(i), quantities.get(i)));
        }
        return orderedInventories;
    }

    private void validateInput(List<String> names, List<Integer> quantities) {
        for (int i = 0; i < names.size(); i++) {
            inventory.validate(names.get(i), quantities.get(i));
        }
    }

    private List<String> getNameList(String input) {
        String[] productsAndQuantities = input.split(",");
        List<String> names = new ArrayList<>();
        for (String prodcutAndQuantity : productsAndQuantities) {
            Matcher matcher = pattern.matcher(prodcutAndQuantity);
            if (matcher.matches()) {
                names.add(matcher.group(1));
            }
        }
        if(productsAndQuantities.length != names.size()) throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
        return names;
    }

    private List<Integer> getQuantityList(String input) {
        String[] productsAndQuantities = input.split(",");
        List<Integer> quantities = new ArrayList<>();
        for (String prodcutAndQuantity : productsAndQuantities) {
            Matcher matcher = pattern.matcher(prodcutAndQuantity);
            if (matcher.matches()) {
                quantities.add(Integer.parseInt(matcher.group(2)));
            }
        }
        if(productsAndQuantities.length != quantities.size()) throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
        return quantities;
    }

    private OrderedInventory order(String name, Integer quantity) {
        inventory.validate(name, quantity);
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
            if (inputView.readYesOrNo(String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?", name, promotionNotAppliedQuantity)))
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
