import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JPanel panelLogin;
    private JPasswordField passwordField;
    private JTextField mailField;
    private JButton loginButton;

    public Login() {
        add(panelLogin);
        setTitle("Login");
        setSize(300, 200);
        setLocation(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);
    }
}
