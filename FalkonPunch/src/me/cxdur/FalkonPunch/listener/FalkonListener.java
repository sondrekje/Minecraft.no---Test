package me.cxdur.FalkonPunch.listener;

import java.util.HashMap;

import me.cxdur.FalkonPunch.FalkonPunch;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class FalkonListener implements Listener {
	
	public static HashMap<Player, ItemStack[]> inventories = new HashMap();
	
	private FalkonPunch plugin;

	public FalkonListener(final FalkonPunch plugin) { 
		this.plugin = plugin; // Slik at vi kan bruke ting fra mainclass + registrere events
	}

	@EventHandler(priority = EventPriority.HIGH) // Foretrekker å ha priority.
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent e) { // Når en entity tar damage

		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Entity p = e.getDamager(); // Sjekker hvem som har angrepet han.
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		if (plugin.falkonpunch.contains(((HumanEntity) p).getName())) { // Sjekker om han har aktivert FalkonPunch
			LivingEntity entity = (LivingEntity)e.getEntity(); // Må gjøre om til LivingEntity
			entity.setHealth(1); // Sette livet hans til 0
			entity.damage(1); // Deretter drepe han, slikt at det kommer i PlayerDeathEvent å jeg kan endre på det	
			plugin.falkonpunched.add(((HumanEntity)entity).getName());
		}
	}

	@EventHandler(priority = EventPriority.HIGH) // Foretrekker å ha priority.
	public void onEntityDeathEvent(final EntityDeathEvent e) { // Når en spiller dør
		if (!(e.getEntity() instanceof Player)) {
			return; 
		}
		Player p = (Player) e.getEntity(); // Spilleren som døde.
		if (plugin.falkonpunched.contains(p.getName())) { // Sjekker at spilleren som angrepte han har FalkonPunch aktivert
		PlayerDeathEvent pde = (PlayerDeathEvent) e;
		pde.setDeathMessage(p.getName() + " vart slått ut KO med"+ ChatColor.GRAY + " Falkon Punch" + ChatColor.WHITE + "!");		
		inventories.put(p, p.getInventory().getContents());
		e.setDroppedExp(0);
		e.getDrops().clear();
	}
		}
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerRespawnEvent(final PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (plugin.falkonpunched.contains(p.getName())) {
			Location spawn = p.getWorld().getSpawnLocation();
			p.teleport(spawn);
			p.getInventory().setContents((ItemStack[])inventories.get(p));
			plugin.falkonpunched.remove(p.getName());
		}
	}
 	}



