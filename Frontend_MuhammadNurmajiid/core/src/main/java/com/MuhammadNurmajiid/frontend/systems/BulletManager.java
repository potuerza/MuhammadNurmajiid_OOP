package com.MuhammadNurmajiid.frontend.systems;

import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.bullets.Bullet;
import com.MuhammadNurmajiid.frontend.objects.bullets.BulletType;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;
import com.MuhammadNurmajiid.frontend.systems.EntityFactory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class BulletManager {
    // 1. Pool Queues for Object Recycling (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

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

    // Borrowing Player Bullet from Pool (5 Points)
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.poll();
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage);
        }
        bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        activePlayerBullets.add(bullet);
        return bullet;
    }

    // Borrowing Enemy Bullet from Pool (5 Points)
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll();
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, damage);
        }
        bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        activeEnemyBullets.add(bullet);
        return bullet;
        // 1. Check whether enemyBulletPool is not empty:
        //    If so, poll from the pool. If not, create a new one using the Factory.
        // 2. Re-initialize the bullet.
        // 3. Add the bullet to the activeEnemyBullets list
        // 4. Return the bullet instance
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
        // 1. Player bullet recycling & movement (5 Points)

        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                pIter.remove();                 // Remove safely during traversal
                bullet.destroy();               // Reset active flag
                playerBulletPool.offer(bullet); // Return to pool for reuse!
            }
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

    }


