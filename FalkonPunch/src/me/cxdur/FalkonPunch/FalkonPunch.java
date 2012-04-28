package me.cxdur.FalkonPunch;

import java.util.ArrayList;
import java.util.List;

import me.cxdur.FalkonPunch.listener.FalkonListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FalkonPunch extends JavaPlugin {

	public FalkonListener Listener = new FalkonListener(this);
	public List<String> falkonpunch = new ArrayList<String>();
	public List<String> falkonpunched = new ArrayList<String>();
	
	@Override
	public void onDisable() {
		System.out.println("Disabler FalkonPunch plugin av CXdur!");		// Fresh melding, liker ikke default
	}

	@Override
	public void onEnable() {
		System.out.println("Enabler FalkonPunch plugin av CXdur."); // fresh melding, liker ikke default 
		PluginManager pm = getServer().getPluginManager(); // Foretrekker pm. istedenfor getServer().getPluginManager()
		pm.registerEvents(Listener, this); // registrer events i listeneren
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("falkonpunch")) {
			Player p = (Player) sender;
			if(!falkonpunch.contains(sender.getName())) {	
				falkonpunch.add(sender.getName());
				sender.sendMessage(ChatColor.RED + "Du bruker nå FalkonPunch!");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "Du bruker ikke lenger FalkonPunch");
				falkonpunch.remove(sender.getName());
				return true;
			}
		}
		return false;
	}		}
