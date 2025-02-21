package packagevista;

import javax.swing.JButton;

public class Casilla extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fila;
	private int col;
	
	public Casilla(int pFila, int pCol) {
		fila = pFila;
		col = pCol;
	}
	
	public int getFila() {
		return fila;
	}
	
	public int getColumna() {
		return col;
	}
	
}
