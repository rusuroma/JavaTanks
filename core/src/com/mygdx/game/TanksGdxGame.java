package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Units.BotTank;
import com.mygdx.game.Units.PlayerTank;
import com.mygdx.game.Units.Tank;



public class TanksGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private PlayerTank player;
    private BulletEmitor bulletEmitter;
    private BotEmitor botEmitter;
    private float gameTimer;

    private static final boolean FIRENDLY_FIRE = false;

    public Map getMap() {
        return map;
    }

    public PlayerTank getPlayer() {
        return player;
    }

    public BulletEmitor getBulletEmitter() {
        return bulletEmitter;
    }

    @Override
    public void create() {
        TextureAtlas atlas = new TextureAtlas("game.pack.pack");
        batch = new SpriteBatch();
        map = new Map(atlas);
        player = new PlayerTank(this, atlas);
        bulletEmitter = new BulletEmitor(atlas);
        botEmitter = new BotEmitor(this, atlas);
        gameTimer=6.0f;
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0.6f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);
        player.render(batch);
        botEmitter.render(batch);
        bulletEmitter.render(batch);
        batch.end();
    }

    public void update(float dt) {
        gameTimer += dt;
        if (gameTimer > 5.0f) {
            gameTimer = 0.0f;

            float coordX,coordY;
            do {
        coordX= MathUtils.random(0, Gdx.graphics.getWidth());
        coordY= MathUtils.random(0, Gdx.graphics.getHeight());
            }while (!map.isAreaClear(coordX,coordY,20));
            botEmitter.activate(coordX, coordY);
        }
        player.update(dt);
        botEmitter.update(dt);
        bulletEmitter.update(dt);
        checkCollisions();
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletEmitter.getBullets().length; i++) {
            Bullet bullet = bulletEmitter.getBullets()[i];
            if (bullet.isActive()) {
                for (int j = 0; j < botEmitter.getBots().length; j++) {
                    BotTank bot = botEmitter.getBots()[j];
                    if (bot.isActive()) {
                        if (checkBulletAndTank(bot, bullet) && bot.getCircle().contains(bullet.getPosition())) {
                            bullet.deactivate();
                            bot.takeDamage(bullet.getDamage());
                            break;
                        }
                    }
                }

                if (checkBulletAndTank(player, bullet) && player.getCircle().contains(bullet.getPosition())) {
                    bullet.deactivate();
                    player.takeDamage(bullet.getDamage());
                }

                map.checkWallAndBulletsCollision(bullet);
            }
        }
    }

    public boolean checkBulletAndTank(Tank tank, Bullet bullet) {
        if (!FIRENDLY_FIRE) {
            return tank.getOwnerType() != bullet.getOwner().getOwnerType();
        } else {
            return tank != bullet.getOwner();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
