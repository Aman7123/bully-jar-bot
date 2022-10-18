package com.aaronrenner.discordnftbot.discord.commands;

import java.awt.Color;
import java.util.Optional;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import com.aaronrenner.discordnftbot.discord.DiscordBot;

public class Help {
	
	final String donateDescription = "Add money to your jar, amount should be an integer 1, 2, or 3!";
	final String leaderboardDescription = "Show the leaderboard!";
	final String clearDescription = "(admin only) Clear all entries in the database!";
	final String helpDescription = "Displays this menu!";

	public Help(Optional<User> userSender, Optional<Server> serverSentIn, Color botColor, DiscordBot bot, Message message, String commandPrefix) {
		EmbedBuilder generalCommands = new EmbedBuilder()
			.setColor(botColor)
			.addField(commandPrefix + "donate <amount>",		donateDescription)
			.addField(commandPrefix + "jar",			        leaderboardDescription)
			.addField(commandPrefix + "clear",			        clearDescription)
			.addField(commandPrefix + "help", 					helpDescription)
		;
		
		MessageBuilder messageBuilder = new MessageBuilder();
		messageBuilder.setEmbed(generalCommands).send(message.getChannel());
	}

}
