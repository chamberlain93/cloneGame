package controller;

import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import boardobject.Firildak;
import boardobject.Gizmo;
import boardobject.LeftTokat;
import boardobject.RightTokat;
import boardobject.SquareTakoz;
import boardobject.TriangleTakoz;
import enums.DirectionEnum;
import enums.ModeEnum;
import enums.StateEnum;
import model.BoardModel;

public class MoveGizmoController extends Controller{

	private Gizmo taggedGizmo;
	boolean hideGizmo = true;
	private Timer timer = new Timer(300, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getTaggedGizmo() != null) {
				if (isHideGizmo()) {
					BoardModel.getInstance().hideGizmo(getTaggedGizmo());
					setHideGizmo(false);
				} else {
					BoardModel.getInstance().showGizmo(getTaggedGizmo());
					setHideGizmo(true);
				}
			}
		}
	});

	public Gizmo getTaggedGizmo() {
		return taggedGizmo;
	}

	public void setTaggedGizmo(Gizmo taggedGizmo) {
		this.taggedGizmo = taggedGizmo;
	}

	public boolean isHideGizmo() {
		return hideGizmo;
	}

	public void setHideGizmo(boolean hideGizmo) {
		this.hideGizmo = hideGizmo;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * @requires Game to be in Building Mode
	 * @modifies The state of the model
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.BUILDING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.MOVE_GIZMO);
			if (!isHideGizmo()) {
				BoardModel.getInstance().showGizmo(getTaggedGizmo());
			}
			getTimer().stop();
			setHideGizmo(true);
			setTaggedGizmo(null);
		}
	}

	/**
	 * @param event
	 *            The event that triggered the method
	 * @requires Game to be in Move Gizmo state
	 */
	public void boardClick(MouseEvent event) {
		if (BoardModel.getInstance().getState().equals(StateEnum.MOVE_GIZMO)) {
			int eventX = (int) (event.getX() / BoardModel.getL());
			int eventY = (int) (event.getY() / BoardModel.getL());

			for (int i = 0; i < BoardModel.getInstance().getGizmos().size(); i++) {
				Gizmo gizmo = BoardModel.getInstance().getGizmos().get(i);
				if (gizmo instanceof SquareTakoz) {
					if (gizmo.getX() == eventX && gizmo.getY() == eventY) {
						if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
							setTaggedGizmo(gizmo);
							BoardModel.getInstance().getOccupied()[(int) gizmo.getX()][(int) gizmo.getY()] = false;
							getTimer().start();
							break;
						}
					}
				} else if (gizmo instanceof TriangleTakoz) {
					if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.SOUTH_WEST)) {
						int[] xCoordinates = new int[3];
						int[] yCoordinates = new int[3];
						xCoordinates[0] = (int) gizmo.getX() * BoardModel.getL();
						xCoordinates[1] = (int) gizmo.getX() * BoardModel.getL();
						xCoordinates[2] = (int) (gizmo.getX() + 1) * BoardModel.getL();

						yCoordinates[0] = (int) gizmo.getY() * BoardModel.getL();
						yCoordinates[1] = (int) (gizmo.getY() - 1) * BoardModel.getL();
						yCoordinates[2] = (int) gizmo.getY() * BoardModel.getL();

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								BoardModel.getInstance()
										.getOccupied()[(int) gizmo.getX()][(int) (gizmo.getY() - 1)] = false;
								getTimer().start();
								break;
							}
						}
					} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.SOUTH_EAST)) {
						int[] xCoordinates = new int[3];
						int[] yCoordinates = new int[3];
						xCoordinates[0] = (int) gizmo.getX() * BoardModel.getL();
						xCoordinates[1] = (int) (gizmo.getX() - 1) * BoardModel.getL();
						xCoordinates[2] = (int) gizmo.getX() * BoardModel.getL();

						yCoordinates[0] = (int) gizmo.getY() * BoardModel.getL();
						yCoordinates[1] = (int) gizmo.getY() * BoardModel.getL();
						yCoordinates[2] = (int) (gizmo.getY() - 1) * BoardModel.getL();
						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								BoardModel.getInstance()
										.getOccupied()[(int) (gizmo.getX() - 1)][(int) (gizmo.getY() - 1)] = false;
								getTimer().start();
								break;
							}
						}
					} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.NORTH_EAST)) {
						int[] xCoordinates = new int[3];
						int[] yCoordinates = new int[3];
						xCoordinates[0] = (int) gizmo.getX() * BoardModel.getL();
						xCoordinates[1] = (int) gizmo.getX() * BoardModel.getL();
						xCoordinates[2] = (int) (gizmo.getX() - 1) * BoardModel.getL();

						yCoordinates[0] = (int) gizmo.getY() * BoardModel.getL();
						yCoordinates[1] = (int) (gizmo.getY() + 1) * BoardModel.getL();
						yCoordinates[2] = (int) gizmo.getY() * BoardModel.getL();
						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								BoardModel.getInstance().getOccupied()[(int) (gizmo.getX() - 1)][(int) gizmo
										.getY()] = false;
								getTimer().start();
								break;
							}
						}
					} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.NORTH_WEST)) {
						int[] xCoordinates = new int[3];
						int[] yCoordinates = new int[3];
						xCoordinates[0] = (int) gizmo.getX() * BoardModel.getL();
						xCoordinates[1] = (int) (gizmo.getX() + 1) * BoardModel.getL();
						xCoordinates[2] = (int) gizmo.getX() * BoardModel.getL();

						yCoordinates[0] = (int) gizmo.getY() * BoardModel.getL();
						yCoordinates[1] = (int) gizmo.getY() * BoardModel.getL();
						yCoordinates[2] = (int) (gizmo.getY() + 1) * BoardModel.getL();
						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								BoardModel.getInstance().getOccupied()[(int) gizmo.getX()][(int) gizmo.getY()] = false;
								getTimer().start();
								break;
							}
						}
					}
				} else if (gizmo instanceof Firildak) {
					if (gizmo.getX() == eventX && gizmo.getY() == eventY) {
						if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
							setTaggedGizmo(gizmo);
							for (int k = (int) (gizmo.getX() - 1); k <= (int) (gizmo.getX() + 1); k++) {
								for (int j = (int) (gizmo.getY() - 1); j <= (int) (gizmo.getY() + 1); j++) {
									BoardModel.getInstance().getOccupied()[k][j] = false;
								}
							}
							getTimer().start();
							break;
						}
					}
				} else if (gizmo instanceof LeftTokat) {
					if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.SOUTH)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() + BoardModel.getL() / 2);
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL() + BoardModel.getL() / 2);

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY() + 2) * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY() + 2) * BoardModel.getL());
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL());

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
									for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.EAST)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL() + 2 * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() + 2 * BoardModel.getL());
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL());

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY()) * BoardModel.getL() - BoardModel.getL() / 2);
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL() - BoardModel.getL() / 2);

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
									for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.NORTH)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() - BoardModel.getL() / 2);
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL() - BoardModel.getL() / 2);

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY()) * BoardModel.getL() - 2 * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY()) * BoardModel.getL() - 2 * BoardModel.getL());
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL());

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
									for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.WEST)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL() - 2 * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() - 2 * BoardModel.getL());
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL());

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY()) * BoardModel.getL() + BoardModel.getL() / 2);
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL() + BoardModel.getL() / 2);

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
									for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					}
				} else if (gizmo instanceof RightTokat) {
					if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.SOUTH)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() - BoardModel.getL() / 2);
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL() - BoardModel.getL() / 2);

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY() + 2) * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY() + 2) * BoardModel.getL());
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL());

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
									for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.EAST)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL() + 2 * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() + 2 * BoardModel.getL());
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL());

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY()) * BoardModel.getL() + BoardModel.getL() / 2);
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL() + BoardModel.getL() / 2);

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
									for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.NORTH)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() + BoardModel.getL() / 2);
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL() + BoardModel.getL() / 2);

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY()) * BoardModel.getL() - 2 * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY()) * BoardModel.getL() - 2 * BoardModel.getL());
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL());

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
									for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.WEST)) {
						int[] xCoordinates = new int[4];
						xCoordinates[0] = (int) (gizmo.getX() * BoardModel.getL());
						xCoordinates[1] = (int) (gizmo.getX() * BoardModel.getL() - 2 * BoardModel.getL());
						xCoordinates[2] = (int) (gizmo.getX() * BoardModel.getL() - 2 * BoardModel.getL());
						xCoordinates[3] = (int) (gizmo.getX() * BoardModel.getL());

						int[] yCoordinates = new int[4];
						yCoordinates[0] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[1] = (int) ((gizmo.getY()) * BoardModel.getL());
						yCoordinates[2] = (int) ((gizmo.getY()) * BoardModel.getL() - BoardModel.getL() / 2);
						yCoordinates[3] = (int) ((gizmo.getY()) * BoardModel.getL() - BoardModel.getL() / 2);

						Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
						if (polygon.contains(event.getX(), event.getY())) {
							if (BoardModel.getInstance().getCurrentPlayer() == gizmo.getOwner()) {
								setTaggedGizmo(gizmo);
								for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
									for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
										BoardModel.getInstance().getOccupied()[k][j] = false;
									}
								}
								getTimer().start();
								break;
							}
						}
					}
				}

			}
		}
	}

	/**
	 * @param event
	 *            The event that triggered the method
	 * @requires Game to be in Move Gizmo state
	 * @modifies The list of gizmos in the model
	 * @modifies The array that represents occupied space
	 */

	public void secondBoardClick(MouseEvent event) {
		if (BoardModel.getInstance().getState().equals(StateEnum.MOVE_GIZMO)) {
			int eventX = (int) (event.getX() / BoardModel.getL());
			int eventY = (int) (event.getY() / BoardModel.getL());

			if (BoardModel.getInstance().getCurrentPlayer() == BoardModel.getInstance().getPlayers().get(0)) {
				if (eventX >= 0 && eventX <= 11 && eventY >= 0 && eventY <= 18) {
					boolean moved = BoardModel.getInstance().moveGizmo(getTaggedGizmo(), eventX, eventY);
					if (!moved && !isHideGizmo()) {
						BoardModel.getInstance().showGizmo(getTaggedGizmo());

						if (getTaggedGizmo() instanceof SquareTakoz) {
							BoardModel.getInstance().getOccupied()[(int) getTaggedGizmo().getX()][(int) getTaggedGizmo()
									.getY()] = true;
						} else {
							if (getTaggedGizmo() instanceof TriangleTakoz) {
								if (((TriangleTakoz) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH_WEST) {
									BoardModel.getInstance().getOccupied()[(int) getTaggedGizmo()
											.getX()][(int) (getTaggedGizmo().getY() - 1)] = true;
								} else {
									if (((TriangleTakoz) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH_EAST) {
										BoardModel.getInstance().getOccupied()[(int) (getTaggedGizmo().getX()
												- 1)][(int) (getTaggedGizmo().getY() - 1)] = true;
									} else {
										if (((TriangleTakoz) getTaggedGizmo())
												.getDirection() == DirectionEnum.NORTH_EAST) {
											BoardModel.getInstance().getOccupied()[(int) (getTaggedGizmo().getX()
													- 1)][(int) getTaggedGizmo().getY()] = true;
										} else {
											if (((TriangleTakoz) getTaggedGizmo())
													.getDirection() == DirectionEnum.NORTH_WEST) {
												BoardModel.getInstance().getOccupied()[(int) getTaggedGizmo()
														.getX()][(int) getTaggedGizmo().getY()] = true;
											}
										}
									}
								}
							} else {
								if (getTaggedGizmo() instanceof Firildak) {
									for (int i = (int) (getTaggedGizmo().getX() - 1); i <= (int) (getTaggedGizmo()
											.getX() + 1); i++) {
										for (int j = (int) (getTaggedGizmo().getY() - 1); j <= (int) (getTaggedGizmo()
												.getY() + 1); j++) {
											BoardModel.getInstance().getOccupied()[i][j] = true;
										}
									}
								} else {
									if (getTaggedGizmo() instanceof LeftTokat) {
										if (((LeftTokat) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH) {
											for (int i = (int) (getTaggedGizmo().getX()); i <= (int) (getTaggedGizmo()
													.getX() + 1); i++) {
												for (int j = (int) (getTaggedGizmo()
														.getY()); j <= (int) (getTaggedGizmo().getY() + 1); j++) {
													BoardModel.getInstance().getOccupied()[i][j] = true;
												}
											}
										} else {
											if (((LeftTokat) getTaggedGizmo()).getDirection() == DirectionEnum.EAST) {
												for (int i = (int) (getTaggedGizmo()
														.getX()); i <= (int) (getTaggedGizmo().getX() + 1); i++) {
													for (int j = (int) (getTaggedGizmo().getY()
															- 2); j <= (int) (getTaggedGizmo().getY() - 1); j++) {
														BoardModel.getInstance().getOccupied()[i][j] = true;
													}
												}
											} else {
												if (((LeftTokat) getTaggedGizmo())
														.getDirection() == DirectionEnum.NORTH) {
													for (int i = (int) (getTaggedGizmo().getX()
															- 2); i <= (int) (getTaggedGizmo().getX() - 1); i++) {
														for (int j = (int) (getTaggedGizmo().getY()
																- 2); j <= (int) (getTaggedGizmo().getY() - 1); j++) {
															BoardModel.getInstance().getOccupied()[i][j] = true;
														}
													}
												} else {
													if (((LeftTokat) getTaggedGizmo())
															.getDirection() == DirectionEnum.WEST) {
														for (int i = (int) (getTaggedGizmo().getX()
																- 2); i <= (int) (getTaggedGizmo().getX() - 1); i++) {
															for (int j = (int) (getTaggedGizmo()
																	.getY()); j <= (int) (getTaggedGizmo().getY()
																			+ 1); j++) {
																BoardModel.getInstance().getOccupied()[i][j] = true;
															}
														}
													}
												}
											}
										}
									} else {
										if (getTaggedGizmo() instanceof RightTokat) {
											if (((RightTokat) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH) {
												for (int i = (int) (getTaggedGizmo().getX()
														- 2); i <= (int) (getTaggedGizmo().getX() - 1); i++) {
													for (int j = (int) (getTaggedGizmo()
															.getY()); j <= (int) (getTaggedGizmo().getY() + 1); j++) {
														BoardModel.getInstance().getOccupied()[i][j] = true;
													}
												}
											} else {
												if (((RightTokat) getTaggedGizmo())
														.getDirection() == DirectionEnum.EAST) {
													for (int i = (int) (getTaggedGizmo()
															.getX()); i <= (int) (getTaggedGizmo().getX() + 1); i++) {
														for (int j = (int) (getTaggedGizmo()
																.getY()); j <= (int) (getTaggedGizmo().getY()
																		+ 1); j++) {
															BoardModel.getInstance().getOccupied()[i][j] = true;
														}
													}
												} else {
													if (((RightTokat) getTaggedGizmo())
															.getDirection() == DirectionEnum.NORTH) {
														for (int i = (int) (getTaggedGizmo()
																.getX()); i <= (int) (getTaggedGizmo().getX()
																		+ 1); i++) {
															for (int j = (int) (getTaggedGizmo().getY()
																	- 2); j <= (int) (getTaggedGizmo().getY()
																			- 1); j++) {
																BoardModel.getInstance().getOccupied()[i][j] = true;
															}
														}
													} else {
														if (((RightTokat) getTaggedGizmo())
																.getDirection() == DirectionEnum.WEST) {
															for (int i = (int) (getTaggedGizmo().getX()
																	- 2); i <= (int) (getTaggedGizmo().getX()
																			- 1); i++) {
																for (int j = (int) (getTaggedGizmo().getY()
																		- 2); j <= (int) (getTaggedGizmo().getY()
																				- 1); j++) {
																	BoardModel.getInstance().getOccupied()[i][j] = true;
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}

					}
					getTimer().stop();
					setHideGizmo(true);
					setTaggedGizmo(null);
				}
			} else {
				if (eventX >= 13 && eventX <= 24 && eventY >= 0 && eventY <= 18) {
					boolean moved = BoardModel.getInstance().moveGizmo(getTaggedGizmo(), eventX, eventY);
					if (!moved && !isHideGizmo()) {
						BoardModel.getInstance().showGizmo(getTaggedGizmo());

						if (getTaggedGizmo() instanceof SquareTakoz) {
							BoardModel.getInstance().getOccupied()[(int) getTaggedGizmo().getX()][(int) getTaggedGizmo()
									.getY()] = true;
						} else {
							if (getTaggedGizmo() instanceof TriangleTakoz) {
								if (((TriangleTakoz) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH_WEST) {
									BoardModel.getInstance().getOccupied()[(int) getTaggedGizmo()
											.getX()][(int) (getTaggedGizmo().getY() - 1)] = true;
								} else {
									if (((TriangleTakoz) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH_EAST) {
										BoardModel.getInstance().getOccupied()[(int) (getTaggedGizmo().getX()
												- 1)][(int) (getTaggedGizmo().getY() - 1)] = true;
									} else {
										if (((TriangleTakoz) getTaggedGizmo())
												.getDirection() == DirectionEnum.NORTH_EAST) {
											BoardModel.getInstance().getOccupied()[(int) (getTaggedGizmo().getX()
													- 1)][(int) getTaggedGizmo().getY()] = true;
										} else {
											if (((TriangleTakoz) getTaggedGizmo())
													.getDirection() == DirectionEnum.NORTH_WEST) {
												BoardModel.getInstance().getOccupied()[(int) getTaggedGizmo()
														.getX()][(int) getTaggedGizmo().getY()] = true;
											}
										}
									}
								}
							} else {
								if (getTaggedGizmo() instanceof Firildak) {
									for (int i = (int) (getTaggedGizmo().getX() - 1); i <= (int) (getTaggedGizmo()
											.getX() + 1); i++) {
										for (int j = (int) (getTaggedGizmo().getY() - 1); j <= (int) (getTaggedGizmo()
												.getY() + 1); j++) {
											BoardModel.getInstance().getOccupied()[i][j] = true;
										}
									}
								} else {
									if (getTaggedGizmo() instanceof LeftTokat) {
										if (((LeftTokat) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH) {
											for (int i = (int) (getTaggedGizmo().getX()); i <= (int) (getTaggedGizmo()
													.getX() + 1); i++) {
												for (int j = (int) (getTaggedGizmo()
														.getY()); j <= (int) (getTaggedGizmo().getY() + 1); j++) {
													BoardModel.getInstance().getOccupied()[i][j] = true;
												}
											}
										} else {
											if (((LeftTokat) getTaggedGizmo()).getDirection() == DirectionEnum.EAST) {
												for (int i = (int) (getTaggedGizmo()
														.getX()); i <= (int) (getTaggedGizmo().getX() + 1); i++) {
													for (int j = (int) (getTaggedGizmo().getY()
															- 2); j <= (int) (getTaggedGizmo().getY() - 1); j++) {
														BoardModel.getInstance().getOccupied()[i][j] = true;
													}
												}
											} else {
												if (((LeftTokat) getTaggedGizmo())
														.getDirection() == DirectionEnum.NORTH) {
													for (int i = (int) (getTaggedGizmo().getX()
															- 2); i <= (int) (getTaggedGizmo().getX() - 1); i++) {
														for (int j = (int) (getTaggedGizmo().getY()
																- 2); j <= (int) (getTaggedGizmo().getY() - 1); j++) {
															BoardModel.getInstance().getOccupied()[i][j] = true;
														}
													}
												} else {
													if (((LeftTokat) getTaggedGizmo())
															.getDirection() == DirectionEnum.WEST) {
														for (int i = (int) (getTaggedGizmo().getX()
																- 2); i <= (int) (getTaggedGizmo().getX() - 1); i++) {
															for (int j = (int) (getTaggedGizmo()
																	.getY()); j <= (int) (getTaggedGizmo().getY()
																			+ 1); j++) {
																BoardModel.getInstance().getOccupied()[i][j] = true;
															}
														}
													}
												}
											}
										}
									} else {
										if (getTaggedGizmo() instanceof RightTokat) {
											if (((RightTokat) getTaggedGizmo()).getDirection() == DirectionEnum.SOUTH) {
												for (int i = (int) (getTaggedGizmo().getX()
														- 2); i <= (int) (getTaggedGizmo().getX() - 1); i++) {
													for (int j = (int) (getTaggedGizmo()
															.getY()); j <= (int) (getTaggedGizmo().getY() + 1); j++) {
														BoardModel.getInstance().getOccupied()[i][j] = true;
													}
												}
											} else {
												if (((RightTokat) getTaggedGizmo())
														.getDirection() == DirectionEnum.EAST) {
													for (int i = (int) (getTaggedGizmo()
															.getX()); i <= (int) (getTaggedGizmo().getX() + 1); i++) {
														for (int j = (int) (getTaggedGizmo()
																.getY()); j <= (int) (getTaggedGizmo().getY()
																		+ 1); j++) {
															BoardModel.getInstance().getOccupied()[i][j] = true;
														}
													}
												} else {
													if (((RightTokat) getTaggedGizmo())
															.getDirection() == DirectionEnum.NORTH) {
														for (int i = (int) (getTaggedGizmo()
																.getX()); i <= (int) (getTaggedGizmo().getX()
																		+ 1); i++) {
															for (int j = (int) (getTaggedGizmo().getY()
																	- 2); j <= (int) (getTaggedGizmo().getY()
																			- 1); j++) {
																BoardModel.getInstance().getOccupied()[i][j] = true;
															}
														}
													} else {
														if (((RightTokat) getTaggedGizmo())
																.getDirection() == DirectionEnum.WEST) {
															for (int i = (int) (getTaggedGizmo().getX()
																	- 2); i <= (int) (getTaggedGizmo().getX()
																			- 1); i++) {
																for (int j = (int) (getTaggedGizmo().getY()
																		- 2); j <= (int) (getTaggedGizmo().getY()
																				- 1); j++) {
																	BoardModel.getInstance().getOccupied()[i][j] = true;
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}

					}
					getTimer().stop();
					setHideGizmo(true);
					setTaggedGizmo(null);
				}
			}

		}
	}

}
