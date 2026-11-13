package com.MuhammadNurmajiid.frontend.objects.enemies;

import com.MuhammadNurmajiid.frontend.objects.Collidable;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.patterns.ShootingPattern;
import com.MuhammadNurmajiid.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;

import com.MuhammadNurmajiid.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.MuhammadNurmajiid.frontend.objects.patterns.entityStrategy.FixedMovement;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy {
    private float collisionCooldown;
    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 30f);


    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        // TODO (2 points): Inject default Boss strategies via setters
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
        // 1. Set Shooting Pattern as SpreadShot with speed 200f, 3 bullets, and 30f spreadAngleDegrees.
        // 2. Set Movement Pattern as FixedMovement.
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
        // TODO (2 points): Perform the same strategy injection in the custom coordinate constructor
    }
}
