
package me.sniperzciinema.cranked;

import me.sniperzciinema.cranked.Tools.Msgs;
import me.sniperzciinema.cranked.Tools.Handlers.ArenaManager;

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
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (ArenaManager.getArena(p) != null)
				{
					sender.sendMessage(Msgs.Error_Already_In_A_Game.getString());
					return true;
				} else if (args.length == 2)
				{
					String arena = args[1];
					if (ArenaManager.arenaRegistered(arena))
					{
						if (ArenaManager.isArenaValid(arena))
						{

							sender.sendMessage("joined arena");
							ArenaManager.getArena(arena).getPlayerManager().addPlayer(p);
						} else
						{
							sender.sendMessage(Msgs.Error_Missing_Spawns.getString("<arena>", arena));
						}
					} else
					{
						sender.sendMessage(Msgs.Error_Not_An_Arena.getString("<arena>", arena));
					}
				} else
				{
					sender.sendMessage(Msgs.Commands_How_To_Join.getString());
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
				if (!p.hasPermission("Cracked.Join"))
				{

					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (ArenaManager.getArena(p) == null)
				{

					p.sendMessage(Msgs.Error_Not_In_A_Game.getString());
					return true;
				} else
				{
					ArenaManager.getArena(p).getPlayerManager().removePlayer(p);

					p.sendMessage("left arena");
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

					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (args.length == 2)
				{
					String arena = args[1];
					if (!ArenaManager.arenaRegistered(arena))
					{

						ArenaManager.createArena(args[1], p.getLocation());

						p.sendMessage(Msgs.Arena_Created.getString("<arena>", arena));
						p.sendMessage(Msgs.Commands_How_To_Set_Spawn.getString());

						plugin.creating.put(p.getName(), arena);
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
				if (!sender.hasPermission("Cracked.SetUp"))
				{

					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else if (args.length == 2)
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
				if (!sender.hasPermission("Cracked.List"))
				{
					sender.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else
				{

					sender.sendMessage(Msgs.Format_Header.getString("<title>", "Arenas"));
					sender.sendMessage(Msgs.Commands_List_Arenas.getString("<validarenas>", ArenaManager.getPossibleArenas(), "<notvalidarenas>", ArenaManager.getNotPossibleArenas()));

					sender.sendMessage(ArenaManager.getPossibleArenas() + " - " + ArenaManager.getNotPossibleArenas());
				}
			}
			// //////////////////////////////SETSPAWN///////////////////////////////////
			else if (args.length > 0 && args[0].equalsIgnoreCase("SETSPAWN"))
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage("Expected a player!");
					return true;
				}
				Player p = (Player) sender;
				if (!p.hasPermission("Cracked.Setup"))
				{
					p.sendMessage(Msgs.Error_No_Permission.getString());
					return true;
				} else
				{
					String arena = plugin.creating.get(p.getName());
					if (ArenaManager.arenaRegistered(arena))
					{

					} else
					{
						p.sendMessage(Msgs.Commands_How_To_Set_Spawn.getString());
					}
				}
			}
		}

		return true;
	}

}