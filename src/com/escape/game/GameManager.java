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
            sc.nextLine();

            Room room = null;

            if (no >= 1 && no <= 4 && playedRoom[no]) {
                System.out.println("이미 클리어하신 방입니다.");
                continue;
            }

            switch (no) {
                case 1:
                    room = new Blackjack();
                    break;
                case 2:
                    room = new Minesweeper();
                    break;
                case 3:
                    room = new Maze();
                    break;
                case 4:
                    room = new Memory();
                    break;
                case 0:
                    System.out.print(
                            "열쇠가 부족하실 경우 탈출에 실패합니다. 정말 들어가시겠습니까?(y/n) "
                    );

                    String goEnd = sc.nextLine();

                    if ("Y".equalsIgnoreCase(goEnd)) {
                        room = new EndRoom(player);
                    }
                    break;
                default:
                    System.out.println("존재하지 않는 방입니다.");
            }

            // 다형성 활용: 실제 객체에 맞는 play() 실행
            if (room != null) {
                room.play();
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
