package vl.gui.swing.eigeneKomponenten;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class JSignalButton extends JButton {

	private static final int BORDER = 7;
	
	private boolean go = false;
	private String startText = null;
	private String stopText = null;
	
	private boolean mouseClickedInCircle = true;
	
	
	public JSignalButton(boolean go, String startText, String stopText) {
		super();
		this.go = go;
		this.startText = startText;
		this.stopText = stopText;
		
		// Minimale und bevorzugte Größe setzen
		// (Die maximale Größe sei uns hier egal.)
		setMinimumSize(new Dimension(40, 40));
		setPreferredSize(new Dimension(60, 60));
		setOpaque(false);

//		// MouseEvents aktivieren (nur AWT, für interne Verarbeitung):
//		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
//		// Anmerkung: Events werden immer dann aktiviert, wenn ein 
//		// entsprechender Listener registriert wird.
//		// MouseEvents verwenden wir ja aber nur für die interne
//		// Prüfung der Gültigkeit von Events (siehe Methoden
//		// processEvent() bzw. checkMouseClick()). Wir müssen also davon 
//		// ausgehen, dass wir keine MouseListener haben.
//		// In Swing sind (anscheinend) die Events automatisch aktiviert,
//		// ich konnte jedenfalls in Swing keine Wirkung der Methode feststellen.
	}

	@Override
	public void paint(Graphics g) {
		// Vom LayoutManager zugewiesene Größe des Buttons ermitteln
		Dimension d = this.getSize();
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();
		
//		// Ein entsprechendes Rechteck löschen 
//		// (bzw. mit der Hintergrundfarbe füllen).
//		// Nur für AWT; in Swing verwende ich setOpaque(), siehe oben.
//		g.clearRect(0, 0, width, height);
		
		// Farbe abhängig vom go-Flag setzen
		// und Button zeichnen
		Color c = go ? Color.GREEN : Color.RED;
		g.setColor(c);
		g.fillArc(0, 0, width, height, 0, 360);
		g.setColor(new Color(200, 200, 200));
		g.fillArc(BORDER, BORDER, width-2*BORDER, height-2*BORDER, 0, 360);
		
		// Button beschriften
		writeLabel(g, width, height);		
	}

	private void writeLabel(Graphics g, int width, int height) {
		// Beschriftung abhängig vom go-Flag wählen
		String text = go ? stopText : startText;
		// Font einstellen
		g.setColor(new Color(0, 0, 0));
		Font myFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
		g.setFont(myFont);
		// Beschriftung anbringen
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int textHeight = fm.getHeight();
		int descent = fm.getDescent();
		g.drawString(text, 
				width / 2 - textWidth / 2, 
				height / 2 + (textHeight / 2 - descent));
	}

	@Override
	protected void processMouseEvent(MouseEvent ev) {
		System.out.println(ev.toString());
		if (ev.getID() == MouseEvent.MOUSE_RELEASED) {
			// Prüfen, ob der MouseEvent innerhalb des Kreises war:
			mouseClickedInCircle = checkMouseClick(ev);
			if (mouseClickedInCircle) {
				// Testausgabe
				System.out.println("Gültiges MouseEvent!");
				// Flag umkehren (grün -> rot bzw. rot -> grün) ...
				go = !go;
				// ... und Button neu zeichnen lassen
				repaint(); 
			} else {
				// Testausgabe
				System.out.println("Ungültiges MouseEvent!");
				// Abbruch, d.h. keine weitere Verarbeitung des Events
				return;
			}
		}
		
		// Allgemeine Event-Verarbeitung für alle "gültigen" Events durchführen.
		// Die Oberklasse JButton sorgt dann dafür, dass aus den gültigen 
		// MouseEvents die gewünschten ActionEvents erzeugt werden.
		super.processMouseEvent(ev);
	}

	private boolean checkMouseClick(MouseEvent me) {
		// War das Event auch innerhalb des Kreises?

		// Koordinaten des Mausklicks
		int mouseX = me.getX();
		int mouseY = me.getY();

		System.out.print("X,Y: " + mouseX + "," + mouseY);

		// Vom LayoutManager zugewiesene Größe des Buttons ermitteln
		Dimension d = this.getSize();
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();

		// Geometrische Berechnung mit Pythagoras: 
		// Klick an (mX, mY) erfolgte im Kreis 
		// mit Radius r an Position (cX, cY):
		// (mX-cX)^2 + (mY-cY)^2 <= r^2
		// anschaulich: deltaX^2 + deltaY^2 <= radius^2
		int radius = width/2;
		int centerX = width/2;
		int centerY = height/2;

		if ( (mouseX-centerX)*(mouseX-centerX) + (mouseY-centerY)*(mouseY-centerY)
				<= radius*radius) {
			System.out.println(" liegt im Kreis.");
			return true;
		}
		else {
			System.out.println(" liegt daneben.");
			return false;
		}
	}
	
	public boolean isGo() {
		return go;
	}
}
