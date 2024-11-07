package store;

public enum PromotionType {
    ONE_PLUS_ONE(1,1),
    TWO_PLUS_ONE(2,1);


    PromotionType(Integer buy, Integer get) {
        this.buy = buy;
        this.get = get;
    }

    private final Integer buy;
    private final Integer get;

    public Integer getBuy() {
        return buy;
    }

    public Integer getGet() {
        return get;
    }
}
