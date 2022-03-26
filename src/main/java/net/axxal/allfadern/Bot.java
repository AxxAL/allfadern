package net.axxal.allfadern;

import net.axxal.allfadern.commands.Command;
import net.axxal.allfadern.commands.misc.PingCommand;
import net.axxal.allfadern.commands.fourchan.DumpThreadMediaCommand;
import net.axxal.allfadern.commands.misc.CurrentWeekCommand;
import net.axxal.allfadern.commands.moderation.PurgeChatCommand;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.List;

public class Bot {

    // Register commands to bot.
    private static final List<Command> commands = List.of(
            new PingCommand(),
            new DumpThreadMediaCommand(),
            new PurgeChatCommand(),
            new CurrentWeekCommand()
    );

    // Create bot instance and attempt to start it.
    public static void start(String token) {
        JDABuilder builder = JDABuilder.createDefault(token)
                .addEventListeners(new EventListener());
        try {
            builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static List<Command> getCommands() {
        return commands;
    }
}
