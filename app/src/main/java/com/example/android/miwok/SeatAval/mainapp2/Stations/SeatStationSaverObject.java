package com.example.android.miwok.SeatAval.mainapp2.Stations;

import java.util.ArrayList;

/**
 * Created by sahu on 6/8/2017.
 */

public class SeatStationSaverObject {
    ArrayList<SeatAnimalNames> list;
    public SeatStationSaverObject(ArrayList<SeatAnimalNames> list) {
       this.list=list;
    }

    public ArrayList<SeatAnimalNames> getList() {
        return list;
    }
}
