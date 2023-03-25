package com.gameoflife.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameoflife.game.controlElements.Button;
import com.gameoflife.game.controlElements.Sprite;
import com.gameoflife.game.controlElements.TextField;

import java.util.ArrayList;
import java.util.Arrays;

public class NewGame extends State {

    private final ArrayList<Integer> conditionOfLife;
    private final ArrayList<Integer> conditionOfBirth;
    private final boolean[] activeNeighbors;
    private int arenaWidth;
    private int arenaHeight;
    private int state;
    // common
    private final Button close;
    // №0
    private Sprite background_0;
    private Button next_0;
    private Button defaultSettings;
    // №1
    private Sprite background_1;
    private Button next_1_2;
    private TextField horizontal;
    private TextField vertical;
    private Button[] numbers;

    public NewGame(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, 768, 432);
        camera.position.set(384, 216, 0);
        conditionOfLife = new ArrayList<>();
        conditionOfBirth = new ArrayList<>();
        activeNeighbors = new boolean[8];
        arenaWidth = 50;
        arenaHeight = 50;
        state = 0;
        close = new Button("newGameClose.png", 666, 358, 22, 22);
        initScreen0();
        initScreen1();
        initScreen2();
        initScreen3();
    }

    private void initScreen0() {
        background_0 = new Sprite("newGameBG0.png", 0, 0, 768, 432);
        next_0 = new Button("newGameNext0.png", 296, 139, 176, 34);
        defaultSettings = new Button("newGameDefSet.png", 296, 82, 176, 34);
    }

    private void initScreen1() {
        background_1 = new Sprite("newGameBG1.png", 0, 0, 768, 432);
        next_1_2 = new Button("newGameNext1-2.png", 296, 55, 176, 34);
        horizontal = new TextField("valueHorizontal.png", 253, 228);
        vertical = new TextField("valueVertical.png", 253, 185);
        numbers = new Button[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = new Button("newGameBtnNum" + i + ".png", 126 + i*53, 111, 40, 40);
        }
    }

    private void initScreen2() {

    }

    private void initScreen3() {

    }

    @Override
    protected void handleInput() {
        switch (state) {
            case 0: handleInputScreen0(); break;
            case 1: handleInputScreen1(); break;
            case 2: handleInputScreen2(); break;
            case 3: handleInputScreen3(); break;
        }
        if (close.isClick(tempDown.x, tempDown.y)) {
            gsm.set(new StartMenu(gsm));
            tempDown.set(-1, -1, 0);
        }
    }

    private void handleInputScreen0() {
        if (next_0.isClick(tempDown.x, tempDown.y)) {
            state++;
            tempDown.set(-1, -1, 0);
        }
        if (defaultSettings.isClick(tempDown.x, tempDown.y)) {
            conditionOfLife.add(2);
            conditionOfLife.add(3);
            conditionOfBirth.add(3);
            Arrays.fill(activeNeighbors, true);
            tempDown.set(-1, -1, 0);
            gsm.set(new Game(gsm, conditionOfLife, conditionOfBirth, activeNeighbors, 500, 500));
        }
    }

    private void handleInputScreen1() {
        if (next_1_2.isClick(tempDown.x, tempDown.y) && !horizontal.getValue().isEmpty() && !vertical.getValue().isEmpty()) {
            arenaWidth = Math.min(Math.max(50, Integer.parseInt(horizontal.getValue())), 500);
            arenaHeight = Math.min(Math.max(50, Integer.parseInt(vertical.getValue())), 500);
            state++;
            tempDown.set(-1, -1, 0);
        }
        if (horizontal.isClick(tempDown.x, tempDown.y)) {
            vertical.setActivity(false);
            horizontal.setActivity(true);
            horizontal.setValue("");
            tempDown.set(-1, -1, 0);
        }
        if (vertical.isClick(tempDown.x, tempDown.y)) {
            horizontal.setActivity(false);
            vertical.setActivity(true);
            vertical.setValue("");
            tempDown.set(-1, -1, 0);
        }
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i].isClick(tempDown.x, tempDown.y)) {
                if (horizontal.isActivity()) horizontal.setValue(horizontal.getValue() + i);
                if (vertical.isActivity()) vertical.setValue(vertical.getValue() + i);
                tempDown.set(-1, -1, 0);
            }
        }
    }

    private void handleInputScreen2() {

    }

    private void handleInputScreen3() {

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
        switch (state) {
            case 0: renderScreen0(sb); break;
            case 1: renderScreen1(sb); break;
            case 2: renderScreen2(sb); break;
            case 3: renderScreen3(sb); break;
        }
        close.draw(sb);
        sb.end();
    }

    private void renderScreen0(SpriteBatch sb) {
        background_0.draw(sb);
        next_0.draw(sb);
        defaultSettings.draw(sb);
    }

    private void renderScreen1(SpriteBatch sb) {
        background_1.draw(sb);
        next_1_2.draw(sb);
        horizontal.draw(sb);
        vertical.draw(sb);
        for (Button number : numbers) {
            number.draw(sb);
        }
    }

    private void renderScreen2(SpriteBatch sb) {

    }

    private void renderScreen3(SpriteBatch sb) {

    }

    @Override
    public void dispose() {
        close.dispose();
        disposeScreen0();
        disposeScreen1();
        disposeScreen2();
        disposeScreen3();
    }

    private void disposeScreen0() {
        background_0.dispose();
        next_0.dispose();
        defaultSettings.dispose();
    }

    private void disposeScreen1() {
        background_1.dispose();
        next_1_2.dispose();
        horizontal.dispose();
        vertical.dispose();
        for (Button number : numbers) {
            number.dispose();
        }
    }

    private void disposeScreen2() {

    }

    private void disposeScreen3() {

    }
}