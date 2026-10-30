package com.MuhammadNurmajiid.frontend.objects.patterns;

import com.MuhammadNurmajiid.frontend.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);

}
