package packagemodelo;

import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import modelo.Tamagotchi;

@SuppressWarnings("deprecation")
public class Tablero extends Observable{

	private static Tablero miTablero = new Tablero();
	private int[][] matrizCasillas = new int[8][12]; 
	private int filaTama;
	private int colTama;
	private int filaBollo;
	private int colBollo;
	private Timer timer;
	private int tiempo;
	private Bloque[][] matrizBloques = null;

	
	private Tablero() {
		tiempo=0;
	}
	
	
	public static Tablero getTablero() {
		return miTablero;
	}
	
	public void crearTablero(int filas, int col) {
		matrizBloques = new Bloque[filas][col];
		Random gen = new Random();
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < col; j++) {
				int tipo = gen.nextInt(3) + 1;
				Bloque bloque = BloqueFactory.getBloqueFactory().crearBloque(tipo);
				matrizBloques[i][j] = bloque;
				System.out.println(bloque.getDureza()+ " ");
				Object[] objectArray = new Object[] {"A�adirCasilla",i, j, bloque.getDureza()};
				matrizCasillas[i][j] = bloque.getDureza();
				setChanged();
				notifyObservers(objectArray);
			}
			System.out.println();
		}
	}
	
	public void crearBollo() {
		Random random = new Random();
		filaBollo = random.nextInt(7) + 1;  
		colBollo = random.nextInt(11) + 1;
		while (TamaMini.getTamaMini().getFila()==filaBollo && TamaMini.getTamaMini().getColumna()==colBollo) {
			filaBollo = random.nextInt(7) + 1;  
			colBollo = random.nextInt(11) + 1;
		}
		int dureza = matrizCasillas[filaBollo][colBollo];
		Object[] objectArray = new Object[] {"A�adirBollo",filaBollo,colBollo,dureza};
		System.out.println(filaBollo+":"+colBollo);
		setChanged();
		notifyObservers(objectArray);
	}
	
	public void romperCasillas(int fila, int col) {
		Object[] objectArray = new Object[4];
		
		
		if (matrizCasillas[fila][col] == 3) {
			objectArray[0]="Cambiar";
			matrizCasillas[fila][col]  = 2;
			objectArray[1] = fila;
			objectArray[2] = col;
			objectArray[3] = 2;
		}
		
		else if (matrizCasillas[fila][col]  == 2) {
			objectArray[0]="Cambiar";
			matrizCasillas[fila][col]  = 1;
			objectArray[1] = fila;
			objectArray[2] = col;
			objectArray[3] = 1;
		}
		
		else if ((matrizCasillas[fila][col]  == 1) && esTama(fila, col)) {
			objectArray[0]="Cambiar";
			matrizCasillas[fila][col]  = -1;
			objectArray[1] = fila;
			objectArray[2] = col;
			objectArray[3] = -1;
			filaTama = fila;
			colTama = col;
		}
		else if ((matrizCasillas[fila][col]  == 1) && esBollo(fila, col)) {
			objectArray[0] ="A�adirBollo";
			matrizCasillas[fila][col]  = -1;
			objectArray[1] = fila;
			objectArray[2] = col;
			objectArray[3] = -1;
		}
		else if ((matrizCasillas[fila][col]  == -1)) {
			objectArray[0]="Cambiar";
			objectArray[1] = 0;
			objectArray[2] = 0;
			objectArray[3] = 0;
		}
		
		else {
			objectArray[0]="Cambiar";
			matrizCasillas[fila][col]  = 0;
			objectArray[1] = fila;
			objectArray[2] = col;
			objectArray[3] = 0;
		}
		setChanged();
		notifyObservers(objectArray);
	}
	
	public void gestionarCasillas(Object pCasilla) {
		
		if (pCasilla.equals("ActCoordenadasTama")) {
			Object[] objectArray = new Object[] {"ActTama", filaTama, colTama, 0, 0};
			matrizCasillas[filaTama][colTama] = 0;
			int fila = TamaMini.getTamaMini().getFila();
			int col = TamaMini.getTamaMini().getColumna();
			
			if(esBollo(fila,col)) {
				Tamagotchi.getTamagotchi().sumarPuntuacion(20);
				String mensaje = "HAS GANADO";
				pararCont();
				setChanged();
				notifyObservers(mensaje);
				Tamagotchi.getTamagotchi().iniciarCont();
			}
			
			else if (matrizCasillas[fila][col]==0) {
				filaTama = fila;
				colTama = col;
				matrizCasillas[filaTama][colTama] = -1;
				objectArray[3] = filaTama;
				objectArray[4] = colTama;	
			}
			else {
				objectArray[0] = "NoPintarNuevo";
			}
			
			setChanged();
			notifyObservers(objectArray);
			
		}
	}
	
	public void cerrarMiniJuego() {
		
		String mensaje = null;
		mensaje="Exit";
		System.out.println("Exit manin");
		pararCont();
		Tamagotchi.getTamagotchi().iniciarCont();
		setChanged();
		notifyObservers(mensaje);

	}
	
	private boolean esTama(int pFila, int pCol) {
		return (TamaMini.getTamaMini().getFila()==pFila) && (TamaMini.getTamaMini().getColumna()==pCol);
	}
	private boolean esBollo(int pFila, int pCol) {
		return (filaBollo==pFila) && (colBollo==pCol);
	}
	
	public int getDurezaCasilla(int fila, int columna) {
	    return matrizCasillas[fila][columna];
	}
	
	public void iniciarCont() 
	{
		//TODO (timer de 20s)
		tiempo=0;
		TimerTask crono = new TimerTask()
		{
			
			@Override
			public void run() {
				//metodo
				cuentaAtras();
			}

			private void cuentaAtras() {
				tiempo++;
				if (tiempo==20)
				{
					pararCont();
					//hay q poner q envie msj
					String mensaje = "Tiempo";
					Tamagotchi.getTamagotchi().sumarPuntuacion(-20);
					setChanged();
					notifyObservers(mensaje);
					Tamagotchi.getTamagotchi().iniciarCont();
				}
			}
		};
				
		timer = new Timer();
		timer.scheduleAtFixedRate(crono, 0, 1000);
	}
	
	public void pararCont() {
		timer.cancel();
	}
	
}