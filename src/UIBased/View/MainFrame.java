package UIBased.View;

import UIBased.Model.Data.Referee;
import UIBased.Presenter.BattlePresenter;
import UIBased.Presenter.BattleSettingsPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Runs the main for the UI version of the game. Loads the BattleSettings and Battle Views
 */
public class MainFrame extends Application
{
	/**
	 * Launches the UI version of the game
	 * @param args
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception 
	{
    	launch(args);
    }

    /**
     * Sets the stage for the battleSettings view and instantiates the referee for the game
     */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{	
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UIBased/View/BattleSettings.fxml"));	
			BorderPane root = fxmlLoader.load();
			Scene scene = new Scene(root, 1000, 500);

			Referee ref = new Referee();
			
			BattleSettingsController settingsController = fxmlLoader.getController();
			BattleSettingsPresenter battleSettingsPresenter = new BattleSettingsPresenter(scene, ref);
			
			settingsController.setPresenter(battleSettingsPresenter);
			battleSettingsPresenter.setView(settingsController);
			
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.show();		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	} 
	
	
}
