package org.Server.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.Server.Test;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Main instance;
	private Stage stage;
	private Thread th;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		instance = this;
		AnchorPane panel = loadPanel();

		stage.setTitle("AwesomeGitmining Server");
		stage.setWidth(500);
		stage.setHeight(250);
		stage.setResizable(false);

		Scene scene = new Scene(panel);
		//stage.setBackground(new ImageView(new Image(Test.class.getResourceAsStream("ui/loading.gif"))));
		scene.getStylesheets().add(Test.class.getResource("ui/server.css").toExternalForm());
		stage.setScene(scene);

		stage.show();

		th = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				RMIHelper.init();
				
				System.out.println("server connected");
			}
		});
	
		th.setDaemon(true);
		th.start();

	}
	
	
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
		System.exit(0);
	}

	public AnchorPane loadPanel() {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(new URL("file:src/main/java/org/Server/ui/server_ui.fxml"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		AnchorPane result = null;
		try {
			result = (AnchorPane) loader.load();
		} catch (IOException e) {
			System.out.println("加载失败");
			e.printStackTrace();
		}
		return result;
	}

	public static Main getInstance() {
		return instance;
	}

	public Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
