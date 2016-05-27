package meshchatnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Server {

	private PrintWriter output;
	private BufferedReader input;
	private ServerSocket server;
	private Socket socket;
	private List<PrintWriter> clients;
	private String myName;
	private JTextArea conversation = null;
	private int port;
	private PrintWriter above;

	public Server(String name, int prt) {
		myName = name.toUpperCase();
		port = prt;
		clients = new ArrayList<PrintWriter>();

	}

	public Server() {

	}

	public void startRunning() {

		/**
		 * start thread that listens for new connection requests once new
		 * connection is established add it to clients- socket =
		 * server.accept(); establish I/O Streams for each new client listen for
		 * conversations from clients and when one is found broadcast to all
		 * clients in array make sure no client receives same message twice.
		 */
		if (conversation != null) {
			new Thread(new Runnable() {

				public void run() {
					try {
						server = new ServerSocket(port);

						while (true) {
							try {
								socket = server.accept();
								output = new PrintWriter(socket.getOutputStream());
								input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								String clientAddress = socket.getInetAddress().toString();
								PrintWriter writer = new PrintWriter(socket.getOutputStream());
								clients.add(writer);

								Thread t = new Thread(new ClientHandler(socket, conversation, clients, above));
								t.start();
								conversation.append("\n\t     Got a new connection from " + clientAddress + "\n");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}).start();
		}
	}

	public String getMyIpAddress() {
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PrintWriter getOutput() {
		return output;
	}

	public BufferedReader getInput() {
		return input;
	}

	public String getMyName() {
		return myName;
	}

	public void sendTextArea(JTextArea convo) {
		conversation = convo;
	}
	
	public void setAboveOutputStream(PrintWriter print){
		above = print;
	}
}
// option 2
/*
 * if (conversation != null) { SwingUtilities.invokeLater(new Runnable() {
 * public void run() {
 * 
 * while (true) { try { Socket clientSocket = server.accept(); int i = 0; for (i
 * = 0; i < maxClientsCount; i++) { if (threads[i] == null) { (threads[i] = new
 * ClientThread( conversation, clientSocket, threads)).start(); break; } } if (i
 * == maxClientsCount) { ObjectOutputStream os = new ObjectOutputStream(
 * clientSocket.getOutputStream()); os.writeObject("Server too busy. Try later."
 * ); os.close(); clientSocket.close(); } } catch (IOException e) {
 * System.out.println(e); } finally { // closeUp(); // close all Streams } } }
 * }); }
 */