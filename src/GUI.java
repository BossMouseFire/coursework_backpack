import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class GUI extends JFrame {
    private JPanel container;
    private JPanel outputDataContainer;
    private JPanel inputDataContainer;
    private final JButton addObjectToTable;
    private JTextField inputNameObject;
    private JTextField inputWeightObject;
    private JTextField inputCostObject;
    private final JButton resultButton;
    private JTextField inputMaxWeight;
    private JTable tableInputData;
    private JLabel tableInputLabel;
    private ArrayList<Item> items;
    private JPanel buttonsProcessBlock;
    private final JButton nextLevel;
    private final JButton finishAlgorithm;
    private JTable tableProcess;
    private JTable tableSorted;
    private JLabel textInstruction;
    private int typeAction;
    private int costProcess;
    private int weightProcess;
    private int weightBagProcess;
    private ArrayList<Item> itemsProcess;
    private JButton startOfProgram;
    private final JButton deleteItem;
    private JPanel actionItem;
    private JTextField inputDeleteIndex;
    private final JButton backToAddPanel;
    private int deleteIndexItem;
    private int stateStrTableProcess;
    private ArrayList<Item> itemsAdded = new ArrayList<>();
    public GUI() {
        super("Задача о рюкзаке (жадный алгоритм)");
        startGUI();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(container);
        this.pack();
        this.getContentPane().setBackground(Color.decode("#edfeff"));
        typeAction = 0;
        costProcess = 0;
        weightProcess = 0;
        deleteIndexItem = 0;
        stateStrTableProcess = -1;
        itemsProcess = new ArrayList<>();
        nextLevel = new JButton("Следующий шаг");
        finishAlgorithm = new JButton("Завершить");
        addObjectToTable = new JButton("Добавить");
        deleteItem = new JButton("Удалить");
        items = new ArrayList<>();
        resultButton = new JButton("Получить результат");
        backToAddPanel = new JButton("Назад");
        backToAddPanel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        addObjectToTable.addActionListener(e -> {
            setAddObjectToTable();
        });
        deleteItem.addActionListener(e -> {
            setDeleteItem();
        });
        backToAddPanel.addActionListener(e -> {
            setBackToAddPanel();
        });

        startOfProgram.addActionListener(e -> {
            setStartOfProgram();
        });
        resultButton.addActionListener(e -> {
            setResultButton();
        });
        finishAlgorithm.addActionListener(e -> {
            setFinishAlgorithm();
        });

        nextLevel.addActionListener(e -> {
           setNextLevel();
        });
    }

    private void setAddObjectToTable(){
        DefaultTableModel model = (DefaultTableModel) tableInputData.getModel();

        if (!inputCostObject.getText().matches("[-+]?\\d+") || Integer.parseInt(inputCostObject.getText()) <= 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Поле «Цена предмета» должно иметь целое положительное числовое значение",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        } else if (!inputWeightObject.getText().matches("[-+]?\\d+") || Integer.parseInt(inputWeightObject.getText()) <= 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Поле «Вес предмета» должно иметь целое положительное числовое значение",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        } else if (inputNameObject.getText().equals("")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Поле «Название предмета» не должно иметь пустое значение",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        } else {
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
    }
    private void setDeleteItem(){
        DefaultTableModel model = (DefaultTableModel) tableInputData.getModel();
        if(deleteIndexItem == 0){
            actionItem.removeAll();
            inputDeleteIndex = new JTextField("Введите индекс предмета");
            actionItem.add(inputDeleteIndex);
            actionItem.add(deleteItem);
            actionItem.add(backToAddPanel);
            container.repaint();
            container.revalidate();
            deleteIndexItem = 1;
        }
        else{
            if(items.size() == 0){
                JOptionPane.showMessageDialog(
                        null,
                        "Количество предметов в рюкзаке не должно быть равно нулю",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(inputDeleteIndex.getText().equals("")){
                JOptionPane.showMessageDialog(
                        null,
                        "Поле с индексом предмета не должно быть пустым",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(!inputDeleteIndex.getText().matches("[-+]?\\d+")){
                JOptionPane.showMessageDialog(
                        null,
                        "Поле с индексом предмета должно содержать целочисленное значение",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(Integer.parseInt(inputDeleteIndex.getText()) < 0 ||
                    Integer.parseInt(inputDeleteIndex.getText()) >= items.size()){
                JOptionPane.showMessageDialog(
                        null,
                        "Значение индекса должно находится в диапазоне количества предметов",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
            else{
                int indexDeletedItem = Integer.parseInt(inputDeleteIndex.getText());
                int input = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить предмет?", "Выберите действие...",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (input == 0) {
                    items.remove(indexDeletedItem);
                    model.removeRow(indexDeletedItem);
                    actionItem.removeAll();
                    actionItem.add(addObjectToTable);
                    actionItem.add(deleteItem);
                    deleteIndexItem = 0;
                    container.repaint();
                    container.revalidate();
                }
            }
        }
    }
    private void setBackToAddPanel(){
        actionItem.removeAll();
        actionItem.add(addObjectToTable);
        actionItem.add(deleteItem);
        deleteIndexItem = 0;
        container.repaint();
        container.revalidate();
    }
    private void setStartOfProgram(){
        container.removeAll();
        typeAction = 0;
        costProcess = 0;
        weightProcess = 0;
        itemsProcess = new ArrayList<>();
        items = new ArrayList<>();
        itemsAdded = new ArrayList<>();
        mainWindow();
        container.repaint();
        container.revalidate();
    }
    private void setResultButton(){
        if (!inputMaxWeight.getText().matches("[-+]?\\d+")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Поле «Максимальный вес рюкзака» должно иметь целое числовое значение",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        } else if (Integer.parseInt(inputMaxWeight.getText()) <= 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Поле «Максимальный вес рюкзака» не должно быть равно меньше или равен нулю",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        } else if (items.size() == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "Количество предметов в рюкзаке не должно быть равно нулю",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            weightBagProcess = Integer.parseInt(inputMaxWeight.getText());
            itemsProcess = new ArrayList<>(items);
            algorithmBlock();
            createTableInput();
        }
    }
    private void setFinishAlgorithm(){
        ContinuousBackpack continuousBackpack = new ContinuousBackpack(
                Integer.parseInt(inputMaxWeight.getText()),
                items
        );
        ArrayList<Item> itemBefore = continuousBackpack.run();
        finishWindow(itemBefore);

    }
    private void setNextLevel(){
        int weightBag = Integer.parseInt(inputMaxWeight.getText());
        Item deleteItem = null;
        if (typeAction == 0) {
            new ContinuousBackpack(weightBag, itemsProcess).sortItems();
            DefaultTableModel model = (DefaultTableModel) tableSorted.getModel();
            while (tableSorted.getRowCount() != 0) {
                model.removeRow(0);
            }
            for (Item item : itemsProcess) {
                model.addRow(new Object[]{
                        item.getName(),
                        item.getCost(),
                        item.getWeight(),
                        item.getRatioWeightToPrice()}
                );
            }
            typeAction += 1;
        } else {
            if (itemsProcess.get(0).getWeight() <= weightBagProcess) {
                DefaultTableModel model = (DefaultTableModel) tableProcess.getModel();
                StringBuilder addedItems = new StringBuilder();
                costProcess += itemsProcess.get(0).getCost();
                weightProcess += itemsProcess.get(0).getWeight();
                itemsAdded.add(itemsProcess.get(0));

                for (Item item : itemsAdded) {
                    addedItems.append(item.getName()).append(";");
                }

                weightBagProcess -= itemsProcess.get(0).getWeight();
                deleteItem = itemsProcess.get(0);
                itemsProcess.remove(0);
                model.addRow(new Object[]{costProcess, addedItems.toString(), weightProcess + "/" + weightBag});
                typeAction += 1;
                stateStrTableProcess = 1;
            }
            else{
                deleteItem = itemsProcess.get(0);
                itemsProcess.remove(0);
                typeAction += 1;
                stateStrTableProcess = 0;
            }
        }

        if (itemsProcess.size() == 0 || weightProcess == weightBag) {
            buttonsProcessBlock.removeAll();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 1;
            buttonsProcessBlock.add(finishAlgorithm, gbc);
            tableProcess.setDefaultRenderer(
                    Object.class,
                    new TableInfoRenderer()
            );
        }

        String text;

        if (stateStrTableProcess == 0 && (itemsProcess.size() == 0 || weightProcess == weightBag)) {
            text = "<html><body style='width: 300px'>" +
                    "<p>Предмет <b>" + deleteItem.getName() + "</b> не помещается в рюкзак." +
                    "Максимальная стоимость чемподана - " + "<b>" + costProcess + "</b>" + " руб." +
                    "<br/>Нажмите кнопку <b>Завершить</b>, чтобы получить полноценный результат программы.</p>" +
                    "</body></html>";
            textInstruction.setText(text);
        }
        else if (stateStrTableProcess == 1 && (itemsProcess.size() == 0 || weightProcess == weightBag)) {
            text = "<html><body style='width: 300px'>" +
                    "<p>Предмет <b>" + deleteItem.getName() + "</b> добавлен в рюкзак." +
                    "Максимальная стоимость чемподана - " + "<b>" + costProcess + "</b>" + " руб." +
                    "<br/>Нажмите кнопку <b>Завершить</b>, чтобы получить полноценный результат программы.</p>" +
                    "</body></html>";
            textInstruction.setText(text);
        }
        else if (typeAction == 1) {
            text = "<html><body style='width: 300px'>" +
                    "<p>Отлично, данные готовы к обработке!" +
                    "<br/>Во второй таблице имеется три столбца: " +
                    "<br/><b>Цена</b> - показывает текущую стоимость рюкзака" +
                    "<br/><b>Предметы</b> - текущие предметы в рюкзаке" +
                    "<br/><b>Вес</b> - текущую заполненность рюкзака" +
                    "<br/>Нажмите <b>«Следущий шаг»</b> для продолжения алгоритма.</p>" +
                    "</body></html>";
            textInstruction.setText(text);
        } else if (stateStrTableProcess == 0) {
            text = "<html><body style='width: 300px'>" +
                    "<p>Предмет <b>" + deleteItem.getName() +"</b> не помещается в рюкзак."+
                    "<br/>Процесс будет идти, пока вес предмета не превысит свободное место." +
                    "<br/>Нажмите кнопку «Следущий шаг» для продолжения алгоритма " +
                    "или нажмите «Завершить», чтобы получить итог.</p>" +
                    "</body></html>";
            textInstruction.setText(text);
        }
        else{
            text = "<html><body style='width: 300px'>" +
                    "<p>Предмет <b>" + deleteItem.getName() + "</b> добавлен в рюкзак."+
                    "<br/>Процесс будет идти, пока вес предмета не превысит свободное место." +
                    "<br/>Нажмите кнопку «Следущий шаг» для продолжения алгоритма " +
                    "или нажмите «Завершить», чтобы получить итог.</p>" +
                    "</body></html>";
            textInstruction.setText(text);
        }
    }

    private void createTableInput() {
        inputDataContainer.removeAll();
        GridBagConstraints gbc;

        DefaultTableModel model = new DefaultTableModel();
        tableInputData = new JTable(model);
        model.addColumn("Название");
        model.addColumn("Цена (руб.)");
        model.addColumn("Вес (кг.)");

        tableInputData.getTableHeader().setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableInputData.setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableInputData.setDefaultRenderer(Object.class, new TableInfoRenderer());

        for (Item item : items) {
            model.addRow(new Object[]{item.getName(), item.getCost(), item.getWeight()});
        }

        JScrollPane scrollPane = new JScrollPane(tableInputData);

        gbc = new GridBagConstraints();
        JLabel labelTable = new JLabel("Исходные данные");
        labelTable.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        inputDataContainer.add(labelTable, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        inputDataContainer.add(scrollPane, gbc);

        String text = "<html><body style='width: 300px'><p>Алгоритм заполнения рюкзака представлен в виде таблиц." +
                "<br/><b>Первая таблица</b> - это список предметов, сортированных по коэффициенту." +
                "<br/><b>Вторая таблица</b> - это список, добавленных предметов в рюкзак." +
                "<br>Нажмите кнопку <b>«Следующий шаг»</b>, чтобы отсортировать данный и начать алгоритм." +
                "<br/>Максиальный вес сумки - <b>" + inputMaxWeight.getText() + "</b> кг." + "</p></body></html>";
        textInstruction = new JLabel(text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.CENTER;

        textInstruction.setFont(new Font("JetBrains Mono", Font.PLAIN, 15));

        inputDataContainer.add(textInstruction, gbc);

        container.repaint();
        container.revalidate();
    }

    private void algorithmBlock() {
        DefaultTableModel modelSort = new DefaultTableModel();
        DefaultTableModel modelProcess = new DefaultTableModel();
        tableProcess = new JTable(modelProcess);
        tableSorted = new JTable(modelSort);
        outputDataContainer.removeAll();
        outputDataContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        modelSort.addColumn("Название");
        modelSort.addColumn("Цена (руб.)");
        modelSort.addColumn("Вес (кг.)");
        modelSort.addColumn("Коэффицент");

        tableSorted.getTableHeader().setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableSorted.setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableSorted.setDefaultRenderer(Object.class, new TableInfoRenderer());

        for(Item item: items){
            modelSort.addRow(new Object[]{
                    item.getName(),
                    item.getCost(),
                    item.getWeight(),
                    item.getRatioWeightToPrice()}
                    );
        }
        JLabel tableRatioLabel = new JLabel("Таблица коэффициентов");
        tableRatioLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        outputDataContainer.add(tableRatioLabel, gbc);

        JScrollPane scrollSorted = new JScrollPane(tableSorted);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        outputDataContainer.add(scrollSorted, gbc);

        JLabel tableProcessLabel = new JLabel("Таблица содержимого рюкзака");
        tableProcessLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        outputDataContainer.add(tableProcessLabel, gbc);

        modelProcess.addColumn("Цена (руб.)");
        modelProcess.addColumn("Предметы");
        modelProcess.addColumn("Вес (кг.)");
        tableProcess.getColumnModel().getColumn(0).setMaxWidth(100);
        tableProcess.getColumnModel().getColumn(1).setMaxWidth(400);
        tableProcess.getColumnModel().getColumn(2).setMaxWidth(100);

        tableProcess.getTableHeader().setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableProcess.setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableProcess.setDefaultRenderer(Object.class, new TableInfoRenderer());

        JScrollPane scrollProcess = new JScrollPane(tableProcess);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        outputDataContainer.add(scrollProcess, gbc);

        buttonsProcessBlock = new JPanel();
        buttonsProcessBlock.setBackground(Color.decode("#edfeff"));
        nextLevel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        finishAlgorithm.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        buttonsProcessBlock.add(nextLevel);
        buttonsProcessBlock.add(finishAlgorithm);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1;
        outputDataContainer.add(buttonsProcessBlock, gbc);

        JLabel processLabel = new JLabel();
        processLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));

        processLabel.setText("Алгоритм заполнения рюкзака");
        processLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0);
        outputDataContainer.add(processLabel, gbc);

        container.repaint();
        container.revalidate();
    }

    private void mainWindow(){
        DefaultTableModel modelInputData = new DefaultTableModel();
        tableInputData = new JTable(modelInputData);
        modelInputData.addColumn("Название");
        modelInputData.addColumn("Цена (руб.)");
        modelInputData.addColumn("Вес (кг.)");

        tableInputData.getTableHeader().setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        tableInputData.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        tableInputData.setDefaultRenderer(Object.class, new TableInfoRenderer());

        GridBagConstraints gbc;
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

        resultButton.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        weightPanel.add(resultButton, gbc);
        JLabel maxWeightLabel = new JLabel();
        maxWeightLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        maxWeightLabel.setText("Максимальный вес рюкзака (кг.)");
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
        JLabel formObjectLabel = new JLabel();
        formObjectLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        formObjectLabel.setText("Форма предмета");
        formObjectLabel.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        formObject.add(formObjectLabel, gbc);

        actionItem = new JPanel();
        actionItem.setBackground(Color.decode("#edfeff"));

        addObjectToTable.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        deleteItem.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        actionItem.add(addObjectToTable);
        actionItem.add(deleteItem);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formObject.add(actionItem, gbc);

        JLabel nameObjectLabel = new JLabel();
        nameObjectLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
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
        weightObjectLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        weightObjectLabel.setText("Вес предмета (кг.)");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        formObject.add(weightObjectLabel, gbc);
        JLabel costObjectLabel = new JLabel();
        costObjectLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        costObjectLabel.setText("Цена предмета (руб.)");
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
        tableInputLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        tableInputLabel.setText("Таблица всех предметов");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        gbc.insets = new Insets(20, 0, 0, 0);
        outputDataContainer.add(tableInputLabel, gbc);
    }
    private void startWindow(){
        GridBagConstraints gridBagConstraints;
        JLabel nameProgram = new JLabel("Задача о рюкзаке (жадный алгоритм)");
        nameProgram.setFont(new Font("JetBrains Mono", Font.PLAIN, 26));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        container.add(nameProgram, gridBagConstraints);

        String text = "<html><body style='width: 500px'><p>" +
                "Задача о рюкзаке – это одна из самых популярных задач комбинаторной оптимизации." +
                "<br/><b>Условие:</b>" +
                "<br/>Имеется рюкзак с ограниченной вместимостью по массе." +
                "<br/Имеется набор вещей с определенным весом и ценностью." +
                "<br/>Необходимо подобрать такой набор вещей, чтобы он помещался в рюкзаке и имел максимальную ценность."+
                "<br/>Нажмите <b>«Начать»</b>, чтобы запустить программу." +
                "</p></body></html>";
        JLabel description = new JLabel(text);
        description.setFont(new Font("JetBrains Mono", Font.PLAIN, 18));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(0, 0, 30, 0);
        container.add(description, gridBagConstraints);

        startOfProgram = new JButton("Начать");
        startOfProgram.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        container.add(startOfProgram, gridBagConstraints);
    }
    private void finishWindow(ArrayList<Item> itemBefore){
        inputDataContainer.removeAll();
        outputDataContainer.removeAll();
        GridBagConstraints gbc;
        DefaultTableModel model;

        model = new DefaultTableModel();
        tableInputData = new JTable(model);
        model.addColumn("Название");
        model.addColumn("Цена (руб.)");
        model.addColumn("Вес (кг.)");

        tableInputData.getTableHeader().setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableInputData.setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableInputData.setDefaultRenderer(Object.class, new TableInfoRenderer());

        for (Item item : items) {
            model.addRow(new Object[]{item.getName(), item.getCost(), item.getWeight()});
        }

        JScrollPane scrollPaneTableInput = new JScrollPane(tableInputData);

        gbc = new GridBagConstraints();
        JLabel labelInputTable = new JLabel("Исходные данные");
        labelInputTable.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
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

        gbc = new GridBagConstraints();
        JLabel labelOutputTable = new JLabel("Результат работы алгоритма");
        labelOutputTable.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        inputDataContainer.add(labelOutputTable, gbc);
        model = new DefaultTableModel();
        JTable tableOutputData = new JTable(model);
        model.addColumn("Название");
        model.addColumn("Цена (руб.)");
        model.addColumn("Вес (кг.)");

        tableOutputData.getTableHeader().setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableOutputData.setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        tableOutputData.setDefaultRenderer(Object.class, new TableInfoRenderer());

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

        JLabel maxWeightLabel = new JLabel("Исходный вес рюкзака - " + inputMaxWeight.getText() + " кг.");
        maxWeightLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        inputDataContainer.add(maxWeightLabel, gbc);

        gbc.gridy = 3;
        inputDataContainer.add(Box.createVerticalStrut(5), gbc);

        JLabel weightBeforeLabel = new JLabel("Получившийся вес рюкзака - " + maxWeightBefore + " кг.");
        weightBeforeLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        inputDataContainer.add(weightBeforeLabel, gbc);

        gbc.gridy = 5;
        inputDataContainer.add(Box.createVerticalStrut(5), gbc);

        JLabel costBeforeLabel = new JLabel("Получившаяся стоимость рюкзака - " + maxCostBefore + " руб.");
        costBeforeLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        inputDataContainer.add(costBeforeLabel, gbc);

        gbc.gridy = 7;
        inputDataContainer.add(Box.createVerticalStrut(10), gbc);

        JPanel buttonsBlock = new JPanel();
        startOfProgram.setText("Начать заново");
        startOfProgram.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        buttonsBlock.add(startOfProgram);

        buttonsBlock.setBackground(Color.decode("#edfeff"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        inputDataContainer.add(buttonsBlock, gbc);

        container.repaint();
        container.revalidate();
    }
    private void startGUI() {
        container = new JPanel();
        container.setLayout(new GridBagLayout());
        startWindow();
    }

}

class TableInfoRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        c.setBackground(new JLabel().getBackground());

        return c;
    }
}