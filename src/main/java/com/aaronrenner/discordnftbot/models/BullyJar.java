package com.aaronrenner.discordnftbot.models;

import java.time.Instant;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(includeFieldNames = true)
public class BullyJar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long    id;
	private long    discordId;
	private String  discordUser;
	private Instant lastUpdated;
	private Long    counter;
	
}
