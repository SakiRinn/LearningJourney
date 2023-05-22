package uk.qmul.learningjourney;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.lang.reflect.Field;
import java.time.chrono.IsoChronology;
import java.util.Map;

public class Position {
    public String name;
    public String date;
    public Boolean isCreditable;

    public void setName(String name){
        this.name = name;
    }

    public String getName() { return name;}

    public void setDate(String date){ this.date = date; }

    public String getDate(){ return date; }

    public void setIsCreditable(Boolean is){ this.isCreditable = is; }

    public Boolean getIsCreditable(){ return isCreditable; }


    public Position(){
        this.date = null;
        this.name = null;
        this.isCreditable = null;
    }

    public Position(String name, String date, Boolean isCreditable) {
        this.date = date;
        this.name = name;
        this.isCreditable = isCreditable;
    }

    @JsonCreator
    public Position(Map<String, Object> property) {
        try {
            for (Field field: this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(this, property.get(field.getName()));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}


