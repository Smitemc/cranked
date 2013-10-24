
package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.ArenaHandlers.ArenaManager;
import me.sniperzciinema.cranked.Tools.Msgs;

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

			// //////////////////////////////JOIN///////////////////////////////////
			if (args.length > 0 && args[0].equalsIgnoreCase("JOIN"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				Player p = (Player) sender;

				if (!sender.hasPermission("Cracked.Join"))
				{
					sender.sendMessage("No perms");
					//sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (ArenaManager.getArena(p) != null)
				{
					sender.sendMessage("Already in");
					//sender.sendMessage(Msgs.Error_Already_In_A_Game.getString());
					return true;
				} else if (args.length == 2)
				{
					String arena = args[1];
					if (ArenaManager.arenaRegistered(arena))
					{
						if(ArenaManager.isArenaValid(arena)){

							sender.sendMessage("joined arena");
							ArenaManager.getArena(arena).getPlayerManager().addPlayer(p);
						} else
						{
							sender.sendMessage("Missing spawns");
							//sender.sendMessage(Msgs.Error_Missing_Spawns.getString("<arena>", arena));
						}
					} else
					{
						sender.sendMessage("Not An Arena");
						//sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", arena));
					}
				} else
				{
					sender.sendMessage("How To Join");
					//sender.sendMessage(Msgs.Commands_How_To_Join.getString());
				}
			}
			// //////////////////////////////LEAVE///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("LEAVE"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				Player p = (Player) sender;
				if (!sender.hasPermission("Cracked.Join"))
				{

					sender.sendMessage("No Perms");
					//sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (ArenaManager.getArena(p) == null)
				{

					sender.sendMessage("Not in game");
					//sender.sendMessage(Msgs.Error_Not_In_A_Game.getString());
					return true;
				} else
				{
					ArenaManager.getArena(p).getPlayerManager().removePlayer(p);

					sender.sendMessage("left arena");
				}
			}

			// //////////////////////////////CREATE///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("CREATE"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				Player p = (Player) sender;
				if (!sender.hasPermission("Cracked.SetUp"))
				{

					sender.sendMessage("No Perms");
					//sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (args.length == 2)
				{
					String arena = args[1];
					if (!ArenaManager.arenaRegistered(arena))
					{

						sender.sendMessage("Arena made");
						ArenaManager.createArena(args[1], p.getLocation());
						
						//sender.sendMessage(Msgs.Arena_Created.getString("<arena>", arena));
						//sender.sendMessage(Msgs.Arena_First_Spawn_Set.getString());
						//sender.sendMessage(Msgs.Arena_How_To_Set_More_Spawns.getString());
					} else
					{

						sender.sendMessage("Already an arena");
//						sender.sendMessage(Msgs.Error_Already_An_Arena.getString("<arena>", arena));
					}
				} else
				{

					sender.sendMessage("How to Create");
					//sender.sendMessage(Msgs.Commands_How_To_Create.getString());
				}

			}

			// //////////////////////////////REMOVE///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("REMOVE"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				if (!sender.hasPermission("Cracked.SetUp"))
				{

					sender.sendMessage("No Perms");
					//sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (args.length == 2)
				{
					String arena = args[1];
					if (ArenaManager.arenaRegistered(arena))
					{
						ArenaManager.removeArena(args[1]);

						sender.sendMessage("Arena removed");
//						sender.sendMessage(Msgs.Arena_Removed.getString("<arena>", arena));
					} else
					{
						sender.sendMessage("Not An Arena");
	//					sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", arena));
					}
				} else
				{
					sender.sendMessage("How to remove");
				//	sender.sendMessage(Msgs.Commands_How_To_Remove.getString());
				}

			}
			// //////////////////////////////ARENAS///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("ARENAS"))
			{
				if (!sender.hasPermission("Cracked.List"))
				{
					sender.sendMessage("No Perms");
					//sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				}else{
					//TODO: send message of good maps and bad maps
				}

			}
			else{
				sender.sendMessage("Test");
			}
		}

		return true;
	}

}