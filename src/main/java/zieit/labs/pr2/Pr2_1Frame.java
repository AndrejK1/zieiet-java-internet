package zieit.labs.pr2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pr2_1Frame extends JFrame {
    private final Font defaultFont = new Font("Arial", Font.PLAIN, 18);
    private JList<Integer> list;
    private JSlider slider;
    private JButton createButton;
    private JButton calcAllButton;
    private JButton calc;
    private JCheckBox enableNegativeCB;
    private JCheckBox maxCB;
    private JCheckBox minCB;
    private JCheckBox diffCB;
    private JCheckBox sumCB;
    private JCheckBox avgCB;
    private JLabel maxCBLabel;
    private JLabel minCBLabel;
    private JLabel diffCBLabel;
    private JLabel sumCBLabel;
    private JLabel avgCBLabel;


    public Pr2_1Frame() {
        list.setFont(defaultFont);
        list.setModel(new DefaultListModel<>());
        list.setSize(100, 400);
        list.setLocation(10, 10);

        slider.setSize(20, 380);
        slider.setLocation(120, 10);

        setSize(650, 420);
        setResizable(false);
        setBackground(Color.GRAY);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(list);
        contentPane.add(slider);
        contentPane.add(createButton);
        renderRightSection().forEach(contentPane::add);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setTitle("Кононенко А.В.");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private List<Component> renderRightSection() {
        int startX = 150;
        int startY = 10;

        createButton.setFont(defaultFont);
        createButton.setSize(120, 30);
        createButton.setLocation(startX, startY);
        createButton.setText("Створити");
        createButton.addMouseListener(constructMouseClickListener(this::regenerateValues));

        calc.setFont(defaultFont);
        calc.setSize(150, 30);
        calc.setLocation(startX + 130, startY);
        calc.setText("Розрахувати");
        calc.addMouseListener(constructMouseClickListener(this::calcAndFillResults));

        calcAllButton.setFont(defaultFont);
        calcAllButton.setSize(200, 30);
        calcAllButton.setLocation(startX + 290, startY);
        calcAllButton.setText("Розрахувати все");
        calcAllButton.addMouseListener(constructMouseClickListener(() -> {
            enableCheckBoxes();
            calcAndFillResults();
        }));

        styleComponent(enableNegativeCB, "Генерація негативних чисел");
        enableNegativeCB.setLocation(startX, startY + 40);
        enableNegativeCB.setSize(300, 30);

        styleComponent(maxCB, "MAX");
        maxCB.setLocation(startX, startY + 80);
        styleLabel(maxCBLabel, "0");
        maxCBLabel.setLocation(startX + 150, startY + 80);

        styleComponent(minCB, "MIN");
        minCB.setLocation(startX, startY + 120);
        styleLabel(minCBLabel, "0");
        minCBLabel.setLocation(startX + 150, startY + 120);

        styleComponent(diffCB, "Розмах");
        diffCB.setLocation(startX, startY + 160);
        styleLabel(diffCBLabel, "0");
        diffCBLabel.setLocation(startX + 150, startY + 160);

        styleComponent(sumCB, "SUM");
        sumCB.setLocation(startX, startY + 200);
        styleLabel(sumCBLabel, "0");
        sumCBLabel.setLocation(startX + 150, startY + 200);

        styleComponent(avgCB, "AVG");
        avgCB.setLocation(startX, startY + 240);
        styleLabel(avgCBLabel, "0");
        avgCBLabel.setLocation(startX + 150, startY + 240);

        return List.of(calcAllButton, calc, calcAllButton, enableNegativeCB,
                maxCB, minCB, diffCB, sumCB, avgCB, maxCBLabel, minCBLabel, diffCBLabel, sumCBLabel, avgCBLabel);
    }

    private void styleComponent(AbstractButton component, String text) {
        component.setSize(150, 30);
        component.setFont(defaultFont);
        component.setText(text);
    }

    private void styleLabel(JLabel component, String text) {
        component.setSize(150, 30);
        component.setFont(defaultFont);
        component.setText(text);
    }

    private MouseAdapter constructMouseClickListener(Runnable action) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                action.run();
            }
        };
    }

    private void regenerateValues() {
        DefaultListModel<Integer> listModel = new DefaultListModel<>();
        listModel.clear();

        for (int i = 0; i < slider.getValue(); i++) {
            int value = (int) (Math.random() * 50);

            // 50% chance to make number negative
            if (enableNegativeCB.isSelected()) {
                value *= Math.signum(Math.random() - 0.5);
            }

            listModel.add(i, value);
        }

        list.setModel(listModel);
    }

    private void calcAndFillResults() {
        // getting all model elements
        List<Integer> allListElements = IntStream.range(0, list.getModel().getSize())
                .mapToObj(list.getModel()::getElementAt)
                .collect(Collectors.toList());

        if (allListElements.isEmpty()) {
            return;
        }

        if (maxCB.isSelected()) {
            maxCBLabel.setText(aggregateList(allListElements, Integer::max).toString());
        } else {
            maxCBLabel.setText("0");
        }

        if (minCB.isSelected()) {
            minCBLabel.setText(aggregateList(allListElements, Integer::min).toString());
        } else {
            minCBLabel.setText("0");
        }

        if (avgCB.isSelected()) {
            avgCBLabel.setText(
                    aggregateList(allListElements, (a, b) -> Math.abs(a) + Math.abs(b))
                            .divide(BigDecimal.valueOf(allListElements.size()), 2, RoundingMode.HALF_UP)
                            .toString()
            );
        } else {
            avgCBLabel.setText("0");
        }

        if (sumCB.isSelected()) {
            sumCBLabel.setText(aggregateList(allListElements, Integer::sum).toPlainString());
        } else {
            sumCBLabel.setText("0");
        }

        if (diffCB.isSelected()) {
            diffCBLabel.setText(calculateStandardDeviation(allListElements).toString());
        } else {
            diffCBLabel.setText("0");
        }
    }

    private BigDecimal aggregateList(List<Integer> values, BinaryOperator<Integer> reducer) {
        return BigDecimal.valueOf(values.stream().reduce(reducer).orElse(0))
                .round(new MathContext(2, RoundingMode.HALF_UP));
    }

    private void enableCheckBoxes() {
        maxCB.setSelected(true);
        minCB.setSelected(true);
        diffCB.setSelected(true);
        sumCB.setSelected(true);
        avgCB.setSelected(true);
    }

    public BigDecimal calculateStandardDeviation(List<Integer> values) {
        double sum = 0.0;
        double standardDeviation = 0.0;
        int length = values.size();

        for (int num : values) {
            sum += num;
        }

        double mean = sum / length;

        for (int num : values) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return BigDecimal.valueOf(Math.sqrt(standardDeviation / length))
                .round(new MathContext(2, RoundingMode.HALF_UP));
    }

    public static void main(String[] args) {
        Pr2_1Frame pr2Frame = new Pr2_1Frame();

        Dimension da = Toolkit.getDefaultToolkit().getScreenSize();
        pr2Frame.setLocation((int) (da.getWidth() - pr2Frame.getWidth()) / 2, (int) (da.getHeight() - pr2Frame.getHeight()) / 2);
        pr2Frame.setVisible(true);
    }

}
