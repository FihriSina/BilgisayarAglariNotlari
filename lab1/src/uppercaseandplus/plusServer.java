package uppercaseandplus;
/**
 *
 * @author fihri
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;
import java.net.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class plusServer {

    public static void main(String[] args) {
        try {
            // Sunucu soketi başlatma
            ServerSocket serverSocket = new ServerSocket(1234); // 1234 portu üzerinden dinleme yapacak
            System.out.println("Sunucu başlatıldı. Bekleniyor...");

            while (true) {
                // İstemciden gelen bağlantıyı kabul et
                Socket socket = serverSocket.accept();
                System.out.println("Bir istemci bağlandı!");

                // Veri akışları
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // İstemciden gelen veriyi oku
                String number1Str = in.readLine();
                String number2Str = in.readLine();
                String operation = in.readLine();

                // Verileri işleyip sonucu hesapla
                double number1 = Double.parseDouble(number1Str);
                double number2 = Double.parseDouble(number2Str);
                double result = 0;

                switch (operation) {
                    case "topla":
                        result = number1 + number2;
                        break;
                    case "cikarma":
                        result = number1 - number2;
                        break;
                    case "carpma":
                        result = number1 * number2;
                        break;
                    case "bolme":
                        if (number2 != 0) {
                            result = number1 / number2;
                        } else {
                            out.println("Hata: Sıfıra bölme!");
                            continue;
                        }
                        break;
                    default:
                        out.println("Geçersiz işlem!");
                        continue;
                }

                // Sonucu istemciye gönder
                out.println("Sonuç: " + result);

                // Bağlantıyı kapat
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
