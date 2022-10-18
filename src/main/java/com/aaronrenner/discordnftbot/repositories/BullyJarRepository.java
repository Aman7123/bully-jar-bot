package com.aaronrenner.discordnftbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aaronrenner.discordnftbot.models.BullyJar;

public interface BullyJarRepository extends JpaRepository<BullyJar, Long> {
	boolean  existsByDiscordId(Long discordId);
	BullyJar getByDiscordId(Long discordId);
}
