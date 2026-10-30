package com.MuhammadNurmajiid.frontend.objects;

import com.MuhammadNurmajiid.frontend.objects.patterns.ShootingPattern;
import com.MuhammadNurmajiid.frontend.objects.patterns.shootingStrategy.SpreadShot;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir = 0;
    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 30f);

    // TODO: Declare a private field named shootingPattern of type ShootingPattern.
    //       No need to initialize it right away.



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







    @Override
    public void update(float delta) {
        super.update(delta); // Advances stateTime for idle animations
        // Player movement handling (LibGDX input)
        float dx = 0;
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x -= speed * delta;
                dx -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x += speed * delta;
                dx += 1;
            }
        }
        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - width));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - height));

        updateAnimationState(dx);
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
        // TODO: Return a new 8x8 Rectangle centered right in the middle of the sprite:
        return new Rectangle(this.x + (width / 2f) - 4f, this.y + (height / 2f) - 4f, 8f, 8f);
        // - X coordinate: x + (width / 2f) - 4f
        // - Y coordinate: y + (height / 2f) - 4f
        // - width: 8f, height: 8f
    }


    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x, y, width, height);
        // TODO: Return a new Rectangle at position coordinates (x, y)
        //       covering the entire visual sprite dimensions (width, height)
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

    // TODO: Update shootBullet() to shootBullet(BulletManager bulletManager)!
    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) {
            return;
        }
        if (shootingPattern != null) {
            shootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true);
        }
        else {
            int damage = 10 + power;
            bulletManager.spawnPlayerBullet(x + width / 2f - 8f, y + height, 0f, 400f, damage);
            // - If bulletManager is null, return immediately
            // - If shootingPattern is not null, call execute() from the player's muzzle coordinates (x + width / 2f - 8f, y + height)
            // - If shootingPattern is null, call bulletManager.spawnPlayerBullet() directly
        }
    }




    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();
        if (item.isDestroyed()) return;
        if (type != null) {
            switch (type) {
                case POWER -> {
                    this.power += type.getPowerBonus();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POWER item! Power increased to " + power);
                }
                case POINT -> {
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POINT item!");
                }
                case BOMB -> {
                    this.spellCards++;
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected BOMB item! SpellCards: " + spellCards);
                }
                case LIFE -> {
                    this.hp += 20;
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected LIFE item! HP: " + hp);
                }

            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
        AudioManager.getInstance().playSound("sfx_item");
        item.destroy();

    }

    public void takeDamage(int damage) {
        setHp(getHp() - damage);
        System.out.println(getName() + " took " + damage + " damage! Remaining HP: " + getHp());
        if (getHp() == 0) {
            System.out.println(getName() + " was defeated (Pichuun~)! ");
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(name + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public boolean isAlive() {
        return getHp() > 0;
    }

    // Encapsulation getters and setters
    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public int getSpellCards() { return spellCards; }
    public void setSpellCards(int spellCards) { this.spellCards = spellCards; }

    public long getScore() { return score; }
}
