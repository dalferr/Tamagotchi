package modelo;

public class Bien implements State2{
	public int getScore() {
		return 1;
	}
	public int getVida() {
		return 0;
	}
	public int getHambre() {
		return 0;
	}
	public boolean getCagado() {
		return false;
	}
	public boolean getEnfermado() {
		return false;
	}
}
