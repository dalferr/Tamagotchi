package modelo;

import java.util.ArrayList;

public class Composite implements Accion{
	private ArrayList <Accion> acc = new ArrayList<Accion>();
	private int punt = 0;
	
	public void addAccion(Accion pA) {
		acc.add(pA);
	}
	
	public void deleteComponente(Accion pA) {
		acc.remove(pA);
	}
	
	public int incrementar() {
		acc.stream().forEach(p->p.incrementar());
		punt = acc.size()*3*acc.size();
		acc = new ArrayList<Accion>();
		return punt;
	}

}
