package com.aaronrenner.discordnftbot.discord.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.user.User;
import static java.util.Objects.isNull;
import com.aaronrenner.discordnftbot.discord.DiscordBot;

public class Donate {
	
	private int MAX_AMOUNT = 3;
	private final String IMPROPER_AMOUNT_MSG = "the amount you entered is incorrect - please enter 1, 2 or 3!";

	public Donate(DiscordBot bot, Message message) {
		String[] splitCommand = message.getContent().split(" ");
		User commandRunner = message.getUserAuthor().get();
		TextChannel executedIn = message.getChannel();
		try {
			String amount = splitCommand[1];
			if(isNull(amount)) {
				String errMsg = String.format("%s %s", commandRunner.getMentionTag(), IMPROPER_AMOUNT_MSG);
				new MessageBuilder().replyTo(message).setContent(errMsg).send(executedIn);
				message.addReaction("👎");
				return;
			}
			int parsedAmount = Integer.valueOf(amount);
			if(0 > parsedAmount || parsedAmount > MAX_AMOUNT) {
				String errMsg = String.format("%s %s", commandRunner.getMentionTag(), IMPROPER_AMOUNT_MSG);
				new MessageBuilder().replyTo(message).setContent(errMsg).send(executedIn);
				message.addReaction("👎");
				return;
			}
			
			bot.addToBJ(commandRunner, executedIn, message.getId(), parsedAmount);
			message.addReaction("👍");
		} catch (Exception e) {
			e.printStackTrace();
			String improperAmountMsg = "the donate command is invalid, please run it as `donate <amount>` where amount in the number 1, 2 or 3!";
			String errMsg = String.format("%s %s", commandRunner.getMentionTag(), improperAmountMsg);
			new MessageBuilder().replyTo(message).setContent(errMsg).send(executedIn);
			message.addReaction("👎");
		}
	}

}
