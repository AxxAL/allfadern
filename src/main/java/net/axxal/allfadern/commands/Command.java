package net.axxal.allfadern.commands;

import net.axxal.allfadern.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class Command {
    protected final String label;
    protected List<String> args = new ArrayList<>();

    protected int requiredArgs = 0;
    protected String helpPageUrl = "https://github.com/AxxAL/allfadern/wiki/Command-usage";

    public Command(String label) {
        this.label = label;
    }

    public boolean handle(MessageReceivedEvent event) throws CommandException {
        if (!event.getMessage().getContentRaw().toLowerCase().startsWith("a!" + label)) return false;
        args = parseMessageContent(event.getMessage().getContentRaw());

        if (args.size() < requiredArgs)
            throw new CommandException("Too few arguments provided.");

        run(event);
        return true;
    }

    private List<String> parseMessageContent(String messageContent) {
        messageContent = messageContent.toLowerCase();
        String[] splitContent = messageContent.trim().split("\\s+");
        return List.of(Arrays.copyOfRange(splitContent, 1, splitContent.length));
    }

    protected abstract void run(MessageReceivedEvent event) throws CommandException;

    public String getHelpPageUrl() {
        return helpPageUrl;
    }
}
