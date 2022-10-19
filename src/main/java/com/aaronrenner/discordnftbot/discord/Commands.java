package com.aaronrenner.discordnftbot.discord;

import java.awt.Color;
import java.util.Optional;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aaronrenner.discordnftbot.discord.commands.*;
import static com.aaronrenner.discordnftbot.utils.DiscordUtils.userHasAdminPerms;

public class Commands implements MessageCreateListener {

	// Custom
	private String commandPrefix = null;
	private Color botColor 		 = Color.ORANGE;
	
	// Required
	private DiscordBot bot;
	private static final Logger LOGGER  = LoggerFactory.getLogger(Commands.class);
	
	public Commands(String commandPrefix, DiscordBot bot) {
		this.commandPrefix 		= commandPrefix;
		this.bot 				= bot;
	}
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		/** The message event for this action */
		Message message = event.getMessage();
		String parsedMsg = message.getContent().trim();
		Optional<User> userSender = message.getUserAuthor();
		Optional<Server> serverSentIn = message.getServer();
		
		if (message.getContent().startsWith(commandPrefix)  && !userSender.get().isYourself()) {
			User sender = userSender.get();
			String userName = sender.getDiscriminatedName();
			
			LOGGER.info("User {} has triggered the the bot listener with [{}]!", userName, parsedMsg);
			//
			if(parsedMsg.contains(commandPrefix+"donate")) {
				new Donate(this.bot, message);
				LOGGER.info("User {} has run donate command!", userName);
			}
			
			//
			if(parsedMsg.equalsIgnoreCase(commandPrefix+"jar")) {
				new Leaderboard(this.bot, message);
				LOGGER.info("User {} has run jar command!", userName);
			}
			
			// Help message
			if(parsedMsg.equalsIgnoreCase(commandPrefix+"help")) {
				new Help(userSender, serverSentIn, this.botColor, this.bot, message, this.commandPrefix);
				LOGGER.info("User {} has run help command!", userName);
			}
			
			// Validate admin for all other commands
			if(userHasAdminPerms(userSender, serverSentIn)) {

				//
				if(parsedMsg.equalsIgnoreCase(commandPrefix+"clear")) {
					new Clear(this.bot, message);
					LOGGER.info("User {} has run clear command!", userName);
				}
			}
        }
	}
}
