/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsis.constructors;

/**
 *
 * @author Kazwell
 */
public class ModelTableMoy {

    /**
     * @return the cne
     */
    public int getCne() {
        return cne;
    }

    /**
     * @param cne the cne to set
     */
    public void setCne(int cne) {
        this.cne = cne;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the filiere
     */
    public String getFiliere() {
        return filiere;
    }

    /**
     * @param filiere the filiere to set
     */
    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    /**
     * @return the moyG
     */
    public float getMoyG() {
        return moyG;
    }

    /**
     * @param moyG the moyG to set
     */
    public void setMoyG(float moyG) {
        this.moyG = moyG;
    }

    /**
     * @return the val
     */
    public String getVal() {
        return val;
    }

    /**
     * @param val the val to set
     */
    public void setVal(String val) {
        this.val = val;
    }
    private int cne;
    private String nom;
    private String prenom;
    private String filiere;
    private float moyG;
    private String val;
    public ModelTableMoy(int cne,String nom,String prenom,String filiere,float moyG,String val){
        this.cne=cne;
        this.nom=nom;
        this.prenom=prenom;
        this.filiere=filiere;
        this.moyG = moyG;
        this.val = val;
    }
}
