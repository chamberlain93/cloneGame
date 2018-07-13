package fileIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import boardobject.Ball;
import boardobject.BoardObject;
import boardobject.Cezerye;
import boardobject.Cezmi;
import boardobject.Firildak;
import boardobject.Gizmo;
import boardobject.LeftTokat;
import boardobject.RightTokat;
import boardobject.SquareTakoz;
import boardobject.TriangleTakoz;
import enums.BoardObjectEnum;
import enums.LevelEnum;
import factory.BoardObjectFactory;
import model.BoardModel;
import model.Player;
import physics.Angle;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLFileLoader implements FileLoader {
	private LevelEnum level;
	private ArrayList<Ball> balls;
	private ArrayList<Cezmi> cezmis;
	private Cezerye cezerye;
	private ArrayList<Player> players;

	public XMLFileLoader() {

	}

	/**
	 * @requires file to be a valid XML file. This validation is done by
	 *           XMLFileValidator.
	 * @param file:
	 *            The file which will be loaded.
	 * @effects: Loads the file into the game.
	 * @modifies: gizmos, balls, players, cezmis, occupied and level.
	 **/
	public String loadFile(File file) {
		String result = "";
		balls = new ArrayList<Ball>();
		players = new ArrayList<Player>();
		cezmis = new ArrayList<Cezmi>();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			players.add(new Player(0, 0));
			players.add(new Player(1, 0));

			if (!parseLevel(doc)) {
				result += "Level is invalid.";
			}
			BoardModel.getInstance().setLevel(level);

			BoardModel.getInstance().setOccupied(new boolean[25][25]);

			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 25; j++) {
					BoardModel.getInstance().getOccupied()[i][j] = false;
				}
			}

			BoardModel.getInstance().setGizmos(new ArrayList<Gizmo>());

			if (!parseCezmi(doc, 1)) {
				result += "\nLocation of the first cezmi is invalid.";
			}
			if (!parseCezmi(doc, 2)) {
				result += "\nLocation of the second cezmi is invalid.";
			}
			parseKeys(doc);
			BoardModel.getInstance().setCezmis(cezmis);

			BoardModel.getInstance().setPlayers(players);

			if (!parseSquareTakoz(doc)) {
				result += "\nLocation of a square takoz is invalid.";
			}

			if (!parseTriangleTakoz(doc)) {
				result += "\nLocation of a triangle takoz is invalid.";
			}

			if (!parseFirildak(doc)) {
				result += "\nLocation of a firildak is invalid.";
			}

			if (!parseLeftTokat(doc)) {
				result += "\nLocation of a left tokat is invalid.";
			}

			if (!parseRightTokat(doc)) {
				result += "\nLocation of a right tokat is invalid.";
			}

			if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() < 4
					&& BoardModel.getInstance().getPlayers().get(1).getGizmoNum() == 4) {
				result += "\nPlayer1 needs to add " + (4 - BoardModel.getInstance().getPlayers().get(0).getGizmoNum())
						+ " more gizmos.";
			} else if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() == 4
					&& BoardModel.getInstance().getPlayers().get(1).getGizmoNum() < 4) {
				result += "\nPlayer2 needs to add " + (4 - BoardModel.getInstance().getPlayers().get(1).getGizmoNum())
						+ " more gizmos.";
			} else if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() < 4
					&& BoardModel.getInstance().getPlayers().get(1).getGizmoNum() < 4) {
				result += "\nPlayer1 needs to add " + (4 - BoardModel.getInstance().getPlayers().get(0).getGizmoNum())
						+ " more gizmos." + "\nPlayer2 needs to add "
						+ (4 - BoardModel.getInstance().getPlayers().get(1).getGizmoNum()) + " more gizmos.";
			}

			if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() > 4) {
				result += "\nPlayer1 has more than 4 gizmos.";
			}

			if (BoardModel.getInstance().getPlayers().get(1).getGizmoNum() > 4) {
				result += "\nPlayer2 has more than 4 gizmos.";
			}

			if (!parseCezerye(doc)) {
				result += "\nLocation of cezerye is invalid.";
			}

			BoardModel.getInstance().setCezerye(cezerye);

			if (!parseBalls(doc)) {
				result += "\nLocation of ball is invalid.";
			}
			BoardModel.getInstance().setBalls(balls);

			parseFriction1(doc);
			parseFriction2(doc);
			parseGravity(doc);

			if (result.equals(""))
				result = "valid";
			else {

				BoardModel.resetBoardModel();

				if (result.substring(0, 1).equals("\n")) {
					result = result.substring(1);
				}
			}

			return result;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @effects: parses the level from the XML file and sets "level" field
	 *           according to the data it parsed.
	 * @modifies: level
	 * @param doc
	 */
	private boolean parseLevel(Document doc) {
		String level = null;
		level = doc.getDocumentElement().getAttribute("level");
		if (level.equals("1")) {
			this.level = LevelEnum.LEVEL1;
			return true;
		} else if (level.equals("2")) {
			this.level = LevelEnum.LEVEL2;
			return true;
		} else {
			return false;
		}
	}

	private void parseFriction1(Document doc) {
		String friction1 = doc.getDocumentElement().getAttribute("friction1");
		if (friction1 != null && friction1 != "")
			Ball.setMu(Double.parseDouble(friction1));
	}

	private void parseFriction2(Document doc) {
		String friction2 = doc.getDocumentElement().getAttribute("friction2");
		if (friction2 != null && friction2 != "")
			Ball.setMu2(Double.parseDouble(friction2));
	}

	private void parseGravity(Document doc) {
		String gravity = doc.getDocumentElement().getAttribute("gravity");
		if (gravity != null && gravity != "")
			Ball.setGravity(Double.parseDouble(gravity));
	}

	/**
	 * @effects: parses the ball locations and ball velocities from the XML file
	 *           and sets "balls" field according to the data it parsed.
	 * @modifies: level
	 * @param doc
	 */
	private boolean parseBalls(Document doc) {
		boolean result = true;
		NodeList ballList = doc.getElementsByTagName("ball");
		for (int i = 0; i < ballList.getLength(); i++) {
			Node nNode = ballList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				double ballX = 0;
				double ballY = 0;
				double ballXVelocity = 0;
				double ballYVelocity = 0;
				if (eElement.getAttribute("xVelocity") != null && !eElement.getAttribute("xVelocity").equals("")) {
					ballXVelocity = Double.parseDouble(eElement.getAttribute("xVelocity"));
				}
				if (eElement.getAttribute("yVelocity") != null && !eElement.getAttribute("yVelocity").equals("")) {
					ballYVelocity = Double.parseDouble(eElement.getAttribute("yVelocity"));
				}
				if (!eElement.getAttribute("x").equals("") && eElement.getAttribute("x") != null) {
					ballX = Double.parseDouble(eElement.getAttribute("x"));
				}
				if (!eElement.getAttribute("y").equals("") && eElement.getAttribute("y") != null) {
					ballY = Double.parseDouble(eElement.getAttribute("y"));
				}
				if (ballX < 0.5 || ballX > 24.5 || ballY < 0.5 || ballY > 24.5) {
					result = false;
				}
				balls.add(
						(Ball) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.BALL, ballX, ballY));
				balls.get(i).setVelocityX(ballXVelocity);
				balls.get(i).setVelocityY(ballYVelocity);
			}
		}
		return result;
	}

	private Player findOwner(BoardObject boardObject) {
		if (boardObject.getX() >= 0 && boardObject.getX() <= 11 && boardObject.getY() >= 0
				&& boardObject.getY() <= 18) {
			BoardModel.getInstance().setCurrentPlayer(BoardModel.getInstance().getPlayers().get(0));
			return players.get(0);
		} else if (boardObject.getX() >= 13 && boardObject.getX() <= 24 && boardObject.getY() >= 0
				&& boardObject.getY() <= 18) {
			BoardModel.getInstance().setCurrentPlayer(BoardModel.getInstance().getPlayers().get(1));
			return players.get(1);
		} else {
			return null;
		}
	}

	/**
	 * @effects: parses the x coordinate of the first cezmi and the score of the
	 *           first player from the XML file and sets "cezmi1" according to
	 *           the data it parsed.
	 * @modifies: cezmis
	 * @param doc
	 */
	private boolean parseCezmi(Document doc, int index) {
		boolean result = true;
		NodeList cezmi1List = doc.getElementsByTagName("cezmi" + index);
		if (cezmi1List != null && cezmi1List.getLength() != 0) {
			Node nNode = cezmi1List.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				double cezmiX = 0;
				int cezmiY = 0;
				int cezmiScore = 0;
				if (!eElement.getAttribute("x").equals("") && eElement.getAttribute("x") != null) {
					cezmiX = Double.parseDouble(eElement.getAttribute("x"));
				}
				if (!eElement.getAttribute("y").equals("") && eElement.getAttribute("y") != null) {
					cezmiY = Integer.parseInt(eElement.getAttribute("y"));
				}
				if (!eElement.getAttribute("score").equals("") && eElement.getAttribute("score") != null) {
					cezmiScore = Integer.parseInt(eElement.getAttribute("score"));
				}
				if (index == 1) {
					if (cezmiX < BoardModel.getInstance().getWalls().get(3).getX()
							+ BoardModel.getInstance().getWalls().get(3).getWidth()
							|| cezmiX > BoardModel.getInstance().getEngel().getX()) {
						result = false;
					}
				} else {
					if ((cezmiX < BoardModel.getInstance().getEngel().getX()
							+ BoardModel.getInstance().getEngel().getWidth())
							|| (cezmiX > BoardModel.getInstance().getWalls().get(1).getX())) {
						result = false;
					}
				}
				cezmis.add((Cezmi) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.CEZMI, cezmiX,
						(double) (cezmiY / BoardModel.getL())));
				players.get(index - 1).setScore(cezmiScore);
				cezmis.get(index - 1).setOwner(players.get(index - 1));
				cezmis.get(index - 1).setLimits();
			}
		}
		return result;
	}

	/**
	 * @effects: parses the x, y coordinates and time field of the cezerye from
	 *           the XML file and sets "cezerye" according to the data it
	 *           parsed.
	 * @modifies: cezerye
	 * @param doc
	 */
	private boolean parseCezerye(Document doc) {
		boolean result = true;
		NodeList cezeryeList = doc.getElementsByTagName("cezerye");
		if (cezeryeList != null && cezeryeList.getLength() != 0) {
			Node nNode = cezeryeList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				int x = Integer.parseInt(eElement.getAttribute("x"));
				int y = Integer.parseInt(eElement.getAttribute("y"));
				double time = Double.parseDouble(eElement.getAttribute("time"));
				if (!BoardModel.getInstance().getOccupied()[x][y]) {
					cezerye = (Cezerye) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.CEZERYE, x,
							y);
					cezerye.setTime(time);
				} else {
					// TODO
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * @effects: parses the x, y coordinates of the squareTakozs from the XML
	 *           file and modifies gizmos according to the data it parsed.
	 * @modifies: gizmos
	 * @param doc
	 */
	private boolean parseSquareTakoz(Document doc) {
		boolean result = true;
		NodeList squareTakozList = doc.getElementsByTagName("squareTakoz");
		if (squareTakozList != null && squareTakozList.getLength() != 0) {
			for (int i = 0; i < squareTakozList.getLength(); i++) {
				Node nNode = squareTakozList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					SquareTakoz squareTakoz = (SquareTakoz) BoardObjectFactory.getInstance()
							.createGizmo(BoardObjectEnum.SQUARE_TAKOZ, x, y);
					Player owner = findOwner(squareTakoz);
					if (BoardModel.getInstance().addGizmo(x, y, BoardObjectEnum.SQUARE_TAKOZ, owner)) {

					} else {
						// TODO
						result = false;
					}

				}
			}
		}
		return result;
	}

	/**
	 * @effects: parses the x, y coordinates and the orientation field of the
	 *           triangleTakozs from the XML file and modifies gizmos according
	 *           to the data it parsed.
	 * @modifies: gizmos
	 * @param doc
	 */
	private boolean parseTriangleTakoz(Document doc) {
		boolean result = true;
		NodeList triangleTakozList = doc.getElementsByTagName("triangleTakoz");
		if (triangleTakozList != null && triangleTakozList.getLength() != 0) {

			for (int i = 0; i < triangleTakozList.getLength(); i++) {
				Node nNode = triangleTakozList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					int orientation = Integer.parseInt(eElement.getAttribute("orientation"));
					int rotationCount = orientation / 90;
					TriangleTakoz triangleTakoz = (TriangleTakoz) BoardObjectFactory.getInstance()
							.createGizmo(BoardObjectEnum.TRIANGLE_TAKOZ, x, y);

					Player owner = findOwner(triangleTakoz);

					if (orientation == 0) {
						if (BoardModel.getInstance().addGizmo(x, y - 1, BoardObjectEnum.TRIANGLE_TAKOZ, owner)) {
							for (int j = 0; j < rotationCount; j++) {
								((TriangleTakoz) BoardModel.getInstance().getGizmos()
										.get(BoardModel.getInstance().getGizmos().size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else if (orientation == 90) {
						if (BoardModel.getInstance().addGizmo(x - 1, y - 1, BoardObjectEnum.TRIANGLE_TAKOZ, owner)) {
							for (int j = 0; j < rotationCount; j++) {
								((TriangleTakoz) BoardModel.getInstance().getGizmos()
										.get(BoardModel.getInstance().getGizmos().size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else if (orientation == 180) {
						if (BoardModel.getInstance().addGizmo(x - 1, y, BoardObjectEnum.TRIANGLE_TAKOZ, owner)) {
							for (int j = 0; j < rotationCount; j++) {
								((TriangleTakoz) BoardModel.getInstance().getGizmos()
										.get(BoardModel.getInstance().getGizmos().size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else {
						if (BoardModel.getInstance().addGizmo(x, y, BoardObjectEnum.TRIANGLE_TAKOZ, owner)) {
							for (int j = 0; j < rotationCount; j++) {
								((TriangleTakoz) BoardModel.getInstance().getGizmos()
										.get(BoardModel.getInstance().getGizmos().size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					}

				}
			}
		}
		return result;
	}

	/**
	 * @effects: parses the x, y coordinates and the angle field of the
	 *           firildaks from the XML file and modifies gizmos according to
	 *           the data it parsed.
	 * @modifies: gizmos
	 * @param doc
	 */
	private boolean parseFirildak(Document doc) {
		boolean result = true;
		NodeList firildakList = doc.getElementsByTagName("firildak");
		if (firildakList != null && firildakList.getLength() != 0) {
			for (int i = 0; i < firildakList.getLength(); i++) {
				Node nNode = firildakList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					int angle = Integer.parseInt(eElement.getAttribute("angle"));
					Firildak firildak = (Firildak) BoardObjectFactory.getInstance()
							.createGizmo(BoardObjectEnum.FIRILDAK, x, y);
					Player owner = findOwner(firildak);
					if (BoardModel.getInstance().addGizmo(x, y, BoardObjectEnum.FIRILDAK, owner)) {
						ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
						Angle oldAngle = ((Firildak) gizmos.get(gizmos.size() - 1)).getAngle();
						((Firildak) gizmos.get(gizmos.size() - 1)).setAngle(new Angle(angle));
						((Firildak) gizmos.get(gizmos.size() - 1)).rotate();
						((Firildak) gizmos.get(gizmos.size() - 1)).setAngle(oldAngle);
					} else {
						// TODO
						result = false;
					}

				}
			}
		}
		return result;
	}

	/**
	 * @effects: parses the x, y coordinates and the orientation field of the
	 *           leftTokats from the XML file and modifies gizmos according to
	 *           the data it parsed.
	 * @modifies: gizmos
	 * @param doc
	 */
	private boolean parseLeftTokat(Document doc) {
		boolean result = true;
		NodeList leftTokatList = doc.getElementsByTagName("leftTokat");
		if (leftTokatList != null && leftTokatList.getLength() != 0) {
			for (int i = 0; i < leftTokatList.getLength(); i++) {
				Node nNode = leftTokatList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					int orientation = Integer.parseInt(eElement.getAttribute("orientation"));
					LeftTokat leftTokat = (LeftTokat) BoardObjectFactory.getInstance()
							.createGizmo(BoardObjectEnum.LEFT_TOKAT, x, y);
					Player owner = findOwner(leftTokat);
					if (orientation == 270) {
						if (BoardModel.getInstance().addGizmo(x, y, BoardObjectEnum.LEFT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((LeftTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							result = false;
						}
					} else if (orientation == 0) {
						if (BoardModel.getInstance().addGizmo(x, y - 2, BoardObjectEnum.LEFT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((LeftTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else if (orientation == 90) {
						if (BoardModel.getInstance().addGizmo(x - 2, y - 2, BoardObjectEnum.LEFT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((LeftTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else {
						if (BoardModel.getInstance().addGizmo(x - 2, y, BoardObjectEnum.LEFT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((LeftTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					}

				}
			}
		}
		return result;
	}

	/**
	 * @effects: parses the x, y coordinates and the orientation field of the
	 *           rightTokats from the XML file and modifies gizmos according to
	 *           the data it parsed.
	 * @modifies: gizmos
	 * @param doc
	 */
	// occupied[x][y + 1]=true;
	// occupied[x - 1][y]=true;
	// occupied[x - 1][y + 1]=true;
	// occupied[x][y]=true;
	private boolean parseRightTokat(Document doc) {
		boolean result = true;
		NodeList rightTokatList = doc.getElementsByTagName("rightTokat");
		if (rightTokatList != null && rightTokatList.getLength() != 0) {
			for (int i = 0; i < rightTokatList.getLength(); i++) {
				Node nNode = rightTokatList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int x = Integer.parseInt(eElement.getAttribute("x"));
					int y = Integer.parseInt(eElement.getAttribute("y"));
					int orientation = Integer.parseInt(eElement.getAttribute("orientation"));
					RightTokat rightTokat = (RightTokat) BoardObjectFactory.getInstance()
							.createGizmo(BoardObjectEnum.RIGHT_TOKAT, x, y);
					Player owner = findOwner(rightTokat);
					if (orientation == 0) {
						if (BoardModel.getInstance().addGizmo(x + 1, y, BoardObjectEnum.RIGHT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((RightTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else if (orientation == 90) {
						if (BoardModel.getInstance().addGizmo(x + 1, y - 2, BoardObjectEnum.RIGHT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((RightTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else if (orientation == 180) {
						if (BoardModel.getInstance().addGizmo(x - 1, y - 2, BoardObjectEnum.RIGHT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((RightTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					} else {
						if (BoardModel.getInstance().addGizmo(x - 1, y, BoardObjectEnum.RIGHT_TOKAT, owner)) {
							for (int j = 0; j < orientation / 90 + 1; j++) {
								ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
								((RightTokat) gizmos.get(gizmos.size() - 1)).startRotating();
							}
						} else {
							// TODO
							result = false;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * @effects: parses the keys that the players will use during the game from
	 *           the XML file and modifies players according to the data it
	 *           parsed.
	 * @modifies: players
	 * @param doc
	 */
	private void parseKeys(Document doc) {
		String key1Left, key1Right, key2Left, key2Right;

		NodeList key1LeftList = doc.getElementsByTagName("key1left");
		if (key1LeftList != null && key1LeftList.getLength() != 0) {
			Node nNode = key1LeftList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				key1Left = eElement.getAttribute("key");
				players.get(0).setLeftKey(key1Left);
			}
		}

		NodeList key1RightList = doc.getElementsByTagName("key1right");
		if (key1RightList != null && key1RightList.getLength() != 0) {
			Node nNode = key1RightList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				key1Right = eElement.getAttribute("key");
				players.get(0).setRightKey(key1Right);
			}
		}

		NodeList key2LeftList = doc.getElementsByTagName("key2left");
		if (key2LeftList != null && key2LeftList.getLength() != 0) {
			Node nNode = key2LeftList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				key2Left = eElement.getAttribute("key");
				players.get(1).setLeftKey(key2Left);
			}
		}

		NodeList key2RightList = doc.getElementsByTagName("key2right");
		if (key2RightList != null && key2RightList.getLength() != 0) {
			Node nNode = key2RightList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				key2Right = eElement.getAttribute("key");
				players.get(1).setRightKey(key2Right);
			}
		}
	}

}
