package com.aaronrenner.discordnftbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.aaronrenner.discordnftbot.DiscordBotApplication;
import com.aaronrenner.discordnftbot.models.RunetimeProperties;

@SpringBootApplication
@EnableConfigurationProperties(RunetimeProperties.class)
public class DiscordBotApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscordBotApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DiscordBotApplication.class, args);

	}
	
	@Bean
	public void printInfo() {
		/* Total number of processors or cores available to the JVM */
		LOGGER.info("Available processors (cores): {}", Runtime.getRuntime().availableProcessors());

		/* Total memory currently in use by the JVM */
		LOGGER.info("Total memory (mb): {}", Runtime.getRuntime().totalMemory()/1000000);

		/* Total amount of free memory available to the JVM */
		LOGGER.info("Free memory (mb): {}", Runtime.getRuntime().freeMemory()/1000000);

		/* This will return Long.MAX_VALUE if there is no preset limit */
		long maxMemory = Runtime.getRuntime().maxMemory();
		/* Maximum amount of memory the JVM will attempt to use */
		LOGGER.info("Maximum memory (mb): {}", (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory/1000000));
	}

}
