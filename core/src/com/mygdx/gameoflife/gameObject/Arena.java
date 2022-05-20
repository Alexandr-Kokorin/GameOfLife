package com.mygdx.gameoflife.gameObject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Arena {

    Cell[][] cells;
    int[][] temp;
    Cell cell;
    int width,height;
    Texture[] cellsTexture;
    int minX,maxX,minY,maxY;
    int tminX,tmaxX,tminY,tmaxY;
    int bi,ei,bj,ej;
    int size;
    int indexTemp;

    public Arena(int width, int height, float dzoom){
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        size = (int)(10*dzoom);
        temp = new int[width*height][3];
        indexTemp = 0;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                cell = new Cell(i * size, j * size, size, size);
                cells[i][j] = cell;
            }
        }
        cellsTexture = new Texture[4];
        cellsTexture[1] = new Texture("liveCell.png");
        cellsTexture[0] = new Texture("deadCell.png");
        cellsTexture[2] = new Texture("liveCell2.png");
        cellsTexture[3] = new Texture("liveCell3.png");
        minX = width;
        maxX = 0;
        minY = height;
        maxY = 0;
    }

    public void updatePress(float tempPosX, float tempPosY,float dx, float dy,boolean isPainting, boolean isErasure){
        int i = (int)(tempPosX/size), j = (int)(tempPosY/size);
        if (i >= 0 && j >= 0 && i < width && j < height) {
            updatePressMini(i, j, isPainting, isErasure);
            if (isPainting || isErasure) {
                while (dx >= 1 || dy >= 1 || dx <= -1 || dy <= -1) {
                    i = (int) ((tempPosX + dx) / size);
                    j = (int) ((tempPosY - dy) / size);
                    updatePressMini(i, j, isPainting, isErasure);
                    if (dx < 0) dx = dx + 1;
                    else if (dx > 0) dx = dx - 1;
                    if (dy < 0) dy = dy + 1;
                    else if (dy > 0) dy = dy - 1;
                }
            }
        }
    }

    private void updatePressMini(int i,int j, boolean isPainting, boolean isErasure){
        try {
            if (cells[i][j].color == 0 && !isErasure) {
                cells[i][j].color = 1;
                changingNeighbors(i, j, 1);
                if (minX > i) minX = i;
                if (maxX < i) maxX = i;
                if (minY > j) minY = j;
                if (maxY < j) maxY = j;
            } else if (cells[i][j].color >= 1 && !isPainting) {
                cells[i][j].color = 0;
                changingNeighbors(i, j, -1);
            }
        } catch (Exception e) {}
    }

    public void updateProgress(){
        lifeAndDeath();
        countingNeighbors();
        changingUpdateBoundaries();
    }

    private void lifeAndDeath(){
        for (int i = minX-1; i < maxX+2; i++) {
            for (int j = minY - 1; j < maxY + 2; j++) {
                try {
                    if ((cells[i][j].color >= 1 && cells[i][j].color <= 3) && (cells[i][j].neighbors != 3 && cells[i][j].neighbors != 2)) {
                        cells[i][j].color = 0;
                        temp[indexTemp][0] = i;
                        temp[indexTemp][1] = j;
                        temp[indexTemp][2] = -1;
                        indexTemp++;
                    } else if (cells[i][j].color >= 1 && cells[i][j].color <= 2) cells[i][j].color++;
                    if (cells[i][j].color == 0 && cells[i][j].neighbors == 3) {
                        cells[i][j].color = 1;
                        temp[indexTemp][0] = i;
                        temp[indexTemp][1] = j;
                        temp[indexTemp][2] = 1;
                        indexTemp++;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void countingNeighbors(){
        for (int i = 0; i < indexTemp; i++){
            changingNeighbors(temp[i][0],temp[i][1],temp[i][2]);
        }
        indexTemp = 0;
    }

    private void changingNeighbors(int x, int y, int index){
        try{ cells[x-1][y-1].neighbors += index; } catch (Exception e){}
        try{ cells[x-1][y].neighbors += index; }catch (Exception e){}
        try{ cells[x-1][y+1].neighbors += index; }catch (Exception e){}
        try{ cells[x][y-1].neighbors += index; }catch (Exception e){}
        try{ cells[x][y+1].neighbors += index; }catch (Exception e){}
        try{ cells[x+1][y-1].neighbors += index; }catch (Exception e){}
        try{ cells[x+1][y].neighbors += index; }catch (Exception e){}
        try{ cells[x+1][y+1].neighbors += index; }catch (Exception e){}
    }

    private void changingUpdateBoundaries(){
        if ((maxX-minX+1)*(maxY-minY+1) <= width*height/2) {
            tminX = width;
            tmaxX = 0;
            tminY = height;
            tmaxY = 0;
            for (int i = minX - 1; i < maxX + 2; i++) {
                for (int j = minY - 1; j < maxY + 2; j++) {
                    try {
                        if (cells[i][j].color >= 1) {
                            if (tminX > i) tminX = i;
                            if (tmaxX < i) tmaxX = i;
                            if (tminY > j) tminY = j;
                            if (tmaxY < j) tmaxY = j;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            minX = tminX;
            maxX = tmaxX;
            minY = tminY;
            maxY = tmaxY;
        } else{
            minX = 0;
            maxX = width-1;
            minY = 0;
            maxY = height-1;
        }
    }

    private void definingDrawingBoundaries(float camPosX, float camPosY, float camZoom, float dx, float dy){
        if  ((int) (camPosX - 384*dx * camZoom) / size < 0){
            bi = 0;
        } else {
            bi = (int) (camPosX - 384*dx * camZoom) / size;
        }
        if  ((int) (camPosX + 384*dx * camZoom) / size + 1 > width){
            ei = width;
        } else {
            ei = (int) (camPosX + 384*dx * camZoom) / size + 1;
        }
        if  ((int) (camPosY - 216*dy * camZoom) / size < 0){
            bj = 0;
        } else {
            bj = (int) (camPosY - 216*dy * camZoom) / size;
        }
        if  ((int) (camPosY + 216*dy * camZoom) / size + 1 > height){
            ej = height;
        } else {
            ej = (int) (camPosY + 216*dy * camZoom) / size + 1;
        }
    }

    public void draw(SpriteBatch sb, float camPosX, float camPosY, float camZoom, float dx, float dy){
        if (camZoom <= 1.5) {
            definingDrawingBoundaries(camPosX,camPosY,camZoom,dx,dy);
            sb.begin();
            sb.disableBlending();
            for (int i = bi; i < ei; i++) {
                for (int j = bj; j < ej; j++) {
                    sb.draw(cellsTexture[cells[i][j].color], cells[i][j].x, cells[i][j].y, cells[i][j].width, cells[i][j].height);
                }
            }
            sb.enableBlending();
            sb.end();
        }else {
            if (Math.sqrt(Math.pow(maxX-minX,2)+Math.pow(maxY-minY,2)) < Math.sqrt(Math.pow(((camPosX + 384*dx * camZoom) / size + 1)-((camPosX - 384*dx * camZoom) / size - 1),2)+Math.pow(((camPosY + 216*dy * camZoom) / size + 1)-((camPosY - 216*dy * camZoom) / size - 1),2))) {
                sb.begin();
                sb.disableBlending();
                for (int i = minX; i < maxX+1; i++) {
                    for (int j = minY; j < maxY+1; j++) {
                        if (cells[i][j].color != 0) {
                            sb.draw(cellsTexture[cells[i][j].color], cells[i][j].x, cells[i][j].y, cells[i][j].width, cells[i][j].height);
                        }
                    }
                }
                sb.enableBlending();
                sb.end();
            } else {
                definingDrawingBoundaries(camPosX,camPosY,camZoom,dx,dy);
                sb.begin();
                sb.disableBlending();
                for (int i = bi; i < ei; i++) {
                    for (int j = bj; j < ej; j++) {
                        if (cells[i][j].color != 0) {
                            sb.draw(cellsTexture[cells[i][j].color], cells[i][j].x, cells[i][j].y, cells[i][j].width, cells[i][j].height);
                        }
                    }
                }
                sb.enableBlending();
                sb.end();
            }
        }
    }

    public void dispose(){
        for (int i = 0; i < 4; i++) {
            cellsTexture[i].dispose();
        }
    }
}
