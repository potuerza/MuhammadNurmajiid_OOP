package com.MuhammadNurmajiid.frontend.commands;

import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}
