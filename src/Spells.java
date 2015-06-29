package plugin.sv.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Spells {
	@SuppressWarnings("deprecation")
	public void castSpell(String label, PlayerInteractEvent e, PlayerMoveEvent ev, HashMap<Player, Location> teleportmap){
		if(label == "Heal"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					if(e.getPlayer().getHealth() == 20){
						e.getPlayer().sendMessage(ChatColor.RED + "You are already at full health!");
						return;
					}
					else if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
					int totalhealth = 0;
					List<Entity> nearbyentities = e.getPlayer().getNearbyEntities(10.0, 10.0, 10.0);
					for(Entity entity : nearbyentities){
						if(entity != null){
							if(nearbyentities != null){
								if(entity instanceof LivingEntity){
									((LivingEntity) entity).damage(2);
									double health = e.getPlayer().getHealth();
									e.getPlayer().setHealth(health + 2);
									totalhealth = totalhealth + 2;
								}
							}
						}
					}
					double pi = Math.PI;
					double radian = 180 / pi;
					double px = e.getPlayer().getLocation().getX();
					double pz = e.getPlayer().getLocation().getZ();
					double y = e.getPlayer().getLocation().getY();
					for(double theta = 0; theta <= 2 * pi; theta += pi/1000){
						double x = px + Math.cos(theta * radian);
						double z = pz + Math.sin(theta * radian);
						e.getPlayer().getWorld().playEffect(new Location(e.getPlayer().getWorld(), x, y, z), Effect.HEART, 10, 1000);
						y += 0.05;
					}
					e.getPlayer().sendMessage(ChatColor.GREEN + "Your health was increased by " + totalhealth + " hearts!");
				}
			}
		}
		if(label == "FireJet"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					ArrayList<Block> blocksinlineofsight = (ArrayList<Block>) e.getPlayer().getLineOfSight((Set<Material>)null, 140);
					outerloop:
					for(Block block : blocksinlineofsight){
						if(block.getType().equals(Material.AIR)){
							block.getWorld().playEffect(block.getLocation(), Effect.GHAST_SHOOT, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.FLAME, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.LARGE_SMOKE, 10, 1000);
							Silverfish silverfish = (Silverfish) block.getWorld().spawnEntity(block.getLocation(), EntityType.SILVERFISH);
							silverfish.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000, 100));
							List<Entity> entitylist = silverfish.getNearbyEntities(1.0, 1.0, 1.0);
							silverfish.teleport(new Location(silverfish.getWorld(), 0, 0, 0));
							innerloop:
							for(Entity entity : entitylist){
								if(!(entity.getLocation().equals(e.getPlayer().getLocation())) && entitylist != null){
									if(entity instanceof LivingEntity){
										if(!(entity instanceof Silverfish)){
											block.getWorld().createExplosion(entity.getLocation(), 1.0f);
											break outerloop;
										}
										else{
											continue;
										}
									}
									else{
										continue;
									}
								}
								else{
									break innerloop;
								}
							}
						}
						else if(block.getType().equals(Material.STATIONARY_WATER) || block.getType().equals(Material.WATER)){
							block.getWorld().playEffect(block.getLocation(), Effect.GHAST_SHOOT, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.FLAME, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.LARGE_SMOKE, 10, 1000);
							Silverfish silverfish = (Silverfish) block.getWorld().spawnEntity(block.getLocation(), EntityType.SILVERFISH);
							silverfish.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000, 100));
							List<Entity> entitylist = silverfish.getNearbyEntities(1.0, 1.0, 1.0);
							silverfish.teleport(new Location(silverfish.getWorld(), 0, 0, 0));
							innerloop:
							for(Entity entity : entitylist){
								if(!(entity.getLocation().equals(e.getPlayer().getLocation())) && entitylist != null){
									if(entity instanceof LivingEntity){
										block.getWorld().createExplosion(entity.getLocation(), 0.5f);
										silverfish.damage(10000);
										break outerloop;
									}
									else{
										continue;
									}
								}
								else{
									break innerloop;
								}
							}
						}
						else{
							block.getWorld().createExplosion(block.getLocation(), 1.0f);
							block.getWorld().playEffect(block.getLocation(), Effect.LARGE_SMOKE, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.FLAME, 10, 1000);
							break outerloop;
						}
					}
				}
			}
		}
		if(label == "Smokescreen"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					List<Entity> livingentitynearby = e.getPlayer().getNearbyEntities(10.0, 10.0, 10.0);
					for(Entity entitynearby : livingentitynearby){
						if(livingentitynearby != null){
							if(entitynearby instanceof LivingEntity){
								((LivingEntity) entitynearby).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 5));
								int taskid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.returnPlugin(), new Runnable(){
									public void run(){
										entitynearby.getWorld().playEffect(entitynearby.getLocation(), Effect.EXPLOSION_LARGE, 10, 1000);
										entitynearby.getWorld().playEffect(entitynearby.getLocation(), Effect.EXPLOSION_HUGE, 10, 1000);
										entitynearby.getWorld().playEffect(entitynearby.getLocation(), Effect.EXPLOSION, 10, 1000);										
									}
								}, 0L, 0L);
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.returnPlugin(), new Runnable(){
									public void run(){
										Bukkit.getServer().getScheduler().cancelTask(taskid);
									}
								}, 100L);
								e.getPlayer().getWorld().playSound(entitynearby.getLocation(), Sound.GHAST_FIREBALL, 16, 1);
								if(entitynearby instanceof Player){
									((Player)entitynearby).sendMessage(ChatColor.DARK_RED + e.getPlayer().getName() + ChatColor.RED + "obscured your vision!");
								}
							}
							else{
								break;
							}
						}
					}
				}
			}
		}
		if(label == "HeavenStrike"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					List<Entity> nearbyentities = e.getPlayer().getNearbyEntities(10.0, 10.0, 10.0);
					for(int i = 50; i >= 0; i--){
						for(Entity entity : nearbyentities){
							if(entity instanceof LivingEntity){
								if(!(entity.getLocation().equals(e.getPlayer().getLocation()))){
									Location entitystrike = new Location(entity.getWorld(), entity.getLocation().getX(), (entity.getLocation().getY() + i), entity.getLocation().getZ());
									double pi = Math.PI;
									double radian = 180/pi;
									for(double theta = 0; theta <= (2 * pi); theta += (pi/100)){
										double x = entitystrike.getX() + Math.cos(theta * radian) * 0.5;
										double y = entitystrike.getY();
										double z = entitystrike.getZ() + Math.sin(theta * radian) * 0.5;
										entitystrike.getWorld().playEffect(new Location(entitystrike.getWorld(), x, y, z), Effect.LAVADRIP, 1, 1000);
									}
									if(i == 0){
										((LivingEntity) entity).damage(4);
										entity.getWorld().getBlockAt(entity.getLocation()).setType(Material.FIRE);
										if(entity instanceof Player){
											((Player)entity).sendMessage(ChatColor.DARK_RED + e.getPlayer().getName() + ChatColor.RED + "struck you with a bolt of fire!");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(label == "Void Beam"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					ArrayList<Block> blocksinlineofsight = (ArrayList<Block>) e.getPlayer().getLineOfSight((Set<Material>)null, 70);
					outerloop:
					for(Block block : blocksinlineofsight){
						e.getPlayer().getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);
						if(block.getType().equals(Material.AIR)){
							block.getWorld().playEffect(block.getLocation(), Effect.VOID_FOG, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.LARGE_SMOKE, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 10, 1000);
							Silverfish silverfish = (Silverfish) block.getWorld().spawnEntity(block.getLocation(), EntityType.SILVERFISH);
							silverfish.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000, 1000));
							List<Entity> entitylist = silverfish.getNearbyEntities(1.0, 1.0, 1.0);
							silverfish.teleport(new Location(silverfish.getWorld(), 0, 0, 0));
							innerloop:
							for(Entity entity : entitylist){
								if(!(entity.getLocation().equals(e.getPlayer().getLocation())) && entitylist != null){
									if(entity instanceof LivingEntity){
										if(!(entity instanceof Silverfish)){
											((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 6));
											((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 90, 1));
											if(entity instanceof Player){
												((Player)entity).sendMessage(ChatColor.DARK_RED + e.getPlayer().getName() + "shot you with a Void Beam!");
											}
											break outerloop;
										}
										else{
											continue;
										}
									}
									else{
										continue;
									}
								}
								else{
									break innerloop;
								}
							}
						}
						else{
							block.getWorld().playEffect(block.getLocation(), Effect.VOID_FOG, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.LARGE_SMOKE, 10, 1000);
							block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 10, 1000);
						}
					}
					for(Block block : blocksinlineofsight){
						if(block != null){
							if(blocksinlineofsight != null){
								e.getPlayer().getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);
							}
						}
					}
				}
			}
		}
		if(label == "Battle Cry"){
			if(e != null){
				if(e.getPlayer() != null){
					for(Entity ent : e.getPlayer().getNearbyEntities(8, 8, 8)){
						if(ent != null){
							if(e.getPlayer().getNearbyEntities(8, 8, 8) != null){
								if(ent instanceof LivingEntity){
									LivingEntity lent = (LivingEntity) ent;
									lent.damage(10);
									lent.setVelocity(new Vector(0, 1, 0));
								}
							}
						}
					}
					double pi = Math.PI;
					double radian = 180/pi;
					for(double theta = 0; theta <= 2 * pi; theta += pi/60){
						double x = e.getPlayer().getLocation().getX() + Math.cos(theta * radian) * 8;
						double z = e.getPlayer().getLocation().getZ() + Math.sin(theta * radian) * 8;
						if(e.getPlayer().getWorld().getBlockAt(new Location(e.getPlayer().getWorld(), x, e.getPlayer().getLocation().getY() - 1, z)).getType().equals(Material.AIR)){
							continue;
						}
						else{
							e.getPlayer().getWorld().getBlockAt(new Location(e.getPlayer().getWorld(), x, e.getPlayer().getLocation().getY() - 1, z)).breakNaturally(new ItemStack(Material.AIR));
							e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.DIG_GRASS, 10, 1);
						}
						e.getPlayer().getWorld().createExplosion(e.getPlayer().getWorld().getBlockAt(new Location(e.getPlayer().getWorld(), x, e.getPlayer().getLocation().getY() - 1, z)).getLocation(), 0.01f, false);
					}
					e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
				}
			}
		}
		if(label == "Snail's Curse"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					List<Entity> entitylist = e.getPlayer().getNearbyEntities(5.0, 5.0, 5.0);
					for(Entity entity : entitylist){
						if(entity != null){
							if(entitylist != null){
								if(entity instanceof LivingEntity){
									((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 4));
									if(entity instanceof Player){
										((Player)entity).sendMessage(ChatColor.DARK_RED + e.getPlayer().getName() + "has slowed your movements down!");
									}
								}
							}
						}
					}
				}
			}
		}
		if(label == "Teleport"){
			if(e !=  null && ev == null){
				if(e.getPlayer() != null){
					if(teleportmap.get(e.getPlayer()) != null){
						Main.endstarttime = new Long(System.currentTimeMillis());
						Main.endendtime = new Long((Main.endstarttime + 5000));
						//DISAPPEAR
						double pi = Math.PI;
						double radian = 180/pi;
						int taskid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.returnPlugin(), new Runnable(){
							double px = e.getPlayer().getLocation().getX();
							double py = e.getPlayer().getLocation().getY();
							double pz = e.getPlayer().getLocation().getZ();
							double theta = 0;
							double rincrease = 1;
							public void run(){
								for(double theta = 0; theta <= (2 * pi); theta += pi/100){
									double x = px + Math.cos(theta * radian) * rincrease;
									double z = pz + Math.sin(theta * radian) * rincrease;
									e.getPlayer().getWorld().playEffect(new Location(e.getPlayer().getWorld(), x, py, z), Effect.PORTAL, 10, 1000);
								}
								rincrease += 0.2;
							}
						}, 0L, 0L);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.returnPlugin(), new Runnable(){
							public void run(){
								Bukkit.getServer().getScheduler().cancelTask(taskid);
							}
						}, 10);
						e.getPlayer().teleport(teleportmap.get(e.getPlayer()));
						teleportmap.remove(e.getPlayer());
						Main.cooldowntime.put(e.getPlayer(), Main.endendtime);
						e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "You have arrived at your destination");
						//APPEAR
						int taskid2 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.returnPlugin(), new Runnable(){
							double px = e.getPlayer().getLocation().getX();
							double py = e.getPlayer().getLocation().getY();
							double pz = e.getPlayer().getLocation().getZ();
							double theta = 0;
							double rincrease = 1;
							public void run(){
								for(double theta = 0; theta <= (2 * pi); theta += pi/100){
									double x = px + Math.cos(theta * radian) * rincrease;
									double z = pz + Math.sin(theta * radian) * rincrease;
									e.getPlayer().getWorld().playEffect(new Location(e.getPlayer().getWorld(), x, py, z), Effect.PORTAL, 10, 1000);
								}
								rincrease += 0.2;
							}
						}, 0L, 0L);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.returnPlugin(), new Runnable(){
							public void run(){
								Bukkit.getServer().getScheduler().cancelTask(taskid2);
							}
						}, 10);
						e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
						teleportmap.remove(e.getPlayer());
					}
					else{
					if(Main.incooldownend.contains(e.getPlayer())){
						if(Main.cooldowntime.get(e.getPlayer()) != null){
							long currenttime = System.currentTimeMillis();
							currenttime = currenttime / 1000;
							long remainingtime = (Main.cooldowntime.get(e.getPlayer()) / 1000) - currenttime;
							if(remainingtime == 0){
								Main.cooldowntime.remove(e.getPlayer());
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
						teleportmap.put(e.getPlayer(), e.getPlayer().getLocation());
						e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Location saved. You can now teleport to this location when the Enderman's Move spell is casted again");
						Main.incooldownend.add(e.getPlayer());
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.returnPlugin(), new Runnable(){
							public void run(){
								Main.incooldownend.remove(e.getPlayer());
							}
						}, 100);
					}	
				}
			}
		}
		if(label == "Mud Wall"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					ArrayList<Block> wallblocklist = new ArrayList<Block>();
					int taskid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.returnPlugin(), new Runnable(){
						double pi = Math.PI;
						double radian = 180/pi;
						double y = e.getPlayer().getLocation().getY();
						double count = 1;
						public void run(){
							for(double theta = 0; theta <= 2*pi; theta += pi/200){
								double x = e.getPlayer().getLocation().getX() + Math.cos(theta * radian) * 3;
								double z = e.getPlayer().getLocation().getZ() + Math.sin(theta * radian) * 3;
								Block mudblock = e.getPlayer().getWorld().getBlockAt(new Location(e.getPlayer().getWorld(), x, y, z));
								wallblocklist.add(mudblock);
								mudblock.setType(Material.DIRT);
							}
							y += 1;
						}
					}, 0L, 1L);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.returnPlugin(), new Runnable(){
						public void run(){
							Bukkit.getServer().getScheduler().cancelTask(taskid);
						}
					}, 5L);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.returnPlugin(), new Runnable(){
						public void run(){
							for(Block block : wallblocklist){
								if(wallblocklist != null){
									if(block != null){
										block.getWorld().playEffect(block.getLocation(), Effect.TILE_BREAK, 10, 1000);
										block.setType(Material.AIR);
									}
								}
							}
						}
					}, 120);
					e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.DIG_GRASS, 1, 1);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.returnPlugin(), new Runnable(){
						public void run(){
							e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.DIG_GRASS, 1, 1);
						}
					}, 120);
					e.getPlayer().sendMessage(ChatColor.GREEN + "You have erected a dirt wall to protect you!");
				}
			}
		}
		if(label == "Kratos' Blessing"){
			if(e != null && ev == null){
				if(e.getPlayer() != null){
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 2));
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 2));
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 2));
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 2));
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 2));
					if(e.getPlayer().getHealth() <= 10){
						e.getPlayer().sendMessage(ChatColor.RED + "You do not have enough health!");
						return;
					}
					else{
						e.getPlayer().sendMessage(ChatColor.GREEN + "You have granted yourself superior strength and agility at a cost of " + ChatColor.RED + "5 hearts" + ChatColor.GREEN + "!");
					}
					e.getPlayer().damage(10);
				}
			}
		}
	}
}
