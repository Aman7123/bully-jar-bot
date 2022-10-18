package com.aaronrenner.discordnftbot.discord;

import java.awt.Color;
import java.util.Optional;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import com.aaronrenner.discordnftbot.discord.commands.*;
import static com.aaronrenner.discordnftbot.utils.DiscordUtils.userHasAdminPerms;

public class Commands implements MessageCreateListener {

	// Custom
	private String commandPrefix = null;
	private Color botColor 		 = Color.ORANGE;
	
	// Required
	private DiscordBot bot;
	
	public Commands(String commandPrefix, DiscordBot bot) {
		this.commandPrefix 		= commandPrefix;
		this.bot 				= bot;
	}
	
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		/** The message event for this action */
		Message message = event.getMessage();
		Optional<User> userSender = message.getUserAuthor();
		Optional<Server> serverSentIn = message.getServer();
		
		if (message.getContent().startsWith(commandPrefix)) {
			
			//
			if(message.getContent().contains(commandPrefix+"donate")) {
				new Donate(this.bot, message);
			}
			
			//
			if(message.getContent().equalsIgnoreCase(commandPrefix+"jar")) {
				new Leaderboard(this.bot, message);
			}
			
			// Help message
			if(message.getContent().equalsIgnoreCase(commandPrefix+"help")) {
				new Help(userSender, serverSentIn, this.botColor, this.bot, message, this.commandPrefix);
			}
			
			// Validate admin for all other commands
			if(userHasAdminPerms(userSender, serverSentIn)) {

				//
				if(message.getContent().equalsIgnoreCase(commandPrefix+"clear")) {
					new Clear(this.bot, message);
				}
			}
        }
	}
}
