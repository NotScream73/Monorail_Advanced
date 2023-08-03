import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FormDrawningEntities extends  JFrame{
    JPanel MainPanel;
    private JButton buttonCreate;
    private JPanel pictureBox;
    private JToolBar StatusStrip;
    private JButton buttonNextEntity;
    JLabel JLabelSpeed;
    JLabel JLabelWeight;
    JLabel JLabelColor;
    private EntityLocomotive _locomotive;
    private IWheelsDrawingObject _wheels;
    private DrawningLocomotive _drawningLocomotive;
    private DrawningEntities<EntityLocomotive, IWheelsDrawingObject> _entities;
    public FormDrawningEntities(){
        super("Генерация локомотивов");
        JLabelSpeed = new JLabel();
        JLabelWeight = new JLabel();
        JLabelColor = new JLabel();
        Box LabelBox = Box.createHorizontalBox();
        LabelBox.setMinimumSize(new Dimension(1, 20));
        LabelBox.add(JLabelSpeed);
        LabelBox.add(JLabelWeight);
        LabelBox.add(JLabelColor);
        StatusStrip.add(LabelBox);
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                _entities = new DrawningEntities<>(10, 5);
                for (int i = 0; i < 10; i ++) {
                    if (random.nextBoolean()) {
                        _entities.Insert(new EntityLocomotive(random.nextInt(100), random.nextInt(100),
                                new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))));
                    }
                    else {
                        _entities.Insert(new EntityMonorail(random.nextInt(100), random.nextInt(100),
                                new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)),
                                new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)),
                                random.nextBoolean(), random.nextBoolean()));
                    }
                }

                for (int i = 0; i < 5; i++) {
                    int randomNumber=random.nextInt(3);
                    if (randomNumber==0){
                        _entities.Insert(new DrawningRectWheels());
                    }
                    else if (randomNumber==1){
                        _entities.Insert(new DrawningTriangleWheels());
                    }
                    else{
                        _entities.Insert(new DrawningWheels());
                    }
                }
            }
        });
        buttonNextEntity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _drawningLocomotive = _entities.getEntity();
                SetData();
                Draw();
            }
        });
    }
    public void SetData(){
        Random random = new Random();
        _drawningLocomotive.SetPosition(10 + random.nextInt(90), 10 + random.nextInt(90), pictureBox.getWidth(), pictureBox.getHeight());
        JLabelSpeed.setText("Cкорость: " + _drawningLocomotive.GetLocomotive().GetSpeed() + " ");
        JLabelWeight.setText("Вес: " + _drawningLocomotive.GetLocomotive().GetWeight() + " ");
        JLabelColor.setText(("Цвет: " + _drawningLocomotive.GetLocomotive().GetBodyColor() + " "));
    }
    public void Draw() {
        if (_drawningLocomotive.GetLocomotive() == null) {
            return;
        }
        _drawningLocomotive.SetDrawBack(true);
        _drawningLocomotive.DrawTransport(pictureBox.getGraphics());
    }
}
