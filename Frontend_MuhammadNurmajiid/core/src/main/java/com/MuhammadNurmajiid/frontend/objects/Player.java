package com.MuhammadNurmajiid.frontend.objects;

import com.MuhammadNurmajiid.frontend.objects.patterns.ShootingPattern;
import com.MuhammadNurmajiid.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.MuhammadNurmajiid.frontend.observers.GameObserver;
import com.MuhammadNurmajiid.frontend.systems.AssetManager;
import com.MuhammadNurmajiid.frontend.systems.AudioManager;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;
import com.MuhammadNurmajiid.frontend.systems.EntityFactory;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;
import com.MuhammadNurmajiid.frontend.objects.enemies.Boss;
import com.MuhammadNurmajiid.frontend.objects.enemies.Enemy;
import com.MuhammadNurmajiid.frontend.objects.enemies.Fairy;
import com.MuhammadNurmajiid.frontend.objects.bullets.Bullet;
import com.MuhammadNurmajiid.frontend.objects.bullets.BulletType;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class Player extends GameObject {
    private String name;
    private int hp;
    private int lives = 3;
    private int power;
    private int spellCards;
    private long score;
    private ShootingPattern shootingPattern;
    int grazeCount = 0;
    boolean focused = false;
    float shootTimer = 0.1f;
    float shootCooldown = 0.1f;
    float invulnerableTimer = 0f;

    private int currentDir = 0;

    // TODO: Declare a private attribute List<GameObserver> observers and initialize it with an empty ArrayList
    List<GameObserver> observers = new ArrayList<>();

    public void registerObserver(GameObserver observer) {
        // TODO:
        if (observer != null && !observers.contains(observer)){
            observer.onScoreChanged(score);
            observer.onHpChanged(hp);
            observer.onLivesChanged(lives);
            observer.onSpellCardsChanged(spellCards);
            observer.onPowerChanged(power);
            observer.onGrazeChanged(grazeCount);
        }
        // 1. Check whether observer is not null AND is not already in the observers list (!observers.contains(observer)).
        // 2. If valid:
        //    - Add observer to the observers list.
        //    - Broadcast all current initial player states to that observer:
        //      * observer.onScoreChanged(score);
        //      * observer.onHpChanged(hp);
        //      * observer.onLivesChanged(lives);
        //      * observer.onSpellCardsChanged(spellCards);
        //      * observer.onPowerChanged(power);
        //      * observer.onGrazeChanged(grazeCount);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    // Notification Broadcast Methods to All Observers (4 points)
    // TODO: Create the following 6 private void methods that LOOP THROUGH ALL ELEMENTS IN 'observers'
    //       and invoke the corresponding callback method:
    // Granular notification dispatchers
    private void notifyHpChanged() {
        for (GameObserver obs : observers) {
            obs.onHpChanged(this.hp);
        }
    }

    private void notifyScoreChanged() {
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.score);
        }
    }

    private void notifyLivesChanged(){
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.lives);
        }
    }

    private void notifySpellCardsChanged(){
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.spellCards);
        }
    }

    private void notifyPowerChanged(){
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.power);
        }
    }

    private void notifyGrazeChanged(){
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.grazeCount);
        }
    }

    // 1. notifyScoreChanged()      -> calls obs.onScoreChanged(score)
    // 2. notifyHpChanged()         -> calls obs.onHpChanged(hp)
    // 3. notifyLivesChanged()      -> calls obs.onLivesChanged(lives) (Touhou Life System)
    // 4. notifySpellCardsChanged() -> calls obs.onSpellCardsChanged(spellCards)
    // 5. notifyPowerChanged()      -> calls obs.onPowerChanged(power)
    // 6. notifyGrazeChanged()      -> calls obs.onGrazeChanged(grazeCount)

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 48, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 48, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }



    // Execute movement from MoveCommand with playfield boundary clamping (Playfield Clamping)
    public void move(float dx, float dy) {
        // TODO:
        this.x += dx * speed;
        this.y += dx * speed;
        clampToPlayfield();
        // 1. Increment x position by dx multiplied by speed (this.x += dx * speed)
        // 2. Increment y position by dy multiplied by speed (this.y += dy * speed)
        // 3. Call clampToPlayfield()
    }

    private void clampToPlayfield() {
        float minX = 32;
        float maxX = 416 - width;
        float minY = 16;
        float maxY = 560 - height;

        if (x < minX) x = minX;
        if (x > maxX) x = maxX;
        if (y < minY) y = minY;
        if (y > maxY) y = maxY;
        // TODO: Clamp x so it is not less than minX and not greater than maxX,
        //       and clamp y so it is not less than minY and not greater than maxY.
    }



    @Override
    public void update(float delta) {
        super.update(delta); // Advances stateTime for idle animations
        shootTimer += delta;
        // TODO: Increment shootTimer by delta (shootTimer += delta;)
        //       Make sure all old Gdx.input reading code inside update() has been removed

        if (invulnerableTimer > 0f) {
            invulnerableTimer -= delta;
            if (invulnerableTimer < 0f) invulnerableTimer = 0f;
        }
    }



    // Updates character animation state based on horizontal movement direction (dx)
    public void updateAnimationState(float dx) {
        AssetManager assets = AssetManager.getInstance();
        if (dx < 0) { // Moving Left
            if (currentDir != -1) {
                currentDir = -1;
                Animation<TextureRegion> anim = assets.getAnimation("player_left");
                if (anim != null) setAnimation(anim);
            }
        } else if (dx > 0) { // Moving Right
            if (currentDir != 1) {
                currentDir = 1;
                Animation<TextureRegion> anim = assets.getAnimation("player_right");
                if (anim != null) setAnimation(anim);
            }
        } else { // Idle / Stationary
            if (currentDir != 0) {
                currentDir = 0;
                Animation<TextureRegion> anim = assets.getAnimation("player_idle");
                if (anim != null) setAnimation(anim);
            }
        }
    }

    public void moveUp(float delta) { this.y += speed * delta; }
    public void moveDown(float delta) { this.y -= speed * delta; }
    public void moveLeft(float delta) { this.x -= speed * delta; }
    public void moveRight(float delta) { this.x += speed * delta; }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Item) {
            Item item = (Item) other;

            if(!item.isCollected()){
                System.out.println("Player touches items");
                collectItem((Item) other);
                item.setCollected(true);
            }


        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(this.x + (width / 2f) - 4f, this.y + (height / 2f) - 4f, 8f, 8f);
        // - X coordinate: x + (width / 2f) - 4f
        // - Y coordinate: y + (height / 2f) - 4f
        // - width: 8f, height: 8f
    }


    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x, y, width, height);
    }


    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        AudioManager.getInstance().playSound("sfx_shoot");
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        boolean defeated = target.takeDamage(damage);
        if (defeated) {
            AudioManager.getInstance().playSound("sfx_enemy_die");
            addScore(target.getScoreValue());
        }
    }

    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) {return;}
        AudioManager.getInstance().playSound("sfx_shoot");
        // TODO 1: Check whether shootTimer is greater than or equal to shootCooldown.
        // If YES:
        if (shootTimer >= shootCooldown) {
            if (shootingPattern != null) {
                shootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true);
            } else {
                bulletManager.spawnPlayerBullet(x + width / 2f - 8f, y + height, 0, 400f, 10 + power);
            }
        }
        // TODO 2: Reset shootTimer back to 0
        shootTimer = 0;
    }

    // Execute Spell Card (Bomb) from BombCommand
    public void useBomb(BulletManager bulletManager, List<GameObject> entities) {
        // TODO:
        if (spellCards > 0){
            spellCards -- ;
            notifySpellCardsChanged();
            invulnerableTimer = 5.0f;
            if (bulletManager != null){
                bulletManager.clearEnemyBullets(entities);
                System.out.println(name + "unleashes SPELL CARD (Fantasy Seal)! All enemy bullets cleared!");

            }
            else {
                System.out.println("No Spell Cards (Bombs) remaining!");
            }

        }
        // 1. Check whether spellCards > 0.
        // 2. If YES:
        //    - Decrement spellCards by 1 (spellCards--).
        //    - Activate invulnerability for 5 seconds: invulnerableTimer = 5.0f;
        //    - If bulletManager is not null, call bulletManager.clearEnemyBullets(entities).
        //    - Print to console: name + " unleashes SPELL CARD (Fantasy Seal)! All enemy bullets cleared!"
        // 3. If NO:
        //    - Print to console: "No Spell Cards (Bombs) remaining!"
    }


    public void takeDamage(int damage) {
        takeDamage(damage, null, null);
    }

    public void takeDamage(int damage, BulletManager bulletManager, List<GameObject> entities) {
        if (isInvulnerable() || isDestroyed()) {
            if (isInvulnerable()) {
                System.out.println(name + " is INVULNERABLE! (0 damage taken)");
            }
            return;
        }
        this.hp -= damage;
        notifyHpChanged();
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(name + " took " + damage + " damage! Remaining HP: " + this.hp);
        if (this.hp == 0) {
            System.out.println(name + " was defeated (Pichuun~)! ");
            loseLife(bulletManager, entities);
        }

    }

    public void loseLife(BulletManager bulletManager, List<GameObject> entities) {
        this.lives--;
        notifyLivesChanged();
        if (bulletManager != null) {
            bulletManager.clearEnemyBullets();
        }

        if (this.lives > 0) {
            if (bulletManager != null) {
                System.out.println("[Player] " + name + " lost a life! Remaining Lives: " + this.lives + ". Respawning at (200, 50)...");
            }
            respawn();
        } else {
            if (bulletManager != null) {
                System.out.println("[Player] GAME OVER! " + name + " has run out of lives!");
            }
            destroy();
        }
    }

    public void respawn() {
        this.active = true;
        this.x = 200;
        this.y = 50;
        this.hp = 100;
        this.invulnerableTimer = 3.0f;
        this.power = Math.max(10, this.power - 16); // Touhou death penalty
        this.spellCards = 3; // Reset bombs to 3 upon respawn
        notifyHpChanged();
        notifySpellCardsChanged();
        notifyPowerChanged();
    }

    public void resetGame() {
        this.lives = 3;
        this.hp = 100;
        this.power = 15;
        this.spellCards = 3;
        this.score = 0;
        this.grazeCount = 0;
        this.active = true;
        this.x = 200;
        this.y = 50;
        this.invulnerableTimer = 3.0f;
        notifyLivesChanged();
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
        notifyScoreChanged();
        notifyGrazeChanged();
    }

    public void collectItem(Item item) {
        if (item.isDestroyed()) return;

        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    this.power += type.getPowerBonus();
                    notifyPowerChanged();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POWER item! Power increased to " + power);
                }
                case POINT -> {
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POINT item!");
                }
                case BOMB -> {
                    this.spellCards++;
                    notifySpellCardsChanged();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected BOMB item! SpellCards: " + spellCards);
                }
                case LIFE -> {
                    this.lives++;   // Note: This line was added!
                    this.hp = 100;
                    notifyLivesChanged();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected LIFE item! Remaining Lives: " + lives);
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
        AudioManager.getInstance().playSound("sfx_item");
        item.destroy();
    }

    public boolean isAlive() {
        return this.hp > 0 && this.lives > 0 && !isDestroyed();
    }

    public boolean isGameOver() {
        return this.lives <= 0;
    }

    public boolean isInvulnerable() {
        return invulnerableTimer > 0f;
    }

    public float getInvulnerableTimer() {
        return invulnerableTimer;
    }

    public int getLives() { return lives; }
    public void setLives(int lives) {
        this.lives = lives;
        notifyLivesChanged();
    }


    public void useBomb(BulletManager bulletManager) {
        useBomb(bulletManager, null);
    }

    public void addGraze() {
        // TODO:
        this.grazeCount++;
        notifyGrazeChanged();
        addScore(50);
        // 1. Increment grazeCount by 1 (this.grazeCount++)
        // 2. Add 50 to the score using addScore(50)
        }


    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            notifyScoreChanged();
            System.out.println(name + " gained " + points + " pts! Total Score: " + this.score);
        }
    }



    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active && !isDestroyed()) {
            // High-frequency blinking animation while invulnerable (respawn / bomb i-frames)
            if (invulnerableTimer > 0f) {
                boolean visible = ((int) (invulnerableTimer * 16)) % 2 == 0;
                if (!visible) return; // Skip drawing this frame to create a blinking effect
            }

            if (focused) {
                batch.setColor(1f, 1f, 1f, 0.5f); // 50% opacity while Focus Mode is active
            }
            super.render(batch);
            if (focused) {
                batch.setColor(1f, 1f, 1f, 1.0f); // Restore opacity to 100%
            }
        }
    }


    // Draws the Core Hitbox indicator dot while Focus Mode (Shift) is active
    public void renderFocusIndicator(ShapeRenderer shapeRenderer) {
        if (focused && shapeRenderer != null && active) {
            Rectangle core = getCoreHitbox();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(core.x + core.width / 2f, core.y + core.height / 2f, 4f);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(core.x + core.width / 2f, core.y + core.height / 2f, 2f);
            shapeRenderer.end();
        }
    }


    // TODO: Create getter isFocused(), setter setFocused(boolean focused), and getter getGrazeCount()

    // Encapsulation getters and setters
    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public int getGrazeCount() {
        return grazeCount;
    }

    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); notifyHpChanged();}

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; notifyPowerChanged();}

    public int getSpellCards() { return spellCards; }
    public void setSpellCards(int spellCards) { this.spellCards = spellCards; notifySpellCardsChanged();}

    public long getScore() { return score; }
}
