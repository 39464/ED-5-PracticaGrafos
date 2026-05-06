package app;

public class Lector {
    private String nombre = "";
    private int id = 0;
    private int edad = 0;
    private String generoLibroFavorito = "";

    public Lector(String nombre, int id, int edad, String genero_libro_favorito) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.generoLibroFavorito = genero_libro_favorito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setGeneroLibroFavorito(String generoLibroFavorito) {
        this.generoLibroFavorito = generoLibroFavorito;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public int getEdad() {
        return edad;
    }

    public String getGeneroLibroFavorito() {
        return generoLibroFavorito;
    }

    @Override
    public String toString() {
        return String.format("Lector %d: { %s , edad: %d, genero: %s}", id, nombre, edad, generoLibroFavorito);
    }

    @Override
    public boolean equals(Object l1) {
        if (this == l1) return true;
        if (l1 == null || getClass() != l1.getClass()) return false;
        Lector lector = (Lector) l1;
        return id == lector.id;
    }

    @Override public int hashCode() { return Integer.hashCode(id); }

}
