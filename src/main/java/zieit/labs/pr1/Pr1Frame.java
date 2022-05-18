package zieit.labs.pr1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pr1Frame extends JFrame {
    private JButton exitButton;
    private JButton info;
    private JTextPane textArea;

    public Pr1Frame() {
        exitButton.setLocation(535, 340);
        exitButton.setSize(60, 30);

        info.setLocation(250, 250);
        info.setSize(100, 30);
        info.setText("Info");

        textArea.setLocation(100, 50);
        textArea.setText("Перша Практична робота!");
        textArea.setSize(400, 100);
        textArea.setFont(new Font("Arial", Font.PLAIN, 28));

        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, "Кононенко А.В.");
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });

        setSize(600, 400);
        setBackground(Color.GRAY);

        contentPane.add(exitButton);
        contentPane.add(textArea);
        contentPane.add(info);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setTitle("Pr 1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Pr1Frame pr1Frame = new Pr1Frame();

        Dimension da = Toolkit.getDefaultToolkit().getScreenSize();
        pr1Frame.setLocation((int) (da.getWidth() - pr1Frame.getWidth()) / 2, (int) (da.getHeight() - pr1Frame.getHeight()) / 2);
        pr1Frame.setVisible(true);
    }

}
