package com.rs.custom.route.strategy;

import com.rs.custom.route.RouteDefinitions;

public class FixedTileDefinitions extends RouteDefinitions {

	/**
	 * X position of tile.
	 */
	private int x;
	/**
	 * Y position of tile.
	 */
	private int y;

	public FixedTileDefinitions(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean canExit(int currentX, int currentY, int sizeXY, int[][] clip, int clipBaseX, int clipBaseY) {
		return currentX == x && currentY == y;
	}

	@Override
	public int getApproxDestinationX() {
		return x;
	}

	@Override
	public int getApproxDestinationY() {
		return y;
	}

	@Override
	public int getApproxDestinationSizeX() {
		return 1;
	}

	@Override
	public int getApproxDestinationSizeY() {
		return 1;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof FixedTileDefinitions))
			return false;
		FixedTileDefinitions strategy = (FixedTileDefinitions) other;
		return x == strategy.x && y == strategy.y;
	}

}
