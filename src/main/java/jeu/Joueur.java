package jeu;

public interface Joueur {
    int mise(); // on suppose que mise positive
    void debiter(int somme) throws DebitImpossibleException;
    void crediter(int somme);
}