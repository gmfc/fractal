package qfrac.fractalizator;

import java.util.Random;

public class BGrid {

	private final double ANCHOR = 0.5;
	private int DATA_SIZE;
	private double[][] grid;
	private double h = 0.25;
	private Random r;

	/**
	 * @param dATA_SIZE
	 * @param r
	 */
	public BGrid(int dataMag, Random r) {
		dataMag = (int) (Math.pow(2, dataMag) + 1);
		this.DATA_SIZE = dataMag;
		this.r = r;
		this.grid = new double[this.DATA_SIZE][this.DATA_SIZE];
	}

	public BGrid() {
		this.DATA_SIZE = 17;//
		this.r = new Random();
		this.grid = new double[this.DATA_SIZE][this.DATA_SIZE];
	}

	/**
	 * Fractaliza a matriz
	 */
	public void fract() {
		grid[0][0] = grid[0][this.DATA_SIZE - 1] = grid[this.DATA_SIZE - 1][0] = grid[this.DATA_SIZE - 1][this.DATA_SIZE - 1] = this.ANCHOR;
		for (int sideLength = this.DATA_SIZE - 1; sideLength >= 2; sideLength /= 2, this.h /= 2.0) {
			int halfSide = sideLength / 2;
			square(sideLength, halfSide);
			diamon(sideLength, halfSide);
		}
	}

	/**
	 * Dado um poligono (4 lados), Amplia a fractalizacao.
	 * 
	 * @param corners
	 */
	public void fract(double[][] corners) {
		long seed = (long) (1000 * corners[0][0] + 1000
				* corners[0][corners.length - 1] + 1000
				* corners[corners.length - 1][0] + 1000 
				* corners[corners.length - 1][corners.length - 1]);
		this.r.setSeed(seed);
		grid[0][0] = corners[0][0];
		grid[0][this.DATA_SIZE - 1] = corners[0][corners.length - 1];
		grid[this.DATA_SIZE - 1][0] = corners[corners.length - 1][0];
		grid[this.DATA_SIZE - 1][this.DATA_SIZE - 1] = corners[corners.length - 1][corners.length - 1];
		for (int sideLength = this.DATA_SIZE - 1; sideLength >= 2; sideLength /= 2, this.h /= 2.0) {
			int halfSide = sideLength / 2;
			square(sideLength, halfSide);
			diamon(sideLength, halfSide);
		}
	}

	public void warpFract() {
		grid[0][0] = grid[0][this.DATA_SIZE - 1] = grid[this.DATA_SIZE - 1][0] = grid[this.DATA_SIZE - 1][this.DATA_SIZE - 1] = this.ANCHOR;
		for (int sideLength = this.DATA_SIZE - 1; sideLength >= 2; sideLength /= 2, this.h /= 2.0) {
			int halfSide = sideLength / 2;
			square(sideLength, halfSide);
			warpDiamon(sideLength, halfSide);
		}
	}

	private void diamon(int sideLength, int halfSide) {
		// generate the diamond values
		// since the diamonds are staggered we only move x
		// by half side
		for (int x = 0; x < this.DATA_SIZE; x += halfSide) {
			// and y is x offset by half a side, but moved by
			// the full side length
			for (int y = (x + halfSide) % sideLength; y < this.DATA_SIZE; y += sideLength) {
				// x, y is center of diamond
				double avg = grid[(x - halfSide + this.DATA_SIZE - 1)
						% (this.DATA_SIZE - 1)][y]
						+ grid[(x + halfSide) % (this.DATA_SIZE - 1)][y]
						+ grid[x][(y + halfSide) % (this.DATA_SIZE - 1)]
						+ grid[x][(y - halfSide + this.DATA_SIZE - 1)
								% (this.DATA_SIZE - 1)];
				avg /= 4.0;
				avg = avg + (this.r.nextDouble() * 2 * this.h) - this.h;
				// update value for center of diamond
				grid[x][y] = avg;
				if (x == 0)
					grid[DATA_SIZE - 1][y] = avg;
				if (y == 0)
					grid[x][DATA_SIZE - 1] = avg;
			}
		}
	}

	public void fullRetarded() {
		this.DATA_SIZE = 17;//
		this.r = new Random();
		this.grid = new double[this.DATA_SIZE][this.DATA_SIZE];
		warpFract();
	}

	private void square(int sideLength, int halfSide) {
		for (int x = 0; x < this.DATA_SIZE - 1; x += sideLength) {
			for (int y = 0; y < this.DATA_SIZE - 1; y += sideLength) {
				double avg = grid[x][y] + // top left
						grid[x + sideLength][y] + // top right
						grid[x][y + sideLength] + // lower left
						grid[x + sideLength][y + sideLength];// lower right
				avg /= 4.0;
				// center is average plus random offset
				grid[x + halfSide][y + halfSide] =
				// We calculate random value in range of 2h
				// and then subtract h so the end value is
				// in the range (-h, +h)
				avg + (this.r.nextDouble() * 2 * this.h) - this.h;
			}
		}
	}

	private void warpDiamon(int sideLength, int halfSide) {
		// generate the diamond values
		// since the diamonds are staggered we only move x
		// by half side
		for (int x = 0; x < this.DATA_SIZE - 1; x += halfSide) {
			// and y is x offset by half a side, but moved by
			// the full side length
			for (int y = (x + halfSide) % sideLength; y < this.DATA_SIZE - 1; y += sideLength) {
				// x, y is center of diamond
				// note we must use mod and add DATA_SIZE for subtraction
				// so that we can wrap around the array to find the corners
				double avg = grid[(x - halfSide + this.DATA_SIZE - 1)
						% (this.DATA_SIZE - 1)][y]
						+ grid[(x + halfSide) % (this.DATA_SIZE - 1)][y]
						+ grid[x][(y + halfSide) % (this.DATA_SIZE - 1)]
						+ grid[x][(y - halfSide + this.DATA_SIZE - 1)
								% (this.DATA_SIZE - 1)];
				avg /= 4.0;
				avg = avg + (this.r.nextDouble() * 2 * this.h) - this.h;
				// update value for center of diamond
				grid[x][y] = avg;
				// wrap values on the edges, remove
				// this and adjust loop condition above
				// for non-wrapping values.
				if (x == 0)
					grid[DATA_SIZE - 1][y] = avg;
				if (y == 0)
					grid[x][DATA_SIZE - 1] = avg;
			}
		}
	}

	/**
	 * @return the grid
	 */
	public double[][] getGrid() {
		return grid;
	}

}
