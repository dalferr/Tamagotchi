package modelo;

public class CagadoEnfermado implements State2{
	public int getScore() {
		return -10;
	}
	public int getVida() {
		return -12;
	}
	public int getHambre() {
		return 15;
	}
	public boolean getCagado() {
		return true;
	}
	public boolean getEnfermado() {
		return true;
	}
}
