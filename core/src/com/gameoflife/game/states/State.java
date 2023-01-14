package com.gameoflife.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    protected OrthographicCamera camera;
    protected GameStateManager gsm;
    protected Vector3 tempDown, tempUp, tempDrag, drag;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera();
        Gdx.input.setInputProcessor(new InputManager());
        tempDown = new Vector3(-1, -1, 0);
        tempUp = new Vector3(-10, -10, 0);
        tempDrag = new Vector3(-1, -1, 0);
        drag = new Vector3(0, 0, 0);
    }

    protected abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

    public class InputManager implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            tempDown.set(screenX, screenY, 0);
            camera.unproject(tempDown);
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            tempUp.set(screenX, screenY, 0);
            camera.unproject(tempUp);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            drag.set(Gdx.input.getDeltaX(), Gdx.input.getDeltaY(), 0);
            tempDrag.set(screenX, screenY, 0);
            camera.unproject(tempDrag);
            return true;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

    }

}