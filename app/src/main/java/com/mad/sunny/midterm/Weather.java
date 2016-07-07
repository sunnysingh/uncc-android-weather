package com.mad.sunny.midterm;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sunny on 6/9/16.
 */
public class Weather implements Serializable {

    private String temperature;
    private String humidity;
    private String pressure;
    private ArrayList<String> descriptions;
    private ArrayList<String> icons;

    public void setTemperature(String t) {
        temperature = t;
    }

    public void setHumidity(String h) {
        humidity = h;
    }

    public void setPressure(String p) {
        pressure = p;
    }

    public void setDescriptions(ArrayList<String> d) {
        descriptions = d;
    }

    public void setIcons(ArrayList<String> i) {
        icons = i;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public ArrayList<String> getIcons() {
        return icons;
    }
}
