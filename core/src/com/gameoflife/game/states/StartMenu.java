package com.gameoflife.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameoflife.game.controlElements.AnimatedSprite;
import com.gameoflife.game.controlElements.Button;

public class StartMenu extends State {

    private final AnimatedSprite background;
    private final Button newGame, loadGame, settings;

    public StartMenu(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 768, 432);
        camera.position.set(384, 216, 0);
        background = new AnimatedSprite(new String[]{"startMenuBG1.png", "startMenuBG1.png", "startMenuBG1.png"}, 0, 0, 768, 432, 0.5);
        newGame = new Button("newGame.png", 118, 336, 54, 73);
        loadGame = new Button("loadGame.png", 20, 236, 71, 73);
        settings = new Button("settings.png", 29, 336, 52, 73);
    }

    @Override
    protected void handleInput() {
        if (newGame.isClick(tempDown.x, tempDown.y)) {
            //gsm.set(new Game(gsm));
            gsm.set(new NewGame(gsm));
        }
        if (loadGame.isClick(tempDown.x, tempDown.y)) {

        }
        if (settings.isClick(tempDown.x, tempDown.y)) {
            gsm.set(new Settings(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        background.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        background.draw(sb);
        newGame.draw(sb);
        loadGame.draw(sb);
        settings.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        newGame.dispose();
        loadGame.dispose();
        settings.dispose();
    }
}