package store.domain;

public enum PromotionType {
    ONE_PLUS_ONE(1,1),
    TWO_PLUS_ONE(2,1),
    NONE(1,0);

    private final Integer buy;
    private final Integer get;


    PromotionType(Integer buy, Integer get) {
        this.buy = buy;
        this.get = get;
    }

    public static PromotionType of(Integer buy, Integer get) {
        for (PromotionType type : PromotionType.values()) {
            if (type.getBuy().equals(buy) && type.getGet().equals(get)) {
                return type;
            }
        }
        return null;
    }

    public Integer getBuy() {
        return buy;
    }

    public Integer getGet() {
        return get;
    }
}
