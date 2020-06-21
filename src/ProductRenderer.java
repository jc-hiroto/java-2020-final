package src;
import src.hash.searchEngine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

import static src.Processor.textCutter;

/**
 * Define basic UI settings of Java Panel, Java Button, Java List
 */
public class ProductRenderer extends JPanel implements ListCellRenderer<ProductData> {
    private JLabel lbTitle = new JLabel();
    private JLabel lbIcon = new JLabel();
    private JLabel lbProductKey = new JLabel();
    private JLabel lbPrice = new JLabel();
    private JLabel lbAmount = new JLabel();
    private JLabel lbCat = new JLabel();
    private JButton btnMore = new JButton();
    private searchEngine sEngine = new searchEngine();
    private Color CYAN = new Color(21, 188, 163);
    private Color LIGHT_CYAN = new Color(179, 241, 236);
    private Color LIGHT_GRAY = new Color(237, 237, 237);

    /**
     * default constructor of ProductRenderer
     */
    public ProductRenderer(){
        setBackground(LIGHT_CYAN);
        setLayout(new BorderLayout(0,0));
        setBorder(new EmptyBorder(0,0,3,0));
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

    /**
     * override all settings in Java List ProductData
     * @param list
     * @param productData
     * @param index
     * @param isSelected
     * @param cellHasFocus
     * @return Component getListCellRendererComponent
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends ProductData> list, ProductData productData, int index, boolean isSelected, boolean cellHasFocus) {

        //String imgFilepath=System.getProperty("user.dir")+ "/img/trip/"+productData.getCode()+"-1x.png";
        URL url = this.getClass().getResource(
                "/img/trip/"+productData.getCode()+"-1x.png");
        ImageIcon icon = new ImageIcon(url);
        lbIcon.setIcon(icon);
        lbIcon.setVerticalAlignment(JTextField.TOP);
        lbTitle.setFont(new Font(Font.DIALOG,Font.BOLD,20));
        lbTitle.setText(textCutter(productData.getTitle(),620,lbTitle.getFontMetrics(lbTitle.getFont())));
        lbCat.setText("  分類:  "+ sEngine.reverseSearch(productData.getCode()));
        lbCat.setForeground(Color.GRAY);
        lbCat.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        lbProductKey.setForeground(Color.GRAY);
        lbProductKey.setFont(new Font(Font.DIALOG,Font.ITALIC,12));
        lbProductKey.setText("  "+productData.getKey());
        lbPrice.setForeground(Color.red);
        lbPrice.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        lbPrice.setText("$"+productData.getCheapestPrice()+" 起 ");
        lbPrice.setHorizontalAlignment(JTextField.RIGHT);
        lbPrice.setVerticalAlignment(JTextField.TOP);
        lbAmount.setForeground(Color.BLUE);
        lbAmount.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        lbAmount.setText("最低 "+productData.getLowestBound()+" 人出團  ");
        lbAmount.setHorizontalAlignment(JTextField.RIGHT);
        lbAmount.setVerticalAlignment(JTextField.TOP);
        lbTitle.setOpaque(true);
        lbCat.setOpaque(true);
        lbIcon.setOpaque(true);
        lbProductKey.setOpaque(true);
        lbAmount.setOpaque(true);
        lbPrice.setOpaque(true);

        // when select item
        if (isSelected) {
            lbTitle.setBackground(list.getSelectionBackground());
            lbProductKey.setBackground(list.getSelectionBackground());
            lbCat.setBackground(list.getSelectionBackground());
            lbIcon.setBackground(list.getSelectionBackground());
            lbAmount.setBackground(list.getSelectionBackground());
            lbPrice.setBackground(list.getSelectionBackground());
            setBackground(list.getSelectionBackground());
        } else { // when don't select
            lbTitle.setBackground(LIGHT_GRAY);
            lbProductKey.setBackground(LIGHT_GRAY);
            lbCat.setBackground(LIGHT_GRAY);
            lbIcon.setBackground(LIGHT_GRAY);
            lbAmount.setBackground(LIGHT_GRAY);
            lbPrice.setBackground(LIGHT_GRAY);
            setBackground(CYAN);
        }
        return this;
    }
}
