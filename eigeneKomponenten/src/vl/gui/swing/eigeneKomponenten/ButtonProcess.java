package vl.gui.swing.eigeneKomponenten;

import java.awt.TextArea;

public class ButtonProcess implements Runnable {

	private TextArea ta = null;
	
	public ButtonProcess(TextArea textArea) {
		this.ta = textArea;
	}
	
	@Override
	// Die Aktivit‰t, die durch den Button gestartet oder unterbrochen werden kann
	public void run() {
		String text;

		try {
			while (!Thread.currentThread().isInterrupted()) {
				text = ta.getText();
				text += "Fortschritt..." + System.getProperty("line.separator");
				ta.setText(text);
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			// Pause von auﬂen abgebrochen; nichts zu tun
		}
	}
}
