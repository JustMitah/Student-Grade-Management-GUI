package projectsis.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectsis.Launcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projectsis.constructors.MyConnection;


public class GestionFEController implements Initializable{
       
        @FXML
        private AnchorPane ChildFE;
	
	
        @FXML
        private JFXTextField nbtp;

        @FXML
        private JFXTextField coeffe;

        @FXML
        private JFXTextField coefftp;
        
        @FXML
        private JFXTextField coeffm;

        @FXML
        private JFXComboBox<String> csem;

        @FXML
        private JFXComboBox<String> cmod;

        @FXML
        private JFXComboBox<String> cmat;

	@FXML
        private JFXComboBox<String> cf;
    
        ObservableList <String> ComboFiliere = FXCollections.observableArrayList();
        ObservableList <String> ComboSemestre = FXCollections.observableArrayList();
        ObservableList <String> ComboModule = FXCollections.observableArrayList();
        ObservableList <String> ComboMatiere = FXCollections.observableArrayList();
        //ObservableList <String> ComboSession = FXCollections.observableArrayList();

    @FXML
    void InsertFData(ActionEvent event) throws SQLException {
        MyConnection cnx = new MyConnection();    
        Connection con = cnx.getConnection();
        int cm = Integer.parseInt(coeffm.getText());
        int ntp = Integer.parseInt(nbtp.getText());
        int ctp=0,ce=1;
        
        if (!(coeffe.getText().equals("") && coefftp.getText().equals(""))){
            ce = Integer.parseInt(coeffe.getText());
            ctp = Integer.parseInt(coefftp.getText());
        }
        try{
        PreparedStatement pst,pst1,pst2;
        
        pst = con.prepareStatement("UPDATE matière SET Nb_SéanceTP=?,Coefficient_Matière=? WHERE Nom_Matière = ?;");
        pst.setInt(1,ntp);
        pst.setInt(2,cm);
        pst.setString(3,cmat.getValue());
        pst.executeUpdate();
        
        pst1 = con.prepareStatement("UPDATE matière SET Coefficient_TP=? WHERE Nom_Matière = ?;");
        pst1.setInt(1,ctp);
        pst1.setString(2,cmat.getValue());
        pst1.executeUpdate();
        
        pst2 = con.prepareStatement("UPDATE matière SET Coefficient_Écrit=? WHERE Nom_Matière = ?;");
        pst2.setInt(1,ce);
        pst2.setString(2,cmat.getValue());
        pst2.executeUpdate();
        JOptionPane.showMessageDialog(null,"Données Enregistrées");
        cmat.setValue("");
        csem.setValue(""); 
        cmod.setValue(""); 
        cf.setValue(""); 
        coeffm.setText("");
        nbtp.setText("");
        coeffe.setText("");
        coefftp.setText("");
        cf.requestFocus();
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,ex);
        }
    }   
    
        private List <String> ComboFiliere() throws Exception{
            MyConnection cnx = new MyConnection();    
            Connection con = cnx.getConnection();
            List <String> choice = new ArrayList<>();
            String query = "Select Nom_Filière from filière";
            try{
                PreparedStatement stat= (PreparedStatement) con.prepareStatement(query);
                ResultSet rslt = stat.executeQuery();
                while(rslt.next()){
                    choice.add(rslt.getString("Nom_Filière"));
                }
                stat.close();
                rslt.close();
                return choice;                   
            } catch (SQLException ex){
                Logger.getLogger(GestionFEController.class.getName()).log(Level.SEVERE,null,ex);
            }
            return null;
        }
        private List <String> ComboSemestre() throws Exception{
            MyConnection cnx = new MyConnection();    
            Connection con = cnx.getConnection();
            List <String> choice = new ArrayList<>();
            String query = "Select Nom_Semestre from semestre";
            try{
                PreparedStatement stat= (PreparedStatement) con.prepareStatement(query);
                ResultSet rslt = stat.executeQuery();
                while(rslt.next()){
                    choice.add(rslt.getString("Nom_Semestre"));
                }
                stat.close();
                rslt.close();
                return choice;                   
            } catch (SQLException ex){
                Logger.getLogger(GestionFEController.class.getName()).log(Level.SEVERE,null,ex);
            }
            return null;
        }
        private List <String> ComboMatiere() throws Exception{
            MyConnection cnx = new MyConnection();    
            Connection con = cnx.getConnection();
            List <String> choice = new ArrayList<>();
            String query = "Select Nom_Matière from matière";
            try{
                PreparedStatement stat= (PreparedStatement) con.prepareStatement(query);
                ResultSet rslt = stat.executeQuery();
                while(rslt.next()){
                    choice.add(rslt.getString("Nom_Matière"));
                    
                }
                stat.close();
                rslt.close();
                return choice;                   
            } catch (SQLException ex){
                Logger.getLogger(GestionFEController.class.getName()).log(Level.SEVERE,null,ex);
            }
            return null;
        }
        private List <String> ComboModule() throws Exception{
            MyConnection cnx = new MyConnection();    
            Connection con = cnx.getConnection();
            List <String> choice = new ArrayList<>();
            String query = "Select Nom_Module from module";
            try{
                PreparedStatement stat= (PreparedStatement) con.prepareStatement(query);
                ResultSet rslt = stat.executeQuery();
                while(rslt.next()){
                    choice.add(rslt.getString("Nom_Module"));
                    
                }
                stat.close();
                rslt.close();
                return choice;                   
            } catch (SQLException ex){
                Logger.getLogger(GestionFEController.class.getName()).log(Level.SEVERE,null,ex);
            }
            return null;
        }
        
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
                try{
                cf.setItems(FXCollections.observableArrayList(ComboFiliere()));
                csem.setItems(FXCollections.observableArrayList(ComboSemestre()));
                cmod.setItems(FXCollections.observableArrayList(ComboModule()));
                cmat.setItems(FXCollections.observableArrayList(ComboMatiere()));
                //csess.setItems(FXCollections.observableArrayList(ComboSession()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }	
        
        private double xOffset = 0;
	private double yOffset = 0;
	private Stage stage;
	private void makeStageDragable() {
		ChildFE.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		ChildFE.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		ChildFE.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		ChildFE.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
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
    void goToCDM(ActionEvent event) throws IOException {
        JOptionPane.showMessageDialog(null,"Fonction en cours de maintenance");
        Parent InterfaceCM = FXMLLoader.load(getClass().getResource("/projectsis/scenes/CoordonnateurDeModule.fxml"));
	Scene icm = new Scene(InterfaceCM);
	Stage ICMchild = (Stage)((Node)event.getSource()).getScene().getWindow();
	ICMchild.setScene(icm);
	ICMchild.show();
        

    }

    @FXML
    void goToListe(ActionEvent event) throws IOException {
       /*if (cmod.getValue()== null){
            JOptionPane.showMessageDialog(null,"Séléctionnez un module!");
        } else { */
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/projectsis/scenes/ChoisirMéthode.fxml"));
           try{
               Parent SaisirSTP = (Parent) loader.load();
                Scene choix = new Scene(SaisirSTP);
                Stage Choixchild = (Stage)((Node)event.getSource()).getScene().getWindow();
                Choixchild.setScene(choix);
                Choixchild.show();             
       } catch (IOException e){
    }
    }
    
    @FXML
    void goToMP(ActionEvent event) throws IOException {
        JOptionPane.showMessageDialog(null,"Fonction en cours de maintenance");
        Parent TMP = FXMLLoader.load(getClass().getResource("/projectsis/scenes/TraitementMP.fxml"));
	Scene MP= new Scene(TMP);
	Stage MPchild = (Stage)((Node)event.getSource()).getScene().getWindow();
	MPchild.setScene(MP);
	MPchild.show();
    }
   

    @FXML
    void goToSaisirNE(ActionEvent event) throws IOException {
         if (cmat.getValue()== null){
            JOptionPane.showMessageDialog(null,"Séléctionnez une matière!");
        } else { 
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/projectsis/scenes/SaisirNoteÉcrit.fxml"));
           try{
               Parent SaisirNE = (Parent) loader.load();
                Scene NE = new Scene(SaisirNE);
                Stage NEchild = (Stage)((Node)event.getSource()).getScene().getWindow();
                NEchild.setScene(NE);
                NEchild.show();
                SaisirNoteÉcritController snec = loader.getController();
                snec.getMatiere(cmat.getValue());              
       } catch (IOException e){
    }}
    }
   
    
    @FXML
    void goToSaisirNTP(ActionEvent event) throws IOException {
        
       if (cmat.getValue()== null){
            JOptionPane.showMessageDialog(null,"Séléctionnez une matière!");
        } else { 
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/projectsis/scenes/SaisirNoteTP.fxml"));
           try{
               Parent SaisirNE = (Parent) loader.load();
                Scene NP = new Scene(SaisirNE);
                Stage NPchild = (Stage)((Node)event.getSource()).getScene().getWindow();
                NPchild.setScene(NP);
                NPchild.show();
                SaisirNoteTPController sntpc = loader.getController();
                sntpc.getMatiere(cmat.getValue());              
       } catch (IOException e){
    }}}

    @FXML
    void goToSaisirSTP(ActionEvent event)  throws IOException {
         if (cmat.getValue()== null){
            JOptionPane.showMessageDialog(null,"Séléctionnez une matière!");
        } else { 
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/projectsis/scenes/SaisirSéanceTP.fxml"));
           try{
               Parent SaisirSTP = (Parent) loader.load();
                Scene STP = new Scene(SaisirSTP);
                Stage STPchild = (Stage)((Node)event.getSource()).getScene().getWindow();
                STPchild.setScene(STP);
                STPchild.show();
                SaisirSéanceTPController sstpc = loader.getController();
                sstpc.getMatiere(cmat.getValue());              
       } catch (IOException e){
    }}
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
		Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/LoginEnseignant.fxml"));
		Scene main = new Scene(backLaunch);
		Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
		Start.setScene(main);
		Start.show();
	}


   

    
}