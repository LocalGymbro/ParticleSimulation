package org.example.data;

import com.badlogic.gdx.math.Vector2;
import org.example.Particle;

import java.util.ArrayList;

public class SpatialGrid {
	/**
	 * Grid represented as 3D array. 1st dimension is column, 2nd rows, 3rd particles
	 */
	private ArrayList<ArrayList<ArrayList<Particle>>> grid;
	private int cellSize;
	private Vector2 res;
	private int cols;
	private int rows;

	public SpatialGrid(int cellSize, Vector2 res) {
		this.cellSize = cellSize;
		this.res = res;
		this.cols = (int)((res.x/ cellSize)+0.5f);
		this.rows = (int)((res.y/ cellSize)+0.5f);

		this.grid = new ArrayList<>();
		for (int i = 0; i < cols; i++) {
			ArrayList<ArrayList<Particle>> temp = new ArrayList<>();
			for (int j = 0; j < rows; j++) {
				temp.add(new ArrayList<Particle>());
			}
			this.grid.add(temp);
		}
	}

	private ArrayList<ArrayList<Particle>> getColumn(int pos) {
		int adjustedPos = Math.abs(pos%this.cols);
		return grid.get(adjustedPos);
	}

	/**
	 * Get all Particles in a cell
	 * @param col column of cell
	 * @param row row of cell
	 * @return Arraylist of all particles in this cell
	 */
	public ArrayList<Particle> getCell(int col, int row) {
		int adjustedPos = Math.abs(row%this.rows);
		return this.getColumn(col).get(adjustedPos);
	}

	private int getCurrentCol(float posX) {
		return (int)(posX/res.x*cols);
	}

	private int getCurrentRow(float posY) {
		return (int)(posY/res.y*rows);
	}

	/**
	 * Gets all the cells in the specified cell radius (All cells in radius around the pos).
	 * Includes the cell the pos is in.
	 * @param cellRadius The radius of the cells to be retrieved
	 * @param pos Origin position
	 * @return Array of Cells
	 */
	public ArrayList<Particle>[] getCellsInCellRadius(int cellRadius, Vector2 pos) {
		// Calculate query area side length
		int sideLength = (cellRadius-1)*2+1;
		// Calculate amount of cells in area
		int area = sideLength*sideLength;

		// Create array of cells with the required size
		@SuppressWarnings("unchecked") ArrayList<Particle>[] cells = new ArrayList[area];

		// TODO: Maybe just save this in the Particle instead of calculating it here every time
		int currentCol = getCurrentCol(pos.x);
		int currentRow = getCurrentRow(pos.y);

		// Calculate left and bottom bounds position
		int leftCell = currentCol-(cellRadius-1);
		int bottomCell = currentRow-(cellRadius-1);

		// Calculate opposing bounding cell positions
		int rightCell = leftCell+sideLength-1;
		int topCell = bottomCell+sideLength-1;

		// Get all cells in the area
		int cellIndex = 0;
		for (int i = leftCell; i <= rightCell; i++) {
			for (int j = bottomCell; j <= topCell; j++) {
				cells[cellIndex] = getCell(i, j);
				cellIndex++;
			}
		}

		return cells;
	}

	/**
	 * Converts a decimal radius to a cellRadius, that encompasses the given radius
	 * @param radius Radius to be converted
	 * @return Cell radius
	 */
	public int convertRadiusToCellRadius(float radius) {
		return (int) radius/cellSize+1;
	}

	/**
	 * Gets all cells in radius.
	 * Same as getCellsInCellRadius, but using a decimal radius, that's automatically being converted to a cellRadius
	 * @param radius Decimal radius
	 * @param pos Origin position
	 * @return Array of Cells
	 */
	public ArrayList<Particle>[] getCellsInRadius(float radius, Vector2 pos) {
		int cellRadius = convertRadiusToCellRadius(radius);
		return getCellsInCellRadius(cellRadius, pos);
	}

	public void addParticleToGrid(Particle particle) {
		Vector2 pos = particle.getPosition();
		int currentCol = getCurrentCol(pos.x);
		int currentRow = getCurrentRow(pos.y);

		getCell(currentCol, currentRow).add(particle);
		if (particle.getCell() != null) {
			Vector2 cell = particle.getCell();
			int col = (int) cell.x;
			int row = (int) cell.y;
			getCell(col, row).remove(particle);
		}
		particle.setCell(new Vector2(currentCol, currentRow));
	}
}