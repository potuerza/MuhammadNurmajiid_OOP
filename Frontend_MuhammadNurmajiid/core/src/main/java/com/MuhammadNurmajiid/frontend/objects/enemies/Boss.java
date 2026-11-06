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


    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        this.collisionCooldown = 0;
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        this.collisionCooldown = 0;

    }


    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(x + width / 2f - 8f, y, bulletManager, false);
        }
    }
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
