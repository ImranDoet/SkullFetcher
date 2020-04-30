package me.imrandoet.skullfetcher.commands;

import co.aikar.commands.PaperCommandManager;
import me.imrandoet.skullfetcher.SkullFetcher;

public class CommandsModule {

    private final SkullFetcher skullFetcher;
    private PaperCommandManager paperCommandManager;

    public CommandsModule(SkullFetcher skullFetcher) {
        this.skullFetcher = skullFetcher;

        load();
    }

    public void load() {
        this.paperCommandManager = new PaperCommandManager(skullFetcher);

        this.paperCommandManager.registerCommand(new SkullCommand(skullFetcher));
    }
}
