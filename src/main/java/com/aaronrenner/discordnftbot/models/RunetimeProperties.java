package com.aaronrenner.discordnftbot.models;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nft-bot")
public class RunetimeProperties {
	
	@Autowired
	DiscordProperties discordProperties;
	
	/**
	 * Sets the discord object!
	 * @param token
	 * @throws RuntimeException
	 */
	public void setDiscord(Map<Object, Object> discordInfo) throws RuntimeException {
		this.discordProperties.configureDiscordProperties(discordInfo);
	}
}