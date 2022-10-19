package com.aaronrenner.discordnftbot.discord.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.user.User;
import static java.util.Objects.isNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aaronrenner.discordnftbot.discord.DiscordBot;

public class Donate {
	
	private int MAX_AMOUNT = 3;
	private final String IMPROPER_AMOUNT_MSG = "the amount you entered is incorrect - please enter 1, 2 or 3!";
	private final String MISSUSE_OF_TARGET = "the target should be @'ing someone in this Discord server!";
	private final String IMPROPER_CMD_USG_MSG = "the donate command is invalid, please run it as `donate <amount> <optional: user>` where amount in the number 1, 2 or 3!";

	public Donate(DiscordBot bot, Message message) {
		String[] splitCommand = message.getContent().split(" ");
		User commandRunner = message.getUserAuthor().get();
		TextChannel executedIn = message.getChannel();
		try {
			String amount = splitCommand[1];
			if(isNull(amount)) {
				amountErrorExit(commandRunner, executedIn, message);
				return;
			}
			int parsedAmount = Integer.valueOf(amount);
			if(0 > parsedAmount || parsedAmount > MAX_AMOUNT) {
				amountErrorExit(commandRunner, executedIn, message);
				return;
			}
			
			//User targetedUser = null;
			if(splitCommand.length > 2) {
				String targetMentionTag = splitCommand[2];
				if(targetMentionTag.matches("<@[!]?([0-9]+)>")) {
					String targetId = targetMentionTag.replaceAll("([<]?[@]?[!]?[>]?)", "");
					//Long targetUserId = Long.valueOf(targetId);
					commandRunner = bot.getDiscord().getUserById(targetId).get();
				} else {
					targetErrorExit(commandRunner, executedIn, message);
				}
			}
			
			bot.addToBJ(commandRunner, executedIn, message.getId(), parsedAmount);
			message.addReaction("👍");
		} catch (Exception e) {
			e.printStackTrace();
			String errMsg = String.format("%s %s", commandRunner.getMentionTag(), IMPROPER_CMD_USG_MSG);
			new MessageBuilder().replyTo(message).setContent(errMsg).send(executedIn);
			message.addReaction("👎");
		}
	}
	
	public void amountErrorExit(User commandRunner, TextChannel executedIn, Message message) {
		String errMsg = String.format("%s %s", commandRunner.getMentionTag(), IMPROPER_AMOUNT_MSG);
		new MessageBuilder().replyTo(message).setContent(errMsg).send(executedIn);
		message.addReaction("👎");
	}
	
	public void targetErrorExit(User commandRunner, TextChannel executedIn, Message message) {
		String errMsg = String.format("%s %s", commandRunner.getMentionTag(), MISSUSE_OF_TARGET);
		new MessageBuilder().replyTo(message).setContent(errMsg).send(executedIn);
		message.addReaction("👎");
	}

}
