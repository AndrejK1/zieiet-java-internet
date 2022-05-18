package zieit.labs.pr3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Pr3_1Frame extends JFrame {
    private final Font defaultFont = new Font("Arial", Font.PLAIN, 18);
    public final List<Class<?>> columnTypes = List.of(String.class, Boolean.class, Integer.class, Double.class);
    public final Object[] columnNames = new Object[]{"Назва", "Б/У", "Кількість", "Ціна"};
    private final String filePath = "/tmp/data/backup.txt";

    private JButton addRow;
    private JButton removeRow;
    private JButton calcSum;

    private JButton clearTable;
    private JButton loadFile;
    private JButton saveToFile;
    private JTable table;

    public Pr3_1Frame() {

        setSize(640, 385);
        setResizable(false);
        setBackground(Color.GRAY);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        styleTable();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBounds(10, 10, 400, 300);

        contentPane.add(scrollPane);

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
        int startX = 430;
        int startY = 10;

        addRow.setFont(defaultFont);
        addRow.setSize(200, 30);
        addRow.setLocation(startX, startY);
        addRow.setText("Додати елемент");
        addRow.addMouseListener(constructMouseClickListener(() -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(model.getRowCount() + 1);
        }));

        removeRow.setFont(defaultFont);
        removeRow.setSize(200, 30);
        removeRow.setLocation(startX, startY + 50);
        removeRow.setText("Видалити елемент");
        removeRow.addMouseListener(constructMouseClickListener(() -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(Math.max(1, model.getRowCount() - 1));
        }));

        calcSum.setFont(defaultFont);
        calcSum.setSize(200, 30);
        calcSum.setLocation(startX, startY + 150);
        calcSum.setText("Розрахувати суму");
        calcSum.addMouseListener(constructMouseClickListener(() -> {
            double sum = IntStream.range(0, table.getModel().getRowCount())
                    .mapToDouble(i -> {
                        Object price = table.getModel().getValueAt(i, 3);
                        Object qty = table.getModel().getValueAt(i, 2);
                        Object oldFlag = table.getModel().getValueAt(i, 1);

                        Double priceValue = Optional.ofNullable(price).map(String::valueOf).map(Double::parseDouble).orElse(0D);
                        Integer qtyValue = Optional.ofNullable(qty).map(String::valueOf).map(Integer::parseInt).orElse(0);
                        Boolean old = Optional.ofNullable(oldFlag).map(String::valueOf).map(Boolean::parseBoolean).orElse(false);

                        double priceMultiplier = old ? 0.7 : 1;

                        return priceValue * qtyValue * priceMultiplier;
                    }).sum();

            JOptionPane.showMessageDialog(null, String.format("ВСЬОГО: %.2f грн.", sum));
        }));

        clearTable.setFont(defaultFont);
        clearTable.setSize(200, 30);
        clearTable.setLocation(10, 320);
        clearTable.setText("Очистити");
        clearTable.addMouseListener(constructMouseClickListener(() -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            model.setRowCount(1);
        }));

        loadFile.setFont(defaultFont);
        loadFile.setSize(200, 30);
        loadFile.setLocation(220, 320);
        loadFile.setText("Загрузити з файла");
        loadFile.addMouseListener(constructMouseClickListener(this::loadFromFile));

        saveToFile.setFont(defaultFont);
        saveToFile.setSize(200, 30);
        saveToFile.setLocation(430, 320);
        saveToFile.setText("Збрегти у файл");
        saveToFile.addMouseListener(constructMouseClickListener(this::writeToFile));

        return List.of(addRow, removeRow, calcSum, clearTable, loadFile, saveToFile);
    }

    private void styleTable() {
        table.setSize(400, 300);

        table.setFont(defaultFont);
        CustomTableModel dataModel = new CustomTableModel(columnNames);
        dataModel.setColumnCount(dataModel.getColumnCount() + 1);
        table.setModel(dataModel);
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

    private void loadFromFile() {
        Path path = Paths.get(filePath);

        List<String> rows;

        try {
            rows = Files.readAllLines(path);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Помилка зчитування даних з файла " + filePath);
            return;
        }

        Object[][] data = new Object[rows.size()][columnTypes.size()];

        try {
            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                String[] rowValues = rows.get(rowIndex).split(",");

                if (rowValues.length != columnTypes.size()) {
                    throw new IllegalArgumentException("Неправильный формат даних");
                }

                for (int columnIndex = 0; columnIndex < rowValues.length; columnIndex++) {
                    data[rowIndex][columnIndex] = extractValue(rowValues[columnIndex], columnTypes.get(columnIndex));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Помилка зчитування даних з файла " + filePath);
            return;
        }

        table.setModel(new CustomTableModel(data, columnNames));

        JOptionPane.showMessageDialog(null, "Файл зчитано " + filePath);
    }

    private <T> T extractValue(Object value, Class<T> clazz) {
        if (clazz == null || value == null || value.toString().equals("")) {
            return null;
        }

        if (clazz.equals(Boolean.class)) {
            return (T) Boolean.valueOf(value.toString());
        } else if (clazz.equals(String.class)) {
            return (T) value.toString();
        } else if (clazz.equals(Integer.class)) {
            return (T) (Integer) Integer.parseInt(value.toString());
        } else if (clazz.equals(Double.class)) {
            return (T) (Double) Double.parseDouble(value.toString());
        }

        throw new IllegalArgumentException("Не можу розпізнати тип");
    }

    private void writeToFile() {
        TableModel model = table.getModel();

        String[][] tableData = new String[model.getRowCount()][model.getColumnCount()];

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                String formattedValue = Optional.ofNullable(model.getValueAt(i, j)).map(String::valueOf).orElse("");
                tableData[i][j] = formattedValue;
            }
        }

        String fileContent = Arrays.stream(tableData)
                .map(columns -> Arrays.stream(columns).reduce((s1, s2) -> String.join(",", s1, s2)).orElse(""))
                .reduce((s1, s2) -> String.join("\n", s1, s2)).orElse("");

        try {
            Path path = Paths.get(filePath);
            path.getParent().toFile().mkdirs();
            path.toFile().createNewFile();
            Files.writeString(path, fileContent);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Помилка запису даних до файла " + filePath);
            return;
        }

        JOptionPane.showMessageDialog(null, "Контент забережено до файла " + filePath);
    }

    public static void main(String[] args) {
        Pr3_1Frame pr2Frame = new Pr3_1Frame();

        Dimension da = Toolkit.getDefaultToolkit().getScreenSize();
        pr2Frame.setLocation((int) (da.getWidth() - pr2Frame.getWidth()) / 2, (int) (da.getHeight() - pr2Frame.getHeight()) / 2);
        pr2Frame.setVisible(true);
    }
    class CustomTableModel extends DefaultTableModel {
        private final Object[] columnNames;

        public CustomTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
            this.columnNames = columnNames;
        }

        public CustomTableModel(Object[] columnNames) {
            super(columnNames, 1);
            this.columnNames = columnNames;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnTypes.get(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column].toString();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }
    }
}
