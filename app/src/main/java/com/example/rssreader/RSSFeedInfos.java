package com.example.rssreader;

import java.util.ArrayList;
import java.util.List;

public class RSSFeedInfos {
    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<String> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public List<String> getImgSourceList() {
        return imgSourceList;
    }

    public void setImgSourceList(List<String> imgSourceList) {
        this.imgSourceList = imgSourceList;
    }

    private List<String> titleList = new ArrayList<>();
    private List<String> descriptionList = new ArrayList<>();
    private List<String> imgSourceList = new ArrayList<>();
}
