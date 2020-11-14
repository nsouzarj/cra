package br.adv.cra.utilitarios;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Macro {
	private Robot robot;
	private static final int SPEED = 300;

	public void Robo(int[] letter) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < letter.length; i++) {
			robot.delay(SPEED);
			robot.keyPress(letter[i]);
			robot.keyRelease(letter[i]);
		}

	}

	public void MoveMouse() {

		robot.mouseMove(800, 600);

	}

	public void copia() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_C);

	}

	public void cola() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);

	}

	public void seleciona() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_A);

	

	}

	public static void main(String[] args) {

		Macro t = new Macro();

		int[] executeNotepad = { KeyEvent.VK_WINDOWS, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_T, KeyEvent.VK_E,
				KeyEvent.VK_P, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER };
		int[] maximizeIt = { KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_ENTER };
		int[] caixaalt = { KeyEvent.VK_CAPS_LOCK };

		int[] messageToPrint = { KeyEvent.VK_F, KeyEvent.VK_I, KeyEvent.VK_L, KeyEvent.VK_H, KeyEvent.VK_A,
				KeyEvent.VK_SPACE, KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_SPACE, KeyEvent.VK_P, KeyEvent.VK_U,
				KeyEvent.VK_T, KeyEvent.VK_A, KeyEvent.VK_ENTER };
		t.Robo(executeNotepad);
		t.Robo(maximizeIt);
		t.Robo(caixaalt);
		t.Robo(messageToPrint);

		for (int A = 0; A < 5; A++) {
			t.Robo(messageToPrint);
		}

		t.seleciona();

		t.cola();

	}

}
