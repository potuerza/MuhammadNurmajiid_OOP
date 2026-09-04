package com.MuhammadNurmajiid.frontend;

public class Enemy {
    String name;
    int hp;
    int maxHp;


    public Enemy(String EN, int EHP) {
        name = EN;
        hp = EHP;
        maxHp = EHP;
    }

    public void TakeDamage(int damage) {

        // 1. Reduce hp by the damage value.
        hp = hp - damage;

        // 2. HP must not become negative.
        if (hp < 0) {
            hp = 0;
        }

        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        if (hp > 0) {
            System.out.println(name + "Took" + Enemy.damage "Damage!" + "Remaining HP = " + hp);

        }

        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if (hp == 0) {
            System.out.println(name + "Has been Defeated!");
        }
    }

    public void attack(Player player, int damage) {
            damage = damage + 10;
            // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
            System.out.println(name + "Shoots" + Player.takeDamage + "Dealing" + damage "DMG!");

            // 2. Call the Player's takeDamage() method using the given damage.

    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if (hp > 0) {
            return true;
        } else {
            return false;
        }

    }
}
