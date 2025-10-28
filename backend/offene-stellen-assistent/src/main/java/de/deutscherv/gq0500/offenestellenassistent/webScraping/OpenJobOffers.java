package de.deutscherv.gq0500.offenestellenassistent.webScraping;

import lombok.Data;

@Data
public class OpenJobOffers {
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
}
