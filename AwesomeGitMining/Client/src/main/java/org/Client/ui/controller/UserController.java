package org.Client.ui.controller;

import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import org.Client.business.impl.repository.RepositoryServiceImpl;
import org.Client.business.impl.user.UserServiceImpl;
import org.Client.business.service.RepositoryService;
import org.Client.business.service.UserService;
import org.Client.ui.MainUI;
import org.Client.ui.utility.BackHandler;
import org.Client.ui.utility.BackObject;
import org.Client.ui.utility.BackType;
import org.Client.ui.utility.LanguageIcon;
import org.Client.ui.utility.RadarChart;
import org.Client.ui.utility.RaderChartGenerator;
import org.Common.po.Statistics;
import org.Common.vo.Colla_ProVO;
import org.Common.vo.Contri_ProVO;
import org.Common.vo.Crea_ProVO;
import org.Common.vo.RepositoryVO;
import org.Common.vo.UserRateVO;
import org.Common.vo.UserVO;
import org.jfree.data.category.DefaultCategoryDataset;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class UserController implements Initializable {
	private static UserController instance;
	private DefaultCategoryDataset dataset;
	private SwingNode swingNode;
	private Group group;
	@FXML
	private Label userNameLabel;
	@FXML
	private Button user_back;
	@FXML
	private Label joinTime;

	@FXML
	private TableView<Contri_ProVO> Contri_Pro_View;
	@FXML
	private TableColumn<Contri_ProVO, String> Contri_Pro;
	@FXML
	private TableView<Crea_ProVO> Crea_Pro_View;
	@FXML
	private TableColumn<Crea_ProVO, String> Crea_Pro;
	@FXML
	private TableView<Colla_ProVO> Colla_Pro_View;
	@FXML
	private TableColumn<Colla_ProVO, String> Colla_Pro;
	@FXML
	private Label name;
	@FXML
	private Label location;
	@FXML
	private Label url;
	@FXML
	private Label email;
	@FXML
	private Label blog;
	@FXML
	private Label image;
	@FXML
	private Label followers;
	@FXML
	private Label followings;
	@FXML
	private StackPane raderPane;
	@FXML
	private FlowPane languages;

	List<String> text1 = new ArrayList<String>();
	List<String> text2;
	List<String> text3;
	Image img;
	private RepositoryService repositoryImpl;
	private RepositoryVO repository;
	private UserService userImpl;

	public static UserController getInstance() {
		if (instance == null) {
			instance = new UserController();
		}
		return instance;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		instance = this;
		repositoryImpl = RepositoryServiceImpl.getInstance();
		userImpl = UserServiceImpl.getInstance();

		user_back.setOnAction((e) -> {
			BackHandler.getInstance().handleUserBack();
		});

	}

	public void setVO(UserVO vo) {
		if (vo != null) {
			if (vo.getHtml_url() != null) {
				String str = vo.getHtml_url();
				int size = 24;
				int line = str.length() / size;
				String result = "";
				int i = 0;
				for (i = 0; i < line; i++) {
					result += str.substring(i * size, i * size + size) + "\n";
				}
				url.setText(result + str.substring(i * size));
			}

			userNameLabel.setText(vo.getLogin());
			joinTime.setText(vo.getCreated_at());
			name.setText(vo.getName());
			if (vo.getLocation() != null) {
				location.setText(vo.getLocation());
			} else {
				location.setText("He hasn't writen down the location yet :)");
			}
			if (vo.getEmail() != null) {
				email.setText(vo.getEmail());
			} else {
				email.setText("He hasn't writen down the email address yet :)");
			}
			if (vo.getBlog() != null) {
				blog.setText(vo.getBlog());
			} else {
				blog.setText("He hasn't writen down the blog address yet :)");
			}

		}
		followers.setText(vo.getFollowers() + "");
		followings.setText(vo.getFollowing() + "");
		// contributed pros
		if (vo.getContributions_fullname() != null) {
			ObservableList<Contri_ProVO> contri_pros = FXCollections.observableArrayList();
			for (String name : vo.getContributions_fullname()) {
				Contri_ProVO cv = new Contri_ProVO(name);
				contri_pros.add(cv);
			}
			Contri_Pro_View.setItems(contri_pros);
			Contri_Pro.setCellFactory(new ContributionCellFactory());
			Contri_Pro.setCellValueFactory(cellData -> cellData.getValue().getProperty());
		} else {
			text1.add("Hasn't contributed any projects.:)");
		}

		// created pros
		if (vo.getRepos_fullname() != null) {
			ObservableList<Crea_ProVO> crea_pros = FXCollections.observableArrayList();
			for (String name : vo.getRepos_fullname()) {
				Crea_ProVO cv = new Crea_ProVO(name);
				crea_pros.add(cv);
			}
			Crea_Pro_View.setItems(crea_pros);
			Crea_Pro.setCellFactory(new CreateCellFactory());
			Crea_Pro.setCellValueFactory(cellData -> cellData.getValue().getProperty());
		} else {
			text2 = new ArrayList<String>();
			text2.add(" ");
			text2.add(" ");
			text2.add("Hasn't created any projects.:)");
		}

		if (vo.getCollaboration_fullname() != null) {
			ObservableList<Colla_ProVO> colla_pros = FXCollections.observableArrayList();
			for (String name : vo.getCollaboration_fullname()) {
				Colla_ProVO cv = new Colla_ProVO(name);
				colla_pros.add(cv);
			}
			Colla_Pro_View.setItems(colla_pros);
			Colla_Pro.setCellFactory(new CollaborationCellFactory());
			Colla_Pro.setCellValueFactory(cellData -> cellData.getValue().getProperty());
		} else {
			text3 = new ArrayList<String>();
			text3.add(" ");
			text3.add(" ");
			text3.add("Hasn't collaborated any projects.:)");
		}
		// raderchart
		UserRateVO ratevo = userImpl.getEvaluation(vo.getLogin());

		if (ratevo != null) {

			createRader(ratevo.getRates());
		}

		// 鏄剧ず澶村儚
		showAvatar(vo.getLogin());
		setLanguages(vo.getLogin());
	}

	private void createRader(Map<String, Double> map) {

		raderPane.getChildren().clear();

		String[] labels = Statistics.userRader.clone();
		Double[] values = new Double[map.size()];
		for(int i=0;i<map.size();i++)
			values[i] = map.get(labels[i]);
		group = RaderChartGenerator.getInstance().createPanel(labels,values,8);
		
		raderPane.getChildren().clear();
		raderPane.getChildren().add(group);
	}

	// 鍦ㄥ姞杞藉ご鍍忕殑鍚屾椂鏄剧ず杩涘害鏉�
	public void showAvatar(String login) {

		UserService user = UserServiceImpl.getInstance();
		img = user.getAvatar(login);

		ProgressIndicator pin = new ProgressIndicator(-1);
		pin.setMaxSize(60, 60);
		image.setAlignment(Pos.CENTER);
		image.setGraphic(pin);

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				UserService user = UserServiceImpl.getInstance();
				img = user.getAvatar(login);

				updateProgress(1, 1);

				return null;
			}

		};
		pin.progressProperty().bind(img.progressProperty());

		pin.progressProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
			if (new_val.intValue() == 1) {
				if (img != null) {
					image.setGraphic(new ImageView(img));
				} else
					image.setGraphic(
							new ImageView(new Image(MainUI.class.getResourceAsStream("style/morentouxiang.jpg"))));
			}
		});
	}

	private class ContributionCellFactory
			implements Callback<TableColumn<Contri_ProVO, String>, TableCell<Contri_ProVO, String>> {

		@Override
		public TableCell<Contri_ProVO, String> call(TableColumn<Contri_ProVO, String> arg0) {
			TextFieldTableCell<Contri_ProVO, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseReleased((MouseEvent t) -> {
				String temp = cell.getText();
				repository = repositoryImpl.searchRepositoryInfo(temp);

				if (repository != null) {
					BackHandler.getInstance().setRepoBack(new BackObject(BackType.USER, userNameLabel.getText(), 0));
					MainController.getInstance().setGroup("Ui_ProjectPanel.fxml");
					ProjectController.getInstance().setVO(repository);
				}
			});
			cell.setOnMouseEntered((MouseEvent t) -> {
				cell.setCursor(Cursor.HAND);
				cell.setUnderline(true);
			});
			cell.setOnMouseExited((MouseEvent t) -> {
				cell.setCursor(Cursor.DEFAULT);
				cell.setUnderline(false);
			});

			return cell;
		}
	}

	private class CreateCellFactory
			implements Callback<TableColumn<Crea_ProVO, String>, TableCell<Crea_ProVO, String>> {

		@Override
		public TableCell<Crea_ProVO, String> call(TableColumn<Crea_ProVO, String> arg0) {
			TextFieldTableCell<Crea_ProVO, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseReleased((MouseEvent t) -> {
				String temp = cell.getText();
				repository = repositoryImpl.searchRepositoryInfo(temp);

				if (repository != null) {
					BackHandler.getInstance().setRepoBack(new BackObject(BackType.USER, userNameLabel.getText(), 0));
					MainController.getInstance().setGroup("Ui_ProjectPanel.fxml");
					ProjectController.getInstance().setVO(repository);
				}
			});
			cell.setOnMouseEntered((MouseEvent t) -> {
				cell.setCursor(Cursor.HAND);
				cell.setUnderline(true);
			});
			cell.setOnMouseExited((MouseEvent t) -> {
				cell.setCursor(Cursor.DEFAULT);
				cell.setUnderline(false);
			});
			return cell;
		}
	}

	private class CollaborationCellFactory
			implements Callback<TableColumn<Colla_ProVO, String>, TableCell<Colla_ProVO, String>> {

		@Override
		public TableCell<Colla_ProVO, String> call(TableColumn<Colla_ProVO, String> arg0) {
			TextFieldTableCell<Colla_ProVO, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseReleased((MouseEvent t) -> {
				String temp = cell.getText();
				repository = repositoryImpl.searchRepositoryInfo(temp);

				if (repository != null) {
					BackHandler.getInstance().setRepoBack(new BackObject(BackType.USER, userNameLabel.getText(), 0));
					MainController.getInstance().setGroup("Ui_ProjectPanel.fxml");
					ProjectController.getInstance().setVO(repository);
				}
			});
			cell.setOnMouseEntered((MouseEvent t) -> {
				cell.setCursor(Cursor.HAND);
				cell.setUnderline(true);
			});
			cell.setOnMouseExited((MouseEvent t) -> {
				cell.setCursor(Cursor.DEFAULT);
				cell.setUnderline(false);
			});
			return cell;
		}
	}

	private void setLanguages(String login) {
		List<String> language = userImpl.getLanguageSkills(login);
		Label label = null;
		for (int i = 0; i < language.size(); i++) {
			label = new Label();
			if (language.get(i) != null)
				label.setGraphic(new ImageView(setIcon(language.get(i))));
			label.setFont(Font.font("Arial", 17));
			label.setText(language.get(i));
			label.setPrefSize(116, 30);
			languages.getChildren().add(label);
		}
	}

	private Image setIcon(String name) {
		Image result = null;
		int in = LanguageIcon.getInstance().hasLanguage(name);
		if (in == -1) {
			result = new Image(MainUI.class.getResourceAsStream("style/other.png"));
		} else if(name.equals("C#")){
			result = new Image(MainUI.class.getResourceAsStream("style/CS.png"));
		}else{
			result = new Image(MainUI.class.getResourceAsStream("style/" + name + ".png"));
		}
		return result;
	}
}
