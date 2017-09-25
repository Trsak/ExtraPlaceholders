/*
 * Spigot plugin for extra placeholders using PlaceholderAPI
 * @author Petr 'Trsak' Sopf
 */
package cz.trsak.extraplaceholders;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ExtraPlaceholdersPlugin extends JavaPlugin {
    private Logger log;

    /**
     * Method called when plugin is enabled
     */
    @Override
    public void onEnable() {
        log = getLogger();

        //Enable plugin only if there are PlaceholderAPI and Residence plugins
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null || getServer().getPluginManager().getPlugin("Residence") == null) {
            log.warning("PlaceholderAPI not found!");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            //Dependencies are ok, let's hook extra placeholders
            new PlaceholdersHook(this).hook();
            log.info("ExtraPlaceholders enabled..");
        }
    }

    /**
     * Method called when plugin is disabled
     */
    @Override
    public void onDisable() {
        log.info("ExtraPlaceholders disabled..");
    }
}
