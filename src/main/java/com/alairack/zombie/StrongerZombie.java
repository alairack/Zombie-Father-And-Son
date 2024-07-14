package com.alairack.zombie;

import org.bukkit.plugin.java.JavaPlugin;

public class StrongerZombie extends JavaPlugin {

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
        getLogger().info("已开启伽刚特尔 by 保卫");

    }
    @Override
    public void onDisable(){
        getLogger().info("已关闭伽刚特尔");
    }


}
