package scripts;

public class Barco {
	
	private String tipo;
	private int longitud;
	private int tocado;
	private Boolean hundido = false;
	private int coordenadasX[];
	private int coordenadasY[];
	private String orientacion;
	
	public Barco(int size, int X, int Y, String orientacion, String tipo) {
		longitud = size;
		coordenadasX = new int[longitud];
		coordenadasY = new int[longitud];
		this.orientacion = orientacion;
		this.tipo = tipo;
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
	
	public static String checkBoat(int X, int Y) {
		String barco = null;
		return barco;
	}

	public Boolean checkHit(int X, int Y) {
		Boolean xChecked=false;
		Boolean yChecked=false;
		Boolean hit = false;
		 
		for(int aux = 0; aux<coordenadasX.length && aux < coordenadasY.length;aux++) {
			if(coordenadasX[aux] == X) {
				xChecked = true;
			}
			if(coordenadasY[aux] == Y) {
				yChecked = true;
			}
			if(xChecked == true && yChecked == true) {
				hit = true;
				break;
			}
		}
		return hit;
	}
	
	public void destroyed() {
		
		 hundido = true;
	 }
}
