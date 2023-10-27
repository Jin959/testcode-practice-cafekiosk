package sample.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuavaLearningTest {

    @Test
    @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
    void partitionLearningTest1() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 3);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1, 2, 3), List.of(4, 5, 6)
                ));
    }

    @Test
    @DisplayName("원소의 개수가 지정한 원소개수로 딱 나누어 떨어지지 않을때는 앞에 있는 것을 우선하여 맞춰 나눈다.")
    void partitionLearningTest2() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 4);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1, 2, 3, 4), List.of(5, 6)
                ));
    }

    @Test
    @DisplayName("Multimap 은 하나의 키에 여러개의 값을 넣을 수 있다.")
    void multimapLearningTest1() {
        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        // when
        Collection<String> strings = multimap.get("커피");

        // then
        assertThat(strings).hasSize(3)
                .isEqualTo(List.of("아메리카노", "카페라떼", "카푸치노"));
    }

    @TestFactory
    @DisplayName("Multimap 삭제 기능 확인")
    Collection<DynamicTest> multimapLearningTest2() {
        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        return List.of(
                DynamicTest.dynamicTest("1개 value 삭제", () -> {
                    // when
                    multimap.remove("커피", "카푸치노");

                    //then
                    Collection<String> result = multimap.get("커피");
                    assertThat(result).hasSize(2)
                            .isEqualTo(List.of("아메리카노", "카페라떼"));
                }),
                DynamicTest.dynamicTest("1개 key 에 해당하는 value 일괄 삭제", () -> {
                    // when
                    multimap.removeAll("커피");

                    //then
                    Collection<String> result = multimap.get("커피");
                    assertThat(result).isEmpty();
                })
        );
    }

}
