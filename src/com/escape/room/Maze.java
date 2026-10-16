package com.escape.room;

public class Maze extends Room {

    private char[][] maze = {
            {'.', '.', '#', '.', '.'},
            {'#', '.', '#', '.', '#'},
            {'.', '.', '.', '.', '.'},
            {'.', '#', '#', '#', '.'},
            {'.', '.', '.', '#', 'E'}
    };

    private int playerRow;
    private int playerCol;

    private int moveCount;
    private static final int MAX_MOVE = 15;

    public Maze() {
        roomIdx = 3;

        playerRow = 0;
        playerCol = 0;
        moveCount = 0;
    }

    @Override
    public void play() {

        // 시작 시 초기화

        playerRow = 0;
        playerCol = 0;
        moveCount = 0;

        printRule();

        while(moveCount < MAX_MOVE) {
            printMaze();

            System.out.println("남은 이동 횟수 : " + (MAX_MOVE - moveCount));

            System.out.println("이동 방향을 입력하세요 (W/A/S/D) >> ");
            String direction = sc.next();

            // 플레이어 이동
            movePlayer(direction);

            // if 출구 도착
            if (checkExit()) {

                printMaze();

                System.out.println();
                System.out.println("축하합니다 !! 미로를 탈출 했습니다 !");
                System.out.println("ROOM 3 CLEAR !");
                System.out.println("열쇠 1개를 획득 했습니다.");

                clear();
                return;
            }

            System.out.println();

        }

        // if 이동 횟수 모두 사용
        System.out.println("남은 이동 횟수는 0 입니다.");
        System.out.println("미로 탈출에 실패 했습니다.");

        fail();

    }

    // 게임 규칙 설명
    public void printRule(){

        System.out.println("====어둠의 미로에 갇혔습니다.====");
        System.out.println("============================");

        System.out.println("15번 안에 출구에 도착하세요!");

        System.out.println();

        System.out.println("[게임 규칙]");
        System.out.println("P : 플레이어");
        System.out.println("# : 벽");
        System.out.println(". : 이동 가능한 길");
        System.out.println("E : 출구");
        System.out.println();

        System.out.println("W : 위");
        System.out.println("S : 아래");
        System.out.println("A : 왼쪽");
        System.out.println("D : 오른쪽");

        System.out.println();
        System.out.println("벽이나 미로 밖으로 이동하면 횟수가 차감되지 않습니다.");
        System.out.println();
    }


    // 미로 출력
    private void printMaze() {

        System.out.print("     ");
        for (int j = 0; j < maze[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        System.out.println("   ┌─────────────┐");


        for (int i = 0; i < maze.length ; i++){

            System.out.print(" " + i + " │ ");

            for (int j = 0; j < maze[i].length ; j++){

                if (i == playerRow && j == playerCol) {
                    System.out.print("P ");
                } else {
                    System.out.print("" + maze[i][j] + " ");
                }
            }

            System.out.println("|");
        }
        System.out.println("   └─────────────┘");
    }

    // 플레이어 이동
    private boolean movePlayer(String direction){

        int nextRow = playerRow;
        int nextCol = playerCol;

        switch (direction.toUpperCase()){
            case "W" :
                nextRow--;
                break;

            case "S" :
                nextRow++;
                break;

            case "A" :
                nextCol--;
                break;

            case "D" :
                nextCol++;
                break;

            default :
                System.out.println("잘못된 입력입니다.");
                System.out.println("W, A, S, D 중 하나를 입력해 주세요");
                return false;
        }

        // 미로 범위
        if (nextRow < 0 || nextRow >= maze.length
                || nextCol < 0 || nextCol >= maze[0].length) {

            System.out.println("미로 밖으로 이동할 수 없습니다.");
            return false;
        }

        // 벽 충돌
        if (maze[nextRow][nextCol] == '#') {

            System.out.println("벽이 막고 있습니다.");
            System.out.println("다른 방향으로 이동해주세요.");

            return false;
        }

        // 플레이어 위치 변경
        playerRow = nextRow;
        playerCol = nextCol;

        // 이동 횟수 증가
        moveCount++;
        System.out.println("플레이어 이동 완료 !");

        return true;
    }

    // exit 도착
    private boolean checkExit() {

        return maze[playerRow][playerCol] == 'E';
    }

}

