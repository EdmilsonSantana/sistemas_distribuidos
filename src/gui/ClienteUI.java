package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClienteUI
{
	private static int ALTURA_JANELA = 540;
	private static int LARGURA_JANELA = 540;
	
	private JFrame janela;
	private JPanel painelPrincipal;
	private JTextField campoSolicitacao;
	private JTextField campoHost;
	private JTextField campoPorta;
	private JTextArea areaTexto;
	
	public void montaTela() {
		preparaJanela();
		prepararJanelaPrincipal();
		prepararBotaoSolicitar();
		prepararCampos();
		mostraJanela();
	}
	
	public void preparaJanela() {
		janela  = new JFrame("");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void mostraJanela() {
		janela.pack();
		janela.setSize(ALTURA_JANELA, 
				LARGURA_JANELA);
		janela.setVisible(true);
	}
	public void prepararJanelaPrincipal() {
		painelPrincipal = new JPanel();
		janela.add(painelPrincipal);
	}
	
	private void prepararCampos() {
		campoSolicitacao = new JTextField(30);
		campoHost = new JTextField(30);
		campoPorta = new JTextField(30);
		painelPrincipal.add(campoSolicitacao);
		painelPrincipal.add(campoHost);
		painelPrincipal.add(campoPorta);
		areaTexto = new JTextArea(100,100);
		areaTexto.setEditable(Boolean.FALSE);
		painelPrincipal.add(areaTexto);
	}
	
	private void prepararBotaoSolicitar() {
		JButton botaoSolicitar = new JButton("Solicitar arquivo");
		botaoSolicitar.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println(campoSolicitacao.getText());
				
			}
		});
		painelPrincipal.add(botaoSolicitar);
	}
}
