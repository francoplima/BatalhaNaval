package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author guilh
 */
public class LayoutGrid extends JPanel implements ActionListener{
    JButton buttons[][];
    public LayoutGrid(int linhas, int colunas) {
        buttons = new JButton[linhas][colunas];
        setLayout(new GridLayout(linhas, colunas));
        for (int i=0; i<linhas; i++) {
            for (int j=0; j<colunas; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setText("T");
                buttons[i][j].setName(i +"_"+j);
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ((JButton)e.getSource()).setBackground(Color.red);
    }
}
