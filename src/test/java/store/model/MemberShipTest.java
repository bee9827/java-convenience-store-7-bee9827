package store.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberShipTest {

    @Test
    void getDiscountPrice() {
        assertThat(MemberShip.getDiscountPrice(0L)).isEqualTo(0);
        assertThat(MemberShip.getAfterDiscountPrice(0L)).isEqualTo(0);
        assertThat(MemberShip.getDiscountPrice(2000L)).isEqualTo(600);
        assertThat(MemberShip.getDiscountPrice(10_000_000_000L)).isEqualTo(8000);
    }

}