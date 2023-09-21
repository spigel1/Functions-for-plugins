package me.eranspigel.functions;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public final class F extends JavaPlugin {

    private static F plugin;
    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getConfigString(String s) {
        return plugin.getConfig().getString(s);
    }

    public static String getColorizeConfigString(String s) {
        return F.colorize(plugin.getConfig().getString(s));
    }

    public static List<String> getConfigStringList(String s) {
        return plugin.getConfig().getStringList(s);
    }

    public static int getConfigInt(String s) {
        return plugin.getConfig().getInt(s);
    }

    public static double getConfigDouble(String s){
        return plugin.getConfig().getDouble(s);
    }

    public static boolean getConfigBoolean(String s) {
        return plugin.getConfig().getBoolean(s);
    }

    // Check if the args are right, returns true/false.
    public static boolean argsCheck(CommandSender sender, int length, String[] args) {
        if (args.length != length) {
            sender.sendMessage(F.getColorizeConfigString("illegalArguments"));
            return false;
        }
        return true;
    }

    // Check if the target is a player, returns true/false.
    public static boolean playerCheck(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        sender.sendMessage(F.getColorizeConfigString("notAPlayer"));
        return false;
    }

    // Check if the target is offline/null, returns true/false.
    public static boolean offlineCheck(Player target, CommandSender sender, String replacement) {
        if (target == null) {
            sender.sendMessage(F.getColorizeConfigString("targetNull").replaceAll("%target%", replacement));
            return false;
        }
        return true;
    }

    // Check if the sender has the needed permission, returns true/false.
    public static boolean permCheck(CommandSender sender, String perm) {
        if (sender.hasPermission(perm)) {
            return true;
        }
        sender.sendMessage(F.getColorizeConfigString("noPermission"));
        return false;
    }

    // Check if the sender and the target are the same, returns true/false.
    public static boolean duplicateCheck(Player sender, Player target) {
        if (sender == target) {
            sender.sendMessage(F.getColorizeConfigString("duplicateMessage"));
            return false;
        }
        return true;
    }

    // Append args to a message and return the appended string
    public static String appendArgs(String[] args) {
        StringBuilder x = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            x.append(args[i]).append(" ");
        }
        return x.toString();
    }

    // Send message to a CommandSender
    public static void sendMsg(CommandSender sender, String path) {
        sender.sendMessage(F.getColorizeConfigString(path));
    }

    // Send message to a CommandSender with a .replaceAll
    public static void sendMsg(CommandSender sender, String path, String replace, String replacement) {
        sender.sendMessage(F.getColorizeConfigString(path).replaceAll(replace, replacement));
    }

    // Send message to a CommandSender with 2 .replaceAll
    public static void sendMsg(CommandSender sender, String path, String replace, String replacement, String replace2, String replacement2) {
        sender.sendMessage(F.getColorizeConfigString(path).replaceAll(replace, replacement).replaceAll(replace2, replacement2));
    }

    // Send a raw message to a CommandSender
    public static void sendRawMsg(CommandSender sender, String msg){
        sender.sendMessage(F.colorize(msg));
    }

    // Send message to a player
    public static void sendMsg(Player player, String path) {
        player.sendMessage(F.getColorizeConfigString(path));
    }

    // Send message to a player with a .replaceAll
    public static void sendMsg(Player player, String path, String replace, String replacement) {
        player.sendMessage(F.getColorizeConfigString(path).replaceAll(replace, replacement));
    }

    // Send message to a player with 2 .replaceAll
    public static void sendMsg(Player player, String path, String replace, String replacement, String replace2, String replacement2) {
        player.sendMessage(F.getColorizeConfigString(path).replaceAll(replace, replacement).replaceAll(replace2, replacement2));
    }

    // Send a raw message to a player
    public static void sendRawMsg(Player player, String msg){
        player.sendMessage(F.colorize(msg));
    }

    // Convert a string list into messages and send them to a CommandSender
    public static void sendMessageList(CommandSender sender, String path) {
        for (String output : F.getConfigStringList(path)) {
            sender.sendMessage(F.colorize(output));
        }
    }

    // Convert a string list into messages and send them to a player
    public static void sendMessageList(Player player, String path) {
        for (String output : F.getConfigStringList(path)) {
            player.sendMessage(F.colorize(output));
        }
    }

    // Util to create ItemStacks easier and quickly
    public static ItemStack createItem(Material material, String displayName, ArrayList<String> lore, short amount) {
        ItemStack itemStack = new ItemStack(material, 1, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) throw new AssertionError();
        itemMeta.setDisplayName(F.colorize(displayName));
        // Avoid exceptions and make putting no lore possible
        if (lore != null) {
            ArrayList<String> newLore = new ArrayList<>();
            for (String loreLine : lore) {
                newLore.add(F.colorize(loreLine));
            }
            itemMeta.setLore(newLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static void teleportPlayerToCoordinates(Player player, double x, double y, double z) {
        World world = player.getWorld();
        Location targetLocation = new Location(player.getWorld(), 1, 105.7, 2);
        player.teleport(targetLocation);
    }

    public static void teleportPlayerToPlayer(Player player, Player targetPlayer) {
        Location targetLocation = (Location) targetPlayer.getLocation();
        player.teleport(targetLocation);
    }
    public static void giveItem(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(item);
        } else {
            player.sendMessage("Your inventory is full");
        }
    }

    public static boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
    public static boolean configContains(String path) {
        return plugin.getConfig().contains(path);
    }

    public static void setConfigValue(String path, Object value) {
        FileConfiguration config = plugin.getConfig();
        config.set(path, value);
        plugin.saveConfig(); // Save changes to the config file
    }

    public static Object getConfigValue(String path) {
        return plugin.getConfig().get(path);
    }
    public static int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    // Function to clear a player's inventory
    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory(); // Update the player's inventory visually
    }
    // Function to check if a player has a specific item
    public static boolean hasItem(Player player, ItemStack item) {
        return player.getInventory().contains(item);
    }

    // Function to remove a specific item from a player's inventory
    public static void removeItem(Player player, ItemStack item) {
        player.getInventory().removeItem(item);
    }

    // Function to set a player's game mode
    public static void setGameMode(Player player, org.bukkit.GameMode gameMode) {
        player.setGameMode(gameMode);
    }
    // Function to clear all potion effects from a player
    public static void clearPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    // Function to check if a player is online
    public static boolean isPlayerOnline(String playerName) {
        Player player = Bukkit.getPlayerExact(playerName);
        return player != null && player.isOnline();
    }
    // Function to heal a player by a specific amount
    public static void healPlayer(Player player, double amount) {
        double maxHealth = player.getMaxHealth();
        double currentHealth = player.getHealth();
        double newHealth = Math.min(maxHealth, currentHealth + amount);
        player.setHealth(newHealth);
    }

    // Function to damage a player by a specific amount
    public static void damagePlayer(Player player, double amount) {
        double currentHealth = player.getHealth();
        double newHealth = Math.max(0, currentHealth - amount);
        player.setHealth(newHealth);
    }
}


