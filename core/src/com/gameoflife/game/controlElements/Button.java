package com.gameoflife.game.controlElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {

    private final Texture texture, textureClick;
    private final Rectangle rectangle;
    private final float x, y, width, height;
    private float zoomX, zoomY;
    private boolean isClick;

    public Button(String texture, float x, float y, float width, float height) {
        this.texture = new Texture(texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureClick = null;
        rectangle = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        zoomX = x;
        zoomY = y;
    }

    public Button(String texture, String textureClick, float x, float y, float width, float height) {
        this.texture = new Texture(texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.textureClick = new Texture(textureClick);
        this.textureClick.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rectangle = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        zoomX = x;
        zoomY = y;
    }

    public boolean isClick(float touchePosX, float touchePosY) {
        return rectangle.contains(touchePosX, touchePosY);
    }

    public void click() {
        isClick = !isClick;
    }

    public void updatePosition(float camPosX, float camPosY) {
        rectangle.x = camPosX + zoomX;
        rectangle.y = camPosY + zoomY;
    }

    public void updateSize(float camZoom) {
        rectangle.width = width * camZoom;
        rectangle.height = height * camZoom;
        zoomX = x * camZoom;
        zoomY = y * camZoom;
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