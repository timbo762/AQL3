package userservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UtilisateurApi utilisateurApiMock;

    @Test
    public void testCreerUtilisateur() throws ServiceException {

        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");


        when(utilisateurApiMock.creerUtilisateur(utilisateur)).thenReturn(true);


        UserService userService = new UserService(utilisateurApiMock);


        boolean resultat = userService.creerUtilisateur(utilisateur);


        verify(utilisateurApiMock, times(1)).creerUtilisateur(utilisateur);
        assertTrue(resultat);
    }

    @Test(expected = ServiceException.class)
    public void testCreerUtilisateur_Exception() throws ServiceException {

        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");


        when(utilisateurApiMock.creerUtilisateur(utilisateur))
                .thenThrow(new ServiceException("Echec de la création de l'utilisateur"));


        UserService userService = new UserService(utilisateurApiMock);


        userService.creerUtilisateur(utilisateur);
    }

    @Test
    public void testCreerUtilisateur_ValidationError() throws ServiceException {

        Utilisateur utilisateur = new Utilisateur("", "", "");


        UserService userService = new UserService(utilisateurApiMock);


        boolean resultat = userService.creerUtilisateur(utilisateur);


        verify(utilisateurApiMock, never()).creerUtilisateur(utilisateur);
        assertFalse(resultat);
    }

    @Test
    public void testCreerUtilisateur_WithId() throws ServiceException {

        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");


        when(utilisateurApiMock.creerUtilisateur(utilisateur)).thenReturn(true);

        // Définition d'un ID fictif
        int idUtilisateur = 123;

        // Configuration du mock pour renvoyer l'ID
        utilisateur.setId(idUtilisateur);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        boolean resultat = userService.creerUtilisateur(utilisateur);

        // Vérification de l'ID de l'utilisateur
        assertEquals(idUtilisateur, utilisateur.getId());
        assertTrue(resultat);
    }

    @Test
    public void testCreerUtilisateur_ArgumentCaptor() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");

        // Configuration du mock
        when(utilisateurApiMock.creerUtilisateur(any(Utilisateur.class))).thenReturn(true);

        // Création du service avec le mock
        UserService userService = new UserService(utilisateurApiMock);

        // Appel de la méthode à tester
        userService.creerUtilisateur(utilisateur);

        // Création du capteur d'arguments
        ArgumentCaptor<Utilisateur> argumentCaptor = ArgumentCaptor.forClass(Utilisateur.class);
        verify(utilisateurApiMock).creerUtilisateur(argumentCaptor.capture());

        // Récupération de l'utilisateur capturé
        Utilisateur utilisateurCapture = argumentCaptor.getValue();

        // Vérification des arguments capturés
        assertEquals("Jean", utilisateurCapture.getNom());
        assertEquals("Dupont", utilisateurCapture.getPrenom());
        assertEquals("jeandupont@email.com", utilisateurCapture.getEmail());
    }
}