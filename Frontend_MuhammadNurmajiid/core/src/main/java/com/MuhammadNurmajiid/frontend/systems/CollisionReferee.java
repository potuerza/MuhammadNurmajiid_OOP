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
        // 2. Mediator checks Enemy Bullets vs Player (Core Hitbox vs Graze Hitbox)
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;

            if (!player.isDestroyed()) {
                // Priority A: Direct Core Hitbox Collision (Damage & Life Loss)
                if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                    // TODO 1: Wrap the contents of this block with an `if (!player.isInvulnerable())` check,
                    for (GameObject entity : entities) {
                        if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                            if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                                boolean defeated = enemy.takeDamage(bullet.getDamage());
                                if (defeated) {
                                    player.addScore(enemy.getScoreValue());
                                    add(EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER));
                                }
                                // Core Business Logic:
                                // Enemy takes damage, player gets score, etc.
                                // Bullet gets marked to be destroyed
                                bullet.destroy();
                            }
                        }
                    }


                    System.out.println("[CollisionReferee] CORE HIT! Enemy Bullet hit " + player.getName() + " for " + bullet.getDamage() + " DMG!");
                    // TODO 2: Replace `player.takeDamage(bullet.getDamage())` with `player.takeDamage(100, bulletManager, entities)`
                    player.takeDamage(100, bulletManager, entities)
                    bullet.destroy(); // Mark bullet for recycling by BulletManager
                    break;
                    // TODO 3: Add `break` at the end!
                }
                // Priority B: Graze Hitbox Collision (Passes close to player without hitting core)
                else if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                    bullet.setGrazed(true);
                    for (Bullet bullet1 : bulletManager.getActiveEnemyBullets()) {
                        if (bullet.isDestroyed()) continue;
                        if (player.isDestroyed()) continue;

                        if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                            player.takeDamage(bullet.getDamage());
                            bullet.destroy();
                        } else if (!bullet.hasBeenGrazed() &&
                            bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                            bullet.setGrazed(true);
                            player.addGraze();
                            // TODO 4: Replace player.addScore(50) with a call to the player's addGraze() method
                            System.out.println("[CollisionReferee] GRAZE! " + player.getName() + " grazed an enemy bullet (+50 pts)!");
}
                    }
                }

                // 3. Mediator checks Player vs Items and Enemies (Core vs Graze)
                    for (GameObject entity : entities) {
                        // ... Item checking code from Module 7 remains the same ...
                    } if (entities instanceof Enemy enemy) {
                        if (player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                            // TODO 5: Wrap the contents of this block with `if (!player.isInvulnerable())`
                            // 3. Evaluate Player vs Items and Physical Enemy Contact
                            for (GameObject entity : entities) {
                                if (entity.isDestroyed()) {
                                    continue;
                                }

                                if (entity instanceof Item item) {
                                    if (item.getCoreHitbox().overlaps(player.getCoreHitbox()) ||
                                        item.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                                        player.collectItem(item);
                                    }
                                }

                                if (entity instanceof Enemy enemy) {
                                    if (player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                                        player.takeDamage(100, bulletManager, entities);
                                    }
                                }
                            }
                        System.out.println("[CollisionReferee] CORE COLLISION! Player collided with " + enemy.getName() + "!");
                        // TODO 6: Replace `player.takeDamage(10)` with `player.takeDamage(100, bulletManager, entities)`
                    }
                }
            }
        }
        entities.addAll(newDrops);
    }
}
