package com.othr.vs.parkhaus.lager;

import java.util.Random;

public class Auto {
    private String kennzeichnen;

    public Auto(String kennzeichnen) {
        this.kennzeichnen = kennzeichnen;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "kennzeichnen='" + kennzeichnen + '\'' +
                '}';
    }

    public String getKennzeichnen() {
        return kennzeichnen;
    }

}
