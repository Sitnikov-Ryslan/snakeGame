package com.javarush.games.snake;

import com.javarush.games.snake.GameObject;
import com.javarush.engine.cell.*;

public class Mine extends GameObject {
    private static final String MINE_SIGN = "\uD83D\uDCA3";
    public boolean isAlive = true;
    public Mine(int x, int y) {
        super(x, y);
    }
    public void draw(Game game) {
        game.setCellValueEx(x, y, Color.NONE, MINE_SIGN, Color.RED, 75);
    }
}
