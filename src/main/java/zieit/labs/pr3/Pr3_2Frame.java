package zieit.labs.pr3;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Pr3_2Frame extends JFrame {
    private final Font defaultFont = new Font("Arial", Font.PLAIN, 18);

    private JRadioButton lineRadioButton;
    private JRadioButton circleRadioButton;
    private JRadioButton qRadioButton;
    private JButton enterButton;
    private JSpinner sizeSelector;
    private JPanel pane;
    private JLabel label;

    private Point firstPoint = null;

    public Pr3_2Frame() {

        setSize(590, 440);
        setResizable(false);
        setBackground(Color.LIGHT_GRAY);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        renderButtons().forEach(contentPane::add);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setTitle("Кононенко А.В.");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private List<Component> renderButtons() {
        ButtonGroup group = new ButtonGroup();
        group.add(lineRadioButton);
        group.add(circleRadioButton);
        group.add(qRadioButton);

        int startX = 10;
        int startY = 10;

        pane.setFont(defaultFont);
        pane.setSize(400, 400);
        pane.setLocation(startX, startY);
        pane.addMouseListener(constructMouseClickListener());
        pane.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));

        enterButton.setFont(defaultFont);
        enterButton.setSize(160, 30);
        enterButton.setLocation(startX + 410, startY);
        enterButton.setText("Застосувати");
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ((Graphics2D)pane.getGraphics())
                        .setStroke(new BasicStroke(Integer.parseInt(sizeSelector.getValue().toString())));
            }
        });

        lineRadioButton.setFont(defaultFont);
        lineRadioButton.setSize(150, 30);
        lineRadioButton.setLocation(startX + 410, startY + 50);
        lineRadioButton.setText("Лінія");
        lineRadioButton.setSelected(true);

        circleRadioButton.setFont(defaultFont);
        circleRadioButton.setSize(150, 30);
        circleRadioButton.setLocation(startX + 410, startY + 90);
        circleRadioButton.setText("Коло");

        qRadioButton.setFont(defaultFont);
        qRadioButton.setSize(150, 30);
        qRadioButton.setLocation(startX + 410, startY + 130);
        qRadioButton.setText("Квадрат");

        label.setFont(defaultFont);
        label.setSize(200, 30);
        label.setLocation(startX + 410, startY + 170);
        label.setText("Ширина фігури");

        sizeSelector.setFont(defaultFont);
        sizeSelector.setSize(60, 30);
        sizeSelector.setLocation(startX + 410, startY + 210);
        sizeSelector.setValue(1);

        return List.of(lineRadioButton, circleRadioButton, qRadioButton, enterButton, sizeSelector, pane, label);
    }

    private void handleClick(MouseEvent mouseEvent) {
        if (firstPoint == null) {
            firstPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    private void handleRelease(MouseEvent mouseEvent) {
        // remember first point
        if (firstPoint == null) {
            return;
        }

        Graphics graphics = pane.getGraphics();
        graphics.setColor(Color.BLACK);
        ((Graphics2D)graphics).setStroke(new BasicStroke(Integer.parseInt(sizeSelector.getValue().toString())));

        if (lineRadioButton.isSelected()) {
            graphics.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(),
                    mouseEvent.getX(), mouseEvent.getY());
        }

        int xStart = mouseEvent.getX() - (int) firstPoint.getX() < 0 ?
                (int) firstPoint.getX() + mouseEvent.getX() - (int) firstPoint.getX()
                : (int) firstPoint.getX();

        int yStart = mouseEvent.getY() - (int) firstPoint.getY() < 0 ?
                (int) firstPoint.getY() + mouseEvent.getY() - (int) firstPoint.getY()
                : (int) firstPoint.getY();

        if (circleRadioButton.isSelected()) {
            graphics.drawOval(xStart, yStart,
                    Math.abs(mouseEvent.getX() - (int) firstPoint.getX()),
                    Math.abs(mouseEvent.getY() - (int) firstPoint.getY())
            );
        } else if (qRadioButton.isSelected()) {
            graphics.drawRect(xStart, yStart,
                    Math.abs(mouseEvent.getX() - (int) firstPoint.getX()),
                    Math.abs(mouseEvent.getY() - (int) firstPoint.getY())
            );
        }

        firstPoint = null;
    }

    private void handleDrag(MouseEvent mouseEvent) {
        // remember first point
        if (firstPoint == null) {
            return;
        }

        Graphics graphics = pane.getGraphics();

        graphics.setColor(Color.BLACK);

        if (lineRadioButton.isSelected()) {
            graphics.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(),
                    mouseEvent.getX(), mouseEvent.getY());
        } else if (circleRadioButton.isSelected()) {
            graphics.drawOval((int) firstPoint.getX(), (int) firstPoint.getY(),
                    mouseEvent.getX() - (int) firstPoint.getX(), mouseEvent.getY() - (int) firstPoint.getY());
        } else if (qRadioButton.isSelected()) {
            graphics.drawRect((int) firstPoint.getX(), (int) firstPoint.getY(),
                    mouseEvent.getX() - (int) firstPoint.getX(), mouseEvent.getY() - (int) firstPoint.getY());
        }
    }

    private MouseAdapter constructMouseClickListener() {
        return new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("clicked in " + e.getX() + " " + e.getY());
                handleClick(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                System.out.println("Dr to " + e.getX() + " " + e.getY());
                handleDrag(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println("RE to " + e.getX() + " " + e.getY());
                handleRelease(e);
            }
        };
    }

    public static void main(String[] args) {
        Pr3_2Frame pr2Frame = new Pr3_2Frame();

        Dimension da = Toolkit.getDefaultToolkit().getScreenSize();
        pr2Frame.setLocation((int) (da.getWidth() - pr2Frame.getWidth()) / 2, (int) (da.getHeight() - pr2Frame.getHeight()) / 2);
        pr2Frame.setVisible(true);
    }

}
