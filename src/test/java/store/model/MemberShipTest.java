package store.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberShipTest {

    @Test
    void getDiscountPrice() {
        MemberShip memberShip = new MemberShip(true,1000L);
        assertThat(memberShip.getDiscountPrice()).isEqualTo(300);
    }

}