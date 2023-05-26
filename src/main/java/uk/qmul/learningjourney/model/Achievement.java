package uk.qmul.learningjourney.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Define Achievement Entity
 *
 * @author Chenxu Shi
 */
public class Achievement {
    /**
     * achievement name
     */
    public String name;
    /**
     * archived date
     */
    public String date;
    /**
     * whether the achievement is academically creditable
     */
    public Boolean isCreditable;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setIsCreditable(Boolean is) {
        this.isCreditable = is;
    }

    public Boolean getIsCreditable() {
        return isCreditable;
    }

    public Achievement() {
        this.date = null;
        this.name = null;
        this.isCreditable = null;
    }

    public Achievement(String name, String date, Boolean isCreditable) {
        this.date = date;
        this.name = name;
        this.isCreditable = isCreditable;
    }

    @Override
    public String toString(){
        return name+","+date+","+isCreditable;
    }

    @JsonCreator
    public Achievement(Map<String, Object> property) {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(this, property.get(field.getName()));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}


