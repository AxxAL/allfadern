package net.axxal.allfadern.commands.fourchan;

import com.google.common.collect.Lists;
import net.axxal.allfadern.commands.Command;
import net.axxal.allfadern.exceptions.CommandException;
import net.axxal.allfadern.fourchan.FourChanPost;
import net.axxal.allfadern.fourchan.FourChanTools;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DumpThreadMediaCommand extends Command {

    public DumpThreadMediaCommand() {
        super("dumpthreadmedia");
        requiredArgs = 2;
    }

    @Override
    public void run(MessageReceivedEvent event) throws CommandException {

        String board = args.get(0);
        String threadId = args.get(1);

        List<String> mediaUrls = new ArrayList<>();
        try {
            // Adds all posts with the media element to the list.
            for (FourChanPost post : FourChanTools.fetchAllPosts(board, threadId)) {
                if (post.mediaUrl == null) continue;
                mediaUrls.add(post.mediaUrl);
            }
        } catch (IOException e) {
            throw new CommandException("Could not find thread.");
        }

        // Discord embeds don't work for messages with more than 5 media elements.
        if (mediaUrls.size() > 5) {
            // Get list of media urls and turn them into 5 element lists.
            List<List<String>> mediaChunks = Lists.partition(mediaUrls, 5);

            // Loop through chunk list.
            for (List<String> chunk : mediaChunks) {
                String message = "";

                // Truncate each string to message.
                for (String mediaUrl : chunk) {
                    message += mediaUrl + "\n";
                }

                // Send message with chunk content.
                event.getChannel().sendMessage(message).queue();
            }
        } else {
            // Sends message without splitting content into smaller lists.
            String message = "";
            for (String mediaUrl : mediaUrls) {
                message += mediaUrl + "\n";
            }
            event.getChannel().sendMessage(message).queue();
        }

        event.getChannel().sendMessage(String.format("Fetched %d media files.", mediaUrls.size())).queue();
    }
}
