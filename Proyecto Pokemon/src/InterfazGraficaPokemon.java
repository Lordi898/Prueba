import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazGraficaPokemon {
    private Pokemon pokemonJugador;

    // Método para iniciar el juego.
    public void iniciarJuego() {
        mostrarMenuCreacionPokemon();
    }

    // Ventana para crear el Pokémon del jugador.
    private void mostrarMenuCreacionPokemon() {
        JFrame frame = new JFrame("Crea tu Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField();

        JLabel tipoLabel = new JLabel("Tipo:");
        String[] tipos = {"Agua", "Tierra", "Fuego"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);

        JButton crearButton = new JButton("Crear Pokémon");

        crearButton.addActionListener(e -> {
            String nombre = nombreField.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingresa un nombre válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String tipo = tipoBox.getSelectedItem().toString().toLowerCase();
            pokemonJugador = new Pokemon(nombre, tipo);
            frame.dispose();
            mostrarCombate(1);
        });

        frame.add(nombreLabel);
        frame.add(nombreField);
        frame.add(tipoLabel);
        frame.add(tipoBox);
        frame.add(new JLabel()); // Espaciador
        frame.add(crearButton);

        frame.setVisible(true);
    }

    // Ventana para gestionar un combate.
    private void mostrarCombate(int numeroRival) {
        Pokemon pokemonRival = obtenerPokemonRival(numeroRival);
        Combate combate = new Combate(pokemonJugador, pokemonRival);

        JFrame frame = new JFrame("Combate Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel superior: Información de los Pokémon
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JLabel jugadorLabel = new JLabel(describirPokemon(pokemonJugador));
        JLabel rivalLabel = new JLabel(describirPokemon(pokemonRival));
        infoPanel.add(jugadorLabel);
        infoPanel.add(rivalLabel);

        // Panel central: Logs del combate
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Panel inferior: Botón para ejecutar rondas
        JButton rondaButton = new JButton("Siguiente Ronda");
        rondaButton.addActionListener(e -> {
            Pokemon ganadorRonda = combate.Ronda();
            logArea.append("Ronda completada.\n");

            jugadorLabel.setText(describirPokemon(pokemonJugador));
            rivalLabel.setText(describirPokemon(pokemonRival));

            if (combate.Ganador() != null) {
                rondaButton.setEnabled(false);
                Pokemon ganador = combate.Ganador();
                logArea.append("¡Ganador del combate: " + ganador.getNombre() + "!\n");

                if (ganador == pokemonJugador && numeroRival < 3) {
                    pokemonJugador.subirNivel();
                    JOptionPane.showMessageDialog(frame, "¡Ganaste este combate! Próximo rival...");
                    frame.dispose();
                    mostrarCombate(numeroRival + 1);
                } else if (ganador == pokemonJugador) {
                    JOptionPane.showMessageDialog(frame, "¡Eres un Maestro Pokémon!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Has sido derrotado. Fin del juego.");
                    frame.dispose();
                }
            }
        });

        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(rondaButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Devuelve el Pokémon rival basado en el número de combate.
    private Pokemon obtenerPokemonRival(int numero) {
        return switch (numero) {
            case 1 -> new Pokemon("Caterpie", "tierra", 1);
            case 2 -> new Pokemon("Bulbasaur", "agua", 2);
            case 3 -> new Pokemon("Charmander", "fuego", 3);
            default -> null;
        };
    }

    // Método auxiliar para describir un Pokémon.
    private String describirPokemon(Pokemon pokemon) {
        return "<html>" +
                "<strong>Nombre:</strong> " + pokemon.getNombre() + "<br>" +
                "<strong>Tipo:</strong> " + pokemon.getTipo() + "<br>" +
                "<strong>Nivel:</strong> " + pokemon.getNivel() + "<br>" +
                "<strong>Aguante:</strong> " + pokemon.getAguante() +
                "</html>";
    }
}
