package factionmod.command.utils;

import java.util.ArrayList;

import factionmod.faction.Faction;
import factionmod.handler.EventHandlerFaction;
import factionmod.utils.ServerUtils;

/**
 * This class provide an help to complete the commands.
 * 
 * @author BrokenSwing
 *
 */
public class AutoCompleter {

	/**
	 * Returns a list containing all the names of the players which starts with
	 * the specified chain of characters.
	 * 
	 * @param playerName
	 *            The chain of character the name of the player start with
	 * @return all the names which start with the specified chain of characters
	 */
	public static ArrayList<String> completePlayer(String playerName) {
		String[] usernames = ServerUtils.getServer().getPlayerProfileCache().getUsernames();
		ArrayList<String> ret = new ArrayList<String>();
		for(String username : usernames) {
			if (username.toLowerCase().startsWith(playerName.toLowerCase())) {
				ret.add(username);
			}
		}
		return ret;
	}

	/**
	 * Returns all the names of the specified objects which start with the
	 * specified name.
	 * 
	 * @param name
	 *            The start of the name
	 * @param objects
	 *            The objects to test
	 * @return the names of the objects which start with the specified name
	 */
	public static ArrayList<String> complete(String name, Object[] objects) {
		ArrayList<String> ret = new ArrayList<String>();
		for(Object obj : objects) {
			if (obj.toString().toLowerCase().startsWith(name.toLowerCase())) {
				ret.add(obj.toString());
			}
		}
		return ret;
	}

	/**
	 * Returns all the name of the factions starting with the specified name.
	 * 
	 * @param factionName
	 *            The name
	 * @return all the names starting with the specified name
	 */
	public static ArrayList<String> completeFactions(String factionName) {
		ArrayList<String> names = new ArrayList<String>();
		for(Faction faction : EventHandlerFaction.getFactions().values()) {
			if (faction.getName().toLowerCase().startsWith(factionName.toLowerCase())) {
				names.add(faction.getName());
			}
		}
		return names;
	}

}
