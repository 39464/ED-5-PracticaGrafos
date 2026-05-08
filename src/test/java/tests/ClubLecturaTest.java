package tests;

import app.ClubLectura;
import app.Lector;
import grafo.GrafoMA;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;

public class ClubLecturaTest {
    private String captureOutput(Runnable action) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
        try {
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString().replace("\r\n", "\n");
    }

    // ========================================================= //
    // Tests ejemplo 1
    // ========================================================= //

    public ClubLectura construirEjemplo1() {
        Lector ana = new Lector("Ana", 0, 20, "Fantasia");
        Lector luis = new Lector("Luis", 1, 21, "Clasico");
        Lector marta = new Lector("Marta", 2, 25, "Fantasia");
        Lector pedro = new Lector("Pedro", 3, 30, "Sci-Fi");
        Lector lucia = new Lector("Lucia", 4, 22, "Drama");

        ClubLectura club = new ClubLectura(new Lector[]{ana, luis, marta, pedro, lucia});

        // Connections:
        club.conectarLectores(ana, luis);
        club.conectarLectores(ana, marta);
        club.conectarLectores(pedro, lucia);

        return club;
    }

    @Test
    public void testConstructorAndGettersEjemplo1() {
        ClubLectura club = construirEjemplo1();

        assertEquals(5, club.getNumLectores());
        assertEquals("Ana", club.getLectores()[0].getNombre());
        assertEquals("Luis", club.getLectores()[1].getNombre());
        assertEquals("Marta", club.getLectores()[2].getNombre());
        assertEquals("Pedro", club.getLectores()[3].getNombre());
        assertEquals("Lucia", club.getLectores()[4].getNombre());
        GrafoMA red = club.getRed();
        assertNotNull(red);
        assertEquals(5, red.getNumVertices());
        assertFalse(red.getDirigido());
    }

    @Test
    public void testGetIndiceEjemplo1() {
        ClubLectura club = construirEjemplo1();

        assertEquals(0, club.getIndice(new Lector("Ana", 0, 20, "Fantasia")));
        assertEquals(1, club.getIndice(new Lector("Luis", 1, 21, "Clasico")));
        assertEquals(2, club.getIndice(new Lector("Marta", 2, 25, "Fantasia")));
        assertEquals(3, club.getIndice(new Lector("Pedro", 3, 30, "Sci-Fi")));
        assertEquals(4, club.getIndice(new Lector("Lucia", 4, 22, "Drama")));
        assertEquals(-1, club.getIndice(new Lector("No", 9, 30, "Misterio")));
        assertEquals(-1, club.getIndice(null));
    }

    @Test
    public void testConectarLectoresInvalidosEjemplo1() {
        ClubLectura club = construirEjemplo1();
        assertFalse(club.conectarLectores(new Lector("No", 9, 30, "Misterio"), new Lector("Ana", 0, 20, "Fantasia")));
        assertFalse(club.conectarLectores(new Lector("Ana", 0, 20, "Fantasia"), new Lector("No", 9, 30, "Misterio")));
        assertFalse(club.conectarLectores(null, new Lector("Ana", 0, 20, "Fantasia")));
        assertFalse(club.conectarLectores(new Lector("Ana", 0, 20, "Fantasia"), null));
    }

    @Test
    public void testInicializaVisitadosEjemplo1() {
        ClubLectura club = construirEjemplo1();
        boolean[] visitados = club.inicializa_visitados();

        assertEquals(5, visitados.length);
        for (boolean v : visitados) {
            assertFalse(v);
        }
    }

    @Test
    public void testGetAmigosEjemplo1() {
        ClubLectura club = construirEjemplo1();

        List<Lector> amigosAna = club.getAmigos(new Lector("Ana", 0, 20, "Fantasia"));
        assertEquals(2, amigosAna.size());
        assertTrue(amigosAna.contains(new Lector("Luis", 1, 21, "Clasico")));
        assertTrue(amigosAna.contains(new Lector("Marta", 2, 25, "Fantasia")));

        List<Lector> amigosPedro = club.getAmigos(new Lector("Pedro", 3, 30, "Sci-Fi"));
        assertEquals(1, amigosPedro.size());
        assertTrue(amigosPedro.contains(new Lector("Lucia", 4, 22, "Drama")));

        List<Lector> amigosNoExiste = club.getAmigos(new Lector("No", 9, 30, "Misterio"));
        assertTrue(amigosNoExiste.isEmpty());
    }

    @Test
    public void testGetGrupoEjemplo1() {
        ClubLectura club = construirEjemplo1();

        List<Lector> grupoAna = club.getGrupo(new Lector("Ana", 0, 20, "Fantasia"));
        assertEquals(3, grupoAna.size());
        assertTrue(grupoAna.contains(new Lector("Ana", 0, 20, "Fantasia")));
        assertTrue(grupoAna.contains(new Lector("Luis", 1, 21, "Clasico")));
        assertTrue(grupoAna.contains(new Lector("Marta", 2, 25, "Fantasia")));

        List<Lector> grupoPedro = club.getGrupo(new Lector("Pedro", 3, 30, "Sci-Fi"));
        assertEquals(2, grupoPedro.size());
        assertTrue(grupoPedro.contains(new Lector("Pedro", 3, 30, "Sci-Fi")));
        assertTrue(grupoPedro.contains(new Lector("Lucia", 4, 22, "Drama")));

        List<Lector> grupoNoExiste = club.getGrupo(new Lector("No", 9, 30, "Misterio"));
        assertTrue(grupoNoExiste.isEmpty());
    }

    @Test
    public void testMayorGrupoYContarGruposEjemplo1() {
        ClubLectura club = construirEjemplo1();
        assertEquals(2, club.contarGrupos());
        List<Lector> mayor = club.mayorGrupo();
        assertEquals(3, mayor.size());
        assertTrue(mayor.contains(new Lector("Ana", 0, 20, "Fantasia")));
        assertTrue(mayor.contains(new Lector("Luis", 1, 21, "Clasico")));
        assertTrue(mayor.contains(new Lector("Marta", 2, 25, "Fantasia")));
    }

    @Test
    public void testGeneroMasFrecuenteGrupoEjemplo1() {
        ClubLectura club = construirEjemplo1();
        String genero = club.generoMasFrecuenteGrupo(new Lector("Ana", 0, 20, "Fantasia"));
        assertEquals("Fantasia", genero);

        String generoNoExiste = club.generoMasFrecuenteGrupo(new Lector("No", 9, 30, "Misterio"));
        assertEquals("", generoNoExiste);
    }

    @Test
    public void testMostrarEjemplo1() {
        ClubLectura club = construirEjemplo1();
        String expected = "Lector 0: { Ana , edad: 20, genero: Fantasia}\n" +
                "Lector 1: { Luis , edad: 21, genero: Clasico}\n" +
                "Lector 2: { Marta , edad: 25, genero: Fantasia}\n" +
                "Lector 3: { Pedro , edad: 30, genero: Sci-Fi}\n" +
                "Lector 4: { Lucia , edad: 22, genero: Drama}\n" +
                "El grafo tiene una Matriz de 5 x 5\n" +
                "De un grafo No dirigido\n" +
                " F  T  T  F  F \n" +
                " T  F  F  F  F \n" +
                " T  F  F  F  F \n" +
                " F  F  F  F  T \n" +
                " F  F  F  T  F ";

        String salida = captureOutput(club::mostrar);

        assertEquals(expected.trim(), salida.trim());
    }
        
    // ========================================================= //
    // Tests ejemplo 2
    // ========================================================= //

    public ClubLectura construirEjemplo2() {
        Lector alba = new Lector("Alba", 0, 19, "Fantasia");
        Lector berto = new Lector("Berto", 1, 23, "Fantasia");
        Lector ana = new Lector("Ana", 2, 27, "Drama");
        Lector dani = new Lector("Dani", 3, 31, "Clasico");
        Lector eva = new Lector("Eva", 4, 28, "Clasico");
        Lector fran = new Lector("Fran", 5, 22, "Sci-Fi");

        ClubLectura club = new ClubLectura(new Lector[]{alba, berto, ana, dani, eva, fran});

        // Connections:
        club.conectarLectores(alba, berto);
        club.conectarLectores(berto, ana);
        club.conectarLectores(dani, eva);

        return club;
    }

    @Test
    public void testConstructorAndGettersEjemplo2() {
        ClubLectura club = construirEjemplo2();
        assertEquals(6, club.getNumLectores());
        assertEquals("Alba", club.getLectores()[0].getNombre());
        assertEquals("Berto", club.getLectores()[1].getNombre());
        assertEquals("Ana", club.getLectores()[2].getNombre());
        assertEquals("Dani", club.getLectores()[3].getNombre());
        assertEquals("Eva", club.getLectores()[4].getNombre());
        assertEquals("Fran", club.getLectores()[5].getNombre());
        GrafoMA red = club.getRed();
        assertNotNull(red);
        assertEquals(6, red.getNumVertices());
        assertFalse(red.getDirigido());
    }

    @Test
    public void testGetIndiceEjemplo2() {
        ClubLectura club = construirEjemplo2();
        assertEquals(0, club.getIndice(new Lector("Alba", 0, 19, "Fantasia")));
        assertEquals(1, club.getIndice(new Lector("Berto", 1, 23, "Fantasia")));
        assertEquals(2, club.getIndice(new Lector("Ana", 2, 27, "Drama")));
        assertEquals(3, club.getIndice(new Lector("Dani", 3, 31, "Clasico")));
        assertEquals(4, club.getIndice(new Lector("Eva", 4, 28, "Clasico")));
        assertEquals(5, club.getIndice(new Lector("Fran", 5, 22, "Sci-Fi")));
        assertEquals(-1, club.getIndice(new Lector("No", 9, 30, "Misterio")));
        assertEquals(-1, club.getIndice(null));
    }

    @Test
    public void testConectarLectoresInvalidosEjemplo2() {
        ClubLectura club = construirEjemplo2();
        assertFalse(club.conectarLectores(new Lector("No", 9, 30, "Misterio"), new Lector("Alba", 0, 19, "Fantasia")));
        assertFalse(club.conectarLectores(new Lector("Alba", 0, 19, "Fantasia"), new Lector("No", 9, 30, "Misterio")));
        assertFalse(club.conectarLectores(null, new Lector("Alba", 0, 19, "Fantasia")));
        assertFalse(club.conectarLectores(new Lector("Alba", 0, 19, "Fantasia"), null));
    }

    @Test
    public void testInicializaVisitadosEjemplo2() {
        ClubLectura club = construirEjemplo2();
        boolean[] visitados = club.inicializa_visitados();
        assertEquals(6, visitados.length);
        for (boolean v : visitados) {
            assertFalse(v);
        }
    }

    @Test
    public void testGetAmigosEjemplo2() {
        ClubLectura club = construirEjemplo2();
        List<Lector> amigosAlba = club.getAmigos(new Lector("Alba", 0, 19, "Fantasia"));
        assertEquals(1, amigosAlba.size());
        assertTrue(amigosAlba.contains(new Lector("Berto", 1, 23, "Fantasia")));
        List<Lector> amigosDani = club.getAmigos(new Lector("Dani", 3, 31, "Clasico"));
        assertEquals(1, amigosDani.size());
        assertTrue(amigosDani.contains(new Lector("Eva", 4, 28, "Clasico")));
        List<Lector> amigosNoExiste = club.getAmigos(new Lector("No", 9, 30, "Misterio"));
        assertTrue(amigosNoExiste.isEmpty());
    }

    @Test
    public void testGetGrupoEjemplo2() {
        ClubLectura club = construirEjemplo2();
        List<Lector> grupoAlba = club.getGrupo(new Lector("Alba", 0, 19, "Fantasia"));
        assertEquals(3, grupoAlba.size());
        assertTrue(grupoAlba.contains(new Lector("Alba", 0, 19, "Fantasia")));
        assertTrue(grupoAlba.contains(new Lector("Berto", 1, 23, "Fantasia")));
        assertTrue(grupoAlba.contains(new Lector("Ana", 2, 27, "Drama")));
        List<Lector> grupoDani = club.getGrupo(new Lector("Dani", 3, 31, "Clasico"));
        assertEquals(2, grupoDani.size());
        assertTrue(grupoDani.contains(new Lector("Dani", 3, 31, "Clasico")));
        assertTrue(grupoDani.contains(new Lector("Eva", 4, 28, "Clasico")));
        List<Lector> grupoNoExiste = club.getGrupo(new Lector("No", 9, 30, "Misterio"));
        assertTrue(grupoNoExiste.isEmpty());
    }

    @Test
    public void testMayorGrupoYContarGruposEjemplo2() {
        ClubLectura club = construirEjemplo2();
        assertEquals(3, club.contarGrupos());
        List<Lector> mayor = club.mayorGrupo();
        assertEquals(3, mayor.size());
        assertTrue(mayor.contains(new Lector("Alba", 0, 19, "Fantasia")));
        assertTrue(mayor.contains(new Lector("Berto", 1, 23, "Fantasia")));
        assertTrue(mayor.contains(new Lector("Ana", 2, 27, "Drama")));
    }

    @Test
    public void testGeneroMasFrecuenteGrupoEjemplo2() {
        ClubLectura club = construirEjemplo2();
        String generoAlba = club.generoMasFrecuenteGrupo(new Lector("Alba", 0, 19, "Fantasia"));
        assertEquals("Fantasia", generoAlba);

        String generoDani = club.generoMasFrecuenteGrupo(new Lector("Dani", 3, 31, "Clasico"));
        assertEquals("Clasico", generoDani);

        String generoFran = club.generoMasFrecuenteGrupo(new Lector("Fran", 5, 22, "Sci-Fi"));
        assertEquals("Sci-Fi", generoFran);
    }

    @Test
    public void testMostrarEjemplo2() {
        ClubLectura club = construirEjemplo2();
        String expected = "Lector 0: { Alba , edad: 19, genero: Fantasia}\n" +
                "Lector 1: { Berto , edad: 23, genero: Fantasia}\n" +
                "Lector 2: { Ana , edad: 27, genero: Drama}\n" +
                "Lector 3: { Dani , edad: 31, genero: Clasico}\n" +
                "Lector 4: { Eva , edad: 28, genero: Clasico}\n" +
                "Lector 5: { Fran , edad: 22, genero: Sci-Fi}\n" +
                "El grafo tiene una Matriz de 6 x 6\n" +
                "De un grafo No dirigido\n" +
                " F  T  F  F  F  F \n" +
                " T  F  T  F  F  F \n" +
                " F  T  F  F  F  F \n" +
                " F  F  F  F  T  F \n" +
                " F  F  F  T  F  F \n" +
                " F  F  F  F  F  F ";
        
        String salida = captureOutput(club::mostrar);
        assertEquals(expected.trim(), salida.trim());
    }

}
