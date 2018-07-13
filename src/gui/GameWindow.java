package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import com.sun.glass.events.WindowEvent;
import controller.*;
import enums.BoardObjectEnum;
import enums.LevelEnum;
import enums.ModeEnum;
import enums.StateEnum;
import fileIO.XMLFileChooser;
import fileIO.XMLFileValidator;
import model.*;
import thread.BallThread;
import thread.CezeryeThread;
import thread.GizmoThread;
import thread.SecondBallThread;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	private static final double L = 20;

	private static GameWindow instance;

	private static CardLayout card;
	private static JPanel container;
	private ClickHandler cHandler;
	protected BoardPanel boardPanel;
	protected BuildingPanel buildingPanel;
	protected BeginningPanel beginningPanel;
	protected JLabel player1Score;
	protected JLabel player2Score;

	private AddGizmoController addGizmoController;
	private MoveGizmoController moveGizmoController;
	private RotateGizmoController rotateGizmoController;
	private DeleteGizmoController deleteGizmoController;
	private PlayController playController;
	private ResumeController resumeController;
	private PauseController pauseController;
	private SaveController saveController;
	private LoadController loadController;
	private QuitController quitController;

	public static CardLayout getCard() {
		return card;
	}

	public static void setCard(CardLayout card) {
		GameWindow.card = card;
	}

	public static JPanel getContainer() {
		return container;
	}

	public static void setContainer(JPanel container) {
		GameWindow.container = container;
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public BuildingPanel getBuildingPanel() {
		return buildingPanel;
	}

	public void setBuildingPanel(BuildingPanel buildingPanel) {
		this.buildingPanel = buildingPanel;
	}

	public BeginningPanel getBeginningPanel() {
		return beginningPanel;
	}

	public void setBeginningPanel(BeginningPanel beginningPanel) {
		this.beginningPanel = beginningPanel;
	}

	public AddGizmoController getAddGizmoController() {
		return addGizmoController;
	}

	public void setAddGizmoController(AddGizmoController addGizmoController) {
		this.addGizmoController = addGizmoController;
	}

	public MoveGizmoController getMoveGizmoController() {
		return moveGizmoController;
	}

	public void setMoveGizmoController(MoveGizmoController moveGizmoController) {
		this.moveGizmoController = moveGizmoController;
	}

	public RotateGizmoController getRotateGizmoController() {
		return rotateGizmoController;
	}

	public void setRotateGizmoController(RotateGizmoController rotateGizmoController) {
		this.rotateGizmoController = rotateGizmoController;
	}

	public DeleteGizmoController getDeleteGizmoController() {
		return deleteGizmoController;
	}

	public void setDeleteGizmoController(DeleteGizmoController deleteGizmoController) {
		this.deleteGizmoController = deleteGizmoController;
	}

	public PlayController getPlayController() {
		return playController;
	}

	public void setPlayController(PlayController playController) {
		this.playController = playController;
	}

	public ResumeController getResumeController() {
		return resumeController;
	}

	public void setResumeController(ResumeController resumeController) {
		this.resumeController = resumeController;
	}

	public PauseController getPauseController() {
		return pauseController;
	}

	public void setPauseController(PauseController pauseController) {
		this.pauseController = pauseController;
	}

	public SaveController getSaveController() {
		return saveController;
	}

	public void setSaveController(SaveController saveController) {
		this.saveController = saveController;
	}

	public LoadController getLoadController() {
		return loadController;
	}

	public void setLoadController(LoadController loadController) {
		this.loadController = loadController;
	}

	public QuitController getQuitController() {
		return quitController;
	}

	public void setQuitController(QuitController quitController) {
		this.quitController = quitController;
	}

	public static double getL() {
		return L;
	}

	public JLabel getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(JLabel player1Score) {
		this.player1Score = player1Score;
	}

	public JLabel getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(JLabel player2Score) {
		this.player2Score = player2Score;
	}

	public static void setInstance(GameWindow instance) {
		GameWindow.instance = instance;
	}

	private GameWindow() {
		super("HadiCezmi by UC");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setResizable(false);
		Dimension dimension = getToolkit().getScreenSize();
		setLocation((dimension.width - 725) / 2, (dimension.height - 532) / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setCard(new CardLayout());
		setContainer(new JPanel());

		setBoardPanel(new BoardPanel());
		setBuildingPanel(new BuildingPanel());
		setBeginningPanel(new BeginningPanel());

		setAddGizmoController(new AddGizmoController());
		setMoveGizmoController(new MoveGizmoController());
		setRotateGizmoController(new RotateGizmoController());
		setDeleteGizmoController(new DeleteGizmoController());
		setPlayController(new PlayController());
		setResumeController(new ResumeController());
		setPauseController(new PauseController());
		setSaveController(new SaveController());
		setLoadController(new LoadController());
		setQuitController(new QuitController());

		JPanel build = new JPanel();
		build.setLayout(new BorderLayout());

		JPanel editButtons1 = addButtonsBuildingPanel1();
		build.add(editButtons1, BorderLayout.WEST);

		JPanel editButtons2 = addButtonsBuildingPanel2();
		build.add(editButtons2, BorderLayout.EAST);

		JToolBar endToolBar = new JToolBar();
		addButtonsBuildingPanelEnd(endToolBar);
		build.add(endToolBar, BorderLayout.SOUTH);

		JScrollPane buildingScrollPane = new JScrollPane(getBuildingPanel());
		buildingScrollPane.setPreferredSize(new Dimension((int) (25 * getL()), (int) (25 * getL())));
		build.add(buildingScrollPane, BorderLayout.CENTER);
		build.setPreferredSize(new Dimension((int) (25 * getL()), (int) (25 * getL())));

		JPanel board = new JPanel();
		board.setLayout(new BorderLayout());

		setPlayer1Score(new JLabel("Score: " + BoardModel.getInstance().getPlayers().get(0).getScore()));
		JPanel player1ScorePanel = addScorePanel(getPlayer1Score());
		board.add(player1ScorePanel, BorderLayout.WEST);

		setPlayer2Score(new JLabel("Score: " + BoardModel.getInstance().getPlayers().get(1).getScore()));
		JPanel player2ScorePanel = addScorePanel(getPlayer2Score());
		board.add(player2ScorePanel, BorderLayout.EAST);

		JToolBar boardToolBar = new JToolBar();
		addButtonsBoardPanel(boardToolBar);
		boardToolBar.setFloatable(false);
		board.add(boardToolBar, BorderLayout.SOUTH);

		JScrollPane boardScrollPane = new JScrollPane(getBoardPanel());
		boardScrollPane.setPreferredSize(new Dimension((int) (25 * getL()), (int) (25 * getL())));
		board.add(boardScrollPane, BorderLayout.CENTER);
		board.setPreferredSize(new Dimension((int) (25 * getL()), (int) (25 * getL())));

		getBeginningPanel().setFocusable(true);
		getBuildingPanel().setFocusable(true);
		getBoardPanel().setFocusable(true);

		getContainer().setPreferredSize(new Dimension((int) (25 * getL() + 225), (int) (25 * getL() + 32)));
		getContainer().setLayout(getCard());

		getContainer().add(getBeginningPanel(), "2");
		getContainer().add(build, "3");
		getContainer().add(board, "4");

		setContentPane(getContainer());
		getCard().show(getContainer(), "1");

	}

	public static GameWindow getInstance() {
		if (instance == null) {
			setInstance(new GameWindow());
		}
		return instance;
	}

	public JPanel addButtonsBuildingPanel1() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

		buttons.setBorder(new EmptyBorder(new Insets(200, 0, 50, 0)));

		String[] gizmoTypes = { "Square Takoz", "Triangle Takoz", "Firildak", "Right Tokat", "Left Tokat" };
		JComboBox<String> addButton = new JComboBox<String>(gizmoTypes);
		addButton.setRenderer(new MyComboBoxRenderer("Add"));
		addButton.setSelectedIndex(-1);
		addButton.setToolTipText("Add a gizmo");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(0));
				if (addButton.getSelectedIndex() != -1) {
					if (!getMoveGizmoController().isHideGizmo()) {
						BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
					}
					getMoveGizmoController().getTimer().stop();
					getMoveGizmoController().setHideGizmo(true);
					getMoveGizmoController().setTaggedGizmo(null);
					switch ((String) addButton.getSelectedItem()) {
					case "Square Takoz":
						getAddGizmoController().buttonClick(BoardObjectEnum.SQUARE_TAKOZ);
						break;
					case "Triangle Takoz":
						getAddGizmoController().buttonClick(BoardObjectEnum.TRIANGLE_TAKOZ);
						break;
					case "Firildak":
						getAddGizmoController().buttonClick(BoardObjectEnum.FIRILDAK);
						break;
					case "Right Tokat":
						getAddGizmoController().buttonClick(BoardObjectEnum.RIGHT_TOKAT);
						break;
					case "Left Tokat":
						getAddGizmoController().buttonClick(BoardObjectEnum.LEFT_TOKAT);
						break;
					default:
						break;
					}
				}
			}
		});
		addButton.setMaximumSize(new Dimension(120, 25));
		buttons.add(addButton);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		JButton button = null;

		button = new JButton("Move");
		button.setToolTipText("Move a gizmo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(0));
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				addButton.setSelectedIndex(-1);
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(120, 25));
		buttons.add(button);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		button = new JButton("Rotate");
		button.setToolTipText("Rotate a gizmo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(0));
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				addButton.setSelectedIndex(-1);
				if (!getMoveGizmoController().isHideGizmo()) {
					BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
				}
				getMoveGizmoController().getTimer().stop();
				getMoveGizmoController().setHideGizmo(true);
				getMoveGizmoController().setTaggedGizmo(null);
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(120, 25));
		buttons.add(button);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		button = new JButton("Delete");
		button.setToolTipText("Delete a gizmo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(0));
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				addButton.setSelectedIndex(-1);
				if (!getMoveGizmoController().isHideGizmo()) {
					BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
				}
				getMoveGizmoController().getTimer().stop();
				getMoveGizmoController().setHideGizmo(true);
				getMoveGizmoController().setTaggedGizmo(null);
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(120, 25));
		buttons.add(button);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		return buttons;
	}

	public JPanel addButtonsBuildingPanel2() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

		buttons.setBorder(new EmptyBorder(new Insets(200, 0, 50, 0)));

		String[] gizmoTypes = { "Square Takoz", "Triangle Takoz", "Firildak", "Right Tokat", "Left Tokat" };
		JComboBox<String> addButton = new JComboBox<String>(gizmoTypes);
		addButton.setRenderer(new MyComboBoxRenderer("Add"));
		addButton.setSelectedIndex(-1);
		addButton.setToolTipText("Add a gizmo");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(1));
				if (addButton.getSelectedIndex() != -1) {
					if (!getMoveGizmoController().isHideGizmo()) {
						BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
					}
					getMoveGizmoController().getTimer().stop();
					getMoveGizmoController().setHideGizmo(true);
					getMoveGizmoController().setTaggedGizmo(null);
					switch ((String) addButton.getSelectedItem()) {
					case "Square Takoz":
						getAddGizmoController().buttonClick(BoardObjectEnum.SQUARE_TAKOZ);
						break;
					case "Triangle Takoz":
						getAddGizmoController().buttonClick(BoardObjectEnum.TRIANGLE_TAKOZ);
						break;
					case "Firildak":
						getAddGizmoController().buttonClick(BoardObjectEnum.FIRILDAK);
						break;
					case "Right Tokat":
						getAddGizmoController().buttonClick(BoardObjectEnum.RIGHT_TOKAT);
						break;
					case "Left Tokat":
						getAddGizmoController().buttonClick(BoardObjectEnum.LEFT_TOKAT);
						break;
					default:
						break;
					}
				}
			}
		});
		addButton.setMaximumSize(new Dimension(120, 25));
		buttons.add(addButton);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		JButton button = null;

		button = new JButton("Move");
		button.setToolTipText("Move a gizmo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(1));
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				addButton.setSelectedIndex(-1);
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(120, 25));
		buttons.add(button);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		button = new JButton("Rotate");
		button.setToolTipText("Rotate a gizmo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(1));
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				addButton.setSelectedIndex(-1);
				if (!getMoveGizmoController().isHideGizmo()) {
					BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
				}
				getMoveGizmoController().getTimer().stop();
				getMoveGizmoController().setHideGizmo(true);
				getMoveGizmoController().setTaggedGizmo(null);
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(120, 25));
		buttons.add(button);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		button = new JButton("Delete");
		button.setToolTipText("Delete a gizmo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentPlayer(BoardModel.getInstance().getPlayers().get(1));
				getDeleteGizmoController().buttonClick();
				addButton.setSelectedIndex(-1);
				if (!getMoveGizmoController().isHideGizmo()) {
					BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
				}
				getMoveGizmoController().getTimer().stop();
				getMoveGizmoController().setHideGizmo(true);
				getMoveGizmoController().setTaggedGizmo(null);
			}
		});
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(120, 25));
		buttons.add(button);
		buttons.add(Box.createRigidArea(new Dimension(0, 5)));

		return buttons;
	}

	public void changeCurrentPlayer(Player player) {
		BoardModel.getInstance().setCurrentPlayer(player);
	}

	public void addButtonsBuildingPanelEnd(JToolBar toolBar) {
		toolBar.setLayout(new GridBagLayout());
		toolBar.setOrientation(SwingConstants.HORIZONTAL);
		toolBar.setFloatable(false);

		JButton button = null;

		button = new JButton("Play");
		button.setToolTipText("Play the game");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() == 4
						&& BoardModel.getInstance().getPlayers().get(1).getGizmoNum() == 4) {
					openLevelSelection();
					if (!getMoveGizmoController().isHideGizmo()) {
						BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
					}
					getMoveGizmoController().getTimer().stop();
					getMoveGizmoController().setHideGizmo(true);
					getMoveGizmoController().setTaggedGizmo(null);
				} else {
					if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() < 4
							&& BoardModel.getInstance().getPlayers().get(1).getGizmoNum() == 4) {
						JOptionPane.showMessageDialog(new JPanel(), "Player1 needs to add "
								+ (4 - BoardModel.getInstance().getPlayers().get(0).getGizmoNum()) + " gizmos!");
					} else {
						if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() == 4
								&& BoardModel.getInstance().getPlayers().get(1).getGizmoNum() < 4) {
							JOptionPane.showMessageDialog(new JPanel(), "Player2 needs to add "
									+ (4 - BoardModel.getInstance().getPlayers().get(1).getGizmoNum()) + " gizmos!");
						} else {
							if (BoardModel.getInstance().getPlayers().get(0).getGizmoNum() < 4
									&& BoardModel.getInstance().getPlayers().get(1).getGizmoNum() < 4) {
								JOptionPane.showMessageDialog(new JPanel(),
										"Player1 needs to add "
												+ (4 - BoardModel.getInstance().getPlayers().get(0).getGizmoNum())
												+ " gizmos!\n" + "Player2 needs to add "
												+ (4 - BoardModel.getInstance().getPlayers().get(1).getGizmoNum())
												+ " gizmos!\n");
							}
						}
					}
				}
			}
		});
		button.setPreferredSize(new Dimension(60, 25));
		toolBar.add(button);

		button = new JButton("Save");
		button.setToolTipText("Save the game");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!getMoveGizmoController().isHideGizmo()) {
					BoardModel.getInstance().showGizmo(getMoveGizmoController().getTaggedGizmo());
				}
				getMoveGizmoController().getTimer().stop();
				getMoveGizmoController().setHideGizmo(true);
				getMoveGizmoController().setTaggedGizmo(null);
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				openFileSaver();
			}
		});
		button.setPreferredSize(new Dimension(60, 25));
		toolBar.add(button);
	}

	public void addButtonsBoardPanel(JToolBar toolBar) {
		toolBar.setLayout(new GridBagLayout());
		toolBar.setOrientation(SwingConstants.HORIZONTAL);
		toolBar.setFloatable(false);

		JButton button = null;

		button = new JButton("Resume");
		button.setToolTipText("Resume the game");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBeginningPanel().getMusic().loop(-1);
				getBeginningPanel().getMusic().start();
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
			}
		});
		button.setPreferredSize(new Dimension(60, 25));
		toolBar.add(button);

		button = new JButton("Pause");
		button.setToolTipText("Pause the game");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBeginningPanel().getMusic().stop();
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
			}
		});
		button.setPreferredSize(new Dimension(60, 25));
		toolBar.add(button);

		button = new JButton("Save");
		button.setToolTipText("Save the game");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBeginningPanel().getMusic().stop();
				cHandler=new ClickHandler();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				cHandler.setController(getMoveGizmoController());
				cHandler.buttonClick();
				
				openFileSaver();
			}
		});
		button.setPreferredSize(new Dimension(60, 25));
		toolBar.add(button);
	}

	public JPanel addScorePanel(JLabel text) {
		JPanel scoreDisplay = new JPanel();
		scoreDisplay.setLayout(new BoxLayout(scoreDisplay, BoxLayout.Y_AXIS));

		scoreDisplay.setBorder(new EmptyBorder(new Insets(240, 0, 50, 0)));

		Font scoreFont = new Font("arial", Font.BOLD, 20);
		text.setFont(scoreFont);
		text.setAlignmentX(CENTER_ALIGNMENT);
		text.setPreferredSize(new Dimension(111, 25));
		scoreDisplay.add(text);

		return scoreDisplay;
	}

	public void openFileChooser() {
		XMLFileChooser fileChooser = new XMLFileChooser();
		int result = fileChooser.showOpenDialog(new JFrame());
		while (result == XMLFileChooser.APPROVE_OPTION) {
			XMLFileValidator validator = new XMLFileValidator();
			File selectedXML = fileChooser.getSelectedFile();
			String isValid = validator.isValid(selectedXML);
			if (!isValid.equals("valid")) {
				JOptionPane.showMessageDialog(new JPanel(), isValid);
				result = fileChooser.showOpenDialog(new JFrame());
			} else {
				String error = getLoadController().loadBoard(selectedXML);
				if (!error.equals("valid")) {
					getLoadController().buttonClick();
					JOptionPane.showMessageDialog(new JPanel(), error);
					result = fileChooser.showOpenDialog(new JFrame());
				} else {
					getCard().show(GameWindow.getContainer(), "4");
					getBoardPanel().requestFocus();
					BoardModel.getInstance().setMode(ModeEnum.RUNNING_MODE);
					BoardModel.getInstance().setState(StateEnum.PLAY);
					if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
						new Thread(new BallThread()).start();
						new Thread(new GizmoThread()).start();
						new Thread(new CezeryeThread()).start();
						getBoardPanel().getTimer().start();
					} else {
						if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL2) {
							new Thread(new BallThread()).start();
							new Thread(new SecondBallThread()).start();
							new Thread(new GizmoThread()).start();
							new Thread(new CezeryeThread()).start();
							getBoardPanel().getTimer().start();
						}
					}
					break;
				}
			}
		}
	}

	public void openFileSaver() {
		XMLFileChooser fileSaver = new XMLFileChooser();
		int result = fileSaver.showSaveDialog(new JFrame());
		if (result == XMLFileChooser.APPROVE_OPTION) {
			getSaveController().saveBoard(fileSaver.getSelectedFile().getAbsolutePath());
			JOptionPane.showMessageDialog(new JPanel(), "Board is successfully saved!");
		}
	}

	public void openLevelSelection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(new Insets(20, 0, 50, 0)));

		JFrame dialog = new JFrame("Level Selection");
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JLabel question = new JLabel("Please select the game level below");
		Font questionFont = new Font("times new roman", Font.BOLD, 14);
		question.setFont(questionFont);
		question.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(question);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		String level1String = "LEVEL 1";
		String level2String = "LEVEL 2";

		JRadioButton level1 = new JRadioButton(level1String);
		JRadioButton level2 = new JRadioButton(level2String);

		level1.setAlignmentX(CENTER_ALIGNMENT);
		level2.setAlignmentX(CENTER_ALIGNMENT);

		level1.setSelected(true);
		level2.setSelected(false);

		level1.setActionCommand(level1String);
		level2.setActionCommand(level2String);

		JRadioButton[] levels = new JRadioButton[2];
		levels[0] = level1;
		levels[1] = level2;

		ButtonGroup group = new ButtonGroup();
		group.add(level1);
		group.add(level2);

		panel.add(level1, BorderLayout.CENTER);
		panel.add(level2, BorderLayout.CENTER);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(80, 25));
		ok.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = group.getSelection().getActionCommand();
				if (command == level1String) {
					BoardModel.getInstance().setLevel(LevelEnum.LEVEL1);
					BoardModel.getInstance().initializeBoardModel();
					getCard().show(GameWindow.getContainer(), "4");
					getPlayController().buttonClick();
					dialog.setVisible(false);
					getBoardPanel().requestFocus();
					new Thread(new BallThread()).start();
					new Thread(new GizmoThread()).start();
					new Thread(new CezeryeThread()).start();
					getBoardPanel().getTimer().start();
				} else {
					if (command == level2String) {
						BoardModel.getInstance().setLevel(LevelEnum.LEVEL2);
						BoardModel.getInstance().initializeBoardModel();
						getCard().show(GameWindow.getContainer(), "4");
						getPlayController().buttonClick();
						dialog.setVisible(false);
						getBoardPanel().requestFocus();
						new Thread(new BallThread()).start();
						new Thread(new SecondBallThread()).start();
						new Thread(new GizmoThread()).start();
						new Thread(new CezeryeThread()).start();
						getBoardPanel().getTimer().start();
					}
				}
			}
		});

		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(panel, CENTER_ALIGNMENT);

		dialog.setPreferredSize(new Dimension(350, 180));
		Dimension dimension = getToolkit().getScreenSize();
		dialog.setLocation((dimension.width - 350) / 2, (dimension.height - 180) / 2);
		dialog.pack();
		dialog.setVisible(true);
	}

	public void updateScores() {
		getPlayer1Score().setText("Score:" + BoardModel.getInstance().getPlayers().get(0).getScore());
		getPlayer2Score().setText("Score:" + BoardModel.getInstance().getPlayers().get(1).getScore());
	}

	public void showGameEndFrame() {
		getBeginningPanel().getMusic().stop();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(new Insets(30, 0, 50, 0)));

		JFrame gameEnd = new JFrame("Game End");
		gameEnd.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		gameEnd.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JLabel info;
		if (BoardModel.getInstance().getPlayers().get(0).getScore() >= 10) {
			info = new JLabel("Player1 won the game!");
		} else {
			if (BoardModel.getInstance().getPlayers().get(1).getScore() >= 10) {
				info = new JLabel("Player2 won the game!");
			} else {
				if (BoardModel.getInstance().getPlayers().get(0).getScore() <= (-10)) {
					info = new JLabel("Player2 won the game!");
				} else {
					info = new JLabel("Player1 won the game!");
				}
			}
		}
		Font infoFont = new Font("times new roman", Font.BOLD, 16);
		info.setFont(infoFont);
		info.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(info);
		panel.add(Box.createRigidArea(new Dimension(0, 40)));

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(80, 25));
		ok.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		Container contentPane = gameEnd.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(panel, CENTER_ALIGNMENT);

		gameEnd.setPreferredSize(new Dimension(350, 180));
		Dimension dimension = getToolkit().getScreenSize();
		gameEnd.setLocation((dimension.width - 350) / 2, (dimension.height - 180) / 2);
		gameEnd.pack();
		gameEnd.setVisible(true);
	}

}

class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MyComboBoxRenderer(String title) {
		setTitle(title);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean hasFocus) {
		if (index == -1 && value == null) {
			setText(getTitle());
		} else {
			setText(value.toString());
		}
		return this;
	}
}