import javax.swing.*;
import com.toedter.calendar.*;
import src.home;

class gui{
    public static void main(String args[]){
        JFrame frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(home.metalUI); // 使用Metal UI 模式啟動
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame.setSize(500, 500);
        JDateChooser dateChooser = new JDateChooser();
        frame.getContentPane().add(dateChooser);
        frame.setVisible(true);
    }
}
