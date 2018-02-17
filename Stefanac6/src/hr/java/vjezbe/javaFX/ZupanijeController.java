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

public class ZupanijeController {

	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;

	@FXML
	private TextField zupanijeFilterTextField;

	@FXML
	private TableView<Zupanija> zupanijeTableView;

	@FXML
	private TableColumn<Zupanija, String> nazivColumn;

	@FXML
	private TableColumn<Zupanija, String> drzavaColumn;



	@FXML
	public void initialize() {

		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiMjPostaje();

		nazivColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));

		drzavaColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Zupanija, String>, 
				ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Zupanija, String> param) {
				return new ReadOnlyObjectWrapper<String> (param.getValue().getDrzava().getNaziv());
			} 

		}); 


	}

	/**
	 * OnAction koji se nalazi na Button-u
	 */

	public void prikaziZupanije() {

		List<Zupanija> filtriraneZupanije = new ArrayList<>();

		if (zupanijeFilterTextField.getText().isEmpty() == false) {
			filtriraneZupanije = listaZupanija.stream().filter(m -> m.getNaziv().contains
					(zupanijeFilterTextField.getText())).collect(Collectors.toList());
		} else {
			filtriraneZupanije = listaZupanija;
		}

		ObservableList<Zupanija> listaZupanijaTablica = FXCollections.observableArrayList(filtriraneZupanije);
		zupanijeTableView.setItems(listaZupanijaTablica);
	}

}
