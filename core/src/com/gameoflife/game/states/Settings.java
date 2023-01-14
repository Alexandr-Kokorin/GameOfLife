package com.gameoflife.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameoflife.game.controlElements.Button;
import com.gameoflife.game.controlElements.Sprite;

public class Settings extends State {

    private final Sprite background;
    private final Button close;

    public Settings(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 768, 432);
        camera.position.set(384, 216, 0);
        background = new Sprite("settingsBG.png", 0, 0, 768, 432);
        close = new Button("settingsClose.png", 511, 334, 22, 22);
    }

    @Override
    protected void handleInput() {
        if (close.isClick(tempDown.x, tempDown.y)) {
            gsm.set(new StartMenu(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        background.draw(sb);
        close.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        close.dispose();
    }
}