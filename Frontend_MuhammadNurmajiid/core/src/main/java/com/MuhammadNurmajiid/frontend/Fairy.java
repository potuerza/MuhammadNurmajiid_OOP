package com.MuhammadNurmajiid.frontend;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;

public class Fairy extends Enemy{
    public Fairy(String name, int hp){
        super(150, 380, 24, 24, Color.PINK, name, hp, 500L);
    }

    public Fairy(float x, float y, String name, int hp){
        super(x, y, 24, 24, Color.PINK, name, hp, 500L);
    }

}
