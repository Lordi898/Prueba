import javax.swing.SwingUtilities;


public class AppPokemon {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazGraficaPokemon juego = new InterfazGraficaPokemon();
            juego.iniciarJuego();
        });
    }
}
