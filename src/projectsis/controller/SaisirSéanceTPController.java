package projectsis.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import projectsis.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projectsis.constructors.ModelTable;
import projectsis.constructors.MyConnection;

public class SaisirSéanceTPController implements Initializable {
	
    @FXML
    private AnchorPane TPinput;
    
    @FXML
    private JFXTextField PrenomField;

    @FXML
    private JFXTextField FiliereField;

    @FXML
    private JFXTextField NbField;

    @FXML
    private JFXTextField CNEField;

    @FXML
    private JFXTextField NomField;
    
    @FXML
    private TableView<ModelTable> Table_TP;

    @FXML
    private TableColumn<ModelTable,String> col_CNE;

    @FXML
    private TableColumn<ModelTable,String> col_Nom;

    @FXML
    private TableColumn<ModelTable,String> col_Prénom;

    @FXML
    private TableColumn<ModelTable,String> col_Filière;
    
    @FXML
    private JFXTextField Matiere;
    
    /**
     * 
     * @param matiere
     * @return
     */
    //Permet de récupérer le contenu de la combobox, le return pour l'utiliser après
    public String getMatiere(String matiere){
        Matiere.setText(matiere);
        return matiere;
    }
    
    
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
	
    private void makeStageDragable() {
		TPinput.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		TPinput.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		TPinput.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		TPinput.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
    
	ObservableList <ModelTable> oblist = FXCollections.observableArrayList();
        
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
                try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select e.CNE_Étudiant,e.Nom_Étudiant,e.Prénom_Étudiant,f.Nom_Filière from étudiant e left join filière f ON e.id_filière=f.id_filière");
            while (rs.next()) {
                oblist.add(new ModelTable(rs.getInt("CNE_Étudiant"),rs.getString("Nom_Étudiant"),rs.getString("Prénom_Étudiant"),rs.getString("Nom_Filière")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SaisirNoteTPController.class.getName()).log(Level.SEVERE, null, ex);
        }
            col_CNE.setCellValueFactory(new PropertyValueFactory<>("cne"));
            col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_Prénom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            col_Filière.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            Table_TP.setItems(oblist);
	}
	
        @FXML
	private void goBack(ActionEvent event) throws IOException {
		Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/GestionFE.fxml"));
		Scene main = new Scene(backLaunch);
		Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
		Start.setScene(main);
		Start.show();
	}

    @FXML
    private void handleClose(MouseEvent event) {
		System.exit(0);
	}
	
    @FXML
    private void handleMini(MouseEvent event) {
		Launcher.stage.setIconified(true);
	}
    @FXML
    void TP_TableClicked(MouseEvent event) {
        ModelTable tp = Table_TP.getSelectionModel().getSelectedItem();
            int cne = tp.getCne();
            String nom = tp.getNom();
            String prenom = tp.getPrenom();
            String filiere = tp.getFiliere();
            CNEField.setText(String.valueOf(cne));
            NomField.setText(nom);
            PrenomField.setText(prenom);
            FiliereField.setText(filiere);
    }

    @FXML
    void EnregistrerSTP_Note(ActionEvent event)throws SQLException {
        MyConnection cnx = new MyConnection();    
        Connection con = cnx.getConnection();
        if (NomField.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Choisissez un élève du tableau!");
        } else{
        try{
        PreparedStatement pst,pstID,pstTest;
        ResultSet rs,rsTest;
        int cne = Integer.parseInt(CNEField.getText());
        pstTest=con.prepareStatement("Select CNE_Étudiant,Nom_Matière from étudiant_matière where CNE_Étudiant=? AND Nom_Matière = ?;");
        pstTest.setInt(1,cne);
        pstTest.setString(2,Matiere.getText());
        rsTest=pstTest.executeQuery();
        if(!rsTest.next()){
            pstID = con.prepareStatement("select id_Module from matière where Nom_Matière=?;");
            pstID.setString(1,Matiere.getText());
            rs = pstID.executeQuery();
            int ID=0;
            while(rs.next()){
            ID=rs.getInt("id_Module");
            }
            pst = con.prepareStatement("INSERT INTO étudiant_matière(CNE_Étudiant,Nom_Matière,Nb_Séance_Présent,id_Module) VALUES(?,?,?,?);");
            pst.setInt(1,Integer.parseInt(CNEField.getText()));
            pst.setString(2,Matiere.getText());
            pst.setInt(3,Integer.parseInt(NbField.getText()));
            pst.setInt(4,ID);
            pst.executeUpdate();
            NomField.setText("");
            PrenomField.setText("");
            FiliereField.setText("");
            CNEField.setText("");
            NbField.setText("");
        } else{
        //JOptionPane.showMessageDialog(null,rsTest.getInt("CNE_Étudiant")+" et "+rsTest.getString("Nom_Matière")); 
        pst = con.prepareStatement("UPDATE étudiant_matière SET Nb_Séance_Présent=? WHERE CNE_Étudiant=? AND Nom_Matière=?;");
        pst.setInt(1,Integer.parseInt(NbField.getText()));
        pst.setInt(2,cne);
        pst.setString(3,Matiere.getText());
        pst.executeUpdate();
        NomField.setText("");
        PrenomField.setText("");
        FiliereField.setText("");
        CNEField.setText("");
        NbField.setText("");
        }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }}
}
