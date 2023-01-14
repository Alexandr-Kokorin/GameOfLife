package com.gameoflife.game.controlElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sprite {

    private final Texture texture;
    private final float x, y, width, height;

    public Sprite(String texture, float x, float y, float width, float height) {
        this.texture = new Texture(texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(SpriteBatch sb) {
        sb.draw(texture, x, y, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}