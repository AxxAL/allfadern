package net.axxal.allfadern.fourchan;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FourChanTools {

    private static final String wakarimasenEndpoint = "https://archive.wakarimasen.moe/_/api/chan/";

    public static List<FourChanPost> fetchAllPosts(String board, String threadId) throws IOException {

        // Build endpoint with query parameters.
        String url = HttpUrl.parse(wakarimasenEndpoint + "/thread/").newBuilder()
                .addQueryParameter("board", board)
                .addQueryParameter("num", threadId)
                .build()
                .toString();

        // Create HTTP request.
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Ready HTTP request.
        Call call = new OkHttpClient().newCall(request);

        List<FourChanPost> postList = new ArrayList<>();

        Response response = call.execute();

        Gson gson = new Gson();

        if (!response.isSuccessful()) {
            throw new IOException("Request was unsuccessful.");
        }

        JsonObject threadObject = gson.fromJson(response.body().string(), JsonObject.class);

        if (threadObject.has("error")) {
            throw new IOException("Thread was not found.");
        }

        JsonObject thread = threadObject.entrySet().stream().findFirst().get().getValue().getAsJsonObject();

        JsonObject op = thread.get("op").getAsJsonObject();
        JsonObject posts = thread.get("posts").getAsJsonObject();

        postList.add(FourChanPost.parseJsonPost(op));
        for (var post : posts.entrySet()) {
            postList.add(FourChanPost.parseJsonPost(post.getValue().getAsJsonObject()));
        }

        return postList;
    }

}
