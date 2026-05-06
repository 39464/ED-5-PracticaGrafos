package app;
import grafo.GrafoMA;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;

public class ClubLectura {
    /*
    Nombre:
    Grupo de practicas:
    */

    // Incluir atributos
    // Grafo NO DIRIGIDO que contiene los lectores del club de lectura
    // Lista de vertices del grafo

    private final GrafoMA red;
    private final Lector[] lectores;
    private final Map<Lector, Integer> indiceLectores; // Mapa para obtener el indice de un lector en el grafo
    private final int numLectores;

    // Constructor
    public ClubLectura(Lector[] lectores) {
        this.lectores = lectores;
        this.numLectores = this.lectores.length;
        this.red = new GrafoMA(numLectores, false);
        this.indiceLectores = new HashMap<>();
        for (int i = 0; i < this.numLectores; i++) {
            this.indiceLectores.put(this.lectores[i], i);
        }
    }

    public GrafoMA getRed() {
        return red;
    }

    public Lector[] getLectores() {
        return lectores;
    }

    public int getNumLectores() {
        return numLectores;
    }

    public int getIndice(Lector l) {
        // Devuelve el indice del lector en el grafo, o -1 si no existe
        return this.indiceLectores.getOrDefault(l, -1);
    }

    public boolean conectarLectores(Lector l1, Lector l2) {
        boolean conectado = false;
        int i1 = getIndice(l1);
        int i2 = getIndice(l2);
        if(l1!= null && l2!= null && red.verticeEnRango(i1) && red.verticeEnRango(i2)) {
            red.insertarArista(i1, i2);
            conectado = red.existeArista(i1, i2);
        }
        return conectado;
    }

    public void mostrar() {
        for (int i = 0; i < this.numLectores; i++)
            System.out.println(this.lectores[i]);
        red.mostrar();
    }

    public boolean[] inicializa_visitados() {
        boolean[] visitados = new boolean[numLectores]; // To-Do: Modificar. Tantos como vertices
        for(int i = 0; i < numLectores; i++){
            visitados[i] = false;
        }
        return visitados; // Array de visitados por defecto (ninguno visitado)
    }

    public List<Lector> getAmigos(Lector lector) { // Obtener lista de amigos directos
        List<Lector> amigos = new ArrayList<>();
        if(red.verticeEnRango(getIndice(lector))){

        }
        // ToDo: Completar getAmigos.
        // Si el lector existe y esta en el grafo, devuelve una lista con los amigos
        // Si el lector no existe o no esta en el grafo, devuelve una lista vacia
        return amigos;
    }

    public List<Lector> getGrupo(Lector lector) { // Obtener lista de grupo de amigos del lector
        List<Lector> grupo = new ArrayList<>();
        // ToDo: Completar getGrupo. Apoyate en recorridoEnProfundidadGD de GrafoMA sin modificarlo.
        // Si el lector existe y esta en el grafo, devuelve una lista con los amigos
        // Si el lector no existe o no esta en el grafo, devuelve una lista vacia
        return grupo;
    }

    public List<Lector> mayorGrupo() {
        List<Lector> mayor = new ArrayList<>();
        boolean[] visitados_global = inicializa_visitados();
        // ToDo: Completar mayorGrupo. Devuelve el grupo de amigos mas grande del club de lectura.
        // Apoyate en recorridoEnProfundidadGD de GrafoMA sin modificarlo.
        // Pista: puedes gestionar un array de visitados global y otro local para cada grupo
        return mayor;
    }

    public int contarGrupos() {
        int numGrupos = 0;
        // ToDo: Completar contarGrupos. Devuelve el numero de grupos de amigos distintos que hay en el club de lectura.
        // Apoyate en recorridoEnProfundidadGD de GrafoMA sin modificarlo.
        return numGrupos;
    }

    public String generoMasFrecuenteGrupo(Lector lector) {
        // ToDo: Completar generoMasFrecuenteGrupo. Devuelve el genero mas frecuente entre los amigos del grupo de un lector.
        // Si no existe el lector o no esta en el grafo, devuelve cadena vacia
        // Si hay empate entre varios generos, devuelve cualquiera de ellos
        // Utiliza un TreeMap para contar la frecuencia de cada genero entre los amigos del grupo
        return "";

        //que

    }

}
