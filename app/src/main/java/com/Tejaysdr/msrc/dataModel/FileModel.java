package com.Tejaysdr.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FileModel implements Serializable {

    public FileModel(String ba1, String closed_img_name) {
       path=ba1;
       name=closed_img_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("path")
    String path;
    @SerializedName("name")
    String name;
}
