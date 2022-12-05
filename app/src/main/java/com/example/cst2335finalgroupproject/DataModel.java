package com.example.cst2335finalgroupproject;

import android.os.Parcel;
import android.os.Parcelable;

/*
* this data is retrieved from Volley, we need to define these data types to use it in the UI
* this class encapsulates each of the image attributes into a datamodel object
* Parcelable makes it possible to transfer a datamodel to a fragment
 */
public class DataModel implements Parcelable {

    private long id;
    private String photographer;
    private String imageUrl;
    private String smallImageUrl;
    private int width;
    private int height;

    public DataModel() {
    }

    //every image from Pexel has a datamodel and use this constructor to fill the fields
    public DataModel(long id, String photographer, String imageUrl, String smallImageUrl, int width, int height) {
        this.id = id;
        this.photographer = photographer;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.width = width;
        this.height = height;
    }

    protected DataModel(Parcel in) {
        id = in.readLong();
        photographer = in.readString();
        imageUrl = in.readString();
        smallImageUrl = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    //individual setters and getters in case we want to change a specific field
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(photographer);
        dest.writeString(imageUrl);
        dest.writeString(smallImageUrl);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
