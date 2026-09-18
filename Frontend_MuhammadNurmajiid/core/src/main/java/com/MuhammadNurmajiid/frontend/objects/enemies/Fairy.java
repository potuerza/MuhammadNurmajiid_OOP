package com.MuhammadNurmajiid.frontend.objects.enemies;
import com.MuhammadNurmajiid.frontend.objects.Collidable;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.badlogic.gdx.graphics.Color;

public class Fairy extends Enemy {
    public Fairy(String name, int hp) {
        super(150, 380, 24, 24, Color.PINK, name, hp, 500L);
    }

    public Fairy(float x, float y, String name, int hp) {
        super(x, y, 24, 24, Color.PINK, name, hp, 500L);
    }

    @Override
    public void onCollision(Collidable other) {
        if(other instanceof Player){
            System.out.println("Player touches fairy");
        }
        // TODO: Check whether the other received by this method is a Player
        // TODO: Print "Player touches fairy"
    }

}
