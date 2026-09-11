package com.MuhammadNurmajiid.frontend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameObject {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;

    public void update(float delta) {

    }

    public void render(ShapeRenderer shapeRenderer) {

    }

    public GameObject(float x, float y, float width, float height, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }


    public float getX(){
        return this.x = x;
    }

    public float getY() {
        return this.y = y;
    }

    public void setX(float x) {
        if (width > 0) this.x = width;
    }

    public void setY(float y) {
        if (height > 0) this.y = height;
    }

    public float getSpeed(float speed) {
        return this.speed = speed;
    }

    public void setSpeed(float speed) {
        if (speed >= 0) this.speed = speed;
    }

    public float getWidth{
        return this.width = width;
    }

    public void setWidth(float width) {
        if (width > 0) this.width = width;
    }

    public float getHeight{
        return this.height;
    }
    public void setHeight(float height) {
        if (height > 0) this.height = height;
    }





}
