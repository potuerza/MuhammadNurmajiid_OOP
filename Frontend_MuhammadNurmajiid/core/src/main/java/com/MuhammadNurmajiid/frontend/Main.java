package com.MuhammadNurmajiid.frontend;

import com.MuhammadNurmajiid.frontend.objects.item.Item;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;
import com.MuhammadNurmajiid.frontend.objects.enemies.Fairy;
import com.MuhammadNurmajiid.frontend.objects.enemies.Boss;
import com.MuhammadNurmajiid.frontend.objects.enemies.Enemy;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.objects.GameObject;
import com.MuhammadNurmajiid.frontend.objects.item.ItemType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.T;
import static com.badlogic.gdx.Input.Keys.Z;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    private Player player;
    private Fairy fairy;
    private Boss boss;
    private Item pointItem;
    private Item powerItem;
    private List<GameObject> entities;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();

        player = new Player("Reimu Hakurei", 100, 15, 3);
        fairy = new Fairy("Stage 1 Fairy", 20);
        boss = new Boss("Cirno (Stage 2 Boss)", 150);

        powerItem = new Item(200, 450, 16, 16, 80f, ItemType.POWER, 500L);
        pointItem = new Item(320, 480, 12, 12, 120f, ItemType.POINT, 1000L);

        entities.add(player);
        entities.add(fairy);
        entities.add(boss);
        entities.add(pointItem);
        entities.add(powerItem);
    }

    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        // 1. Get an Iterator<T> from the given list.
        Iterator<T> iterator = list.iterator();
        // 2. While there are still elements available (hasNext()):
        while (iterator.hasNext()){
            T object = iterator.next();
            object.update(delta);
            if(object.isOffScreen(screenWidth,screenHeight) || object.isDestroyed()){
                System.out.println( "Removed via Generic Iterator: " + getClass().getSimpleName());
                iterator.remove();

            }
        }
        //    a. Get the current element using next() and store it in a variable of type T.
        //    b. Call update(delta) on the element.
        //    c. If the element is off-screen (isOffScreen(screenWidth, screenHeight))
        //       OR isDestroyed():
        //       - Display the message: "Removed via Generic Iterator: " + [entity class name, using getClass().getSimpleName()]
        //       - Remove the element from the list using the Iterator's method
        //         (NOT list.remove()!).
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // TODO 1: If the Z key was just pressed, add a new bullet from player.shootBullet()
        if (Gdx.input.isKeyPressed(Input.Keys.Z)){
            player.shootBullet();
        }
        // to the entities list.
        // Clue: Gdx.input.isKeyJustPressed()

        // TODO 2: Call updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // to update and clean up destroyed/off-screen entities.

        // 3. Collision detection between entities (skip entities that are already destroyed)
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

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject entity : entities) {
            // TODO 3: Use an if statement to check whether the entity has not been destroyed (!entity.isDestroyed()).
            // If so, call entity.render(shapeRenderer);
            if(!entity.isDestroyed()){
                entity.render(shapeRenderer);
            }
        }
        shapeRenderer.end();
    }


    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}

