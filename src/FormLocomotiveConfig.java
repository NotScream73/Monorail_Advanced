import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class FormLocomotiveConfig extends JDialog{
    DrawningLocomotive _locomotive;
    Consumer<DrawningLocomotive> EventAddLocomotive;
    public JPanel MainPanel;
    private JButton buttonOk;
    private JButton buttonCancel;
    private JPanel groupBoxConfig;
    private JLabel labelParametr;
    private JLabel labelSpeed;
    private JSpinner spinnerSpeed;
    private JLabel labelWeight;
    private JSpinner spinnerWeight;
    private JCheckBox checkBoxMagneticRail;
    private JCheckBox checkBoxSecondCabin;
    private JPanel groupBoxColors;
    private JPanel panelRed;
    private JPanel panelGreen;
    private JPanel panelBlue;
    private JPanel panelYellow;
    private JPanel panelWhite;
    private JPanel panelGray;
    private JPanel panelBlack;
    private JPanel panelPurple;
    private JLabel labelColor;
    private JLabel labelBaseColor;
    private JLabel labelDopColor;
    private JLabel labelSimpleObject;
    private JLabel labelModifiedObject;
    private JPanel pictureBox;
    private JLabel labelSetWheels;
    private JSpinner spinnerWheels;
    private JLabel labelWheels;
    private JLabel labelTriangleWheels;
    private JLabel labelRectWheels;
    private JLabel labelStandartWheels;
    Object dragObject;
    Object enterObject;
    public FormLocomotiveConfig(){
        super( new Frame("Локомотив"));
        setModal(true);
        labelSetWheels.setText("Колёса");
        //границы
        labelSimpleObject.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelModifiedObject.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelBaseColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelDopColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelStandartWheels.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelTriangleWheels.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelRectWheels.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labelSetWheels.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pictureBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //ограничения
        spinnerSpeed.setModel(new SpinnerNumberModel(100, 100, 1000, 1));
        spinnerWeight.setModel(new SpinnerNumberModel(100, 100, 1000, 1));
        spinnerWheels.setModel(new SpinnerNumberModel(2, 2, 4, 1));

        MouseAdapter drag = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                Drop((JComponent) e.getSource());
            }
        };

        MouseAdapter defCursor = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        };

        pictureBox.addMouseListener(defCursor);
        labelBaseColor.addMouseListener(defCursor);
        labelDopColor.addMouseListener(defCursor);
        labelSetWheels.addMouseListener(defCursor);

        panelRed.addMouseListener(drag);
        panelGreen.addMouseListener(drag);
        panelBlue.addMouseListener(drag);
        panelYellow.addMouseListener(drag);
        panelWhite.addMouseListener(drag);
        panelGray.addMouseListener(drag);
        panelBlack.addMouseListener(drag);
        panelPurple.addMouseListener(drag);

        labelSimpleObject.addMouseListener(drag);
        labelModifiedObject.addMouseListener(drag);
        labelStandartWheels.addMouseListener(drag);
        labelTriangleWheels.addMouseListener(drag);
        labelRectWheels.addMouseListener(drag);
        AdditWheelsDropObject(labelStandartWheels);
        AdditWheelsDropObject(labelRectWheels);
        AdditWheelsDropObject(labelTriangleWheels);

        AdditWheelsDropTarget(labelSetWheels);
        buttonOk.addActionListener(e -> {
            EventAddLocomotive.accept(_locomotive);
            dispose();
        });

        buttonCancel.addActionListener(e -> dispose());
    }
    public void AddEvent(Consumer<DrawningLocomotive> ev) {
        EventAddLocomotive = ev;
    }
    public void Drop(JComponent droppedItem) {
        if (droppedItem == null) {
            return;
        }
        if (droppedItem instanceof JPanel panel) {
            if (_locomotive == null)
                return;
            if (labelBaseColor.getMousePosition() != null) {
                _locomotive.ChangeColor(panel.getBackground());
            }
            if (labelDopColor.getMousePosition() != null && _locomotive instanceof DrawningMonorail monorail) {
                monorail.ChangeDopColor(panel.getBackground());
            }
            Draw();
        }
        if (droppedItem instanceof JLabel label && pictureBox.getMousePosition() != null) {
            int speed = (int)spinnerSpeed.getValue();
            int weight = (int)spinnerWeight.getValue();
            boolean magneticRail = checkBoxMagneticRail.isSelected();
            boolean secondCabin = checkBoxSecondCabin.isSelected();

            if (label == labelSimpleObject) {
                _locomotive = new DrawningLocomotive(speed, weight, Color.WHITE);
                _locomotive.SetWheels((int)spinnerWheels.getValue(), new DrawningTriangleWheels());
            } else if (label == labelModifiedObject) {
                _locomotive = new DrawningMonorail(speed, weight, Color.WHITE, Color.WHITE, magneticRail, secondCabin);
                _locomotive.SetWheels((int)spinnerWheels.getValue(), new DrawningTriangleWheels());
            }

            if (_locomotive!=null) {
                _locomotive.SetPosition(pictureBox.getWidth() - 200, pictureBox.getHeight() - 150, pictureBox.getWidth(), pictureBox.getHeight());
            }
            Draw();

        }
    }
    void AdditWheelsDropTarget(JComponent obj)
    {
        obj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {super.mouseEntered(e);
                Paddle_DragEnter(obj);
            }
            @Override
            public void mouseExited(MouseEvent e) {super.mouseExited(e);
                DragExit();
            }
        });
    }
    void AdditWheelsDropObject(JComponent obj)
    {
        obj.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {super.mousePressed(e);
                Paddle_MouseDown(obj);
            }
            @Override
            public void mouseReleased(MouseEvent e) {super.mouseReleased(e);
                Paddle_DragDrop();
            }
        });
    }

    void Paddle_MouseDown(Object sender)
    {
        IWheelsDrawingObject wheels;
        switch (((JLabel)sender).getText()){
            case "Обычные":
                wheels = new DrawningWheels();
                break;
            case "Треугольные":
                wheels = new DrawningTriangleWheels();
                break;
            case "Квадратные":
                wheels = new DrawningRectWheels();
                break;
            default:
                return;
        }
        enterObject = wheels;
    }

    void Paddle_DragEnter(Object sender)
    {
        if(enterObject != null && IWheelsDrawingObject.class.isAssignableFrom(enterObject.getClass()) && _locomotive != null)
        {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            dragObject = sender;
        }
    }

    void Paddle_DragDrop()
    {
        if(dragObject == null)
        {
            return;
        }
        _locomotive.SetWheels((int)spinnerWheels.getValue(), (IWheelsDrawingObject) enterObject);
        DragExit();
        enterObject = null;
    }

    void DragExit()
    {
        if(enterObject != null && dragObject != null)
        {
            setCursor(Cursor.getDefaultCursor());
            Draw();
            dragObject = null;
        }
    }
    public void Draw() {
        if (_locomotive.GetLocomotive() == null) {
            return;
        }
        _locomotive.SetDrawBack(true);
        _locomotive.DrawTransport(pictureBox.getGraphics());
    }
}
