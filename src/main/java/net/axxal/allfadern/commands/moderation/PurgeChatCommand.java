package net.axxal.allfadern.commands.moderation;

import net.axxal.allfadern.commands.Command;
import net.axxal.allfadern.exceptions.CommandException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PurgeChatCommand extends Command {

    public PurgeChatCommand() {
        super("purge");
        requiredArgs = 1;
    }

    @Override
    public void run(MessageReceivedEvent event) throws CommandException {
        int purgeAmount;

        try {
            purgeAmount = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("Please provide a valid number.").queue();
            return;
        }

        List<Message> messagesToBePurged = event.getChannel().getHistory().retrievePast(purgeAmount).complete();

        event.getChannel().purgeMessages(messagesToBePurged);
        event.getChannel().sendMessage(String.format("Purged %d messages! This message will self destruct after 5 seconds.", messagesToBePurged.size()))
                .delay(5, TimeUnit.SECONDS)
                .flatMap(Message::delete)
                .queue();
    }
}
