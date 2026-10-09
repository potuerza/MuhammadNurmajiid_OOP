package com.MuhammadNurmajiid.frontend.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.bullets.Bullet;
import com.MuhammadNurmajiid.frontend.objects.bullets.BulletType;
import com.MuhammadNurmajiid.frontend.objects.enemies.Boss;
import com.MuhammadNurmajiid.frontend.objects.enemies.Fairy;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;
import org.w3c.dom.Text;

public class EntityFactory {

    // Create a Player and assign the 'player_idle' animation from AssetManager
    public static Player createPlayer(float x, float y, String name, int hp, int power, int spellCards) {
        Player player = new Player(x, y, name, hp, power, spellCards);
        Animation<TextureRegion> idleanim = AssetManager.getInstance().getAnimation("player_idle");
        player.setAnimation(idleanim);
        return player;
    }

    // Create an Item and assign its sprite from AssetManager based on ItemType
    public static Item createItem(float x, float y, ItemType itemType) {
        Item item = new Item(x, y, itemType);
        String key = switch (itemType) {
            case POWER -> "item_power";
            case POINT -> "item_point";
            case BOMB  -> "item_bomb";
            case LIFE  -> "item_life";
        };
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(key);
        item.setSprite(sprite);
        return item;
    }

    // Create an enemy bullet (type DANMAKU, speed 0f, sprite bullet_danmaku)
    public static Bullet createEnemyBullet(float x, float y, int damage) {
        Bullet bullet = new Bullet(x, y, 0f, BulletType.DANMAKU, damage);
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
        bullet.setSprite(sprite);
        return bullet;
    }

    //Missing is based on the package, Boss and Enemies are NOT created yet

    public static Boss createBoss(float x, float y, String name, int hp) {
        Boss boss = new Boss(x, y, name, hp);
        Animation<TextureRegion> idleanim = AssetManager.getInstance().getAnimation("Boss");
        boss.setAnimation(idleanim);
        return boss;
    }

    public static Fairy createFairy(float x, float y, String name, int hp) {
        Fairy fairy = new Fairy(x, y, name, hp);
        Animation<TextureRegion> idleanim = AssetManager.getInstance().getAnimation("Red_Fairy");
        fairy.setAnimation(idleanim);
        return fairy;
    }


    public static Fairy createFairy(float x, float y, String name, int hp, String keyString) {
        // TODO:
        Fairy fairy = new Fairy(x, y, name, hp);
        Animation<TextureRegion> idleanim = AssetManager.getInstance().getAnimation(keyString);
        // 1. Create a new Fairy using x, y, name, and hp from the parameters;
        //    store it in a local variable named `fairy`.
        // 2. Retrieve the animation for keyString using getAnimation(...)
        //    from AssetManager.getInstance(). Store the result in
        //    a local variable named `idleAnim`.
        // 3. Assign idleAnim to fairy using fairy.setAnimation(...).
        // 4. Return fairy.
        fairy.setAnimation(idleanim);
        return fairy;
    }


    public static Bullet createPlayerBullet(float x, float y, int damage, String spriteKey) {
        // TODO 1: Retrieve the TextureRegion for spriteKey using getTextureRegion from
        // AssetManager.getInstance(), then store it in a local variable named `sprite`.

        // TODO 2:
        // Create a new Bullet using x, y, BulletType.AMULET, and damage;
        // store it in a local variable named `bullet`.

        // TODO 3:
        // Assign sprite to bullet using bullet.setSprite(...).

        // TODO 4:
        // Return bullet.
        return Bullet;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage) {
        // TODO 5:
        // Return the result of calling the previous createPlayerBullet overload with "bullet_amulet" as spriteKey.
    }


}
