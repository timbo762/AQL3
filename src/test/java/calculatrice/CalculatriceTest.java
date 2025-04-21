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

        when(calculatriceMock.additionner(6, 1)).thenReturn(7);


        int resultat = calculatriceMock.additionner(6, 1);


        assertEquals(7, resultat);

        verify(calculatriceMock).additionner(6, 1);
        verifyNoMoreInteractions(calculatriceMock);


        when(calculatriceMock.getResult()).thenReturn(7);
        assertEquals(7, calculatriceMock.getResult());
        verify(calculatriceMock).getResult();
    }

    @Test
    public void testAdditionnerAvecObjetReel() {

        Calculatrice calculatrice = new Calculatrice();


        int resultat = calculatrice.additionner(6, 1);


        assertEquals(7, resultat);


        assertEquals(7, calculatrice.getResult());
    }
}