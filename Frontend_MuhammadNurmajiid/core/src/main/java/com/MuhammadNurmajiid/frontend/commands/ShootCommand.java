package com.MuhammadNurmajiid.frontend.commands;

import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;
import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;

public class ShootCommand implements Command {
    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null){
            player.shootBullet(bulletManager);
        }
    }
    // TODO: Override `execute` with the following business logic:

    // - Check if player exists (is not null)
    // - If so, call the player's shootBullet(bulletManager) method
}
