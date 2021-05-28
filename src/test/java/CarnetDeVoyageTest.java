import carnet.exceptions.PageInexistanteException;
import carnet.exceptions.SupprimerPageDePresentationException;
import carnet.model.CarnetDeVoyage;
import carnet.model.PageDuCarnet;
import carnet.outil.FabriqueNumeroPage;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarnetDeVoyageTest {
    private CarnetDeVoyage car2voy;
    private FabriqueNumeroPage fabrik;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        fabrik = FabriqueNumeroPage.getInstance();
        fabrik.reset();
        car2voy = new CarnetDeVoyage();
    }

    @org.junit.jupiter.api.Test
    void ajouterPage() {
        assertEquals(car2voy.getNbPagesDuCarnet(), 1);
        car2voy.ajouterPage();
        assertEquals(car2voy.getNbPagesDuCarnet(), 2);
        for (int i = 0; i < 9; ++i)
            car2voy.ajouterPage();
        assertEquals(car2voy.getNbPagesDuCarnet(), 11);
    }

    @org.junit.jupiter.api.Test
    void supprimerPage() {
        assertEquals(car2voy.getNbPagesDuCarnet(), 1);
        assertThrows(SupprimerPageDePresentationException.class, () -> car2voy.supprimerPage(1));
        for (int i = 0; i < 9; ++i)
            car2voy.ajouterPage();
        assertEquals(car2voy.getNbPagesDuCarnet(), 10);
        assertDoesNotThrow(() -> car2voy.supprimerPage(9));
        assertThrows(PageInexistanteException.class, () -> car2voy.supprimerPage(9));
        assertEquals(car2voy.getNbPagesDuCarnet(), 9);
        assertDoesNotThrow(() -> car2voy.supprimerPage(5));
        assertDoesNotThrow(() -> car2voy.supprimerPage(6));
        assertEquals(car2voy.getNbPagesDuCarnet(), 7);
    }

    @org.junit.jupiter.api.Test
    void pagePrecedente() {
        assertEquals(car2voy.getNbPagesDuCarnet(), 1);
        assertEquals(car2voy.pagePrecedente(), car2voy.getPageDePresentation());

        car2voy.ajouterPage();
        assertEquals(car2voy.getNbPagesDuCarnet(), 2);
        assertEquals(car2voy.pagePrecedente(), car2voy.getPageDePresentation());

        car2voy.ajouterPage();
        assertEquals(car2voy.getNbPagesDuCarnet(), 3);
        assertEquals(car2voy.pagePrecedente(), car2voy.getPageDePresentation());

        car2voy.ajouterPage();
        car2voy.ajouterPage();
        try {
            assertEquals(car2voy.pagePrecedente(), car2voy.getPageDuCarnetAvecUnNumero(4));
        } catch (PageInexistanteException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void pageSuivante() {
        assertEquals(car2voy.getNbPagesDuCarnet(), 1);
        car2voy.pageSuivante();
        assertEquals(car2voy.getNbPagesDuCarnet(), 2);

        car2voy.pagePrecedente();
        car2voy.pageSuivante();
        assertEquals(car2voy.getNbPagesDuCarnet(), 2);
        assertDoesNotThrow(() -> car2voy.getPageDuCarnetAvecUnNumero(2));
    }

    @org.junit.jupiter.api.Test
    void raffraichirIndices() {
        //On appel la fonction raffraichir dans la fonction supprimer, il n'est donc pas nécessaire de refaire un autre appel à la fonction raffraichir
        PageDuCarnet pageARaffraichir;
        car2voy.ajouterPage();
        car2voy.ajouterPage();
        assertDoesNotThrow(() -> car2voy.supprimerPage(2));
        car2voy.ajouterPage();

        int cpt = 2;
        for (Iterator<PageDuCarnet> iter = car2voy.iteratorPageDuCarnet(); iter.hasNext(); ) {
            pageARaffraichir = iter.next();
            assertEquals(pageARaffraichir.getIndicePage(), cpt);
            cpt++;
        }
    }
}