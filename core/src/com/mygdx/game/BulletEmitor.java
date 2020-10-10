package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Units.Tank;

public class BulletEmitor {
    private TextureRegion bulletTexture;
    private  Bullet[] bullets;
    public static final int MAX_BULLETS_COUNTS = 500;

    public Bullet[] getBullets() {
        return bullets;
    }

    public BulletEmitor(TextureAtlas atlas){
        this.bulletTexture=atlas.findRegion("projectile");
        this.bullets=new Bullet[MAX_BULLETS_COUNTS];
        for(int i=0;i<MAX_BULLETS_COUNTS;i++){
            this.bullets[i]=new Bullet();
        }//end for
    }
    public void activate(Tank owner,float x, float y, float vx, float vy, int damage,float maxTime){
        for(int i=0;i<bullets.length;i++){
            if(!bullets[i].isActive()){
                bullets[i].activate(owner,x,y,vx,vy,damage,maxTime);
                break;
            }
        }
    }//end activate

    public  void render(SpriteBatch batch) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                batch.draw(bulletTexture, bullets[i].getPosition().x - 8, bullets[i].getPosition().y - 8);
            }
        }
    }//end render
    public  void update(float dt){
        for(int i=0;i<bullets.length;i++){
            if(bullets[i].isActive()){
                bullets[i].update(dt);
            }
        }
    }//end update
}
