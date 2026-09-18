package com.MuhammadNurmajiid.frontend.objects;

import com.MuhammadNurmajiid.frontend.objects.enemies.Enemy;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.util.zip.CheckedInputStream;


public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 200, Color.RED);
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
        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    this.power += type.getPowerBonus();
                    addScore(item.getScoreValue());
                    System.out.println(name + "Collected POINT Item!");
                    System.out.println(name + "Collected POWER item! power increased to" + power);
                    // 1. Increase power by type.getPowerBonus() via this.power
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                }
                case POINT -> {
                    addScore(item.getScoreValue());
                    System.out.println(name + "Collected POINT Item!");
                    // 1. Add score by item.getScoreValue() via addScore()
                    // 2. Print: [name] collected POINT item!
                }
                case BOMB -> {
                    spellCards += 1;
                    addScore(item.getScoreValue());
                    System.out.println(name + "Collected BOMB Item!" + "SpellCards: " + spellCards);
                    // 1. Increase spellCards by 1
                    // 2. Add score by item.getScoreValue() via addScore()
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    hp += 20;
                    addScore(item.getScoreValue());
                    System.out.println(name + "Collected LIFE Item!HP: " + hp);
                    // 2. Add score by item.getScoreValue() via addScore()
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
    }


    @Override
    public Rectangle getGrazeHitbox() {
        return null;
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        // TODO: Print "Player touches items" then call collectItem((Item) other)
        if(other instanceof Item){
            System.out.println("Player touches items");
            collectItem((Item) other);
        }


    }

    @Override
    public void update(float delta) {
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
                y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                x -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                x += speed * delta;
            }

            // TODO: Check W / UP input   → y += speed * delta
            // TODO: Check S / DOWN input → y -= speed * delta
            // TODO: Check A / LEFT input → x -= speed * delta
            // TODO: Check D / RIGHT input → x += speed * delta
        }
    }




}

