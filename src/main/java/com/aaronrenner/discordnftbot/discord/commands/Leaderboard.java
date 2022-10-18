package com.aaronrenner.discordnftbot.discord.commands;

import org.javacord.api.entity.message.Message;
import com.aaronrenner.discordnftbot.discord.DiscordBot;

public class Leaderboard {

	public Leaderboard(DiscordBot bot, Message message) {
		try {
			bot.listLeaderboard(message.getChannel());
			message.addReaction("👍");
		} catch (Exception e) {
			e.printStackTrace();
			message.addReaction("👎");
		}
	}
}
