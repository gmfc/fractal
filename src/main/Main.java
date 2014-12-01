package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import qfrac.fractalizator.Grid;
import qfrac.render.ASCIIRender;

public class Main {
	
	BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private Grid map;

	public static void main(String[] args) {
		Main main = new Main();
		try {
			main.go();
		} catch (IOException e) {
			e.printStackTrace();
		}
		O.l("SAINDO!");
		System.exit(0);

	}

	private void go() throws IOException {
		O.l("Bem vindo!");
		O.b();
		mainMenu();
		
	}

	private void mainMenu() throws IOException {
		O.l("Entre com a seed para o mapa.");
		O.l("Valores nao numericos serao tratados como random");
		O.l("EXIT para fechar.");
		String v = r.readLine();
		if(v.equals("EXIT")){
			return;
		}
		Random rand;
		try {
			long seed = Long.parseLong(v);
			rand = new Random(seed);
			O.l("Seed: "+seed);
		} catch (NumberFormatException e) {
			O.l("Gerando seed aleatoria");
			rand = new Random();
			long seed = rand.nextLong();
			O.l("Seed usada:" + seed);
			rand = new Random(seed);
		}
		
		createMap(rand);

		
		
	}

	private void createMap(Random rand) throws IOException {
		this.map = new Grid(6,rand);
		map.warpFract();
		map.cleanGrid();
		O.l("Mapa Criado");
		O.b();
		render();
		
		
	}
	
	private void render() throws IOException{
		O.l("Entre com o nivel do mar (0-9)");
		try {
			int lvl = Integer.parseInt(this.r.readLine());
			O.l("Renderizando.. com mar em: " + lvl);
			O.b();
			ASCIIRender.fullRenderW(map, lvl);
		} catch (NumberFormatException e) {
			O.l("Nao intendi, usando valor padrao (4)..");
			O.l("Renderizando..");
			O.b();
			ASCIIRender.fullRenderW(map, 4);
			O.b();
		}
		
		
		O.l("Deseja ampliar? (S/N)");
		String i = r.readLine();
		
		if(i.equals("S")){
			amplify();
		} else {
			mainMenu();
		}
	}

	private void amplify() throws NumberFormatException, IOException {
		O.l("Metodo ainda experimental.");
		O.b();
		O.l("Mapa possui 65x65 chunks (renderizados com a tecnologia ASCII)");
		O.l("Entre com a coordenada de um deles.");
		O.l("x:");
		int x = 0;
		try {
			x = Integer.parseInt(r.readLine());
		} catch (NumberFormatException e) {
			O.l("Isso nao e um numero!");
			amplify();
		}
		O.l("y:");
		int y = 0;
		try {
			y = Integer.parseInt(r.readLine());
		} catch (NumberFormatException e) {
			O.l("Isso nao e um numero!");
			amplify();
		}
		this.map.fract(getCorners(x,y));
		render();
		
	}

	private double[][] getCorners(int x, int y) {
		double[][] auxMap = map.getGrid();
		double[][] corners = new double[2][2];
		if(x!=0&&y!=0&&x!=64&&y!=64){
			corners[0][0] = auxMap[x-1][y-1];
			corners[0][1] = auxMap[x-1][y+1];
			corners[1][0] = auxMap[x+1][y-1];
			corners[1][1] = auxMap[x+1][y+1];
		}
		
		return corners;
	}

}
