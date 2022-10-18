package com.aaronrenner.discordnftbot.discord.commands;

import org.javacord.api.entity.message.Message;
import com.aaronrenner.discordnftbot.discord.DiscordBot;

public class Clear {

	public Clear(DiscordBot bot, Message message) {
		try {
			bot.clearLeaderboard();
			message.addReaction("👍");
		} catch (Exception e) {
			e.printStackTrace();
			message.addReaction("👎");
		}
	}
}
