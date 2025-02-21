package modelo;

public class Cagado implements State2{
	public int getScore() {
		return -5;
	}
	public int getVida() {
		return -5;
	}
	public int getHambre() {
		return 10;
	}
	public boolean getCagado() {
		return true;
	}
	public boolean getEnfermado() {
		return false;
	}
}
