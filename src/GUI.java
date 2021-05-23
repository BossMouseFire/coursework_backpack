import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.*;

public class GUI extends JFrame {
    private JPanel container;
    private JPanel outputDataContainer;
    private JPanel inputDataContainer;
    private JButton addObjectToTable;
    private JTextField inputNameObject;
    private JTextField inputWeightObject;
    private JTextField inputCostObject;
    private JButton resultButton;
    private JTextField inputMaxWeight;
    private JTable tableInputData;
    private JLabel tableInputLabel;
    private final ArrayList<Item> items;

    private JPanel buttonsProcessBlock;
    private final JButton nextLevel;
    private final JButton finishAlgorithm;
    private JTable tableProcess;
    private int columnTable;
    private JLabel textInstruction;
    private final JButton restart;
    private final JButton aboutProject;
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setSize(750, 400);
        gui.setVisible(true);
    }

    public GUI() {
        super("Задача о рюкзаке (динамическое программирование)");
        $$$setupUI$$$();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(container);
        this.pack();
        this.getContentPane().setBackground(Color.decode("#edfeff"));
        columnTable = 2;
        nextLevel = new JButton("Следующий шаг");
        finishAlgorithm = new JButton("Завершить");
        restart = new JButton("Начать заново");
        aboutProject = new JButton("О проекте");

        items = new ArrayList<>();
        addObjectToTable.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) tableInputData.getModel();

            if(!inputCostObject.getText().matches("[-+]?\\d+")){
                JOptionPane.showMessageDialog(
                        null,
                        "Поле «Цена предмета» должно иметь целое числовое значение",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(!inputWeightObject.getText().matches("[-+]?\\d+")){
                JOptionPane.showMessageDialog(
                        null,
                        "Поле «Вес предмета» должно иметь целое числовое значение",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(inputNameObject.getText().equals("")){
                JOptionPane.showMessageDialog(
                        null,
                        "Поле «Название предмета» не должно иметь пустое значение",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else{
                model.addRow(new Object[]{
                        inputNameObject.getText(),
                        inputCostObject.getText(),
                        inputWeightObject.getText()});
                items.add(
                        new Item(inputNameObject.getText(),
                                Integer.parseInt(inputCostObject.getText()),
                                Integer.parseInt(inputWeightObject.getText()))
                );
            }

        });
        restart.addActionListener(e -> {
            inputDataContainer.removeAll();
            outputDataContainer.removeAll();
            mainWindow();
            container.repaint();
            container.revalidate();
        });
        resultButton.addActionListener(e -> {
            if (!inputMaxWeight.getText().matches("[-+]?\\d+")){
                JOptionPane.showMessageDialog(
                        null,
                        "Поле «Максимальный вес рюкзака» должно иметь целое числовое значение",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(Integer.parseInt(inputMaxWeight.getText()) == 0){
                JOptionPane.showMessageDialog(
                        null,
                        "Поле «Максимальный вес рюкзака» не должно быть равно нулю",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(items.size() == 0){
                JOptionPane.showMessageDialog(
                        null,
                        "Количество предметов в рюкзаке не должно быть равно нулю",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else{
                algorithmBlock();
                createTableInput();
            }
        });
        finishAlgorithm.addActionListener(e -> {
            inputDataContainer.removeAll();
            outputDataContainer.removeAll();

            ContinuousBackpack continuousBackpack = new ContinuousBackpack(
                    Integer.parseInt(inputMaxWeight.getText()),
                    items
            );

            GridBagConstraints gbc;
            DefaultTableModel model;

            model = new DefaultTableModel();
            tableInputData = new JTable(model);
            model.addColumn("Название предмета");
            model.addColumn("Цена товара");
            model.addColumn("Вес");

            for (Item item : items) {
                model.addRow(new Object[]{item.getName(), item.getCost(), item.getWeight()});
            }

            JScrollPane scrollPaneTableInput = new JScrollPane(tableInputData);

            gbc = new GridBagConstraints();
            JLabel labelInputTable = new JLabel("Исходные данные");
            Font labelInputTableFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, labelInputTable.getFont());
            if (labelInputTableFont != null) labelInputTable.setFont(labelInputTableFont);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 0, 10, 0);
            outputDataContainer.add(labelInputTable, gbc);

            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 10, 0);
            outputDataContainer.add(scrollPaneTableInput, gbc);

            ArrayList<Item> itemBefore = continuousBackpack.run();
            gbc = new GridBagConstraints();
            JLabel labelOutputTable = new JLabel("Результат работы алгоритма");
            Font labelOutputTableFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, labelOutputTable.getFont());
            if (labelOutputTableFont != null) labelOutputTable.setFont(labelInputTableFont);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 0, 10, 0);
            inputDataContainer.add(labelOutputTable, gbc);
            model = new DefaultTableModel();
            JTable tableOutputData = new JTable(model);
            model.addColumn("Название предмета");
            model.addColumn("Цена товара");
            model.addColumn("Вес");

            int maxWeightBefore = 0;
            int maxCostBefore = 0;
            for (Item item : itemBefore) {
                model.addRow(new Object[]{item.getName(), item.getCost(), item.getWeight()});
                maxWeightBefore += item.getWeight();
                maxCostBefore += item.getCost();
            }

            JScrollPane scrollPaneTableOutput = new JScrollPane(tableOutputData);

            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.5;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 10, 0);
            inputDataContainer.add(scrollPaneTableOutput, gbc);

            JLabel maxWeightLabel = new JLabel("Исходный вес рюкзака - " + inputMaxWeight.getText() + " ед.");
            Font maxWeightFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, maxWeightLabel.getFont());
            if (maxWeightFont != null) maxWeightLabel.setFont(maxWeightFont);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 1.0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 0, 0, 0);
            gbc.fill = GridBagConstraints.BOTH;
            inputDataContainer.add(maxWeightLabel, gbc);

            gbc.gridy = 3;
            inputDataContainer.add(Box.createVerticalStrut(5), gbc);

            JLabel weightBeforeLabel = new JLabel("Получившийся вес рюкзака - " + maxWeightBefore + " ед.");
            Font weightBeforeLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, weightBeforeLabel.getFont());
            if (weightBeforeLabelFont != null) weightBeforeLabel.setFont(weightBeforeLabelFont);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.weightx = 1.0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            inputDataContainer.add(weightBeforeLabel, gbc);

            gbc.gridy = 5;
            inputDataContainer.add(Box.createVerticalStrut(5), gbc);

            JLabel costBeforeLabel = new JLabel("Получившаяся стоимость рюкзака - " + maxCostBefore + " долл. США.");
            Font costBeforeLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 16, costBeforeLabel.getFont());
            if (costBeforeLabelFont != null) costBeforeLabel.setFont(costBeforeLabelFont);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            inputDataContainer.add(costBeforeLabel, gbc);

            gbc.gridy = 7;
            inputDataContainer.add(Box.createVerticalStrut(10), gbc);

            JPanel buttonsBlock = new JPanel();

            Font restartFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, -1, restart.getFont());
            if (restartFont != null) restart.setFont(restartFont);

            Font aboutProjectFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN,  -1, aboutProject.getFont());
            if (aboutProjectFont != null) aboutProject.setFont(aboutProjectFont);

            buttonsBlock.add(restart);
            buttonsBlock.add(aboutProject);
            buttonsBlock.setBackground(Color.decode("#edfeff"));
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 8;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 0, 0, 0);
            inputDataContainer.add(buttonsBlock, gbc);

            container.repaint();
            container.revalidate();
        });
        nextLevel.addActionListener(e -> {
            int weightBag = Integer.parseInt(inputMaxWeight.getText());
            int maxCostBag = 0;
            DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();

            if (columnTable == (items.size() + 1)) {
                tableCellRenderer.setBackground(Color.green);
            } else {
                tableCellRenderer.setBackground(Color.decode("#d1cfcf"));
            }

            tableProcess.setDefaultRenderer(
                    Object.class,
                    new TableInfoRenderer(
                            Integer.parseInt(inputMaxWeight.getText())
                    )
            );

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
                if (columnTable == (items.size() + 1) && row == weightBag) {
                    maxCostBag = maxNumber;
                }
                tableProcess.getModel().setValueAt(maxNumber, row, columnTable);
            }

            String text;

            if (columnTable == (items.size() + 1)) {
                text = "<html><body style='width: 200px'>" +
                        "<p>Максимальная стоимость рюкзака - " +
                        "<b>" + maxCostBag + "</b>" + " ед." +
                        "<br/>Нажмите кнопку <b>Завершить</b>, чтобы получить полноценный результат программы.</p>" +
                        "</body></html>";
                textInstruction.setText(text);
            }

            else if (columnTable == 2) {
                text = "<html><body style='width: 200px'>" +
                        "<p>Находим максимальную сумму среди двух чисел и записываем результут в ячейку. " +
                        "Так идём до последней ячейки.</p>" +
                        "</body></html>";
                textInstruction.setText(text);
            } else if (columnTable == 3) {
                text = "<html><body style='width: 200px'>" +
                        "<p>Продолжаем наблюдать за алгоритмом до его окончания</p>" +
                        "</body></html>";
                textInstruction.setText(text);
            }


            tableProcess.getColumnModel().getColumn(columnTable++).setCellRenderer(tableCellRenderer);

            if (columnTable == (items.size() + 2)) {
                buttonsProcessBlock.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weightx = 1;
                buttonsProcessBlock.add(finishAlgorithm, gbc);
                tableProcess.setDefaultRenderer(
                        Object.class,
                        new TableInfoRenderer(
                                Integer.parseInt(inputMaxWeight.getText())
                        )
                );
            }
            container.repaint();
            container.revalidate();
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
        gbc.insets = new Insets(10, 0, 10, 0);
        inputDataContainer.add(labelTable, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        inputDataContainer.add(scrollPane, gbc);

        String text = "<html><body style='width: 200px'><p>Алгоритм заполнения рюкзака представлен ввиде таблицы." +
                "<br/>По горизонтали размещён вес предметов." +
                "<br/>По вертикали - вес рюкзака." +
                "<br/>Максиальный вес сумки - <b>" + inputMaxWeight.getText() + "</b> ед." + "</p></body></html>";
        textInstruction = new JLabel(text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.CENTER;

        Font textLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 15, tableInputLabel.getFont());
        textInstruction.setFont(textLabelFont);

        inputDataContainer.add(textInstruction, gbc);

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
        buttonsProcessBlock.setBackground(Color.decode("#edfeff"));
        Font nextLevelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, -1, nextLevel.getFont());
        if (nextLevelFont != null) nextLevel.setFont(nextLevelFont);

        Font finishAlgorithmFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, -1, finishAlgorithm.getFont());
        if (finishAlgorithmFont != null) finishAlgorithm.setFont(finishAlgorithmFont);

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
        gbc.insets = new Insets(10, 0, 10, 0);
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

    private void mainWindow(){
        createUIComponents();
        GridBagConstraints gbc;
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new GridBagLayout());
        weightPanel.setBackground(Color.decode("#edfeff"));
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
        resultButton.setText("Получить результат");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        weightPanel.add(resultButton, gbc);
        JLabel maxWeightLabel = new JLabel();
        Font maxWeightLabelFont = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 14, maxWeightLabel.getFont());
        if (maxWeightLabelFont != null) maxWeightLabel.setFont(maxWeightLabelFont);
        maxWeightLabel.setText("Максимальный вес рюкзака");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        weightPanel.add(maxWeightLabel, gbc);
        inputMaxWeight = new JTextField();
        inputMaxWeight.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        weightPanel.add(inputMaxWeight, gbc);
        JPanel formObject = new JPanel();
        formObject.setLayout(new GridBagLayout());
        formObject.setBackground(Color.decode("#edfeff"));
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
        JLabel nameObjectLabel = new JLabel();
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
        JLabel weightObjectLabel = new JLabel();
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
        JLabel costObjectLabel = new JLabel();
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
    private void $$$setupUI$$$() {
        container = new JPanel();
        GridBagConstraints gbc;
        container.setLayout(new GridBagLayout());

        inputDataContainer = new JPanel();
        inputDataContainer.setBackground(Color.decode("#edfeff"));
        inputDataContainer.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 20);
        container.add(inputDataContainer, gbc);

        outputDataContainer = new JPanel();
        outputDataContainer.setBackground(Color.decode("#edfeff"));
        outputDataContainer.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 20, 10, 10);
        container.add(outputDataContainer, gbc);

        mainWindow();
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

class TableInfoRenderer extends DefaultTableCellRenderer {
    private final int size;
    public TableInfoRenderer(int size){
        this.size = size;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);

        c.setBackground(new JLabel().getBackground());
        return c;
    }
}
