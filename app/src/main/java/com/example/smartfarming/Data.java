package com.example.smartfarming;

import com.example.smartfarming.model.Datastreams;

import java.util.List;

public class Data {
    private int count;
    private List<Datastreams> datastreams;
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setDatastreams(List<Datastreams> datastreams) {
        this.datastreams = datastreams;
    }
    public List<Datastreams> getDatastreams() {
        return datastreams;
    }
}
