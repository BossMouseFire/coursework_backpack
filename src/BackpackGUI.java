import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class BackpackGUI extends JFrame{
    private final DefaultTableModel model = new DefaultTableModel();
    private JTextField input_name = new JTextField("Введите название");
    private JTextField input_cost = new JTextField("Введите стоимость");
    private JTextField input_weight = new JTextField("Введите вес");
    public BackpackGUI(){
        super( "Тест");
        this.setBounds(300, 100, 600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5, 1, 10, 10));
        JTable table = new JTable(model);
        model.addColumn("Название");
        model.addColumn("Цена");
        model.addColumn("Вес");
        container.add(new JScrollPane(table));
        container.add(input_name);
        container.add(input_cost);
        container.add(input_weight);
        JButton button_1 = new JButton("Added");
        button_1.addActionListener(new ButtonEventListener());
        container.add(button_1);
    }
    class ButtonEventListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.addRow(new Object[]{input_name.getText(), input_cost.getText(), input_weight.getText()});
        }
    }
}
