package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {
    public static final int SIZE_X = 64;
    public static final int SIZE_Y = 36;
    public static final int CELL_SIZE = 20;
private enum WallType{
    HARD(0,5,true),SOFT(1,3,true),INDESTRUCTABLE(2,1,false),NONE(0,0,false);
    int index;
    int maxHP;
    boolean destructuble;

    WallType(int index, int maxHP, boolean destructuble) {
        this.index = index;
        this.maxHP = maxHP;
        this.destructuble = destructuble;
    }
}
private class Cell{
    WallType type;
    int hp;

    public Cell(WallType type) {
        this.type = type;
        this.hp = type.maxHP;
    }
    public void damage(){
        hp--;
        if(hp<=0){
            type=WallType.NONE;
        }
    }
    public void changeType(WallType type){
        this.type=type;
        this.hp=type.maxHP;
    }
}
private TextureRegion grassTexture;
   // private TextureRegion wallTexture;
    private Cell cells[][];
    private TextureRegion[][] wallsTexture;

    public Map(TextureAtlas atlas) {
        this.wallsTexture = new TextureRegion(atlas.findRegion("walls")).split(CELL_SIZE, CELL_SIZE);
        this.grassTexture = atlas.findRegion("grass40");
        //this.wallTexture = atlas.findRegion("block");
        this.cells = new Cell[SIZE_X][SIZE_Y];
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                this.cells[i][j]=new Cell(WallType.NONE);
                int cx=(int)(i/4);
                int cy=(int)(j/4);
                if(cx%2==0 &&cy%2==0){
                    cells[i][j].changeType(WallType.HARD);
                }
            }
        }
        for (int i = 0; i <SIZE_X ; i++) {
            cells[i][0].changeType(WallType.INDESTRUCTABLE);
            cells[i][SIZE_X-1].changeType(WallType.INDESTRUCTABLE);
        }
    }

    public void checkWallAndBulletsCollision(Bullet bullet) {
        int cx = (int) (bullet.getPosition().x / CELL_SIZE);
        int cy = (int) (bullet.getPosition().y / CELL_SIZE);

        if (cx >= 0 && cy >= 0 && cx < SIZE_X && cy <= SIZE_Y) {
            if (cells[cx][cy].type != WallType.NONE) {
                cells[cx][cy].damage();
                bullet.deactivate();
            }
        }
    }

    public boolean isAreaClear(float x,float y,float halfsize){
        int leftx= (int) ((x-halfsize)/CELL_SIZE);
        int righttx= (int) ((x+halfsize)/CELL_SIZE);
        int bottomy= (int) ((y-halfsize)/CELL_SIZE);
        int topy= (int) ((y+halfsize)/CELL_SIZE);

        if(leftx<0){
            leftx=0;
        }
        if(righttx>=SIZE_X){
            righttx=SIZE_X-1;
        }
        if(bottomy<0){
            bottomy=0;
        }
        if(topy>=SIZE_Y){
            topy=SIZE_Y-1;
        }

        for (int i =leftx;i<=righttx;i++){
            for (int j =bottomy;j<=topy;j++){
                if (cells[i][j].type!=WallType.NONE){//verificam daca este vreun obiect in acel loc
                    return false;
                }
            }
        }
        return true;
    }
    public void render(SpriteBatch batch) {
        for (int i = 0; i < 1280 / 40; i++) {
            for (int j = 0; j < 720 / 40; j++) {
                batch.draw(grassTexture, i * 40, j * 40);
            }
        }

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (cells[i][j].type!=WallType.NONE ) {
                    batch.draw(wallsTexture[cells[i][j].type.index][cells[i][j].hp-1], i * CELL_SIZE, j * CELL_SIZE);
                }
            }
        }
    }
}
