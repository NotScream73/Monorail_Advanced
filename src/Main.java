import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Локомотив");
        frame.setContentPane(new FormMapWithSetLocomotives().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);
        frame.pack();
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }
}