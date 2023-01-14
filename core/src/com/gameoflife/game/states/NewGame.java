package com.gameoflife.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameoflife.game.controlElements.Button;
import com.gameoflife.game.controlElements.Sprite;
import java.util.ArrayList;

public class NewGame extends State {

    private ArrayList<Integer> conditionOfLife;
    private ArrayList<Integer> conditionOfBirth;
    private boolean[] activeNeighbors;
    private int state;

    private final Button close;

    private final Sprite background_0;
    private final Button next_0;

    public NewGame(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 768, 432);
        camera.position.set(384, 216, 0);
        conditionOfLife = new ArrayList<>();
        conditionOfBirth = new ArrayList<>();
        activeNeighbors = new boolean[8];
        state = 0;

        close = new Button("newGameClose.png", 666, 358, 22, 22);
        // №0
        background_0 = new Sprite("newGameBG0.png", 0, 0, 768, 432);
        next_0 = new Button("newGameNext0.png", 296, 101, 176, 34);
    }

    @Override
    protected void handleInput() {
        if (state == 0) {
            if (next_0.isClick(tempDown.x, tempDown.y)) {
                state++;
                tempDown.set(-1, -1, 0);
            }
        }
        if (state == 1) {

        }
        if (state == 2) {

        }
        if (state == 3) {

        }
        if (close.isClick(tempDown.x, tempDown.y)) {
            gsm.set(new StartMenu(gsm));
            tempDown.set(-1, -1, 0);
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
        if (state == 0) {
            background_0.draw(sb);
            next_0.draw(sb);
        }
        if (state == 1) {

        }
        if (state == 2) {

        }
        if (state == 3) {

        }
        close.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        close.dispose();
        background_0.dispose();
        next_0.dispose();
    }
}