import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TestGUI extends JFrame {
    private final DefaultTableModel model = new DefaultTableModel();
    private final JTable table = new JTable(model);
    private int columnTable = 2;
    public TestGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table.setDefaultRenderer(Object.class, new TableInfoRenderer());
        int amountSub = 5;
        int weight = 40;
        Item[] items = new Item[amountSub];
        items[0] = new Item("тест1", 20, 10);
        items[1] = new Item("тест2", 30, 13);
        items[2] = new Item("тест5", 15, 7);
        items[3] = new Item("тест3", 40, 10);
        items[4] = new Item("тест4", 25, 5);
        JPanel panel = new JPanel();

        model.addColumn("Вес / Вес товара");
        model.addColumn("");
        for(Item item: items){
            model.addColumn(item.getWeight());
        }
        for (int i = 0; i <= weight; i++){
            model.addRow(new Object[]{i});
        }
        for (int row = 0; row <= weight; row++){
            table.getModel().setValueAt(0, row, 1);
        }
        for (int column = 1; column <= amountSub + 1; column++){
            table.getModel().setValueAt(0, 0, column);
        }
        JButton button = new JButton();

        button.addActionListener(e -> {
            for (int row = 1; row <= weight; row++){
                int maxNumber;
                try{
                    maxNumber = Math.max(
                            getCellTable(
                                    row - items[columnTable - 2].getWeight(),
                                    columnTable - 1) +
                                    items[columnTable - 2].getCost(),
                            getCellTable(row, columnTable - 1));
                }
                catch (Exception ex){
                    maxNumber = getCellTable(row, columnTable - 1);
                }
                table.getModel().setValueAt(maxNumber, row, columnTable);
            }
            columnTable++;
        });
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        panel.add(button);
        getContentPane().add(panel);
    }

    protected int getCellTable(int row, int column){
        if(table.getModel().getValueAt(row, column) == null){
            return 0;
        }
        return (int) table.getModel().getValueAt(row, column);
    }


}

class TableInfoRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);

        if(column==0) c.setHorizontalAlignment(CENTER);
        else  c.setHorizontalAlignment(LEFT);

        if(row == 2) c.setBackground(Color.red);
        else c.setBackground(new JLabel().getBackground());
        return c;
    }
}
