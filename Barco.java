package scripts;

public class Barco {
	
	private int longitud;
	private Boolean hundido = false;
	private int coordenadasX[];
	private int coordenadasY[];
	private String orientacion;
	private Boolean destroyed[];
	public Barco(int size, int X, int Y, String orientacion) {
		longitud = size;
		Boolean tmp[] = new Boolean[longitud];
		for(int aux = 0; aux<tmp.length;aux++) {
			tmp[aux] = false;
		}
		destroyed = tmp;
		coordenadasX = new int[longitud];
		coordenadasY = new int[longitud];
		this.orientacion = orientacion;
	}
	
	public void addCoordinate(int X, int Y) {
		int bX = X;
		int bY = Y;
		if(orientacion.equals("v")) {
			for(int aux = 0 ; aux<coordenadasX.length ;aux++,bX++) {
				coordenadasX[aux] = bX;
			}
			for(int aux = 0; aux < coordenadasY.length;aux++) {
				coordenadasY[aux] = bY;
			}
		}
		
		if(orientacion.equals("h")) {
			
			for(int aux = 0 ; aux<coordenadasX.length ;aux++) {
				coordenadasX[aux] = bX;
			}
			
			for(int aux = 0; aux < coordenadasY.length;aux++,bY++) {
				coordenadasY[aux] = bY;
			}
		}
	}

	public Boolean checkHit(int X, int Y, String ID) {
		Boolean hit = false;
		Boolean eliminated = false;
		Boolean  drowned = false;
		int allHits = 0;
		 
		for(int aux = 0; aux<coordenadasX.length && aux < coordenadasY.length;aux++) {
			if(coordenadasX[aux] == X && coordenadasY[aux] == Y) {
				hit = true;
				destroyed[aux] = true;
				break;
			}
		}
		for(int aux = 0; aux<destroyed.length;aux++) {
			if(destroyed[aux] == true) {
				drowned = true;
				allHits++;
				
			}else {
				drowned = false;
			}
			if(drowned == true) {
				eliminated = true;
			}
			else {
				eliminated = false;
			}
		}
		if(eliminated == true && allHits == destroyed.length && hundido == false) {
			destroyed(ID);
		}
		return hit;
	}
	
	public void destroyed(String ID) {
		hundido = true;
		if(hundido == true && ID.equals("player")) {
			if(longitud == 1) {
				System.out.println("Fragata enemiga hundida");
			}else if(longitud == 2){
				System.out.println("Crucero enemigo hundido");
			}
			else if(longitud == 3){
				System.out.println("Acorazado enemigo hundido");
			}
			else if(longitud == 4){
				System.out.println("Portaaviones enemigo hundido");
			}
			GameManager.updateBoatsE(1);;
		}else if(hundido == true && ID.equals("enemy")) {
			if(longitud == 1) {
				System.out.println("Fragata aliada hundida");
			}else if(longitud == 2){
				System.out.println("Crucero aliado hundido");
			}
			else if(longitud == 3){
				System.out.println("Acorazado aliado hundido");
			}
			else if(longitud == 4){
				System.out.println("Portaaviones aliado hundido");
			}
			GameManager.updateBoatsP(1);
		}
	}
	public Boolean checkAlive() {
		return hundido;
	}
	public String toString() {
		String showB = "";
		if(longitud == 1) {
			showB += "fragata";
		}else if(longitud == 2){
			showB += "crucero";
		}
		else if(longitud == 3){
			showB += "acorazado";
		}
		else if(longitud == 4){
			showB += "portaaviones";
		}
		return showB;
	}
}
