package uppercaseandplus;

/**
 *
 * @author fihri
 */


import java.net.Socket;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class plusClient {
    public static void main(String[] args) {
        // GUI'yi oluştur
        JFrame frame = new JFrame("Java Client");
        JPanel panel = new JPanel();
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label1 = new JLabel("Sayı 1:");
        JTextField number1Field = new JTextField(10);
        JLabel label2 = new JLabel("Sayı 2:");
        JTextField number2Field = new JTextField(10);
        JLabel label3 = new JLabel("İşlem:");
        JComboBox<String> operations = new JComboBox<>(new String[]{"topla", "cikarma", "carpma", "bolme"});
        JButton sendButton = new JButton("Gönder");
        JLabel resultLabel = new JLabel("Sonuç:");

        // Paneli düzenle
        panel.add(label1);
        panel.add(number1Field);
        panel.add(label2);
        panel.add(number2Field);
        panel.add(label3);
        panel.add(operations);
        panel.add(sendButton);
        panel.add(resultLabel);

        frame.add(panel);
        frame.setVisible(true);

        // Butona tıklama olayını dinle
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String number1 = number1Field.getText();
                    String number2 = number2Field.getText();
                    String operation = (String) operations.getSelectedItem();

                    // Sunucuya bağlan
                    Socket socket = new Socket("localhost", 1234);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    // Veriyi sunucuya gönder
                    out.println(number1);
                    out.println(number2);
                    out.println(operation);

                    // Sonucu al
                    String result = in.readLine();
                    resultLabel.setText("Sonuç: " + result);

                    // Bağlantıyı kapat
                    socket.close();
                } catch (IOException ex) {
                    resultLabel.setText("Bağlantı hatası!");
                    ex.printStackTrace();
                }
            }
        });
    }
}

