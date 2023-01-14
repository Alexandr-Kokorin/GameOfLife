package com.gameoflife.game.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class Arena {

    private final Cell[][] cells;
    private final int[][] temp;
    private final int width, height;
    private final Texture[] cellsTexture;
    private int minX, maxX, minY, maxY;
    private int begin_i, end_i, begin_j, end_j;
    private final int cellSize;
    private int indexTemp;
    private final ArrayList<Integer> conditionOfLife;
    private final ArrayList<Integer> conditionOfBirth;
    private final boolean[] activeNeighbors;

    public Arena(int width, int height, float dZoom, ArrayList<Integer> conditionOfLife, ArrayList<Integer> conditionOfBirth, boolean[] activeNeighbors) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        cellSize = (int) (10 * dZoom);
        temp = new int[width * height][3];
        indexTemp = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i * cellSize, j * cellSize);
            }
        }
        cellsTexture = new Texture[4];
        cellsTexture[0] = new Texture("deadCell.png");
        cellsTexture[1] = new Texture("liveCell.png");
        cellsTexture[2] = new Texture("liveCell2.png");
        cellsTexture[3] = new Texture("liveCell3.png");
        minX = width;
        maxX = 0;
        minY = height;
        maxY = 0;
        this.conditionOfLife = conditionOfLife;
        this.conditionOfBirth = conditionOfBirth;
        this.activeNeighbors = activeNeighbors;
        //this.conditionOfLife = new ArrayList<>();
        //conditionOfLife.add(2);
        //conditionOfLife.add(3);
        //this.conditionOfBirth = new ArrayList<>();
        //conditionOfBirth.add(3);
        //this.activeNeighbors = new boolean[8];
        //activeNeighbors[0] = true;
        //activeNeighbors[1] = true;
        //activeNeighbors[2] = true;
        //activeNeighbors[3] = true;
        //activeNeighbors[4] = true;
        //activeNeighbors[5] = true;
        //activeNeighbors[6] = true;
        //activeNeighbors[7] = true;
    }

    public void updatePress(float tempPosX, float tempPosY, float dx, float dy, boolean isPainting, boolean isErasure) {
        int i = (int) (tempPosX / cellSize), j = (int) (tempPosY / cellSize);
        if (i >= 0 && j >= 0 && i < width && j < height) {
            updatePressMini(i, j, isPainting, isErasure);
            if (isPainting || isErasure) {
                while (dx >= 1 || dy >= 1 || dx <= -1 || dy <= -1) {
                    i = (int) ((tempPosX + dx) / cellSize);
                    j = (int) ((tempPosY - dy) / cellSize);
                    updatePressMini(i, j, isPainting, isErasure);
                    if (dx < 0) dx = dx + 1;
                    else if (dx > 0) dx = dx - 1;
                    if (dy < 0) dy = dy + 1;
                    else if (dy > 0) dy = dy - 1;
                }
            }
        }
    }

    private void updatePressMini(int i, int j, boolean isPainting, boolean isErasure) {
        try {
            if (!cells[i][j].isAlive && !isErasure) {
                cells[i][j].birth();
                changingNeighbors(i, j, 1);
                if (minX > i) minX = i;
                if (maxX < i) maxX = i;
                if (minY > j) minY = j;
                if (maxY < j) maxY = j;
            } else if (cells[i][j].isAlive && !isPainting) {
                cells[i][j].death();
                changingNeighbors(i, j, -1);
            }
        } catch (Exception e) {
        }
    }

    public void updateProgress() {
        lifeAndDeath();
        countingNeighbors();
        changingUpdateBoundaries();
    }

    private void lifeAndDeath() {
        boolean flag;
        for (int i = minX - 1; i < maxX + 2; i++) {
            for (int j = minY - 1; j < maxY + 2; j++) {
                try {
                    if (cells[i][j].isAlive) {
                        flag = true;
                        for(Integer condition: conditionOfLife) {
                            if (cells[i][j].neighbors == condition) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            cells[i][j].death();
                            temp[indexTemp][0] = i;
                            temp[indexTemp][1] = j;
                            temp[indexTemp][2] = -1;
                            indexTemp++;
                        } else cells[i][j].addColor();
                    }
                    else {
                        flag = false;
                        for(Integer condition: conditionOfBirth) {
                            if (cells[i][j].neighbors == condition) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            cells[i][j].birth();
                            temp[indexTemp][0] = i;
                            temp[indexTemp][1] = j;
                            temp[indexTemp][2] = 1;
                            indexTemp++;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void countingNeighbors() {
        for (int i = 0; i < indexTemp; i++) {
            changingNeighbors(temp[i][0], temp[i][1], temp[i][2]);
        }
        indexTemp = 0;
    }

    private void changingNeighbors(int x, int y, int index) {
        try {
            if (activeNeighbors[2]) cells[x - 1][y - 1].neighbors += index;
        } catch (Exception e) {}
        try {
            if (activeNeighbors[4]) cells[x - 1][y].neighbors += index;
        } catch (Exception e) {}
        try {
            if (activeNeighbors[7]) cells[x - 1][y + 1].neighbors += index;
        } catch (Exception e) {}
        try {
            if (activeNeighbors[1]) cells[x][y - 1].neighbors += index;
        } catch (Exception e) {}
        try {
            if (activeNeighbors[6]) cells[x][y + 1].neighbors += index;
        } catch (Exception e) {}
        try {
            if (activeNeighbors[0]) cells[x + 1][y - 1].neighbors += index;
        } catch (Exception e) {}
        try {
            if (activeNeighbors[3]) cells[x + 1][y].neighbors += index;
        } catch (Exception e) {}
        try {
            if (activeNeighbors[5]) cells[x + 1][y + 1].neighbors += index;
        } catch (Exception e) {}
    }

    private void changingUpdateBoundaries() {
        if ((maxX - minX + 1) * (maxY - minY + 1) <= width * height / 2) {
            int tempMinX = width;
            int tempMaxX = 0;
            int tempMinY = height;
            int tempMaxY = 0;
            for (int i = minX - 1; i < maxX + 2; i++) {
                for (int j = minY - 1; j < maxY + 2; j++) {
                    try {
                        if (cells[i][j].isAlive) {
                            if (tempMinX > i) tempMinX = i;
                            if (tempMaxX < i) tempMaxX = i;
                            if (tempMinY > j) tempMinY = j;
                            if (tempMaxY < j) tempMaxY = j;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            minX = tempMinX;
            maxX = tempMaxX;
            minY = tempMinY;
            maxY = tempMaxY;
        } else {
            minX = 0;
            maxX = width - 1;
            minY = 0;
            maxY = height - 1;
        }
    }

    private void definingDrawingBoundaries(float camPosX, float camPosY, float camZoom, float dx, float dy) {
        if ((int) (camPosX - 384 * dx * camZoom) / cellSize < 0) {
            begin_i = 0;
        } else {
            begin_i = (int) (camPosX - 384 * dx * camZoom) / cellSize;
        }
        if ((int) (camPosX + 384 * dx * camZoom) / cellSize + 1 > width) {
            end_i = width;
        } else {
            end_i = (int) (camPosX + 384 * dx * camZoom) / cellSize + 1;
        }
        if ((int) (camPosY - 216 * dy * camZoom) / cellSize < 0) {
            begin_j = 0;
        } else {
            begin_j = (int) (camPosY - 216 * dy * camZoom) / cellSize;
        }
        if ((int) (camPosY + 216 * dy * camZoom) / cellSize + 1 > height) {
            end_j = height;
        } else {
            end_j = (int) (camPosY + 216 * dy * camZoom) / cellSize + 1;
        }
    }

    public void draw(SpriteBatch sb, float camPosX, float camPosY, float camZoom, float dx, float dy) {
        if (camZoom <= 1.5) {
            definingDrawingBoundaries(camPosX, camPosY, camZoom, dx, dy);
            sb.begin();
            sb.disableBlending();
            for (int i = begin_i; i < end_i; i++) {
                for (int j = begin_j; j < end_j; j++) {
                    sb.draw(cellsTexture[cells[i][j].color], cells[i][j].x, cells[i][j].y, cellSize, cellSize);
                }
            }
            sb.enableBlending();
            sb.end();
        } else {
            if (Math.sqrt(Math.pow(maxX - minX, 2) + Math.pow(maxY - minY, 2)) < Math.sqrt(Math.pow(((camPosX + 384 * dx * camZoom) / cellSize + 1) - ((camPosX - 384 * dx * camZoom) / cellSize - 1), 2) + Math.pow(((camPosY + 216 * dy * camZoom) / cellSize + 1) - ((camPosY - 216 * dy * camZoom) / cellSize - 1), 2))) {
                sb.begin();
                sb.disableBlending();
                for (int i = minX; i < maxX + 1; i++) {
                    for (int j = minY; j < maxY + 1; j++) {
                        if (cells[i][j].isAlive) {
                            sb.draw(cellsTexture[cells[i][j].color], cells[i][j].x, cells[i][j].y, cellSize, cellSize);
                        }
                    }
                }
                sb.enableBlending();
                sb.end();
            } else {
                definingDrawingBoundaries(camPosX, camPosY, camZoom, dx, dy);
                sb.begin();
                sb.disableBlending();
                for (int i = begin_i; i < end_i; i++) {
                    for (int j = begin_j; j < end_j; j++) {
                        if (cells[i][j].isAlive) {
                            sb.draw(cellsTexture[cells[i][j].color], cells[i][j].x, cells[i][j].y, cellSize, cellSize);
                        }
                    }
                }
                sb.enableBlending();
                sb.end();
            }
        }
    }

    public void dispose() {
        for (int i = 0; i < cellsTexture.length; i++) {
            cellsTexture[i].dispose();
        }
    }
}