package Gui;

import Airports.Airport;
import DataBase.SaveInfos;
import Logic.Brain;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public class UserGui extends JFrame implements ItemListener {
    static final Brain app_brain = new Brain();
    static final JFrame frame = new JFrame("Menor rota");
    static final String icon_path = "src/images/airports.png";
    static final String background_path = "src/images/background_image.png";
    static JComboBox<String> origin_state_box;
    static JComboBox<String> origin_airport_box;
    static JComboBox<String> goal_state_box;
    static JComboBox<String> goal_airport_box;
    static Vector<String> origin_iatas = new Vector<>();
    static Vector<String> goal_iatas = new Vector<>();
    static JButton OK_button = new JButton("CALCULAR ROTA");
    static int NUM_MAX_PATH = 6;
    static JLabel[] route_text = new JLabel[NUM_MAX_PATH];



    static public void show_gui() {
        origin_state_box = new JComboBox<>(app_brain.get_states_name());
        origin_iatas = app_brain.get_airports_name(origin_state_box.getSelectedItem() + "");
        origin_airport_box = new JComboBox<>(origin_iatas);

        goal_state_box = new JComboBox<>(app_brain.get_states_name());
        goal_iatas = app_brain.get_airports_name(goal_state_box.getSelectedItem() + "");
        goal_airport_box = new JComboBox<>(goal_iatas);
        goal_airport_box.setSelectedItem(goal_iatas.get(1));

        for(int i = 0; i < NUM_MAX_PATH; i++) {
            route_text[i] = new JLabel();
        }

        OK_button.addActionListener(e -> {
            for(int i = 0; i < NUM_MAX_PATH; i++) {
                route_text[i].setText("");
            }
            route_text[0].setText("Rota recomendavel");
            Vector<Airport> paths = app_brain.get_path(origin_airport_box.getSelectedItem()+"", goal_airport_box.getSelectedItem()+"");
            for(int i = 0; i < paths.size(); i++) {
                route_text[i+1].setText(paths.get(i).get_name() + " (" + paths.get(i).get_state_name() + ")");
            }
            SaveInfos.save_infos(paths, NUM_MAX_PATH);
        });

        JPanel panel = new JPanel();
        UserGui gui = new UserGui();
        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(background_path)))));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon(icon_path);
        frame.setIconImage(icon.getImage());
        frame.setLayout(null);

        origin_airport_box.addItemListener(gui);
        origin_state_box.addItemListener(gui);
        goal_airport_box.addItemListener(gui);
        goal_state_box.addItemListener(gui);

        panel.setBounds(15, 40, 400, 420);
        panel.setLayout(new GridLayout(18, 1, 0, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel.setBackground(new Color(220, 230, 220, 240));
        panel.add(new JLabel("Selecione estado de origem"));
        panel.add(origin_state_box);
        panel.add(new JLabel("Selecione aeroporto de origem"));
        panel.add(origin_airport_box);
        panel.add(new JLabel("Selecione estado de destino"));
        panel.add(goal_state_box);
        panel.add(new JLabel("Selecione aeroporto de destino"));
        panel.add(goal_airport_box);
        panel.add(new JLabel(" ")); // space for button
        panel.add(OK_button);
        panel.add(new JLabel(" "));
        for(int i = 0; i < NUM_MAX_PATH; i++) {
            panel.add(route_text[i]);
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == origin_state_box) {
            origin_iatas.clear();
            origin_iatas.addAll(app_brain.get_airports_name(origin_state_box.getSelectedItem() + ""));
            origin_airport_box.setSelectedItem(origin_iatas.get(0));
        }
        if(e.getSource() == goal_state_box) {
            goal_iatas.clear();
            goal_iatas.addAll(app_brain.get_airports_name(goal_state_box.getSelectedItem() + ""));
            goal_airport_box.setSelectedItem(goal_iatas.get(0));
        }
        if(Objects.equals(origin_state_box.getSelectedItem(), goal_state_box.getSelectedItem()) && Objects.equals(origin_airport_box.getSelectedItem(), goal_airport_box.getSelectedItem()))
            OK_button.setEnabled(false);
        else
            OK_button.setEnabled(true);
    }
}
