package com.MuhammadNurmajiid.frontend;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;

public class Boss extends Enemy{
    public Boss (String name, int hp){
        super(380, 480, 48, 48, Color.BLUE, name, hp, 500L);
    }
    public Boss (float x, float y, String name, int hp){
        super(x, y, 48, 48, Color.BLUE, name, hp, 500L);
    }
}
