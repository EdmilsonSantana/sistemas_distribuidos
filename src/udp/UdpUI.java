package udp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import httplike.cliente.Cliente;

public class UdpUI {
	private static UdpUI ui = null;

	private JFrame janela;
	private JTextField campoSolicitacao;
	private JTextField campoHost;
	private JTextField campoPorta;
	private JTextPane areaTexto;
	private JPanel painelCampoHost;
	private JPanel painelCampoPorta;
	private JPanel painelCampoSolicitacao;
	private JPanel painelBotaoSolicitar;
	private JButton botaoSolicitar;
	
	public UdpUI() {
		montaTela();
	}

	public void montaTela() {
		preparaJanela();
		prepararPaineis();
		prepararCampos();
		prepararBotaoSolicitar();
		mostraJanela();
		
	}

	
	
	private void preparaJanela() {
		janela = new JFrame("");
		janela.setLayout(new BoxLayout(janela.getContentPane(),
				BoxLayout.Y_AXIS));
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void mostraJanela() {
		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	}

	private void prepararPaineis() {
		painelCampoSolicitacao = new JPanel();
		painelBotaoSolicitar = new JPanel();
		painelCampoHost = new JPanel();
		painelCampoPorta = new JPanel();
		painelCampoSolicitacao.setLayout(new GridLayout(1, 0));
		painelCampoHost.setLayout(new GridLayout(1, 0));
		painelCampoPorta.setLayout(new GridLayout(1, 0));
		painelBotaoSolicitar.setLayout( new FlowLayout(FlowLayout.CENTER));
		janela.add(painelCampoSolicitacao);
		janela.add(painelCampoHost);
		janela.add(painelCampoPorta);
		janela.add(painelBotaoSolicitar);
	}

	private void prepararCampos() {

		campoSolicitacao = new JTextField(15);
		campoSolicitacao.setToolTipText("Recurso");
		campoSolicitacao.setText("udp.txt");
		campoHost = new JTextField(15);
		campoHost.setToolTipText("Host");
		campoPorta = new JTextField(15);
		campoPorta.setToolTipText("Porta");
		JLabel recurso = new JLabel("Recurso");
		painelCampoSolicitacao.add(recurso);
		painelCampoSolicitacao.add(campoSolicitacao);
		JLabel host = new JLabel("Host");
		campoHost.setText("127.0.0.1");
		painelCampoHost.add(host);
		painelCampoHost.add(campoHost);
		JLabel porta = new JLabel("Porta");
		campoPorta.setText("6791");
		painelCampoPorta.add(porta);
		painelCampoPorta.add(campoPorta);

	}

	private void solicitacao() {
		ClienteUDP cliente = new ClienteUDP();
		String portaCampo = campoPorta.getText();
		String host = campoHost.getText();
		String recurso = campoSolicitacao.getText();
		if ( validarEntrada(host, recurso, portaCampo)) {
			int porta = Integer.valueOf(portaCampo);
			cliente.enviarArquivo(recurso, host, porta);
		} 
			
	}
	
	

	private void prepararBotaoSolicitar() {
		botaoSolicitar = new JButton("Solicitar arquivo");
		painelBotaoSolicitar.add(botaoSolicitar);
		botaoSolicitar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				solicitacao();
			}
		});
		painelBotaoSolicitar.add(botaoSolicitar);
	}

	public static UdpUI getInstancia() {
		if (ui == null) {
			ui = new UdpUI();
		}
		return ui;
	}

	private boolean validarEntrada(String host, String recurso, String porta) {
		if( "".equals(host) || "".equals(recurso) || "".equals(porta)) {
			return false;
		}
		return true;
	}
	
	
	
	
	public static void main(String[] args) {

		UdpUI.getInstancia();
	}

}
