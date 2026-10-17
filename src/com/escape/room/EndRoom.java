package com.escape.room;

import com.escape.player.Player;

public class EndRoom extends Room{
    private Player player;

    public EndRoom(Player player) {
        this.player = player;
        roomIdx = 0;
    }

    @Override
    protected void init() {

    }

    @Override
    public void play() {
        // 멤버의 열쇠 갯수가 3개 이상이면 clear(), 미만이면 fail() 호출
        if(player.getKeyCount() >= 3) {
            clear();
        } else {
            System.out.println("열쇠 갯수가 부족합니다!");
            fail();
        }
    }

    @Override
    public void printRule() {}

    @Override
    public void clear() {
        System.out.println("\n축하드립니다!! 성공적으로 방을 탈출하셨습니다!!🎉");
        gm.setGameEnd();
    }

    @Override
    public void fail() {
        System.out.println("방 탈출에 실패하셨습니다.");
        System.out.print("\n재도전 하시겠습니까? (y/n) : ");
        String select = sc.nextLine();
        if("Y".equalsIgnoreCase(select)) {
            // 재도전 횟수, 열쇠 개수, gameManager 에서 플레이한 방 기록 초기화
            // gameManager 메뉴 선택 호출
            player.resetInfo();
            gm.reset();
            gm.selectMenu();
        } else {
            System.out.println("게임을 종료합니다.");
            gm.setGameEnd();
        }
    }
}
