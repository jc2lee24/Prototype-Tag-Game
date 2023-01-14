//Acts as runner
//when started, start a new client screen
import javax.swing.*;
import java.io.*;

public class Client {

    public static void main(String args[]) throws IOException {

		JFrame frame = new JFrame("Client");

		ClientScreen sc = new ClientScreen();
		frame.add(sc);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		sc.animate();
		sc.poll();
	}
}