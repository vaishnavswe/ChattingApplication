package chatting.application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import javax.swing.JScrollBar;

public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Client() {
        f.setLayout(null);
        // green header panel
        JPanel p1= new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0, 450,50);
        p1.setLayout(null);
        f.add(p1);

        // back button or the close button
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,10,25,25);
        p1.add(back);
        //button function override
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        // Client app profile picture
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel gaitonde = new JLabel(i6);
        gaitonde.setBounds(40,5,40,40);
        p1.add(gaitonde);

        // option button
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i8 = i7.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel optionButton = new JLabel(i9);
        optionButton.setBounds(400,5,40,40);
        p1.add(optionButton);

        // phone call picture
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(355,7,40,40);
        p1.add(phone);

        // video call picture
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i14 = i13.getImage().getScaledInstance(28,28,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel videoCall = new JLabel(i15);
        videoCall.setBounds(300,6,40,40);
        p1.add(videoCall);

        // Client chat name
        JLabel ClientName = new JLabel("Kabootar");
        ClientName.setBounds(90, 12,100, 18);
        ClientName.setForeground(Color.WHITE);
        ClientName.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(ClientName);

        // status text
        JLabel status = new JLabel("Active Now");
        status.setBounds(90, 27,100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        p1.add(status);

        //text box panel
        a1 = new JPanel();
        a1.setBounds(5,55,440, 585);
        f.add(a1);

        // text typing field
        text = new JTextField();
        text.setBounds(5,650,350,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        // send button
        JButton send = new JButton("Send");
        send.setBounds(360,650,85,40);
        send.setBackground(new Color(7,84,84));
        send.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        send.addActionListener(this);
        send.setForeground(Color.white);
        f.add(send);
        // main panel
        f.setSize(450, 700);
        f.setLocation(550, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.white);
        f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String message = text.getText();
            JPanel p2 = formatLabel(message);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(message);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output;
        if (out.length() >21) {
            output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        }
        else {
            output = new JLabel("<html><p>" + out + "</p></html>");
        }
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);




        return panel;
    }


    public static void main(String[] args) {
        new Client();
        try {
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while(true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
