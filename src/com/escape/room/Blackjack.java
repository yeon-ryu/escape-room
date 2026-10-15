package com.escape.room;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Blackjack extends Room{
    private String[] deck = new String[52]; // 빠져나간 카드는 "X" 로 변경
    private static final int BLACK_JACK = 21;

    public Blackjack() {
        roomIdx = 1;

        // 덱 초기화
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 13; j++) {
                deck[i * 13 + j] = makeCard(i, j + 2);
            }
        }

//        System.out.println(Arrays.toString(deck));
    }

    @Override
    public void play() {
        printRule();

        /*
        1. 딜러와 플레이어는 각자 2장의 카드를 받는다.
        2. 그 후 플레이어는 **카드를 더 받을 건지 선택**을 추가로 카드를 한장씩 더 받는다.
            1. 플레이어가 카드를 뽑을 때마다 **현재 플레이어의 패를 출력**해준다.
            2. 시스템 내부에서는 **플레이어의 카드 총합을 계산**하고, 플레이어의 카드 (A 의 경우 최대)**합계가 21 이 될 경우 블랙잭이 되고 승리**가 되며, (A의 경우 최소)**합계가 21 이 넘을 경우 플레이어의 패배**가 된다.
        3. 플레이어가 더이상 받지 않겠다 선택하면(블랙잭도 버스트도 아닐 경우), **딜러는 카드를 합계 17이상, 플레이어의 합계 이상이 될 때까지** 뽑는다.
            1. 계산하여 딜러가 버스트가 되면 그 순간 플레이어의 승리가 된다.
        4. 플레이어가 딜러보다 더 높을 경우 승리, 딜러가 더 높을 경우 플레이어의 패배가 된다.
        */
        // 승리시 clear(), 패배시 fail() 호출
    }

    /* 카드풀의 점수를 계산한다. 모든 경우의 수를 측정하여 최대한 21을 안 넘는 선에서 큰 수를 반환한다. */
    private int calculateSum(List<String> cards) {
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
        long nowHash = System.nanoTime();
        while(true) {
            Random random = new Random(nowHash);
            int idx = random.nextInt(52);
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

    private void printRule() {
        System.out.println("블랙잭 룰 설명 (+승리조건, 패배조건)");
    }
}
