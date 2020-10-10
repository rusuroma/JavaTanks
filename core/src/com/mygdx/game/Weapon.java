package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weapon {
  //  private float fireTimer;
    private float firePeriod;
    private int damage;
    private  float projectileSpeed;
    private float projectileLifeTime;
    private float radius;
    private TextureRegion texture;

    public float getFirePeriod() {
        return firePeriod;
    }

    public float getRadius() {
        return radius;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public float getProjectileLifeTime() {
        return projectileLifeTime;
    }

    public int getDamage() {
        return damage;
    }

    public TextureRegion getTextureRegion() {
        return texture;
    }

    public Weapon(TextureAtlas atlas){
        this.texture=atlas.findRegion("simpleWeapon");
        this.firePeriod=0.1f;
        this.damage=1;
        this.radius=300.0f;
        this.projectileSpeed=320.0f;
        this.projectileLifeTime=this.radius/this.projectileSpeed;
            }

}
