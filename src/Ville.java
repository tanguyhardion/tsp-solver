package src;

/**
 * Modèle d'une ville.
 * Contient un index, un nom, une latitude et une longitude.
 * 
 * @author Tanguy Hardion
 */
public class Ville {
    private int index;
    private String nom;
    private double latitude;
    private double longitude;

    public Ville(int index, String nom, double latitude, double longitude) {
        this.index = index;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return index + " " + nom + " " + latitude + " " + longitude;
    }
}
