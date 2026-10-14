package com.escape.main;

import com.escape.game.GameManager;

public class Application {
    public static void main(String[] args) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.selectMenu();
    }
}
