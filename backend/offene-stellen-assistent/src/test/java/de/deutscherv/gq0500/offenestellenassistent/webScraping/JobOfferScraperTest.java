package de.deutscherv.gq0500.offenestellenassistent.webScraping;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.models.JobOffer;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobLinkScraper;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobOfferScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class JobOfferScraperTest {

    @Autowired
    JobOfferScraper jobOfferScraper;

    @Autowired
    JobLinkScraper jobLinkScraper;

    @Test
    public void jobOfferScraperSpecificTest() {
        JobOffer jobOffer = jobOfferScraper.scrapeOpenJobOffer("https://drv-bund-karriere.de/jobs/oracle-administrator-berlin");
        System.out.println(jobOffer);

        assertThat(jobOffer.getTitle(), is("Oracle Administrator (m/w/div)"));
        assertThat(jobOffer.getLocation(), is("Berlin"));
        assertThat(jobOffer.getEntryDate(), is("Zum nächstmöglichen Zeitpunkt"));
        assertThat(jobOffer.getApplicationDeadline(), is("23. November 2025"));
        assertThat(jobOffer.getCompensation(), is("Entgeltgruppe 11 TV EntgO-DRV\n(51.614,46-76.482,43 Euro)"));
        assertThat(jobOffer.getTypeOfEmployment(), is("Teilzeit (unbefristet), Vollzeit (unbefristet)"));
        assertThat(jobOffer.getTenderNumber(), is("09-086-2025"));

        assertThat(jobOffer.getTasks(), is("Mit einem ganzheitlichen Blick gestalten Sie die hochverfügbaren Oracle Datenbanken für mehrere Rechenzentren, dazu gehören:\n- Planen und Konzeptionieren der Einführung neuer Oracle-Produkte und Erstellen der entsprechenden Dokumentationen\n- Programmieren von Skripten zur Automatisierung von Aufgaben im Datenbankumfeld\n- Unterstützen bei der Fehleranalyse sowie des regelmäßigen Patchings unserer Oracle Datenbanken und Produkte\n- Betreuen und Initiieren von Teststellungen"));
        assertThat(jobOffer.getAreaOfActivity(), is("Der Bereich Database Management ist für den Datenbankbetrieb der Rechenzentren der Deutschen Rentenversicherung verantwortlich. Unser Team Database Transition verantwortet hierbei die Planung und Konzeptionierung der Oracle Datenbanken für alle Rentenversicherungsträger. Als zentraler Bestandteil der IT-Architektur übernehmen wir eine wichtige Rolle, um eine reibungslose Bearbeitung von Rentenvorgängen zu ermöglichen. Wir freuen uns auf neue Kolleg*innen im Team am Standort Berlin."));
        assertThat(jobOffer.getApplicationProfile(), is("- Eine abgeschlossene Hochschulbildung (Bachelor, Diplom) im IT-Bereich oder eine gleichwertige Qualifikation, z.B. eine IT-spezifische Qualifizierung, eine abgeschlossene Berufsausbildung als Fachinformatiker*in oder vergleichbare praktische Erfahrungen im IT-Bereich\n- Fundierte Kenntnisse in Oracle RDBMS sowie den dazugehörigen Produkten\n- Mehrjährige praktische Erfahrung in Datenbankadministration\n- Kenntnisse und Erfahrungen im Erkennen, Analysieren und Beheben von Fehlerzuständen in Oracle Datenbanken sowie im Umgang mit Betriebssystemen (Unix / Linux, Shell)\n- Idealerweise bringen Sie bereits Erfahrung mit dem Betrieb von hochverfügbaren Datenbanken auf Engineered Systems / Exadata genauso wie Erfahrungen mit Automatisierungstools (zum Beispiel Ansible und Gitlab) mit\n- Wünschenswert sind außerdem Erfahrungen in der Projektarbeit und mit konzeptioneller Textgestaltung\n- Eigeninitiative und Kreativität beim Aufbau neuer Strukturen sowie ausgeprägte Team- und Kommunikationsfähigkeiten runden Ihr Profil ab"));
        assertThat(jobOffer.getFurtherInformation(), is("- Zur Besetzung der Position werden wir mit Bewerber*innen, die sich in der engeren Auswahl befinden, Gespräche führen.\n- Als Rechenzentrum der Deutschen Rentenversicherung sind wir Betreiber von kritischer Infrastruktur (KRITIS). Bitte beachten Sie, dass Sie nach einer Einstellungszusage gemäß Sicherheitsüberprüfungsgesetz (§ 9 SÜG) geprüft werden. Diese Sicherheitsüberprüfung darf nicht zum Ergebnis haben, dass ein Sicherheitsrisikovorliegt, das der sicherheitsempfindlichen Tätigkeit entgegensteht (§ 14 SÜG).\n- Für die Tätigkeit sind verhandlungssichere Deutschkenntnisse in Wort und Schrift erforderlich.\n- Diese Stellenausschreibung bezieht sich auf einen Bereich, in dem Frauen im Sinne des Bundesgleichstellungsgesetzes unterrepräsentiert sind. Die Deutsche Rentenversicherung Bund hat sich die berufliche Förderung von Frauen zum Ziel gesetzt. Wir sehen daher Bewerbungen von Frauen mit besonderem Interesse entgegen.\n- Menschen mit einer Schwerbehinderung oder ihnen Gleichgestellte im Sinne von § 2 Abs. 2 und 3 SGB IX werden bei gleicher Eignung bevorzugt berücksichtigt.\n- Begrüßt werden Bewerbungen von Menschen aller Nationalitäten.\nWir bieten Ihnen\n- Alle Vorteile einer großen öffentlichen Arbeitgeberin inklusive fach- und kompetenzorientierter Fort- und Weiterbildungsmöglichkeiten sowie nachhaltiger Entwicklungs- und Aufstiegsmöglichkeiten\n- Eine strukturierte Einarbeitung in ein spannendes Aufgabenfeld mit der Möglichkeit für eigenverantwortliches Gestalten und fachlichen Austausch im kollegialen Team\n- Familienfreundliche und serviceorientierte Arbeitszeitmodelle ergänzt durch unsere Serviceangebote und Kooperationspartner*innen zur Vereinbarkeit von Beruf & Familie für Eltern oder pflegende Angehörige\n- Flexible Möglichkeiten für die Arbeit auch im Homeoffice – nach erfolgreicher Einarbeitung\n- Die Gesundheit unserer Beschäftigten ist uns wichtig - wir unterstützen die praktische Umsetzung des Betrieblichen Gesundheitsmanagements in den Arbeitsalltag"));
    }

    @Test
    public void setJobOfferScraperAllTest() {
        List<String> links = jobLinkScraper.fetchAllListingLinks();
        links.forEach(link -> jobOfferScraper.scrapeOpenJobOffer(link));
    }
}
