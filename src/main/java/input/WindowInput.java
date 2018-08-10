package input;

import haha.TableSolver;
import min2phase.Min2PhaseSolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
        int w = faceWidth / N, h = faceHeight / N;
        for (int i = 0; i < 6 * N * N; i++) {
            int faceRow = face[i / (N * N)] / 3, faceCol = face[i / (N * N)] % 3;
            int row = (i % (N * N)) / N, col = (i % (N * N)) % N;
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
JTextArea inputArea = new JTextArea();
JTextArea outputArea = new JTextArea();
// 4行3列=12个小面,剑形六个面对应的小面
int[] face = {1, 3, 4, 5, 7, 10};
final static String colorChars = "rygwbo";
int N;//N阶魔方

Color getColorFromChar(char c) {
    Color[] colorArray = {Color.RED, Color.YELLOW, Color.GREEN, Color.WHITE, Color.BLUE, Color.ORANGE};
    int id = colorChars.indexOf(c);
    if (id == -1)
        return Color.darkGray;
    return colorArray[id];
}

void init() {
    inputArea.setLineWrap(true);
    inputArea.setFont(new Font("Conoslas", Font.BOLD, 20));
    inputArea.setColumns(10);
    outputArea.setFont(new Font("楷体", Font.BOLD, 24));
    outputArea.setEditable(false);
}

public WindowInput(Solver solver) {
    this.N = solver.getN();
    setTitle(this.N + "阶魔方求解器");

    this.setSize(800, 800);
    imgPanel.setBounds(0, 0, 540, 720);
    inputArea.setBounds(540, 0, 260, 720);
    outputArea.setBounds(0, 720, 800, 80);
    outputArea.setBackground(Color.BLACK);
    outputArea.setForeground(Color.white);
    this.setResizable(false);
    this.setLayout(null);
    getContentPane().add(imgPanel);
    getContentPane().add(inputArea);
    getContentPane().add(outputArea);
    init();

    inputArea.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            String s = inputArea.getText();
            s = s.toLowerCase().replaceAll("[^" + colorChars + "]", "");
            s = s.substring(0, Math.min(6 * N * N, s.length()));
            if (s.length() == 6 * N * N) {
                outputArea.setText(solver.solve(s));
            } else {
                outputArea.setText("");
            }
            colors = s;
            imgPanel.repaint();
        }
    });
    setLocationRelativeTo(null);
    setVisible(true);
    inputArea.requestFocus();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
}

public static void main(String[] args) {
    new WindowInput(new TableSolver());
//    new WindowInput(new ThreeSolver());
//    new WindowInput(new BackTracing());
//    new WindowInput(new Min2PhaseSolver());
}
}
