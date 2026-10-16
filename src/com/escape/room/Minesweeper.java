package com.escape.room;

import java.util.Map;

public class Minesweeper extends Room{
    private static final int MAP_SIZE = 7;
    private static final int MINE_COUNT = 2;

    private int[][] map;
    private boolean[][] openMap;

    public Minesweeper() {
        roomIdx = 2;

        map = new int[MAP_SIZE][MAP_SIZE];
        openMap = new boolean[MAP_SIZE][MAP_SIZE];

        System.out.println("== 지뢰찾기 게임 방에 들어오셨습니다. ==");
    }

    @Override
    public void play() {
        System.out.println("맵을 새로 생성합니다.");
        makeNewMap();


    }

    /* map 에 랜덤으로 MINE_COUNT 만큼의 지뢰를 만들고 그 주변의 갯수 숫자를 넣는다. */
    private void makeNewMap() {
        if(MINE_COUNT > MAP_SIZE * MAP_SIZE) {
            System.out.println("지뢰 숫자가 이상하여 지뢰찾기 맵을 만들 수 없습니다.");
            return;
        }

        // map 에 랜덤으로 MINE_COUNT 만큼의 지뢰 생성
        int count = 0;
        while(count < MINE_COUNT) {
            int x = (int)(Math.random() * MAP_SIZE);
            int y = (int)(Math.random() * MAP_SIZE);

            if(map[x][y] == 0) {
                // 지뢰 표시
                map[x][y] = -1;
                count++;
            }
        }

        // map 을 돌며 주변 지뢰 숫자 표시
        int[] dirX = {-1, -1, 1, 1, 0, 0, 0, 0};
        int[] dirY = {0, 0, 0, 0, -1, -1, 1, 1};

        for(int i = 0; i < MAP_SIZE; i++) {
            for(int j = 0; j < MAP_SIZE; j++) {
                if(map[i][j] == -1) continue;

                int mineCnt = 0;
                for(int dirIdx = 0; dirIdx < 8; dirIdx++) {
                    int x = i + dirX[dirIdx];
                    int y = j + dirY[dirIdx];

                    if(x < 0 || x >= MAP_SIZE || y < 0 || y >= MAP_SIZE) continue;

                    if(map[x][y] == -1) mineCnt++;
                }
                map[i][j] = mineCnt;
            }
        }
    }
}
