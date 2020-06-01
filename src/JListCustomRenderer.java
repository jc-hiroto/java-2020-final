package src;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import src.ProductData;
import src.ProductCombination;

public class JListCustomRenderer extends JFrame{
    private ArrayList<ProductData> displayData = new ArrayList<ProductData>();
    public JListCustomRenderer(){

        /*
        ProductData prd1 = new ProductData("關西山陰米其林６天～海上沙漠．白鷺姬路城．足立美術館．天橋立","VDR0598182453","401");
        prd1.addCombination(new ProductCombination());
        ProductData prd2 = new ProductData("幸福北歐~極光玻璃屋帝王蟹 10天(縱遊 瑞典、 挪威、 芬蘭、愛沙尼亞)","VDR0000001732","40");
        prd2.addCombination(new ProductCombination());
        ProductData prd3 = new ProductData("馬達加斯加 猴麵包樹 夢幻生態天堂10天","VDR0000007686","100");
        prd3.addCombination(new ProductCombination());
        displayData.add(prd1);
        displayData.add(prd2);
        displayData.add(prd3);
        //add(createPanel());
         */
    }
    public JPanel createPanel(ArrayList<ProductData> PD){
        if(PD != null){
            displayData = PD;
        }
        JPanel frame = new JPanel(new BorderLayout());
        JPanel cardHolder = new JPanel(new CardLayout());
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnBack = new JButton("回列表");
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(21, 188, 163));
        btnBack.setFont(new Font(Font.DIALOG,Font.BOLD,18));
        btnBack.setVisible(false);
        cardHolder.add(panel,"List");
        CardLayout cl = (CardLayout)(cardHolder.getLayout());
        cl.show(cardHolder, "List");
        JList jlist = createList();
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList<String> theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        System.out.println("Double-clicked on: " + o);
                        ProductData target = new ProductData();
                        for(int i=0; i<displayData.size(); i++){
                            if(displayData.get(i).getTitle().equals(o.toString())){
                                target = displayData.get(i);
                                break;
                            }
                        }
                        tripInfo info = new tripInfo(target);
                        JPanel panel2 = info.getPanel();
                        cardHolder.add(panel2,"Info");
                        cl.show(cardHolder,"Info");
                        btnBack.setVisible(true);
                    }
                }
            }
        };
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cl.show(cardHolder,"List");
                btnBack.setVisible(false);
            }
        });
        jlist.addMouseListener(mouseListener);
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setBackground(new Color(21,188,163));
        panel.add(new JScrollPane(jlist),BorderLayout.CENTER);
        frame.add(cardHolder);
        frame.add(btnBack, BorderLayout.SOUTH);
        return frame;
    }
    public JList<ProductData> createList(){
        DefaultListModel<ProductData> model = new DefaultListModel<ProductData>();
        for(ProductData val : displayData)
            model.addElement(val);
        JList<ProductData> list = new JList<ProductData>(model);
        list.setCellRenderer(new ProductRenderer());
        return list;
    }
}
