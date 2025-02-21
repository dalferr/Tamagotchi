package packagemodelo;

public abstract class Bloque {
	
	protected Bloque() {}
		
	public void pasarRaton() {
		int d = getDureza()-1;
		if (d==0) {
			destruido();
		}
	}
	
	public void destruido() {
		System.out.println("destruido");
	}
	
	public abstract int getDureza();
	
}
