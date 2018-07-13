package fileIO;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import boardobject.Ball;
import boardobject.Cezerye;
import boardobject.Cezmi;
import boardobject.Firildak;
import boardobject.Gizmo;
import boardobject.LeftTokat;
import boardobject.RightTokat;
import boardobject.SquareTakoz;
import boardobject.TriangleTakoz;
import enums.DirectionEnum;
import enums.LevelEnum;
import enums.ModeEnum;
import model.BoardModel;
import model.Player;

public class XMLFileSaver implements FileSaver {

	/**
	 * @param path:
	 *            location that the file will be saved in the local disk.
	 * @requires path is a valid destination in local disk.
	 * @effects creates an XML file which holds the data for game level, cezmis,
	 *          player scores, gizmo locations and types, ball location and
	 *          velocities and cezerye location.
	 **/
	public void saveFile(String path) {
		try {
			ArrayList<Cezmi> cezmis = BoardModel.getInstance().getCezmis();
			ArrayList<Player> players = BoardModel.getInstance().getPlayers();
			ArrayList<Gizmo> gizmos = BoardModel.getInstance().getGizmos();
			ArrayList<Ball> balls = BoardModel.getInstance().getBalls();
			Cezerye cezerye = BoardModel.getInstance().getCezerye();
			LevelEnum level = BoardModel.getInstance().getLevel();
			String levelString;
			if (BoardModel.getInstance().getMode().equals(ModeEnum.RUNNING_MODE)) {
				if (level.equals(LevelEnum.LEVEL1))
					levelString = "1";
				else
					levelString = "2";
			} else {
				levelString = "1";
			}

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("board");
			doc.appendChild(rootElement);

			Attr attrLevel = doc.createAttribute("level");
			attrLevel.setValue(levelString);
			rootElement.setAttributeNode(attrLevel);

			if (balls.size() != 0) {
				for (int i = 0; i < balls.size(); i++) {
					Element ballElement = doc.createElement("ball");
					rootElement.appendChild(ballElement);

					Attr attrX = doc.createAttribute("x");
					attrX.setValue(String.valueOf(balls.get(i).getX() / BoardModel.getL()));
					ballElement.setAttributeNode(attrX);

					Attr attrY = doc.createAttribute("y");
					attrY.setValue(String.valueOf(balls.get(i).getY() / BoardModel.getL()));
					ballElement.setAttributeNode(attrY);

					Attr attrXVelocity = doc.createAttribute("xVelocity");
					attrXVelocity.setValue(String.valueOf(balls.get(i).getVelocityX()));
					ballElement.setAttributeNode(attrXVelocity);

					Attr attrYVelocity = doc.createAttribute("yVelocity");
					attrYVelocity.setValue(String.valueOf(balls.get(i).getVelocityY()));
					ballElement.setAttributeNode(attrYVelocity);
				}
			} else {
				Element ballElement = doc.createElement("ball");
				rootElement.appendChild(ballElement);

				Attr attrX = doc.createAttribute("x");
				attrX.setValue("5.0");
				ballElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue("19.0");
				ballElement.setAttributeNode(attrY);

				Attr attrXVelocity = doc.createAttribute("xVelocity");
				attrXVelocity.setValue("3.0");
				ballElement.setAttributeNode(attrXVelocity);

				Attr attrYVelocity = doc.createAttribute("yVelocity");
				attrYVelocity.setValue("4.0");
				ballElement.setAttributeNode(attrYVelocity);
			}

			if (cezmis.size() != 0) {
				Element cezmiElement = doc.createElement("cezmi1");
				rootElement.appendChild(cezmiElement);

				Attr attrX = doc.createAttribute("x");
				attrX.setValue(String.valueOf(cezmis.get(0).getX()));
				cezmiElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue(String.valueOf((int) (BoardModel.getL() * cezmis.get(0).getY())));
				cezmiElement.setAttributeNode(attrY);

				Attr attrScore = doc.createAttribute("score");
				attrScore.setValue(String.valueOf((int) cezmis.get(0).getOwner().getScore()));
				cezmiElement.setAttributeNode(attrScore);
			} else {
				Element cezmiElement = doc.createElement("cezmi1");
				rootElement.appendChild(cezmiElement);

				Attr attrX = doc.createAttribute("x");
				attrX.setValue("5.0");
				cezmiElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue("500");
				cezmiElement.setAttributeNode(attrY);

				Attr attrScore = doc.createAttribute("score");
				attrScore.setValue("0");
				cezmiElement.setAttributeNode(attrScore);
			}

			if (cezmis.size() != 0) {
				Element cezmiElement = doc.createElement("cezmi2");
				rootElement.appendChild(cezmiElement);

				Attr attrX = doc.createAttribute("x");
				attrX.setValue(String.valueOf(cezmis.get(1).getX()));
				cezmiElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue(String.valueOf((int) (BoardModel.getL() * cezmis.get(1).getY())));
				cezmiElement.setAttributeNode(attrY);

				Attr attrScore = doc.createAttribute("score");
				attrScore.setValue(String.valueOf((int) cezmis.get(1).getOwner().getScore()));
				cezmiElement.setAttributeNode(attrScore);
			} else {
				Element cezmiElement = doc.createElement("cezmi2");
				rootElement.appendChild(cezmiElement);

				Attr attrX = doc.createAttribute("x");
				attrX.setValue("15.0");
				cezmiElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue("500");
				cezmiElement.setAttributeNode(attrY);

				Attr attrScore = doc.createAttribute("score");
				attrScore.setValue("0");
				cezmiElement.setAttributeNode(attrScore);
			}

			if (cezerye != null) {
				Element cezeryesElement = doc.createElement("cezeryes");
				rootElement.appendChild(cezeryesElement);

				Element cezeryeElement = doc.createElement("cezerye");
				cezeryesElement.appendChild(cezeryeElement);

				Attr attrX = doc.createAttribute("x");
				attrX.setValue(String.valueOf((int) cezerye.getX()));
				cezeryeElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue(String.valueOf((int) cezerye.getY()));
				cezeryeElement.setAttributeNode(attrY);

				Attr attrTime = doc.createAttribute("time");
				attrTime.setValue(String.valueOf(cezerye.getTime()));
				cezeryeElement.setAttributeNode(attrTime);
			} else {
				Element cezeryesElement = doc.createElement("cezeryes");
				rootElement.appendChild(cezeryesElement);

				int[] coordinates = BoardModel.getInstance().randomAvailableCoordinates();

				Element cezeryeElement = doc.createElement("cezerye");
				cezeryesElement.appendChild(cezeryeElement);

				Attr attrX = doc.createAttribute("x");
				attrX.setValue(String.valueOf(coordinates[0]));
				cezeryeElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue(String.valueOf(coordinates[1]));
				cezeryeElement.setAttributeNode(attrY);

				Attr attrTime = doc.createAttribute("time");
				attrTime.setValue("0.5");
				cezeryeElement.setAttributeNode(attrTime);
			}

			if (gizmos.size() != 0) {
				Element gizmosElement = doc.createElement("gizmos");
				rootElement.appendChild(gizmosElement);

				for (int i = 0; i < gizmos.size(); i++) {
					Gizmo gizmo = gizmos.get(i);
					Element gizmoElement;
					if (gizmo instanceof SquareTakoz) {
						gizmoElement = doc.createElement("squareTakoz");

						Attr attrX = doc.createAttribute("x");
						attrX.setValue(String.valueOf((int) gizmo.getX()));
						gizmoElement.setAttributeNode(attrX);

						Attr attrY = doc.createAttribute("y");
						attrY.setValue(String.valueOf((int) gizmo.getY()));
						gizmoElement.setAttributeNode(attrY);

						gizmosElement.appendChild(gizmoElement);
					} else if (gizmo instanceof TriangleTakoz) {
						gizmoElement = doc.createElement("triangleTakoz");

						Attr attrX = doc.createAttribute("x");
						attrX.setValue(String.valueOf((int) gizmo.getX()));
						gizmoElement.setAttributeNode(attrX);

						Attr attrY = doc.createAttribute("y");
						attrY.setValue(String.valueOf((int) gizmo.getY()));
						gizmoElement.setAttributeNode(attrY);

						Attr attrOrientation = doc.createAttribute("orientation");
						String orientation;
						DirectionEnum direction = ((TriangleTakoz) gizmo).getDirection();
						if (direction.equals(DirectionEnum.SOUTH_WEST))
							orientation = "0";
						else if (direction.equals(DirectionEnum.SOUTH_EAST))
							orientation = "90";
						else if (direction.equals(DirectionEnum.NORTH_EAST))
							orientation = "180";
						else
							orientation = "270";

						attrOrientation.setValue(orientation);
						gizmoElement.setAttributeNode(attrOrientation);

						gizmosElement.appendChild(gizmoElement);
					} else if (gizmo instanceof Firildak) {
						gizmoElement = doc.createElement("firildak");

						Attr attrX = doc.createAttribute("x");
						attrX.setValue(String.valueOf((int) gizmo.getX()));
						gizmoElement.setAttributeNode(attrX);

						Attr attrY = doc.createAttribute("y");
						attrY.setValue(String.valueOf((int) gizmo.getY()));
						gizmoElement.setAttributeNode(attrY);

						double diffInOrdinates = (((Firildak) gizmo).getInitTopLeft().y()
								- ((Firildak) gizmo).getInitTopRight().y());
						double diffInAxises = (((Firildak) gizmo).getInitTopLeft().x()
								- ((Firildak) gizmo).getInitTopRight().x());
						double slope = diffInOrdinates / diffInAxises;
						double radian = Math.atan(slope);
						double angle = ((radian * 180.0 / Math.PI) + 360.0) % 360.0;

						Attr attrAngle = doc.createAttribute("angle");
						attrAngle.setValue(String.valueOf((int) angle));
						gizmoElement.setAttributeNode(attrAngle);

						gizmosElement.appendChild(gizmoElement);
					} else if (gizmo instanceof LeftTokat) {
						gizmoElement = doc.createElement("leftTokat");

						Attr attrX = doc.createAttribute("x");
						attrX.setValue(String.valueOf((int) gizmo.getX()));
						gizmoElement.setAttributeNode(attrX);

						Attr attrY = doc.createAttribute("y");
						attrY.setValue(String.valueOf((int) gizmo.getY()));
						gizmoElement.setAttributeNode(attrY);

						Attr attrOrientation = doc.createAttribute("orientation");
						DirectionEnum direction = ((LeftTokat) gizmo).getDirection();
						String directionString;
						if (direction.equals(DirectionEnum.SOUTH))
							directionString = "270";
						else if (direction.equals(DirectionEnum.EAST))
							directionString = "0";
						else if (direction.equals(DirectionEnum.NORTH))
							directionString = "90";
						else
							directionString = "180";
						attrOrientation.setValue(String.valueOf(directionString));
						gizmoElement.setAttributeNode(attrOrientation);

						gizmosElement.appendChild(gizmoElement);
					} else {
						gizmoElement = doc.createElement("rightTokat");

						Attr attrX = doc.createAttribute("x");
						attrX.setValue(String.valueOf((int) gizmo.getX()));
						gizmoElement.setAttributeNode(attrX);

						Attr attrY = doc.createAttribute("y");
						attrY.setValue(String.valueOf((int) gizmo.getY()));
						gizmoElement.setAttributeNode(attrY);

						Attr attrOrientation = doc.createAttribute("orientation");
						DirectionEnum direction = ((RightTokat) gizmo).getDirection();
						String directionString;
						if (direction.equals(DirectionEnum.SOUTH))
							directionString = "270";
						else if (direction.equals(DirectionEnum.EAST))
							directionString = "0";
						else if (direction.equals(DirectionEnum.NORTH))
							directionString = "90";
						else
							directionString = "180";
						attrOrientation.setValue(String.valueOf(directionString));
						gizmoElement.setAttributeNode(attrOrientation);

						gizmosElement.appendChild(gizmoElement);
					}
				}
			} else {
				Element gizmosElement = doc.createElement("gizmos");
				rootElement.appendChild(gizmosElement);

				Element gizmoElement = doc.createElement("squareTakoz");

				Attr attrX = doc.createAttribute("x");
				attrX.setValue("0");
				gizmoElement.setAttributeNode(attrX);

				Attr attrY = doc.createAttribute("y");
				attrY.setValue("0");
				gizmoElement.setAttributeNode(attrY);

				gizmosElement.appendChild(gizmoElement);
			}

			if (players.size() != 0) {
				Element keysElement = doc.createElement("keys");

				Element key1LeftElement = doc.createElement("key1left");
				Attr attrKey1Left = doc.createAttribute("key");
				attrKey1Left.setValue(players.get(0).getLeftKey());
				key1LeftElement.setAttributeNode(attrKey1Left);
				keysElement.appendChild(key1LeftElement);

				Element key1RightElement = doc.createElement("key1right");
				Attr attrKey1Right = doc.createAttribute("key");
				attrKey1Right.setValue(players.get(0).getRightKey());
				key1RightElement.setAttributeNode(attrKey1Right);
				keysElement.appendChild(key1RightElement);

				Element key2LeftElement = doc.createElement("key2left");
				Attr attrKey2Left = doc.createAttribute("key");
				attrKey2Left.setValue(players.get(1).getLeftKey());
				key2LeftElement.setAttributeNode(attrKey2Left);
				keysElement.appendChild(key2LeftElement);

				Element key2RightElement = doc.createElement("key2right");
				Attr attrKey2Right = doc.createAttribute("key");
				attrKey2Right.setValue(players.get(1).getRightKey());
				key2RightElement.setAttributeNode(attrKey2Right);
				keysElement.appendChild(key2RightElement);

				rootElement.appendChild(keysElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result;
			if (path.substring(path.length() - 4).equals(".xml")) {
				result = new StreamResult(new File(path));
			} else {
				result = new StreamResult(new File(path + ".xml"));
			}

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

}
