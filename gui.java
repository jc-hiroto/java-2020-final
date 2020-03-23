import javax.swing.*;

class gui{
    public static void main(String args[]){
        JFrame frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        JButton button1 = new JButton("Press");
        frame.getContentPane().add(button1);
        frame.setVisible(true);
    }
}
