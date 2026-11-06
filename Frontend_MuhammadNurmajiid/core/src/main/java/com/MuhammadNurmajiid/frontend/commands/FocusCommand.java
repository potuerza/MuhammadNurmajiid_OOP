package com.MuhammadNurmajiid.frontend.commands;

import com.MuhammadNurmajiid.frontend.objects.Player;
import com.MuhammadNurmajiid.frontend.systems.BulletManager;

public class FocusCommand implements Command {
    private boolean focused;

    public FocusCommand(boolean focused) {
        // TODO: Initialize the focused attribute from the constructor parameter
        this.focused = focused;
    }

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        player.setFocused(focused);

    }
    // TODO: Override `execute` with the following business logic:
    // - Call the player's setFocused(focused) method


}
