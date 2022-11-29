package com.javarush.games.snake;

import java.util.ArrayList;
import java.util.List;

import com.javarush.engine.cell.*;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private List<GameObject> snakeParts = new ArrayList<>();
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        GameObject s1 = new GameObject(x, y);
        GameObject s2 = new GameObject(x+1, y);
        GameObject s3 = new GameObject(x+2, y);

        snakeParts.add(s1);
        snakeParts.add(s2);
        snakeParts.add(s3);
    }

    public void setDirection(Direction direction) {
        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }

        if (this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        } else if (this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        } else if (this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        } else if (this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }
        this.direction = direction;
    }

    public void draw (Game game) {
        for (GameObject part : snakeParts) {
            if (snakeParts.indexOf(part) == 0) {
                if (!this.isAlive) {
                    game.setCellValueEx(part.x, part.y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                } else {
                    game.setCellValueEx(part.x, part.y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                }
            } else {
                if (!this.isAlive) {
                    game.setCellValueEx(part.x, part.y, Color.NONE, BODY_SIGN, Color.RED, 75);
                } else {
                    game.setCellValueEx(part.x, part.y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
            }
        }
    }

    public void move(Apple apple, Mine mine) {
        GameObject header = createNewHead();
        /*if (header.x >= SnakeGame.WIDTH || header.y >= SnakeGame.HEIGHT || header.x < 0 || header.y < 0) {
            isAlive = false;
            return;
        }*/
        teleport(header);

        if (checkCollision(header)) {
            isAlive = false;
            return;
        }

        snakeParts.add(0, header);

        if (header.x == apple.x && header.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }

        if (header.x == mine.x && header.y == mine.y) {
            isAlive = false;
        }
    }

    public GameObject createNewHead() {
        GameObject newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y);
        switch (direction) {
            case LEFT:
                newHead.x = snakeParts.get(0).x-1;
                newHead.y = snakeParts.get(0).y;
            break;
            case UP:
                newHead.x = snakeParts.get(0).x;
                newHead.y = snakeParts.get(0).y-1;
            break;
            case RIGHT:
                newHead.x = snakeParts.get(0).x+1;
                newHead.y = snakeParts.get(0).y;
            break;
            case DOWN:
                newHead.x = snakeParts.get(0).x;
                newHead.y = snakeParts.get(0).y+1;
        }
        return newHead;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision(GameObject object) {
        boolean result = false;
        for (GameObject part : snakeParts) {
            if (object.x == part.x && object.y == part.y) {
                result = true;
            }
        }
        return result;
    }

    public int getLength() {
        return snakeParts.size();
    }

    public void teleport(GameObject head) {
        if (head.x < 0) {
            head.x += SnakeGame.WIDTH;
        } else if (head.x >= SnakeGame.WIDTH) {
            head.x -= SnakeGame.WIDTH;
        } else if (head.y < 0) {
            head.y += SnakeGame.HEIGHT;
        } else if (head.y >= SnakeGame.HEIGHT) {
            head.y -= SnakeGame.HEIGHT;
        }
    }
}
