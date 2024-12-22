import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        // Показывать 1 знак после запятой для целых чисел
        formatter.setMaximumFractionDigits(1);
        formatter.setMinimumFractionDigits(1);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        if (col == 2 && value instanceof Boolean) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected((Boolean) value);
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            return checkBox;
        }

        String formattedDouble = (value instanceof Number) ? formatter.format(value) : value.toString();
        label.setText(formattedDouble);

        if (col == 1 && needle != null && needle.equals(formattedDouble)) {
            // Если совпадает, установить цвет для ячейки предыдущего столбца (индекс 0)
            if (col > 0) {
                Component prevComponent = table.prepareRenderer(table.getCellRenderer(row, col - 1), row, col - 1);
                if (prevComponent instanceof JLabel) {
                    prevComponent.setBackground(Color.RED);
                    ((JLabel) prevComponent).setOpaque(true);
                }
            }
        }

        // Проверка ячейки текущего столбца
        if (col == 0 && needle != null && needle.equals(table.getValueAt(row, col + 1).toString())) {
            panel.setBackground(Color.RED);
        } else {
            panel.setBackground(Color.WHITE);
        }

        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }
}
