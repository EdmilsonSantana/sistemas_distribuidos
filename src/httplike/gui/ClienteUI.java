package httplike.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import httplike.cliente.Cliente;

public class ClienteUI
{
	private static int ALTURA_JANELA = 540;
	private static int LARGURA_JANELA = 540;
	private static ClienteUI ui = null;
	
	private JFrame janela;
	private JPanel painelPrincipal;
	private JTextField campoSolicitacao;
	private JTextField campoHost;
	private JTextField campoPorta;
	private JTextPane areaTexto;
	private StyledDocument documentoEstilo;
	private Style estilo;
	
	public ClienteUI() {
		montaTela();
	}
	public void montaTela() {
		preparaJanela();
		prepararJanelaPrincipal();
		prepararBotaoSolicitar();
		prepararCampos();
		mostraJanela();
		montarAreaTexto();
	}
	public void montarAreaTexto() {
		areaTexto = new JTextPane();
		documentoEstilo = areaTexto.getStyledDocument();
		estilo = areaTexto.addStyle("Estilo", null); 
  
        painelPrincipal.add(areaTexto);
	}
	
	public void novoTextoItalico(String texto) {
		 
		StyleConstants.setItalic(this.estilo, true);
		try {
			documentoEstilo.insertString(documentoEstilo.getLength(), texto, this.estilo);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void novoTextoNegrito(String texto) {
		 
		StyleConstants.setBold(this.estilo, true);
		try {
			documentoEstilo.insertString(documentoEstilo.getLength(), texto, this.estilo);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void preparaJanela() {
		janela  = new JFrame("");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void mostraJanela() {
		janela.pack();
		janela.setSize(ALTURA_JANELA, 
				LARGURA_JANELA);
		janela.setVisible(true);
	}
	private void prepararJanelaPrincipal() {
		painelPrincipal = new JPanel();
		janela.add(painelPrincipal);
	}
	
	private void prepararCampos() {
		campoSolicitacao = new JTextField(30);
		campoSolicitacao.setToolTipText("Recurso");
		campoHost = new JTextField(30);
		campoHost.setToolTipText("Host");
		campoPorta = new JTextField(30);
		campoPorta.setToolTipText("Porta");
		painelPrincipal.add(campoSolicitacao);
		painelPrincipal.add(campoHost);
		painelPrincipal.add(campoPorta);
		
	}
	
	private void solicitacao() {
		Cliente cliente = new Cliente(ui);
		int porta = Integer.valueOf(campoPorta.getText());
		String host = campoHost.getText();
		String recurso = campoSolicitacao.getText();
		cliente.solicitarRecurso(host, porta, recurso);
	}
	
	private void prepararBotaoSolicitar() {
		JButton botaoSolicitar = new JButton("Solicitar arquivo");
		botaoSolicitar.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				solicitacao();
			}
		});
		painelPrincipal.add(botaoSolicitar);
	}
	
	public static ClienteUI getInstancia() {
		if ( ui == null ) {
			ui = new ClienteUI();
		}
		return ui;
	}
	
	public static void main(String[] args) {

		ClienteUI ui = ClienteUI.getInstancia();
	
	}
	
	
}
