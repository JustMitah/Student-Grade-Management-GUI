package projectsis.constructors;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class CreationPDFforMOYD { 

	 public static void generateMoyD(int cne) throws FileNotFoundException, DocumentException {
             Document document = new Document();
		 try
		 {
                        
			PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Kazwell/Documents/NetBeansProjects/ProjectSIS/src/projectsis/constructors/Bulletin Semestriel.pdf"));
			document.open();
			 
			// Insertion des logos
			Image img =Image.getInstance("C:/Users/Kazwell/Documents/NetBeansProjects/ProjectSIS/src/projectsis/images/newLOGO INSA.png");
			img.scalePercent(20);
                        document.add(img);
			 
			 Font font = new Font();  //create d'un font pour modifier le text
			 font.setFamily("Times New Roman (Titres CS)");
			 font.setColor(30,127,203);
			 font.setSize(18);
			 String titre="Bulletin Semestriel" ;
			 Paragraph p =new Paragraph(titre,font); //ajouter le font à la paragraphe 
			 p.setAlignment(Element.ALIGN_CENTER);//Alignement au center
			 p.setSpacingAfter(20);
			 document.add(p);
			 
			 PdfPTable table = new PdfPTable(6);//créer un tableau 
                         
			 PdfPCell cellule1=new PdfPCell(new Phrase("CNE"));
			 cellule1.setHorizontalAlignment(1);
			 cellule1.setGrayFill(0.8f);
			 table.addCell(cellule1);
			 
			 PdfPCell cellule2=new PdfPCell(new Phrase("Nom"));
			 cellule2.setHorizontalAlignment(1);
			 cellule2.setGrayFill(0.8f);
			 table.addCell(cellule2);
			 
			 PdfPCell cellule3=new PdfPCell(new Phrase("Prénom"));
			 cellule3.setHorizontalAlignment(2);
			 cellule3.setGrayFill(0.8f);
			 table.addCell(cellule3);
                         
			 PdfPCell cellule4=new PdfPCell(new Phrase("Nom du module"));
			 cellule4.setHorizontalAlignment(1);
			 cellule4.setGrayFill(0.8f);
			 table.addCell(cellule4);
                         
			 PdfPCell cellule5=new PdfPCell(new Phrase("Moyenne du module"));
			 cellule5.setHorizontalAlignment(1);
			 cellule5.setGrayFill(0.8f);
			 table.addCell(cellule5);
			 
			 PdfPCell cellule6=new PdfPCell(new Phrase("Validation"));
			 cellule6.setHorizontalAlignment(1);
			 cellule6.setGrayFill(0.8f);
			 table.addCell(cellule6);
			 
			 /**************** Création de la liste ******************/
			 
			MyConnection cnx = new MyConnection();
                        Connection con = cnx.getConnection();
			Statement statement = (Statement) con.createStatement(); 
			
			String requete="SELECT CNE_Étudiant,Nom_Étudiant,Prénom_Étudiant,Nom_Module,Note_Module,module_étudiant.Validation\n" +
"FROM project.module_étudiant join project.module join project.étudiant \n" +
"on étudiant_CNE_Étudiant = CNE_Étudiant and module.id_module = module_id_Module;";
				    
			ResultSet resultset = statement.executeQuery(requete);
			while(resultset.next())
			{
                        if(resultset.getInt("CNE_Étudiant") == cne){
                        table.addCell(resultset.getString("CNE_Étudiant"));
		    	table.addCell(resultset.getString("Nom_Étudiant"));
		    	table.addCell(resultset.getString("Prénom_Étudiant"));
		    	table.addCell(resultset.getString("Nom_Module"));
                        table.addCell(String.valueOf(resultset.getFloat("Note_Module")));
		    	table.addCell(resultset.getString("Validation"));
			}}
			
			document.add(table);
			
			resultset.close();
			statement.close();
			document.close();
		 } 
		 catch (DocumentException | IOException | SQLException e) 
		 {
                     JOptionPane.showMessageDialog(null,e);
		 } 
        
	 }

}
