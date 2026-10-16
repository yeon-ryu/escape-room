package com.escape.room;

import com.escape.game.GameManager;

import java.util.Scanner;

public abstract class Room {
    protected Scanner sc = new Scanner(System.in);
    // 순환 오류 안 나는 것 같으니 protected 필드로 뒀는데 발생 시 메소드 안에서 부르도록 수정
    protected GameManager gm = GameManager.getInstance();

    /* 각 방은 고유한 roomIdx 를 반드시 구현하여야 합니다. */
    protected int roomIdx = -1;

    public abstract void play();

    public abstract void printRule();

    // Room 1~4 는 이하 메소드 오버라이딩 따로 필요 없음

    /* play() 에서 게임 클리어 시 호출 */
    public void clear() {
        System.out.println("성공적으로 " + roomIdx + "번 방을 클리어 하셨습니다! 열쇠를 획득하셨습니다.");
        gm.clear(roomIdx);
    }

    /* play() 에서 게임 실패 시 호출 */
    public void fail() {
        System.out.println("방 클리어에 실패하셨습니다. 재도전 횟수가 차감됩니다.");
        gm.fail();
    }
}
