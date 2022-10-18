package com.aaronrenner.discordnftbot.models;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aaronrenner.discordnftbot.discord.DiscordBot;

@Component
public class DiscordProperties {
	
	@Autowired
	DiscordBot discordBot;
	
	private final String TOKEN = "token";
	private final String COMMAND_PREFIX = "commandPrefix";

	public void configureDiscordProperties(Map<Object, Object> discordInfo) throws RuntimeException {
		
		/** Variables of the discord root node*/
		String discordToken = (String) discordInfo.get(TOKEN);
		String commandPrefix = (String) discordInfo.get(COMMAND_PREFIX);

		this.discordBot.build(discordToken, commandPrefix);
	}

}
