package com.gameoflife.game.controlElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class TextField {

    private final Rectangle rectangle;
    private final Texture line;
    private final Texture defaultValue;
    private final Texture[] numbers;
    private String value;
    private boolean isActive;

    public TextField(String defaultValue, float x, float y) {
        rectangle = new Rectangle(x, y, 263, 18);
        line = new Texture("line.png");
        line.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.defaultValue = new Texture(defaultValue);
        this.defaultValue.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        numbers = new Texture[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = new Texture("number" + i + ".png");
            numbers[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        value = "";
        isActive = false;
    }

    public boolean isClick(float touchePosX, float touchePosY) {
        return rectangle.contains(touchePosX, touchePosY);
    }

    public void setActivity(boolean active) {
        isActive = active;
    }

    public boolean isActivity() {
        return isActive;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void draw(SpriteBatch sb) {
        if (isActive) sb.draw(line, rectangle.x, rectangle.y, rectangle.width, 2);
        else sb.draw(line, rectangle.x, rectangle.y, rectangle.width, 1);
        if (!isActive && value.length() == 0) sb.draw(defaultValue, rectangle.x + 2, rectangle.y + 3, (int)(defaultValue.getWidth()/2), 8);
        else {
            int size = 0;
            for (int i = 0; i < value.length(); i++) {
                sb.draw(numbers[value.charAt(i) - '0'], rectangle.x + 2 + size, rectangle.y + 4, (int)(numbers[value.charAt(i) - '0'].getWidth()/4), 13);
                size += (int)(numbers[value.charAt(i) - '0'].getWidth()/4) + 2;
            }
        }
    }

    public void dispose() {
        line.dispose();
        defaultValue.dispose();
        for (Texture number : numbers) {
            number.dispose();
        }
    }
}
