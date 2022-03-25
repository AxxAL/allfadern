package net.axxal.allfadern.listeners;

import net.axxal.allfadern.Bot;
import net.axxal.allfadern.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        for (Command command : Bot.getCommands()) {
            if (command.handle(event)) return;
        }
    }
}
