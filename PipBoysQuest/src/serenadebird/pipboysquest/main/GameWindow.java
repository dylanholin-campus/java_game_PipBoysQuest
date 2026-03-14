package serenadebird.pipboysquest.main; // Package de la fenetre visuelle du jeu.

import javax.imageio.ImageIO; // Lecture d'image depuis un fichier.
import javax.swing.BorderFactory; // Creation de bordures Swing.
import javax.swing.JFrame; // Fenetre principale Swing.
import javax.swing.JLabel; // Etiquettes d'information.
import javax.swing.JPanel; // Conteneur Swing de base.
import javax.swing.JScrollPane; // Conteneur scrollable.
import javax.swing.JTextPane; // Zone de texte stylée pour espacement linéaire.
import javax.swing.JTextField; // Champ de saisie mono-ligne.
import javax.swing.SwingConstants; // Alignement des labels.
import javax.swing.SwingUtilities; // Outils thread-safe pour Swing.
import javax.swing.WindowConstants; // Constantes de fermeture de fenetre.
import java.awt.Color; // Gestion des couleurs.
import java.awt.Dimension; // Gestion de dimensions (largeur/hauteur).
import java.awt.Font; // Gestion de police.
import java.awt.Graphics; // Contexte graphique de dessin.
import java.awt.Insets; // Marges internes.
import java.awt.Toolkit; // Feedback sonore systeme.
import java.awt.event.FocusAdapter; // Ecoute des evenements de focus.
import java.awt.event.FocusEvent; // Evenement de focus.
import java.awt.event.KeyAdapter; // Ecoute clavier simplifiee.
import java.awt.event.KeyEvent; // Evenement clavier.
import java.awt.image.BufferedImage; // Image en memoire.
import java.io.IOException; // Exception I/O.
import java.io.OutputStream; // Flux de sortie bytes.
import java.io.PipedInputStream; // Flux d'entree connecte a un flux de sortie.
import java.io.PipedOutputStream; // Flux de sortie connecte a un flux d'entree.
import java.io.PrintStream; // Flux texte pour System.out/err.
import java.nio.charset.StandardCharsets; // Charset UTF-8.
import java.nio.file.Files; // Outils systeme de fichiers.
import java.nio.file.Path; // Representation de chemin.
import java.util.ArrayList; // Liste d'historique de commandes.
import java.util.List; // Type d'interface List.
import javax.swing.text.BadLocationException; // Gestion d'insertion dans le doc.
import javax.swing.text.SimpleAttributeSet; // Attributs de style de paragraphe.
import javax.swing.text.StyleConstants; // Regles de format (interligne).
import javax.swing.text.StyledDocument; // Document stylé pour JTextPane.
import javax.swing.text.AttributeSet; // Type generique d'attribut de style.

/**
 * Fenetre native simple (Swing) pour afficher l'image Pip-Boy et les logs du jeu.
 */
public final class GameWindow { // Classe finale: non heritable.
    private static final String INPUT_PLACEHOLDER = "Commande (ex: 1) puis Entree";

    private final JFrame frame; // Fenetre principale.
    private final JTextPane logArea; // Zone d'affichage stylée.
    private final JTextField inputField; // Champ de commande joueur.
    private final JLabel titleLabel; // En-tete de l'ecran texte.
    private final JLabel statusLabel; // Bandeau d'etat contextuel.
    private final PipedOutputStream inputWriter; // Ecriture vers System.in simule.
    private final PrintStream originalOut; // Sauvegarde du System.out original.
    private final PrintStream originalErr; // Sauvegarde du System.err original.
    private final SimpleAttributeSet logAttributes; // Permet d'appliquer un interligne.
    private final SimpleAttributeSet styleDefault; // Style de base.
    private final SimpleAttributeSet stylePv; // PV / vie en rouge.
    private final SimpleAttributeSet styleDamage; // Degats en orange.
    private final SimpleAttributeSet styleSuccess; // Victoire / succes en vert.
    private final SimpleAttributeSet styleInput; // Commandes joueur.
    private final SimpleAttributeSet styleInfo; // Infos generales.
    private final SimpleAttributeSet styleMenu; // Titres/menu en cyan.
    private final SimpleAttributeSet styleLoot; // Lignes de loot en ambre.
    private final SimpleAttributeSet styleError; // Erreurs en rouge vif.

    private final List<String> commandHistory; // Historique des commandes saisies.
    private int historyCursor; // Position courante de navigation dans l'historique.
    private boolean placeholderVisible; // Indique si le placeholder est affiche.

    private GameWindow() throws IOException { // Constructeur prive: creation controlee.
        this.originalOut = System.out; // Memorise sortie standard d'origine.
        this.originalErr = System.err; // Memorise sortie erreur d'origine.
        this.commandHistory = new ArrayList<>();
        this.historyCursor = -1;
        this.placeholderVisible = false;

        PipedInputStream gameInput = new PipedInputStream(); // Cree l'entree reliee.
        this.inputWriter = new PipedOutputStream(gameInput); // Cree la sortie reliee a gameInput.
        System.setIn(gameInput); // Redirige System.in vers ce pipe.

        BufferedImage background = loadBackground(); // Charge l'image de fond.

        this.frame = new JFrame("Pip-Boy's Quest"); // Cree la fenetre titree.
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Ferme l'app a la fermeture.

        BackgroundPanel root = new BackgroundPanel(background); // Panel de fond image.
        root.setLayout(null); // Positionnement absolu des composants.

        this.logArea = new JTextPane(); // Instancie la zone de logs stylée.
        logArea.setEditable(false); // Interdit l'edition directe.
        logArea.setOpaque(true); // Fond non transparent pour eviter l'effet de texte fantome.
        logArea.setBackground(new Color(8, 28, 16)); // Fond uni lisible.
        logArea.setForeground(new Color(184, 255, 173)); // Couleur texte vert Pip-Boy.
        Font logFont = new Font("Monospaced", Font.PLAIN, 15);
        logArea.setFont(logFont); // Police monospace lisible.
        logArea.setMargin(new Insets(6, 6, 6, 6)); // Fallback: certains Look&Feel ignorent partiellement cette marge.
        logArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(85, 138, 92, 180), 1),
                BorderFactory.createEmptyBorder(14, 14, 10, 10)
        ));

        this.logAttributes = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(logAttributes, 0.36f); // Interligne plus agreable.
        StyleConstants.setSpaceAbove(logAttributes, 1.0f); // Leger espace avant chaque paragraphe.
        StyleConstants.setSpaceBelow(logAttributes, 1.0f); // Leger espace apres chaque paragraphe.
        logArea.setParagraphAttributes(logAttributes, false);

        this.styleDefault = new SimpleAttributeSet();
        StyleConstants.setForeground(styleDefault, new Color(184, 255, 173));
        StyleConstants.setBold(styleDefault, true);

        this.stylePv = new SimpleAttributeSet();
        StyleConstants.setForeground(stylePv, new Color(255, 110, 110));
        StyleConstants.setBold(stylePv, true);

        this.styleDamage = new SimpleAttributeSet();
        StyleConstants.setForeground(styleDamage, new Color(255, 180, 90));
        StyleConstants.setBold(styleDamage, true);

        this.styleSuccess = new SimpleAttributeSet();
        StyleConstants.setForeground(styleSuccess, new Color(140, 255, 140));
        StyleConstants.setBold(styleSuccess, true);

        this.styleInput = new SimpleAttributeSet();
        StyleConstants.setForeground(styleInput, new Color(120, 210, 255));
        StyleConstants.setBold(styleInput, true);

        this.styleInfo = new SimpleAttributeSet();
        StyleConstants.setForeground(styleInfo, new Color(210, 255, 180));
        StyleConstants.setBold(styleInfo, true);

        this.styleMenu = new SimpleAttributeSet();
        StyleConstants.setForeground(styleMenu, new Color(130, 235, 255));
        StyleConstants.setBold(styleMenu, true);

        this.styleLoot = new SimpleAttributeSet();
        StyleConstants.setForeground(styleLoot, new Color(255, 190, 95));
        StyleConstants.setBold(styleLoot, true);

        this.styleError = new SimpleAttributeSet();
        StyleConstants.setForeground(styleError, new Color(255, 120, 120));
        StyleConstants.setBold(styleError, true);

        JScrollPane scroll = new JScrollPane(logArea); // Place logArea dans scroll.
        scroll.setBorder(null); // Pas de bordure.
        scroll.setViewportBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4)); // Padding viewport robuste.
        scroll.getViewport().setOpaque(true); // Viewport non transparent pour eviter les artefacts.
        scroll.getViewport().setBackground(new Color(8, 28, 16)); // Meme fond que la zone de logs.
        scroll.setOpaque(false); // Scroll pane transparent.

        this.inputField = new JTextField(); // Instancie champ de saisie.
        inputField.setFont(new Font("Monospaced", Font.BOLD, 16)); // Police plus grande pour saisie.
        inputField.setForeground(new Color(200, 255, 191)); // Couleur texte de saisie.
        inputField.setBackground(new Color(12, 28, 18, 230)); // Fond sombre lisible.
        inputField.setCaretColor(new Color(200, 255, 191)); // Couleur du curseur.
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 165, 112, 210), 1),
                BorderFactory.createEmptyBorder(9, 10, 7, 10)
        ));

        this.titleLabel = new JLabel("PIP-BOY'S QUEST", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        titleLabel.setForeground(new Color(154, 255, 168));

        this.statusLabel = new JLabel("Pret. Choisissez votre action.", SwingConstants.LEFT);
        statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(130, 235, 255));

        JPanel overlay = new JPanel(null); // Panel superpose sur image.
        overlay.setBackground(new Color(8, 28, 16, 220)); // Fond translucide type ecran.
        overlay.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 78, 210), 1));
        overlay.add(titleLabel);
        overlay.add(scroll); // Ajoute zone scrollable.
        overlay.add(statusLabel);
        overlay.add(inputField); // Ajoute champ de saisie.

        root.add(overlay); // Monte l'overlay sur le fond.
        frame.setContentPane(root); // Definit root comme contenu de la fenetre.
        frame.setMinimumSize(new Dimension(900, 550)); // Taille minimale.
        frame.setSize(1200, 760); // Taille initiale.
        frame.setLocationRelativeTo(null); // Centre la fenetre.

        root.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = root.getWidth();
                int h = root.getHeight();

                int ox = (int) (w * 0.23);
                int oy = (int) (h * 0.14);
                int ow = (int) (w * 0.50);
                int oh = (int) (h * 0.78);
                overlay.setBounds(ox, oy, ow, oh);

                int headerTitleY = 10;
                int scrollY = 42;
                int inputHeight = 40;
                int statusHeight = 20;
                int inputY = oh - inputHeight - 12;
                int statusY = inputY - statusHeight - 6;
                int scrollHeight = Math.max(80, statusY - scrollY - 8);

                titleLabel.setBounds(12, headerTitleY, ow - 24, 24);
                scroll.setBounds(10, scrollY, ow - 20, scrollHeight);
                statusLabel.setBounds(12, statusY, ow - 24, statusHeight);
                inputField.setBounds(10, inputY, ow - 20, inputHeight);
            }
        });

        installInputEnhancements();
    }

    public static void startAndHookConsole() {
        try {
            GameWindow window = new GameWindow();
            window.logArea.setFont(new Font("Monospaced", Font.PLAIN, 15)); // format plus compact
            window.inputField.setFont(new Font("Monospaced", Font.BOLD, 15));
            SwingUtilities.invokeLater(() -> {
                window.frame.setVisible(true);
                window.inputField.requestFocusInWindow();
            });
            window.installOutputMirroring();
            window.appendLine("=== PIP-BOY'S QUEST ===");
            window.appendLine("Interface Swing active");
            window.appendLine("tapez 1 pour faire un lancer");
        } catch (Exception error) {
            System.out.println("Interface visuelle indisponible: " + error.getMessage());
        }
    }

    private void installInputEnhancements() {
        showPlaceholder();

        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (placeholderVisible) {
                    hidePlaceholder();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().trim().isEmpty()) {
                    showPlaceholder();
                }
            }
        });

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    navigateHistory(-1);
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    navigateHistory(1);
                    e.consume();
                }
            }
        });

        inputField.addActionListener(e -> {
            String line = getEffectiveInput();
            if (line.isEmpty()) {
                Toolkit.getDefaultToolkit().beep();
                setStatus("Commande vide. Saisissez une action valide.", new Color(255, 190, 120));
                return;
            }

            hidePlaceholder();
            inputField.setText("");
            appendLine("> " + line);
            commandHistory.add(line);
            historyCursor = -1;
            setStatus("Commande envoyee: " + line, new Color(130, 235, 255));

            try {
                inputWriter.write((line + "\n").getBytes(StandardCharsets.UTF_8));
                inputWriter.flush();
            } catch (IOException ioException) {
                appendLine("[ERREUR] Echec d'envoi de la commande: " + ioException.getMessage());
            }
        });
    }

    private String getEffectiveInput() {
        if (placeholderVisible) {
            return "";
        }
        return inputField.getText().trim();
    }

    private void showPlaceholder() {
        placeholderVisible = true;
        inputField.setForeground(new Color(135, 170, 138));
        inputField.setText(INPUT_PLACEHOLDER);
    }

    private void hidePlaceholder() {
        if (!placeholderVisible) {
            return;
        }
        placeholderVisible = false;
        inputField.setText("");
        inputField.setForeground(new Color(200, 255, 191));
    }

    private void navigateHistory(int direction) {
        if (commandHistory.isEmpty()) {
            return;
        }

        hidePlaceholder();

        if (historyCursor == -1) {
            historyCursor = commandHistory.size();
        }

        historyCursor += direction;
        if (historyCursor < 0) {
            historyCursor = 0;
        }

        if (historyCursor >= commandHistory.size()) {
            historyCursor = -1;
            inputField.setText("");
            setStatus("Saisie courante.", new Color(130, 235, 255));
            return;
        }

        String fromHistory = commandHistory.get(historyCursor);
        inputField.setText(fromHistory);
        setStatus("Historique: " + fromHistory, new Color(170, 225, 255));
    }

    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private void installOutputMirroring() { // Redirige sorties vers console + UI.
        OutputStream uiStream = new OutputStream() { // Flux custom pour l'UI.
            private final StringBuilder lineBuffer = new StringBuilder(); // Tampon de ligne.

            @Override
            public void write(int b) { // Ecriture byte par byte.
                char c = (char) b; // Convertit en caractere.
                lineBuffer.append(c); // Ajoute au tampon.
                if (c == '\n') { // Si fin de ligne,
                    flush(); // pousse le tampon.
                }
            }

            @Override
            public void flush() { // Vide le tampon vers UI.
                if (lineBuffer.length() == 0) { // Si rien a envoyer,
                    return; // sort.
                }
                String chunk = lineBuffer.toString(); // Capture le contenu.
                lineBuffer.setLength(0); // Reset tampon.
                appendRaw(chunk); // Ajoute au JTextArea.
            }
        };

        System.setOut(new PrintStream(new TeeOutputStream(originalOut, uiStream), true, StandardCharsets.UTF_8)); // Duplique stdout.
        System.setErr(new PrintStream(new TeeOutputStream(originalErr, uiStream), true, StandardCharsets.UTF_8)); // Duplique stderr.
    }

    private void appendLine(String line) { // Ajout ligne complete.
        appendRaw(line + "\n"); // Ajoute avec saut de ligne.
    }

    private void appendRaw(String chunk) { // Ajout brut dans la zone de log.
        SwingUtilities.invokeLater(() -> { // Execute sur thread Swing.
            StyledDocument doc = logArea.getStyledDocument();
            try {
                String[] lines = chunk.split("\\n", -1);
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    if (!line.isEmpty()) {
                        doc.insertString(doc.getLength(), line, pickStyle(line));
                        updateStatusFromGameLine(line);
                    }
                    if (i < lines.length - 1) {
                        doc.insertString(doc.getLength(), "\n", styleDefault);
                    }
                }
            } catch (BadLocationException ignored) {
                // Ignorer: apparition tres peu probable car on ne modifie pas le doc en parallele.
            }
            logArea.setCaretPosition(logArea.getDocument().getLength()); // Auto-scroll en bas.
        });
    }

    private void updateStatusFromGameLine(String line) {
        String lower = line.toLowerCase();
        if (lower.contains("victoire") || lower.contains("vaincu")) {
            setStatus("Combat termine: victoire.", new Color(140, 255, 140));
        } else if (lower.contains("defaite") || lower.contains("tombe au combat")) {
            setStatus("Combat termine: defaite.", new Color(255, 130, 130));
        } else if (lower.contains("[loot]") || lower.contains("ramasser l'objet")) {
            setStatus("Loot detecte: choisissez quoi faire.", new Color(255, 210, 120));
        } else if (lower.contains("fuite reussie")) {
            setStatus("Retraite validee.", new Color(140, 255, 140));
        } else if (lower.contains("fuite ratee")) {
            setStatus("Fuite ratee: combat en cours.", new Color(255, 180, 90));
        } else if (lower.contains("erreur")) {
            setStatus("Attention: une erreur est survenue.", new Color(255, 130, 130));
        } else if (lower.contains("tour") || lower.contains("choix") || lower.contains("action")) {
            setStatus(line, new Color(170, 225, 255));
        }
    }

    private AttributeSet pickStyle(String line) {
        String lower = line.toLowerCase();
        if (line.startsWith("> ")) {
            return styleInput;
        }
        if (lower.startsWith("choix") || lower.startsWith("nom du personnage")
                || lower.contains("(o/n)") || lower.contains("(1-2)")
                || lower.contains("(1-3)") || lower.contains("(1-5)")) {
            return styleInfo;
        }
        if (lower.contains("[loot]") || lower.contains("vous trouvez") || lower.contains("gain potentiel")
                || lower.contains("offensif actuel") || lower.contains("defensif actuel")
                || lower.contains("ramasser l'objet")) {
            return styleLoot;
        }
        if (line.startsWith("===") || line.startsWith("---") || lower.contains("menu")) {
            return styleMenu;
        }
        if (lower.contains("[erreur]") || lower.contains("exception")) {
            return styleError;
        }
        if (lower.contains("pv") || lower.contains("vie")) {
            return stylePv;
        }
        if (lower.contains("degat") || lower.contains("inflige") || lower.contains("attaque")) {
            return styleDamage;
        }
        if (lower.contains("victoire") || lower.contains("vaincu") || lower.contains("esquive") || lower.contains("fuite reussie")) {
            return styleSuccess;
        }
        if (lower.contains("defaite") || lower.contains("fuite ratee") || lower.contains("dangereuse")) {
            return styleError;
        }
        if (lower.contains("interface") || lower.contains("astuce") || lower.contains("objectif")) {
            return styleInfo;
        }
        return styleDefault;
    }

    private BufferedImage loadBackground() throws IOException { // Charge l'image de fond.
        Path image = resolveImagePath(); // Cherche chemin image.
        if (image == null) { // Si introuvable,
            throw new IOException("image/main_screen_game.jpg introuvable"); // exception explicite.
        }
        return ImageIO.read(image.toFile()); // Lit et retourne l'image.
    }

    private Path resolveImagePath() { // Resout chemin image en remontant dossiers.
        Path current = Path.of("").toAbsolutePath(); // Point de depart: cwd.
        for (int i = 0; i < 6 && current != null; i++) { // Remonte jusqu'a 6 niveaux.
            Path candidate = current.resolve("image").resolve("main_screen_game.jpg"); // Candidat image.
            if (Files.exists(candidate)) { // Si existe,
                return candidate; // retourne ce chemin.
            }
            current = current.getParent(); // Monte d'un niveau.
        }
        return null; // Aucun chemin valide trouve.
    }

    private static final class TeeOutputStream extends OutputStream { // Flux duplique vers deux sorties.
        private final OutputStream one; // Sortie 1 (console originale).
        private final OutputStream two; // Sortie 2 (UI).

        private TeeOutputStream(OutputStream one, OutputStream two) { // Constructeur du flux duplique.
            this.one = one; // Affecte sortie 1.
            this.two = two; // Affecte sortie 2.
        }

        @Override
        public void write(int b) throws IOException { // Ecrit un byte sur les deux sorties.
            one.write(b); // Ecrit sur sortie 1.
            two.write(b); // Ecrit sur sortie 2.
        }

        @Override
        public void flush() throws IOException { // Flush des deux sorties.
            one.flush(); // Flush sortie 1.
            two.flush(); // Flush sortie 2.
        }
    }

    private static final class BackgroundPanel extends JPanel { // Panel qui dessine image de fond.
        private final BufferedImage image; // Image chargee.

        private BackgroundPanel(BufferedImage image) { // Constructeur panel.
            this.image = image; // Affecte image.
        }

        @Override
        protected void paintComponent(Graphics g) { // Dessin personnalise du panel.
            super.paintComponent(g); // Dessin standard d'abord.
            if (image == null) { // Si pas d'image,
                return; // rien a dessiner.
            }
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null); // Dessine image etiree sur panel.
        }
    }
}
