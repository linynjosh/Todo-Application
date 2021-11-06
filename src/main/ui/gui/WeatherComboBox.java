package ui.gui;

import ui.WeatherApp;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class WeatherComboBox extends JPanel
        implements ActionListener {
    JTextArea pictureText;
    WeatherApp weather;
    JLabel picture;

    // Represents the weather combo box on the gui
    public WeatherComboBox(String location) {
        super(new BorderLayout());
        weather = new WeatherApp(location);
        String[] petStrings = { "Weather Description", "Weather Data", "Location", "Combined Weather Data"};
        JComboBox petList = new JComboBox(petStrings);
        petList.setSelectedIndex(0);
        petList.addActionListener(this);
        pictureText = new JTextArea();
        picture = new JLabel();
        pictureText.setFont(pictureText.getFont().deriveFont(Font.ITALIC));
        //picture.setHorizontalAlignment(JLabel.CENTER);
        setIcon(petStrings[petList.getSelectedIndex()]);
        pictureText.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        picture.setPreferredSize(new Dimension(177, 122 + 10));

        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        //picture.setHorizontalAlignment(JLabel.CENTER);
        setIcon(petStrings[petList.getSelectedIndex()]);
        picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        picture.setPreferredSize(new Dimension(177, 122 + 10));

        add(petList, BorderLayout.PAGE_START);
        add(picture, BorderLayout.CENTER);
        add(pictureText, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
    }


    // MODIFIES: this
    // EFFECTS: displays Icon and text according to selected choice for combo box
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        setIcon(petName);
    }


    // MODIFIES: this
    // EFFECTS: sets the Icon for picture
    protected void setIcon(String name) {
        URL url = null;
        try {
            url = new URL("http://openweathermap.org/img/wn/" + weather.getImageIcon() + "@2x.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image);
        picture.setIcon(icon);
        pictureSetText(name);
    }

    // MODIFIES: this
    // EFFECTS: sets the text under the picture
    private void pictureSetText(String name) {
        if (name.equals("Weather Description")) {
            pictureText.setText("Weather Description: \n" + weather.getWeatherDescription());
        } else if (name.equals("Weather Data")) {
            pictureText.setText("Weather Data: \n" + weather.getWeatherData());
        } else if (name.equals("Location")) {
            pictureText.setText("Coordinate: \n" + weather.getCoord());
        } else {
            pictureText.setText("Weather Description: \n" + weather.getWeatherDescription() + "\n"
                    + "Weather Data: \n" + weather.getWeatherData() + "\n"
                    + "Coordinate: \n" + weather.getCoord());
        }
    }


}

