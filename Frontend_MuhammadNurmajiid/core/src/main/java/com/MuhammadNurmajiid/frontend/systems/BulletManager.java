package com.MuhammadNurmajiid.frontend.systems;

import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.bullets.Bullet;
import com.MuhammadNurmajiid.frontend.objects.bullets.BulletType;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;
import com.MuhammadNurmajiid.frontend.objects.patterns.ShootingPattern;
import com.MuhammadNurmajiid.frontend.objects.patterns.bulletStrategy.FantasySealMovement;
import com.MuhammadNurmajiid.frontend.objects.patterns.shootingStrategy.RingShot;
import com.MuhammadNurmajiid.frontend.systems.EntityFactory;

import com.MuhammadNurmajiid.frontend.objects.patterns.bulletStrategy.HomingBulletMovement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;


public class BulletManager {

    // [ADDITION] Add this import at the top of the file:

    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;
    private List<GameObject> entities; // [ADDITION] Add field to hold the arena entity list

    // 1. Pool Queues for Object Recycling (Object Pool Pattern)

    // 2. Lists of Active Bullets on Screen
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;


    public BulletManager() {
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();

        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
        // 1. Initialize playerBulletPool and enemyBulletPool as empty LinkedLists
        // 2. Initialize activePlayerBullets and activeEnemyBullets as empty ArrayLists
    }

    public void spawnBombOrbs(float originX, float originY, GameObject targetEnemy, List<GameObject> entities) {
        int orbCount = 8;
        int orbDamage = 35; // 35 damage x 8 orbs = 280 total spell card damage potential!

        List<GameObject> targetEntities = (entities != null) ? entities : this.entities;
        // TODO (2 points): Apply Strategy Composition — instantiate a RingShot object with bulletSpeed 150f, bullet count orbCount, damage orbDamage, and bullet movement strategy FantasySealMovement(targetEnemy, targetEntities), then call execute(originX, originY, this, true)
        RingShot ringShot = new RingShot(150f, orbCount, orbDamage, new FantasySealMovement(targetEnemy, targetEntities);

        ringShot.execute(originX, originY, this, true);

        System.out.println("[BulletManager] Spawned 8 Bomb Spirit Orbs via RingShot composed with FantasySealMovement!");
    }

    public void spawnBombOrbs(float originX, float originY, GameObject targetEnemy) {
        spawnBombOrbs(originX, originY, targetEnemy, null);
    }


    // [MODIFY] Update the Module 8 spawnPlayerBullet method with the following changes:
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage, String spriteKey) { // Add String spriteKey as a parameter
        String key = (spriteKey != null) ? spriteKey : "bullet_amulet"; // Add local String variable key
        Bullet bullet;
        if (!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
            bullet.setSprite(AssetManager.getInstance().getTextureRegion(key)); // Add setSprite using key
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage, key); // Add key as 4th argument
            bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        }
        activePlayerBullets.add(bullet);
        return bullet;
    }

    // [ADDITION] Add the following 5-parameter overloaded method (delegating to the 6-parameter method above):
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        return spawnPlayerBullet(x, y, vx, vy, damage, "bullet_amulet");
    }

    // [MODIFY] Update the Module 8 spawnEnemyBullet method with the following changes:
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage, String spriteKey) { // Add String spriteKey as a parameter
        String key = (spriteKey != null) ? spriteKey : "bullet_danmaku"; // Add local String variable key
        Bullet bullet;
        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
            bullet.setSprite(AssetManager.getInstance().getTextureRegion(key)); // Add setSprite using key
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, damage, key); // Add key as 4th argument
            bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        }
        activeEnemyBullets.add(bullet);
        return bullet;
    }

    // [ADDITION] Add the following 5-parameter overloaded method (delegating to the 6-parameter method above):
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        return spawnEnemyBullet(x, y, vx, vy, damage, "bullet_danmaku");
    }



    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();

            // [ADDITION] Insert this check right before bullet.update(delta):
            if (entities != null && bullet.getMovementPattern() instanceof HomingBulletMovement homing && homing.getEntities() == null) {
                homing.setEntities(entities);
            }

            bullet.update(delta);
        }


    // 2. Enemy bullet recycling & movement (5 Points)
        Iterator<Bullet> eIter = activeEnemyBullets.iterator();
        while (eIter.hasNext()) {
            Bullet bullet = eIter.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                eIter.remove();                 // Remove safely during traversal
                bullet.destroy();               // Reset active flag
                enemyBulletPool.offer(bullet); // Return to pool for reuse!
            }
        }
        // - Follow the exact same procedure as above for activeEnemyBullets
        // - Enemy bullets that go off-screen or are destroyed are returned to enemyBulletPool via offer()


    }

        // Helper methods to access pools and active lists
        public List<Bullet> getActivePlayerBullets () {
            return activePlayerBullets;
        }
        public List<Bullet> getActiveEnemyBullets () {
            return activeEnemyBullets;
        }
        public int getPlayerPoolSize () {
            return playerBulletPool.size();
        }
        public int getEnemyPoolSize () {
            return enemyBulletPool.size();
        }

    // Spell Card (Bomb) Execution: Clear all active enemy bullets and convert them into POINT item drops!
    public void clearEnemyBullets(List<GameObject> entities) {
        // TODO:
        // 1. Create an Iterator<Bullet> to safely traverse activeEnemyBullets.
        Iterator<Bullet> eIter = activeEnemyBullets.iterator();
        while (eIter.hasNext()) {
            Bullet bullet = eIter.next();

            if (entities != null) {
                entities.add(EntityFactory.createItem(bullet.getX(), bullet.getY(), ItemType.POINT));
            }

            bullet.destroy();
            eIter.remove();
            enemyBulletPool.offer(bullet);
        }
    }

            // 2. While the iterator has a next element:
            //    - Retrieve the current Bullet object.
            //    - If the entities parameter is not null:
            //      * Create an Item object of type ItemType.POINT using EntityFactory.createItem(...)
            //        at position (bullet.getX(), bullet.getY()), then add it to the entities list.
            //    - Mark the bullet as destroyed (bullet.destroy()).
            //    - Remove the bullet from activeEnemyBullets using remove() on the iterator from step 1.
            //    - Return the bullet to the enemyBulletPool queue using offer().
            // TIP: See the rough outline in `clearPlayerBullets()` below

        public void clearEnemyBullets () {
            clearEnemyBullets(null);
        }

        // Clear all player bullets when restarting the game (Copy-Paste):
        public void clearPlayerBullets () {
            Iterator<Bullet> pIter = activePlayerBullets.iterator();
            while (pIter.hasNext()) {
                Bullet bullet = pIter.next();
                bullet.destroy();
                pIter.remove();
                playerBulletPool.offer(bullet);
            }
        }


    // [ADDITION] Add the following getters & setters at the bottom of the BulletManager class:
    public List<GameObject> getEntities() { return entities; }
    public void setEntities(List<GameObject> entities) { this.entities = entities; }

    }


