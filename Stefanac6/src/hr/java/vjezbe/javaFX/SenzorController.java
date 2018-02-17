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
import javafx.util.Callback;

public class SenzorController {

	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;

	@FXML
	private TextField senzoriFilterTextField;

	@FXML
	private TableView<Senzor> senzoriTableView;

	@FXML
	private TableColumn<Senzor, String> mjernaJedinicaColumn;

	@FXML
	private TableColumn<Senzor, String> preciznostColumn;



	@FXML
	public void initialize() {

		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiMjPostaje();

		mjernaJedinicaColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Senzor, String>, 
				ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
				return new ReadOnlyObjectWrapper<String> (param.getValue().getMjernaJedinica());
			}

		});

		preciznostColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Senzor, String>, 
				ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
				return new ReadOnlyObjectWrapper<String> (Byte.toString(param.getValue().getPreciznost()));
			}

		});


	}

	/**
	 * OnAction koji se nalazi na Button-u
	 */

	public void prikaziSenzore() {

		List<Senzor> filtriraniSenzori = new ArrayList<>();

		if (senzoriFilterTextField.getText().isEmpty() == false) {
			filtriraniSenzori = listaSenzora.stream().filter(m -> m.getMjernaJedinica().contains
					(senzoriFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriraniSenzori = listaSenzora;
		}

		ObservableList<Senzor> listaSenzoraTablica = FXCollections.observableArrayList(filtriraniSenzori);
		senzoriTableView.setItems(listaSenzoraTablica);
	}
}
