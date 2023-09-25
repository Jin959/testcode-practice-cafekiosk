package sample.cafekiosk.unit.beverages;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();

        assertEquals(americano.getName(), "아메리카노");             // Junit 5
        assertThat(americano.getName()).isEqualTo("아메리카노"); // AssertJ를 얹어서 사용
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}