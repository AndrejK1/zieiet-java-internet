package zieit.labs.pr2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.function.BinaryOperator;

public class Pr2Frame extends JFrame {
    private JRadioButton firstTRUE;
    private JRadioButton firstFALSE;
    private JRadioButton secondTRUE;
    private JRadioButton secondFALSE;
    private JButton andButton;
    private JButton orButton;
    private JButton xorButton;
    private JTextField booleanCalcResult;
    private JTextField calcInput1;
    private JTextField calcInput2;
    private JButton plusButton;
    private JButton minusButton;
    private JButton multiButton;
    private JButton divideButton;
    private JButton sinButton;
    private JButton lnButton;
    private JButton sqrtButton;
    private JButton powerButton;
    private JButton exprButton;
    private JLabel calcLabel;
    private JButton expr2Button;
    private final Font defaultFont = new Font("Arial", Font.PLAIN, 18);

    public Pr2Frame() {
        ButtonGroup first = new ButtonGroup();

        firstTRUE.setText("TRUE");
        firstTRUE.setFont(defaultFont);
        firstTRUE.setLocation(30, 10);
        firstTRUE.setSize(100, 30);
        firstTRUE.setSelected(true);

        firstFALSE.setText("FALSE");
        firstFALSE.setFont(defaultFont);
        firstFALSE.setLocation(30, 50);
        firstFALSE.setSize(100, 30);


        first.add(firstTRUE);
        first.add(firstFALSE);

        ButtonGroup second = new ButtonGroup();
        secondTRUE.setText("TRUE");
        secondTRUE.setFont(defaultFont);
        secondTRUE.setLocation(130, 10);
        secondTRUE.setSize(100, 30);
        secondTRUE.setSelected(true);

        secondFALSE.setText("FALSE");
        secondFALSE.setFont(defaultFont);
        secondFALSE.setLocation(130, 50);
        secondFALSE.setSize(100, 30);

        second.add(secondTRUE);
        second.add(secondFALSE);

        setSize(600, 300);
        setResizable(false);
        setBackground(Color.GRAY);

        // boolean logic buttons
        andButton.setFont(defaultFont);
        andButton.setSize(75, 30);
        andButton.setLocation(10, 100);

        orButton.setFont(defaultFont);
        orButton.setSize(75, 30);
        orButton.setLocation(90, 100);

        xorButton.setFont(defaultFont);
        xorButton.setSize(75, 30);
        xorButton.setLocation(170, 100);

        andButton.addMouseListener(createMouseCalcListener(Boolean::logicalAnd));
        orButton.addMouseListener(createMouseCalcListener(Boolean::logicalOr));
        xorButton.addMouseListener(createMouseCalcListener(Boolean::logicalXor));

        booleanCalcResult.setLocation(10, 150);
        booleanCalcResult.setFont(defaultFont);
        booleanCalcResult.setHorizontalAlignment(SwingConstants.CENTER);
        booleanCalcResult.setSize(235, 50);
        booleanCalcResult.setEditable(false);

        // calculator
        calcInput1.setLocation(280, 10);
        calcInput1.setSize(150, 40);
        calcInput1.setHorizontalAlignment(SwingConstants.CENTER);
        calcInput1.setText("0");
        calcInput1.setFont(defaultFont);

        calcInput2.setLocation(440, 10);
        calcInput2.setSize(150, 40);
        calcInput2.setHorizontalAlignment(SwingConstants.CENTER);
        calcInput2.setText("0");
        calcInput2.setFont(defaultFont);

        updateCalcButton(plusButton, "+", Double::sum);
        plusButton.setLocation(280, 60);
        updateCalcButton(minusButton, "-", (a, b) -> a - b);
        minusButton.setLocation(360, 60);
        updateCalcButton(multiButton, "*", (a, b) -> a * b);
        multiButton.setLocation(440, 60);
        updateCalcButton(divideButton, "/", (a, b) -> a / b);
        divideButton.setLocation(520, 60);
        updateCalcButton(sinButton, "sin", (a, b) -> Math.sin(a));
        sinButton.setLocation(280, 100);
        updateCalcButton(lnButton, "ln", (a, b) -> Math.log(a));
        lnButton.setLocation(360, 100);
        updateCalcButton(sqrtButton, "sqrt", (a, b) -> Math.sqrt(a));
        sqrtButton.setLocation(440, 100);
        updateCalcButton(powerButton, "pow", Math::pow);
        powerButton.setLocation(520, 100);

        updateCalcButton(exprButton, "sqrt(sqrt(x)+sqrt(y))", (a, b) -> Math.sqrt(Math.sqrt(a) + Math.sqrt(b)));
        exprButton.setLocation(280, 140);
        exprButton.setSize(310, 30);

        updateCalcButton(expr2Button, "sqrt(x^2+y^2)", (a, b) -> Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
        expr2Button.setLocation(280, 180);
        expr2Button.setSize(310, 30);

        calcLabel.setLocation(280, 220);
        calcLabel.setSize(310, 30);
        calcLabel.setHorizontalAlignment(SwingConstants.CENTER);
        calcLabel.setFont(defaultFont);
        calcLabel.setText("Почніть розрахунок");

        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        contentPane.add(firstTRUE);
        contentPane.add(firstFALSE);
        contentPane.add(secondTRUE);
        contentPane.add(secondFALSE);
        contentPane.add(andButton);
        contentPane.add(orButton);
        contentPane.add(xorButton);
        contentPane.add(booleanCalcResult);
        contentPane.add(plusButton);
        contentPane.add(minusButton);
        contentPane.add(multiButton);
        contentPane.add(divideButton);
        contentPane.add(sinButton);
        contentPane.add(lnButton);
        contentPane.add(sqrtButton);
        contentPane.add(powerButton);
        contentPane.add(exprButton);
        contentPane.add(expr2Button);
        contentPane.add(calcInput1);
        contentPane.add(calcInput2);
        contentPane.add(calcLabel);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setTitle("Кононенко А.В.");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private MouseAdapter createMouseCalcListener(BinaryOperator<Boolean> operator) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                booleanCalcResult.setText(printBoolean(performBooleanLogic(operator)));
            }
        };
    }

    private void updateCalcButton(JButton button, String text, BinaryOperator<Double> operator) {
        button.setText(text);
        button.setSize(70, 30);
        button.setFont(defaultFont);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                calcLabel.setText(String.format("Результат: %.2f", performCalculatorLogic(operator)));
            }
        });
    }

    private boolean performBooleanLogic(BinaryOperator<Boolean> operator) {
        return operator.apply(firstTRUE.isSelected(), secondTRUE.isSelected());
    }

    private double performCalculatorLogic(BinaryOperator<Double> operator) {
        return operator.apply(Double.parseDouble(calcInput1.getText()), Double.parseDouble(calcInput2.getText()));
    }

    private String printBoolean(boolean b) {
        return String.valueOf(b).toUpperCase(Locale.ROOT);
    }

    public static void main(String[] args) {
        Pr2Frame pr2Frame = new Pr2Frame();

        Dimension da = Toolkit.getDefaultToolkit().getScreenSize();
        pr2Frame.setLocation((int) (da.getWidth() - pr2Frame.getWidth()) / 2, (int) (da.getHeight() - pr2Frame.getHeight()) / 2);
        pr2Frame.setVisible(true);
    }

}
