package com.MuhammadNurmajiid.frontend;

import com.MuhammadNurmajiid.frontend.systems.AssetManager;
import com.MuhammadNurmajiid.frontend.systems.EntityFactory;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.enemies.Boss;
import com.MuhammadNurmajiid.frontend.objects.enemies.Fairy;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;

import com.MuhammadNurmajiid.frontend.systems.AudioManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;

    @Override
    public void create() {
        // TODO 1:
        shapeRenderer = new ShapeRenderer();
        // When initializing the renderer, create a SpriteBatch and store it in batch.
        // LibGDX hint: new SpriteBatch().
        batch = new SpriteBatch();
        // TODO 2:
        // Initialize the fairy and entities lists as empty ArrayLists.
        fairy = new ArrayList<>();
        entities = new ArrayList<>();

        // TODO 3:
        // Before creating entities, get the AssetManager instance and call init().
        AssetManager.getInstance().init();
        AudioManager.getInstance().init();

        AudioManager.getInstance().play("bgm_intro", "bgm_loop");



        // TODO 4:
        player = EntityFactory.createPlayer(280, 40, "Reimu Hakurei", 100, 15, 3);


        Fairy RedFairy = EntityFactory.createFairy(150, 380, "Red Fairy", 20);
        Fairy BlueFairy = EntityFactory.createFairy(250, 380,"Blue Fairy", 20, "fairy_idle_blue");

        boss = EntityFactory.createBoss(380, 400, "Rumia", 150);

        powerItem = EntityFactory.createItem(200, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(320, 480, ItemType.POINT);

        // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
        // using the appropriate EntityFactory methods.
        // Add both Fairies to the fairy list using add(...).

        // TODO 5:
        // Add all the objects you have just created to entities.
        fairy.add(RedFairy);
        fairy.add(BlueFairy);
        entities.add(player);
        entities.add(RedFairy);
        entities.add(BlueFairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);





        }




    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 1. Check Player Bullet shooting input (Key Z)
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            entities.add(player.shootBullet());
        }

        // 2. Generic update & safe removal of off-screen/destroyed entities using non-static instance method
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 3. Collision detection between active entities
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                if (!a.isDestroyed() && !b.isDestroyed()) {
                    if (a.getCoreHitbox().overlaps(b.getCoreHitbox())) {
                        a.onCollision(b);
                        b.onCollision(a);
                    }
                }
            }
        }

        // 4. Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }
        batch.end();

    }

    // Non-static (Instance) Generic Method with Bounded Type Parameter <T extends GameObject>
    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                System.out.println("Removed via Generic Iterator: " + entity.getClass().getSimpleName());
                iterator.remove(); // Safe removal using Iterator!
            }
        }
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        AssetManager.getInstance().dispose();
        AudioManager.getInstance().dispose();
    }
}
