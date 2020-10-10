package com.mygdx.game.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TanksGdxGame;
import com.mygdx.game.Utilits.Direction;
import com.mygdx.game.Utilits.TankOwner;
import com.mygdx.game.Utilits.Utils;
import com.mygdx.game.Weapon;

public abstract class Tank {
     TanksGdxGame game;
     TankOwner ownerType;
     Weapon weapon;
     float hp;
     float hpMax;
     Vector2 position;
     Vector2 tmp;
     TextureRegion texture;
    TextureRegion textureHp;
    Circle circle;

     float speed;
     float angle;

     float turretAngle;
     float fireTimer;

 int width;
 int height;
   public  Tank(TanksGdxGame game){
       this.game=game;
       this.tmp=new Vector2(0.0f,0.0f);

   }

    public Circle getCircle() {
        return circle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public  void render(SpriteBatch batch){

       batch.draw(texture,position.x-width/2,position.y-height/2,width/2,height/2,width,height,1,1,angle);
       batch.draw(weapon.getTextureRegion(),position.x-width/2,position.y-height/2,width/2,height/2,width,height,1,1,turretAngle);
       if(hp<hpMax){
       batch.setColor(0,0,0,1);
       batch.draw(textureHp,position.x-width/2,position.y+height/2-3);
       batch.setColor(1,0,0,1);
       batch.draw(textureHp,position.x-width/2,position.y+height/2-3,((float)hp/hpMax)*40,8);
       batch.setColor(1,1,1,1);}
   }//render

    public TankOwner getOwnerType() {
        return ownerType;
    }

    public void rotateTurretToPoint(float pointx, float pointy, float dt){
        float angleTo= Utils.getAngle(position.x,position.y,pointx,pointy);
        turretAngle=Utils.makeRotation(turretAngle,angleTo,180.0f,dt);
        turretAngle=Utils.angleToFromNegPiToPosPi(turretAngle);
    }

    public void takeDamage(int damage){
       hp-=damage;
       if(hp<=0){
           destroy();
       }
    }

    public abstract void destroy();
public void move(Direction direction,float dt){
tmp.set(position);
    tmp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
if(game.getMap().isAreaClear(tmp.x,tmp.y,width/2)){
    angle=direction.getAngle();
    position.set(tmp);

}
}
    public void update(float dt){
        fireTimer+=dt;
        if(position.x<0.0f){
            position.x=0.0f;
        }
        if(position.x> Gdx.graphics.getWidth()){
            position.x=Gdx.graphics.getWidth();
        }
        if(position.y<0.0f){
            position.y=0.0f;
        }
        if(position.y> Gdx.graphics.getHeight()){
            position.y=Gdx.graphics.getHeight();
        }

      circle.setPosition(position);
    }//end update

public  void fire(){
    if(fireTimer>=weapon.getFirePeriod()){
        fireTimer=0.0f;
        float angRadian=(float)Math.toRadians(turretAngle);
        game.getBulletEmitter().activate(this,position.x,position.y,weapon.getProjectileSpeed()*(float)Math.cos(angRadian),weapon.getProjectileSpeed()*(float)Math.sin(angRadian),weapon.getDamage() ,weapon.getProjectileLifeTime());
    }

  }//end fire

   }//end class
