package me.imrandoet.skullfetcher;

import me.imrandoet.skullcacher.api.ConfiguredSkullCacherClient;
import me.imrandoet.skullcacher.api.SkullCacherClient;
import me.imrandoet.skullfetcher.commands.CommandsModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SkullFetcher extends JavaPlugin {

    private SkullCacherClient skullCacherClient;

    @Override
    public void onEnable() {
        File cacheFolder = new File(getDataFolder(), "cache");
        if (!cacheFolder.exists()) cacheFolder.mkdirs();

        skullCacherClient = new ConfiguredSkullCacherClient(cacheFolder, false);
        skullCacherClient.setLogger(getSLF4JLogger());
        skullCacherClient.load();

        new CommandsModule(this);
    }

    @Override
    public void onDisable() {
        skullCacherClient.unload();
    }

    public SkullCacherClient getSkullCacherClient() {
        return skullCacherClient;
    }
}
