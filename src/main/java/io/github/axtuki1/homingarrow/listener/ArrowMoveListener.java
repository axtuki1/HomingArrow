package io.github.axtuki1.homingarrow.listener;

import io.github.axtuki1.homingarrow.HomingArrow;
import io.github.axtuki1.homingarrow.task.ArrowHomingTask;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class ArrowMoveListener implements Listener {

    @EventHandler
    public void OnArrowMoveEvent(EntityShootBowEvent e){
        if( !(e.getEntity() instanceof Player) ) return; // プレイヤーでない
        String bowName = HomingArrow.getMain().getConfig().getString("BowName");
        if(!bowName.equalsIgnoreCase("")){
            LivingEntity entity = e.getEntity();
            ItemStack item = entity.getEquipment().getItemInMainHand();
            if(item == null ||
                    !item.getType().equals(Material.BOW) ||
                    !item.getItemMeta().getDisplayName().equalsIgnoreCase(bowName)){
                return;
            }
        }
        new ArrowHomingTask(
                HomingArrow.getMain(),
                e.getEntity(),
                (Arrow) e.getProjectile(),
                HomingArrow.getMain().getConfig().getDouble("DetectDistance")
        ).start(1);
    }

}
