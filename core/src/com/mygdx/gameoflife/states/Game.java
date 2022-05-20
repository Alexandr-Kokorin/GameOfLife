package com.mygdx.gameoflife.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameoflife.controlElements.Button;
import com.mygdx.gameoflife.gameObject.Arena;

public class Game extends State {

    Arena arena;
    double timeBetweenMoves;
    float camZoom;
    float windowWidth,windowHeight,dx,dy,dzoom;
    double distanceTouches,dDistanceTouches;
    boolean stateOfTheGame,isMultyTouche,isPainting,isErasure;
    Button runAndStop,plus,minus,pen,lastick;
    Vector2 moveMap;

    public Game(GameStateManager gsm) {
        super(gsm);
        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        dx = windowWidth/768;
        dy = windowHeight/432;
        dzoom = (dx+dy)/2;
        camera.setToOrtho(false, windowWidth, windowHeight);
        camera.position.set((int)(10*dzoom)*250,(int)(10*dzoom)*250, 0);
        camZoom = 1;
        camera.zoom = camZoom;
        arena = new Arena(500,500,dzoom);
        runAndStop = new Button("start.png",camera.position.x,camera.position.y,-380*dx,-212*dy,50*dzoom,50*dzoom);
        plus = new Button("plus.png",camera.position.x,camera.position.y,380*dx-50*dzoom,5*dy,50*dzoom,50*dzoom);
        minus = new Button("minus.png",camera.position.x,camera.position.y,380*dx-50*dzoom,-5*dy-50*dzoom,50*dzoom,50*dzoom);
        pen = new Button("pen.png",camera.position.x,camera.position.y,-380*dx,212*dy-50*dzoom,50*dzoom,50*dzoom);
        lastick = new Button("lastick.png",camera.position.x,camera.position.y,-380*dx,212*dy-100*dzoom-10*dy,50*dzoom,50*dzoom);
        timeBetweenMoves = 0;
        distanceTouches = 0;
        dDistanceTouches = 0;
        moveMap = new Vector2(0,0);
        stateOfTheGame = false;
        isMultyTouche = false;
        isPainting = false;
        isErasure = false;
    }

    @Override
    protected void handleInput() {
        if (runAndStop.rectangle.contains(tempDown.x,tempDown.y) && !stateOfTheGame){
            stateOfTheGame = true;
            runAndStop.newTexture("stop.png");
            isPainting = false;
            isErasure = false;
            tempDown.set(-1,-1,0);
            return;
        }
        if (runAndStop.rectangle.contains(tempDown.x,tempDown.y) && stateOfTheGame){
            stateOfTheGame = false;
            runAndStop.newTexture("start.png");
            tempDown.set(-1,-1,0);
            return;
        }
        if (plus.rectangle.contains(tempDown.x,tempDown.y) || minus.rectangle.contains(tempDown.x,tempDown.y)) {
            if (plus.rectangle.contains(tempDown.x,tempDown.y)) {
                if (camZoom > 0.1 && camZoom <= 0.25) camZoom -= 0.05;
                if (camZoom > 0.25 && camZoom <= 1.5) camZoom -= 0.25;
                if (camZoom > 1.5 && camZoom <= 4) camZoom -= 0.5;
                if (camZoom > 4 && camZoom <= 10) camZoom -= 1;
                if (camZoom > 10 && camZoom <= 50) camZoom -= 2.5;
                if (camZoom < 0.1) camZoom = 0.1f;
            }
            if (minus.rectangle.contains(tempDown.x,tempDown.y)) {
                if (camZoom >= 0.1 && camZoom < 0.25) camZoom += 0.05;
                if (camZoom >= 0.25 && camZoom < 1.5) camZoom += 0.25;
                if (camZoom >= 1.5 && camZoom < 4) camZoom += 0.5;
                if (camZoom >= 4 && camZoom < 10) camZoom += 1;
                if (camZoom >= 10 && camZoom < 50) camZoom += 2.5;
                if (camZoom > 50) camZoom = 50;
            }
            updateButtonWH();
            camera.zoom = camZoom;
            tempDown.set(-1,-1,0);
            return;
        }
        if (pen.rectangle.contains(tempDown.x,tempDown.y) && !isPainting && !stateOfTheGame){
            isPainting = true;
            isErasure = false;
            tempDown.set(-1,-1,0);
            tempDrag.set(-1,-1,0);
            return;
        }
        if (pen.rectangle.contains(tempDown.x,tempDown.y) && isPainting){
            isPainting = false;
            tempDown.set(-1,-1,0);
            return;
        }
        if (lastick.rectangle.contains(tempDown.x,tempDown.y) && !isErasure && !stateOfTheGame){
            isPainting = false;
            isErasure = true;
            tempDown.set(-1,-1,0);
            tempDrag.set(-1,-1,0);
            return;
        }
        if (lastick.rectangle.contains(tempDown.x,tempDown.y) && isErasure) {
            isErasure = false;
            tempDown.set(-1, -1, 0);
            return;
        }
        if (tempDown.x > tempUp.x-2 && tempDown.x < tempUp.x+2 && tempDown.y > tempUp.y-2 && tempDown.y < tempUp.y+2 && !stateOfTheGame && !isPainting && !isErasure && camZoom <= 3) {
            arena.updatePress(tempDown.x,tempDown.y, drag.x,drag.y,isPainting,isErasure);
            tempDown.set(-1,-1,0);
        }
        if ((isPainting || isErasure) && tempDrag.x != -1 && tempDrag.y != -1 && !stateOfTheGame && camZoom <= 3 && !pen.rectangle.contains(tempDrag.x,tempDrag.y)) {
            arena.updatePress(tempDrag.x,tempDrag.y, drag.x,drag.y,isPainting,isErasure);
            tempDrag.set(-1,-1,0);
        }
    }

    public void zooming(){
        if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
            if (distanceTouches == 0) {
                distanceTouches = Math.sqrt(Math.pow(Gdx.input.getX(0) - Gdx.input.getX(1), 2) + Math.pow(Gdx.input.getY(0) - Gdx.input.getY(1), 2));
                isMultyTouche = true;
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
        if ((!Gdx.input.isTouched(0) || !Gdx.input.isTouched(1)) && isMultyTouche) {
            distanceTouches = 0;
            dDistanceTouches = 0;
            isMultyTouche = false;
        }
    }

    public void updateButtonWH(){
        runAndStop.updateWH(camZoom);
        plus.updateWH(camZoom);
        minus.updateWH(camZoom);
        pen.updateWH(camZoom);
        lastick.updateWH(camZoom);
    }

    @Override
    public void update(float dt) {
        handleInput();
        zooming();
        runAndStop.updateXY(camera.position.x,camera.position.y);
        plus.updateXY(camera.position.x,camera.position.y);
        minus.updateXY(camera.position.x,camera.position.y);
        pen.updateXY(camera.position.x,camera.position.y);
        lastick.updateXY(camera.position.x,camera.position.y);
        timeBetweenMoves += dt;
        if (timeBetweenMoves >= 0.1) timeBetweenMoves = 0.1;
        if (stateOfTheGame && timeBetweenMoves == 0.1){
            arena.updateProgress();
            timeBetweenMoves = 0;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        arena.draw(sb,camera.position.x,camera.position.y,camZoom,dx,dy);
        sb.begin();
        sb.draw(runAndStop.texture,runAndStop.rectangle.x,runAndStop.rectangle.y,runAndStop.rectangle.width,runAndStop.rectangle.height);
        sb.draw(plus.texture,plus.rectangle.x,plus.rectangle.y,plus.rectangle.width,plus.rectangle.height);
        sb.draw(minus.texture,minus.rectangle.x,minus.rectangle.y,minus.rectangle.width,minus.rectangle.height);
        sb.draw(pen.texture,pen.rectangle.x,pen.rectangle.y,pen.rectangle.width,pen.rectangle.height);
        sb.draw(lastick.texture,lastick.rectangle.x,lastick.rectangle.y,lastick.rectangle.width,lastick.rectangle.height);
        sb.end();
        if (!isMultyTouche && !isPainting && !isErasure) {
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
        lastick.dispose();
    }
}
