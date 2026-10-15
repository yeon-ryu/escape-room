package com.escape.room;

import com.escape.game.GameManager;

import java.util.Scanner;

public abstract class Room {
    protected Scanner sc = new Scanner(System.in);
    // 순환 오류 안 나는 것 같으니 protected 필드로 뒀는데 발생 시 메소드 안에서 부르도록 수정
    protected GameManager gm = GameManager.getInstance();

    public abstract void play();

    // Room 1~4 는 이하 메소드 오버라이딩 따로 필요 없음

    public void clear(int roomIdx) {
        // play() 에서 clear 시 해당 메소드를 부른다.
        gm.clear(roomIdx);
    }

    public void fail() {
        // play() 에서 실패 시 해당 메소드를 부른다.
        gm.fail();
    }
}
