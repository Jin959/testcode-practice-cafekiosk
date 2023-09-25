package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverages.Americano;
import sample.cafekiosk.unit.beverages.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.beverages.size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    /**
     * 한 종류의 음료를 여러 잔 담는 요구사항에 대한 경계값 테스트
     * 2개일 때가 경계
     * 일단 대충 여러 잔을 담을때, 같은 인스턴스를 사용한다고 하자.
     */
    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        // 같은 인스턴스가 두번 추가 되었는가?
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
    }

    /**
     * 한 종류의 음료를 여러 잔 담는 요구사항에 대한 예외 테스트
     * CafeKiosk 클래스에서 예외를 던지는 것으로 구현하기로 했었다.
     */
    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 한잔 이상 주문 하셔야 합니다.");   // 한술 더뜨면 메세지 내용까지 검증 할 수 있다.
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    /**
     * TDD 맛보기
     *
     * 테스트를 먼저 작성한다.
     */
    @Test
    void calculateTotalPrice() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        int totalPrice = cafeKiosk.calculateTotalPrice();

        assertThat(totalPrice).isEqualTo(8500);
    }

    /**
     * 가게 운영 시간(10:00~22:00) 외에는 주문을 생성할 수 없다는 요구사항이 발생
     *
     * 항상 성공하는 테스트가 아니다.
     * 이 테스트는 우리가 설정한 가게 영업시간에 테스트를 할 때만 성공하는 테스트이다.
     */
    @Test
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    /**
     * 가게 운영 시간(10:00~22:00) 외에는 주문을 생성할 수 없다는 요구사항의 경계값 테스트
     *
     * 프로덕션 구현내용을 조금 바꿔서 createOrder() 와 다르게 원하는 시간에 성공적인 테스트를 수행할 수 있다.
     */
    @Test
    void createOrderWithOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        // 경계값 10시
        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023, 9, 25, 10, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    /**
     * 가게 운영 시간(10:00~22:00) 외에는 주문을 생성할 수 없다는 요구사항의 예외 테스트
     */
    @Test
    void createOrderWithOutSideOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 9, 25, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}