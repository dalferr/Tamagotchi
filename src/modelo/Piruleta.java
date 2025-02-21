package modelo;

public class Piruleta implements Accion{

	@Override
	public int incrementar() {
		// TODO Auto-generated method stub
		System.out.println("Has dado una piruleta");
		Tamagotchi tama = Tamagotchi.getTamagotchi();
		tama.darCandy();
		return 0;
	}

}
