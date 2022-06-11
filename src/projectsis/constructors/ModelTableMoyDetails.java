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
public class ModelTableMoyDetails {

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
     * @return the nomMod
     */
    public String getNomMod() {
        return nomMod;
    }

    /**
     * @param nomMod the nomMod to set
     */
    public void setNomMod(String nomMod) {
        this.nomMod = nomMod;
    }

    /**
     * @return the noteMod
     */
    public float getNoteMod() {
        return noteMod;
    }

    /**
     * @param noteMod the noteMod to set
     */
    public void setNoteMod(float noteMod) {
        this.noteMod = noteMod;
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
    private String nomMod;
    private float noteMod;
    private String val;
    
     public ModelTableMoyDetails(int cne,String nom,String prenom,String nomMod,float noteMod,String val){
        this.cne=cne;
        this.nom=nom;
        this.prenom=prenom;
        this.nomMod=nomMod;
        this.noteMod =noteMod;
        this.val = val;
    }
}
