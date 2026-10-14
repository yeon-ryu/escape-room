package com.escape.room;

import com.escape.game.GameManager;

import java.util.Scanner;

public abstract class Room {
    Scanner sc = new Scanner(System.in);
    private GameManager gameManager = new GameManager();

    public abstract void play();

    // Room 1~4 는 이하 메소드 오버라이딩 따로 필요 없음

    public void clear() {
        // play() 에서 clear 시 해당 메소드를 부른다.
        gameManager.clear();
    }

    public void fail() {
        // play() 에서 실패 시 해당 메소드를 부른다.
        gameManager.fail();
    }
}
