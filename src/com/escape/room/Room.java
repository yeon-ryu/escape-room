package com.escape.room;

import java.util.Scanner;

public abstract class Room {
    Scanner sc = new Scanner(System.in);

    public abstract void play();

    // Room 1~4 는 이하 메소드 오버라이딩 따로 필요 없음

    public void clear() {
        // play() 에서 clear 시 해당 메소드를 부른다.
        // 플레이어 열쇠 갯수 늘리고 gameManager 의 선택 메뉴 호출
    }

    public void fail() {
        // play() 에서 실패 시 해당 메소드를 부른다.
        // 플레이어 재도전 횟수를 줄이고 gameManage 의 선택 메뉴 호출
        // 재도전 횟수가 0번이라면 바로 Room5 로 이동
    }
}
