package factionmod.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import akka.japi.Pair;
import factionmod.handler.EventHandlerChunk;
import factionmod.manager.IChunkManager;
import factionmod.manager.ManagerWarZone;
import factionmod.manager.instanciation.ChunkManagerCreator;
import factionmod.manager.instanciation.ZoneInstance;
import factionmod.utils.DimensionalPosition;
import factionmod.utils.MessageHelper;

public class CommandWarZone extends CommandBase {

	@Override
	public String getName() {
		return "warzone";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/warzone [remove]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!(sender instanceof EntityPlayerMP))
			throw new WrongUsageException("You have to be a player", new Object[0]);

		DimensionalPosition position = DimensionalPosition.from(sender);
		if (args.length == 0) {
			if (EventHandlerChunk.getManagerFor(position) != null) {
				sender.sendMessage(MessageHelper.error("A chunk manager is already set to this chunk."));
			} else {
				String arguments = "";
				for(String str : args) {
					arguments += str + " ";
				}
				Pair<IChunkManager, ZoneInstance> pair = ChunkManagerCreator.createChunkHandler("war", arguments);
				EventHandlerChunk.registerChunkManager(pair.first(), position, pair.second(), true);
				sender.sendMessage(MessageHelper.info("A war zone was created."));
			}
		} else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("remove")) {
				IChunkManager manager = EventHandlerChunk.getManagerFor(position);
				if (manager == null)
					sender.sendMessage(MessageHelper.info("Nothing to remove here."));
				else if (!(manager instanceof ManagerWarZone))
					sender.sendMessage(MessageHelper.error("This chunk is not a warzone"));
				else {
					EventHandlerChunk.unregisterChunkManager(position, true);
					sender.sendMessage(MessageHelper.info("War zone removed."));
				}

			} else {
				throw new WrongUsageException("/warzone [remove]", new Object[0]);
			}
		}
	}

}
