package net.axxal.allfadern.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class Command {
    protected final String label;
    protected List<String> args = new ArrayList<>();

    public Command(String label) {
        this.label = label;
    }

    public boolean handle(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().toLowerCase().startsWith("a!" + label)) return false;
        args = parseMessageContent(event.getMessage().getContentRaw());
        run(event);
        return true;
    }

    private List<String> parseMessageContent(String messageContent) {
        messageContent = messageContent.toLowerCase();
        String[] splitContent = messageContent.trim().split("\\s+");
        return List.of(Arrays.copyOfRange(splitContent, 1, splitContent.length));
    }

    public abstract void run(MessageReceivedEvent event);
}
