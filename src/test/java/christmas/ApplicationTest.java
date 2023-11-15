package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void 모든_타이틀_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "<주문 메뉴>",
                    "<할인 전 총주문 금액>",
                    "<증정 메뉴>",
                    "<혜택 내역>",
                    "<총혜택 금액>",
                    "<할인 후 예상 결제 금액>",
                    "<12월 이벤트 배지>"
            );
        });
    }

    @Test
    void 혜택_내역_없음_출력() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });
    }
    @DisplayName("주문 금액이 1,0000원 이하일 때 혜택 내역은 없다.")
    @Test
    void 혜택_내역_없음_출력2() {
        assertSimpleTest(() -> {
            run("23", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });
    }

    @Test
    void 날짜_예외_테스트() {
        assertSimpleTest(() -> {
            runException("a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("날짜 대신 공백을 입력하면 예외가 발생한다.")
    @Test
    void 날짜_예외_테스트2() {
        assertSimpleTest(() -> {
            runException(" ");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("1~31 사이에 입력되지 않은 날짜는 예외 처리 된다.")
    @Test
    void 날짜_예외_테스트3() {
        assertSimpleTest(() -> {
            runException("40");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 주문_예외_테스트() {
        assertSimpleTest(() -> {
            runException("3", "제로콜라-a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }
    @DisplayName("메뉴판에 없는 메뉴를 입력하면 예외 처리 된다.")
    @Test
    void 주문_예외_테스트2() {
        assertSimpleTest(() -> {
            runException("3", "연어초밥-5");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }
    @DisplayName("메뉴명을 입력하지 않은 경우 예외 처리 된다.")
    @Test
    void 주문_예외_테스트3() {
        assertSimpleTest(() -> {
            runException("3", "-8");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }
    @DisplayName("메뉴 개수를 입력하지 않은 경우 예외 처리 된다.")
    @Test
    void 주문_예외_테스트4() {
        assertSimpleTest(() -> {
            runException("3", "타파스-");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }
    @DisplayName("메뉴간 구분자에 쉼표를 사용하지 않으면 예외 처리가 발생한다.")
    @Test
    void 주문_예외_테스트5() {
        assertSimpleTest(() -> {
            runException("3", "제로콜라-1/타파스-2");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("메뉴 이름과 개수 간 구분자에 -를 사용하지 않으면 예외 처리가 발생한다.")
    @Test
    void 주문_예외_테스트6() {
        assertSimpleTest(() -> {
            runException("3", "제로콜라/1");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("음료만 입력하면 예외 처리가 발생한다.")
    @Test
    void 주문_예외_테스트7() {
        assertSimpleTest(() -> {
            runException("3", "제로콜라-1,레드와인-2");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("메뉴 개수가 20개를 초과하면 예외 처리가 발생한다.")
    @Test
    void 주문_예외_테스트8() {
        assertSimpleTest(() -> {
            runException("3", "타파스-12,제로콜라-9");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("메뉴를 중복 입력하면 예외 처리가 발생한다.")
    @Test
    void 주문_예외_테스트9() {
        assertSimpleTest(() -> {
            runException("3", "타파스-2, 타파스-2");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }
    @DisplayName("메뉴 개수를 0개로 입력하면 예외 처리가 발생한다.")
    @Test
    void 주문_예외_테스트10() {
        assertSimpleTest(() -> {
            runException("3", "타파스-0");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("메뉴 개수를 음수로 입력하면 예외 처리가 발생한다.")
    @Test
    void 주문_예외_테스트11() {
        assertSimpleTest(() -> {
            runException("3", "타파스--1");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }


    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
