import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;

public class FormLocomotive extends JDialog {
    private DrawningLocomotive Locomotive;
    private DrawningLocomotive SelectedLocomotive;
    public DrawningLocomotive GetSelectedLocomotive() {
        return SelectedLocomotive;
    }
    private JButton ButtonCreate;
    private JButton ButtonUp;
    private JButton ButtonDown;
    private JButton ButtonRight;
    private JButton ButtonLeft;
    private JToolBar StatusStrip;
    public JPanel MainPanel;
    private JPanel PictureBoxLocomotive;
    private JButton ButtonCreateModif;
    private JButton ButtonSelectLocomotive;
    private JLabel JLabelSpeed;
    private JLabel JLabelWeight;
    private JLabel JLabelColor;
    private boolean drawBack;
    public void SetDrawBack(boolean mode){
        drawBack = mode;
    }
    public FormLocomotive(){
        super( new Frame("Локомотив"));
        setModal(true);
        JLabelSpeed = new JLabel();
        JLabelWeight = new JLabel();
        JLabelColor = new JLabel();
        Box LabelBox = Box.createHorizontalBox();
        LabelBox.setMinimumSize(new Dimension(1, 20));
        LabelBox.add(JLabelSpeed);
        LabelBox.add(JLabelWeight);
        LabelBox.add(JLabelColor);
        StatusStrip.add(LabelBox);
        ButtonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color colorFirst = JColorChooser.showDialog(null, "Цвет", null);
                Random random = new Random();
                Locomotive = new DrawningLocomotive(100 + random.nextInt(200), 1000 + random.nextInt(1000), colorFirst);
                SetData();
                Draw();
            }
        });
        ButtonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Locomotive.MoveTransport(Direction.Up);
                Draw();
            }
        });
        ButtonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Locomotive.MoveTransport(Direction.Down);
                Draw();
            }
        });
        ButtonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Locomotive.MoveTransport(Direction.Left);
                Draw();
            }
        });
        ButtonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Locomotive.MoveTransport(Direction.Right);
                Draw();
            }
        });
        PictureBoxLocomotive.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (Locomotive == null) {
                    return;
                }
                Locomotive.ChangeBorders(PictureBoxLocomotive.getWidth(), PictureBoxLocomotive.getHeight());
                Draw();
            }
        });
        ButtonCreateModif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color colorFirst = JColorChooser.showDialog(null, "Цвет", null);
                Color colorSecond = JColorChooser.showDialog(null, "Цвет", null);
                Random random = new Random();
                Locomotive = new DrawningMonorail(100 + random.nextInt(200), 1000 + random.nextInt(1000), colorFirst, colorSecond, random.nextBoolean(), random.nextBoolean());
                SetData();
                Draw();
            }
        });
        ButtonSelectLocomotive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectedLocomotive = Locomotive;
                dispose();
            }
        });
    }
    protected void ChangeLocomotive(DrawningObjectLocomotive locomotive){
        Locomotive = locomotive.GetDrawingObjectLocomotive();
    }
    public void SetData(){
        Random random = new Random();
        Locomotive.SetPosition(10 + random.nextInt(90), 10 + random.nextInt(90), PictureBoxLocomotive.getWidth(), PictureBoxLocomotive.getHeight());
        JLabelSpeed.setText("Cкорость: " + Locomotive.GetLocomotive().GetSpeed() + " ");
        JLabelWeight.setText("Вес: " + Locomotive.GetLocomotive().GetWeight() + " ");
        JLabelColor.setText(("Цвет: " + Locomotive.GetLocomotive().GetBodyColor() + " "));
    }
    public void Draw() {
        if (Locomotive.GetLocomotive() == null) {
            return;
        }
        if (drawBack){
            Locomotive.SetDrawBack(true);
        }
        Locomotive.DrawTransport(PictureBoxLocomotive.getGraphics());
    }
}