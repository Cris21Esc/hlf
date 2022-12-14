package scripts;
import java.util.Scanner;
public class GameManager {
	private static int boatsToSet = 0;
	private static int boatsInGameP = 0;
	private static int boatsInGameE = 0;
	private static Scanner sc = new Scanner(System.in);
	private static TableroHLF plBoard = new TableroHLF(8,' ',"player");
	private static TableroHLF opBoard = new TableroHLF(8,' ',"enemy");
	
	public static void main(String[] args) throws InterruptedException {
		showBoards();
		setBoatPlayer();
		setBoatEnemy();
		while(boatsInGameP != 0 || boatsInGameE != 0) {
			shootBoat();
			shootEnemy();
			showBoards();
			Thread.sleep(2000);
		}
	}
	
	public static void setBoatPlayer() {
		System.out.println("Decide cuantos barcos quieres colocar");
		if (boatsInGameP == 0) {
			boatsToSet = sc.nextInt();
		}
		while(boatsInGameP != boatsToSet) {
			System.out.println("Coordenadas del barco");
			int X = sc.nextInt();
			int Y = sc.nextInt();
			System.out.println("Longitud (Fragata: 1, Crucero: 2, Acorazado: 3, Portaaviones: 4)");
			int L = sc.nextInt();
			while(L < 1 || L > 4) {
				L = sc.nextInt();
			}
			System.out.println("¿h/v?");
			String O = sc.next();
			plBoard.setBoat(X,Y,L,O);
			if(L == 1) {
				Barco fragata = new Barco(L,X,Y,O,"fragata");
				fragata.addCoordinate(X, Y);
			}
			else if(L == 2) {
				Barco crucero = new Barco(L,X,Y,O,"crucero");
				crucero.addCoordinate(X, Y);
			}
			else if(L == 3) {
				Barco acorazado = new Barco(L,X,Y,O,"acorazado");
				acorazado.addCoordinate(X, Y);
			}
			else if(L == 4) {
				Barco portaaviones = new Barco(L,X,Y,O,"portaaviones");
				portaaviones.addCoordinate(X, Y);
			}
			boatsInGameP++;
		}
	}
	public static void setBoatEnemy() {
		System.out.println("Decide cuantos barcos enemigos quieres colocar");
		if (boatsInGameE == 0) {
			boatsToSet = sc.nextInt();
		}
		while(boatsInGameE != boatsToSet) {
			System.out.println("Coordenadas del barco");
			int X = sc.nextInt();
			int Y = sc.nextInt();
			System.out.println("Longitud");
			int L = sc.nextInt();
			System.out.println("¿h/v?");
			String O = sc.next();
			opBoard.setBoat(X,Y,L,O);
			boatsInGameE++;
		}
	}
	
	public static void shootBoat() {
		System.out.println("¿Donde quieres disparar?");
		int X = sc.nextInt();
		if(X < 0 || X > 8) {
			System.out.println("Ingresa una coordenada dentro del tablero");
			X = sc.nextInt();
		}
		int Y = sc.nextInt();
		if(Y < 0 || Y > 8) {
			System.out.println("Ingresa una coordenada dentro del tablero");
			Y = sc.nextInt();
		}
		if(opBoard.checkBoat(X, Y) == true) {
			opBoard.hitBoat(X, Y);
			System.out.println("Has dado a un barco");
		}else {
			opBoard.missShot(X,Y);
			System.out.println("Disparo fallido");
		}
		
	}
	/*
	public static void shootBoat() {
		int X = (int) (Math.random()*8)+1;
		int Y= (int) (Math.random()*8)+1;
		System.out.println("Disparas en X: " + X + " Y: " + Y);
		if(plBoard.checkBoat(X, Y) == true) {
			System.out.println("Has golpeado un barco");
			opBoard.hitBoat(X, Y);
		}
		else {
			opBoard.missShot(X, Y);
			System.out.println("Has fallado el disparo");
		}
	}*/
	
	public static void shootEnemy() {
		int X = (int) (Math.random()*8)+1;
		int Y= (int) (Math.random()*8)+1;
		System.out.println("Oponente dispara en X: " + X + " Y: " + Y);
		if(plBoard.checkBoat(X, Y) == true) {
			System.out.println("Han golpeado tu barco");
			plBoard.hitBoat(X, Y);
		}
		else {
			plBoard.missShot(X, Y);
			System.out.println("Ha fallado el disparo");
		}
	}
	public static void showBoards() {
		System.out.println(opBoard);
		System.out.println(plBoard);
	}
	
	public int getBoats() {
		return boatsInGameP;
	}
}
