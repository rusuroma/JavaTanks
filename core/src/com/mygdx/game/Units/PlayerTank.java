package com.mygdx.game.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TanksGdxGame;
import com.mygdx.game.Utilits.Direction;
import com.mygdx.game.Utilits.TankOwner;
import com.mygdx.game.Weapon;



public class PlayerTank extends Tank {
    int lifes;

    public PlayerTank(TanksGdxGame game, TextureAtlas atlas) {
        super(game);
        this.ownerType = TankOwner.PLAYER;
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("playerTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(100.0f, 100.0f);
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 10;
        this.hp = this.hpMax ;
        this.circle = new Circle(position.x, position.y, (width + height) / 2);
        this.lifes = 5;
    }

    @Override
    public void destroy() {
        lifes--;
        hp = hpMax;
    }

    public void update(float dt) {
        checkMovement(dt);
        float mx = Gdx.input.getX();
        float my = Gdx.graphics.getHeight() - Gdx.input.getY();

        rotateTurretToPoint(mx, my, dt);

        if (Gdx.input.justTouched()) {
            fire();
        }

        super.update(dt);
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
         move(Direction.LEFT,dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move(Direction.RIGHT,dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
          move(Direction.UP,dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(Direction.DOWN,dt);
        }
    }
}
