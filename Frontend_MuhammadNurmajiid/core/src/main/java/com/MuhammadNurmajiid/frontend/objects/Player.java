package com.MuhammadNurmajiid.frontend.objects;

import com.MuhammadNurmajiid.frontend.objects.enemies.Enemy;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;


public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
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

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return this.power;
    }

    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
    }

    public int getSpellCards() {
        return this.spellCards;
    }

    public long getScore() {
        return this.score;
    }

    public void takeDamage(int damage) {
        setHp(getHp() - damage);

        if (getHp() > 0) {
            System.out.print(getName() + " took " + damage + " damage! Remaining HP: " + getHp());
        }
        else {
            System.out.print(getName() + " has been defeated.");
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }

    public boolean isAlive() {
        if (getHp() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public void collectItem(Item item) {
        System.out.println(getName() + " collected " + item.getItemType() + "!");
        if (item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
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

