package packagemodelo;

public class BloqueFactory {

	private static BloqueFactory miBloqueF;
	
	private BloqueFactory() {
	}
	
	public static BloqueFactory getBloqueFactory() {
		if (miBloqueF == null) {
			miBloqueF = new BloqueFactory();
		}
		return miBloqueF;
	}
	
	public Bloque crearBloque(int tipo){
		Bloque b = null;
		if (tipo==1) {
			b = new Bloque1();
		}
		if (tipo==2) {
			b = new Bloque2();
		}
		if (tipo==3) {
			b = new Bloque3();
		}
		return b;
	}
}
