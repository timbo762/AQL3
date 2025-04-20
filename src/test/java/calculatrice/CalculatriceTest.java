package calculatrice;




import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatriceTest {

    @Mock
    private Calculatrice calculatriceMock;

    @Test
    public void testAdditionnerAvecMockito() {

        when(calculatriceMock.additionner(2, 3)).thenReturn(5);


        int resultat = calculatriceMock.additionner(2, 3);


        assertEquals(5, resultat);

        verify(calculatriceMock).additionner(2, 3);
        verifyNoMoreInteractions(calculatriceMock);


        when(calculatriceMock.getResult()).thenReturn(5);
        assertEquals(5, calculatriceMock.getResult());
        verify(calculatriceMock).getResult();
    }

    @Test
    public void testAdditionnerAvecObjetReel() {

        Calculatrice calculatrice = new Calculatrice();


        int resultat = calculatrice.additionner(2, 3);


        assertEquals(5, resultat);


        assertEquals(5, calculatrice.getResult());
    }
}