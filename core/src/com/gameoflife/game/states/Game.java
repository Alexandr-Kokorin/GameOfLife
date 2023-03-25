package com.gameoflife.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameoflife.game.controlElements.Button;
import com.gameoflife.game.gameObject.Arena;
import java.util.ArrayList;

public class Game extends State {

    private final Arena arena;
    private double timeBetweenMoves;
    private float camZoom;
    private final float dx, dy;
    private double distanceTouches, dDistanceTouches;
    private boolean stateOfTheGame, isMultiTouch, isPainting, isErasure;
    private final Button runAndStop, plus, minus, pen, eraser;

    public Game(GameStateManager gsm, ArrayList<Integer> conditionOfLife, ArrayList<Integer> conditionOfBirth, boolean[] activeNeighbors, int arenaWidth, int arenaHeight) {
        super(gsm);
        float windowWidth = Gdx.graphics.getWidth();
        float windowHeight = Gdx.graphics.getHeight();
        dx = windowWidth / 768;
        dy = windowHeight / 432;
        float dZoom = (dx + dy) / 2;
        camera.setToOrtho(false, windowWidth, windowHeight);
        camera.position.set((int)((10 * dZoom) * arenaWidth/2), (int)((10 * dZoom) * arenaHeight/2), 0);
        camZoom = 1;
        camera.zoom = camZoom;
        arena = new Arena(arenaWidth, arenaHeight, dZoom, conditionOfLife, conditionOfBirth, activeNeighbors);
        runAndStop = new Button("start.png", "stop.png", -380 * dx, -212 * dy, 50 * dZoom, 50 * dZoom);
        plus = new Button("plus.png", 380 * dx - 50 * dZoom, 5 * dy, 50 * dZoom, 50 * dZoom);
        minus = new Button("minus.png", 380 * dx - 50 * dZoom, -5 * dy - 50 * dZoom, 50 * dZoom, 50 * dZoom);
        pen = new Button("pen.png", -380 * dx, 212 * dy - 50 * dZoom, 50 * dZoom, 50 * dZoom);
        eraser = new Button("lastick.png", -380 * dx, 212 * dy - 100 * dZoom - 10 * dy, 50 * dZoom, 50 * dZoom);
        timeBetweenMoves = 0;
        distanceTouches = 0;
        dDistanceTouches = 0;
        stateOfTheGame = false;
        isMultiTouch = false;
        isPainting = false;
        isErasure = false;
    }

    @Override
    protected void handleInput() {
        if (runAndStop.isClick(tempDown.x, tempDown.y) && !stateOfTheGame) {
            runAndStop.click();
            stateOfTheGame = true;
            isPainting = false;
            isErasure = false;
            tempDown.set(-1, -1, 0);
            return;
        }
        if (runAndStop.isClick(tempDown.x, tempDown.y) && stateOfTheGame) {
            runAndStop.click();
            stateOfTheGame = false;
            tempDown.set(-1, -1, 0);
            return;
        }
        if (plus.isClick(tempDown.x, tempDown.y) || minus.isClick(tempDown.x, tempDown.y)) {
            if (plus.isClick(tempDown.x, tempDown.y)) {
                if (camZoom > 0.1 && camZoom <= 0.25) camZoom -= 0.05;
                if (camZoom > 0.25 && camZoom <= 1.5) camZoom -= 0.25;
                if (camZoom > 1.5 && camZoom <= 4) camZoom -= 0.5;
                if (camZoom > 4 && camZoom <= 10) camZoom -= 1;
                if (camZoom > 10 && camZoom <= 50) camZoom -= 2.5;
                if (camZoom < 0.1) camZoom = 0.1f;
            }
            if (minus.isClick(tempDown.x, tempDown.y)) {
                if (camZoom >= 0.1 && camZoom < 0.25) camZoom += 0.05;
                if (camZoom >= 0.25 && camZoom < 1.5) camZoom += 0.25;
                if (camZoom >= 1.5 && camZoom < 4) camZoom += 0.5;
                if (camZoom >= 4 && camZoom < 10) camZoom += 1;
                if (camZoom >= 10 && camZoom < 50) camZoom += 2.5;
                if (camZoom > 50) camZoom = 50;
            }
            updateButtonWH();
            camera.zoom = camZoom;
            tempDown.set(-1, -1, 0);
            return;
        }
        if (pen.isClick(tempDown.x, tempDown.y) && !isPainting && !stateOfTheGame) {
            isPainting = true;
            isErasure = false;
            tempDown.set(-1, -1, 0);
            tempDrag.set(-1, -1, 0);
            return;
        }
        if (pen.isClick(tempDown.x, tempDown.y) && isPainting) {
            isPainting = false;
            tempDown.set(-1, -1, 0);
            return;
        }
        if (eraser.isClick(tempDown.x, tempDown.y) && !isErasure && !stateOfTheGame) {
            isPainting = false;
            isErasure = true;
            tempDown.set(-1, -1, 0);
            tempDrag.set(-1, -1, 0);
            return;
        }
        if (eraser.isClick(tempDown.x, tempDown.y) && isErasure) {
            isErasure = false;
            tempDown.set(-1, -1, 0);
            return;
        }
        if (tempDown.x > tempUp.x - 2 && tempDown.x < tempUp.x + 2 && tempDown.y > tempUp.y - 2 && tempDown.y < tempUp.y + 2 && !stateOfTheGame && !isPainting && !isErasure && camZoom <= 3) {
            arena.updatePress(tempDown.x, tempDown.y, drag.x, drag.y, isPainting, isErasure);
            tempDown.set(-1, -1, 0);
        }
        if ((isPainting || isErasure) && tempDrag.x != -1 && tempDrag.y != -1 && !stateOfTheGame && camZoom <= 3 && !pen.isClick(tempDrag.x, tempDrag.y)) {
            arena.updatePress(tempDrag.x, tempDrag.y, drag.x, drag.y, isPainting, isErasure);
            tempDrag.set(-1, -1, 0);
        }
    }

    public void zooming() {
        if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
            if (distanceTouches == 0) {
                distanceTouches = Math.sqrt(Math.pow(Gdx.input.getX(0) - Gdx.input.getX(1), 2) + Math.pow(Gdx.input.getY(0) - Gdx.input.getY(1), 2));
                isMultiTouch = true;
            } else {
                double dt = Math.sqrt(Math.pow(Gdx.input.getX(0) - Gdx.input.getX(1), 2) + Math.pow(Gdx.input.getY(0) - Gdx.input.getY(1), 2));
                dDistanceTouches = distanceTouches - dt;
                distanceTouches = dt;
                if ((camZoom > 0.1 && dDistanceTouches < 0) || (camZoom < 50 && dDistanceTouches > 0)) {
                    camZoom += dDistanceTouches / (750 / camZoom);
                    if (camZoom < 0.1) camZoom = 0.1f;
                    if (camZoom > 50) camZoom = 50;
                    updateButtonWH();
                    camera.zoom = camZoom;
                }
            }
        }
        if ((!Gdx.input.isTouched(0) || !Gdx.input.isTouched(1)) && isMultiTouch) {
            distanceTouches = 0;
            dDistanceTouches = 0;
            isMultiTouch = false;
        }
    }

    public void updateButtonWH() {
        runAndStop.updateSize(camZoom);
        plus.updateSize(camZoom);
        minus.updateSize(camZoom);
        pen.updateSize(camZoom);
        eraser.updateSize(camZoom);
    }

    @Override
    public void update(float dt) {
        handleInput();
        zooming();
        runAndStop.updatePosition(camera.position.x, camera.position.y);
        plus.updatePosition(camera.position.x, camera.position.y);
        minus.updatePosition(camera.position.x, camera.position.y);
        pen.updatePosition(camera.position.x, camera.position.y);
        eraser.updatePosition(camera.position.x, camera.position.y);
        timeBetweenMoves += dt;
        if (timeBetweenMoves >= 0.1) timeBetweenMoves = 0.1;
        if (stateOfTheGame && timeBetweenMoves == 0.1) {
            arena.updateProgress();
            timeBetweenMoves = 0;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        arena.draw(sb, camera.position.x, camera.position.y, camZoom, dx, dy);
        sb.begin();
        runAndStop.draw(sb);
        plus.draw(sb);
        minus.draw(sb);
        pen.draw(sb);
        eraser.draw(sb);
        sb.end();
        if (!isMultiTouch && !isPainting && !isErasure) {
            camera.translate((int) (drag.x * (-0.75) * camZoom), (int) (drag.y * (0.75) * camZoom), 0);
            drag.set(0, 0, 0);
        }
    }

    @Override
    public void dispose() {
        arena.dispose();
        runAndStop.dispose();
        plus.dispose();
        minus.dispose();
        pen.dispose();
        eraser.dispose();
    }
}