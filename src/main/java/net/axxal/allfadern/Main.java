package net.axxal.allfadern;

public class Main {
    public static void main(String[] args) {
        Configuration.setProperties();

        Bot.start(Configuration.getDiscordToken());
    }
}
