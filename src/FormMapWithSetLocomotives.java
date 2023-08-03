import org.apache.log4j.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class FormMapWithSetLocomotives extends JFrame{
    public JPanel MainPanel;
    private JPanel groupBoxTools;
    private JLabel labelTools;
    private JComboBox comboBoxSelectorMap;
    private JButton buttonAddLocomotive;
    private JButton buttonRemoveLocomotive;
    private JButton buttonShowStorage;
    private JButton buttonShowOnMap;
    private JTextField maskedTextBoxPosition;
    private JPanel pictureBox;
    private JPanel buttonsBox;
    private JButton buttonRight;
    private JButton buttonLeft;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton ButtonDeletedLocomotives;
    private JButton ButtonDeleteMap;
    private JList listBoxMaps;
    private JButton ButtonAddMap;
    private JTextField textFieldNewMapName;
    private JMenu menu;
    private JMenuItem saveToolStripMenuItem;
    private JMenuItem loadToolStripMenuItem;
    private JMenuBar menuBar;
    private JMenuItem saveMapToolStripMenuItem;
    private JMenuItem loadMapToolStripMenuItem;
    private JButton buttonSortByColor;
    private JButton buttonSortByType;

    Logger logger = Logger.getLogger("FormMapWithSetLocomotives");

    private boolean nowMap;
    private final HashMap<String, AbstractMap> _mapsDict = new HashMap<>() {{
        put("Простая карта", new SimpleMap());
        put("Пустыня", new DesertMap());
        put("Газон", new LawnMap());
    }};
    private final MapsCollection _mapsCollection;
    public FormMapWithSetLocomotives()
    {
        pictureBox.setSize(600,500);
        _mapsCollection = new MapsCollection(pictureBox.getWidth(), pictureBox.getHeight());
        comboBoxSelectorMap.removeAllItems();
        for (String elem : _mapsDict.keySet()) {
            comboBoxSelectorMap.addItem(elem);
        }
        buttonAddLocomotive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormLocomotiveConfig form = new FormLocomotiveConfig();
                form.setContentPane(form.MainPanel);
                form.AddEvent(newLocomotive -> {
                    if (listBoxMaps.getSelectedIndex() == -1) {
                        return;
                    }
                    if (newLocomotive != null) {
                        DrawningObjectLocomotive locomotive = new DrawningObjectLocomotive(newLocomotive);
                        locomotive.GetDrawingObjectLocomotive().SetDrawBack(false);
                        try {
                            if (_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).Add(locomotive) != -1) {
                                JOptionPane.showMessageDialog(null, "Объект добавлен");
                                logger.log(Level.INFO,"Добавлен объект: " + locomotive);
                                pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).ShowSet(), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                                nowMap = false;
                            } else {
                                logger.log(Level.INFO,"Не удалось добавить объект: " + locomotive);
                                JOptionPane.showMessageDialog(null, "Не удалось добавить объект");
                            }
                        } catch (StorageOverflowException ex) {
                            logger.log(Level.WARN,"Ошибка переполнения хранилища: " + ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Ошибка переполнения хранилища: "+ex.getMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ex){
                            JOptionPane.showMessageDialog(null, "Неизвестная ошибка: "+ex.getMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
                            logger.log(Level.FATAL,"Неизвестная ошибка: " + ex.getMessage());
                        }
                    }
                });
                form.setSize(1000, 450);
                form.setVisible(true);
            }
        });
        buttonRemoveLocomotive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maskedTextBoxPosition.getText().isEmpty()) {
                    return;
                }
                int result = JOptionPane.showConfirmDialog(null,"Удалить объект??","Удаление",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.NO_OPTION)
                {
                    return;
                }
                int pos = Integer.parseInt(maskedTextBoxPosition.getText());
                try {
                    var tempLocomotive = _mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).Delete(pos);
                    if (tempLocomotive != null)
                    {
                        JOptionPane.showMessageDialog(null, "Объект удален");
                        logger.log(Level.INFO,"Объект удален " + tempLocomotive);
                        pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).ShowSet(), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                        nowMap = false;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Не удалось удалить объект");
                        logger.log(Level.INFO,"Не удалось удалить объект " + tempLocomotive);
                    }
                } catch (LocomotiveNotFoundException ex) {
                    logger.log(Level.WARN,"Ошибка удаления: "+ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Ошибка удаления: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex){
                    logger.log(Level.FATAL,"Неизвестная ошибка: "+ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Неизвестная ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonShowStorage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() == -1) {
                    return;
                }
                pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).ShowSet(), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                nowMap = false;
            }
        });
        buttonShowOnMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() == -1) {
                    return;
                }
                pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).ShowOnMap(), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                nowMap = true;
            }
        });
        buttonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() != -1 && nowMap) {
                    pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).MoveObject(Direction.Up), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                }
            }
        });
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() != -1 && nowMap) {
                    pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).MoveObject(Direction.Left), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                }
            }
        });
        buttonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() != -1 && nowMap) {
                    pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).MoveObject(Direction.Down), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                }
            }
        });
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() != -1 && nowMap) {
                    pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).MoveObject(Direction.Right), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                }
            }
        });
        maskedTextBoxPosition.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) || maskedTextBoxPosition.getText().length() >= 2) {
                    e.consume();
                }
            }
        });
        ButtonAddMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxSelectorMap.getSelectedIndex() == -1 || textFieldNewMapName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Не все данные заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    logger.log(Level.INFO,"Не все данные заполнены");
                    return;
                }
                if (!_mapsDict.containsKey(comboBoxSelectorMap.getSelectedItem())) {
                    JOptionPane.showMessageDialog(null, "Нет такой карты", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    logger.log(Level.WARN,"Нет такой карты");
                    return;
                }
                _mapsCollection.AddMap(textFieldNewMapName.getText(), _mapsDict.get(comboBoxSelectorMap.getSelectedItem().toString()));
                logger.log(Level.INFO,"Добавлена карта: " + textFieldNewMapName.getText());
                ReloadMaps();
            }
        });
        ButtonDeleteMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() == -1) {
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Удалить карту " + listBoxMaps.getSelectedValue().toString() + "?","Удаление", JOptionPane.YES_NO_OPTION) == 0) {
                    _mapsCollection.DelMap(listBoxMaps.getSelectedValue().toString());
                    logger.log(Level.INFO, "Удалена карта: " + listBoxMaps.getSelectedValue().toString());
                    ReloadMaps();
                }
            }
        });
        listBoxMaps.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (listBoxMaps.getSelectedIndex() == -1){
                    return;
                }
                pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).ShowSet(), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                nowMap = false;
                logger.log(Level.INFO, "Переход на карту: "+listBoxMaps.getSelectedValue().toString());
            }
        });
        ButtonDeletedLocomotives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() == -1)
                    return;
                DrawningObjectLocomotive locomotive = (DrawningObjectLocomotive) _mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).GetLocomotiveInDeleted();
                if (locomotive==null) {
                    JOptionPane.showMessageDialog(null, "Очередь пуста", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                FormLocomotive form = new FormLocomotive();
                form.ChangeLocomotive(locomotive);
                form.SetDrawBack(true);
                form.setContentPane(form.MainPanel);
                form.setSize(1000,700);
                form.setVisible(true);
            }
        });
        saveToolStripMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser();
                fs.setDialogTitle("Save a file");
                int result = fs.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try{
                        File selectedFile = fs.getSelectedFile();
                        _mapsCollection.SaveData(selectedFile.getPath());
                        JOptionPane.showMessageDialog(null, "Сохранение прошло успешно", "Результат",JOptionPane.INFORMATION_MESSAGE);
                        logger.log(Level.INFO,"Сохранение прошло успешно");
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Не сохранилось: " + ex.getMessage(), "Результат",JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.FATAL,"Не сохранилось: " + ex.getMessage());
                    }
                }
            }
        });
        loadToolStripMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser();
                fs.setDialogTitle("Load a file");
                int result = fs.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fs.getSelectedFile();
                    try {
                        _mapsCollection.LoadData(selectedFile.getPath());
                        ReloadMaps();
                        JOptionPane.showMessageDialog(null, "Загрузка прошла успешно", "Результат",JOptionPane.INFORMATION_MESSAGE);
                        logger.log(Level.INFO,"Загрузка прошла успешно");
                    } catch (FileNotFoundException | IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Не удалось загрузить: " + ex.getMessage(), "Результат",JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.ERROR,"Не удалось загрузить: " + ex.getMessage());
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Неизвестная ошибка: " + ex.getMessage(), "Результат",JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.FATAL,"Неизвестная ошибка: " + ex.getMessage());
                    }
                }
            }
        });
        saveMapToolStripMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser();
                fs.setDialogTitle("Save a file");
                int result = fs.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try{
                        File selectedFile = fs.getSelectedFile();
                        _mapsCollection.SaveMap(selectedFile.getPath(), listBoxMaps.getSelectedValue().toString());
                        JOptionPane.showMessageDialog(null, "Сохранение карты прошло успешно", "Результат",JOptionPane.INFORMATION_MESSAGE);
                        logger.log(Level.INFO,"Сохранение карты прошло успешно");
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Карта не сохранилась: " + ex.getMessage(), "Результат",JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.FATAL,"Карта не сохранилась: " + ex.getMessage());
                    }
                }
            }
        });
        loadMapToolStripMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser();
                fs.setDialogTitle("Load a file");
                int result = fs.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fs.getSelectedFile();
                    try {
                        _mapsCollection.LoadMap(selectedFile.getPath());
                        ReloadMaps();
                        JOptionPane.showMessageDialog(null, "Загрузка карты прошла успешно", "Результат",JOptionPane.INFORMATION_MESSAGE);
                        logger.log(Level.INFO,"Загрузка карты прошла успешно");
                    } catch (FileNotFoundException | IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Не удалось загрузить карту: " + ex.getMessage(), "Результат",JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.ERROR,"Не удалось загрузить карту: " + ex.getMessage());
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Неизвестная ошибка. Не удалось загрузить карту: " + ex.getMessage(), "Результат",JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.FATAL,"Неизвестная ошибка. Не удалось загрузить карту: " + ex.getMessage());
                    }
                }
            }
        });
        buttonSortByColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() == -1)
                {
                    return;
                }
                _mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).Sort(new LocomotiveCompareByColor());
                pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).ShowSet(), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                nowMap = false;
            }
        });
        buttonSortByType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxMaps.getSelectedIndex() == -1)
                {
                    return;
                }
                _mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).Sort(new LocomotiveCompareByType());
                pictureBox.getGraphics().drawImage(_mapsCollection.Get(listBoxMaps.getSelectedValue().toString()).ShowSet(), 0, 0, pictureBox.getWidth(), pictureBox.getHeight(), null);
                nowMap = false;
            }
        });
    }
    private void ReloadMaps() {
        int index = listBoxMaps.getSelectedIndex();
        DefaultListModel<String> model = (DefaultListModel<String>) listBoxMaps.getModel();
        model.removeAllElements();
        for (int i = 0; i < _mapsCollection.Keys().size(); i++) {
            model.addElement(_mapsCollection.Keys().get(i));
        }
        if (listBoxMaps.getModel().getSize() > 0 &&
                (index == -1 || index >= listBoxMaps.getModel().getSize())) {
            listBoxMaps.setSelectedIndex(0);
        } else if (listBoxMaps.getModel().getSize() > 0 &&
                index > -1 && index < listBoxMaps.getModel().getSize()) {
            listBoxMaps.setSelectedIndex(index);
        }
    }
    private void createUIComponents() {
        DefaultListModel<String> dlm = new DefaultListModel<String>();
        listBoxMaps = new JList(dlm);
    }
}
