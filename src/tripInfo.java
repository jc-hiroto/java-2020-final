package src;

import javax.swing.*;
import src.ProductData;
import src.ProductCombination;
import src.hash.searchEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static src.Processor.textCutter;

public class tripInfo {
    private JPanel tripInfoPanel;
    private JLabel lbTitle;
    private JLabel lbIcon;
    private JLabel lbKey;
    private JLabel lbCat;
    private JTable table1;
    private JButton 下訂單Button;
    private ProductData PDHold;
    private ArrayList<ProductCombination> PCArrayL;

    public tripInfo(ProductData PD){
        PDHold = PD;
        PCArrayL = PD.getCombination();
        String imgFilepath=System.getProperty("user.dir")+ "/img/trip/"+PD.getCode()+"-1x.png";
        lbIcon.setIcon(new ImageIcon(imgFilepath));
        lbTitle.setText(""+PD.getTitle());
        lbKey.setText("產品編號："+PD.getKey());
        lbCat.setText("分類："+ new searchEngine().reverseSearch(PD.getCode()));
    }
    public JPanel getPanel(){
        return tripInfoPanel;
    }
    public void initTable(){
        PCArrayL = PDHold.getCombination();
        String[][] displayPCArray = new String[PCArrayL.size()][6];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < PCArrayL.size(); i++) {
            ProductCombination PCObj = PCArrayL.get(i);
            String[] row = {sdf.format(PCObj.getStartDate()),sdf.format(PCObj.getEndDate()),""+PCObj.getLowerBound(),""+PCObj.getUpperBound(),""+PCObj.getPrice(),"OK"};
            displayPCArray[i] = row;
        }
        Object[][] data={
                {"A20200401001","馬達加斯加 猴麵包樹 夢幻生態天堂10天", "2020-04-23","2020-05-02","158900","訂單成立"},
                {"A20200401002","【波蘭、波羅的海三小國、俄羅斯】精彩12日","2020-07-16","2020-07-27","79900","訂單成立"},
                {"A20200401003","【國航假期】東歐純情三國+波蘭8日", "2020-04-26","2020-05-03","47300","訂單處理中"},
                {"A20200401004","《日本嚴選》四國鐵道千年物語•夢幻天空之鏡•高知食彩5日", "2020-07-11","2020-07-15","58900","訂單處理中"}};
        String[] columns={"出團日期","返回日期","最低出團人數","最高出團人數","價格", "產品狀態"};
        table1 = new JTable(displayPCArray,columns);
    }

    private void createUIComponents() {
        initTable();
    }
}

