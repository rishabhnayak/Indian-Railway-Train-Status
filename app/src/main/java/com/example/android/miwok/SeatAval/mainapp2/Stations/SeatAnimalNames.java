package com.example.android.miwok.SeatAval.mainapp2.Stations;

/**
 * Created by sahu on 5/6/2017.
 */


public class SeatAnimalNames {
    String animalName;
     String animalNo;
    public SeatAnimalNames(String animalName, String animalNo) {
        this.animalName = animalName;
        this.animalNo=animalNo;
    }
public SeatAnimalNames(){

}
    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public void setAnimalNo(String animalNo) {
        this.animalNo = animalNo;
    }

    public String getAnimalName() {
        return this.animalName;
    }

    public String getAnimalNo() {
        return animalNo;
    }
}