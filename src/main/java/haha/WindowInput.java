package haha;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WindowInput extends JFrame {
	class ImagePanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			if (buf == null || buf.getWidth() != getWidth() || buf.getHeight() != getHeight()) {
				buf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			}
			Graphics2D gg = buf.createGraphics();
			gg.setColor(Color.black);
			gg.fillRect(0, 0, getWidth(), getHeight());
			int faceWidth = getWidth() / 3, faceHeight = getHeight() / 4;
			int w = faceWidth / 2, h = faceHeight / 2;
			for (int i = 0; i < 24; i++) {
				int faceRow = face[i / 4] / 3, faceCol = face[i / 4] % 3;
				int row = (i % 4) / 2, col = (i % 4) % 2;
				Rectangle rec = new Rectangle(faceCol * faceWidth + col * w, faceRow * faceHeight + row * h, w, h);
				Color c = i < colors.length() ? getColorFromChar(colors.charAt(i)) : Color.GRAY;
				gg.setColor(c);
				gg.fillRect(rec.x, rec.y, rec.width - 2, rec.height - 2);
			}
			g.drawImage(buf, 0, 0, null);
		}
	}

	BufferedImage buf;
	ImagePanel imgPanel = new ImagePanel();
	String colors = "";
	int now = 0;
	JTextArea area = new JTextArea();
	JTextField field = new JTextField();
	// 4行3列=12个小面,剑形六个面对应的小面
	int[] face = { 1, 3, 4, 5, 7, 10 };
	final static String colorChars = "rygwbo";
	TableSolver solver = new TableSolver();

	Color getColorFromChar(char c) {
		Color[] colorArray = { Color.RED, Color.YELLOW, Color.GREEN, Color.WHITE, Color.BLUE, Color.ORANGE };
		int id = colorChars.indexOf(c);
		if (id == -1)
			return Color.darkGray;
		return colorArray[id];
	}

	void init() {
		area.setLineWrap(true);
		area.setFont(new Font("Conoslas", Font.BOLD, 20));
		area.setColumns(10);
		field.setFont(new Font("楷体", Font.BOLD, 24));
		field.setEditable(false);
	}

	public WindowInput() {
		setTitle("二阶魔方求解器");
		setSize(600, 600);
		setLayout(new BorderLayout());
		getContentPane().add(imgPanel, BorderLayout.CENTER);
		getContentPane().add(area, BorderLayout.EAST);
		getContentPane().add(field, BorderLayout.SOUTH);
		init();

		area.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String s = area.getText();
				s = s.toLowerCase().replaceAll("[^" + colorChars + "]", "");
				s = s.substring(0, Math.min(24, s.length()));
				if (s.length() == 24) {
					field.setText(solver.solve(s));
				} else {
					field.setText("");
				}
				colors = s;
				imgPanel.repaint();
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
		area.requestFocus();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new WindowInput();
	}
}
