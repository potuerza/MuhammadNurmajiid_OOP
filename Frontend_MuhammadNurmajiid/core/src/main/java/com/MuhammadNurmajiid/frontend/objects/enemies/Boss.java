package com.MuhammadNurmajiid.frontend.objects.enemies;

import com.MuhammadNurmajiid.frontend.objects.Collidable;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy {
    private float collisionCooldown;
    public Boss(String name, int hp) {
        super(380, 400, 48, 48, Color.BLUE, name, hp, 5000L);
        this.collisionCooldown = 0;
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48, Color.BLUE, name, hp, 5000L);
        this.collisionCooldown = 0;

    }

    @Override
    public void update(float delta) {
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
        // TODO: Check whether the other received by this method is a Player
        // TODO: Print "Player touches boss"
    }


}
