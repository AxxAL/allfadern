package net.axxal.allfadern.commands.misc;

import net.axxal.allfadern.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

public class CurrentWeekCommand extends Command {

    public CurrentWeekCommand() {
        super("week");
    }

    @Override
    public void run(MessageReceivedEvent event) {
        LocalDate date = LocalDate.now();
        int weekOfYear = date.get(WeekFields.ISO.weekOfYear());
        event.getChannel().sendMessage(String.format("Current week number is %d. ", weekOfYear)).queue();
    }
}
