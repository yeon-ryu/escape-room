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

        printRule();

        // play코드 구현 ..
        printMaze();
    }

    // 게임 규칙 설명
    private void printRule(){

        System.out.println("========== ROOM 3 ==========");
        System.out.println("어둠의 미로에 갇혔습니다.");
        System.out.println("15번 안에 출구에 도착하세요!");

        System.out.println("W : 위");
        System.out.println("S : 아래");
        System.out.println("A : 왼쪽");
        System.out.println("D : 오른쪽");

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
                System.out.println("W, A, S, D 중 하나를 입력해 주세요");
                return false;
        }

        // 미로 범위

        // 벽 충돌

        // 플레이어 위치 변경

        // 이동 횟수 증가

        return false;
    }

    // exit 도착
    private boolean checkExit() {

        return maze[playerRow][playerCol] == 'E';
    }

//    public static void main(String[] args) {
//        Maze maze = new Maze();
//        maze.play();
//    }
}

