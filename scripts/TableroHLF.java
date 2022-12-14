package scripts;

public class TableroHLF {
	
	String ID;
	int bX;
	int bY;
	public char tablero[][];
	public char tableroMostrar[][];

	public TableroHLF(int espacio, char naruto,String ID) {
		this.ID = ID;
		tablero = new char[espacio][espacio];
		tableroMostrar = new char[espacio][espacio];
		rellenaTablero(naruto);
	}
	private void rellenaTablero(char caracter) {
		for(int aux = 0; aux < tablero.length; aux++) {
			for(int aux2 = 0; aux2 < tablero[aux].length;aux2++) {
				tablero[aux][aux2] = caracter;
			}
		}
		for(int aux = 0; aux < tableroMostrar.length; aux++) {
			for(int aux2 = 0; aux2 < tableroMostrar[aux].length;aux2++) {
				tableroMostrar[aux][aux2] = caracter;
			}
		}
	}
	public void setBoat(int X,int Y, int longitud, String orientacion) {
		int colocados = 0;
		bX = X-1;
		bY = Y-1;
		while (colocados != longitud) {
			if(tablero[bX][bY] == ' ' && orientacion.equals("h")) {				
				tablero[bX][bY] = '*';
				bY++;
				colocados++;
			}else if(tablero[bX][bY] == ' ' && orientacion.equals("v")){
				tablero[bX][bY] = '*';
				bX++;
				colocados++;
			}
		}
	}
	public Boolean checkBoat(int X, int Y) {
		Boolean hit = false;
		if(tablero[X-1][Y-1] == '*') {
			hit = true;
		}
		return hit;
	}
	public void hitBoat(int X, int Y) {
		if(ID.equals("player")) {
			tablero[X-1][Y-1] = '#';
		}
		else if(ID.equals("enemy")) {
			tablero[X-1][Y-1] = '#';
			tableroMostrar[X-1][Y-1] = '#';
		}		
	}
	
	public void missShot(int X, int Y) {
		if(ID.equals("player")) {
			tablero[X-1][Y-1] = 'A';
		}
		else if(ID.equals("enemy")) {
			tablero[X-1][Y-1] = 'A';
			tableroMostrar[X-1][Y-1] = 'A';
		}
	}
	
	public String toString() {
		String showT = "";
		
		if(ID.equals("player")) {
			for(int aux = 0; aux<tablero.length; aux++) {
				for(int aux2 = 0; aux2<tablero[aux].length;aux2++){
					showT += "[" + tablero[aux][aux2] + "]";
				}
				showT+="\n";
			}
		}
		else if(ID.equals("enemy")) {
			for(int aux = 0; aux<tableroMostrar.length; aux++) {
				for(int aux2 = 0; aux2<tableroMostrar[aux].length;aux2++){
					showT += "[" + tableroMostrar[aux][aux2] + "]";
				}
				showT+="\n";
			}
		}
		return showT;
	}
}