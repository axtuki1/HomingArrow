package io.github.axtuki1.homingarrow;

import io.github.axtuki1.homingarrow.command.HomingArrowCommand;
import io.github.axtuki1.homingarrow.listener.ArrowMoveListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class HomingArrow extends JavaPlugin {

    private static HomingArrow main;

    private HashMap<String, TabExecutor> commands;

    public static String getPrefix() {
        return ChatColor.GREEN + "[" + ChatColor.AQUA + "HomingArrow" + ChatColor.GREEN + "] " + ChatColor.WHITE;
    }

    public static HomingArrow getMain() {
        return main;
    }

    @Override
    public void onEnable() {
        main = this;

        saveDefaultConfig();
        reloadConfig();

        commands = new HashMap<>();
        commands.put("hmb", new HomingArrowCommand(this));

        getServer().getPluginManager().registerEvents(new ArrowMoveListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commands.get(command.getName()).onCommand(sender,command,label,args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return commands.get(command.getName()).onTabComplete(sender, command, alias, args);
    }
}
