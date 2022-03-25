package net.axxal.allfadern;

import net.axxal.allfadern.commands.Command;
import net.axxal.allfadern.commands.PingCommand;
import net.axxal.allfadern.commands.fourchan.DumpThreadMediaCommand;
import net.axxal.allfadern.listeners.CommandListener;
import net.axxal.allfadern.listeners.GuildListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.List;

public class Bot {

    private static JDA jda;
    private static final List<Command> commands = List.of(
            new PingCommand(),
            new DumpThreadMediaCommand()
    );

    public static void start(String token) {
        JDABuilder builder = JDABuilder.createDefault(token)
                .addEventListeners(
                        new CommandListener(),
                        new GuildListener()
                );
        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static JDA getJda() {
        return jda;
    }

    public static List<Command> getCommands() {
        return commands;
    }
}
