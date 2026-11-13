package com.MuhammadNurmajiid.frontend.commands;

import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;

public class ShootCommand implements Command {
    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.shootBullet(bulletManager);
        }
    }
}
