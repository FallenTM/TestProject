package me.fallenspring.reportplus;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	private Inventory inv = Bukkit.createInventory(null, 27, "Reports Inventory");
	
	public void onEnable() {
		Bukkit.getServer().getConsoleSender().sendMessage("[ReportPlus] has been enabled!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("report")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Sorry, but you cannot do that.");
				return true;
			}
			Player player = (Player) sender;
			if (args.length < 2) {
				player.sendMessage(ChatColor.RED + "Invalid Usage! /report <Player> <Reason>");
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				player.sendMessage(ChatColor.RED + "Sorry, but that player is invalid.");
				return true;
			}
			StringBuilder sb = new StringBuilder();
			
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String message = sb.toString().trim();
			for (Player admins : Bukkit.getOnlinePlayers()) {
				if (admins.hasPermission("Reportplus.allow")) {
					admins.sendMessage(ChatColor.RED + "-=[Reports Plus]");
					admins.sendMessage(ChatColor.RED + "Reported Player: " + target.getName());
					admins.sendMessage(ChatColor.RED + "Reported by: " + player.getName());
					admins.sendMessage(ChatColor.RED + "Reason " + message);
					admins.sendMessage(ChatColor.RED + "-=[Reports Plus]");
					ItemStack i = new ItemStack(Material.PAPER);
					ItemMeta meta = i.getItemMeta();
					meta.setDisplayName("Report, Click to Discard");
					meta.setLore(Arrays.asList(ChatColor.RED + "Player: " + target.getName() + " has been reported for: " + message));
					i.setItemMeta(meta);
					
					int amount = -1;
					if (inv.getSize() > amount) {
						amount++;
						inv.setItem(amount, i);
					}
					player.sendMessage(ChatColor.RED + "Report Submitted.");
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("reportgui")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Sorry, but you cannot do that.");
				return true;
			}
			Player player = (Player) sender;
			player.openInventory(inv);
		}
		return false;
	}
	
	@EventHandler
	public void on(InventoryClickEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(inv.getName())) {
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Report, Click to Discard")) {
				e.setCancelled(true);
				e.getCurrentItem().setType(Material.AIR);
				e.getWhoClicked().sendMessage("You cleared a report.");
			}
		}
	}
}
