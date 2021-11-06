package ui.gui;

import ui.TopNewsApp;
import ui.WeatherApp;
import ui.gui.WeatherComboBox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Represents the news headlines page on the gui
public class News extends JPanel
        implements ActionListener {
    JTextArea pictureText;
    JLabel picture;
    TopNewsApp news;

    public News() {
        super(new BorderLayout());
        try {
            news = new TopNewsApp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] petStrings = { "Weather Description", "Weather Data", "Location", "Combined Weather Data"};

        JComboBox petList = new JComboBox(petStrings);
        petList.setSelectedIndex(0);
        petList.addActionListener(this);

        pictureText = new JTextArea();
        picture = new JLabel();
        pictureText.setFont(pictureText.getFont().deriveFont(Font.ITALIC));

        updateLabel(petStrings[petList.getSelectedIndex()]);
        pictureText.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        picture.setPreferredSize(new Dimension(177, 122 + 10));

        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        updateLabel(petStrings[petList.getSelectedIndex()]);
        picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        picture.setPreferredSize(new Dimension(177, 122 + 10));

        add(pictureText, BorderLayout.PAGE_START);
        setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
    }

   // EFFECTS: updates label
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        updateLabel(petName);
    }

    // MODIFIES: this
    // EFFECTS: sets picture text
    protected void updateLabel(String name) {
        pictureText.setText(news.getNews());
    }



}