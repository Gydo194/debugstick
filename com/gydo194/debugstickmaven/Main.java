/*
 * DebugStick plugin v1.0
 * GPLv3
 * March 2019
 * By Gydo194
 */
package com.gydo194.debugstickmaven;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Gydo194
 */
public class Main extends JavaPlugin implements CommandExecutor {

    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.setupPermissions();
        this.setupChat();

        this.getCommand("debugstick").setExecutor(this);

        this.getLogger().severe("DebugStick plugin by Gydo194 has successfully loaded");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Error -- Only players can use this command");
            return false;
        }

        if (!(this.getPermissions().playerHas((Player) sender, "debugstick.use"))) {
            ((Player) sender).sendMessage("Thou shall not make use of the glorious debug stick!");
            return false;
        }
        
        ((Player) sender).sendMessage("Have your glorious debug stick please.");

        ItemStack debugstickStack = new ItemStack(Material.DEBUG_STICK);
        debugstickStack.setAmount(1);
        ((Player) sender).getInventory().addItem(debugstickStack);
        return true;
    }

    public Economy getEcononomy() {
        return economy;
    }

    public Permission getPermissions() {
        return permission;
    }

    public Chat getChat() {
        return chat;
    }

}
