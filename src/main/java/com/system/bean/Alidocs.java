package com.system.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by yjl on 2019-03-13.
 */
public class Alidocs {
    @Field(value="id")
    private String id;

    @Field(value="title")
    private String title;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Alidocs{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
