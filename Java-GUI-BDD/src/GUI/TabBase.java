package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class TabBase extends JPanel {
    private final GridBagConstraints gbc;
    protected ArrayList<JComponent> listComponents;

    public TabBase() {
        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Color.LIGHT_GRAY);

        listComponents = new ArrayList<>();
    }

    /**
     * Virtual method to load data into different panels
     */
    public void Load() {
    }

    /**
     * Virtual method to set position and layout
     */
    protected void SetElements() {
    }

    /**
     * Add every elements of listComponents to the JPanel on a grid
     *
     * @param modulo int Number of columns
     */
    protected void DisplayElements(Integer modulo) {
        for (int i = 0; i < listComponents.size(); i++) {
            gbc.gridx = i % modulo;
            gbc.gridy = i / modulo;
            this.add(listComponents.get(i), gbc);
        }
    }
}
