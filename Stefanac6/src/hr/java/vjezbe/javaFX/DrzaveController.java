package hr.java.vjezbe.javaFX;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DrzaveController {

	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;

	@FXML
	private TextField drzaveFilterTextField;

	@FXML
	private TableView<Drzava> drzaveTableView;

	@FXML
	private TableColumn<Drzava, String> nazivColumn;

	@FXML
	private TableColumn<Drzava, String> povrsinaColumn;



	@FXML
	public void initialize() {

		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiMjPostaje();

		nazivColumn.setCellValueFactory(new PropertyValueFactory<Drzava, String>("naziv"));

		povrsinaColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Drzava, String>, 
				ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Drzava, String> param) {
				return new ReadOnlyObjectWrapper<String> (param.getValue().getPovrsina().toString());
			}

		});


	}

	public void prikaziDrzave() {

		List<Drzava> filtriraneDrzave = new ArrayList<>();

		if (drzaveFilterTextField.getText().isEmpty() == false) {
			filtriraneDrzave = listaDrzava.stream().filter(m -> m.getNaziv().contains
					(drzaveFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriraneDrzave = listaDrzava;
		}

		ObservableList<Drzava> listaDrzavaTablica = FXCollections.observableArrayList(filtriraneDrzave);
		drzaveTableView.setItems(listaDrzavaTablica);
	}

}
