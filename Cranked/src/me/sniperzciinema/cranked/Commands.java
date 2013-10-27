
package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.ArenaHandlers.Arena;
import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.ArenaHandlers.States;
import me.sniperzciinema.cranked.Extras.Menus;
import me.sniperzciinema.cranked.GameMechanics.Agility;
import me.sniperzciinema.cranked.GameMechanics.DeathTypes;
import me.sniperzciinema.cranked.GameMechanics.Deaths;
import me.sniperzciinema.cranked.Messages.Msgs;
import me.sniperzciinema.cranked.Messages.Time;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayer;
import me.sniperzciinema.cranked.PlayerHandlers.CPlayerManager;

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
				p = (Player)sender;
				cp = CPlayerManager.getCrankedPlayer(p);
			}

			if (args.length > 0 && args[0].equalsIgnoreCase("TEST"))
			{
				Deaths.playerDies(p, null, DeathTypes.Melee);
			}
			// //////////////////////////////JOIN///////////////////////////////////
			if (args.length > 0 && args[0].equalsIgnoreCase("JOIN"))
			{
				if (p == null)
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!sender.hasPermission("Cranked.Join"))
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
							Game.join(cp, ArenaManager.getArena(arenaName));
							Arena arena = cp.getArena();
							// Info the players of their current situation
							p.sendMessage(Msgs.Format_Line.getString());
							p.sendMessage("");
							p.sendMessage(Msgs.Game_You_Joined_A_Game.getString("<arena>", cp.getArena().getName()));
							p.sendMessage(Msgs.Arena_Creator.getString("<creator>", arena.getCreator()));
							p.sendMessage("");
							p.sendMessage("");
							if (arena.getState() == States.Waiting)
							{
								p.sendMessage(Msgs.Arena_StatusUpdate.getString("<current>", String.valueOf(arena.getPlayers().size()), "<needed>", String.valueOf(arena.getSettings().getRequiredPlayers())));
							} else if (arena.getState() == States.PreGame)
							{
								p.sendMessage(Msgs.Game_Starting.getString("<time>", Time.getTime((long) arena.getTimer().getTimeLeft())));
							} else if (arena.getState() == States.Started)
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
						if(args.length == 3)
							ArenaManager.getArena(arena).setCreator(args[2]);
						else
							ArenaManager.getArena(arena).setCreator("Unkown");
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
					sender.sendMessage(Msgs.Commands_List_Arenas.getString("<validarenas>", ArenaManager.getPossibleArenas(), "<notvalidarenas>", ArenaManager.getNotPossibleArenas()));
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
						p.sendMessage(Msgs.Commands_Spawn_Set.getString("<spawns>", String.valueOf(ArenaManager.getArena(arena).getSpawns().size())));
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
					if(args.length == 2){
						
						
						if (ArenaManager.arenaRegistered(args[1]))
						{
							cp.setCreating(args[1]);
							p.sendMessage(Msgs.Commands_Arena_Is_Set.getString("<arena>", args[1]));
						} 
						else
						{
							sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", args[1]));
						}		
					}
					else{
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
					if(args.length == 2){
						
					}
				}
			}
		}

		return true;
	}

}