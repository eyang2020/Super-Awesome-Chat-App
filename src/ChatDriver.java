import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ChatDriver
 * A GUI panel that a chat user interacts with.
 *
 * @author Camber Boles
 * @version 20 November 2020
 */
public class ChatDriver extends JComponent implements Runnable {

    JTextField message;
    JButton sendMessage;
    JLabel testLabel;


    public ChatDriver() {

    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Chat");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        message = new JTextField("Send message...", 20);
        sendMessage = new JButton("Send");
        testLabel = new JLabel();
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testLabel.setText(message.getText());
            }
        });
        panel.add(message);
        panel.add(sendMessage);
        content.add(panel, BorderLayout.SOUTH);
        content.add(testLabel, BorderLayout.CENTER);

        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ChatDriver());
    }
}
