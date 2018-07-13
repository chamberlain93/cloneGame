package model;

import java.util.ArrayList;
import java.util.List;

import boardobject.Ball;
import boardobject.Cezmi;
import boardobject.LeftTokat;
import boardobject.Observable;
import boardobject.RightTokat;
import enums.StateEnum;
import observer.Observer;
import observer.PlayerObserver;

public class Player implements Observable {
	private double score;
	private int id;
	private List<Observer> observers = new ArrayList<Observer>();
	private String leftKey;
	private String rightKey;
	private String leftTokatKey;
	private String rightTokatKey;
	private int gizmoNum;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public int getGizmoNum() {
		return gizmoNum;
	}

	public void setGizmoNum(int gizmoNum) {
		this.gizmoNum = gizmoNum;
	}

	public Player(int id, double score) {
		this.id = id;
		this.score = score;
		attachObserver(new PlayerObserver());
		setGizmoNum(0);
		if (id == 0) {
			setLeftKey("a");
			setRightKey("s");
			setLeftTokatKey("r");
			setRightTokatKey("t");
		} else if (id == 1) {
			setLeftKey("k");
			setRightKey("l");
			setLeftTokatKey("o");
			setRightTokatKey("p");
		}
	}

	public static boolean updateScores(ArrayList<Player> players, Ball ball) {
		if (ball.getHistory()[2] == BoardModel.getInstance().getWalls().get(2)) {
			if (ball.getX() <= BoardModel.getL() * BoardModel.getInstance().getEngel().getX()) {
				if ((Cezmi) ball.getHistory()[0] != null
						&& ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(1)) {
					if (ball.getHistory()[1] instanceof LeftTokat || ball.getHistory()[1] instanceof RightTokat) {
						players.get(1).setScore(players.get(1).getScore() + 2);

						players.get(0).notifyAllObservers();
						players.get(1).notifyAllObservers();
						if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
								|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
							BoardModel.getInstance().setState(StateEnum.PAUSE);
							BoardModel.getInstance().notifyAllObservers();
						}

						return true;
					} else {
						players.get(1).setScore(players.get(1).getScore() + 1);

						players.get(0).notifyAllObservers();
						players.get(1).notifyAllObservers();
						if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
								|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
							BoardModel.getInstance().setState(StateEnum.PAUSE);
							BoardModel.getInstance().notifyAllObservers();
						}

						return true;
					}
				}
				if ((Cezmi) ball.getHistory()[0] != null && ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(0)
						&& !ball.isHitRightWall()) {
					players.get(0).setScore(players.get(0).getScore() - 1);

					players.get(0).notifyAllObservers();
					players.get(1).notifyAllObservers();
					if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
							|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
						BoardModel.getInstance().setState(StateEnum.PAUSE);
						BoardModel.getInstance().notifyAllObservers();
					}

					return true;
				}
			} else {
				if ((Cezmi) ball.getHistory()[0] != null
						&& ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(0)) {
					if (ball.getHistory()[1] instanceof LeftTokat || ball.getHistory()[1] instanceof RightTokat) {
						players.get(0).setScore(players.get(0).getScore() + 2);

						players.get(0).notifyAllObservers();
						players.get(1).notifyAllObservers();
						if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
								|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
							BoardModel.getInstance().setState(StateEnum.PAUSE);
							BoardModel.getInstance().notifyAllObservers();
						}

						return true;
					} else {
						players.get(0).setScore(players.get(0).getScore() + 1);

						players.get(0).notifyAllObservers();
						players.get(1).notifyAllObservers();
						if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
								|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
								|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
							BoardModel.getInstance().setState(StateEnum.PAUSE);
							BoardModel.getInstance().notifyAllObservers();
						}

						return true;
					}
				}
				if ((Cezmi) ball.getHistory()[0] != null && ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(1)
						&& !ball.isHitLeftWall()) {
					players.get(1).setScore(players.get(1).getScore() - 1);

					players.get(0).notifyAllObservers();
					players.get(1).notifyAllObservers();
					if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
							|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
						BoardModel.getInstance().setState(StateEnum.PAUSE);
						BoardModel.getInstance().notifyAllObservers();
					}

					return true;
				}
			}
		}
		if (ball.getHistory()[2] == BoardModel.getInstance().getEngel()) {
			if ((Cezmi) ball.getHistory()[0] != null && ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(0)
					&& ball.getHistory()[1] == ball.getHistory()[0]) {
				players.get(0).setScore(players.get(0).getScore() - 0.5);

				players.get(0).notifyAllObservers();
				players.get(1).notifyAllObservers();
				if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
						|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
						|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
						|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
					BoardModel.getInstance().setState(StateEnum.PAUSE);
					BoardModel.getInstance().notifyAllObservers();
				}

				return true;
			} else {
				if ((Cezmi) ball.getHistory()[0] != null && ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(1)
						&& ball.getHistory()[1] == ball.getHistory()[0]) {
					players.get(1).setScore(players.get(1).getScore() - 0.5);

					players.get(0).notifyAllObservers();
					players.get(1).notifyAllObservers();
					if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
							|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
						BoardModel.getInstance().setState(StateEnum.PAUSE);
						BoardModel.getInstance().notifyAllObservers();
					}

					return true;
				}
			}
		}
		if (ball.getHistory()[2] == BoardModel.getInstance().getWalls().get(0)) {
			if ((Cezmi) ball.getHistory()[0] != null && ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(0)
					&& ball.getHistory()[1] == ball.getHistory()[0]) {
				players.get(0).setScore(players.get(0).getScore() - 0.5);

				players.get(0).notifyAllObservers();
				players.get(1).notifyAllObservers();
				if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
						|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
						|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
						|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
					BoardModel.getInstance().setState(StateEnum.PAUSE);
					BoardModel.getInstance().notifyAllObservers();
				}

				return true;
			} else {
				if ((Cezmi) ball.getHistory()[0] != null && ((Cezmi) ball.getHistory()[0]).getOwner() == players.get(1)
						&& ball.getHistory()[1] == ball.getHistory()[0]) {
					players.get(1).setScore(players.get(1).getScore() - 0.5);

					players.get(0).notifyAllObservers();
					players.get(1).notifyAllObservers();
					if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(1).getScore() >= 10
							|| BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)
							|| BoardModel.getInstance().getPlayers().get(1).getScore() <= (-10)) {
						BoardModel.getInstance().setState(StateEnum.PAUSE);
						BoardModel.getInstance().notifyAllObservers();
					}

					return true;
				}
			}
		}
		return false;
	}

	public void attachObserver(Observer observer) {
		getObservers().add(observer);
	}

	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

	public String getLeftKey() {
		return leftKey;
	}

	public void setLeftKey(String leftKey) {
		this.leftKey = leftKey;
	}

	public String getRightKey() {
		return rightKey;
	}

	public void setRightKey(String rightKey) {
		this.rightKey = rightKey;
	}

	public String getLeftTokatKey() {
		return leftTokatKey;
	}

	public void setLeftTokatKey(String leftTokatKey) {
		this.leftTokatKey = leftTokatKey;
	}

	public String getRightTokatKey() {
		return rightTokatKey;
	}

	public void setRightTokatKey(String rightTokatKey) {
		this.rightTokatKey = rightTokatKey;
	}

}