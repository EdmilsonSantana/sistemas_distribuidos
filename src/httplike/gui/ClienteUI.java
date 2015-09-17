package httplike.gui;

import httplike.cliente.Cliente;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.management.RuntimeErrorException;
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
	private JPanel painelBotaoSolicitar;
	private JButton botaoSolicitar;
	
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
	}

	public void novoTextoItalico(String texto) {

		StyleConstants.setItalic(this.estilo, true);
		try {
			documentoEstilo.insertString(documentoEstilo.getLength(), texto,
					this.estilo);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
		estilo.removeAttributes(estilo.getAttributeNames());
	}
	public void novoTexto(String texto) {
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
		estilo.removeAttributes(estilo.getAttributeNames());
	}
	public void novoTextoVermelho(String texto){
		
		StyleConstants.setForeground(this.estilo, Color.red);
		try{
			documentoEstilo.insertString(documentoEstilo.getLength(), texto, this.estilo);
			
		}catch(BadLocationException e){
			throw new RuntimeException(e);
		}
		estilo.removeAttributes(estilo.getAttributeNames());
	}
	public void novoTextoAzul(String texto){
		
		StyleConstants.setForeground(this.estilo, Color.blue);
		try{
			documentoEstilo.insertString(documentoEstilo.getLength(), texto, this.estilo);
		}catch(BadLocationException e){
			throw new RuntimeException(e);
		}
		estilo.removeAttributes(estilo.getAttributeNames());
	}
	public void novoTextoSublinhado(String texto){
		
		StyleConstants.setUnderline(this.estilo,true);
		try{
			documentoEstilo.insertString(documentoEstilo.getLength(), texto, this.estilo);
		}catch(BadLocationException e){
			throw new RuntimeException(e);
		}
		estilo.removeAttributes(estilo.getAttributeNames());
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
	
	public void apresentarRecurso(String[][] conteudo, String pathRecurso ) {
		for( int i = 0; i < conteudo[1].length; i++ ) {
			if( "italico".equals(conteudo[1][i])) {
				ui.novoTextoItalico(conteudo[0][i]);
			} else if ( "negrito".equals(conteudo[1][i]) ) {
				ui.novoTextoNegrito(conteudo[0][i]);
			} else if( "vermelho".equals(conteudo[1][i])){
				ui.novoTextoVermelho(conteudo[0][i]);
			} else if( "azul".equals(conteudo[1][i])){
				ui.novoTextoAzul(conteudo[0][i]);
			} else if( "sublinhado".equals(conteudo[1][i])){
				ui.novoTextoSublinhado(conteudo[0][i]);
			} else {
				novoTexto(conteudo[0][i]);
			}
		}
		JOptionPane.showMessageDialog(null, areaTexto, pathRecurso, JOptionPane.NO_OPTION);
		limparAreaTexto();
	}
	
	private void limparAreaTexto() {
		areaTexto.setText("");
	}
	
	public static void main(String[] args) {

		ClienteUI.getInstancia();
	}

}
