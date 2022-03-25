package net.axxal.allfadern;

public class Main {
    public static void main(String[] args) {
        Configuration.loadProperties();

        Bot.start(Configuration.getDiscordToken());
    }
}
