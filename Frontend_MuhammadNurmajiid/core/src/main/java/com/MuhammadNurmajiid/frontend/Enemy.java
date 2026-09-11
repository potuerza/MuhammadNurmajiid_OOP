package com.MuhammadNurmajiid.frontend;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {
    String name;
    int hp;
    int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200, 380, 24, 24, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue){
        super(x, y, width, height, 0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    public float getHp(){
        this.hp = hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }


    public boolean TakeDamage(int damage) {
        setHp(getHp(this.hp) - damage);

        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        System.out.println(name + "Took" + damage + "Damage!" + "Remaining HP = " + hp);

        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if (this.hp == 0) {
            System.out.println(name + "Has been Defeated!");
            return true;
        }
        return false;

        }


    public void attack(Player player, int damage) {
            damage = damage + 10;
            // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
            System.out.println(this.name + "Unleash Damage" + player.name + "Dealing" + damage + "DMG!");

            // 2. Call the Player's takeDamage() method using the given damage.
            player.TakeDamage(damage);
    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        return this.hp > 0;

        }
}
