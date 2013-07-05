package vl.gui.swing.eigeneKomponenten;

import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import vl.gui.swing.eigeneKomponenten.ButtonProcess;

public class JButtonContainer extends JFrame {

	private TextArea ta = null;
	private boolean running = false;
	private Thread t = null;
	
	public JButtonContainer(String title) {
		super(title);
		
		this.setLayout(new FlowLayout());
		
		ta = new TextArea();
		ta.setSize(400, 200);
		this.add(ta);

		// Unser eigener Button
		JSignalButton sb = new JSignalButton(false, "Go!", "Stop!");
		// Listener hinzufügen, der die Aktivität startet bzw. stoppt
		sb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				running = !running;
				if (running) {
					// neuen Vorgang starten
					t = new Thread(new ButtonProcess(ta));
					t.start();
				}
				else {
					// laufenden Vorgang abbrechen
					t.interrupt();
				}
			}
		});
		this.add(sb);
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		this.pack();
	}

	
	public static void main(String[] args) {
		JButtonContainer myFrame = new JButtonContainer("Unterbrechbarer Ablauf");
	}
}
