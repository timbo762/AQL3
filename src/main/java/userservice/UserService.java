package userservice;

public class UserService {
    private final UtilisateurApi utilisateurApi;

    public UserService(UtilisateurApi utilisateurApi) {
        this.utilisateurApi = utilisateurApi;
    }

    public boolean creerUtilisateur(Utilisateur utilisateur) throws ServiceException {
        if (utilisateur.getNom().isEmpty() || utilisateur.getPrenom().isEmpty() || utilisateur.getEmail().isEmpty()) {
            return false;
        }
        return utilisateurApi.creerUtilisateur(utilisateur);
    }
}
