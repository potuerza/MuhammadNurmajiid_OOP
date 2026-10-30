package com.MuhammadNurmajiid.frontend.systems;

import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.bullets.Bullet;
import com.MuhammadNurmajiid.frontend.objects.enemies.Enemy;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {
    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;
        List<GameObject> newDrops = new ArrayList<>();
        // Collision logic is divided into 3 stages
        // 1. Evaluate Player Bullets vs Enemies

        // TODO Code for Question 3:
        // - Loop through each bullet in the ActivePlayerBullets list.
        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;
            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        boolean defeated = enemy.takeDamage(bullet.getDamage());
                        if (defeated) {
                            newDrops.add(EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER));
                        }
                        // Core Business Logic:
                        // Enemy takes damage, player gets score, etc.
                        // Bullet gets marked to be destroyed
                        bullet.destroy();
                    }
                }
            }
        }
        //   - If the bullet is already destroyed, skip it (continue)

        //   - Loop through each entity in entities: (HINT: the `entities` variable is in the parameters above)
        //     - Check whether entity is an instance of Enemy enemy and is not destroyed yet.
        //     - If so, check whether the bullet's Core Hitbox collides with the enemy's Core Hitbox.
        //     - If they collide:
        //       1. Deal damage to the enemy: boolean defeated = enemy.takeDamage(bullet.getDamage());
        //       2. If the enemy is killed (defeated):
        //          - Add the enemy's score (scoreValue) to the player.
        //          - Create a POWER item drop via the factory at the x, y coordinates where the enemy died,
        //            then add that item to the newDrops list
        //       3. Mark the bullet as destroyed so it will be recycled later by BulletManager


        // 2. Evaluate Enemy Bullets vs Player (Core Hit vs Graze Hit)


        // TODO Code for Question 4:
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;

            for (GameObject entity : entities) {
                if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                    player.takeDamage(bullet.getDamage());
                    bullet.destroy();
                }

                else if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getCoreHitbox()) ) {
                    bullet.setGrazed(true);
                    player.addScore(50);
                }

                    // Core Business Logic:
                    // Enemy takes damage, player gets score, etc.
                    // Bullet gets marked to be destroyed
                    newDrops.add(EntityFactory.createItem(player.getX(), player.getY(), ItemType.POWER));
                    bullet.destroy();
                }
            }




            // - Loop through each bullet in ActiveEnemyBullets.
            //   - If the bullet is already destroyed, skip it (continue)
            //
            //   - Check if player is not destroyed (destroyed), then:
            //
            //     - PRIORITY 1: Core Hit (Player was Shot)
            //       - Check whether the bullet's Core Hitbox collides with the player's Core Hitbox.
            //       - If YES:
            //         * Deal damage to the player: player.takeDamage(bullet.getDamage())
            //         * Mark the bullet as destroyed / destroy the bullet.
            //
            //     - PRIORITY 2: Graze Hit (Bullet Grazes Player)
            //       - Check if the bullet has NOT been grazed yet
            //         AND the bullet's Core Hitbox touches the player's Graze Hitbox.
            //       - If YES:
            //         * Mark the bullet as grazed.
            //         * Give the graze score bonus to the player: player.addScore(50)


            // 3. Evaluate Player vs Items and Physical Enemy Contact
            // TODO Code for Question 5:

            for (GameObject entity : entities) {
                if (entity.isDestroyed()){
                    continue;
                }

                if (entity instanceof Item item){
                    if (item.getCoreHitbox().overlaps(player.getCoreHitbox()) || item.getCoreHitbox().overlaps(player.getGrazeHitbox())){
                        player.collectItem(item);
                    }
                }

                if (entity instanceof Enemy enemy){
                    if(player.getCoreHitbox().overlaps(enemy.getCoreHitbox())){
                    player.takeDamage(10);
                    }
                }
            }
            // - Loop through each entity in entities:
            //   - If the entity is already destroyed, skip it (continue)
            //
            //   - CASE A: Item Collection
            //     - Check whether entity is an instance of Item item
            //     - Check whether the item's Core Hitbox touches the player's Core Hitbox OR Graze Hitbox.
            //     - If YES: call player.collectItem(item)
            //
            //   - CASE B: Physical Contact with Enemy
            //     - Check whether entity is an instance of Enemy enemy
            //     - If the player's Core Hitbox collides with the enemy's Core Hitbox.
            //     - If YES: deal 10 body collision damage to the player: player.takeDamage(10)

            entities.addAll(newDrops);
        }

    }
