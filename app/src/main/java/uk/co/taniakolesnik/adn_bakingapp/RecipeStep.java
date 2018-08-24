package uk.co.taniakolesnik.adn_bakingapp;

/**
 * Created by tetianakolesnik on 23/08/2018.
 */

public class RecipeStep {

    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;


    private RecipeStep(int id, String shortDescription, String description, String videoURL) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(String videoURL) {
        mVideoURL = videoURL;
    }

}
