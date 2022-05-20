package com.mygdx.gameoflife.controlElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button2 {

    private Texture texture, textureClick;
    private Rectangle rectangle;
    private float x,y;
    public boolean isClick;

    public Button2(String texture, float x, float y, float width, float height) {
        this.texture = new Texture(texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rectangle = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
    }

    public Button2(String texture, String textureClick, float x, float y, float width, float height) {
        this.texture = new Texture(texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.textureClick = new Texture(textureClick);
        this.textureClick.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rectangle = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
    }

    public boolean isClick(float touchePosX, float touchePosY) {
        return rectangle.contains(touchePosX,touchePosY);
    }

    public void update(float camPosX, float camPosY){
        rectangle.x = camPosX + x;
        rectangle.y = camPosY + y;
    }

    public void draw(SpriteBatch sb) {
        if (!isClick) sb.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        else sb.draw(textureClick, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void dispose() {
        texture.dispose();
        if (textureClick != null) textureClick.dispose();
    }
}