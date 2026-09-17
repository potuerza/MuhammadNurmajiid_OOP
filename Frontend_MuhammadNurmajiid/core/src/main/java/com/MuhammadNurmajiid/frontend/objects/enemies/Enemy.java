package com.MuhammadNurmajiid.frontend.objects.enemies;

import com.MuhammadNurmajiid.frontend.objects.Collidable;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends GameObject {
    private String name;
    private int hp;
    private int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200, 380, 24, 24, 0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 100L;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue) {
        super(x, y, width, height, 0, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getHp() {
        return this.hp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public void setScoreValue(long scoreValue) {
        this.scoreValue = scoreValue;
    }

    public long getScoreValue() {
        return this.scoreValue;
    }

    public boolean takeDamage(int damage) {
        if (getHp() <= 0) {
            return false;
        }

        int oldHp = getHp();
        setHp(getHp() - damage);

        if (oldHp > 0 && getHp() == 0) {
            System.out.print(getName() + " was defeated!");
            return true;
        }
        else {
            System.out.print(getName() + " took " + damage + " damage! HP: " + getHp() + "/" + getMaxHp());
            return false;
        }
    }

    public void attack(Player player, int damage) {
        System.out.print(getName() + " unleashes bullet barrage on " + player.getName());
        player.takeDamage(damage);
    }

    public boolean isAlive() {
        if (getHp() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return null;
    }

    @Override
    public void onCollision(Collidable other) {

    }
}

