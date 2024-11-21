import java.util.Random;

public class Pokemon {

    private String nombre;
    private String tipo;
    private int nivel;
    private int aguante;

    public Pokemon(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;

        this.nivel = 1;
        this.aguante = calcularAguante(this.nivel);
    }

    public Pokemon(String nombre, String tipo, int nivel) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;

        this.aguante = calcularAguante(nivel);
    }

    private int calcularAguante(int nivel) {
        return nivel * 3;
    }

    public int getAguante() {
        return aguante;
    }

    public void setAguante(int aguante) {
        this.aguante = aguante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
        this.aguante = calcularAguante(nivel);
    }

    public int calcularPoder(Pokemon contrincante){
        // Generar un poder base aleatorio dependiendo del nivel del Pokémon.
        Random random = new Random();
        int poderBase = switch (this.nivel) {
            case 1 -> random.nextInt(8) + 3;  // Rango [3-10]
            case 2 -> random.nextInt(15) + 6; // Rango [6-20]
            case 3 -> random.nextInt(22) + 9; // Rango [9-30]
            case 4 -> random.nextInt(29) + 12; // Rango [12-40]
            default -> 3; // Nivel mínimo.
    };
        // Modificar el poder base según las ventajas/desventajas de tipo.
        int modificador = 0;

        // Ventaja/desventaja según tipo.
        if (this.tipo.equals("agua") && contrincante.tipo.equals("fuego")) {
            modificador = 2 * this.nivel; // Agua vence a fuego.
        } else if (this.tipo.equals("agua") && contrincante.tipo.equals("tierra")) {
            modificador = -2 * contrincante.nivel; // Agua pierde contra tierra.
        } else if (this.tipo.equals("fuego") && contrincante.tipo.equals("tierra")) {
            modificador = 2 * this.nivel; // Fuego vence a tierra.
        } else if (this.tipo.equals("fuego") && contrincante.tipo.equals("agua")) {
            modificador = -2 * contrincante.nivel; // Fuego pierde contra agua.
        } else if (this.tipo.equals("tierra") && contrincante.tipo.equals("agua")) {
            modificador = 2 * this.nivel; // Tierra vence a agua.
        } else if (this.tipo.equals("tierra") && contrincante.tipo.equals("fuego")) {
            modificador = -2 * contrincante.nivel; // Tierra pierde contra fuego.
        }

        // Sumar el modificador al poder base.
        return Math.max(poderBase + modificador, 0); // Asegurarse de que no sea negativo.
    }

    public void subirNivel(){
        this.nivel++;
        this.actualizarStats();
    }

    public void actualizarStats(){
        this.aguante = (int) Math.round(nivel * 2.5);
    }

    public void disminuirAguante(){
        this.aguante--;
    }

    public String toString(){
        return "Nombre: " + this.nombre +
                "\n tipo: " + this.tipo +
                "\n nivel: " + this.nivel +
                "\n aguante " + this.aguante;
    }
}
