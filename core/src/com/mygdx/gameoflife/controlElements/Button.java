package com.mygdx.gameoflife.controlElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button {

    public Texture texture;
    public Rectangle rectangle;
    float x,y,width,height;
    float xz,yz;

    public Button(String texture,float camPosX, float camPosY, float x, float y, float width, float height) {
        this.texture = new Texture(texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rectangle = new Rectangle(camPosX+x, camPosY+y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        xz = x;
        yz = y;
    }

    public void newTexture(String texture){
        this.texture = new Texture(texture);
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void updateXY(float camPosX, float camPosY){
        rectangle.x = camPosX + xz;
        rectangle.y = camPosY + yz;
    }

    public void updateWH(float camZoom){
        rectangle.width = width*camZoom;
        rectangle.height = height*camZoom;
        xz = x*camZoom;
        yz = y*camZoom;
    }

    public void dispose() {
        texture.dispose();
    }
}
