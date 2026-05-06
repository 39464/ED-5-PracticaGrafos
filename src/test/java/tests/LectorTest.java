package tests;

import app.Lector;
import org.junit.Test;

import static org.junit.Assert.*;

public class LectorTest {
    @Test
    public void testConstructorAndGetters() {
        Lector lector = new Lector("Ana", 3, 25, "Fantasia");
        assertEquals("Ana", lector.getNombre());
        assertEquals(3, lector.getId());
        assertEquals(25, lector.getEdad());
        assertEquals("Fantasia", lector.getGeneroLibroFavorito());
    }

    @Test
    public void testSetters() {
        Lector lector = new Lector("", 0, 0, "");
        lector.setNombre("Luis");
        lector.setId(7);
        lector.setEdad(31);
        lector.setGeneroLibroFavorito("Clasico");

        assertEquals("Luis", lector.getNombre());
        assertEquals(7, lector.getId());
        assertEquals(31, lector.getEdad());
        assertEquals("Clasico", lector.getGeneroLibroFavorito());
    }

    @Test
    public void testToString() {
        Lector lector = new Lector("Ana", 3, 25, "Fantasia");
        String esperado = "Lector 3: { Ana , edad: 25, genero: Fantasia}";
        assertEquals(esperado, lector.toString());
    }

    @Test
    public void testEqualsById() {
        Lector l1 = new Lector("Ana", 1, 20, "Fantasia");
        Lector l2 = new Lector("Otra", 1, 30, "Clasico");
        Lector l3 = new Lector("Ana", 2, 20, "Fantasia");

        assertTrue(l1.equals(l2));
        assertFalse(l1.equals(l3));
        assertFalse(l1.equals(null));
    }
}
