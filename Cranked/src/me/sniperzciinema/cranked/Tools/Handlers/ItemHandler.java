
package me.sniperzciinema.cranked.Tools.Handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemHandler {

	// Get the items ID from the path
	public static Integer getItemID(String Path) {
		String itemid = null;
		String string = Path;
		if (string.contains(":"))
		{
			String[] ss = string.split(":");
			itemid = ss[0];
		} else if (string.contains(","))
		{
			String[] ss = string.split(",");
			itemid = ss[0];
		} else if (string.contains("-"))
		{
			String[] ss = string.split("-");
			itemid = ss[0];
		} else if (string.contains("@"))
		{
			String[] ss = string.split("@");
			itemid = ss[0];
		} else if (string.contains("%"))
		{
			String[] ss = string.split("%");
			itemid = ss[0];
		} else
			itemid = string;
		int i = 0;
		try
		{
			i = Integer.valueOf(itemid);
		} catch (NumberFormatException nfe)
		{
			i = 0;
		}
		return i;
	}

	// Get the items durability from the path
	public static Short getItemData(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains(":"))
		{
			String[] s = string.split(":");
			if (s[1].contains(","))
			{
				String[] ss = s[1].split(",");
				itemdata = ss[0];
			} else if (s[1].contains("-"))
			{
				String[] ss = s[1].split("-");
				itemdata = ss[0];
			} else if (s[1].contains("@"))
			{
				String[] ss = s[1].split("@");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
				itemdata = s[1];

		} else
			itemdata = "0";
		Short s = 0;
		try
		{
			s = Short.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			s = 0;
		}
		return s;
	}

	// Get the items amount from the path
	public static Integer getItemAmount(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains(","))
		{
			String[] s = string.split(",");
			if (s[1].contains("-"))
			{
				String[] ss = s[1].split("-");
				itemdata = ss[0];
			} else if (s[1].contains("@"))
			{
				String[] ss = s[1].split("@");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
				itemdata = s[1];
		} else
			itemdata = "1";
		int i = 1;
		try
		{
			i = Integer.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			i = 1;
		}
		return i;
	}

	// Get the items Enchantment from the path(Used a little differently then
	// the rest(Refer to getItemStack))
	public static int getItemEnchant(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains("-"))
		{
			String[] s = string.split("-");
			if (s[1].contains("@"))
			{
				String[] ss = s[1].split("@");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
			{
				itemdata = s[1];
			}
		} else
			itemdata = "0";
		int i = 1;
		try
		{
			i = Integer.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			i = 1;
		}
		return i;
	}

	// Get the items Enchantment level from the path(Used a little differently
	// then the rest(Refer to getItemStack))
	public static int getItemEnchantLvl(String Path) {
		String itemdata = null;
		String string = Path;
		if (string.contains("@"))
		{
			String[] s = string.split("@");
			if (s[1].contains("-"))
			{
				String[] ss = s[1].split("-");
				itemdata = ss[0];
			} else if (s[1].contains("%"))
			{
				String[] ss = s[1].split("%");
				itemdata = ss[0];
			} else
			{
				itemdata = s[1];
			}
		} else
			itemdata = "1";
		int i = 1;
		try
		{
			i = Integer.valueOf(itemdata);
		} catch (NumberFormatException nfe)
		{
			i = 1;
		}
		return i;
	}

	// Get the items name from the path
	public static String getItemName(String Path) {
		String itemName = null;
		if (Path.contains("%"))
		{
			String[] ss = Path.split("%");
			itemName = ChatColor.translateAlternateColorCodes('&', ss[1]);
		} else
		{
			itemName = null;
		}
		return itemName;
	}

	// Take all methods to get a new ItemStack
	@SuppressWarnings("deprecation")
	public static ItemStack getItemStack(String location) {
		ItemStack is = null;
		// Make sure the ItemID isn't null, if so set the id, and amount
		if (Material.getMaterial(getItemID(location)) != null)
			is = new ItemStack(Material.getMaterial(getItemID(location)),
					getItemAmount(String.valueOf(location)));
		else
			is = new ItemStack(Material.AIR);

		// Sets the durability
		is.setDurability(getItemData(location));
		// Sets the custom name
		if (!(getItemName(location) == null))
		{
			ItemMeta im = is.getItemMeta();
			String name = getItemName(location).replaceAll("_", " ");
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			is.setItemMeta(im);
		}
		// Make sure we're not trying to enchant air
		if (is.getType() != Material.AIR)
			// If the string contains a -
			if (location.contains("-"))
			{
				int i;
				// Split the sting to get ALL enchantments listed
				String enchants[] = location.split("-");
				for (i = 1; i != enchants.length; i++)
				{
					if (enchants[i] != null)
					{
						// Add the enchantment
						enchants[i] = "-" + enchants[i];

						is.addUnsafeEnchantment(Enchantment.getById(getItemEnchant(enchants[i])), getItemEnchantLvl(enchants[i]));
					}
				}
			}
		return is;
	}

	// Loop through a list of these Item Codes and make a ItemStack[]
	public static ItemStack[] getItemStackList(List<String> list) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (String string : list)
		{
			items.add(getItemStack(string));
		}
		ItemStack[] stack = items.toArray(new ItemStack[0]);
		return stack;
	}

	// Take the item in the players hand and convert it into a code.
	@SuppressWarnings("deprecation")
	public static String getItemStackToString(ItemStack i) {
		String itemCode = "0";

		if (i.getTypeId() != 0 && i != null)
		{

			itemCode = String.valueOf(i.getTypeId());

			if (i.getDurability() != 0)
				itemCode = itemCode + ":" + i.getDurability();
			if (i.getAmount() != 1)
				itemCode = itemCode + "," + i.getAmount();
			for (Entry<Enchantment, Integer> ench : i.getEnchantments().entrySet())
			{
				itemCode = itemCode + "-" + ench.getKey().getId();
				if (ench.getValue() != 1)
					itemCode = itemCode + "@" + ench.getValue();
			}
			if (i.getItemMeta().getDisplayName() != null)
				itemCode = itemCode + "%" + i.getItemMeta().getDisplayName().replaceAll(" ", "_").replaceAll("�", "&");
		}
		return itemCode;
	}

}
