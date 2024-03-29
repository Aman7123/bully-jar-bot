# Bully Jar Bot
* Description: A Discord bot made with @Javacord and with love from the Bananaz Tech team!
* Version: (Check main for release or develop for dev)
* Creator: Aaron Renner

### Table of Contents
* [Introduction](#introduction)
* Setup *"How to"*
  * [Run Spring-Boot](#running-the-project)
* Help
  * [Setup Libraries and Examples](#libraries)
  
## Introduction

This Java application is built on the Spring-Boot framework! This project interacts with Discord commands or startup objects in the application.yml to keep tract of moments in Discord.

## Setup
### Application.yml Setup
The following document formatting MUST REMAIN THE SAME, replace or add only where noted to!

``` yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://aaronrenner.com:8306/botstuff?createDatabaseIfNotExist=true
    username: USERNAME
    password: PASSWORD
    hikari:
      connectionTimeout: 120000
      idleTimeout: 600000
      # 15 minutes recommended for lifetime
      maxLifetime: 900000
      maximumPoolSize: 25
# Custom
# Custom
nft-bot:
  discord:
    token: <DISCORD-TOKEN>
    commandPrefix: <DISCORD-COMMAND-PREFIX>
```

### Running the Project

Executing the project can be done in two ways, the first is by initializing using Maven which the second produces a traditional Jar file. Before attempting to run the program some setup must be done inside of the [src/main/resources/application.properties](src/main/resources/application.yml), you can follow the guides.

### Build with Maven

If you have Maven installed on your machine you can navigate to the root project directory with this README file and execute the following. Remember to follow the above Database setup procedures first.
```sh
mvn -B -DskipTests clean package
```
You can also use the built in Maven wrapper and execute the project by following this command.
```sh
./mvnw -B -DskipTests clean package
```
### Setting up in IDE

Download Lombok to your IDE or VS Code Extension!

### Creating a Docker Image

To build a container that can execute the application from a safe location you can use my supplied [Dockerfile](Dockerfile) to do so. You should follow the guides first to better understand some of these arguments.

```Dockerfile
CMD [ "java", \
        "-jar", \
        "discord-bot.jar"]
```

## Libraries

### Jump To
* [Required Dependencies](#spring-boot-required)
* [Lombok](#lombok)
* [JSON-Smart](#json-smart)
* [Javacord](#javacord)
* [Web3J](#web3j)

### Spring-Boot Required
<details><summary>Lombok</summary>
* [Lombok - Automated Class Method Generation](https://projectlombok.org/features/all)
```pom
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```
</details>
<details><summary>JSON-Smart</summary>
* [JSON Parser JAVADOC](https://javadoc.io/doc/net.minidev/json-smart/latest/index.html)
```pom
<dependency>
    <groupId>net.minidev</groupId>
    <artifactId>json-smart</artifactId>
</dependency>
```
</details>
<details><summary>Javacord</summary>
* [Javacord - An easy to use multithreaded library for creating Discord bots in Java](https://github.com/Javacord/Javacord/)
```pom
<dependency>
    <groupId>org.javacord</groupId>
    <artifactId>javacord</artifactId>
    <type>pom</type>
</dependency>
```
</details>
<details><summary>Web3J</summary>
* [Web3J - Lightweight Java and Android library for integration with Ethereum clients.](https://github.com/web3j/web3j)
```pom
<dependency>
    <groupId>org.javacord</groupId>
    <artifactId>javacord</artifactId>
    <type>pom</type>
</dependency>
```
</details>
