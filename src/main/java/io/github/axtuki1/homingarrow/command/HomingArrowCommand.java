package io.github.axtuki1.homingarrow.command;

import io.github.axtuki1.homingarrow.HomingArrow;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HomingArrowCommand implements TabExecutor {

    HomingArrow plugin;

    public HomingArrowCommand(HomingArrow plugin){ this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length == 0 ){
            List<String> msg = new ArrayList<>();
            msg.add(ChatColor.RED + "=======================================");
            msg.add(ChatColor.AQUA + "/hmb reload");
            msg.add(ChatColor.AQUA + "   Configを再読み込みします。");
            msg.add(ChatColor.AQUA + "/hmb distance <double>");
            msg.add(ChatColor.AQUA + "   検出範囲(半径)を設定します。");
            msg.add(ChatColor.AQUA + "/hmb setitem");
            msg.add(ChatColor.AQUA + "   効果を発揮するアイテム名を手持ち弓から設定します。");
            msg.add(ChatColor.AQUA + "/hmb removeitem");
            msg.add(ChatColor.AQUA + "   すべてのプレイヤーが放つ弓を対象にします。");
            msg.add(ChatColor.RED + "=======================================");
            sender.sendMessage(msg.toArray(new String[0]));
            return true;
        }
        if( args[0].equalsIgnoreCase("reload") ) {
            plugin.reloadConfig();
            sender.sendMessage(HomingArrow.getPrefix() + "Configを再読み込みしました。");
        }
        if( args[0].equalsIgnoreCase("distance") ) {
            if(args.length == 1){
                List<String> msg = new ArrayList<>();
                msg.add(ChatColor.RED + "=======================================");
                msg.add(ChatColor.AQUA + "/hmb reload");
                msg.add(ChatColor.AQUA + "   Configを再読み込みします。");
                msg.add(ChatColor.RED + "=======================================");
                sender.sendMessage(msg.toArray(new String[0]));
            } else {
                HomingArrow.getMain().saveConfig();
                HomingArrow.getMain().getConfig().set("DetectDistance", Double.valueOf(args[1]));
                sender.sendMessage(HomingArrow.getPrefix() + "範囲を設定しました。");
            }
        }
        if( args[0].equalsIgnoreCase("setitem") ) {
            if(sender instanceof Player){
                Player p = (Player)sender;
                ItemStack item = p.getEquipment().getItemInMainHand();
                if(item != null && item.getType().equals(Material.BOW)){
                    HomingArrow.getMain().getConfig().set("BowName", item.getItemMeta().getDisplayName());
                    sender.sendMessage(HomingArrow.getPrefix() + "アイテム名を設定しました。");
                    HomingArrow.getMain().saveConfig();
                } else {
                    sender.sendMessage(HomingArrow.getPrefix() + ChatColor.RED + "弓を手に持ってください。");
                }
            } else {
                sender.sendMessage(HomingArrow.getPrefix() + ChatColor.RED + "プレイヤーが実行してください。");
            }
        }
        if( args[0].equalsIgnoreCase("removeitem") ) {
            HomingArrow.getMain().getConfig().set("BowName", "");
            HomingArrow.getMain().saveConfig();
            sender.sendMessage(HomingArrow.getPrefix() + "すべての弓を対象にしました。");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<>();
        if( args.length == 1 ){
            for (String name : new String[]{
                    "reload", "distance", "setitem", "removeitem"
            }) {
                if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
                    out.add(name);
                }
            }
        }
        return out;
    }

}
