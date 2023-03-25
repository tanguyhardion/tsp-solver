package src;

import java.io.IOException;
import java.util.List;

/**
 * Classe principale de l'application.
 * Affiche les villes, les distances entre les villes, les tournées et leur
 * coût.
 */
public class App {

    public static void main(String[] args) throws IOException {
        Manager manager = new Manager();
        manager.readVilles("../data/top80.txt");
        List<Ville> villes = manager.getListeVilles();

        System.out.println("-------------- Villes --------------");
        System.out.println("Numéro Nom Latitude Longitude");
        manager.afficherVilles();
        System.out.println("------------------------------------\n");

        System.out.println("Distance entre ville 1 et ville 2 : " + manager.distance(villes.get(0), villes.get(1)));

        manager.setTournee(villes);
        System.out.println("\n-------------- Premières Tournées --------------");
        System.out.print("Tournée croissante : ");
        manager.afficherTournee(manager.getTournee());
        System.out.println("Coût de la tournée croissante : " + manager.cout(manager.getTournee()));

        System.out.print("Tournée aléatoire : ");
        manager.afficherTournee(manager.tourneeAleatoire(villes));
        System.out.println("Coût de la tournée aléatoire : " + manager.cout(manager.tourneeAleatoire(villes)));

        System.out.println("\n-------------- Plus Proche Voisin --------------");
        Ville v = new Ville(villes.get(0).getIndex(), villes.get(0).getNom(), villes.get(0).getLatitude(),
                villes.get(0).getLongitude());
        List<Ville> plusProcheVoisin = manager.plusProcheVoisin(v);
        System.out.print("Tournée plus proche voisin : ");
        manager.afficherTournee(plusProcheVoisin);
        System.out.println("Coût de la tournée plus proche voisin : " + manager.cout(plusProcheVoisin) + "\n");

        System.out.println("\n-------------- Échange de successeurs --------------");
        List<Ville> premierDabord = manager.rechercheLocale(plusProcheVoisin);
        manager.afficherTournee(premierDabord);
        System.out.println("Coût de la tournée premier d'abord : " + manager.cout(premierDabord));
    }

}
