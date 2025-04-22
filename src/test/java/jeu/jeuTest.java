package jeu;

import jeu.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class jeuTest {

    @Mock
    private Banque banqueMock;
    @Mock
    private Joueur joueurMock;
    @Mock
    private De de1Mock;
    @Mock
    private De de2Mock;

    private Jeu jeu;

    @Before
    public void setUp() {
        jeu = new Jeu(banqueMock);
    }

    @Test(expected = JeuFermeException.class)
    public void testJouer_JeuFerme() throws JeuFermeException, DebitImpossibleException {
        // Arrange
        jeu.fermer();

        // Act
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Assert - l'exception est attendue
        // Vérifier qu'aucune interaction n'a eu lieu
        verifyNoInteractions(joueurMock, de1Mock, de2Mock, banqueMock);
    }

    @Test
    public void testJouer_JoueurInsolvable() throws JeuFermeException, DebitImpossibleException {
        // Arrange
        when(joueurMock.mise()).thenReturn(100);
        doThrow(new DebitImpossibleException("Solde insuffisant"))
                .when(joueurMock).debiter(100);

        // Act
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Assert
        verify(joueurMock).mise();
        verify(joueurMock).debiter(100);
        verifyNoInteractions(de1Mock, de2Mock);
        verifyNoMoreInteractions(joueurMock);
        assertFalse(jeu.estOuvert());
    }

    @Test
    public void testJouer_PartiePerdue() throws JeuFermeException, DebitImpossibleException {
        // Arrange
        when(joueurMock.mise()).thenReturn(100);
        when(de1Mock.lancer()).thenReturn(2);
        when(de2Mock.lancer()).thenReturn(2);

        // Act
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Assert
        verify(joueurMock).mise();
        verify(joueurMock).debiter(100);
        verify(banqueMock).crediter(100);
        verify(de1Mock).lancer();
        verify(de2Mock).lancer();
        verifyNoMoreInteractions(joueurMock, banqueMock);
        assertTrue(jeu.estOuvert());
    }

    @Test
    public void testJouer_PartieGagnee_BanqueSolvable() throws JeuFermeException, DebitImpossibleException {
        // Arrange
        when(joueurMock.mise()).thenReturn(100);
        when(de1Mock.lancer()).thenReturn(3);
        when(de2Mock.lancer()).thenReturn(4);
        when(banqueMock.est_solvable()).thenReturn(true);

        // Act
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Assert
        verify(joueurMock).mise();
        verify(joueurMock).debiter(100);
        verify(joueurMock).crediter(200);
        verify(banqueMock).crediter(100);
        verify(banqueMock).debiter(200);
        verify(banqueMock).est_solvable();
        verify(de1Mock).lancer();
        verify(de2Mock).lancer();
        assertTrue(jeu.estOuvert());
    }

    @Test
    public void testJouer_PartieGagnee_BanqueInsolvable() throws JeuFermeException, DebitImpossibleException {
        // Arrange
        when(joueurMock.mise()).thenReturn(100);
        when(de1Mock.lancer()).thenReturn(3);
        when(de2Mock.lancer()).thenReturn(4);
        when(banqueMock.est_solvable()).thenReturn(false);

        // Act
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Assert
        verify(joueurMock).mise();
        verify(joueurMock).debiter(100);
        verify(joueurMock).crediter(200);
        verify(banqueMock).crediter(100);
        verify(banqueMock).debiter(200);
        verify(banqueMock).est_solvable();
        verify(de1Mock).lancer();
        verify(de2Mock).lancer();
        assertFalse(jeu.estOuvert());
    }
}