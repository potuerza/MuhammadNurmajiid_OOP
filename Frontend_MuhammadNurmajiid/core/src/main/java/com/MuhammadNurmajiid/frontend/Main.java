package com.MuhammadNurmajiid.frontend;

import com.MuhammadNurmajiid.frontend.commands.Command;
import com.MuhammadNurmajiid.frontend.objects.bullets.Bullet;
import com.MuhammadNurmajiid.frontend.systems.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.enemies.Boss;
import com.MuhammadNurmajiid.frontend.objects.enemies.Fairy;
import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;

import com.MuhammadNurmajiid.frontend.commands.InputHandler;
import com.MuhammadNurmajiid.frontend.ui.GameHUD;

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

    private float enemyShootTimer = 0f;
    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        // TODO: Instantiate new objects for inputHandler and gameHUD
        InputHandler = new InputHandler();
        GameHUD = new GameHUD();

        AssetManager.getInstance().init();

        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);
        // ... fairy, boss, and item creation ...

        // TODO: Register gameHUD as an Observer on the player object!
        player.registerObserver(GameHUD);
        // Hint: player.registerObserver(gameHUD);

        entities.add(player);
        // ...
        entities.add(powerItem);
        entities.add(pointItem);

        // TODO: Register the entities list to inputHandler for bullet conversion during a Bomb!
        inputHandler.setEntities(entities);
        // Hint: inputHandler.setEntities(entities);

        @Override
        public void create () {
            shapeRenderer = new ShapeRenderer();
            // When initializing the renderer, create a SpriteBatch and store it in batch.
            // LibGDX hint: new SpriteBatch().
            batch = new SpriteBatch();
            // Initialize the fairy and entities lists as empty ArrayLists.
            fairy = new ArrayList<>();
            entities = new ArrayList<>();
            bulletManager = new BulletManager();
            collisionReferee = new CollisionReferee();


            // Before creating entities, get the AssetManager instance and call init().
            AssetManager.getInstance().init();
            AudioManager.getInstance().init();

            AudioManager.getInstance().play("bgm_intro", "bgm_loop");

            player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);

            fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));

            fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
            boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
            powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
            pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);


            // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
            // using the appropriate EntityFactory methods.
            // Add both Fairies to the fairy list using add(...).

            // Add all the objects you have just created to entities.
            entities.add(player);
            entities.addAll(fairy);
            entities.add(boss);
            entities.add(powerItem);
            entities.add(pointItem);

        }
    }


        @Override
        public void render() {
            float delta = Gdx.graphics.getDeltaTime();

            // 0. Restart Game on 'R' key when Game Over
            if (Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.R) || player.isGameOver() == true){
                restartGame();
            }
            // TODO: Check if Gdx.input != null AND the R key was just pressed (isKeyJustPressed(Input.Keys.R))
            //       AND player.isGameOver() is true: call restartGame();

            // 1. Process all player inputs through the Command Pattern (Invoker Command Stream)
            // TODO:
            List<Command> commands = inputHandler.handleInput(delta);
            command.execute(player, bulletManager);

            // - Retrieve the command stream: List<Command> commands = inputHandler.handleInput(delta);
            // - Loop through each command in commands and call: command.execute(player, bulletManager);
            // (Remove any old manual Gdx.input checks in render() if still present!)

            // 2. Boss periodic shooting scheduler
            updateEnemyScheduler(delta);

            // 3. Generic update & clean for standard entities within Playfield bounds (416, 560)
            updateAndClean(entities, delta, 416, 560);

            // 4. Update and recycle Object Pool within Playfield bounds (416, 560)
            bulletManager.update(delta, 416, 560);

            // 5. Evaluate collisions through the Mediator Pattern (CollisionReferee)
            collisionReferee.resolveCollisions(player, entities, bulletManager);

            // 6. Clear the screen
            ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

            // 7. Render active entity sprites, pooled bullets, and GameHUD Observer text
            batch.begin();
            for (GameObject entity : entities) {
                if (!entity.isDestroyed()) {
                    entity.render(batch);
                }
            }
            bulletManager.render(batch);
            // TODO: Call gameHUD's render method with batch as parameter
            GameHUD.render(batch);
            batch.end();

            // 8. Render UI border frame lines & Focus Mode Core Hitbox indicator (Drawn ON TOP of sprites!)
            GameHUD.renderBackground();
            player.renderFocusIndicator(gameHUD.getShapeRenderer());
        }

        @Override
        public void dispose() {
            if (batch != null) {
                batch.dispose();
            }
            // TODO: If gameHUD is not null, call gameHUD.dispose()
            AssetManager.getInstance().dispose();
        }


    private void restartGame() {
        System.out.println("[Main] Restarting game...");
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(player);
        player.resetGame();

        fairy.clear();
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
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
