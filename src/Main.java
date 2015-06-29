import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener{
	private Spells spell;
	private static Plugin plu;
	public void onEnable(){
		plu = this;
		/**
		 * RECIPE
		 * */
		ItemStack magicwand = new ItemStack(Material.STICK);
		ItemMeta metamagicwand = magicwand.getItemMeta();
		metamagicwand.setDisplayName(ChatColor.YELLOW + "Spell Wand");
		metamagicwand.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by left-clicking with this wand.", ChatColor				.YELLOW + "Choose the next spell by right-clicking.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + 				"Blazing Wind"));
		magicwand.setItemMeta(metamagicwand);
		ShapedRecipe magicwandrecipe = new ShapedRecipe(magicwand);
		magicwandrecipe.shape("*^*", "!#!", "*&*");
		magicwandrecipe.setIngredient('*', Material.ARROW);
		magicwandrecipe.setIngredient('^', Material.SLIME_BALL);
		magicwandrecipe.setIngredient('!', Material.GOLD_NUGGET);
		magicwandrecipe.setIngredient('#', Material.STICK);
		magicwandrecipe.setIngredient('&', Material.BLAZE_POWDER);
		Bukkit.getServer().addRecipe(magicwandrecipe);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	public void onDisable(){
		//TODO
	}
	public static Plugin returnPlugin(){
		return plu;
	}
	/**
	 * Cooldown ArrayLists
	 * */
	private ArrayList<Player> incooldownsmoke = new ArrayList<Player>();
	private ArrayList<Player> incooldownwind = new ArrayList<Player>();
	private ArrayList<Player> incooldownheaven = new ArrayList<Player>();
	private ArrayList<Player> incooldownlife = new ArrayList<Player>();
	private ArrayList<Player> incooldownmud = new ArrayList<Player>();
	private ArrayList<Player> incooldownkrato = new ArrayList<Player>();
	private ArrayList<Player> incooldownvoid = new ArrayList<Player>();
	private ArrayList<Player> incooldownsnail = new ArrayList<Player>();
	private ArrayList<Player> incooldownearth = new ArrayList<Player>();
	public static ArrayList<Player> incooldownend = new ArrayList<Player>();
	public static HashMap<Player, Long> cooldowntime = new HashMap<Player, Long>();
	public static Long endstarttime;
	public static Long endendtime;
	HashMap<Player, Location> teleportmap = new HashMap<Player, Location>();
	/**
	 * SPELL CASTING
	 * */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		spell = new Spells();
		if(e.getPlayer().getItemInHand() != null){
			if(e.getPlayer().getItemInHand().getType().equals(Material.STICK)){
				if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.")){
					if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
						ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
						if(meta.getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Blazing Wind")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Heaven's Judgement"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Heaven's Judgement (ATTACK) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Heaven's Judgement")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Void Beam"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Void Beam (ATTACK) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Void Beam")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Battle Cry"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Battle Cry (ATTACK) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Battle Cry")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Snail's Curse"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Snail's Curse (DEFENSE) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Snail's Curse")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Mud Wall"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Mud Wall (DEFENSE) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Mud Wall")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Blinding Fog"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Blinding Fog (DEFENSE) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Blinding Fog")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Heavenly Life"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Heavenly Life (UTILITY/ATTACK) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Heavenly Life")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Enderman's Move"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Enderman's Move (UTILITY/DEFENSE) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Enderman's Move")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Kratos' Blessing"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Kratos' Blessing (UTILITY/ATTACK/DEFENSE) spell");
						}
						else if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Kratos' Blessing")){
							meta.getLore().clear();
							meta.setLore(Arrays.asList(ChatColor.YELLOW + "Cast spells by right-clicking with this wand.", ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Blazing Wind"));
							e.getPlayer().getItemInHand().setItemMeta(meta);
							e.getPlayer().sendMessage(ChatColor.GREEN + "You have selected the Blazing Wind (ATTACK) spell");
						}
						else{
							e.getPlayer().sendMessage(ChatColor.RED + "Error: no (default) spell selected");
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Blinding Fog")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownsmoke.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							spell.castSpell("Smokescreen", e, ull, null);
							incooldownsmoke.add(e.getPlayer());
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 2000));
							cooldowntime.put(e.getPlayer(), endtime);
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownsmoke.remove(e.getPlayer());
								}
							}, 40);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Blazing Wind")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownwind.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							spell.castSpell("FireJet", e, null, null);
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 7000));
							cooldowntime.put(e.getPlayer(), endtime);
							incooldownwind.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownwind.remove(e.getPlayer());
								}
							}, 140);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Heaven's Judgement")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownheaven.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 10000));
							cooldowntime.put(e.getPlayer(), endtime);
							spell.castSpell("HeavenStrike", e, null, null);
							incooldownheaven.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownheaven.remove(e.getPlayer());
								}
							}, 200);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Heavenly Life")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownlife.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 12000));
							cooldowntime.put(e.getPlayer(), endtime);
							spell.castSpell("Heal", e, null, null);
							incooldownlife.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownlife.remove(e.getPlayer());
								}
							}, 240);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Mud Wall")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownmud.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 15000));
							cooldowntime.put(e.getPlayer(), endtime);
							spell.castSpell("Mud Wall", e, null, null);
							incooldownmud.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownmud.remove(e.getPlayer());
								}
							}, 300);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Enderman's Move")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							spell.castSpell("Teleport", e, null, teleportmap);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Kratos' Blessing")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownkrato.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 20000));
							cooldowntime.put(e.getPlayer(), endtime);
							spell.castSpell("Kratos' Blessing", e, null, null);
							incooldownkrato.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownkrato.remove(e.getPlayer());
								}
							}, 400);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Void Beam")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownvoid.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 10000));
							cooldowntime.put(e.getPlayer(), endtime);
							spell.castSpell("Void Beam", e, null, null);
							incooldownvoid.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownvoid.remove(e.getPlayer());
								}
							}, 200);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Snail's Curse")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownsnail.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 9000));
							cooldowntime.put(e.getPlayer(), endtime);
							spell.castSpell("Snail's Curse", e, null, null);
							incooldownsnail.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownsnail.remove(e.getPlayer());
								}
							}, 180);
						}
					}
					if(e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Spell selected: " + ChatColor.GRAY + "Battle Cry")){
						if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							if(incooldownearth.contains(e.getPlayer())){
								if(cooldowntime.get(e.getPlayer()) != null){
									long currenttime = System.currentTimeMillis();
									currenttime = currenttime / 1000;
									long remainingtime = (cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
									if(remainingtime == 0){
										cooldowntime.remove(e.getPlayer());
									}
									if(remainingtime != 0){
										if(remainingtime > 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " seconds later!");
										}
										else if(remainingtime == 1){
											e.getPlayer().sendMessage(ChatColor.RED + "You cannot cast this spell until " + remainingtime + " second later!");
										}
									}	
								}
								return;
							}
							Long starttime = new Long(System.currentTimeMillis());
							Long endtime = new Long((starttime + 20000));
							cooldowntime.put(e.getPlayer(), endtime);
							spell.castSpell("Battle Cry", e, null, null);
							incooldownearth.add(e.getPlayer());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
								public void run(){
									incooldownearth.remove(e.getPlayer());
								}
							}, 400);
						}
					}
				}
			}
		}
	}
}
