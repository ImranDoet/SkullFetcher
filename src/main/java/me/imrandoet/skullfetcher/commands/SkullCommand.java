package me.imrandoet.skullfetcher.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Syntax;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.imrandoet.skullcacher.api.SkullCacheCallback;
import me.imrandoet.skullcacher.api.Texture;
import me.imrandoet.skullfetcher.SkullFetcher;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.ArrayList;
import java.util.UUID;

@CommandAlias("skull")
public class SkullCommand extends BaseCommand {

    private final SkullFetcher skullFetcher;

    public SkullCommand(SkullFetcher skullFetcher) {
        this.skullFetcher = skullFetcher;
    }

    @Default
    @Syntax("[player]")
    @Description("Gives you the skull of the specified player")
    public void onDefault(Player player, String target) {
        skullFetcher.getSkullCacherClient().generateTexture(target, new SkullCacheCallback() {
            @Override
            public void fail() {
                player.sendMessage(ChatColor.RED + "Failed fetching the skull!");
            }

            @Override
            public void error(Exception e) {
                player.sendMessage(ChatColor.RED + "Failed fetching the skull! (Error logged)");
                skullFetcher.getSLF4JLogger().error("Couldn't fetch skull of player " + target, e);
            }

            @Override
            public void start() {
                player.sendMessage(ChatColor.GREEN + "Fetching the skull...");
            }

            @Override
            public void success(Texture texture) {
                player.sendMessage(ChatColor.GREEN + "Fetched skull!");
                ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                skullMeta.setDisplayName(ChatColor.GOLD + target);

                PlayerProfile playerProfile = player.getPlayerProfile();
				playerProfile.setName(target);
				playerProfile.setId(UUID.randomUUID());
				playerProfile.setProperties(new ArrayList<ProfileProperty>());
                playerProfile.setProperty(new ProfileProperty("textures", texture.value, texture.signature));
				skullMeta.setPlayerProfile(playerProfile);
				
				itemStack.setItemMeta(skullMeta);
                player.getInventory().addItem(itemStack);
            }
        });
    }

}
