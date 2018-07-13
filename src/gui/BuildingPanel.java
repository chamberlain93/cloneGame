package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import boardobject.*;
import enums.DirectionEnum;
import model.BoardModel;

@SuppressWarnings("serial")
public class BuildingPanel extends JPanel implements MouseListener {

	private final static double L = 20;

	private boolean firstClickForMove = false;

	public static double getL() {
		return L;
	}

	public boolean isFirstClickForMove() {
		return firstClickForMove;
	}

	public void setFirstClickForMove(boolean firstClickForMove) {
		this.firstClickForMove = firstClickForMove;
	}

	public BuildingPanel() {
		addMouseListener(this);
		requestFocus();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(new ImageIcon(this.getClass().getResource("background.jpg")).getImage(), 0, 0,
				(int) (25 * getL() + 225), (int) (25 * getL() + 32), this);

		Font playerFont = new Font("arial", Font.BOLD, 20);
		g.setFont(playerFont);
		g.setColor(Color.DARK_GRAY);
		g.drawString("Player 1", 82, 420);
		g.drawString("Player 2", 342, 420);

		paintLines(g);

		paintBalls(g);
		paintCezmis(g);
		paintEngel(g);
		paintWalls(g);
		paintGizmos(g);
		paintCezerye(g);
	}

	public void paintLines(Graphics g) {
		for (int i = 0; i <= 25; i++) {
			g.setColor(Color.BLACK);
			g.drawLine((int) (i * getL()), (int) 0, (int) (i * getL()), (int) (19 * getL()));
		}
		for (int i = 0; i < 20; i++) {
			g.setColor(Color.BLACK);
			g.drawLine((int) 0, (int) (i * getL()), (int) (12 * getL()), (int) (i * getL()));
		}
		for (int i = 0; i < 20; i++) {
			g.setColor(Color.BLACK);
			g.drawLine((int) (int) (13 * getL()), (int) (i * getL()), (int) (25 * getL()), (int) (i * getL()));
		}
	}

	public void paintBalls(Graphics g) {
		if (!BoardModel.getInstance().getBalls().isEmpty()) {
			for (Ball ball : BoardModel.getInstance().getBalls()) {
				Rectangle clipRectangle = g.getClipBounds();
				Rectangle ballBoundingBox = new Rectangle((int) (ball.getX() - ball.getWidth() / 2),
						(int) (ball.getY() - ball.getHeight() / 2), (int) ball.getWidth(), (int) ball.getHeight());
				if (clipRectangle.intersects(ballBoundingBox)) {
					g.setColor(ball.getColor());
					g.fillOval((int) (getL() * (ball.getX() - ball.getWidth() / 2)),
							(int) (getL() * (ball.getY() - ball.getHeight() / 2)), (int) (getL() * ball.getWidth()),
							(int) (getL() * ball.getHeight()));
				}
			}
		}
	}

	public void paintCezmis(Graphics g) {
		if (!BoardModel.getInstance().getCezmis().isEmpty()) {
			for (Cezmi cezmi : BoardModel.getInstance().getCezmis()) {
				g.setColor(cezmi.getColor());
				g.fillOval((int) (getL() * (cezmi.getX() - cezmi.getWidth() / 2)),
						(int) (getL() * (cezmi.getY() - cezmi.getHeight())), (int) (getL() * cezmi.getWidth()),
						(int) (getL() * cezmi.getWidth()));
			}
		}
	}

	public void paintEngel(Graphics g) {
		if (BoardModel.getInstance().getEngel() != null) {
			Engel engel = BoardModel.getInstance().getEngel();
			g.setColor(engel.getColor());
			g.fillRect((int) (getL() * engel.getX()), (int) (getL() * engel.getY()), (int) (getL() * engel.getWidth()),
					(int) (getL() * engel.getHeight()));
		}
	}

	public void paintWalls(Graphics g) {
		if (!BoardModel.getInstance().getWalls().isEmpty()) {
			for (Wall wall : BoardModel.getInstance().getWalls()) {
				g.setColor(wall.getColor());
				g.fillRect((int) (getL() * wall.getX()), (int) (getL() * wall.getY()), (int) (getL() * wall.getWidth()),
						(int) (getL() * wall.getHeight()));
			}
		}
	}

	public void paintGizmos(Graphics g) {
		if (!BoardModel.getInstance().getGizmos().isEmpty()) {
			for (Gizmo gizmo : BoardModel.getInstance().getGizmos()) {
				if (gizmo instanceof Firildak) {
					Polygon poly = new Polygon();
					poly.addPoint((int) (getL() * ((Firildak) gizmo).getInitTopLeft().x()),
							(int) (getL() * ((Firildak) gizmo).getInitTopLeft().y()));
					poly.addPoint((int) (getL() * ((Firildak) gizmo).getInitTopRight().x()),
							(int) (getL() * ((Firildak) gizmo).getInitTopRight().y()));
					poly.addPoint((int) (getL() * ((Firildak) gizmo).getInitBottomRight().x()),
							(int) (getL() * ((Firildak) gizmo).getInitBottomRight().y()));
					poly.addPoint((int) (getL() * ((Firildak) gizmo).getInitBottomLeft().x()),
							(int) (getL() * ((Firildak) gizmo).getInitBottomLeft().y()));
					g.setColor(gizmo.getColor());
					g.fillPolygon(poly);
				} else {
					if (gizmo instanceof TriangleTakoz) {
						double corner1x = gizmo.getX();
						double corner1y = gizmo.getY() - gizmo.getHeight();
						double corner2x = gizmo.getX() + gizmo.getWidth();
						double corner2y = gizmo.getY();

						switch (((TriangleTakoz) gizmo).getDirection()) {
						case SOUTH_WEST:
							corner1x = gizmo.getX();
							corner1y = gizmo.getY() - gizmo.getHeight();
							corner2x = gizmo.getX() + gizmo.getWidth();
							corner2y = gizmo.getY();
							break;
						case SOUTH_EAST:
							corner1x = gizmo.getX();
							corner1y = gizmo.getY() - gizmo.getHeight();
							corner2x = gizmo.getX() - gizmo.getWidth();
							corner2y = gizmo.getY();
							break;
						case NORTH_EAST:
							corner1x = gizmo.getX() - gizmo.getWidth();
							corner1y = gizmo.getY();
							corner2x = gizmo.getX();
							corner2y = gizmo.getY() + gizmo.getHeight();
							break;
						case NORTH_WEST:
							corner1x = gizmo.getX() + gizmo.getWidth();
							corner1y = gizmo.getY();
							corner2x = gizmo.getX();
							corner2y = gizmo.getY() + gizmo.getHeight();
							break;
						default:
							break;
						}
						Polygon poly = new Polygon();
						poly.addPoint((int) (getL() * corner1x), (int) (getL() * corner1y));
						poly.addPoint((int) (getL() * gizmo.getX()), (int) (getL() * gizmo.getY()));
						poly.addPoint((int) (getL() * corner2x), (int) (getL() * corner2y));
						g.setColor(gizmo.getColor());
						g.fillPolygon(poly);
					} else {
						if (gizmo instanceof SquareTakoz) {
							g.setColor(gizmo.getColor());
							g.fillRect((int) (getL() * gizmo.getX()), (int) (getL() * gizmo.getY()),
									(int) (getL() * gizmo.getWidth()), (int) (getL() * gizmo.getHeight()));
						} else {
							if (gizmo instanceof LeftTokat) {
								double corner1x = ((LeftTokat) gizmo).getTopLine().p1().x();
								double corner1y = ((LeftTokat) gizmo).getTopLine().p1().y();

								double corner2x = ((LeftTokat) gizmo).getRightLine().p1().x();
								double corner2y = ((LeftTokat) gizmo).getRightLine().p1().y();

								double corner3x = ((LeftTokat) gizmo).getBottomLine().p2().x();
								double corner3y = ((LeftTokat) gizmo).getBottomLine().p2().y();

								double corner4x = ((LeftTokat) gizmo).getLeftLine().p2().x();
								double corner4y = ((LeftTokat) gizmo).getLeftLine().p2().y();

								Polygon poly = new Polygon();
								poly.addPoint((int) (getL() * corner1x), (int) (getL() * corner1y));
								poly.addPoint((int) (getL() * corner2x), (int) (getL() * corner2y));
								poly.addPoint((int) (getL() * corner3x), (int) (getL() * corner3y));
								poly.addPoint((int) (getL() * corner4x), (int) (getL() * corner4y));
								g.setColor(gizmo.getColor());
								g.fillPolygon(poly);

								double headCenterX = ((LeftTokat) gizmo).getHeadCircle().getCenter().x();
								double headCenterY = ((LeftTokat) gizmo).getHeadCircle().getCenter().y();
								double headCenterRadius = ((LeftTokat) gizmo).getHeadCircle().getRadius();
								g.fillOval((int) (getL() * (headCenterX - headCenterRadius)),
										(int) (getL() * (headCenterY - headCenterRadius)),
										(int) (getL() * (2 * headCenterRadius)),
										(int) (getL() * (2 * headCenterRadius)));

								double tailCenterX = ((LeftTokat) gizmo).getTailCircle().getCenter().x();
								double tailCenterY = ((LeftTokat) gizmo).getTailCircle().getCenter().y();
								double tailCenterRadius = ((LeftTokat) gizmo).getTailCircle().getRadius();
								g.fillOval((int) (getL() * (tailCenterX - tailCenterRadius)),
										(int) (getL() * (tailCenterY - tailCenterRadius)),
										(int) (getL() * (2 * tailCenterRadius)),
										(int) (getL() * (2 * tailCenterRadius)));

								g.setColor(Color.GREEN);
								g.fillOval((int) (getL() * (headCenterX - 0.2)), (int) (getL() * (headCenterY - 0.2)),
										(int) (getL() * (0.4)), (int) (getL() * (0.4)));
							} else {
								if (gizmo instanceof RightTokat) {
									double corner1x = ((RightTokat) gizmo).getTopLine().p1().x();
									double corner1y = ((RightTokat) gizmo).getTopLine().p1().y();

									double corner2x = ((RightTokat) gizmo).getRightLine().p1().x();
									double corner2y = ((RightTokat) gizmo).getRightLine().p1().y();

									double corner3x = ((RightTokat) gizmo).getBottomLine().p2().x();
									double corner3y = ((RightTokat) gizmo).getBottomLine().p2().y();

									double corner4x = ((RightTokat) gizmo).getLeftLine().p2().x();
									double corner4y = ((RightTokat) gizmo).getLeftLine().p2().y();

									Polygon poly = new Polygon();
									poly.addPoint((int) (getL() * corner1x), (int) (getL() * corner1y));
									poly.addPoint((int) (getL() * corner2x), (int) (getL() * corner2y));
									poly.addPoint((int) (getL() * corner3x), (int) (getL() * corner3y));
									poly.addPoint((int) (getL() * corner4x), (int) (getL() * corner4y));
									g.setColor(gizmo.getColor());
									g.fillPolygon(poly);

									double headCenterX = ((RightTokat) gizmo).getHeadCircle().getCenter().x();
									double headCenterY = ((RightTokat) gizmo).getHeadCircle().getCenter().y();
									double headCenterRadius = ((RightTokat) gizmo).getHeadCircle().getRadius();
									g.fillOval((int) (getL() * (headCenterX - headCenterRadius)),
											(int) (getL() * (headCenterY - headCenterRadius)),
											(int) (getL() * (2 * headCenterRadius)),
											(int) (getL() * (2 * headCenterRadius)));

									double tailCenterX = ((RightTokat) gizmo).getTailCircle().getCenter().x();
									double tailCenterY = ((RightTokat) gizmo).getTailCircle().getCenter().y();
									double tailCenterRadius = ((RightTokat) gizmo).getTailCircle().getRadius();
									g.fillOval((int) (getL() * (tailCenterX - tailCenterRadius)),
											(int) (getL() * (tailCenterY - tailCenterRadius)),
											(int) (getL() * (2 * tailCenterRadius)),
											(int) (getL() * (2 * tailCenterRadius)));

									g.setColor(Color.GREEN);
									g.fillOval((int) (getL() * (headCenterX - 0.2)),
											(int) (getL() * (headCenterY - 0.2)), (int) (getL() * (0.4)),
											(int) (getL() * (0.4)));
								}
							}
						}
					}
				}
			}
		}
	}

	public void paintCezerye(Graphics g) {
		if (BoardModel.getInstance().getCezerye() != null) {
			Cezerye cezerye = BoardModel.getInstance().getCezerye();
			g.setColor(cezerye.getColor());
			g.fillRect((int) (getL() * cezerye.getX()), (int) (getL() * cezerye.getY()),
					(int) (getL() * cezerye.getWidth()), (int) (getL() * cezerye.getHeight()));
		}
	}

	public void repaintBall(Ball ball) {
		repaint((int) (getL() * (ball.getX() - ball.getWidth())), (int) (getL() * (ball.getY() - ball.getHeight())),
				(int) (getL() * (2 * ball.getWidth())), (int) (getL() * (2 * ball.getHeight())));
	}

	public void repaintCezerye(Cezerye cezerye) {
		repaint((int) (getL() * (cezerye.getX() - cezerye.getWidth() / 2)),
				(int) (getL() * (cezerye.getY() - cezerye.getHeight() / 2)), (int) (getL() * (2 * cezerye.getWidth())),
				(int) (getL() * (2 * cezerye.getHeight())));
	}

	public void repaintCezmi(Cezmi cezmi) {
		repaint();
	}

	public void repaintFirildak(Firildak firildak) {
		repaint((int) (getL() * (firildak.getX() - firildak.getWidth() / 2)),
				(int) (getL() * (firildak.getY() - firildak.getHeight() / 2)),
				(int) (getL() * (2 * firildak.getWidth())), (int) (getL() * (2 * firildak.getHeight())));
	}

	public void repaintLeftTokat(LeftTokat leftTokat) {
		if (leftTokat.getDirection() == DirectionEnum.SOUTH) {
			repaint((int) (getL() * (leftTokat.getX() - 1)), (int) (getL() * (leftTokat.getY() - 1)),
					(int) (4 * getL()), (int) (4 * getL()));
		} else {
			if (leftTokat.getDirection() == DirectionEnum.EAST) {
				repaint((int) (getL() * (leftTokat.getX() - 1)), (int) (getL() * (leftTokat.getY() - 3)),
						(int) (4 * getL()), (int) (4 * getL()));
			} else {
				if (leftTokat.getDirection() == DirectionEnum.NORTH) {
					repaint((int) (getL() * (leftTokat.getX() - 3)), (int) (getL() * (leftTokat.getY() - 3)),
							(int) (4 * getL()), (int) (4 * getL()));
				} else {
					if (leftTokat.getDirection() == DirectionEnum.WEST) {
						repaint((int) (getL() * (leftTokat.getX() - 3)), (int) (getL() * (leftTokat.getY() - 1)),
								(int) (4 * getL()), (int) (4 * getL()));
					}
				}
			}
		}
	}

	public void repaintRightTokat(RightTokat rightTokat) {
		if (rightTokat.getDirection() == DirectionEnum.SOUTH) {
			repaint((int) (getL() * (rightTokat.getX() - 3)), (int) (getL() * (rightTokat.getY() - 1)),
					(int) (4 * getL()), (int) (4 * getL()));
		} else {
			if (rightTokat.getDirection() == DirectionEnum.EAST) {
				repaint((int) (getL() * (rightTokat.getX() - 1)), (int) (getL() * (rightTokat.getY() - 1)),
						(int) (4 * getL()), (int) (4 * getL()));
			} else {
				if (rightTokat.getDirection() == DirectionEnum.NORTH) {
					repaint((int) (getL() * (rightTokat.getX() - 1)), (int) (getL() * (rightTokat.getY() - 3)),
							(int) (4 * getL()), (int) (4 * getL()));
				} else {
					if (rightTokat.getDirection() == DirectionEnum.WEST) {
						repaint((int) (getL() * (rightTokat.getX() - 3)), (int) (getL() * (rightTokat.getY() - 3)),
								(int) (4 * getL()), (int) (4 * getL()));
					}
				}
			}
		}
	}

	public void repaintSquareTakoz(SquareTakoz squareTakoz) {
		repaint((int) (getL() * squareTakoz.getX()), (int) (getL() * squareTakoz.getY()),
				(int) (getL() * squareTakoz.getWidth()), (int) (getL() * squareTakoz.getHeight()));
	}

	public void repaintTriangleTakoz(TriangleTakoz triangleTakoz) {
		repaint((int) (getL() * (triangleTakoz.getX() - triangleTakoz.getWidth())),
				(int) (getL() * (triangleTakoz.getY() - triangleTakoz.getHeight())),
				(int) (getL() * (2 * triangleTakoz.getWidth())), (int) (getL() * (2 * triangleTakoz.getHeight())));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (BoardModel.getInstance().getState()) {
		case ADD_GIZMO:
			GameWindow.getInstance().getAddGizmoController().boardClick(e);
			break;
		case MOVE_GIZMO:
			if (!isFirstClickForMove()) {
				GameWindow.getInstance().getMoveGizmoController().boardClick(e);
				if (GameWindow.getInstance().getMoveGizmoController().getTaggedGizmo() != null) {
					setFirstClickForMove(true);
				} else {
					setFirstClickForMove(false);
				}
			} else {
				GameWindow.getInstance().getMoveGizmoController().secondBoardClick(e);
				setFirstClickForMove(false);
			}
			break;
		case ROTATE_GIZMO:
			GameWindow.getInstance().getRotateGizmoController().boardClick(e);
			break;
		case DELETE_GIZMO:
			GameWindow.getInstance().getDeleteGizmoController().boardClick(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}