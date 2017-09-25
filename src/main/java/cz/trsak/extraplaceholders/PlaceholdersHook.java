/*
 * Spigot plugin for extra placeholders using PlaceholderAPI
 * @author Petr 'Trsak' Sopf
 */
package cz.trsak.extraplaceholders;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaceholdersHook extends EZPlaceholderHook {
    /**
     * Constructor, hooks extra placeholders with name tartaros for PlaceholderAPI
     *
     * @param plugin Plugin instance
     */
    PlaceholdersHook(JavaPlugin plugin) {
        super(plugin, "tartaros");
    }

    /**
     * Function called when player uses placeholder with name tartaros
     *
     * @param p          Player that used placeholder
     * @param identifier Placeholder identifier
     * @return Returns placeholder replacement
     */
    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) {
            return "";
        }

        switch (identifier) {
            case "biome":
                return getBiome(p);
            case "compass":
                return getCompass(p);
            case "residence":
                return getResidence(p);
            case "residenceowner":
                return getResidenceOwner(p);
            case "worldtime":
                return getWorldTime(p);
            case "lightlevel":
                return getLightLevel(p);
        }

        return null;
    }

    /**
     * Gets player's current biome and adjusts it to readable form
     *
     * @param p Player that used placeholder
     * @return String Player's current biome
     */
    private String getBiome(Player p) {

        Location loc = p.getLocation();
        World world = loc.getWorld();

        Biome biome = world.getBiome(loc.getBlockX(), loc.getBlockZ());

        String name = biome.name();

        name = name.replaceAll("_", " ");
        name = name.substring(0, 1) + name.substring(1).toLowerCase();

        return name;
    }

    /**
     * Gets player's looking direction a determines world direction
     *
     * @param p Player that used placeholder
     * @return String World direction
     */
    private String getCompass(Player p) {
        float yaw = p.getLocation().getYaw();

        if (yaw < 0) {
            yaw += 360;
        }

        if (yaw > 360 || yaw < 45) {
            return ChatColor.YELLOW + "Jih";
        } else if (yaw <= 90) {
            return ChatColor.YELLOW + "Jihozapad";
        } else if (yaw < 135) {
            return ChatColor.YELLOW + "Zapad";
        } else if (yaw <= 180) {
            return ChatColor.YELLOW + "Severozapad";
        } else if (yaw < 225) {
            return ChatColor.YELLOW + "Sever";
        } else if (yaw <= 270) {
            return ChatColor.YELLOW + "Severovychod";
        } else if (yaw < 315) {
            return ChatColor.YELLOW + "Vychod";
        } else if (yaw <= 360) {
            return ChatColor.YELLOW + "Jihovychod";
        }

        return ChatColor.YELLOW + "Sever";
    }

    /**
     * Gets residence name of residence located on player's location
     *
     * @param p Player that used placeholder
     * @return String Residence name
     */
    private String getResidence(Player p) {
        ResidenceManager manager = Residence.getInstance().getResidenceManager();

        ClaimedResidence res = manager.getByLoc(p.getLocation());

        if (res == null) {
            return ChatColor.YELLOW + "Zadna";
        }

        return ChatColor.YELLOW + res.getName();
    }

    /**
     * Gets residence owner of residence located on player's location
     *
     * @param p Player that used placeholder
     * @return String Residence owner
     */
    private String getResidenceOwner(Player p) {
        ResidenceManager manager = Residence.getInstance().getResidenceManager();

        ClaimedResidence res = manager.getByLoc(p.getLocation());

        if (res == null) {
            return ChatColor.YELLOW + "Zadna";
        }

        return ChatColor.YELLOW + res.getOwner();
    }

    /**
     * Gets current world time
     *
     * @param p Player that used placeholder
     * @return String Current world time in format hh:mm
     */
    private String getWorldTime(Player p) {
        int hours = (int) (p.getWorld().getTime() / 1000 + 8) % 24;
        int minutes = (int) (60 * (p.getWorld().getTime() % 1000) / 1000);

        return ChatColor.YELLOW + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }

    /**
     * Gets light level on player's position
     *
     * @param p Player that used placeholder
     * @return String Light level
     */
    private String getLightLevel(Player p) {
        Byte light = p.getLocation().getBlock().getLightLevel();
        return ChatColor.YELLOW + String.valueOf(light);
    }
}
