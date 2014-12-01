package main;

import java.io.IOException;
import java.util.Random;

import qfrac.WarpGrid;
import qfrac.fractalizator.Grid;
import qfrac.render.ASCIIRender;

public class MainTests {

	public static void main(String[] args) {
		MainTests main = new MainTests();
		main.go5();
	}

	public void go() {
		WarpGrid w = new WarpGrid();
		w.fullRetarded();
	}

	public void go2() {
		Random r = new Random(5);
		System.out.println(">> " + r.nextDouble() * 2 * 500);
	}

	public void go3() {
		double menor = 100000;
		double maior = 0;
		double m = 0;
		int cont = 0;
		int zr = 0;
		int um = 0;
		for (int i = 0; i < 100000; i++) {
			Grid g = new Grid();
			double[][] data;
			// g.fullRetarded();
			g.warpFract();
			g.cleanGrid();
			data = g.getGrid();

			for (double[] c : data) {
				for (double e : c) {
					if (e >= maior) {
						maior = e;
					}
					if (e <= menor) {
						menor = e;
					}
					if (e == 0) {
						zr++;
					}
					if (e == 1) {
						um++;
					}
					m += e;
					cont++;
				}
			}
			System.out.println("===");
			System.out.println("m: " + menor + " M: " + maior + " me: " + m
					/ cont + " c: " + cont + " m: " + m);
			System.out.println("0s: " + zr + " ums: " + um);
			System.out.println("p0s: " + (double) zr / 100000 + " pums: "
					+ (double) um / 100000);
			System.out.println("===");
		}
		Grid g = new Grid();
		g.warpFract();
		g.cleanGrid();
		ASCIIRender.render(g);
	}

	public void go4() {
		Random rn = new Random();
		Grid g = new Grid(7, rn);
		g.warpFract();
		g.cleanGrid();
		try {
			ASCIIRender.renderScan(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void go5() {
		Random rn = new Random();
		Grid g = new Grid(6, rn);
		g.warpFract();
		g.cleanGrid();

		ASCIIRender.fullRender(g,4);

	}

}
