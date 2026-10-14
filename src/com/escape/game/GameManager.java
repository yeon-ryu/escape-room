package com.escape.game;

import com.escape.player.Player;
import com.escape.room.Room5;

import java.util.Arrays;
import java.util.Scanner;

public class GameManager {
    private static GameManager gameManager;

    private Player player;
    private boolean[] playedRoom = new boolean[5];
    private Scanner sc = new Scanner(System.in);

    private GameManager() {}

    public static GameManager getInstance() {
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }

    private void insertPlayer() {
        // 플레이어 정보 입력
    }

    public void selectMenu() {
        if(player == null) {
            insertPlayer();
        }

        player.showPlayerStatus();

        // 메인 메뉴 선택
    }

    public void reset() {
        Arrays.fill(playedRoom, false);
    }

    public void clear() {
        int keyCount = player.addKey();
        if(keyCount >= 3) {
            System.out.println("열쇠를 3개 이상 모으셨습니다. 5번 방으로 가시면 클리어하실 수 있습니다.");
        }
        selectMenu();
    }

    public void fail() {
        int retryCount = player.decreaseRetryCount();
        if(retryCount == 0) {
            Room5 room5 = new Room5();
            room5.fail();
        }
    }
}
