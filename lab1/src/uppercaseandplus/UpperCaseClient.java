package uppercaseandplus;
/**
 *
 * @author fihri
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class UpperCaseClient {

    private JFrame frame;
    private JTextField textField;
    private JButton button;

    public UpperCaseClient() {
        frame = new JFrame("UpperCase Client");
        textField = new JTextField(20);
        button = new JButton("ToUpper");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendTextToServer();
            }
        });

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(button);
        frame.add(panel);

        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void sendTextToServer() {
        try (Socket socket = new Socket("localhost", 12345); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputText = textField.getText();
            out.println(inputText);

            String response = in.readLine();
            textField.setText(response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UpperCaseClient();
    }
}
