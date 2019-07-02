package edu.buffalo;

public class Main {
	
	/**
	 * This is the driver class. It launches the application.
	 * @author Miki Padhiary
	 *
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ReservationHandler();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
