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
    Nombre: Irene Lombardo Cabrera
    Grupo de practicas: IWSIM12
    */

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
        boolean[] visitados = new boolean[numLectores];
        for(int i = 0; i < numLectores; i++){
            visitados[i] = false;
        }
        return visitados;
    }

    public List<Lector> getAmigos(Lector lector) {
        List<Lector> amigos = new ArrayList<>();
        if(lector!= null) {
            if (red.verticeEnRango(getIndice(lector))) {
                for (int i = 0; i < numLectores; i++) {
                    if (red.existeArista(getIndice(lector), i)) amigos.add(lectores[i]);
                }
            }
        }
        return amigos;
    }

    public List<Lector> getGrupo(Lector lector) {
        List<Lector> grupo = new ArrayList<>();
        if(lector!=null) {
            if(getIndice(lector) != -1) {
                boolean[] visitados = inicializa_visitados();
                red.recorridoEnProfundidad(getIndice(lector), visitados);
                for (int i = 0; i < numLectores; i++) {
                    if (visitados[i]) grupo.add(lectores[i]);
                }
            }
        }
        return grupo;
    }

    public List<Lector> mayorGrupo() {
        List<Lector> mayor = new ArrayList<>();
        boolean[] visitados_global = inicializa_visitados();
        for(int i = 0; i < numLectores; i++){
            if(!visitados_global[i]) {
                List<Lector> aux = this.getGrupo(lectores[i]);
                if (aux.size() > mayor.size()) {
                    mayor = aux;
                }
            }
        }
        return mayor;
    }

    public int contarGrupos() {
        int numGrupos = 0;
        boolean[] visitados = inicializa_visitados();
        for(int i = 0; i < numLectores; i++){
            if(!visitados[i]){
                red.recorridoEnProfundidad(i, visitados);
                numGrupos++;
            }
        }
        return numGrupos;
    }

    public String generoMasFrecuenteGrupo(Lector lector) {
        String resultado = "";
        List<Lector> grupo = this.getGrupo(lector);
        TreeMap<String, Integer> arbolGrupo = null;
        if (!grupo.isEmpty()) {
            arbolGrupo = new TreeMap<>();
            for (int i = 0; i < grupo.size(); i++) {
                String genAux = grupo.get(i).getGeneroLibroFavorito();
                int frec = arbolGrupo.getOrDefault(genAux, 0);
                if (frec == 0) arbolGrupo.put(genAux, 1);
                else arbolGrupo.put(genAux, frec + 1);
            }
            int mayor = 0;
            for (Map.Entry<String, Integer> entry : arbolGrupo.entrySet()) {
                if (entry.getValue() > mayor) {
                    resultado = entry.getKey();
                    mayor = entry.getValue();
                }
            }
        }
        return resultado;
    }

}
