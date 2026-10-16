package com.escape.player;

public class Player {

    private String name;
    private int retryCount;
    private int keyCount;

    // 게임 시작 시 name입력 받음
    public Player(String name) {
        this.name = name;
        this.retryCount = 5;
        this.keyCount = 0;
    }

    // 열쇠 획득
    public int addKey(){

        return ++keyCount;
    }

    // 재도전 횟수 감소
    public int decreaseRetryCount(){
        if (retryCount > 0) {
            return --retryCount;
        } else {
            return retryCount;
        }
    }

    // 재도전 가능 여부
    public boolean canRetry() {
        return retryCount >0;
    }

    // 재도전 시 열쇠, 재도전횟수 카운트 리셋
    public void resetInfo() {
        retryCount = 5;
        keyCount = 0;
    }

    // 게임 클리어 시 출력할 player 정보
    public void showPlayerStatus() {
        System.out.println(" <===== 플레이어 정보 =====> ");
        System.out.println(name + "님의 남은 재도전 횟수 : " + retryCount + "회"+ "\t" + "획득한 열쇠 개수 : " + keyCount + " / 3 개");
        System.out.println(" ======================== ");
    }

    // getter 생성
    public String getName() {
        return name;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public int getKeyCount() {
        return keyCount;
    }
}
