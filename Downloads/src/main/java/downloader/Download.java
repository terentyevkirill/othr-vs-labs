package downloader;


import javax.swing.JProgressBar;

// aktive Klasse
public class Download
{
	
	private final JProgressBar balken;
    // weitere Attribute zur Synchronisation hier definieren
    
	public Download(JProgressBar balken /* ggf. weitere Objekte */) {
		this.balken = balken;
        // ...
    }


    /*  hier die Methode definieren, die jeweils den Balken weiterschiebt
     *  Mit balken.getMaximum() bekommt man den Wert fuer 100 % gefuellt
     *  Mit balken.setValue(...) kann man den Balken einstellen (wieviel gefuellt) dargestellt wird
     *  Setzen Sie den value jeweils und legen Sie die Methode dann für eine zufaellige Zeit schlafen
     */


}
