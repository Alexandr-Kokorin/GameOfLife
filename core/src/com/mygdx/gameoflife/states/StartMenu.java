package com.mygdx.gameoflife.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gameoflife.controlElements.Button2;

public class StartMenu extends State {

    Texture[] BGTextures;
    Button2 newGame, loadGame, settings;
    int state;
    double timeBetween;

    public StartMenu(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 768, 432);
        camera.position.set(384,216, 0);
        BGTextures = new Texture[3];
        BGTextures[0] = new Texture("startMenuBG1.png");
        BGTextures[1] = new Texture("startMenuBG1.png");
        BGTextures[2] = new Texture("startMenuBG1.png");
        BGTextures[0].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        BGTextures[1].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        BGTextures[2].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        state = 0;
        timeBetween = 0;
        newGame = new Button2("newGame.png",118,336,54,73);
        loadGame = new Button2("loadGame.png",20,236,71,73);
        settings = new Button2("settings.png",29,336,52,73);
    }

    @Override
    protected void handleInput() {
        if (newGame.isClick(tempDown.x,tempDown.y)) {
            gsm.set(new Game(gsm));
        }
        if (loadGame.isClick(tempDown.x,tempDown.y)) {

        }
        if (settings.isClick(tempDown.x,tempDown.y)) {
            gsm.set(new Settings(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        timeBetween += dt;
        if (timeBetween > 0.5) {
            state++;
            timeBetween = 0;
            if (state > 2) state = 0;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(BGTextures[state],0,0,768,432);
        newGame.draw(sb);
        loadGame.draw(sb);
        settings.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        for (int i = 0; i < BGTextures.length; i++) {
            BGTextures[i].dispose();
        }
        newGame.dispose();
        loadGame.dispose();
        settings.dispose();
    }
}