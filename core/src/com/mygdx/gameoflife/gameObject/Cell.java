package com.mygdx.gameoflife.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Cell {

    public int color;
    public int neighbors;
    public int x,y,width,height;

    public Cell(int x, int y, int width, int height){
        color = 0;
        neighbors = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}