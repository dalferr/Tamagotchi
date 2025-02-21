package modelo;

public class Cucharada implements Accion{

	@Override
	public int incrementar() {
		// TODO Auto-generated method stub
		System.out.println("Has dado una cucharada");
		Tamagotchi tama = Tamagotchi.getTamagotchi();
		tama.darSopa();
		return 0;
	}

}
