package modelo;

public class Enfermado implements State2{
	
	public int getScore() {
		return -5;
	}
	public int getVida() {
		return -7;
	}
	public int getHambre() {
		return 5;
	}
	public boolean getCagado() {
		return false;
	}
	public boolean getEnfermado() {
		return true;
	}
}
