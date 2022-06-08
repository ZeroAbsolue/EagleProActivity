package com.example.EagleActivities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Activity {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String description;
    private LocalDateTime start_date;
    private LocalDateTime end_date;


    Activity(String type, String description, LocalDateTime start_date, LocalDateTime end_date) {
        this.type = type;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Activity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDateTime start_date) {this.start_date = start_date;}

    public LocalDateTime getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Activity activity = (Activity) object;
        return Objects.equals(id, activity.id) && Objects.equals(type, activity.type) && Objects.equals(description, activity.description) && Objects.equals(start_date, activity.start_date) && Objects.equals(end_date, activity.end_date);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id, type, description, start_date, end_date);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", type=" + type +
                ", description=" + description +
                ", startDate=" + start_date +
                ", endDate=" + end_date +
                '}';
    }
}
