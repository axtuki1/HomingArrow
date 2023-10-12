package io.github.axtuki1.homingarrow.task;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ArrowHomingTask extends BasicTask{

    private final Arrow traceTargetArrow;
    private final Entity shooter;
    private final double distance;
    private Location beforeLocation;

    private final double stopDetectDistance = 0.1d;


    public ArrowHomingTask(JavaPlugin plugin, Entity shooter, Arrow arrow, double distance){
        super(plugin);
        this.shooter = shooter;
        this.traceTargetArrow = arrow;
        this.distance = distance;
    }

    @Override
    public void run() {
        try {
            Location currentLocation = traceTargetArrow.getLocation();
            // 着弾済み検知
            if(beforeLocation != null && beforeLocation.distance(currentLocation) <= stopDetectDistance){
                cancel();
                return;
            }
            // Mob判定
            Entity target = null;
            double targetDistance = distance;
            World world = currentLocation.getWorld();
            for(Entity e : world.getNearbyEntities(currentLocation,distance,distance,distance)){
                if(!(e instanceof LivingEntity) || e.equals(shooter)) continue;
                double currentDistance = e.getLocation().distance(currentLocation);
                if(targetDistance >= currentDistance){
                    targetDistance = currentDistance;
                    target = e;
                }
            }
            if(target != null){
                Location targetLoc = target.getLocation();
                Vector v = targetLoc.toVector().subtract(currentLocation.toVector());
                traceTargetArrow.setVelocity(v.normalize());
            }
            world.spawnParticle(Particle.CLOUD, currentLocation, 5);
            beforeLocation = currentLocation;
        } catch (Exception e) {
            e.printStackTrace();
            cancel();
        }
    }

    private void checkOnHit(){
        traceTargetArrow.getPickupStatus();
    }
}
