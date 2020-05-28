package GUI;

import Project.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abstract class that is the base of every window with tabs
 */
public abstract class PanelBase extends JPanel implements ActionListener {
    private JFrame _frame;
    protected JTabbedPane tabbedPane;
    private JButton _logoutButton;

    public PanelBase() {
        super(new GridLayout(1, 1));
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
    }

    /**
     * Virtual function to simplify inheritance
     */
    public void Load() {}

    public void LoadTabs() {}

    /**
     * Display the window
     *
     * @param nameWindow String name of the Window
     */
    protected void setFrame(String nameWindow) {
        _frame = new JFrame(nameWindow);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.add(this, BorderLayout.WEST);

        // Display at correct dimension
        _frame.pack();
        _frame.setVisible(true);
        _frame.setResizable(false);
    }

    /**
     * Add a logout button
     */
    protected void addLogOutButtonToTab() {
        _logoutButton = new JButton("Logout");
        _logoutButton.addActionListener(this);
        JPanel tmp = makeTextPanel("Log out page");
        tabbedPane.addTab("", tmp);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, _logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _logoutButton) {
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to log out?","Warning", JOptionPane.YES_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                Main.loadLogin();
            }
        }
    }

    /**
     * Create a panel with only text inside
     * @param text String to insert
     * @return JPanel created
     */
    private JPanel makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }


    /**
     * Close window
     */
    public void dispose() {
        if (_frame != null) {
            _frame.dispose();
        }
    }
}
