# Use the official image as a parent image.
FROM maven:3-openjdk-11

# Set the working directory.
WORKDIR /tmp/

# Copy the rest of your app's source code from your host to your image filesystem.
COPY target/*.jar DiscordBot.jar

# Run the specified command within the container.
CMD [ "java", \
        "-jar", \
        "DiscordBot.jar"]