import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final String NAME = "Tom";
    private static final int PORT = 25000;

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Write a message to Server");
        }
            try {
                String msg = args[0];
                String message = "{\"name\":\"" + NAME + "\",\"message\":\"" + msg + "\"}";

                InetAddress address = InetAddress.getByName(HOST);
                Socket socket = new Socket(address, PORT);

                OutputStream os = new DataOutputStream(socket.getOutputStream());
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                bw.write(message);
                bw.flush();
                System.out.println(message);

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                msg = br.readLine();
                msg = parseToString(msg);
                System.out.println("Message received from the server : " + msg);
                socket.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
}

    private static String parseToString(String msg) {
        String message = "";
        String[] fields = msg.split("\"");
        for (int i = 1; i < fields.length - 2; i += 2) {
            if (fields[i].equals("message")) {
                message = fields[i + 2];
                break;
            }
        }
        return message;
    }
    }