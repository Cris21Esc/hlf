package scripts;
import java.util.Scanner;
public class GameManager {
	private static int boatsToSetP = 0;
	private static int boatsToSetE = 0;
	private static int boatsInGameP = 0;
	private static int boatsInGameE = 0;
	public static Boolean ganador = false;
	private static Scanner sc = new Scanner(System.in);
	private static TableroHLF plBoard = new TableroHLF(8,' ',"player");
	private static TableroHLF opBoard = new TableroHLF(8,' ',"enemy");
	private static Barco tipoDeBarcoP[];
	private static Barco tipoDeBarcoE[];
	private static String rm;
	private static int diff;
	public static void main(String[] args) {
		diff = setDiff();
		showBoards();
		System.out.println("¿Generar barcos aleatoriamente (r) o manualmente (m)?");
		rm = sc.next();
		if(rm.toLowerCase().equals("r") == true) {
			setBoatPlayerRandom(diff);
		}else {
			setBoatPlayerManually(diff);
		}
		setBoatEnemy(diff);
		showBoards();
		while(boatsInGameP != 0 || boatsInGameE != 0) {
			shootBoat();
			shootEnemy();
			showBoards();
			if(boatsInGameP <= 0) {
				break;
			}
			if(boatsInGameE <= 0) {
				ganador = true;
				break;
			}
			//Thread.sleep(2000);
		}
		gameOver(ganador);
	}
	public static void setBoatPlayerManually(int diff) {
		System.out.println("Vamos a colocar los barcos");
		if (boatsInGameP == 0) {
			boatsToSetP = diff;
		}
		Barco tmp[] = new Barco[boatsToSetP];
		while(boatsInGameP != boatsToSetP) {
			String place;
			int Y;
			System.out.println("Coordenadas del barco");
			place = sc.next();
			Y = letrasToNumeros(place.substring(0, 1).toLowerCase());
			while(Y == 0) {
				System.out.println("Pon una coordenada valida");
				place = sc.next();
				Y = letrasToNumeros(place.substring(0, 1));
			}
			int X = Integer.parseInt(place.substring(1));
			while(X < 1 || X > 8) {
				System.out.println("Pon una coordenada numerica del tablero");
				X = sc.nextInt();
			}
			System.out.println("Longitud (Fragata: 1, Crucero: 2, Acorazado: 3, Portaaviones: 4)");
			int L = sc.nextInt();
			while(L < 1 || L > 4) {
				L = sc.nextInt();
			}
			System.out.println("¿h/v?");
			String O = sc.next();
			if(O.equals("v") || O.equals("h")) {}
			else {
				System.out.println("h para horizontal. v para vertical");
				O = sc.next();
			}
			if(O.equals("h")) {
				Y = secureBoatPlayerY(L,Y,O);
			}
			else {
				X = secureBoatPlayerX(L,X,O);	
			}
			while(checkPositionP(X, Y, L, O) == false){
				System.out.println("Hay un barco en las coordenadas especificadas");
				System.out.println("Coordenadas del barco");
				place = sc.next();
				Y = letrasToNumeros(place.substring(0, 1));
				while(Y == 0) {
					System.out.println("Pon una coordenada valida");
					place = sc.next();
					Y = letrasToNumeros(place.substring(0, 1));
				}
				X = Integer.parseInt(place.substring(1));
				while(X < 1 || X > 8) {
					System.out.println("Pon una coordenada numerica del tablero");
					X = sc.nextInt();
				}
				System.out.println("Longitud (Fragata: 1, Crucero: 2, Acorazado: 3, Portaaviones: 4)");
				L = sc.nextInt();
				while(L < 1 || L > 4) {
					L = sc.nextInt();
				}
				System.out.println("¿h/v?");
				O = sc.next();
				if(O.toLowerCase().equals("v") || O.toLowerCase().equals("h")) {}
				else {
					System.out.println("h para horizontal. v para vertical");
					O = sc.next();
				}
				if(O.equals("h")) {
					Y = secureBoatPlayerY(L,Y,O);
				}
				else {
					X = secureBoatPlayerX(L,X,O);	
				}
				
			}
			plBoard.setBoat(X,Y,L,O);
			if(L == 1) {
				Barco fragata = new Barco(L,X,Y,O);
				fragata.addCoordinate(X, Y);
				tmp[boatsInGameP] = fragata;
			}
			else if(L == 2) {
				Barco crucero = new Barco(L,X,Y,O);
				crucero.addCoordinate(X, Y);
				tmp[boatsInGameP] = crucero;
			}
			else if(L == 3) {
				Barco acorazado = new Barco(L,X,Y,O);
				acorazado.addCoordinate(X, Y);
				tmp[boatsInGameP] = acorazado;
			}
			else if(L == 4) {
				Barco portaaviones = new Barco(L,X,Y,O);
				portaaviones.addCoordinate(X, Y);
				tmp[boatsInGameP] = portaaviones;
			}
			boatsInGameP++;
		}
		tipoDeBarcoP = tmp;
	}
	public static void setBoatPlayerRandom(int diff) {
		if (boatsInGameP == 0) {
			boatsToSetP = diff;
		}
		Barco tmp[] = new Barco[boatsToSetP];
		while(boatsInGameP != boatsToSetP) {
			int X = (int) (Math.random()*8)+1;
			while(X < 1 || X > 8) {
				X = (int) (Math.random()*8)+1;
			}
			int Y = (int) (Math.random()*8)+1;
			while(Y < 1 || Y > 8) {
				Y = (int) (Math.random()*8)+1;
			}
			int L = (int) (Math.random()*4)+1;
			while(L < 1 || L > 4) {
				L = (int) (Math.random()*4)+1;
			}
			int h_v;
			String O;
			h_v = (int) (Math.random()*2)+1;
			if(h_v > 1) {
				O = "h";
			}else {
				O = "v";
			}
			if(O.equals("h")) {
				Y = secureBoatPlayerRY(L,Y,O);
			}
			else {
				X = secureBoatPlayerRX(L,X,O);	
			}
			while(checkPositionP(X,Y,L,O) == false) {
				X = (int) (Math.random()*8)+1;
				while(X < 1 || X > 8) {
					X = (int) (Math.random()*8)+1;
				}
				Y = (int) (Math.random()*8)+1;
				while(Y < 1 || Y > 8) {
					Y = (int) (Math.random()*8)+1;
				}
				L = (int) (Math.random()*4)+1;
				while(L < 1 || L > 4) {
					L = (int) (Math.random()*4)+1;
				}
				h_v = (int) (Math.random()*2)+1;
				if(h_v > 1) {
					O = "h";
				}else {
					O = "v";
				}
				if(O.equals("h")) {
					Y = secureBoatPlayerRY(L,Y,O);
				}
				else {
					X = secureBoatPlayerRX(L,X,O);	
				}
			}
			plBoard.setBoat(X,Y,L,O);
			if(L == 1) {
				Barco fragata = new Barco(L,X,Y,O);
				fragata.addCoordinate(X, Y);
				tmp[boatsInGameP] = fragata;
			}
			else if(L == 2) {
				Barco crucero = new Barco(L,X,Y,O);
				crucero.addCoordinate(X, Y);
				tmp[boatsInGameP] = crucero;
			}
			else if(L == 3) {
				Barco acorazado = new Barco(L,X,Y,O);
				acorazado.addCoordinate(X, Y);
				tmp[boatsInGameP] = acorazado;
			}
			else if(L == 4) {
				Barco portaaviones = new Barco(L,X,Y,O);
				portaaviones.addCoordinate(X, Y);
				tmp[boatsInGameP] = portaaviones;
			}
			boatsInGameP++;
		}
		tipoDeBarcoP = tmp;
	}
	public static void setBoatEnemy(int diff) {
		if (boatsInGameE == 0) {
			boatsToSetE = diff;
		}
		Barco tmp[] = new Barco[boatsToSetE];
		while(boatsInGameE != boatsToSetE) {
			int X = (int) (Math.random()*8)+1;
			while(X < 1 || X > 8) {
				X = (int) (Math.random()*8)+1;
			}
			int Y = (int) (Math.random()*8)+1;
			while(Y < 1 || Y > 8) {
				Y = (int) (Math.random()*8)+1;
			}
			int L = (int) (Math.random()*4)+1;
			while(L < 1 || L > 4) {
				L = (int) (Math.random()*4)+1;
			}
			int h_v;
			String O;
			h_v = (int) (Math.random()*2)+1;
			if(h_v > 1) {
				O = "h";
			}else {
				O = "v";
			}
			if(O.equals("h")) {
				Y = secureBoatEnemyY(L,Y,O);
			}
			else {
				X = secureBoatEnemyX(L,X,O);	
			}
			while(checkPositionE(X,Y,L,O) == false) {
				X = (int) (Math.random()*8)+1;
				while(X < 1 || X > 8) {
					X = (int) (Math.random()*8)+1;
				}
				Y = (int) (Math.random()*8)+1;
				while(Y < 1 || Y > 8) {
					Y = (int) (Math.random()*8)+1;
				}
				L = (int) (Math.random()*4)+1;
				while(L < 1 || L > 4) {
					L = (int) (Math.random()*4)+1;
				}
				h_v = (int) (Math.random()*2)+1;
				if(h_v > 1) {
					O = "h";
				}else {
					O = "v";
				}
				if(O.equals("h")) {
					Y = secureBoatEnemyY(L,Y,O);
				}
				else {
					X = secureBoatEnemyX(L,X,O);	
				}
			}
			opBoard.setBoat(X,Y,L,O);
			if(L == 1) {
				Barco fragata = new Barco(L,X,Y,O);
				fragata.addCoordinate(X, Y);
				tmp[boatsInGameE] = fragata;
			}
			else if(L == 2) {
				Barco crucero = new Barco(L,X,Y,O);
				crucero.addCoordinate(X, Y);
				tmp[boatsInGameE] = crucero;
			}
			else if(L == 3) {
				Barco acorazado = new Barco(L,X,Y,O);
				acorazado.addCoordinate(X, Y);
				tmp[boatsInGameE] = acorazado;
			}
			else if(L == 4) {
				Barco portaaviones = new Barco(L,X,Y,O);
				portaaviones.addCoordinate(X, Y);
				tmp[boatsInGameE] = portaaviones;
			}
			boatsInGameE++;
		}
		tipoDeBarcoE = tmp;
	}
	public static void shootBoat() {
		String place;
		int Y;
		System.out.println("¿Donde quieres disparar");
		place = sc.next();
		Y = letrasToNumeros(place.substring(0, 1));
		while(Y < 1 || Y > 8) {
			System.out.println("Escoge una coordenada alfabetica dentro del tablero");
			place = sc.next();
			Y = letrasToNumeros(place.substring(0, 1));
		}
		int X = Integer.parseInt(place.substring(1));
		while(X < 1 || X > 8) {
			System.out.println("Escoge una coordenada numerica dentro del tablero");
			X = sc.nextInt();
		}
		if(opBoard.checkBoat(X, Y) == true) {
			System.out.println("Has dado a un barco");
			for(int aux = 0; aux < tipoDeBarcoE.length; aux++) {
				if(tipoDeBarcoE[aux].checkHit(X, Y,"player") == true) {
					opBoard.hitBoat(X, Y);
				}								
			}
		}else {
			opBoard.missShot(X,Y);
			System.out.println("Disparo fallido");
		}
		
	}
	@SuppressWarnings("unused")
	public static void shootEnemy() {
		int X = (int) (Math.random()*8)+1;
		int Y= (int) (Math.random()*8)+1;
		String letras[] = {"A","B","C","D","E","F","G","H"};
		System.out.println("Oponente dispara en: "+ letras[(Y-1)] + X);
		while(opBoard.whereIHit[(X-1)][(Y-1)] != false) {
			X = (int) (Math.random()*8)+1;
			Y= (int) (Math.random()*8)+1;
		}
		if(plBoard.checkBoat(X, Y) == true) {
			System.out.println("Han golpeado tu barco");
			for(int aux = 0; aux < boatsToSetE;aux++) {
				if(tipoDeBarcoP[aux].checkHit(X, Y,"enemy") == true && diff >= 4) {
					plBoard.hitBoat(X, Y);
					opBoard.whereIHit[(X-1)][(Y-1)]= true;
					break;
				}
				else {
					plBoard.hitBoat(X, Y);
					break;
				}
			}
		}
		else {
			plBoard.missShot(X, Y);
			System.out.println("Ha fallado el disparo");
		}
	}
	public static void showBoards() {
		System.out.println(opBoard);
		System.out.println(plBoard);
		if(boatsInGameE >= 0 && boatsInGameP >= 0) {
			System.out.println("Barcos aliados con vida: " + boatsInGameP );
			for(int aux = 0; aux < boatsToSetP; aux++) {
				if(tipoDeBarcoP[aux].checkAlive() == false) {
					System.out.println(tipoDeBarcoP[aux]);
				}
			}
			System.out.println("Barcos enemigos con vida: " + boatsInGameE);
			for(int aux = 0; aux < boatsToSetE; aux++) {
				if(tipoDeBarcoE[aux].checkAlive() == false) {
					System.out.println(tipoDeBarcoE[aux]);
				}
			}
		}
	}
	public static void updateBoatsP(int num) {
		boatsInGameP = boatsInGameP - num;
	}
	public static void updateBoatsE(int num) {
		boatsInGameE = boatsInGameE - num;
	}
	public static int secureBoatPlayerY(int L, int X, String ori) {
		
		if(L == 4 && X > 5 && ori.equals("h")) {
			while(X > 5) {
				System.out.println("El portaaviones no entra. Escoje otra coordenada numerica");
				X = sc.nextInt();
			}
		}
		if(L == 3 && X > 6 && ori.equals("h")) {
			while(X > 6) {
				System.out.println("El acorazado no entra. Escoje otra coordenada numerica");
				X = sc.nextInt();
			}
		}
		if(L == 2 && X > 7 && ori.equals("h")) {
			while(X > 7) {
				System.out.println("El crucero no entra. Escoje otra coordenada numerica");
				X = sc.nextInt();
			}
		}
		return X;
	}
	public static int secureBoatPlayerX(int L, int X, String ori) {

		if(L == 4 && X > 5 && ori.equals("v")) {
			while(X > 5) {
				System.out.println("El portaaviones no entra. Escoje otra coordenada en letra");
				String letra;
				letra = sc.next();
				X = letrasToNumeros(letra);
			}
		}
		if(L == 3 && X > 6 && ori.equals("v")) {
			while(X > 6) {
				System.out.println("El acorazado no entra. Escoje otra coordenada en letra");
				String letra;
				letra = sc.next();
				X = letrasToNumeros(letra);
			}
		}
		if(L == 2 && X > 7 && ori.equals("v")) {
			while(X > 7) {
				System.out.println("El crucero no entra. Escoje otra coordenada en letra");
				String letra;
				letra = sc.next();
				X = letrasToNumeros(letra);
			}
		}
		return X;
	}
	public static int secureBoatPlayerRX(int L, int X, String ori) {
		if(L == 4 && X > 5 && ori.equals("v")) {
			X = (int) (Math.random()*5)+1;
		}
		if(L == 3 && X > 6 && ori.equals("v")) {
			X = (int) (Math.random()*6)+1;
		}
		if(L == 2 && X > 7 && ori.equals("v")) {
			X = (int) (Math.random()*7)+1;
		}
		return X;
	}
	public static int secureBoatPlayerRY(int L, int Y,String ori) {
		if (L == 4 && Y > 5 && ori.equals("h")) {
			Y = (int) (Math.random()*5)+1;
		}
		if(L == 3 && Y > 6 && ori.equals("h")) {
			Y = (int) (Math.random()*6)+1;
		}
		if(L == 2 && Y > 7 && ori.equals("h")) {
			Y = (int) (Math.random()*7)+1;
		}
		return Y;
	}
	public static int secureBoatEnemyX(int L, int X, String ori) {
		if(L == 4 && X > 5 && ori.equals("v")) {
			X = (int) (Math.random()*5)+1;
		}
		if(L == 3 && X > 6 && ori.equals("v")) {
			X = (int) (Math.random()*6)+1;
		}
		if(L == 2 && X > 7 && ori.equals("v")) {
			X = (int) (Math.random()*7)+1;
		}
		return X;
	}
	public static int secureBoatEnemyY(int L, int Y,String ori) {
		if (L == 4 && Y > 5 && ori.equals("h")) {
			Y = (int) (Math.random()*5)+1;
		}
		if(L == 3 && Y > 6 && ori.equals("h")) {
			Y = (int) (Math.random()*6)+1;
		}
		if(L == 2 && Y > 7 && ori.equals("h")) {
			Y = (int) (Math.random()*7)+1;
		}
		return Y;
	}
	public static int letrasToNumeros(String letra) {
		int num;
		int aux = 0;
		num = aux;
		Boolean fuera = true;
		String coordenadasL[] = {"a","b","c","d","e","f","g","h"};
		letra.toLowerCase();
		for(; aux<coordenadasL.length;aux++) {
			if(coordenadasL[aux].equals(letra) == true) {
				num = aux;
				fuera = false;
				break;
			}
		}
		if(fuera == true) {
			num = -1;
		}
		return (num+1);
	}
	public static Boolean checkPositionP(int X, int Y,int L,String ori) {
		Boolean pG = true;
		int bx = X;
		int by = Y;
		if (ori.equals("h")) {
			for(int aux = 0; aux<L && aux <= 8;aux++,by++) {
				if(plBoard.checkBoat(bx, by) == true) {
					pG = false;
				}
			}
		}else if(ori.equals("v")){
			for(int aux = 0; aux<L && aux <= 8;aux++,bx++) {
				if(plBoard.checkBoat(bx, by) == true) {
					pG = false;
				}
			}
		}
		return pG;
	}
	public static Boolean checkPositionE(int X, int Y,int L,String ori) {
		Boolean pG = true;
		int bx = X;
		int by = Y;
		if (ori.equals("h")) {
			for(int aux = 0; aux<L;aux++,by++) {
				if(opBoard.checkBoat(bx, by) == true) {
					pG = false;
				}
			}
		}else if(ori.equals("v")){
			for(int aux = 0; aux<L;aux++,bx++) {
				if(opBoard.checkBoat(bx, by) == true) {
					pG = false;
				}
			}
		}
		return pG;
	}
	public static int setDiff() {
		int diff;
		System.out.println("Escoge una dificultad:"
				+ "\n 1: muy facil (1 barco)"
				+ "\n 2: facil (2 barcos)"
				+ "\n 3: normal (3 barcos)"
				+ "\n 4: dificil (4 barcos)"
				+ "\n 5: Jack Sparrow (5 barcos)");
		diff = sc.nextInt();
		while(diff < 1 || diff > 5) {
			System.out.println("Escoge una dificultad de las propuestas");
			diff = sc.nextInt();
		}
		return diff;
	}
	private static void gameOver(Boolean ganador2) {
		if(ganador2 == true) {
			System.out.println("Has ganado");
		}else {
			System.out.println("Has perdido");
		}
	}
}