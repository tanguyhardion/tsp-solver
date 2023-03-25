package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logique de l'application.
 * Contient les algorithmes de recherche de voisins.
 */
public class Manager {
    private List<Ville> listeVilles = new ArrayList<>();
    private List<Ville> tournee = new ArrayList<>();

    /**
     * Lit le fichier contenant les villes et les ajoute à la liste des villes.
     * 
     * @param file le fichier contenant les villes
     */
    public void readVilles(String file) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String ligne = "";

            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split(" ");

                Ville ville = new Ville(Integer.parseInt(data[0]), data[1],
                        Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                listeVilles.add(ville);
            }
            reader.close();

        } catch (FileNotFoundException fileNotFound) {
            throw new FileNotFoundException("Fichier introuvable");
        }
    }

    /**
     * Affiche les villes dans la console.
     * 
     * @throws IOException
     */
    public void afficherVilles() throws IOException {
        for (Ville v : listeVilles) {
            System.out.println(v);
        }
    }

    /**
     * Calcule et renvoie la distance en kilomètres entre deux villes
     * en utilisant la formule de Haversine.
     *
     * @param ville1 la première ville
     * @param ville2 la deuxième ville
     * @return la distance en kilomètres entre ville1 et ville2
     */
    public double distance(Ville ville1, Ville ville2) {
        int r = 6371;
        double x1 = Math.toRadians(ville1.getLongitude());
        double y1 = Math.toRadians(ville1.getLatitude());
        double x2 = Math.toRadians(ville2.getLongitude());
        double y2 = Math.toRadians(ville2.getLatitude());

        return Math.abs(r * Math.acos(
                Math.sin(y1) * Math.sin(y2)) + (Math.cos(y1) * Math.cos(y2) * Math.cos(x1 - x2)));
    }

    /**
     * Affiche la tournée passée en paramètre dans la console.
     * 
     * @param tournee la tournée à afficher
     */
    public void afficherTournee(List<Ville> tournee) {
        System.out.print("[");
        for (int i = 0; i < tournee.size() - 1; i++) {
            System.out.print(tournee.get(i).getIndex() + ", ");
        }
        System.out.print(tournee.get(tournee.size() - 1).getIndex() + "]\n");
    }

    /**
     * Génère et renvoie une tournée aléatoire à partir
     * de la liste passée en paramètre.
     * 
     * @param liste la liste des villes
     * @return une tournée aléatoire
     */
    public List<Ville> tourneeAleatoire(List<Ville> liste) {
        List<Ville> aleatoire = new ArrayList<>(liste);
        Collections.shuffle(aleatoire);
        return aleatoire;
    }

    /**
     * Calcule et renvoie le coût de la tournée passée en paramètre.
     * 
     * @param tournee la tournée dont on veut calculer le coût
     * @return le coût de la tournée
     */
    public double cout(List<Ville> tournee) {
        double cout = 0;
        for (int i = 0; i < tournee.size() - 1; i++) {
            cout += this.distance(tournee.get(i), tournee.get(i + 1));
        }
        cout += this.distance(tournee.get(0), tournee.get(tournee.size() - 1));
        return cout;
    }

    /**
     * Calcule le trajet de plus proche voisin à partir
     * de la ville de départ passée en paramètre.
     * 
     * @param s la ville de départ
     * @return une liste ordonnée des villes visitées
     */
    public List<Ville> plusProcheVoisin(Ville s) {
        List<Ville> t1 = new ArrayList<>();
        List<Ville> nonVisite = listeVilles;

        nonVisite.remove(s);

        Ville suivant = null;
        while (!nonVisite.isEmpty()) {
            double distance = 999999;
            for (Ville v : nonVisite) {
                if (this.distance(s, v) < distance) {
                    distance = this.distance(s, v);
                    suivant = v;
                }
            }
            t1.add(suivant);
            nonVisite.remove(suivant);
            s = suivant;
        }

        return t1;
    }

    /**
     * Recherche locale basée sur l'heuristique "Premier d'abord"
     * pour une liste donnée de villes.
     * 
     * @param t_entree la liste de villes où sera appliquée la recherche locale
     * @return la liste de villes trouvée par la recherche locale
     */
    public List<Ville> rechercheLocale(List<Ville> t_entree) {
        List<Ville> t_courante = new ArrayList<>(t_entree);
        boolean a = false;
        while (!a) {
            a = true;
            List<Ville> t_voisins = rechercheLocalePremierDabord(t_courante);
            double c = this.cout(t_courante);
            if (this.cout(t_voisins) < c) {
                a = false;
                t_courante = t_voisins;
            }
        }
        return t_courante;
    }

    /**
     * Heuristique "Premier d'abord" pour une liste donnée de villes.
     * 
     * @param t_entree la liste de villes où sera appliquée l'heuristique
     * @return la liste de villes trouvée par l'heuristique
     */
    public List<Ville> rechercheLocalePremierDabord(List<Ville> t_entree) {
        List<Ville> t_courante = new ArrayList<>(t_entree);
        for (int i = 0; i < t_courante.size(); i++) {
            if (this.distance(t_courante.get((i - 1 + t_courante.size()) % t_courante.size()),
                    t_courante.get(i % t_courante.size()))
                    + this.distance(t_courante.get((i + 1) % t_courante.size()),
                            t_courante.get((i + 2) % t_courante.size())) > this.distance(
                                    t_courante.get((i - 1 + t_courante.size()) % t_courante.size()),
                                    t_courante.get((i + 1) % t_courante.size()))
                                    + this.distance(t_courante.get(i % t_courante.size()),
                                            t_courante.get((i + 2) % t_courante.size()))) {
                Collections.swap(t_courante, i, (i + 1) % t_courante.size());
            }
        }
        return t_courante;
    }

    public List<Ville> getTournee() {
        return this.tournee;
    }

    public void setTournee(List<Ville> tournee) {
        this.tournee = tournee;
    }

    public List<Ville> getListeVilles() {
        return this.listeVilles;
    }
}
