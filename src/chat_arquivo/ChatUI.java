
package chat_arquivo;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatUI {

	private ClienteChat cliente;

	private JFrame janela;

	private JTextField campoNomeCliente;

	private JTextField campoEscritaMensagem;

	private JPanel painelCampoNomeCliente;

	private JPanel painelBotaoEntrarNaSala;

	private JButton botaoEntrarNaSala;

	private JTextArea areaDeMensagens;

	private JPanel painelAreaDeConversa;

	private JButton botaoEscolhaDeArquivo;

	private JButton botaoEnviarMensagem;
	
	private JButton botaoSair;

	private JFileChooser seletorDeArquivo;

	private JPanel painelAreaDeMensagem;

	private ChatUI() {
		super();
	}

	public ChatUI(ClienteChat cliente) {
		this.cliente = cliente;
		montaTela();
	}

	public void montaTela() {

		preparaJanela();
		prepararPaineis();
		prepararCampos();
		prepararBotaoEntrarNaSala();
		prepararBotaoEnviarMensagem();
		prepararBotaoEscolhaDeArquivo();
		prepararBotaoSair();
		mostraJanela();

	}

	private void preparaJanela() {

		janela = new JFrame("");
		janela.setLayout(new BoxLayout(janela.getContentPane(), BoxLayout.Y_AXIS));
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void mostraJanela() {

		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	}

	private void prepararPaineisEntradaNaTrocaDeMensagens() {

		painelCampoNomeCliente = new JPanel();
		painelBotaoEntrarNaSala = new JPanel();
		painelCampoNomeCliente.setLayout(new GridLayout(1, 0));
		painelBotaoEntrarNaSala.setLayout(new FlowLayout(FlowLayout.CENTER));
		janela.add(painelCampoNomeCliente);
		janela.add(painelBotaoEntrarNaSala);
	}

	private void prepararPaineisNaTrocaDeMensagens() {

		painelAreaDeMensagem = new JPanel();
		painelAreaDeMensagem.setLayout(new GridLayout(1, 0));
		painelAreaDeConversa = new JPanel();
		painelAreaDeConversa.setLayout(new FlowLayout(FlowLayout.CENTER));
	}

	private void prepararPaineis() {

		prepararPaineisEntradaNaTrocaDeMensagens();
		prepararPaineisNaTrocaDeMensagens();
	}

	private void prepararCamposPaineisDeEntradaChat() {

		campoNomeCliente = new JTextField(15);
		campoNomeCliente.setToolTipText("Apelido");
		JLabel recurso = new JLabel("Apelido");
		painelCampoNomeCliente.add(recurso);
		painelCampoNomeCliente.add(campoNomeCliente);
	}

	private void prepararCamposAreaTrocaDeMensagens() {

		areaDeMensagens = new JTextArea(200, 200);
		//areaDeMensagens.scrollRectToVisible(aRect);
		painelAreaDeMensagem.add(areaDeMensagens);
		campoEscritaMensagem = new JTextField(15);
		painelAreaDeConversa.add(campoEscritaMensagem);
	}

	private void prepararCampos() {

		prepararCamposPaineisDeEntradaChat();
		prepararCamposAreaTrocaDeMensagens();

	}

	private void entrarAreaDeTrocaDeMensagens() {

		janela.remove(painelBotaoEntrarNaSala);
		janela.remove(painelCampoNomeCliente);
		janela.add(painelAreaDeMensagem);
		janela.add(painelAreaDeConversa);
		janela.setSize (600, 600);
		janela.revalidate();

	}

	public void escreverMensagemAreaTexto(String msg) {
		areaDeMensagens.append(msg.concat("\n"));
	}

	private void prepararBotaoSair(){
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				cliente.enviarMensagem("Saiu");
				janela.dispose();
			}
		});
		painelAreaDeConversa.add(botaoSair);
		
	}
	
	private void prepararBotaoEnviarMensagem() {

		botaoEnviarMensagem = new JButton("Enviar");
		botaoEnviarMensagem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				cliente.enviarMensagem(campoEscritaMensagem.getText());
			}
		});
		painelAreaDeConversa.add(botaoEnviarMensagem);
	}

	private void prepararBotaoEscolhaDeArquivo() {

		botaoEscolhaDeArquivo = new JButton("Anexo");
		seletorDeArquivo = new JFileChooser();
		botaoEscolhaDeArquivo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {

				int resultadoDoEvento = seletorDeArquivo.showOpenDialog(janela);
				if (resultadoDoEvento == JFileChooser.APPROVE_OPTION) {
					File arquivo = seletorDeArquivo.getSelectedFile();

					cliente.enviarArquivoDoDiretorio(arquivo);

				}

			}
		});
		painelAreaDeConversa.add(botaoEscolhaDeArquivo);
	}

	private void prepararBotaoEntrarNaSala() {

		botaoEntrarNaSala = new JButton("Entrar");
		painelBotaoEntrarNaSala.add(botaoEntrarNaSala);
		botaoEntrarNaSala.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean entradaValida = cliente.autenticarEntradaNoChat(campoNomeCliente.getText());
				if (entradaValida) {
					entrarAreaDeTrocaDeMensagens();
				}

			}
		});

		painelBotaoEntrarNaSala.add(botaoEntrarNaSala);
	}

	private boolean validarEntrada(String host, String recurso, String porta) {

		if ("".equals(host) || "".equals(recurso) || "".equals(porta)) {
			return false;
		}
		return true;
	}

}
