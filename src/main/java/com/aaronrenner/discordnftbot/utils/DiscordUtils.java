package com.aaronrenner.discordnftbot.utils;

import java.util.Optional;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class DiscordUtils {
	
	private static final PermissionType ADMIN = PermissionType.ADMINISTRATOR;
	
	public static boolean userHasAdminPerms(Optional<User> user, Optional<Server> server) {
		boolean response = false;
		if(!user.isEmpty() && !server.isEmpty()) {
			User userObj = user.get();
			Server serverObj = server.get();
			if(serverObj.getPermissions(userObj).getAllowedPermission().contains(ADMIN) ||
					userObj.getIdAsString().equals("176355202687959051")  || /* Aaron's validation */
					userObj.getIdAsString().equals("551865831517061120") /* Tim's validation */) {
				response = true;
			}
		}
		return response;
	}

}
