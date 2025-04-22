package jeu;

public class Jeu {
    private final Banque banque;
    private boolean ouvert;

    public Jeu(Banque labanque) {
        this.banque = labanque;
        this.ouvert = true;
    }

    public void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException {
        if (!estOuvert()) {
            throw new JeuFermeException("Le jeu est fermé");
        }

        int mise = joueur.mise();

        try {
            joueur.debiter(mise);
            banque.crediter(mise);

            int resultat = de1.lancer() + de2.lancer();

            if (resultat == 7) {
                int gain = mise * 2;
                banque.debiter(gain);
                joueur.crediter(gain);

                if (!banque.est_solvable()) {
                    fermer();
                }
            }
        } catch (DebitImpossibleException e) {
            fermer();
        }
    }

    public void fermer() {
        this.ouvert = false;
    }

    public boolean estOuvert() {
        return this.ouvert;
    }
}