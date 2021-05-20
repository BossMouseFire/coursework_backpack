package version;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class TestingGUI extends JFrame {
    private JPanel container;
    private JPanel outputDataContainer;
    private JPanel inputDataContainer;
    private JPanel weightPanel;
    private JPanel formObject;
    private JButton addObjectToTable;
    private JTextField inputNameObject;
    private JTextField inputWeightObject;
    private JTextField inputCostObject;
    private JLabel nameObjectLabel;
    private JLabel weightObjectLabel;
    private JLabel costObjectLabel;
    private JButton resultButton;
    private JTextField inputMaxWeight;
    private JLabel maxWeightLabel;
    private JTable tableInputData;
    private JLabel tableInputLabel;
    private final ArrayList<Item> items;

    private JPanel buttonsProcessBlock;
    private final JButton nextLevel;
    private final JButton finishAlgorithm;
    private JTable tableProcess;
    private int columnTable;

    public static void main(String[] args) {
        TestingGUI testingGUI = new TestingGUI();
        testingGUI.setVisible(true);
    }

    public TestingGUI() {
        $$$setupUI$$$();
        this.setSize(1000, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(container);
        this.pack();
        columnTable = 2;
        nextLevel = new JButton("Следующий шаг");
        finishAlgorithm = new JButton("Завершить");

        items = new ArrayList<>();
        addObjectToTable.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) tableInputData.getModel();
            model.addRow(new Object[]{inputNameObject.getText(), inputCostObject.getText(), inputWeightObject.getText()});
            items.add(
                    new Item(inputNameObject.getText(),
                            Integer.parseInt(inputCostObject.getText()),
                            Integer.parseInt(inputWeightObject.getText())));

        });
        resultButton.addActionListener(e -> {
            algorithmBlock();
            createTableInput();
        });

        nextLevel.addActionListener(e -> {
            int weightBag = Integer.parseInt(inputMaxWeight.getText());
            for (int row = 1; row <= weightBag; row++) {
                int maxNumber;
                try {
                    maxNumber = Math.max(
                            getCellTable(
                                    row - items.get(columnTable - 2).getWeight(),
                                    columnTable - 1) +
                                    items.get(columnTable - 2).getCost(),
                            getCellTable(row, columnTable - 1));
                } catch (Exception ex) {
                    maxNumber = getCellTable(row, columnTable - 1);
                }
                tableProcess.getModel().setValueAt(maxNumber, row, columnTable);
            }
            columnTable++;
            if (columnTable == (items.size() + 2)) {
                buttonsProcessBlock.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weightx = 1;
                buttonsProcessBlock.add(finishAlgorithm, gbc);

                container.repaint();
                container.revalidate();
            }
        });
    }

    private int getCellTable(int row, int column) {
        if (tableProcess.getModel().getValueAt(row, column) == null) {
            return 0;
        }
        return (int) tableProcess.getModel().getValueAt(row, column);
    }

    private void createTableInput() {
        inputDataContainer.removeAll();
        GridBagConstraints gbc;

        DefaultTableModel model = new DefaultTableModel();
        tableInputData = new JTable(model);
        model.addColumn("Название предмета");
        model.addColumn("Цена товара");
        model.addColumn("Вес");

        for (Item item : items) {
            model.addRow(new Object[]{item.getName(), item.getCost(), item.getWeight()});
        }

        JScrollPane scrollPane = new JScrollPane(tableInputData);

        gbc = new GridBagConstraints();
        JLabel labelTable = new JLabel("Исходные данные");
        Font labelTableFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, labelTable.getFont());
        if (labelTableFont != null) labelTable.setFont(labelTableFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputDataContainer.add(labelTable, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        inputDataContainer.add(scrollPane, gbc);

        String text = "<html><body style='width: 200px'><p>Алгоритм заполнения рюкзака представлен ввиде таблицы." +
                "<br/>По горизонтале размещён вес предметов." +
                "<br/>По вертикале - вес рюкзака." +
                "<br/>Максиальный вес сумки - <b>" + inputMaxWeight.getText() + "</b></p></body></html>";
        JLabel textLabel = new JLabel(text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.CENTER;

        Font textLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 15, tableInputLabel.getFont());
        textLabel.setFont(textLabelFont);

        inputDataContainer.add(textLabel, gbc);

        container.repaint();
        container.revalidate();
    }

    private void algorithmBlock() {
        DefaultTableModel model = new DefaultTableModel();
        tableProcess = new JTable(model);
        outputDataContainer.removeAll();
        outputDataContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        model.addColumn("Вес / Вес товара");
        model.addColumn("");
        int weightBag = Integer.parseInt(inputMaxWeight.getText());

        for (Item item : items) {
            model.addColumn(item.getWeight());
        }
        for (int i = 0; i <= weightBag; i++) {
            model.addRow(new Object[]{i});
        }

        for (int row = 0; row <= weightBag; row++) {
            tableProcess.getModel().setValueAt(0, row, 1);
        }
        for (int column = 1; column <= items.size() + 1; column++) {
            tableProcess.getModel().setValueAt(0, 0, column);
        }
        JScrollPane scrollPane = new JScrollPane(tableProcess);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        outputDataContainer.add(scrollPane, gbc);


        buttonsProcessBlock = new JPanel();
        buttonsProcessBlock.add(nextLevel);
        buttonsProcessBlock.add(finishAlgorithm);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;

        outputDataContainer.add(buttonsProcessBlock, gbc);


        JLabel tableProcessLabel = new JLabel();
        Font tableProcessLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, tableProcessLabel.getFont());
        if (tableProcessLabelFont != null) tableProcessLabel.setFont(tableProcessLabelFont);

        tableProcessLabel.setText("Алгоритм заполнения рюкзака");
        tableProcessLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        outputDataContainer.add(tableProcessLabel, gbc);

        container.repaint();
        container.revalidate();
    }

    private void createUIComponents() {
        DefaultTableModel modelInputData = new DefaultTableModel();
        tableInputData = new JTable(modelInputData);
        modelInputData.addColumn("Название предмета");
        modelInputData.addColumn("Цена товара");
        modelInputData.addColumn("Вес");
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        container = new JPanel();
        container.setLayout(new GridBagLayout());
        inputDataContainer = new JPanel();
        inputDataContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        container.add(inputDataContainer, gbc);
        weightPanel = new JPanel();
        weightPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        inputDataContainer.add(weightPanel, gbc);
        resultButton = new JButton();
        Font resultButtonFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, -1, resultButton.getFont());
        if (resultButtonFont != null) resultButton.setFont(resultButtonFont);
        resultButton.setText("Результат");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        weightPanel.add(resultButton, gbc);
        maxWeightLabel = new JLabel();
        Font maxWeightLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, maxWeightLabel.getFont());
        if (maxWeightLabelFont != null) maxWeightLabel.setFont(maxWeightLabelFont);
        maxWeightLabel.setText("Максимальный вес объекта");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        weightPanel.add(maxWeightLabel, gbc);
        inputMaxWeight = new JTextField();
        inputMaxWeight.setText("вес");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        weightPanel.add(inputMaxWeight, gbc);
        formObject = new JPanel();
        formObject.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        inputDataContainer.add(formObject, gbc);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Форма предмета");
        label1.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        formObject.add(label1, gbc);
        addObjectToTable = new JButton();
        Font addObjectToTableFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, -1, addObjectToTable.getFont());
        if (addObjectToTableFont != null) addObjectToTable.setFont(addObjectToTableFont);
        addObjectToTable.setText("Добавить предмет");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formObject.add(addObjectToTable, gbc);
        nameObjectLabel = new JLabel();
        Font nameObjectLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, nameObjectLabel.getFont());
        if (nameObjectLabelFont != null) nameObjectLabel.setFont(nameObjectLabelFont);
        nameObjectLabel.setText("Название предмета");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        formObject.add(nameObjectLabel, gbc);
        inputNameObject = new JTextField();
        inputNameObject.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formObject.add(inputNameObject, gbc);
        inputWeightObject = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formObject.add(inputWeightObject, gbc);
        inputCostObject = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formObject.add(inputCostObject, gbc);
        weightObjectLabel = new JLabel();
        Font weightObjectLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, weightObjectLabel.getFont());
        if (weightObjectLabelFont != null) weightObjectLabel.setFont(weightObjectLabelFont);
        weightObjectLabel.setText("Вес предмета");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        formObject.add(weightObjectLabel, gbc);
        costObjectLabel = new JLabel();
        Font costObjectLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, costObjectLabel.getFont());
        if (costObjectLabelFont != null) costObjectLabel.setFont(costObjectLabelFont);
        costObjectLabel.setText("Цена предмета");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        formObject.add(costObjectLabel, gbc);
        outputDataContainer = new JPanel();
        outputDataContainer.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        container.add(outputDataContainer, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 0);
        outputDataContainer.add(scrollPane1, gbc);
        scrollPane1.setViewportView(tableInputData);
        tableInputLabel = new JLabel();
        Font tableInputLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, tableInputLabel.getFont());
        if (tableInputLabelFont != null) tableInputLabel.setFont(tableInputLabelFont);
        tableInputLabel.setText("Таблица исходных данных");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        gbc.insets = new Insets(20, 0, 0, 0);
        outputDataContainer.add(tableInputLabel, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return container;
    }

}


class Item{
    private final int cost;
    private final int weight;
    private final String name;
    public Item(String name, int cost, int weight) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "cost=" + cost +
                ", weight=" + weight +
                '}';
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}
