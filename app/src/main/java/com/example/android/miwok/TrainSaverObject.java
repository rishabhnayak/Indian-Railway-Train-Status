package com.example.android.miwok;

import java.util.ArrayList;

/**
 * Created by sahu on 6/8/2017.
 */

public class TrainSaverObject {
    ArrayList<TrainDetailsObj> list;
    public TrainSaverObject(ArrayList<TrainDetailsObj> list) {
       this.list=list;
    }

    public ArrayList<TrainDetailsObj> getList() {
        return list;
    }
}
