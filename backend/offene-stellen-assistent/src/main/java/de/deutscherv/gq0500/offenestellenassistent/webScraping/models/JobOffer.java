package de.deutscherv.gq0500.offenestellenassistent.webScraping.models;

import lombok.Data;

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
}
