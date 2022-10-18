package com.aaronrenner.discordnftbot.discord;

import java.awt.Color;
import java.time.Instant;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aaronrenner.discordnftbot.models.BullyJar;
import com.aaronrenner.discordnftbot.repositories.BullyJarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DiscordBot {
	
	// Required
	public static final String NEWLINE  = "\n";
	public static final Color MSG_COLOR = Color.ORANGE;
	private DiscordApi disc;
	@Autowired
	private BullyJarRepository bjRepo;
	
	// Custom
	private static final Logger LOGGER  = LoggerFactory.getLogger(DiscordBot.class);
	
	public void build(String token, String commandPrefix) {
		try {
			// Start Discord connection
	        this.disc = new DiscordApiBuilder()
	        					.setToken(token)
	        					.login()
	        					.join();
	        // Listeners
	        this.disc.addListener(new Commands(commandPrefix, this));
	        // Sets the Discord activity message
	        this.disc.updateActivity(ActivityType.LISTENING, commandPrefix + "help");

	        startupLogger();
		} catch (Exception e) {
			LOGGER.error("Discord Error: Failed starting bot! Exception: " + e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
	}
	
	public void addToBJ(User commandRunner, TextChannel executedIn, long existingMessageId, long parsedAmountMessage) {
		// Perform save logic
		long userId = commandRunner.getId();
		boolean userHasAJar = this.bjRepo.existsByDiscordId(userId);
		BullyJar getJar = null;
		if(userHasAJar) {
			getJar = this.bjRepo.getByDiscordId(userId);
		} else {
			getJar = new BullyJar();
			getJar.setCounter(Long.parseLong("0"));
		}
		long existingCounterBuffer = getJar.getCounter();
		long newCounterTotal       = (existingCounterBuffer + parsedAmountMessage);
		getJar.setDiscordId(userId);
		getJar.setDiscordUser(commandRunner.getDiscriminatedName());
		getJar.setCounter(newCounterTotal);
		getJar.setLastUpdated(Instant.now());
		this.bjRepo.save(getJar);
		
		// Send confimation response in Discord
		String coinsAddedDisplay = "";
		for(int i = 0; i < parsedAmountMessage; i++) {
			coinsAddedDisplay+=":coin:";
		}
		String addedToJarMsg = "added %s to your account for a total of %s:coin:'s";
		String currentRecord = String.format(addedToJarMsg, coinsAddedDisplay, newCounterTotal);
		String successMsg = String.format("%s %s", commandRunner.getMentionTag(), currentRecord);
		new MessageBuilder().replyTo(existingMessageId).setContent(successMsg).send(executedIn);
	}
	
	public void listLeaderboard(TextChannel executedIn) throws InterruptedException {
		// Logic for Discord
		List<BullyJar> allUsers = this.bjRepo.findAll();
		// Discord only supports so many char per msg
		// When running a game with more than ~50 people
		// This will cause the msg to be lost
		int maxPerMsg = 15;
		// Calculate how many batches of 25 will need
		int piece = (int) (Math.floor(allUsers.size() / maxPerMsg) + 1);
		// Hold overall position
		int overallPos = 0;
		// Run batches
		for(int w = 0; (w<piece); w++) {
			// Holds this msg
			MessageBuilder messageBuilder = new MessageBuilder();
			String partialMsg = "";
			// Add Winners
			for(int t = 0; t < maxPerMsg; t++) {
				if(overallPos < allUsers.size()) {
					BullyJar bj = allUsers.get(overallPos);
					partialMsg += String.format("`%s. ` %s • **%s** :coin:s %s", (overallPos + 1), "<@!"+bj.getDiscordId()+">", bj.getCounter(), NEWLINE);
					overallPos++;
					// Break here to save CPU cycles
				} else break;
			}
			// Dispatch message
			EmbedBuilder newMsg = new EmbedBuilder()
				.setColor(MSG_COLOR)
				.addField(
					"**Bully Jar Leaderboard**",
					String.format("%sLeaderboard (%s/%s)%s", NEWLINE, (w + 1), piece,NEWLINE))
				.addField(
					"**  **",
					partialMsg)
				.setTimestampToNow();
			messageBuilder.setEmbed(newMsg);
			messageBuilder.send(executedIn);
			Thread.sleep(1000);
		}
	}

	public void clearLeaderboard() {
		bjRepo.deleteAll();
	}
	
	private void startupLogger() throws JsonProcessingException {
        LOGGER.info("--------");
		LOGGER.debug("Discord bot config: "+ new ObjectMapper().writeValueAsString(disc.getApplicationInfo().join()));
        LOGGER.debug("Discord connection started!");
        LOGGER.info("Need this bot in your server? " + disc.createBotInvite(Permissions.fromBitmask(0x0000000008)));
        LOGGER.info("--------");
	}
	
	public DiscordApi getDiscord() {
		return this.disc;
	}
}