package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Units.BotTank;

public class BotEmitor {
    private BotTank[] bots;

    public BotTank[] getBots() {
        return bots;
    }

    public static final int MAX_COUNTS = 200;


    public BotEmitor(TanksGdxGame game, TextureAtlas atlas){

        this.bots=new BotTank[MAX_COUNTS];
        for(int i=0;i<bots.length;i++){
            this.bots[i]=new BotTank(game,atlas);
        }//end for
    }
    public void activate(float x,float y){
        for(int i=0;i<bots.length;i++){
            if(!bots[i].isActive()){
                bots[i].activate(x,y);
                break;
            }
        }
    }//end activate

    public  void render(SpriteBatch batch) {
        for (int i = 0; i < bots.length; i++) {
            if (bots[i].isActive()) {
              bots[i].render(batch);
            }
        }
    }//end render
    public  void update(float dt){
        for(int i=0;i<bots.length;i++){
            if(bots[i].isActive()){
                bots[i].update(dt);
            }
        }
    }//end update

}
