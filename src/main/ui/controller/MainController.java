package main.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import main.ui.MainUI;
import main.ui.utility.fxmlLoader;

public class MainController implements Initializable {

	private static MainController instance;

	public static MainController getInstance() {
		if (instance == null) {
			instance = new MainController();
		}
		return instance;
	}

	@FXML
	private AnchorPane center_panel, common;
	@FXML
	private TextField search;
	@FXML
	private Button btn_search, btn_menu;
	@FXML
	private Label logo;
	@FXML
	private Label minimize;
	@FXML
	private Label exit;

	public String getSearchId() {
		return search.getText();
	}

	public void initSearchId() {
		search.setPromptText("search what you want...");
		search.setText("");
	}

	/**
	 * when start the app, init the homePagePanel
	 */
	public void initPanel() {
		setPanel("Ui_HomePagePanel.fxml");
	}

	/**
	 * the common method to change the current panel
	 *
	 * @param panel
	 */
	public void setPanel(String name) {
		AnchorPane panel = fxmlLoader.loadPanel(name);
		center_panel.getChildren().clear();
		center_panel.getChildren().add(panel);
		// otherwise the searchButton cannot use
		common.toFront();
	}

	public void setGroup(String name) {
		Group group = fxmlLoader.loadGroup(name);
		center_panel.getChildren().clear();
		center_panel.getChildren().add(group);
		// otherwise the searchButton cannot use
		common.toFront();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;

		Tooltip toolTip = new Tooltip();
		toolTip.setText("enter the keyword of what you want to search");
		search.setTooltip(toolTip);

		buttonInit();
		logoInit();
	}

	private void buttonInit() {
		/*
		 * use lambda expression to refactor code
		 */
		btn_search.setOnAction((e) -> {
			if (getSearchId() != null && !getSearchId().isEmpty())
				setPanel("Ui_SearchPanel.fxml");
		});

		btn_menu.setOnAction((e) -> {
			initPanel();
		});

		search.setOnKeyPressed((e) -> {
			if(e.getCode() == KeyCode.ENTER){
				if (getSearchId() != null && !getSearchId().isEmpty())
					setPanel("Ui_SearchPanel.fxml");
			}
		});

		minimize.setOnMouseEntered((e) -> {
			labelInit(minimize,"min_hover.png");
		});
		minimize.setOnMouseExited((e) -> {
			labelInit(minimize,"min_normal.png");
		});
		minimize.setOnMousePressed((e) -> {
			labelInit(minimize,"min_active.png");
		});
		minimize.setOnMouseReleased((e) -> {
			MainUI.getUI().getStage().setIconified(true);
		});

		exit.setOnMouseEntered((e) -> {
			labelInit(exit,"exitFrameExit.png");
		});
		exit.setOnMouseExited((e) -> {
			labelInit(exit,"exitFrameMove.png");
		});
		exit.setOnMousePressed((e) -> {
			labelInit(exit,"exitFrameClick.png");
		});
		exit.setOnMouseReleased((e) -> {
			System.exit(0);
		});

	}

	private void logoInit() {
		logo.setText("awesomeGitmining");
		logo.setFont(Font.font("Calibri", 27));
		labelInit(logo,"mark.png");
		labelInit(minimize, "min_normal.png");
		labelInit(exit, "exitFrameExit.png");
	}

	public void labelInit(Label label,String path) {
		Image image = new Image(MainUI.class.getResourceAsStream("style/"+path));
		label.setGraphic(new ImageView(image));
	}
	public String dateConvert(String str){
		if(str.contains("T")||str.contains("Z")){
			str.replace("T", " ");
			str.replace("Z", " ");
		}
		return str;

	}

}
