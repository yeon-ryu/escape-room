package com.escape.room;

import java.util.ArrayList;
import java.util.List;

public class Blackjack extends Room{
    private final String[] deck = new String[52]; // 빠져나간 카드는 "X" 로 변경
    private static final int BLACK_JACK = 21;

    public Blackjack() {
        roomIdx = 1;

        // 덱 초기화
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 13; j++) {
                deck[i * 13 + j] = makeCard(i, j + 2);
            }
        }

        System.out.println("== 블랙잭 게임방에 들어오셨습니다. ==");
    }

    @Override
    public void play() {
        printRule();

        System.out.println("== 블랙잭 게임을 시작합니다. ==");

        List<String> dealerCards = new ArrayList<>();
        List<String> playerCards = new ArrayList<>();

        /* 1. 딜러와 플레이어는 각자 2장의 카드를 받는다. */
        for(int i = 0; i < 2; i++) {
            dealerCards.add(drawCard());
            playerCards.add(drawCard());
        }

        int playerScore = calculateScore(playerCards);

        /* 2. 그 후 플레이어는 카드를 더 받을 건지 선택을 추가로 카드를 한장씩 더 받는다.
            2-1. 플레이어가 카드를 뽑을 때마다 현재 플레이어의 패를 출력해준다.
            2-2. 시스템 내부에서는 플레이어의 카드 총합을 계산하고, 플레이어의 카드 (A 의 경우 최대)합계가 21 이 될 경우 블랙잭이 되고 승리가 되며, (A의 경우 최소)**합계가 21 이 넘을 경우 플레이어의 패배**가 된다.
        */
        while(true) {
            System.out.println("딜러 카드 : [" + dealerCards.getFirst() + ", □]");
            System.out.println("플레이어 카드 : " + playerCards);
//            System.out.println("현재 플레이어의 숫자 : " + playerScore); // 스스로 계산하는 것까지가 블랙잭의 묘미일듯

            // 첫 턴에 블랙잭이 될 수 있다 (내가 테스트하며 겪음)
            if(playerScore == BLACK_JACK) {
                System.out.println("축하드립니다!! 블랙잭으로 승리하셨습니다!");
                System.out.println("플레이어 점수 : " + playerScore + ", 플레이어 카드 : " + playerCards);
                clear();
                return;
            }

            System.out.print("카드를 한 장 더 뽑으시겠습니까?(y/n) : ");
            String select = sc.nextLine();

            if("Y".equalsIgnoreCase(select)) {
                playerCards.add(drawCard());
                playerScore = calculateScore(playerCards);
                
                if(playerScore == BLACK_JACK) {
                    System.out.println("축하드립니다!! 블랙잭으로 승리하셨습니다!");
                    System.out.println("플레이어 점수 : " + playerScore + ", 플레이어 카드 : " + playerCards);
                    clear();
                    return;
                } else if(playerScore > BLACK_JACK) {
                    System.out.println("버스트로 패배하셨습니다.");
                    System.out.println("플레이어 점수 : " + playerScore + ", 플레이어 카드 : " + playerCards);
                    fail();
                    return;
                }
            } else {
                System.out.println("스테이하셨습니다.");
                break;
            }
        }

        /* 딜러는 카드를 합계 17이상 될 때까지 뽑는다 */
        System.out.println("딜러가 카드를 뽑기 시작합니다.");
        int dealerScore = calculateScore(dealerCards);
        while(dealerScore < 17) {
            dealerCards.add(drawCard());
            dealerScore = calculateScore(dealerCards);
        }

        System.out.println("딜러가 카드를 전부 뽑았습니다.");
        System.out.println("==== 결과 ====");

        /* 4. 플레이어가 딜러보다 더 높거나 딜러가 버스트 됐을 경우 승리, 딜러가 더 높거나 같을 경우 플레이어의 패배가 된다. */
        boolean playerWin = false;
        if(dealerScore > BLACK_JACK || dealerScore < playerScore) {
            playerWin = true;
        }
        System.out.println("딜러 점수 : " + dealerScore + (dealerScore > BLACK_JACK ? "(버스트!!)" : "") + ", 딜러 카드 : " + dealerCards);
        System.out.println("플레이어 점수 : " + playerScore + ", 플레이어 카드 : " + playerCards);

        // 승리시 clear(), 패배시 fail() 호출
        if(playerWin) {
            System.out.println("축하드립니다! 승리하셨습니다.");
            clear();
        } else {
            System.out.println("패배하셨습니다.");
            fail();
        }
    }

    /* 카드풀의 점수를 계산한다. 모든 경우의 수를 측정하여 최대한 21을 안 넘는 선에서 큰 수를 반환한다. */
    private int calculateScore(List<String> cards) {
        int sum = 0;

        // 카드는 ◆4 나 ♣A 이런 식이므로 subStr 로 1번째 인덱스부터 읽어와 대응하는 숫자로 변경한다.
        // A 는 1 도 되고 11 도 되는데 계산할 때 11 로 계산시 버스트가 될 경우 1 로 계산하여 21 이 넘지 않는 최대한 높은 수가 되도록 계산
        int aceCount = 0;

        for(String card : cards) {
            String strNum = card.substring(1);
            if(strNum.equals("A")) {
                aceCount++;
            } else if(strNum.equals("K") || strNum.equals("Q") || strNum.equals("J")) {
                sum += 10;
            } else {
                sum += Integer.valueOf(strNum);
            }
        }

        sum = calculateAce(sum, aceCount, 0);

        return sum;
    }

    /* 모든 경우의 수를 측정하여 최대한 21을 안 넘는 선에서 큰 수를 반환한다. */
    private int calculateAce(int sum, int aceCount, int idx) {
        if(idx == aceCount) return sum;

        int small = calculateAce(sum + 1, aceCount, idx + 1);
        int large = calculateAce(sum + 11, aceCount, idx + 1);

        if(small > BLACK_JACK || large > BLACK_JACK) {
            return small;
        } else {
            return large;
        }
    }

    private String drawCard() {
        while(true) {
            int idx = (int)(Math.random() * 52);

            if(!deck[idx].equals("X")) {
                String draw = deck[idx];
                deck[idx] = "X";
                return draw;
            }
        }
    }

    private String makeCard(int type, int num) {
        StringBuilder sb = new StringBuilder();
        String[] strCards = {"K", "Q", "J", "A"};

        // 카드를 n벌 사용할 경우 deck 은 52 * n, type 은 4 * n 개씩 늘린다. (현재는 1벌 사용 중)
        type = type % 4;

        switch(type) {
            case 0:
                sb.append("◆");
                if(num <= 10) {
                    sb.append(num);
                } else {
                    sb.append(strCards[num - 11]);
                }
                break;
            case 1:
                sb.append("♠");
                if(num <= 10) {
                    sb.append(num);
                } else {
                    sb.append(strCards[num - 11]);
                }
                break;
            case 2:
                sb.append("♥");
                if(num <= 10) {
                    sb.append(num);
                } else {
                    sb.append(strCards[num - 11]);
                }
                break;
            case 3:
                sb.append("♣");
                if(num <= 10) {
                    sb.append(num);
                } else {
                    sb.append(strCards[num - 11]);
                }
                break;
        }
        return sb.toString();
    }

    public void printRule() {
        System.out.println("블랙잭 룰 설명 (+승리조건, 패배조건)");
    }
}
