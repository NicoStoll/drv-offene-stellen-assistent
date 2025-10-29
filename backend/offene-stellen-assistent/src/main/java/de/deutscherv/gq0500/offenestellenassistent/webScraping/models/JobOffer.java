package de.deutscherv.gq0500.offenestellenassistent.webScraping.models;

import lombok.Data;

import java.util.Map;

@Data
public class JobOffer {
    String title;

    String location;
    String entryDate;
    String applicationDeadline;
    String compensation;
    String typeOfEmployment; // Vollzeit/Teilzeit
    String tenderNumber;

    String tasks;
    String areaOfActivity;
    String applicationProfile;
    String furtherInformation;

    String link;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new java.util.LinkedHashMap<>();

        if (title != null) map.put("title", title);
        if (location != null) map.put("location", location);
        if (entryDate != null) map.put("entryDate", entryDate);
        if (applicationDeadline != null) map.put("applicationDeadline", applicationDeadline);
        if (compensation != null) map.put("compensation", compensation);
        if (typeOfEmployment != null) map.put("typeOfEmployment", typeOfEmployment);
        if (tenderNumber != null) map.put("tenderNumber", tenderNumber);
        if (tasks != null) map.put("tasks", tasks);
        if (areaOfActivity != null) map.put("areaOfActivity", areaOfActivity);
        if (applicationProfile != null) map.put("applicationProfile", applicationProfile);
        if (furtherInformation != null) map.put("furtherInformation", furtherInformation);
        if (link != null) map.put("link", link);

        return map;
    }
}
