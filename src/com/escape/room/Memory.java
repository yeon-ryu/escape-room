package com.escape.room;

import java.util.Arrays;

public class Memory extends Room{

    // 카드 8장 (숫자 1~4가 각각 2장)
    private int[] cards = {1, 1, 2, 2, 3, 3, 4, 4};

    // 짝을 맞힌 카드인지 확인
    private boolean[] matched = new boolean[8];

    // 턴 횟수와 맞힌 카드 쌍
    private int turnCount;
    private int matchCount;

    private static final int MAX_TURN = 10;

    // 생성자
    public Memory() {
        roomIdx = 4;
    }

    // 게임 진행
    @Override
    public void play() {

        // 게임 상태 초기화
        turnCount = 0;
        matchCount = 0;
        Arrays.fill(matched, false);

        initCards();
        printRule();

        while (turnCount < MAX_TURN) {

            printCards();

            System.out.println("남은 턴 : " + (MAX_TURN - turnCount));
            System.out.println("맞힌 카드 : " + matchCount + " / 4쌍");
            System.out.println();

            // 첫 번째 카드 선택
            System.out.print("첫 번째 카드 선택 >> ");
            int first = selectCard();

            // 두 번째 카드 선택
            int second;

            while (true) {
                System.out.print("두 번째 카드 선택 >> ");
                second = selectCard();

                if (first == second) {
                    System.out.println("같은 카드를 두 번 선택할 수 없습니다.");
                } else {
                    break;
                }
            }

            // 선택한 카드 공개
            System.out.println();
            System.out.println((first + 1) + "번 카드 : " + cards[first]);
            System.out.println((second + 1) + "번 카드 : " + cards[second]);

            // 한 턴 사용
            turnCount++;

            // 카드 짝 확인
            if (checkMatch(first, second)) {

                System.out.println("정답! 같은 카드입니다.");

                matched[first] = true;
                matched[second] = true;

                matchCount++;

            } else {
                System.out.println("틀렸습니다!");
                System.out.println("카드가 다시 뒤집힙니다.");
            }

            System.out.println();

            // 4쌍을 모두 맞힌 경우
            if (matchCount == 4) {

                printCards();

                System.out.println("모든 카드의 짝을 맞혔습니다!");
                System.out.println("ROOM 4 CLEAR!");
                System.out.println("열쇠 1개를 획득했습니다.");

                clear();
                return;
            }
        }

        // 10턴을 모두 사용한 경우
        System.out.println("10턴을 모두 사용했습니다.");
        System.out.println("기억의 방 탈출에 실패했습니다.");

        fail();
    }

    // 게임 규칙 출력
    public void printRule() {

        System.out.println();
        System.out.println("========== ROOM 4 ==========");
        System.out.println("          기억의 방");
        System.out.println("============================");

        System.out.println("8장의 카드 중 같은 숫자 2장을 찾으세요.");
        System.out.println("숫자 1~4가 각각 2장씩 존재합니다.");
        System.out.println("10턴 안에 4쌍을 완성하면 성공입니다.");

        System.out.println();
        System.out.println("[게임 규칙]");
        System.out.println("1. 카드 번호는 1~8입니다.");
        System.out.println("2. 한 턴에 카드 2장을 선택합니다.");
        System.out.println("3. 같은 숫자면 카드가 공개된 상태로 유지됩니다.");
        System.out.println("4. 다른 숫자면 카드가 다시 뒤집힙니다.");
        System.out.println("5. 잘못된 입력은 턴이 차감되지 않습니다.");
        System.out.println();
    }

    // 카드 초기화 및 랜덤 섞기
    private void initCards() {

        // 카드 배열 초기화
        for (int i = 0; i < cards.length; i++) {
            cards[i] = (i / 2) + 1;
        }

        // Fisher-Yates 방식으로 카드 섞기
        for (int i = cards.length - 1; i > 0; i--) {

            int randomIndex = (int) (Math.random() * (i + 1));

            // 두 카드의 위치 교환
            int temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    // 카드 상태 출력
    private void printCards() {

        System.out.println();

        for (int i = 0; i < cards.length; i++) {

            if (matched[i]) {
                System.out.print("[ " + cards[i] + " ] ");
            } else {
                System.out.print("[ ? ] ");
            }

            // 카드 4장마다 줄바꿈
            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println("  1     2     3     4");
        System.out.println("  5     6     7     8");
        System.out.println();
    }

    // 카드 선택
    private int selectCard() {

        while (true) {

            // 숫자가 아닌 값을 입력한 경우
            if (!sc.hasNextInt()) {
                System.out.println("숫자를 입력해주세요.");
                sc.next();
                System.out.print("다시 선택 >> ");
                continue;
            }

            int number = sc.nextInt();

            // 1~8 범위 확인
            if (number < 1 || number > 8) {
                System.out.println("1~8 사이의 숫자를 입력해주세요.");
                System.out.print("다시 선택 >> ");
                continue;
            }

            int index = number - 1;

            // 이미 맞힌 카드인지 확인
            if (matched[index]) {
                System.out.println("이미 맞힌 카드입니다.");
                System.out.print("다시 선택 >> ");
                continue;
            }

            return index;
        }
    }

    // 두 카드의 숫자가 같은지 확인
    private boolean checkMatch(int first, int second) {

        return cards[first] == cards[second];
    }

    public static void main(String[] args) {

        Memory memoryRoom = new Memory();
        memoryRoom.play();
    }
}
