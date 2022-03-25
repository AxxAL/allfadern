package net.axxal.allfadern.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping");
    }

    @Override
    public void run(MessageReceivedEvent event) {
        long time = System.currentTimeMillis();
        event.getChannel()
                .sendMessage("Pong!")
                .queue(res ->
                    res.editMessageFormat("Pong! Operation took %dms.", System.currentTimeMillis() - time).queue()
                );
    }
}