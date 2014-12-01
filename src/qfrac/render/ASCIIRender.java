package qfrac.render;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.fusesource.jansi.Ansi;

import qfrac.fractalizator.Grid;

public class ASCIIRender {
	
	
	public static void render(Grid grid){
		double[][] data = grid.getGrid();
		for(double[] r:data){
			for(double e:r){
				System.out.print("|");
				System.out.printf( "%.2f", e );
			}
			System.out.println();
		}
	}

	public static void renderScan(Grid grid) throws IOException {
		File file = new File("lMap.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		double[][] data = grid.getGrid();
		for(int level=0;level<10;level++){
			System.out.println("================================================");
			bw.write("================================================");
			bw.write(System.lineSeparator());
			System.out.println("LEVEL: " + level);
			bw.write("LEVEL: " + level);
			bw.write(System.lineSeparator());
			System.out.println("------------------------------------------------");
			bw.write("------------------------------------------------");
			bw.write(System.lineSeparator());
			for(double[] r:data){
				for(double e:r){
					int point = (int) (10 * e);
					if(point>=level){
						System.out.print("x");
						bw.write("x");
					} else {
						System.out.print(" ");
						bw.write(" ");
					}
				}
				System.out.println();
				bw.write(System.lineSeparator());
			}
		}
		bw.close();
	}
	
	public static void fullRender(Grid grid,int sLevel){
		String[] paleta = {" ",".",":","+","!","J","I","M","Q","#",};
		double[][] data = grid.getGrid();
		for(double[] r:data){
			for(double v:r){
				int point = (int) (10*v);
				if(point-1>sLevel){
					System.out.print(paleta[point-1]);
				} else {
					System.out.print(paleta[0]);
				}
				
			}
			System.out.println();
		}
	}
	
	public static void fullRenderW(Grid grid,int sLevel){
		Object[] paleta = {
				Ansi.ansi().fg(Ansi.Color.BLUE).a("."),
				Ansi.ansi().fg(Ansi.Color.CYAN).a(","),
				Ansi.ansi().fg(Ansi.Color.WHITE).a(":"),
				Ansi.ansi().fg(Ansi.Color.YELLOW).a("+"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("!"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("J"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("I"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("M"),
				Ansi.ansi().fg(Ansi.Color.WHITE).a("Q"),
				Ansi.ansi().fg(Ansi.Color.WHITE).a("#"),
				Ansi.ansi().fg(Ansi.Color.BLUE).a("."),
				Ansi.ansi().fg(Ansi.Color.CYAN).a(","),
				Ansi.ansi().fg(Ansi.Color.WHITE).a(":"),
				Ansi.ansi().fg(Ansi.Color.YELLOW).a("+"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("!"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("J"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("I"),
				Ansi.ansi().fg(Ansi.Color.GREEN).a("M"),
				Ansi.ansi().fg(Ansi.Color.WHITE).a("Q"),
				Ansi.ansi().fg(Ansi.Color.WHITE).a("#")
				};
		double[][] data = grid.getGrid();
		int rad = (int)(data.length/Math.sqrt(2*Math.PI));
		for(int x =0;x<data.length;x++){
			for(int y =0;y<data.length;y++){
				double dist = Math.sqrt(Math.pow((x-(data.length/2)), 2)+Math.pow((y-(data.length/2)), 2));
				double v = data[x][y];
				int point = (int) (20*v);
				if(dist<rad){
					if(point-1>sLevel){
						O.p(paleta[point-1]);
					} else {
						O.p(paleta[0]);
					}
				} else {
					O.p(" ");
				}
				
				
			}
			System.out.println();
		}
	}
	
	public static void fullRenderPrint(Grid grid,int sLevel) throws IOException{
		File file = new File("MAPA.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String[] paleta = {" ",".",":","+","!","J","I","M","Q","#",};
		double[][] data = grid.getGrid();
		for(double[] r:data){
			for(double v:r){
				int point = (int) (10*v);
				if(point-1>sLevel){
					System.out.print(paleta[point-1]);
					bw.write(paleta[point-1]);
				} else {
					System.out.print(paleta[0]);
					bw.write(paleta[0]);
				}
				
			}
			System.out.println();
			bw.write(System.lineSeparator());
		}
		bw.close();
	}
	

}
