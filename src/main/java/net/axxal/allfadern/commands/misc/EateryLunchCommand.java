package net.axxal.allfadern.commands.misc;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.axxal.allfadern.commands.Command;
import net.axxal.allfadern.exceptions.CommandException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

public class EateryLunchCommand extends Command {

    private final String api = "https://lunch.axxal.net";

    public EateryLunchCommand() {
        super("lunch");
    }

    @Override
    protected void run(MessageReceivedEvent event) throws CommandException {
        Gson gson = new Gson();

        // Create HTTP request.
        Request request = new Request.Builder()
                .url(api + "/menu")
                .build();

        // Ready HTTP request.
        Call call = new OkHttpClient().newCall(request);

        // Execute HTTP request.
        Response response;
        JsonObject menuObject;
        try {
            response = call.execute();
            menuObject = gson.fromJson(response.body().string(), JsonObject.class);
        } catch (IOException e) {
            throw new CommandException("Could not contact API.");
        }

        int weekNumber = menuObject.get("weekNumber").getAsInt();
        int year = menuObject.get("year").getAsInt();
        JsonArray days = menuObject.get("days").getAsJsonArray();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Eatery Lunch Menu");
        embedBuilder.setDescription(String.format("Year: %d, Week: %d", year, weekNumber));

        for (JsonElement day : days) {
            String title = day.getAsJsonObject().get("day").getAsString();
            String dishes = "";
            for (JsonElement dish : day.getAsJsonObject().get("dishes").getAsJsonArray())  {
                dishes += dish.getAsString() + "\n";
            }
            embedBuilder.addField(title, dishes, false);
        }

        embedBuilder.setFooter(api);
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setColor(Color.WHITE);

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
