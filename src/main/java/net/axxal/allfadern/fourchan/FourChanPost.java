package net.axxal.allfadern.fourchan;

import com.google.gson.JsonObject;

public class FourChanPost {

    public String boardName = null;
    public String boardNameShort = null;
    public String posterNum = null;
    public boolean isOp = false;
    public String name = null;
    public String tripCode = null;
    public String comment = null;
    public String posterHash = null;
    public String posterCountry = null;
    public boolean isDeleted = false;
    public String mediaFilename = null;
    public int mediaWidth = 0;
    public int mediaHeight = 0;
    public int mediaSize = 0;
    public String mediaUrl = null;


    public static FourChanPost parseJsonObject(JsonObject jsonPost) {
        FourChanPost parsedPost = new FourChanPost();

        parsedPost.boardName = jsonPost.get("board").getAsJsonObject()
                .get("name").getAsString();
        parsedPost.boardNameShort = jsonPost.get("board").getAsJsonObject()
                .get("shortname").getAsString();
        parsedPost.posterNum = jsonPost.get("num").getAsString();
        parsedPost.isOp = jsonPost.get("op").getAsString().equals("1");
        parsedPost.name = jsonPost.get("name").getAsString();

        if (!jsonPost.get("trip").isJsonNull()) {
            parsedPost.tripCode = jsonPost.get("trip").getAsString();
        }

        if (!jsonPost.get("comment").isJsonNull()) {
            parsedPost.comment = jsonPost.get("comment").getAsString();
        }

        if (!jsonPost.get("poster_hash").isJsonNull()) {
            parsedPost.posterHash = jsonPost.get("poster_hash").getAsString();
        }

        if (!jsonPost.get("poster_country").isJsonNull()) {
            parsedPost.posterCountry = jsonPost.get("poster_country").getAsString();
        }

        parsedPost.isDeleted = jsonPost.get("deleted").getAsString().equals("1");

        if (!jsonPost.get("media").isJsonNull()) {
            JsonObject media = jsonPost.getAsJsonObject("media");
            parsedPost.mediaFilename = media.get("media_filename").getAsString();
            parsedPost.mediaHeight = media.get("media_h").getAsInt();
            parsedPost.mediaWidth = media.get("media_w").getAsInt();
            parsedPost.mediaSize = media.get("media_size").getAsInt();
            parsedPost.mediaUrl = media.get("media_link").getAsString();
        }
        return parsedPost;
    }

}
