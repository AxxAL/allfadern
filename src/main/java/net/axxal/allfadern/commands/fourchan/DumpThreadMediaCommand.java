package net.axxal.allfadern.commands.fourchan;

import net.axxal.allfadern.commands.Command;
import net.axxal.allfadern.fourchan.FourChanPost;
import net.axxal.allfadern.fourchan.FourChanTools;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class DumpThreadMediaCommand extends Command {

    public DumpThreadMediaCommand() {
        super("dumpthreadmedia");
    }

    @Override
    public void run(MessageReceivedEvent event) {

        if (args.size() < 2) {
            event.getChannel().sendMessage("Incorrect usage! Find help at https://allfadern.axxal.net/").queue();
        }

        String board = args.get(0);
        String threadId = args.get(1);

        event.getChannel().sendMessage("Fetching content...")
                .queue(res -> {
                    try {
                        List<FourChanPost> posts = FourChanTools.fetchAllPosts(board, threadId);

                        String message = "";

                        for (FourChanPost post : posts) {
                            if (post.mediaUrl == null) continue;
                            if (message.length() + post.mediaUrl.length() > 2000) break;
                            message += post.mediaUrl + "\n";
                        }
                        res.editMessage(message).queue();
                    } catch (IOException e) {
                        res.editMessage("Failed to fetch content. " +  e.getMessage()).queue();
                    }
                });
    }
}
