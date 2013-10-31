
package me.sniperzciinema.cranked;

import java.util.List;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.ArenaHandlers.GameState;
import me.sniperzciinema.cranked.Extras.Menus;
import me.sniperzciinema.cranked.GameMechanics.Agility;
import me.sniperzciinema.cranked.GameMechanics.DeathTypes;
import me.sniperzciinema.cranked.GameMechanics.Deaths;
import me.sniperzciinema.cranked.GameMechanics.Stats;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.Messages.StringUtil;
import me.sniperzciinema.cranked.Messages.Time;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;
import me.sniperzciinema.cranked.Tools.Files;
import me.sniperzciinema.cranked.Tools.Handlers.LocationHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	Main plugin;

	public Commands(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Cranked"))
		{
			Player p = null;
			CPlayer cp = null;
			if (sender instanceof Player)
			{
				p = (Player) sender;
				cp = CPlayerManager.getCrankedPlayer(p);
			}

			if (args.length > 0 && args[0].equalsIgnoreCase("TEST"))
			{
			}
			// //////////////////////////////JOIN///////////////////////////////////
			if (args.length > 0 && args[0].equalsIgnoreCase("JOIN"))
			{
				if (p == null)
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!sender.hasPermission("Cranked.Join") && !sender.hasPermission("Cranked.Join." + args[1]))
				{
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (cp.getArena() != null)
				{
					sender.sendMessage(Msgs.Error_Already_In_A_Game.getString());
					return true;
				} else if (args.length >= 2)
				{
					String arenaName = args[1];
					if (ArenaManager.arenaRegistered(arenaName))
					{
						if (ArenaManager.isArenaValid(arenaName))
						{
							Game.join(cp, ArenaManager.getArena(StringUtil.getWord(arenaName)));
							Arena arena = cp.getArena();
							// Info the players of their current situation
							p.sendMessage(Msgs.Format_Line.getString());
							p.sendMessage("");
							p.sendMessage(Msgs.Game_You_Joined_A_Game.getString("<arena>", cp.getArena().getName()));
							p.sendMessage(Msgs.Arena_Creator.getString("<creator>", arena.getCreator()));
							p.sendMessage("");
							p.sendMessage("");
							if (arena.getState() == GameState.Waiting)
							{
								p.sendMessage(Msgs.Game_StatusUpdate.getString("<current>", String.valueOf(arena.getPlayers().size()), "<needed>", String.valueOf(arena.getSettings().getRequiredPlayers())));
							} else if (arena.getState() == GameState.PreGame)
							{
								p.sendMessage(Msgs.Game_Starting.getString("<time>", Time.getTime((long) arena.getTimer().getTimeLeft())));
							} else if (arena.getState() == GameState.Started)
							{
								p.sendMessage(Msgs.Game_Time_Left.getString("<time>", Time.getTime((long) arena.getTimer().getTimeLeft())));
							}
							p.sendMessage("");
							p.sendMessage(Msgs.Format_Line.getString());
							for (Player ppl : cp.getArena().getPlayers())
								if (ppl != cp.getPlayer())
									ppl.sendMessage(Msgs.Game_They_Joined_A_Game.getString("<player>", p.getName(), "<arena>", cp.getArena().getName()));

							Agility.speedUp(p, false);
						} else
						{
							sender.sendMessage(Msgs.Error_Missing_Spawns.getString("<arena>", arenaName));
						}
					} else
					{
						sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", arenaName));
					}
				} else
				{
					Menus.chooseArena(p);
				}
			}
			// //////////////////////////////LEAVE///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("LEAVE"))
			{
				if (p == null)
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!p.hasPermission("Cranked.Join"))
				{
					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (cp.getArena() == null)
				{
					p.sendMessage(Msgs.Error_Not_In_A_Game.getString());
					return true;
				} else
				{
					Arena arena = cp.getArena();
					Game.leave(cp);

					// Tell the player they left
					p.sendMessage(Msgs.Format_Line.getString());
					p.sendMessage("");
					p.sendMessage(Msgs.Game_You_Left_A_Game.getString("<arena>", arena.getName()));
					p.sendMessage("");
					p.sendMessage(Msgs.Format_Line.getString());
					// Update the other players on the situation
					for (Player ppl : arena.getPlayers())
					{
						CPlayerManager.getCrankedPlayer(ppl).getScoreBoard().showStats();
						ppl.sendMessage(Msgs.Game_They_Left_A_Game.getString("<player>", cp.getName(), "<arena>", arena.getName()));
					}

					Agility.resetSpeed(p);
				}
			}

			// //////////////////////////////CREATE///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("CREATE"))
			{
				
				if (p == null)
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!sender.hasPermission("Cranked.SetUp"))
				{

					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (args.length >= 2)
				{
					String arena = args[1];
					if (!ArenaManager.arenaRegistered(arena))
					{

						ArenaManager.createArena(args[1]);
						p.sendMessage(Msgs.Format_Line.getString());
						p.sendMessage(Msgs.Arena_Created.getString("<arena>", arena));
						p.sendMessage(Msgs.Arena_How_To_Set_More_Spawns.getString());
						cp.setCreating(arena);
						if (args.length == 3)
							ArenaManager.getArena(StringUtil.getWord(arena)).setCreator(args[2]);
						else
							ArenaManager.getArena(StringUtil.getWord(arena)).setCreator("Unkown");
						p.sendMessage(Msgs.Format_Line.getString());

					} else
					{

						p.sendMessage(Msgs.Error_Already_An_Arena.getString("<arena>", arena));
					}
				} else
				{

					p.sendMessage(Msgs.Commands_How_To_Create.getString());
				}

			}

			// //////////////////////////////REMOVE///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("REMOVE"))
			{
				if (!sender.hasPermission("Cranked.SetUp"))
				{

					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (args.length >= 2)
				{
					String arena = args[1];
					if (ArenaManager.arenaRegistered(arena))
					{
						ArenaManager.removeArena(args[1]);

						sender.sendMessage(Msgs.Arena_Removed.getString("<arena>", arena));
					} else
					{
						sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", arena));
					}
				} else
				{
					sender.sendMessage(Msgs.Commands_How_To_Remove.getString());
				}

			}
			// //////////////////////////////ARENAS///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("ARENAS"))
			{
				if (!sender.hasPermission("Cranked.List"))
				{
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else
				{
					sender.sendMessage(Msgs.Format_Header.getString("<title>", "Arenas"));
					sender.sendMessage(Msgs.Arena_List_Arenas.getString("<validarenas>", ArenaManager.getPossibleArenas(), "<notvalidarenas>", ArenaManager.getNotPossibleArenas()));
					sender.sendMessage(Msgs.Format_Line.getString());
				}
			}
			// //////////////////////////////SETSPAWN///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("SETSPAWN"))
			{
				if (p == null)
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!p.hasPermission("Cranked.Setup"))
				{
					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else
				{
					String arena = cp.getCreating();
					if (ArenaManager.arenaRegistered(arena))
					{
						ArenaManager.setSpawn(arena, p.getLocation());
						p.sendMessage(Msgs.Arena_Spawn_Set.getString("<spawn>", String.valueOf(ArenaManager.getArena(StringUtil.getWord(arena)).getSpawns().size())));
					} else
					{
						p.sendMessage(Msgs.Commands_How_To_Set_Spawn.getString());
					}
				}
			}
			// //////////////////////////////SETSPAWN///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("SETARENA"))
			{
				if (p == null)
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!p.hasPermission("Cranked.Setup"))
				{
					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else
				{
					if (args.length == 2)
					{
						String arenaName = StringUtil.getWord(args[1]);
						if (ArenaManager.arenaRegistered(args[1]))
						{
							cp.setCreating(arenaName);
							p.sendMessage(Msgs.Arena_Arena_Is_Set.getString("<arena>", arenaName));
						} else
						{
							sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", arenaName));
						}
					} else
					{
						p.sendMessage(Msgs.Commands_How_To_Set_Arena.getString());
					}
				}
			}
			// //////////////////////////////ADMIN///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("ADMIN"))
			{
				if (!sender.hasPermission("Cranked.Admin"))
				{
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else
				{
					if (args.length == 2)
					{

					}
				}
			}

			// //////////////////////////////////////
			// INFO
			else if (args.length > 0 && args[0].equalsIgnoreCase("Info"))
			{

				if (!sender.hasPermission("Cranked.Info"))
				{
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				}
				if (args.length == 2)
				{
					if (ArenaManager.arenaRegistered(args[1]))
					{
						Arena arena = ArenaManager.getArena(args[1]);
						sender.sendMessage("");
						sender.sendMessage(Msgs.Format_Header.getString("<title>", arena.getName() + " Information"));
						sender.sendMessage(Msgs.Info_Players_In.getString("<current>", String.valueOf(arena.getPlayers().size()), "<max>", String.valueOf(arena.getSettings().getMaxPlayers())));
						sender.sendMessage(Msgs.Info_Required_Players_To_Start.getString("<needed>", String.valueOf(arena.getSettings().getRequiredPlayers())));
						sender.sendMessage(Msgs.Info_Game_State.getString("<state>", String.valueOf(arena.getState())));
						sender.sendMessage(Msgs.Info_Time_Left.getString("<time>", String.valueOf(arena.getState() == GameState.Started ? Time.getTime((long) arena.getTimer().getTimeLeft()) : "N/A")));
						sender.sendMessage(Msgs.Info_Time_Limit.getString("<time>", String.valueOf(arena.getSettings().getGameTime())));
						sender.sendMessage(Msgs.Info_Creator.getString("<creator>", String.valueOf(arena.getCreator())));
					} else
					{
						sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", args[1]));
					}
				} else
				{
					sender.sendMessage(Msgs.Commands_How_To_Info.getString());
				}

			}

			// /////////////////////////////////////////////////////////////
			// SUICIDE
			else if (args.length > 0 && args[0].equalsIgnoreCase("Suicide"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				} else if (!sender.hasPermission("Cranked.Suicide"))
				{
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				}
				if (cp.getArena() != null)
				{
					Deaths.playerDies(null, cp.getPlayer(), DeathTypes.Other);
				} else
				{
					p.sendMessage(Msgs.Error_Not_In_A_Game.getString());
				}
			}
			// /////////////////////////////////////////////////
			// HELP
			else if (args.length > 0 && args[0].equalsIgnoreCase("Help"))
			{
				if (!sender.hasPermission("Cranked.Join"))
				{
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else
				{
					sender.sendMessage("");
					sender.sendMessage(Msgs.Format_Header.getString("<title>", "Cranked Help"));
					sender.sendMessage(Msgs.Commands_How_To_Join.getString());
					sender.sendMessage(Msgs.Commands_How_To_Leave.getString());
					if (sender.hasPermission("Cranked.Suicide"))
						sender.sendMessage(Msgs.Commands_How_To_Suicide.getString());
					if (sender.hasPermission("Cranked.Info"))
						sender.sendMessage(Msgs.Commands_How_To_Info.getString());
					if (sender.hasPermission("Cranked.Arenas"))
						sender.sendMessage(Msgs.Commands_How_To_Arenas.getString());
					if (sender.hasPermission("Cranked.SetUp"))
					{
						sender.sendMessage(Msgs.Commands_How_To_Set_Arena.getString());
						sender.sendMessage(Msgs.Commands_How_To_Set_Spawn.getString());
						sender.sendMessage(Msgs.Commands_How_To_Show_Arena_Spawns.getString());
						sender.sendMessage(Msgs.Commands_How_To_Tp_To_Arena_Spawns.getString());
						sender.sendMessage(Msgs.Commands_How_To_Delete_Arena_Spawns.getString());
						sender.sendMessage(Msgs.Commands_How_To_Create.getString());
						sender.sendMessage(Msgs.Commands_How_To_Remove.getString());
					}
					if (sender.hasPermission("Cranked.Admin"))
						sender.sendMessage(Msgs.Commands_How_To_Admin.getString());
				}
			}
			// TODO: Admin
			/*
			 * // /////////////////////////////////////////////////////////////
			 * // ADMIN else if (args.length > 0 &&
			 * args[0].equalsIgnoreCase("Admin")) { CommandSender player =
			 * sender; if (!player.hasPermission("Cranked.Admin")) {
			 * player.sendMessage(Messages.sendMessage(Msgs.ERROR_NOPERMISSION,
			 * null, null)); return true; }
			 * 
			 * if (args.length == 2) { if (args[1].equalsIgnoreCase("Shutdown"))
			 * { if (Cranked.getGameState() != GameState.DISABLED) {
			 * Cranked.setGameState(GameState.DISABLED);
			 * player.sendMessage(plugin.I + ChatColor.GRAY +
			 * "Joining Cranked has been disabled."); } else {
			 * Cranked.setGameState(GameState.INLOBBY);
			 * player.sendMessage(plugin.I + ChatColor.GRAY +
			 * "Joining Cranked has been enabled."); } } else if
			 * (args[1].equalsIgnoreCase("Reload")) {
			 * System.out.println("===== Cranked =====");
			 * Cranked.filesReloadAll(); plugin.addon.getAddons();
			 * System.out.println("====================");
			 * 
			 * player.sendMessage(plugin.I +
			 * "Crankeds Files have been reloaded"); } else if
			 * (args[1].equalsIgnoreCase("Code")) { player.sendMessage(plugin.I
			 * + "Code: " + ChatColor.WHITE +
			 * ItemHandler.getItemStackToString(((Player)
			 * sender).getItemInHand())); player.sendMessage(plugin.I +
			 * "This code has also been sent to your console to allow for copy and paste!"
			 * ); System.out.println(ItemHandler.getItemStackToString(((Player)
			 * sender).getItemInHand())); } else { player.sendMessage(plugin.I +
			 * ChatColor.RED + "Unknown Admin Command, Type /Cranked Admin");
			 * return true; } } else if (args.length == 3) { if
			 * (args[1].equalsIgnoreCase("Kick")) { Player user =
			 * Bukkit.getPlayer(args[2]); if (user == null ||
			 * !Cranked.isPlayerInGame(user) || !Cranked.isPlayerInLobby(user))
			 * { player.sendMessage(plugin.I + ChatColor.RED +
			 * "The player must be playing Cranked"); } else {
			 * user.performCommand("Cranked Leave");
			 * user.sendMessage(Messages.sendMessage(Msgs.ADMIN_YOUAREKICKED,
			 * null, null)); player.sendMessage(plugin.I + "You have kicked " +
			 * user.getName() + " from Cranked"); } } else if
			 * (args[1].equalsIgnoreCase("Reset")) { String name = args[2];
			 * Cranked.filesGetPlayers().set("Players." + name.toLowerCase(),
			 * null); player.sendMessage(plugin.I + name + "'s now reset!"); } }
			 * else if (args.length == 4) { String user = args[2];
			 * 
			 * int i = Integer.parseInt(args[3]); if
			 * (args[1].equalsIgnoreCase("Points")) {
			 * Cranked.playerSetPoints(user, Cranked.playerGetPoints(user) + i,
			 * 0); player.sendMessage(plugin.I + user + "'s new points is: " +
			 * Cranked.playerGetPoints(user)); } else if
			 * (args[1].equalsIgnoreCase("Score")) {
			 * Cranked.playerSetScore(user, Cranked.playerGetScore(user) + i);
			 * player.sendMessage(plugin.I + user + "'s new score is: " +
			 * Cranked.playerGetScore(user)); } else if
			 * (args[1].equalsIgnoreCase("kStats")) {
			 * Cranked.playerSetKills(user, Cranked.playerGetKills(user) + i);
			 * player.sendMessage(plugin.I + user + "'s new kill count is: " +
			 * Cranked.playerGetKills(user)); } else if
			 * (args[1].equalsIgnoreCase("DStats")) {
			 * Cranked.playerSetDeaths(user, Cranked.playerGetDeaths(user) + i);
			 * player.sendMessage(plugin.I + user + "'s new death count is: " +
			 * Cranked.playerGetDeaths(user)); } else {
			 * player.sendMessage(plugin.I + ChatColor.RED +
			 * "Thats an invalid command"); } } else {
			 * player.sendMessage(plugin.I + ChatColor.GREEN +
			 * ChatColor.STRIKETHROUGH + ChatColor.BOLD + "======" +
			 * ChatColor.GOLD + " Admin Menu " + ChatColor.GREEN +
			 * ChatColor.STRIKETHROUGH + ChatColor.BOLD + "======");
			 * player.sendMessage(plugin.I + ChatColor.AQUA +
			 * "/Inf Admin Points <Player> <#>"); player.sendMessage(plugin.I +
			 * ChatColor.RED + "-> " + ChatColor.WHITE + ChatColor.ITALIC
			 * +"Add points to a player(Also goes negative)");
			 * player.sendMessage(plugin.I + ChatColor.BLUE +
			 * "/Inf Admin Score <Player> <#>"); player.sendMessage(plugin.I +
			 * ChatColor.RED + "-> " + ChatColor.WHITE + ChatColor.ITALIC
			 * +"Add score to a player(Also goes negative)");
			 * player.sendMessage(plugin.I + ChatColor.DARK_AQUA +
			 * "/Inf Admin KStats <Player> <#>"); player.sendMessage(plugin.I +
			 * ChatColor.RED + "-> " + ChatColor.WHITE + ChatColor.ITALIC
			 * +"Add kills to a player(Also goes negative)");
			 * player.sendMessage(plugin.I + ChatColor.DARK_BLUE +
			 * "/Inf Admin DStats <Player> <#>"); player.sendMessage(plugin.I +
			 * ChatColor.RED + "-> " + ChatColor.WHITE + ChatColor.ITALIC
			 * +"Add deaths to a player(Also goes negative)");
			 * player.sendMessage(plugin.I + ChatColor.DARK_GRAY +
			 * "/Inf Admin Kick <Player>"); player.sendMessage(plugin.I +
			 * ChatColor.RED + "-> " + ChatColor.WHITE + ChatColor.ITALIC
			 * +"Kick a player out of Cranked"); player.sendMessage(plugin.I +
			 * ChatColor.DARK_GREEN + "/Inf Admin Reset <Player>");
			 * player.sendMessage(plugin.I + ChatColor.RED + "-> " +
			 * ChatColor.WHITE + ChatColor.ITALIC +"Reset a player's stats");
			 * player.sendMessage(plugin.I + ChatColor.DARK_PURPLE +
			 * "/Inf Admin Shutdown"); player.sendMessage(plugin.I +
			 * ChatColor.RED + "-> " + ChatColor.WHITE + ChatColor.ITALIC
			 * +"Prevent joining Cranked"); player.sendMessage(plugin.I +
			 * ChatColor.DARK_RED + "/Inf Admin Reload");
			 * player.sendMessage(plugin.I + ChatColor.RED + "-> " +
			 * ChatColor.WHITE + ChatColor.ITALIC +"Reload the config");
			 * player.sendMessage(plugin.I + ChatColor.GOLD +
			 * "/Inf Admin Code"); player.sendMessage(plugin.I + ChatColor.RED +
			 * "-> " + ChatColor.WHITE + ChatColor.ITALIC
			 * +"See Cranked's item code for the item in hand"); } }
			 */
			// ////////////////////////////////////////////
			// STATS
			else if (args.length > 0 && args[0].equalsIgnoreCase("Stats"))
			{

				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				Player player = (Player) sender;
				if (!player.hasPermission("Cranked.Stats"))
				{
					player.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				}
				if (args.length != 1)
				{
					if (!player.hasPermission("Cranked.Stats.Other"))
					{
						player.sendMessage(Msgs.Error_No_Permission.getString());
						return true;
					}

					String user = args[1].toLowerCase();
					player.sendMessage("");
					player.sendMessage(Msgs.Format_Header.getString("<title>", user));
					player.sendMessage(Msgs.Stats_Score.getString("<value>", String.valueOf(Stats.getScore(user))));
					player.sendMessage(Msgs.Stats_Score.getString("<value>", String.valueOf(Stats.getKills(user))));
					player.sendMessage(Msgs.Stats_Score.getString("<value>", String.valueOf(Stats.getDeaths(user))));

				} else
				{
					String user = player.getName().toLowerCase();

					player.sendMessage("");
					player.sendMessage(Msgs.Format_Header.getString("<title>", user));
					player.sendMessage(Msgs.Stats_Score.getString("<value>", String.valueOf(Stats.getScore(user))));
					player.sendMessage(Msgs.Stats_Score.getString("<value>", String.valueOf(Stats.getKills(user))));
					player.sendMessage(Msgs.Stats_Score.getString("<value>", String.valueOf(Stats.getDeaths(user))));

				}
			}

			// ////////////////////////////////////////////////
			// TPSPAWN
			else if (args.length > 0 && args[0].equalsIgnoreCase("TpSpawn"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!p.hasPermission("Cranked.SetUp"))
				{
					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				}
				if (cp.getCreating() == null)
				{
					p.sendMessage(Msgs.Commands_How_To_Show_Arena_Spawns.getString());
					return true;
				}
				if (args.length != 1)
				{
					try
					{
						int spawn = Integer.valueOf(args[1]);
						List<String> list = ArenaManager.getArena(cp.getCreating()).getSpawns();
						Location loc = LocationHandler.getPlayerLocation(list.get(spawn - 1));
						p.teleport(loc);
						p.sendMessage(Msgs.Arena_Tpd_To_Spawn.getString("<spawn>", String.valueOf(spawn)));
					} catch (NumberFormatException NFE)
					{
						p.sendMessage(Msgs.Commands_How_To_Tp_To_Arena_Spawns.getString());
					}
				} else
				{
					p.sendMessage(Msgs.Commands_How_To_Tp_To_Arena_Spawns.getString());
				}
			}
			// /////////////////////////////////////////
			// DELSPAWN
			else if (args.length > 0 && args[0].equalsIgnoreCase("DelSpawn"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!p.hasPermission("Cranked.SetUp"))
				{
					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				}
				if (cp.getCreating() == null)
				{
					p.sendMessage(Msgs.Commands_How_To_Show_Arena_Spawns.getString());
					return true;
				}
				if (args.length != 1)
				{
					try
					{
						int spawn = Integer.valueOf(args[1]);
						String arenaName = cp.getCreating();
						List<String> list = ArenaManager.getArena(arenaName).getSpawns();
						list.remove(spawn-1);
						Files.getArenas().set("Arenas." + arenaName + ".Spawns", list);
						Files.saveArenas();
						p.sendMessage(Msgs.Arena_Spawn_Removed.getString("<spawn>", String.valueOf(spawn)));
					} catch (NumberFormatException NFE)
					{
						p.sendMessage(Msgs.Commands_How_To_Delete_Arena_Spawns.getString());
					}
				} else
				{
					p.sendMessage(Msgs.Commands_How_To_Delete_Arena_Spawns.getString());
				}
			}

			// ///////////////////////////////////
			// SPAWNS
			else if (args.length > 0 && args[0].equalsIgnoreCase("Spawns"))
			{

				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!p.hasPermission("Cranked.SetUp"))
				{
					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				}
				if (cp.getCreating() == null)
				{
					p.sendMessage(Msgs.Commands_How_To_Show_Arena_Spawns.getString());
					return true;
				}
				List<String> list = ArenaManager.getArena(cp.getCreating()).getSpawns();
				p.sendMessage(Msgs.Arena_Spawns.getString("<spawns>", String.valueOf(list.size())));
			} else
			{
				CommandSender player = sender;
				player.sendMessage("");
				player.sendMessage(Msgs.Format_Header.getString("<title>", "Cranked"));
				if (Main.update)
					player.sendMessage(Main.cranked + ChatColor.RED + ChatColor.BOLD + "Update Available: " + ChatColor.WHITE + ChatColor.BOLD + Main.name);
				player.sendMessage("");
				player.sendMessage(Main.cranked + ChatColor.GRAY + "Author: " + ChatColor.GREEN + ChatColor.BOLD + "SniperzCiinema(xXSniperzzXx_SD)");
				player.sendMessage(Main.cranked + ChatColor.GRAY + "Version: " + ChatColor.GREEN + ChatColor.BOLD + plugin.getDescription().getVersion());
				player.sendMessage(Main.cranked + ChatColor.GRAY + "BukkitDev: " + ChatColor.GREEN + ChatColor.BOLD + "http://bit.ly/1c8oN9S");

				player.sendMessage(Main.cranked + ChatColor.YELLOW + "For Help type: /Cranked Help");

			}
		}
		return true;
	}

}