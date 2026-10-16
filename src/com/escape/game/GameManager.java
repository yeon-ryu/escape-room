package com.escape.game;

import com.escape.player.Player;
import com.escape.room.*;

import java.util.Arrays;
import java.util.Scanner;

public class GameManager {
    private static final int MAX_ROOM = 5;

    private static GameManager gameManager;

    private Player player;
    private boolean[] playedRoom = new boolean[MAX_ROOM];
    private Scanner sc = new Scanner(System.in);

    private boolean isGameEnd = false;

    private GameManager() {}

    public static GameManager getInstance() {
        if(gameManager == null) gameManager = new GameManager();
        return gameManager;
    }

    private void insertPlayer() {
        // 플레이어 정보 입력
        System.out.print("플레이어명을 입력해주세요 : ");
        String name = sc.nextLine();
        player = new Player(name);
    }

    public void selectMenu() {
        if(player == null) {
            insertPlayer();
        }

        // 메인 메뉴 선택
        while(true) {
            if(isGameEnd) return;

            System.out.println();
            player.showPlayerStatus();

            StringBuilder selectSb = new StringBuilder();

            for(int i = 0; i < MAX_ROOM; i++) {
                if(!playedRoom[i]) {
                    if(i == 0) {
                        selectSb.append(0 + "번 방(탈출 방)\t");
                    } else {
                        selectSb.append(i + "번 방\t");
                    }
                }
            }
            System.out.println(selectSb.toString());
            System.out.print("들어갈 방 번호를 입력해 주세요 : ");

            int no = sc.nextInt();

            switch(no) {
                case 1 :
                    if(playedRoom[1]) {
                        System.out.println("이미 클리어하신 방입니다.");
                        break;
                    }
                    // 1번 방 이동
                    Room blackjack = new Blackjack();
                    blackjack.play();
                    break;
                case 2 :
                    if(playedRoom[2]) {
                        System.out.println("이미 클리어하신 방입니다.");
                        break;
                    }
                    // 2번 방 이동
                    Room minesweeper = new Minesweeper();
                    minesweeper.play();
                    break;
                case 3 :
                    if(playedRoom[3]) {
                        System.out.println("이미 클리어하신 방입니다.");
                        break;
                    }
                    // 3번 방 이동
                    Room maze = new Maze();
                    maze.play();
                    break;

                case 4 :
                    if(playedRoom[4]) {
                        System.out.println("이미 클리어하신 방입니다.");
                        break;
                    }
                    // 4번 방 이동
                    Room memory = new Memory();
                    memory.play();
                    break;

                case 0 :
                    Room endRoom = new EndRoom(player);
                    endRoom.play();
                    break;
                default:
                    System.out.println("존재하지 않는 방입니다.");

            }
        }
    }

    public void reset() {
        Arrays.fill(playedRoom, false);
    }

    public void clear(int roomIdx) {
        // 클리어한 방 번호 받아서 플레이 완료 처리
        playedRoom[roomIdx] = true;

        // 열쇠 증가
        int keyCount = player.addKey();
        if(keyCount >= 3) {
            System.out.println("열쇠를 3개 이상 모으셨습니다. 탈출 방으로 가시면 클리어하실 수 있습니다.");
        }
        selectMenu();
    }

    public void fail() {
        // 재도전 횟수 감소
        int retryCount = player.decreaseRetryCount();
        if(retryCount == 0) {
            EndRoom endRoom = new EndRoom(player);
            endRoom.fail();
        }
    }

    public void setGameEnd() {
        isGameEnd = true;
    }
}
