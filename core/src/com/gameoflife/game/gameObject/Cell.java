package com.gameoflife.game.gameObject;

public class Cell {

    public int color;
    public int neighbors;
    public int x, y;
    public boolean isAlive;

    public Cell(int x, int y) {
        isAlive = false;
        color = 0;
        neighbors = 0;
        this.x = x;
        this.y = y;
    }

    public void birth() {
        isAlive = true;
        color = 1;
    }

    public void death() {
        isAlive = false;
        color = 0;
    }

    public void addColor() {
        if (color < 3) color++;
    }
}