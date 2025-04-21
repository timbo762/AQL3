package userservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import userservice.ServiceException;
import userservice.UtilisateurApi;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UtilisateurApi utilisateurApiMock;

    @Test
    public void testCreerUtilisateur() throws ServiceException {

        Utilisateur utilisateur = new Utilisateur("oussama", "zahali", "oussamazahali@email.com");


        when(utilisateurApiMock.creerUtilisateur(utilisateur)).thenReturn(true);


        UserService userService = new UserService(utilisateurApiMock);


        boolean resultat = userService.creerUtilisateur(utilisateur);


        verify(utilisateurApiMock, times(1)).creerUtilisateur(utilisateur);
        assertTrue(resultat);
    }

    @Test(expected = ServiceException.class)
    public void testCreerUtilisateur_Exception() throws ServiceException {

        Utilisateur utilisateur = new Utilisateur("oussama", "zahali", "oussamazahali@email.com");


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

        Utilisateur utilisateur = new Utilisateur("oussama", "zahali", "oussamazahali@email.com");


        when(utilisateurApiMock.creerUtilisateur(utilisateur)).thenReturn(true);


        int idUtilisateur = 123;


        utilisateur.setId(idUtilisateur);


        UserService userService = new UserService(utilisateurApiMock);


        boolean resultat = userService.creerUtilisateur(utilisateur);


        assertEquals(idUtilisateur, utilisateur.getId());
        assertTrue(resultat);
    }

    @Test
    public void testCreerUtilisateur_ArgumentCaptor() throws ServiceException {

        Utilisateur utilisateur = new Utilisateur("Joussama", "zahali", "oussamazahali@email.com");


        when(utilisateurApiMock.creerUtilisateur(any(Utilisateur.class))).thenReturn(true);


        UserService userService = new UserService(utilisateurApiMock);


        userService.creerUtilisateur(utilisateur);


        ArgumentCaptor<Utilisateur> argumentCaptor = ArgumentCaptor.forClass(Utilisateur.class);
        verify(utilisateurApiMock).creerUtilisateur(argumentCaptor.capture());


        Utilisateur utilisateurCapture = argumentCaptor.getValue();


        assertEquals("oussama", utilisateurCapture.getNom());
        assertEquals("zahali", utilisateurCapture.getPrenom());
        assertEquals("oussamazahali@gmail.com", utilisateurCapture.getEmail());
    }
}