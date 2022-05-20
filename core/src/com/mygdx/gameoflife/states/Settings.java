package com.mygdx.gameoflife.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gameoflife.controlElements.Button2;

public class Settings extends State {

    Texture BGTexture;
    Button2 close;

    public Settings(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 768, 432);
        camera.position.set(384,216, 0);
        BGTexture = new Texture("settingsBG.png");
        BGTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        close = new Button2("settingsClose.png",511,334,22,22);
    }

    @Override
    protected void handleInput() {
        if (close.isClick(tempDown.x,tempDown.y)) {
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
        sb.draw(BGTexture,0,0,768,432);
        close.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        BGTexture.dispose();
        close.dispose();
    }
}
