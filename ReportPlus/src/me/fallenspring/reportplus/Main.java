package me.fallenspring.reportplus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		Bukkit.getServer().getConsoleSender().sendMessage("[ReportPlus] has been enabled!");
	}

}
