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
    // №2
    private Sprite background_2;
    private Button[] neighbors;
    // №3
    private Sprite background_3;
    private Button next_3;
    private Button[] lives;
    private Button[] births;

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
        background_2 = new Sprite("newGameBG2.png", 0, 0, 768, 432);
        neighbors = new Button[8];
        for (int i = 0; i < neighbors.length; i++) {
            if (i == 0 || i == 1 || i == 2)
                neighbors[i] = new Button("newGameNeighborsOff.png", "newGameNeighborsOn.png", 289 + i*67, 245, 57, 57);
            if (i == 3)
                neighbors[i] = new Button("newGameNeighborsOff.png", "newGameNeighborsOn.png", 289, 178, 57, 57);
            if (i == 4)
                neighbors[i] = new Button("newGameNeighborsOff.png", "newGameNeighborsOn.png", 289 + 67*2, 178, 57, 57);
            if (i == 5 || i == 6 || i == 7)
                neighbors[i] = new Button("newGameNeighborsOff.png", "newGameNeighborsOn.png", 289 + i*67 - 67*5, 111, 57, 57);
        }
    }

    private void initScreen3() {
        background_3 = new Sprite("newGameBG3.png", 0, 0, 768, 432);
        next_3 = new Button("newGameNext3.png", 296, 53, 176, 34);
        lives = new Button[8];
        for (int i = 0; i < lives.length; i++) {
            if (i == 0 || i == 1 || i == 2)
                lives[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 166 + i*58, 216, 50, 50);
            if (i == 3)
                lives[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 166, 158, 50, 50);
            if (i == 4)
                lives[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 166 + 58*2, 158, 50, 50);
            if (i == 5 || i == 6 || i == 7)
                lives[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 166 + i*58 - 58*5, 100, 50, 50);
        }
        births = new Button[8];
        for (int i = 0; i < births.length; i++) {
            if (i == 0 || i == 1 || i == 2)
                births[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 427 + i*58, 216, 50, 50);
            if (i == 3)
                births[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 427, 158, 50, 50);
            if (i == 4)
                births[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 427 + 58*2, 158, 50, 50);
            if (i == 5 || i == 6 || i == 7)
                births[i] = new Button("newGameLifeBirthOff" + i + ".png", "newGameLifeBirthOn" + i + ".png", 427 + i*58 - 58*5, 100, 50, 50);
        }
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
            tempDown.set(-1, -1, 0);
            gsm.set(new StartMenu(gsm));
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
                if (horizontal.isActivity() && horizontal.getValue().length() < 4) horizontal.setValue(horizontal.getValue() + i);
                if (vertical.isActivity()  && vertical.getValue().length() < 4) vertical.setValue(vertical.getValue() + i);
                tempDown.set(-1, -1, 0);
            }
        }
    }

    private void handleInputScreen2() {
        if (next_1_2.isClick(tempDown.x, tempDown.y)) {
            state++;
            tempDown.set(-1, -1, 0);
        }
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i].isClick(tempDown.x, tempDown.y)) {
                neighbors[i].click();
                activeNeighbors[i] = neighbors[i].getIsClick();
                tempDown.set(-1, -1, 0);
            }
        }
    }

    private void handleInputScreen3() {
        if (next_3.isClick(tempDown.x, tempDown.y)) {
            tempDown.set(-1, -1, 0);
            gsm.set(new Game(gsm, conditionOfLife, conditionOfBirth, activeNeighbors, arenaWidth, arenaHeight));
        }
        for (int i = 0; i < lives.length; i++) {
            if (lives[i].isClick(tempDown.x, tempDown.y)) {
                lives[i].click();
                if (lives[i].getIsClick()) conditionOfLife.add(i+1);
                else conditionOfLife.remove((Integer)(i+1));
                tempDown.set(-1, -1, 0);
            }
        }
        for (int i = 0; i < births.length; i++) {
            if (births[i].isClick(tempDown.x, tempDown.y)) {
                births[i].click();
                if (births[i].getIsClick()) conditionOfBirth.add(i+1);
                else conditionOfBirth.remove((Integer)(i+1));
                tempDown.set(-1, -1, 0);
            }
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
        background_2.draw(sb);
        next_1_2.draw(sb);
        for (Button neighbor : neighbors) {
            neighbor.draw(sb);
        }
    }

    private void renderScreen3(SpriteBatch sb) {
        background_3.draw(sb);
        next_3.draw(sb);
        for (Button life : lives) {
            life.draw(sb);
        }
        for (Button birth : births) {
            birth.draw(sb);
        }
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
        background_2.dispose();
        for (Button neighbor : neighbors) {
            neighbor.dispose();
        }
    }

    private void disposeScreen3() {
        background_3.dispose();
        next_3.dispose();
        for (Button life : lives) {
            life.dispose();
        }
        for (Button birth : births) {
            birth.dispose();
        }
    }
}