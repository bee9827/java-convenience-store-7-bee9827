package store.domain;


import java.time.LocalDate;
import java.time.LocalDateTime;


public class Promotion {
    private final String name;
    private final PromotionType type;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, PromotionType type, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean checkQuantity(int quantity) {
        return quantity % (type.getBuy() + type.getGet()) == type.getBuy();
    }

    public boolean checkDateTime(LocalDateTime dateTime) {
        return dateTime.isAfter(startDate.atStartOfDay())
                && dateTime.isBefore(endDate.plusDays(1).atStartOfDay());
    }

    public String getName() {
        return name;
    }

    public PromotionType getType() {
        return type;
    }

    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() {
        return endDate;
    }

}
