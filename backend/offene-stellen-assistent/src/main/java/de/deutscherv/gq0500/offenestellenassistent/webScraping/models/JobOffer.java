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
        return Map.ofEntries(Map.entry("title", title), Map.entry("location", location),
                             Map.entry("entryDate", entryDate), Map.entry("applicationDeadline", applicationDeadline),
                             Map.entry("compensation", compensation), Map.entry("typeOfEmployment", typeOfEmployment),
                             Map.entry("tenderNumber", tenderNumber), Map.entry("tasks", tasks),
                             Map.entry("areaOfActivity", areaOfActivity),
                             Map.entry("applicationProfile", applicationProfile),
                             Map.entry("furtherInformation", furtherInformation), Map.entry("link", link));
    }
}
