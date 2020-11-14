package br.adv.cra.utilitarios;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class telaMSN {

	public static void main(String[] args) {

		final TrayIcon trayIcon;
		// declarando uma constante do tipo TrayIcon
		// Aqui vamos testar se o recurso é suportado

		if (SystemTray.isSupported()) {
			// declarando uma variavel do tipo SystemTray
			SystemTray tray = SystemTray.getSystemTray();

			// declarando uma variavel do tipo Image que contera a imagem
			// tray.gif
			Image image = Toolkit.getDefaultToolkit().getImage(
					"WebContent/imagens/mobilelogo.jpg");

			// Criamos um listener para escutar os eventos de mouse
			MouseListener mouseListener = new MouseListener() {

				public void mouseClicked(MouseEvent e) {

				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {

				}

				public void mouseReleased(MouseEvent e) {
				}

			};

			// Criamos um ActionListener para a ação de encerramento do
			// programa.
			ActionListener exitListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// Imprime uma mensagem de despedida na tela
					System.out.println("Saindo...");
					JOptionPane.showMessageDialog(null, "Saindo...");
					System.exit(0);

				}

			};

			// Criamos um ActionListener para a exibir uma mensagem na tela ao
			// clicarmos
			// em um item do menu.

			ActionListener mostramsgliDiligencia = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Pagina Diligência...");

				}
			};

			ActionListener mostramsglistener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Pagina Audiência...");

				}
			};

			// Criando um objeto PopupMenu
			PopupMenu popup = new PopupMenu("Solicitações");

			// criando itens do menu
			MenuItem dili = new MenuItem("10 Diligências");
			MenuItem audi = new MenuItem("13 Audiências");
			MenuItem defaultItem = new MenuItem("Sair");

			// na linha a seguir associamos os objetos aos eventos
			dili.addActionListener(mostramsgliDiligencia);
			audi.addActionListener(mostramsglistener);

			defaultItem.addActionListener(exitListener);

			// Adicionando itens ao PopupMenu
			popup.add(dili);
			popup.add(audi);

			// adiconando um separador
			popup.addSeparator();

			// Criando objetos do tipo Checkbox

			CheckboxMenuItem cheque2 = new CheckboxMenuItem(
					"Iniciar Minimizado");
			popup.add(cheque2);
			popup.addSeparator();

			// Criando um submenu
			PopupMenu popup2 = new PopupMenu("TESTE CRA");
			MenuItem menu1 = new MenuItem("Item1");

			popup.add(popup2);
			popup.addSeparator();
			popup.add(defaultItem);

			// criando um objeto do tipo TrayIcon
			trayIcon = new TrayIcon(image, "Cavalcante Ramos Advogado", popup);
			ActionListener actionListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					trayIcon.displayMessage("Action Event",
							"Um Evento foi disparado",
							TrayIcon.MessageType.INFO);
				}
			};

			// Na linha a seguir a imagem a ser utilizada como icone sera
			// redimensionada
			trayIcon.setImageAutoSize(true);

			// Seguida adicionamos os actions listeners
			trayIcon.addActionListener(actionListener);
			trayIcon.addMouseListener(mouseListener);

			try {
				tray.add(trayIcon);
				// balão de aviso
				trayIcon.displayMessage("Audiências",
						"Chegaram novas Audiências!", TrayIcon.MessageType.INFO);

			} catch (AWTException e) {
				System.err
						.println("TrayIcon não pode ser adicionado no sistema.");
			}

		} else {
			System.err.println("Bandeja do sistema não é suportado.");
		}
	}

}
