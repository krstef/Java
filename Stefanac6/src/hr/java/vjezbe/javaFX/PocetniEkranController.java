package hr.java.vjezbe.javaFX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class PocetniEkranController {

	private List<Drzava> listaDrzava;  
	private List<Zupanija> listaZupanija;
	private List<Mjesto> listaMjesta; 
	private List<Senzor> listaSenzora;  
	private List<MjernaPostaja> listaPostaja; 

	@FXML
	private TextField mjestaFilterTextField;

	@FXML
	private TableView<Mjesto> mjestaTableView;

	@FXML  
	private TableColumn<Mjesto, String> nazivColumn;    

	@FXML  
	private TableColumn<Mjesto, String> tipColumn;    

	@FXML  
	private TableColumn<Mjesto, String> zupanijaColumn;

	@FXML  
	public void initialize() { 

		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiMjPostaje();

		nazivColumn.setCellValueFactory(new PropertyValueFactory<Mjesto, String>("naziv"));

		tipColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Mjesto, String>, 
				ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
				return new ReadOnlyObjectWrapper<String> (param.getValue().getVrstaMjesta().toString());
			}
		}
				);

		/*zupanijaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Mjesto, String>, 
				ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getZupanija().getNaziv());
			}
		}
				);*/

	}

	public void prikaziMjesta() {

		List<Mjesto> filtriranaMjesta = new ArrayList<Mjesto>();

		if(mjestaFilterTextField.getText().isEmpty() == false) {
			filtriranaMjesta = listaMjesta.stream().filter(m -> m.getNaziv().contains(
					mjestaFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriranaMjesta = listaMjesta;
		}

		ObservableList<Mjesto> listaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		mjestaTableView.setItems(listaMjesta);
	}

	public void prikaziEkranDrzave() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Drzave.fxml"));
			BorderPane zupanijePane = (BorderPane) loader.load();			 

			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void prikaziEkranZupanije() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Zupanije.fxml"));
			BorderPane zupanijePane = (BorderPane) loader.load();			 

			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void prikaziEkranMjesta() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Mjesta.fxml"));
			BorderPane zupanijePane = (BorderPane) loader.load();			 

			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void prikaziEkranMjernePostaje() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("MjernePostaje.fxml"));
			BorderPane zupanijePane = (BorderPane) loader.load();			 

			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	public void prikaziEkranSenzora() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Senzori.fxml"));
			BorderPane zupanijePane = (BorderPane) loader.load();			 

			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

/*	private static BorderPane root;
	private Stage primaryStage;

	@Override
	public void start(Stage stage) {
		primaryStage = stage;

		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("PocetniEkran.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (Exception e) {
			e.printStackTrace();		
		}
	}

	public static void setCenterPane(BorderPane centerPane) {   
		root.setCenter(centerPane);  
	}

	public void prikaziEkranZupanije() {   

		try {    
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Zupanije.fxml"));    
			Main.setCenterPane(zupanijePane);   
		} 
		catch (IOException e) {
			e.printStackTrace();   
		}  
	} */
}