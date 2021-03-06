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
    protected final String helpPageUrl = "https://github.com/AxxAL/allfadern/wiki/Command-usage";

    public Command(String label) {
        this.label = label;
    }

    // Check if message event is calling for extending command.
    public boolean handle(MessageReceivedEvent event) throws CommandException {
        // Returns false if message event did not call for extending command.
        if (!event.getMessage().getContentRaw().split("\\s+")[0].toLowerCase().equals(String.format("a!%s", label))) return false;

        // Parses the message arguments.
        parseCommandArguments(event.getMessage().getContentRaw());

        if (args.size() < requiredArgs) {
            throw new CommandException("Too few arguments provided.");
        }

        // Run the command action.
        run(event);
        return true;
    }

    // Parse arguments into argument list.
    private void parseCommandArguments(String messageContent) throws CommandException {
        messageContent = messageContent.toLowerCase();

        // Splits content at every blank space.
        String[] splitContent = messageContent.trim().split("\\s+");

        // Takes content after command request and puts it into the argument list.
        args = List.of(Arrays.copyOfRange(splitContent, 1, splitContent.length));
    }

    protected abstract void run(MessageReceivedEvent event) throws CommandException;

    public String getHelpPageUrl() {
        return helpPageUrl;
    }
}
