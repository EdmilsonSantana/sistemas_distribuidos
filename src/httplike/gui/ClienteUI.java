package httplike.gui;

import httplike.cliente.Cliente;

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

public class ClienteUI {
	private static ClienteUI ui = null;

	private JFrame janela;
	private JTextField campoSolicitacao;
	private JTextField campoHost;
	private JTextField campoPorta;
	private JTextPane areaTexto;
	private StyledDocument documentoEstilo;
	private Style estilo;
	private JPanel painelCampoHost;
	private JPanel painelCampoPorta;
	private JPanel painelCampoSolicitacao;

	public ClienteUI() {
		montaTela();
	}

	public void montaTela() {
		preparaJanela();
		prepararPaineis();
		prepararCampos();
		prepararBotaoSolicitar();
		mostraJanela();
		montarAreaTexto();
	}

	public void montarAreaTexto() {
		areaTexto = new JTextPane();
		documentoEstilo = areaTexto.getStyledDocument();
		estilo = areaTexto.addStyle("Estilo", null);

		janela.add(areaTexto);
	}

	public void novoTextoItalico(String texto) {

		StyleConstants.setItalic(this.estilo, true);
		try {
			documentoEstilo.insertString(documentoEstilo.getLength(), texto,
					this.estilo);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	public void novoTextoNegrito(String texto) {

		StyleConstants.setBold(this.estilo, true);
		try {
			documentoEstilo.insertString(documentoEstilo.getLength(), texto,
					this.estilo);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
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
		painelCampoHost = new JPanel();
		painelCampoPorta = new JPanel();
		painelCampoSolicitacao.setLayout(new GridLayout(1, 0));
		painelCampoHost.setLayout(new GridLayout(1, 0));
		painelCampoPorta.setLayout(new GridLayout(1, 0));
		janela.add(painelCampoSolicitacao);
		janela.add(painelCampoHost);
		janela.add(painelCampoPorta);

	}

	private void prepararCampos() {

		campoSolicitacao = new JTextField(15);
		campoSolicitacao.setToolTipText("Recurso");
		campoHost = new JTextField(15);
		campoHost.setToolTipText("Host");
		campoPorta = new JTextField(15);
		campoPorta.setToolTipText("Porta");
		JLabel recurso = new JLabel("Recurso");
		painelCampoSolicitacao.add(recurso);
		painelCampoSolicitacao.add(campoSolicitacao);
		JLabel host = new JLabel("Host");
		painelCampoHost.add(host);
		painelCampoHost.add(campoHost);
		JLabel porta = new JLabel("Porta");
		painelCampoPorta.add(porta);
		painelCampoPorta.add(campoPorta);

	}

	private void solicitacao() {
		Cliente cliente = new Cliente(ui);
		String portaCampo = campoPorta.getText();
		String host = campoHost.getText();
		String recurso = campoSolicitacao.getText();
		if ( validarEntrada(host, recurso, portaCampo)) {
			int porta = Integer.valueOf(portaCampo);
			cliente.solicitarRecurso(host, porta, recurso);
		} 
			
	}

	private void prepararBotaoSolicitar() {
		JButton botaoSolicitar = new JButton("Solicitar arquivo");
		botaoSolicitar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				solicitacao();
			}
		});
		janela.add(botaoSolicitar);
	}

	public static ClienteUI getInstancia() {
		if (ui == null) {
			ui = new ClienteUI();
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

		ClienteUI ui = ClienteUI.getInstancia();
	}

}
