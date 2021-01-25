package com.rs.custom.route;

import com.rs.custom.CustomUtilities;
import com.rs.custom.route.strategy.*;
import com.rs.custom.route.strategy.FloorItemDefinitions;
import com.rs.custom.route.strategy.ObjectDefinitions;
import com.rs.game.Entity;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.tools.DebugLine;
import com.rs.utils.Utils;

public class RouteEvent {

	/**
	 * Object to which we are finding the route.
	 */
	private Object targetObject;
	/**
	 * The event instance.
	 */
	private Runnable event;
	/**
	 * Whether we also run on alternative.
	 */
	private boolean alternative;
	/**
	 * Contains last route definitions.
	 */
	private RouteDefinitions[] last;

	public RouteEvent(Object targetObject, Runnable event) {
		this(targetObject, event, false);
	}

	public RouteEvent(Object targetObject, Runnable event, boolean alternative) {
		this.targetObject = targetObject;
		this.event = event;
		this.alternative = alternative;
	}

	public boolean processEvent(final NPC npc) {
		if (!isTargetObjectInSamePlane(npc)) {
			return true;
		}
		if (npc.getFreezeDelay() > Utils.currentTimeMillis())
			return true;
		RouteDefinitions[] definitions = generateDefinitions();
		if (definitions == null)
			return false;
		else if (last != null && match(definitions, last) && npc.hasWalkSteps())
			return false;
		else if (last != null && match(definitions, last) && !npc.hasWalkSteps()) {
			for (int i = 0; i < definitions.length; i++) {
				RouteDefinitions strategy = definitions[i];
				int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, npc.getX(), npc.getY(), npc.getPlane(),	npc.getSize(), strategy, i == (definitions.length - 1));
				if (steps == -1)
					continue;
				if ((!RouteFinder.lastIsAlternative() && steps <= 0) || alternative) {
					event.run();
					return true;
				}
			}
			return true;
		} else {
			last = definitions;

			for (int i = 0; i < definitions.length; i++) {
				RouteDefinitions strategy = definitions[i];
				int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, npc.getX(), npc.getY(), npc.getPlane(),	npc.getSize(), strategy,
						i == (definitions.length - 1));
				if (steps == -1)
					continue;
				if ((!RouteFinder.lastIsAlternative() && steps <= 0)) {
					event.run();
					return true;
				}
				int[] bufferX = RouteFinder.getLastPathBufferX();
				int[] bufferY = RouteFinder.getLastPathBufferY();
				npc.resetWalkSteps();
				if (npc.getFreezeDelay() > Utils.currentTimeMillis())
					return false;
				for (int step = steps - 1; step >= 0; step--) {
					if (!npc.addWalkSteps(bufferX[step], bufferY[step], 25, true))
						break;
				}

				return false;
			}
			return true;
		}
	}

	public boolean processEvent(final Player player) {
		if (!isTargetObjectInSamePlane(player)) {
			player.getPackets().sendGameMessage("You can't reach that.");
			player.getPackets().sendResetMinimapFlag();
			return true;
		}
		if (player.getLockDelay() > Utils.currentTimeMillis())
			return true;
		RouteDefinitions[] strategies = generateDefinitions();
		if (strategies == null)
			return false;
		else if (last != null && match(strategies, last) && player.hasWalkSteps())
			return false;
		else if (last != null && match(strategies, last) && !player.hasWalkSteps()) {
			for (int i = 0; i < strategies.length; i++) {
				RouteDefinitions strategy = strategies[i];
				int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, player.getX(), player.getY(),
						player.getPlane(), player.getSize(), strategy, i == (strategies.length - 1));
				if (steps == -1)
					continue;
				if ((!RouteFinder.lastIsAlternative() && steps <= 0) || alternative) {
					if (alternative)
						player.getPackets().sendResetMinimapFlag();
					if (player.getNextFaceEntity() != -1)
						player.setNextFaceEntity(null);
					event.run();
					return true;
				}
			}

			player.getPackets().sendGameMessage("You can't reach that.");
			player.getPackets().sendResetMinimapFlag();
			return true;
		} else {
			last = strategies;

			for (int i = 0; i < strategies.length; i++) {
				RouteDefinitions strategy = strategies[i];
				int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, player.getX(), player.getY(),
						player.getPlane(), player.getSize(), strategy, i == (strategies.length - 1));
				if (steps == -1)
					continue;
				if ((!RouteFinder.lastIsAlternative() && steps <= 0)) {
					if (alternative)
						player.getPackets().sendResetMinimapFlag();
					if (player.getNextFaceEntity() != -1)
						player.setNextFaceEntity(null);
					DebugLine.print("");
					event.run();
					return true;
				}
				int[] bufferX = RouteFinder.getLastPathBufferX();
				int[] bufferY = RouteFinder.getLastPathBufferY();

				WorldTile last = new WorldTile(bufferX[0], bufferY[0], player.getPlane());
				player.resetWalkSteps();
				player.setNextFaceWorldTile(new WorldTile(last.getX(), last.getY(), last.getPlane()));
				player.getPackets().sendMinimapFlag(
						last.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()),
						last.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()));
				if (player.getFreezeDelay() > Utils.currentTimeMillis())
					return false;
				for (int step = steps - 1; step >= 0; step--) {
					if (!player.addWalkSteps(bufferX[step], bufferY[step], 25, true))
						break;
				}
				return false;
			}

			player.getPackets().sendGameMessage("You can't reach that.");
			player.getPackets().sendResetMinimapFlag();
			return true;
		}
	}

	private boolean isTargetObjectInSamePlane(Player player) {
		if (targetObject == null)
			return false;
		if (targetObject instanceof Entity) {
			return player.getPlane() == ((Entity) targetObject).getPlane();
		} else if (targetObject instanceof WorldObject) {
			return player.getPlane() == ((WorldObject) targetObject).getPlane();
		} else if (targetObject instanceof FloorItem) {
			return player.getPlane() == ((FloorItem) targetObject).getTile().getPlane();
		} else {
			throw new RuntimeException(targetObject + " is not instanceof any reachable entity.");
		}
	}

	private boolean isTargetObjectInSamePlane(NPC npc) {
		if (targetObject == null)
			return false;
		if (targetObject instanceof Entity) {
			return npc.getPlane() == ((Entity) targetObject).getPlane();
		} else if (targetObject instanceof WorldObject) {
			return npc.getPlane() == ((WorldObject) targetObject).getPlane();
		} else if (targetObject instanceof FloorItem) {
			return npc.getPlane() == ((FloorItem) targetObject).getTile().getPlane();
		} else {
			throw new RuntimeException(targetObject + " is not instanceof any reachable entity.");
		}
	}

	private RouteDefinitions[] generateDefinitions() {
		if (targetObject == null)
			return last;
		if (targetObject instanceof Entity) {
			return new RouteDefinitions[] { new EntityDefinitions((Entity) targetObject) };
		} else if (targetObject instanceof WorldObject) {
			return new RouteDefinitions[] { new ObjectDefinitions((WorldObject) targetObject) };
		} else if (targetObject instanceof FloorItem) {
			FloorItem item = (FloorItem) targetObject;
			return new RouteDefinitions[] { new FixedTileDefinitions(item.getTile().getX(), item.getTile().getY()), new FloorItemDefinitions(item) };
		} else {
			throw new RuntimeException(targetObject + " is not instanceof any reachable entity.");
		}
	}

	private boolean match(RouteDefinitions[] a1, RouteDefinitions[] a2) {
		if (a1.length != a2.length)
			return false;
		for (int i = 0; i < a1.length; i++)
			if (!a1[i].equals(a2[i]))
				return false;
		return true;
	}

}
