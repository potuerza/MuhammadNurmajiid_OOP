package com.MuhammadNurmajiid.frontend;

import java.lang.annotation.Target;

public class Player {
    String name;
    int hp;
    int power;
    int spellCards;
    int maxHp = hp;

    public Player(String PN, int PHP, int PPP, int PSC) {
        name = PN;
        hp = PHP;
        power = PPP;
        spellCards = PSC;

    }

    public void TakeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        damage = hp - damage;

        // 2. HP must not become negative.
        if (hp < 0){
            hp = 0;
        }

        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        if (hp > 0 ){
            System.out.println(name + "Took" + damage "Damage!" + "Remaining HP = " + hp/maxHp);

        }

        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if (hp == 0){
            System.out.println (name + "Has been Defeated!");
        }
    }

    public void shoot(Enemy target) {
        int damage = power + 10;
        // 1. Create an int named damage, calculated by adding 10 to power.

        // 2. Display information that the Player is shooting the Enemy, in the format: [name] shoots [TargetName] dealing [damage] DMG!
        System.out.println(name + "Shoots" + hp + "Dealing" + damage "DMG!");

        // 3. Call the Enemy object's takeDamage() method.
        Enemy.takeDamage();
    }
    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if (hp > 0) {
            return true;
        }
        else {
            return false;
        }

    }

}

