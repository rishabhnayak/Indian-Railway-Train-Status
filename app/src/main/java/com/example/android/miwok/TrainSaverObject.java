package com.example.android.miwok;

import java.util.ArrayList;

/**
 * Created by sahu on 6/8/2017.
 */

public class TrainSaverObject {
    ArrayList<AnimalNames> list;
    public TrainSaverObject(ArrayList<AnimalNames> list) {
       this.list=list;
    }

    public ArrayList<AnimalNames> getList() {
        return list;
    }
}
