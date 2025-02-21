package modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


@SuppressWarnings("deprecation")
public class Tamagotchi extends Observable{
	
	private String nombre;
	private int vida;
	private int hambre;
	private Timer timer;
	private int cont;
	private int score;
	private boolean muerto;
	private boolean ultima;
	private static Tamagotchi miTamagotchi = new Tamagotchi();
	private int sopas;
	private int candys;
	private static TimerTask segundos;
	private boolean enMiniJuego;
	private State state;
	private State2 state2;
	private Composite comp;
	
	private Tamagotchi() {
		vida = 40;
		hambre = 40;
		cont = 0;
		score = 0;
		muerto = false;
		ultima=false;
		enMiniJuego = false;
		sopas=0;
		candys=0;
		state = new Egg();
		state2 = new Bien();
		comp = new Composite();
		}
	
	public static Tamagotchi getTamagotchi() {
		return miTamagotchi;	}
	
	public void iniciarCont() 
	{
		segundos = new TimerTask()
		{
			
			@Override
			public void run() {
				if (!muerto && !enMiniJuego) {
					actualizarTama();
				}
			}
		};
				
		timer = new Timer();
		timer.scheduleAtFixedRate(segundos, 0, 1000);
		enMiniJuego = false;
	}
	
	private void pararCont() {
		if(segundos!=null) {
			segundos.cancel();
		}
	}
	
	
	private void actualizarTama() {//se llama a este metodo cada 1 segundo
		cont++;
		if(cont%4==0) //cada 4 segundos
		{
			Random random = new Random();
			double minijuego = random.nextDouble(); //numero al azar para determinar si salta el minijuego
			
			if (minijuego<=0.12)
			{
				String mensaje = "minijuego";
				setChanged();
				notifyObservers(mensaje);
				System.out.println("Minijuego");
				pararCont();
				enMiniJuego = true;
			}
			
			
			if (state instanceof Egg)
			{
				score++;
			}
			else
			{
				if (!(state2 instanceof Cagado)) //determinar si se ha cagado o se ha enfermado, solo se hace en caso de que no este enfermo ni cagado
				{
					//numero al azar para determinar si se ha cagado o se ha enfermado				
					double caca = random.nextDouble();	
					
					if (caca <= 0.2) 
					{
						state2 = new Cagado();
						Object[] objectArray = new Object[] {"Cagado",state2.getCagado()}; 
						setChanged();
						notifyObservers(objectArray);
					}
				}
				
				if (!(state2 instanceof Enfermado)) //determinar si se ha cagado o se ha enfermado, solo se hace en caso de que no este enfermo ni cagado
				{
					//numero al azar para determinar si se ha cagado o se ha enfermado				
					double enfermo = random.nextDouble();			
					if (enfermo <= 0.3) 
					{
							if(state2 instanceof Cagado) {
								state2 = new CagadoEnfermado();
							}
							else {
								state2 = new Enfermado();
							}
							
							Object[] objectArray = new Object[] {"Enfermado",state2.getEnfermado()}; 
							setChanged();
							notifyObservers(objectArray);
					}				
				}
				
				
				//esto es lo del state
				
				int vidaS2 = state2.getVida();
				int hambreS2 = state2.getHambre();
				int scoreS2 = state2.getScore();

				vida = vida + vidaS2;
				hambre = hambre + hambreS2;
				score = score + scoreS2;
			}
		    
			//dependiendo de la evoluciï¿½n cambiamos el hambre y la vida
			actualizarHambreVida();
			aumentarCandys(); //cada 4 segundos se le aumenta un candy
    		aumentarSopas();//cada 4 segundos se le aumenta una sopa
			
			//para visionar en consola lo que va pasando
			System.out.println("cont: "+cont+"| score: "+score+"| evoluciï¿½n: "+state.getEv()+"| hambre: "+hambre+"| vida: "+vida+" CANDYS: "+candys+" SOPAS: "+sopas);
			if (state2 instanceof Cagado)
			{
				System.out.println("cagado");
			}
			
			if (state2 instanceof Enfermado)
			{		
				System.out.println("enfermo");
			}
			
			if (state2 instanceof CagadoEnfermado)
			{		
				System.out.println("cagado y enfermo");
			}
			
			System.out.println(" ");
		}
		
        if ((vida<=0 || hambre<=0))
        {
        	muerto=true;
        	String mensaje = "Muerto"; 
        	System.out.println("MUERTO");
        	pararCont();
     		setChanged();
     		notifyObservers(mensaje);
     		
        }
		
        
        gestionarCandys();
        gestionarSopas();
        evolucionar(); //dependiendo del tiempo que llevemos cambia la evoluciï¿½n
        gestionarCorazones(); //dependiendo de la vida cambia el numero de corazones de la pantalla
        gestionarComida(); //dependiendo del hambre cambia el numero de cuencos de la pantalla
        Object[] objectArray = new Object[] {"Actualizar puntuacion",score}; 
		setChanged();
		notifyObservers(objectArray);
	}
	
	private void gestionarSopas() {
		Object[] objectArray = new Object[] {"Actualizar sopas",sopas}; 
		setChanged();
		notifyObservers(objectArray);
	}

	private void gestionarCandys() {
		Object[] objectArray = new Object[] {"Actualizar candys",candys}; 
		setChanged();
		notifyObservers(objectArray);
	}

	private void aumentarSopas() {
		if (sopas<3)
		{
			sopas++;
		}
		Object[] objectArray = new Object[] {"Actualizar sopas",sopas}; 
		setChanged();
		notifyObservers(objectArray);
	}

	private void aumentarCandys() {
		if (candys<3)
		{
			candys++;
		}
		Object[] objectArray = new Object[] {"Actualizar candys",candys}; 
		setChanged();
		notifyObservers(objectArray);
	}
	
	public void empezarJuego() {
		if (nombre.length()==3) {
			String mensaje;
			reiniciar();
			this.iniciarCont();
			mensaje = "Juego iniciado";
			setChanged();
			notifyObservers(mensaje);
		}
	}
	
	public void cerrarTodo() {
		System.exit(0);
	}
	
	public void cerrarJuego() {
		String mensaje;
		System.out.println("exit");
		this.pararCont();
		int punt = score;
		String nombre = this.nombre;
		mensaje = "Cerrar juego";
		actualizarScores(nombre,punt);
		setChanged();
		notifyObservers(mensaje);
	}
	
	public void darCandy() {
		if (vida==40)
		{
			score = score-5;
			System.out.println("CANDY!--> LLENO --- -5 de score");
		}
		else if (vida>=30)
		{
			vida=40;
			System.out.println("CANDY!--> +3 score --- +10 vida");
		}
		else 
		{
			vida = vida+10;
			System.out.println("CANDY!--> +3 score --- +10 vida");
		}	
	}
	
	public void darSopa() {
		if (hambre==40)
		{
			score = score-5;
			System.out.println("SOUP!--> LLENO --- -5 de score");
		}
		else if (hambre>=30)
		{
			hambre=40;
			System.out.println("SOUP!--> +3 score --- +10 hambre");
		}
		else
		{
			hambre = hambre+10;
			System.out.println("SOUP!--> +3 score --- +10 hambre");
		}
	}
	
	public void gestionarCandySopa(int pA) {
		if (!muerto) 
		{
			if (pA==1) 
			{
				if (candys>0) 
				{
					Piruleta piru = new Piruleta();
					comp.addAccion(piru);
					candys--;
				}
				else 
				{
					System.out.println("NO quedan candys");
				}
			}
			else
			{
				if (sopas>0) 
				{
				Cucharada cuch = new Cucharada();
				comp.addAccion(cuch);
				sopas--;
				}
				else
				{
					System.out.println("No quedan soups");
				}
			}
		}
		else
		{
			System.out.println("Est� muerto capullo no le des nada");
		}
	}
	
	public void darCandySopa() {
		score = score + comp.incrementar();
	}
	
	public void limpiarCaca() {
		String mensaje;
		if (state2 instanceof CagadoEnfermado) {
			state2 = new Enfermado();
		}
		else {
			state2 = new Bien();
		}
		mensaje = "Limpiado";
		System.out.println("se ha limpiadoooooooo");
		setChanged();
		notifyObservers(mensaje);
	}
	
	public void curar() {
		String mensaje;
		if (state2 instanceof CagadoEnfermado) {
			state2 = new Cagado();
		}
		else {
			state2 = new Bien();
		}
		mensaje = "Curado";
		System.out.println("se ha curadoooo siiii");
		setChanged();
		notifyObservers(mensaje);
	}
	
	private void actualizarHambreVida() {
		
		int hambreS = state.getHambre();
		int vidaS = state.getVida();
		
		
		if (hambre + hambreS > 40) {
			hambre = 40;
		}
		else {
			hambre = hambre + hambreS;
		}
		
		if (vida + vidaS > 40) {
			vida = 40;
		}
		else {
			vida = vida + vidaS;
		}
		
	}
	
	private void evolucionar() {
		if (8<cont && cont<=24) {
			state = new Kuchipatchi();
		}
		else if (24<cont && cont<=32) {
			state = new Mimitchi();
		}
		else if (cont>32) 
		{
			if (!ultima)
			{
				if (score>=100)
				{
					state = new Mametchi();
					ultima=true;
				}
				else
				{
					state = new Maskutchi();
					ultima=true;
				}
			} 
		}
		Object[] objectArray = new Object[] {"Tamagotchi evolucionado",state.getEv()};
		setChanged();
		notifyObservers(objectArray);
	}
	
	private void gestionarCorazones() {
		int coraRestantes=0;
		if (vida>30) {
			coraRestantes = 4;
		}
		else if (vida>20 && vida<=30) {
			coraRestantes = 3;
		}
		else if (vida>10 && vida<=20) {
			coraRestantes = 2;
		}
		else if (vida<=10 && vida>0) {
			coraRestantes = 1;
		}
		else if(vida<=0)
		{
			coraRestantes = 0;
			muerto=true;
        	System.out.println("MUERTO por vida");
		}
		Object[] objectArray = new Object[] {"Actualizar corazones",coraRestantes}; 
		setChanged();
		notifyObservers(objectArray);
	}
	
	private void gestionarComida() {
		int comidaRestante = 0;
		if (hambre>30) {
			comidaRestante = 4;
		}
		else if (hambre>20 && hambre<=30) {
			comidaRestante = 3;
		}
		else if (hambre>10 && hambre<=20) {
			comidaRestante = 2;
		}
		else if (hambre<=10 && hambre>0) {
			comidaRestante = 1;
		}
		else if (hambre<=0) {
			comidaRestante = 0;
			muerto=true;
        	System.out.println("MUERTO por hambre");
		}
		Object[] objectArray = new Object[] {"Actualizar comida",comidaRestante}; 
		setChanged();
		notifyObservers(objectArray);
	}
	
	public int getVida() {
		return vida;
	}
	
	public int getHambre() {
		return hambre;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean getKK() {
		return state2.getCagado();
	}
	
	public boolean getEnf() {
		return state2.getEnfermado();
	}
	
	private void reiniciar() {
		vida = 40;
		hambre = 40;
		cont = 0;
		score = 0;
		muerto = false;
		enMiniJuego = false;
		gestionarComida();
		gestionarCorazones();
		sopas = 0;
		candys = 0;
		ultima=false;
		state = new Egg();
		state2 = new Bien();
	}
	
	private void actualizarScores(String pNombre, int pScore) {
        try {
            FileWriter fileWriter = new FileWriter("LeaderBoard.txt", true); // Abre el archivo en modo de agregar (append)
            fileWriter.write(pNombre + "   " + pScore + "\n"); // Escribe el nombre y el puntaje al archivo
            fileWriter.close(); // Cierra el archivo despuï¿½s de escribir
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo LeaderBoard.txt: " + e.getMessage());
        }
    }

	public void actualizarNombre(String nom) {
		this.nombre=nom;
	}

	public void sumarPuntuacion(int dureza) {
		this.score=this.score+dureza;
		Object[] objectArray = new Object[] {"Actualizar puntuacion",score}; 
		setChanged();
		notifyObservers(objectArray);
	}

	public String getEvol() {
		return state.getEv();
	}
	
}
