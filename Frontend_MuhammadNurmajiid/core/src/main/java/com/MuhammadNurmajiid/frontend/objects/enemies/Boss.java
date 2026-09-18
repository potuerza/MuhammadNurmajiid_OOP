package com.MuhammadNurmajiid.frontend.objects.enemies;

import com.MuhammadNurmajiid.frontend.objects.Collidable;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy {
    public Boss(String name, int hp) {
        super(380, 400, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    @Override
    public void onCollision(Collidable other) {
            if(other instanceof Player){
                System.out.println("Player touches Boss");
            }
        // TODO: Check whether the other received by this method is a Player
        // TODO: Print "Player touches boss"
    }


}
