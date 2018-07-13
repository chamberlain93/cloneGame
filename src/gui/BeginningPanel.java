package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BeginningPanel extends JPanel implements MouseListener {

	private final static double L = 20;

	private Rectangle newGame;
	private Rectangle loadGame;
	private Rectangle quitGame;
	private Clip music;

	public Rectangle getNewGame() {
		return newGame;
	}

	public void setNewGame(Rectangle newGame) {
		this.newGame = newGame;
	}

	public Rectangle getLoadGame() {
		return loadGame;
	}

	public void setLoadGame(Rectangle loadGame) {
		this.loadGame = loadGame;
	}

	public Rectangle getQuitGame() {
		return quitGame;
	}

	public void setQuitGame(Rectangle quitGame) {
		this.quitGame = quitGame;
	}

	public static double getL() {
		return L;
	}

	public Clip getMusic() {
		return music;
	}

	public void setMusic(Clip music) {
		this.music = music;
	}

	public BeginningPanel() {
		setPreferredSize(new Dimension((int) (25 * getL()), (int) (25 * getL())));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createVerticalStrut(300));

		add(Box.createVerticalGlue());

		try {
			URL url = this.getClass().getResource("background_music.wav");
			AudioInputStream audio = AudioSystem.getAudioInputStream(url);
			setMusic(AudioSystem.getClip());
			getMusic().open(audio);
			getMusic().loop(-1);
		} catch (Exception e) {
			System.out.println("Music can not be played.");
		}

		setNewGame(new Rectangle(302, 250, 120, 40));
		setLoadGame(new Rectangle(302, 300, 120, 40));
		setQuitGame(new Rectangle(302, 350, 120, 40));

		addMouseListener(this);
		requestFocus();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(new ImageIcon(this.getClass().getResource("background.jpg")).getImage(), 0, 0,
				(int) (25 * getL() + 225), (int) (25 * getL() + 32), this);

		Graphics2D g2d = (Graphics2D) g;

		Font myFont = new Font("arial", Font.BOLD, 50);
		g.setFont(myFont);
		g.setColor(Color.YELLOW);
		g.drawString("HADÝ CEZMÝ", 218, 150);

		Font myFont1 = new Font("arial", Font.ITALIC + Font.BOLD, 19);
		g.setFont(myFont1);
		g.drawString("by UC", 334, 180);

		g2d.setColor(Color.GREEN);
		g2d.fill(getNewGame());
		g2d.fill(getLoadGame());
		g2d.fill(getQuitGame());

		Font myFont2 = new Font("arial", Font.BOLD, 20);
		g.setFont(myFont2);
		g.setColor(Color.WHITE);
		g.drawString("New Game", getNewGame().x + 10, getNewGame().y + 28);
		g.drawString("Load Game", getLoadGame().x + 7, getLoadGame().y + 28);
		g.drawString("Quit Game", getQuitGame().x + 10, getQuitGame().y + 28);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (getNewGame().contains(e.getX(), e.getY())) {
			GameWindow.getCard().show(GameWindow.getContainer(), "3");
			GameWindow.getInstance().getBuildingPanel().requestFocus();
		} else {
			if (getLoadGame().contains(e.getX(), e.getY())) {
				GameWindow.getInstance().getLoadController().buttonClick();
				GameWindow.getInstance().openFileChooser();

			} else {
				if (getQuitGame().contains(e.getX(), e.getY())) {
					GameWindow.getInstance().getQuitController().buttonClick();
				}
			}
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