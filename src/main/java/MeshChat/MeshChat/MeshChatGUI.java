package MeshChat.MeshChat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MeshChatGUI extends JFrame{
	
	private Client client;
	
	private JTextArea conversation;
	private JLabel IPaddress;
	private JLabel enterIp;
	private JLabel notifyMsg;
	private JButton connect, send;
	private JTextField text;
	private JTextField serverIp;
	private BorderLayout layout;
	
	public MeshChatGUI(){
		
		client = new Client();
		
		setTitle("Mesh Chat");
		setSize(600, 730);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		layout = new BorderLayout();
		this.setLayout(layout);
		
		setFeatures();
		setButtons();
	}

	
	private void setFeatures() {
		conversation = new JTextArea();
		conversation.setEditable(false);
		conversation.setLineWrap(true);
		conversation.setWrapStyleWord(true);
		IPaddress = new JLabel("My IP Address: " + client.getMyIpAddress(), SwingConstants.CENTER);
		notifyMsg = new JLabel("");
		enterIp = new JLabel("Enter IP Address: ");
		serverIp = new JTextField(10);
		//connect is for the client to connect to a server
		connect = new JButton("Connect"); //add ActionListener
		//send is a server sending out to all clients in its branches
		send = new JButton("Send"); //Add ActionListener
		text = new JTextField(40);	
		
		conversation.setBounds(0,0,500, 700);

        JScrollPane scrollPane = new JScrollPane(conversation,
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(525, 725));
        scrollPane.setBounds(0, 0, 500, 500);
        
		JPanel top = new JPanel(new BorderLayout());
		JPanel topCenter = new JPanel();
		top.add(IPaddress, BorderLayout.NORTH);
		topCenter.add(enterIp);
		topCenter.add(serverIp);
		topCenter.add(connect);
		topCenter.add(notifyMsg);
		top.add(topCenter, BorderLayout.CENTER);
		
		JPanel center = new JPanel();
		center.add(scrollPane);
		
		JPanel bottom = new JPanel();
		bottom.add(text);
		bottom.add(send);
		
		add(top, BorderLayout.PAGE_START);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);
		
	}
	
	public void setButtons(){
		connect.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				notifyMsg.setText(""); //clears error message
				if (client.validateIP(serverIp.getText())){
					if(client.connectToServer(serverIp.getText())){
						notifyMsg.setText("Connected");
					}
					else{
						notifyMsg.setText("Unable to Connect");
					}
				}
				else{
					notifyMsg.setText("Invalid IP Address Format");
				}
			}
		});
	}


	public static void main(String[] args){
		new MeshChatGUI().setVisible(true);
	}
}
