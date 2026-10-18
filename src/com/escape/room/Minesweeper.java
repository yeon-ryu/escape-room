package com.escape.room;

import java.util.Arrays;

public class Minesweeper extends Room{
    private int mapSize;
    private int mineCount;

    private int[][] map;
    private boolean[][] openMap;

    private boolean isEnd;

    public Minesweeper() {
        roomIdx = 2;

        isEnd = false;

        System.out.println("\n== 지뢰찾기 게임방에 들어오셨습니다. ==");
    }

    @Override
    public void play() {
        printRule();

        System.out.println("맵을 새로 생성합니다.");

        boolean successCreate = createNewMap();
        if(!successCreate) return;

        System.out.println("맵이 완성되었습니다.");

        int openCount = 0;
        int maxOpenCount = mapSize * mapSize - mineCount;

        while(true) {
            printMap();

            // 플레이어가 판을 연다.
            System.out.print("확인할 칸의 행과 열을 띄어쓰기로 구분하여 입력해주세요 : ");
            String inputStr = sc.nextLine();
            if(!inputStr.matches("^\\d+ \\d+$")) {
                System.out.println("입력 형식이 잘못 되었습니다.");
                continue;
            }
            int openX = Integer.valueOf(inputStr.split(" ")[0]) - 1;
            int openY = Integer.valueOf(inputStr.split(" ")[1]) - 1;

            if(openX < 0 || openX >= mapSize || openY < 0 || openY >= mapSize) {
                System.out.println("맵 밖 위치를 지정할 수 없습니다!");
                continue;
            }
            if(openMap[openX][openY]) {
                System.out.println("이미 열린 칸입니다!");
                continue;
            }

            if(map[openX][openY] == -1) {
                isEnd = true;
                printMap();
                System.out.println("지뢰가 터졌습니다! 실패하셨습니다.");
                fail();
                return;
            }

            openCount++;
            if(openCount == maxOpenCount) {
                isEnd = true;
                printMap();
                System.out.println("축하드립니다! 모든 안전한 칸을 여셨습니다!");
                clear();
                return;
            }
            openMap[openX][openY] = true;
        }
    }

    /* map 에 랜덤으로 mineCount 만큼의 지뢰를 만들고 그 주변의 갯수 숫자를 넣는다. */
    private boolean createNewMap() {
        while(mapSize <= 0) {
            System.out.print("맵의 크기를 입력해주세요 : ");
            if (!sc.hasNextInt()) {
                System.out.println("맵 크기는 숫자로 입력하셔야 합니다.");
                sc.next();
                continue;
            }
            mapSize = sc.nextInt();
            if(mapSize <= 0) {
                System.out.println("0 이하의 숫자는 입력하실 수 없습니다.");
            }
        }
        while(mineCount <= 0) {
            System.out.print("지뢰의 갯수를 입력해주세요 : ");
            if (!sc.hasNextInt()) {
                System.out.println("지뢰의 갯수는 숫자로 입력하셔야 합니다.");
                sc.next();
                continue;
            }
            mineCount = sc.nextInt();
            sc.nextLine();
            if(mineCount <= 0) {
                System.out.println("0 이하의 숫자는 입력하실 수 없습니다.");
            }
        }

        if(mineCount > mapSize * mapSize - 1) {
            System.out.println("지뢰 숫자가 이상하여 지뢰찾기 맵을 만들 수 없습니다.");
            return false;
        }

        map = new int[mapSize][mapSize];
        openMap = new boolean[mapSize][mapSize];

        // map 에 랜덤으로 mineCount 만큼의 지뢰 생성
        int count = 0;
        while(count < mineCount) {
            int x = (int)(Math.random() * mapSize);
            int y = (int)(Math.random() * mapSize);

            if(map[x][y] == 0) {
                // 지뢰 표시
                map[x][y] = -1;
                count++;
            }
        }

        // map 을 돌며 주변 지뢰 숫자 표시
        int[] dirX = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dirY = {-1, 1, 0, 0, -1, 1, -1, 1};

        for(int i = 0; i < mapSize; i++) {
            for(int j = 0; j < mapSize; j++) {
                if(map[i][j] == -1) continue;

                int mineCnt = 0;
                for(int dirIdx = 0; dirIdx < 8; dirIdx++) {
                    int x = i + dirX[dirIdx];
                    int y = j + dirY[dirIdx];

                    if(x < 0 || x >= mapSize || y < 0 || y >= mapSize) continue;

                    if(map[x][y] == -1) mineCnt++;
                }
                map[i][j] = mineCnt;
            }
        }

        return true;
    }

    /* 플레이어가 연 정보, 플레이어가 지뢰 표시한 정보를 기반으로 보일 맵을 그린다 */
    private void printMap() {
        System.out.println();

        for(int i = 0; i < mapSize; i++) {
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < mapSize; j++) {
                if(isEnd || openMap[i][j]) { // 열린 칸
                    if(map[i][j] == -1) {
                        sb.append(" 💣 ");
                    } else {
                        sb.append("  " + map[i][j] + "  ");
                    }
                } else { // 닫힌 칸
                    sb.append("  ■  ");
                }
            }
            System.out.println(sb.toString());
        }

        System.out.println();
    }

    public void printRule() {
        System.out.println("지뢰를 피해 안전한 칸을 전부 선택해주세요!");
        System.out.println();

        System.out.println("[게임 규칙]");
        System.out.println("칸의 위치는 (1, 1) 부터 시작합니다.");
        System.out.println("입력은 행과 열을 띄워쓰기로 구분하여 입력합니다.");
        System.out.println();
    }
}
