package net.axxal.allfadern.listeners;

import net.axxal.allfadern.Bot;
import net.axxal.allfadern.commands.Command;
import net.axxal.allfadern.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        for (Command command : Bot.getCommands()) {
            try {
                if (command.handle(event)) return;
            } catch (CommandException e) {
                event.getChannel().sendMessage(
                        String.format("Error! %s\nSee command usage here: %s",
                                e.getMessage(),
                                command.getHelpPageUrl()
                        )
                ).queue();
            }
        }
    }
}
