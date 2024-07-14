package com.alairack.zombie;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.SplittableRandom;


public class SpawnListener implements Listener {
    @EventHandler
    public void on_zombie_spawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            if (event.getEntity().getType() == EntityType.ZOMBIE) {

                if (event.getEntity().getVehicle() == null) {
                    SplittableRandom random = new SplittableRandom();
                    if (random.nextInt(1, 101) <= 3) {
                        Zombie zombie = (Zombie) event.getEntity();
                        if (zombie.isAdult()) {
                            Zombie smallZombie = (Zombie) event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.ZOMBIE);
                            smallZombie.setBaby();
                            event.getEntity().addPassenger(smallZombie);

                            if (random.nextInt(1, 101) <= 10) {
                                SkeletonHorse horse = (SkeletonHorse) event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.SKELETON_HORSE);
                                horse.addPassenger(zombie);
                                horse.setVisualFire(true);

                                if (zombie.getEquipment() != null && smallZombie.getEquipment() != null){
                                    zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                                    zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                                    zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));

                                    smallZombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                                    smallZombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void on_zombie_hurt(EntityDamageByEntityEvent event){
        if (event.getEntity().getType() == EntityType.ZOMBIE){
            if (!event.getEntity().isEmpty()){
                Zombie zombie = (Zombie) event.getEntity();
                if (!zombie.getPassengers().isEmpty()){
                    Entity passenger = zombie.getPassengers().get(0);
                    zombie.swingMainHand();
                    zombie.eject();

                    passenger.setVelocity(zombie.getEyeLocation().getDirection().normalize().multiply(1));

                }
            }
            if (event.getEntity().getVehicle() != null && event.getEntity().getVehicle().getType() == EntityType.ZOMBIE){
                Zombie vehicle = (Zombie) event.getEntity().getVehicle();
                vehicle.swingMainHand();
                vehicle.eject();
                event.getEntity().setVelocity(vehicle.getEyeLocation().getDirection().normalize().multiply(1));
            }
        }
    }
}
