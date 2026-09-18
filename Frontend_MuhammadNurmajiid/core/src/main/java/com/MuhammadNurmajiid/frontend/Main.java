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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        for (GameObject obj : entities) {
            obj.update(delta);

        }

        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);
                if(a.getCoreHitbox().overlaps(b.getCoreHitbox())){
                    a.onCollision(a);
                    b.onCollision(b);
                }
                // TODO: Check whether getCoreHitbox() of a and b overlap (use the .overlaps() method of Rectangle)
                // TODO: Call a.onCollision(b) and b.onCollision(a)
            }
        }

        // AABB Collision detection between every unique entity pair


        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            obj.render(shapeRenderer);
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

