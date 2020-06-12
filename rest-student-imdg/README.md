# Verteilte Systeme: Im Memory Data Grid

##Aufgabe 1: Distributed Datastructures mit In-Memory-Data-Grid „Hazelcast“

Im vorherigen Übungsblatt nutzten Sie JDBC, um Daten zu Studierenden aus einer
relationalen Datenbank auszulesen (innerhalb der Methode getStudentById).
Für diese Übung wird davon ausgegangen, dass Student-Objekte sehr häufig gelesen
und sehr selten verändert werden. Verfügbarkeit sei nun weit wichtiger als Konsistenz!
Aus diesem Grund soll ein „In Memory Data Grid“ mit Hilfe des Frameworks „Hazelcast“
zwischen allen Webservern (Ihren jeweiligen REST-Applikationen) im Netzwerk
aufgebaut werden.
Sie können für diese Übung den Lösungsvorschlag für die Übung 8 nutzen (siehe GRIPS)
oder alternativ Ihr bestehendes Projekt weiternutzen. In diesem Fall nutzen Sie bitte den
Lösungsvorschlag als Vorlage für die Konfigurationsdatei pom.xml (siehe <repository>
neue <dependency>-Einträge) sowie die main-Methode der Klasse Server und passen
Ihre Inhalte entsprechend den geänderten Details an.

### Ihre Aufgaben:
- Nur falls Sie Ihr bisheriges Projekt weiter nutzen:
Verwenden Sie ab jetzt nicht mehr Ihre eigenen Klassen Student, Prüfungsleistung
usw. sondern importieren und nutzen Sie die Klassen aus dem Package
de.othr.vs.xml. Ersetzen Sie die import-Statements und passen Sie Ihren Code ggf.
an.
(Zur Erklärung: Mit dem In-Memory-Data-Grid wird Ihr CIP-Pool-Rechner und andere
Rechner im Netzwerk jeweils als Node automatisch in ein gemeinsames Grid
zusammengefügt. In diesem Grid werden Objekte ausgetauscht, so dass allen
Objekten auf allen Nodes die selben Class-Dateien zugrunde liegen müssen.)
- Machen Sie sich über die Website http://hazelcast.org mit dem Hazelcast IMDG
vertraut, insbesondere mit den „Java Member Code Samples“, diese zeigen die
wichtigsten Anwendungsfälle für das IMDG.
- Wählen Sie eine passende Datenstruktur entsprechend der obigen „Code Samples“
aus, nennen Sie diese Datenstruktur im Grid "students" (dieser Name ist wichtig,
denn alle beteiligten Nodes verwenden denselben Namen).
Hinweis: Gehen Sie davon aus, dass Objekte auf jeder Node schnellst möglich
gefunden werden sollen.
Sichern Sie in der neuen Datenstruktur neu erzeugte Student-Objekte nach Auslesen
aus der MySQL-Datenbank zusätzlich darin.
    - Diese Datenstruktur soll dieses Objekt für 5 Minuten vorhalten, anschließend
gilt es als veraltet (vgl. „soft state“, dies wird in der put-Methode individuell
angegeben) und soll nicht mehr darin enthalten sein.
- Prüfen Sie beim Abrufen eines Student-Objekts nun, ob zur angeforderten
Matrikelnummer bereits ein Objekt in der verteilten Datenstruktur vorliegt. Falls ja,
geben Sie dieses zurück, eine Abfrage der Datenbank ist in diesem Fall nicht
notwendig. Falls kein Objekt vorhanden ist, lesen Sie es aus der Datenbank aus und
fügen Sie es der verteilten Datenstruktur hinzu.
- Beim Erzeugen neuer Student-Objekte soll die nächste freie Matrikelnummer aus
einer passenden Datenstruktur des Data-Grids gewählt werden (statt durch die
Datenbank). Wählen Sie hierzu eine passende Datenstruktur aus, so dass die Vergabe
der nächsten Matrikelnummer auch „thread-safe“ (im verteilten Sinne) ist.
