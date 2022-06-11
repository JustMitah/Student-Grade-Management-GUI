/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import projectsis.Launcher;
import projectsis.constructors.ModelTable;
import projectsis.constructors.MyConnection;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class SaisirNoteTPController implements Initializable {
    
    @FXML
    private AnchorPane TPGradeinput;
    @FXML
    private JFXTextField PrenomField;

    @FXML
    private JFXTextField FiliereField;

    @FXML
    private JFXTextField NoteField;

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
    
    
    @FXML
    private double xOffset = 0;
    private double yOffset = 0;
    public float NoteMat =0;
    public float NoteMod =0;
    private Stage stage;
    
    private void makeStageDragable() {
		TPGradeinput.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		TPGradeinput.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		TPGradeinput.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		TPGradeinput.setOnMouseReleased((event)->{
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
    public String getMatiere(String matiere){
        Matiere.setText(matiere);
        return matiere;
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
    private void handleClose(MouseEvent event) {
		System.exit(0);
	}
	
    @FXML
    private void handleMini(MouseEvent event) {
		Launcher.stage.setIconified(true);
	}
        
     @FXML
    private void goBack(ActionEvent event) throws IOException 
    {
	Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/GestionFE.fxml"));
	Scene main = new Scene(backLaunch);
	Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
	Start.setScene(main);
	Start.show();
    }
    @FXML
    void EnregistrerTP_Note(ActionEvent event)throws SQLException {
        MyConnection cnx = new MyConnection();    
        Connection con = cnx.getConnection();
        if (NomField.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Choisissez un élève du tableau!");
        } else{
        try{
            
        PreparedStatement pst,pstID,pstTest,pstCoeff,pstNote,pstMoy,pstFin;
        ResultSet rs,rsTest,rsCoeff,rsNote,rsMoy;
        
        int cne = Integer.parseInt(CNEField.getText());
        pstTest=con.prepareStatement("Select CNE_Étudiant,Nom_Matière from étudiant_matière where CNE_Étudiant=? AND Nom_Matière = ?;");
        pstTest.setInt(1,cne);
        pstTest.setString(2,Matiere.getText());
        rsTest=pstTest.executeQuery();
        if(!rsTest.next()){
            pstCoeff = con.prepareStatement("SELECT Coefficient_Écrit,Coefficient_TP,Coefficient_Matière FROM matière WHERE Nom_Matière=?;");
            pstCoeff.setString(1,Matiere.getText());
            rsCoeff = pstCoeff.executeQuery();
            int CoE=0,CoTP=0,CoM=0;
            while(rsCoeff.next()){
            CoE=rsCoeff.getInt("Coefficient_Écrit");
            CoTP=rsCoeff.getInt("Coefficient_TP");
            CoM=rsCoeff.getInt("Coefficient_Matière");
            }
            
            pstID = con.prepareStatement("SELECT id_Module FROM matière where Nom_Matière=?;");
            pstID.setString(1,Matiere.getText());
            rs = pstID.executeQuery();
            int ID=0;
            while(rs.next()){
            ID=rs.getInt("id_Module");
            }
            
            pst = con.prepareStatement("INSERT INTO étudiant_matière(CNE_Étudiant,Nom_Matière,Note_Matière,Note_Écrit,Note_TP,id_Module) VALUES(?,?,?,?,?,?);");
            Float NoteTP = Float.parseFloat(NoteField.getText());
            int NoteÉcrit = 0;
            NoteMat = (CoE*NoteÉcrit + CoTP*NoteTP)/(CoTP+CoE);
            pst.setInt(1,Integer.parseInt(CNEField.getText()));
            pst.setString(2,Matiere.getText());
            pst.setFloat(3,NoteMat);
            pst.setFloat(4,NoteÉcrit);
            pst.setFloat(5,NoteTP);
            pst.setInt(6,ID);
            pst.executeUpdate();
            
            
            NomField.setText("");
            PrenomField.setText("");
            FiliereField.setText("");
            CNEField.setText("");
            NoteField.setText("");

            
        } else{
        pstNote = con.prepareStatement("SELECT Note_Écrit FROM étudiant_matière where Nom_Matière = ? AND CNE_Étudiant =? ;"); 
        pstNote.setString(1,Matiere.getText());
        pstNote.setInt(2,Integer.parseInt(CNEField.getText()));
        rsNote = pstNote.executeQuery();
        
        Float NoteÉcrit = 0.0f;
        if (rsNote.next()){
            NoteÉcrit = rsNote.getFloat("Note_Écrit");
        }
        pstCoeff = con.prepareStatement("SELECT Coefficient_Écrit,Coefficient_TP FROM matière WHERE Nom_Matière=?;");
            pstCoeff.setString(1,Matiere.getText());
            rsCoeff = pstCoeff.executeQuery();
            int CoE=0,CoTP=0;
            while(rsCoeff.next()){
            CoE=rsCoeff.getInt("Coefficient_Écrit");
            CoTP=rsCoeff.getInt("Coefficient_TP");
            }
           
        Float NoteTP = Float.parseFloat(NoteField.getText());
        pst = con.prepareStatement("UPDATE étudiant_matière SET Note_TP=?,Note_Matière =? WHERE CNE_Étudiant=? AND Nom_Matière=?;");
        pst.setFloat(1,NoteTP);
        pst.setInt(3,cne);
        pst.setString(4,Matiere.getText());
        NoteMat = (CoE*NoteÉcrit + CoTP*NoteTP)/(CoE+CoTP);
        pst.setFloat(2,NoteMat);
        pst.executeUpdate();
        
        NomField.setText("");
        PrenomField.setText("");
        FiliereField.setText("");
        CNEField.setText("");
        NoteField.setText("");
            
        }
           /*--------------------------------------------------------------------------------------------
        Calculer et afficher les notes de module
        */
        
        
        PreparedStatement pstGet,pstSet;
        ResultSet rsGet;
          pstGet = con.prepareStatement("Select étudiant_matière.id_Module,\n" +
    "CNE_Étudiant,	\n" +
    "sum(Note_Matière*Coefficient_Matière)/sum(Coefficient_Matière) AS Moy\n" +
    "FROM project.étudiant_matière JOIN project.matière on \n" +
    "project.étudiant_matière.Nom_Matière = project.matière.Nom_Matière\n" +
    "group by id_Module,CNE_Étudiant; ");
        rsGet = pstGet.executeQuery();
        con.prepareStatement("DELETE FROM module_étudiant;").executeUpdate();
        while (rsGet.next()){
            pstSet = con.prepareStatement("INSERT INTO module_étudiant(module_id_Module,étudiant_CNE_étudiant,Note_Module,Validation) VALUES(?,?,?,?);");
            pstSet.setInt(1, rsGet.getInt("id_Module"));
            pstSet.setInt(2, rsGet.getInt("CNE_Étudiant"));
            pstSet.setFloat(3,rsGet.getFloat("Moy"));
            String Validation = "Validé";
            if (rsGet.getFloat("Moy")<10){
                Validation = "Non Validé";
            }
            pstSet.setString(4,Validation);
            pstSet.executeUpdate();      
            
        }
        
        
         /*--------------------------------------------------------------------------------------------
        Calculer et afficher la moyenne finale
        */
           pstMoy = con.prepareStatement("Select étudiant_CNE_Étudiant,\n" +
"    sum(Note_Module*Coefficient_Module)/sum(Coefficient_Module) AS MoyG\n" +
"    FROM project.module_étudiant JOIN project.module on module_id_Module=module.id_Module\n" +
"    group by étudiant_CNE_Étudiant;");
        rsMoy = pstMoy.executeQuery();
        while (rsMoy.next()){
            pstFin = con.prepareStatement("UPDATE étudiant SET Moyenne_Générale=?,Validation=? WHERE CNE_Étudiant=?;");
            pstFin.setFloat(1, rsMoy.getFloat("MoyG"));
            String Validation = "Validé";
            if (rsMoy.getFloat("MoyG")<10){
                Validation = "Non Validé";
            }
            pstFin.setString(2,Validation);
            pstFin.setInt(3, rsMoy.getInt("étudiant_CNE_Étudiant"));
            pstFin.executeUpdate();
         
        }
        
        
        
        
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        
    }
    
    }
    
}
