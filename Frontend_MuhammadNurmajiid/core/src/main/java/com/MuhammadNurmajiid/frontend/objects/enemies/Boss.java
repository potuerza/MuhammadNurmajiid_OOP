package com.MuhammadNurmajiid.frontend.objects.enemies;

import com.MuhammadNurmajiid.frontend.objects.Collidable;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.patterns.ShootingPattern;
import com.MuhammadNurmajiid.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy {
    private float collisionCooldown;
    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 30f);
    // TODO: Declare a private field named `shootingPattern` of type ShootingPattern
    //       and immediately initialize it with the `SpreadShot` we just created.
    //       Make sure speed is 200f, spread angle is 30f, and 3 bullets (num) are fired.


    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        this.collisionCooldown = 0;
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        this.collisionCooldown = 0;

    }

    // TODO: Create the shootBullet(BulletManager bulletManager) method:
    //       If shootingPattern is not null and the boss hasn't been destroyed,
    //       execute shootingPattern at the boss's center cone coordinates (x + width / 2 - 8, y).
    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(x + width / 2f - 8f, y, bulletManager, false);
        }
    }
    // TODO: Provide a getter and setter for shootingPattern
    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (collisionCooldown > 0) {
            collisionCooldown -= delta;
        }
    }

    @Override
    public void onCollision(Collidable other) {
            if(other instanceof Player){
                if(collisionCooldown <= 0) {
                    System.out.println("Player touches Boss");
                    collisionCooldown = 1;
                }

            }
    }


}
