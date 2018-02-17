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

public class MjPostajeController {


	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;

	@FXML
	private TextField mjernePostajeFilterTextField;

	@FXML
	private TableView<MjernaPostaja> mjernePostajeTableView;

	@FXML
	private TableColumn<MjernaPostaja, String> nazivColumn;

	@FXML
	private TableColumn<MjernaPostaja, String> mjestoColumn;

	@FXML
	private TableColumn<MjernaPostaja, String> geoTockaColumn;

	@FXML
	public void initialize() {

		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiMjPostaje();

		nazivColumn.setCellValueFactory(new PropertyValueFactory<MjernaPostaja, String>("naziv"));

		mjestoColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, 
				ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
				return new ReadOnlyObjectWrapper<String> (param.getValue().getMjesto().getNaziv());
			}

		});

		geoTockaColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, 
				ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
				return new ReadOnlyObjectWrapper<String> (param.getValue().getGeografskaTocka().getX().toString() + " "
						+param.getValue().getGeografskaTocka().getY().toString());
			}

		}); 

	}

	/**
	 * OnAction koji se nalazi na Button-u
	 */

	public void prikaziPostaje() {

		List<MjernaPostaja> filtriranePostaje = new ArrayList<>();

		if (mjernePostajeFilterTextField.getText().isEmpty() == false) {
			filtriranePostaje = listaPostaja.stream().filter(m -> m.getNaziv().contains
					(mjernePostajeFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriranePostaje = listaPostaja;
		}

		ObservableList<MjernaPostaja> listaPostajaTablica = FXCollections.observableArrayList(filtriranePostaje);
		mjernePostajeTableView.setItems(listaPostajaTablica);
	}
}
