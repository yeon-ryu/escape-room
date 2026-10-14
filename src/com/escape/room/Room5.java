package com.escape.room;

public class Room5 extends Room{
    @Override
    public void play() {
        // 멤버의 열쇠 갯수가 3개 이상이면 clear(), 미만이면 fail() 호출
    }

    @Override
    public void clear() {
        // 꾸미는건 나중에 상의하겠습니다..
        System.out.println("축하드립니다!! 게임을 클리어하셨습니다!!🎉");
    }

    @Override
    public void fail() {
        System.out.println("게임 클리어에 실패하셨습니다.");
        System.out.print("재도전 하시겠습니까? (y/n) : ");
        String select = sc.nextLine();
        if("Y".equalsIgnoreCase(select)) {
            // 재도전 횟수, 열쇠 개수, gameManager 에서 플레이한 방 기록 초기화 (gameManager 초기화는 해당 클래스에 존재하는 메소드 호출)
            // gameMaager 메뉴 선택 호출
        } else {
            System.out.println("게임을 종료합니다.");
        }
    }
}
