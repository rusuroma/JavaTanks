package com.mygdx.game.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.TanksGdxGame;
import com.mygdx.game.Utilits.Direction;
import com.mygdx.game.Utilits.TankOwner;
import com.mygdx.game.Weapon;

public class BotTank extends Tank {
    Direction prefferedDirection;
    float aiTimer;
    float aiTimerTo;
    float pursuitRadius;
    boolean active;
    Vector3 lastposition;

    public boolean isActive() {
        return active;
    }

    public BotTank(TanksGdxGame game, TextureAtlas atlas) {
        super(game);
        this.ownerType = TankOwner.AIM;
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("botTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(500.0f, 500.0f);
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 3;
        this.hp = this.hpMax;
        this.aiTimerTo = 3.0f;
        this.pursuitRadius = 300.0f;
        this.prefferedDirection = Direction.UP;
        this.circle = new Circle(position.x, position.y, (width + height) / 2);
        this.lastposition=new Vector3(0.0f,0.0f,0.0f);
    }

    public void activate(float x, float y) {
        hpMax = 3;
        hp = hpMax;
        prefferedDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
        angle = prefferedDirection.getAngle();
        position.set(x, y);
        aiTimer = 0.0f;
        active = true;
    }

    @Override
    public void destroy() {
        active = false;
    }

    public void update(float dt) {
        aiTimer += dt;
        if (aiTimer >= aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(3.5f, 6.0f);
            prefferedDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
            angle = prefferedDirection.getAngle();
        }
        move(prefferedDirection,dt);
        //position.add(speed * prefferedDirection.getVx() * dt, speed * prefferedDirection.getVy() * dt);

        float dst = this.position.dst(game.getPlayer().getPosition());
        if (dst < pursuitRadius) {
            rotateTurretToPoint(game.getPlayer().getPosition().x, game.getPlayer().getPosition().y, dt);
            fire();
        }
        if(Math.abs(position.x-lastposition.x)<0.5f &&Math.abs(position.y-lastposition.y)<0.5f)//daca pozitia nu s-a miscat cu 0.5 pixeli
            lastposition.z+=dt;//adugam timp
        if(lastposition.z>0.3f){//daca sta mai mult de un timp oarcare
            aiTimer+=10.0f;//il impunem sa isi schimbe directia
        }else{
            lastposition.x=position.x;
            lastposition.y=position.y;
            lastposition.z=0.0f;
        }
        super.update(dt);
    }
}
