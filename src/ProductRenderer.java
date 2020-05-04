package src;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import src.ProductData;
import src.ProductCombination;
public class ProductRenderer extends JPanel implements ListCellRenderer<ProductData> {
    private JLabel lbName = new JLabel();
    private JLabel lbProductKey = new JLabel();
    private JLabel lbPrice = new JLabel();
    public ProductRenderer(){
        setLayout(new BorderLayout(5,5));
        JPanel panelText = new JPanel(new GridLayout(0,1));
        panelText.add(lbProductKey);
        panelText.add(lbPrice);
        add(lbName, BorderLayout.WEST);
        add(panelText, BorderLayout.EAST);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends ProductData> list, ProductData productData, int index, boolean isSelected, boolean cellHasFocus) {
        lbName.setText(productData.getTitle());
        lbProductKey.setText(productData.getKey());
        lbPrice.setText(String.valueOf(productData.getFirstCombination().getPrice()));
        return this;
    }
}
