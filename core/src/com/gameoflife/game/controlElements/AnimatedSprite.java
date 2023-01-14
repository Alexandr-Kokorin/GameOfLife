package com.gameoflife.game.controlElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnimatedSprite {

    private final Texture[] textures;
    private final float x, y, width, height;
    private final double timeBetweenFrames;
    private double elapsedTime;
    private int state;

    public AnimatedSprite(String[] textures, float x, float y, float width, float height, double timeBetweenFrames) {
        this.textures = new Texture[textures.length];
        for (int i = 0; i < textures.length; i++) {
            this.textures[i] = new Texture(textures[i]);
            this.textures[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.timeBetweenFrames = timeBetweenFrames;
        elapsedTime = 0;
        state = 0;
    }

    public void update(float dTime) {
        elapsedTime += dTime;
        if (elapsedTime >= timeBetweenFrames) {
            state++;
            elapsedTime = 0;
            if (state >= textures.length) state = 0;
        }
    }

    public void draw(SpriteBatch sb) {
        sb.draw(textures[state], x, y, width, height);
    }

    public void dispose() {
        for (int i = 0; i < textures.length; i++) {
            textures[i].dispose();
        }
    }
}