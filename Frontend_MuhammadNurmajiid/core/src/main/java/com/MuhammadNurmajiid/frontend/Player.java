package com.MuhammadNurmajiid.frontend;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;

import java.lang.annotation.Target;

public class Player extends GameObject {
    String name;
    int hp;
    int power;
    int spellCards;
    long score;

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }

    public int getHp(){
        return this.hp = hp;
    }
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public float getPower(){
        return this.power = power;
    }

    public void setPower(){
        this.power = Math.max(0, power);
    }

    public String getName(){
        return this.name = name;
    }

    public void setName(String name){
        this.name = Math.max(0, name);
    }


    public void TakeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        this.hp -= damage;

        if (this.hp < 0) {
            this.hp = 0;
        }

        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        if (this.hp > 0) {
            System.out.println(this.name + "Took" + damage + "Damage!" + "Remaining HP = " + this.hp);

            // 2. HP must not become negative.
        }

        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if (this.hp == 0) {
            System.out.println(name + "Has been Defeated!");
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + getPower(this.power);
        System.out.println(getName(this.name) + " shoots " + target.getName(this.hp) + " dealing " + damage + " DMG!");

    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if (this.hp > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void addScore(long points) {
        if (points > 0) {
            score += points;
        }

    }


}


