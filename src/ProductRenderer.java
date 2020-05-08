package src;
import java.awt.*;
import java.awt.Font.*;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.Border;

import src.ProductData;
import src.ProductCombination;
import src.hash.searchEngine;
public class ProductRenderer extends JPanel implements ListCellRenderer<ProductData> {
    private JLabel lbTitle = new JLabel();
    private JLabel lbIcon = new JLabel();
    private JLabel lbProductKey = new JLabel();
    private JLabel lbPrice = new JLabel();
    private JLabel lbAmount = new JLabel();
    private JLabel lbCat = new JLabel();
    private searchEngine sEngine = new searchEngine();
    public ProductRenderer(){
        setLayout(new BorderLayout(5,5));
        JPanel panelLeft = new JPanel(new GridLayout(0,1));
        JPanel panelRight = new JPanel(new GridLayout(0,1));
        panelLeft.add(lbTitle);
        panelLeft.add(lbCat);
        panelLeft.add(lbProductKey);
        panelRight.add(lbPrice);
        panelRight.add(lbAmount);
        add(panelRight, BorderLayout.EAST);
        add(panelLeft, BorderLayout.CENTER);
        add(lbIcon, BorderLayout.WEST);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends ProductData> list, ProductData productData, int index, boolean isSelected, boolean cellHasFocus) {
        String imgFilepath=System.getProperty("user.dir")+ "/img/trip/"+productData.getCode()+"-1x.png";
        lbIcon.setIcon(new ImageIcon(imgFilepath));
        lbTitle.setText(productData.getTitle());
        lbTitle.setFont(new Font(Font.DIALOG,Font.BOLD,20));
        lbCat.setText("分類:  "+ sEngine.reverseSearch(productData.getCode()));
        lbCat.setForeground(Color.GRAY);
        lbCat.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        lbProductKey.setForeground(Color.GRAY);
        lbProductKey.setFont(new Font(Font.DIALOG,Font.ITALIC,12));
        lbProductKey.setText(productData.getKey());
        lbPrice.setForeground(Color.red);
        lbPrice.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        lbPrice.setText("$"+productData.getFirstCombination().getPrice()+" 起");
        lbAmount.setForeground(Color.BLUE);
        lbAmount.setFont(new Font(Font.DIALOG,Font.PLAIN,15));
        lbAmount.setText("最低 "+productData.getFirstCombination().getLowerBound()+" 人出團   ");
        return this;
    }
}
