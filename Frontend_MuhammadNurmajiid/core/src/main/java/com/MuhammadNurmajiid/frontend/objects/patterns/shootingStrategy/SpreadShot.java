package com.MuhammadNurmajiid.frontend.objects.patterns.shootingStrategy;

import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.patterns.ShootingPattern;
import com.MuhammadNurmajiid.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;

public class SpreadShot implements ShootingPattern {
    private float speed;
    private int numBullets;
    private float spreadAngleDegrees;
    private GameObject target;
    private float horizontalSpacing = 0f;
    private BulletMovementPattern bulletMovementPattern;
    private String spriteKey;

    // Master Constructor: Accommodates all attributes and configurations
    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, int damage, float horizontalSpacing,
                      BulletMovementPattern bulletMovementPattern, GameObject target, String spriteKey) {
        this.speed = speed;
        this.numBullets = numBullets;
        this.spreadAngleDegrees = spreadAngleDegrees;
        this.damage = damage;
        this.horizontalSpacing = horizontalSpacing;
        this.bulletMovementPattern = bulletMovementPattern;
        this.target = target;
        this.spriteKey = spriteKey;
    }


    // Static angle SpreadShot constructor
    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees) {
        this(speed, numBullets, spreadAngleDegrees, null);
    }

    // Targeted SpreadShot constructor (FAN_AIMED) that aims at a target
    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, GameObject target) {
        this.speed = speed;
        this.numBullets = numBullets;
        this.spreadAngleDegrees = spreadAngleDegrees;
        this.target = target;
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        float startX = (horizontalSpacing > 0f && numBullets > 1) ? (originX - ((numBullets - 1) * horizontalSpacing / 2f)) : originX;

        for (int i = 0; i < numBullets; i++) {
            float angleDeg = startAngle + (i * angleStep);
            float angleRad = (float) Math.toRadians(angleDeg);

            float vx = (float) (speed * Math.cos(angleRad));
            float vy = (float) (speed * Math.sin(angleRad));
            float spawnX = (horizontalSpacing > 0f && numBullets > 1) ? (startX + i * horizontalSpacing) : originX;

            Bullet bullet;
            if (isPlayer) {
                bullet = bulletManager.spawnPlayerBullet(spawnX, originY, vx, vy, bulletDamage, spriteKey);
            } else {
                bullet = (spriteKey != null)
                    ? bulletManager.spawnEnemyBullet(spawnX, originY, vx, vy, bulletDamage, spriteKey)
                    : bulletManager.spawnEnemyBullet(spawnX, originY, vx, vy, bulletDamage);
            }

            // Inject trajectory strategy into bullet if available
            if (bullet != null && bulletMovementPattern != null) {
                bullet.setMovementPattern(bulletMovementPattern);
            }
        }

    }

    public float getHorizontalSpacing() { return horizontalSpacing; }
    public void setHorizontalSpacing(float horizontalSpacing) { this.horizontalSpacing = horizontalSpacing; }

    public BulletMovementPattern getBulletMovementPattern() { return bulletMovementPattern; }
    public void setBulletMovementPattern(BulletMovementPattern bulletMovementPattern) { this.bulletMovementPattern = bulletMovementPattern; }

    public String getSpriteKey() { return spriteKey; }
    public void setSpriteKey(String spriteKey) { this.spriteKey = spriteKey; }


    // New constructor: injecting trajectory strategy (BulletMovementPattern)
    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, int damage, BulletMovementPattern bulletMovementPattern) {
        this(speed, numBullets, spreadAngleDegrees, damage, 0f, bulletMovementPattern, null, null);
    }

    // New constructor: injecting target and bullet color sprite (e.g., "bullet_danmaku_red")
    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, int damage, GameObject target, String spriteKey) {
        this(speed, numBullets, spreadAngleDegrees, damage, 0f, null, target, spriteKey);
    }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public int getNumBullets() { return numBullets; }
    public void setNumBullets(int numBullets) { this.numBullets = numBullets; }

    public float getSpreadAngleDegrees() { return spreadAngleDegrees; }
    public void setSpreadAngleDegrees(float spreadAngleDegrees) { this.spreadAngleDegrees = spreadAngleDegrees; }

    public GameObject getTarget() { return target; }
    public void setTarget(GameObject target) { this.target = target; }
}
