package View;

import Domain.Embarcacao;
import Domain.Jogador;
import Service.Tabuleiro;
import static Service.Tabuleiro.COLUNAS;
import static Service.Tabuleiro.LINHAS;
import Service.TabuleiroService;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class TelaInicial extends javax.swing.JFrame {
    private static final int SINGLEPLAYER = 1;
    private static final int MULTIPLAYER = 2;
    private static final int TENTATIVAS_TOTAL = 5;
    
    private JButton buttons[][];
    private ArrayList<JButton> allButtons;
    private Tabuleiro tabuleiro;
    private final int linhas=10, colunas=10;
    private int rodada=1, tentativas = TelaInicial.TENTATIVAS_TOTAL;
    
    public TelaInicial() throws Throwable {
        initComponents();
        
        allButtons = new ArrayList<>();
        
        embarc1.setText(TabuleiroService.getEmbarcacoesNome(Embarcacao.ENCOURACADO_ID));
        embarc2.setText(TabuleiroService.getEmbarcacoesNome(Embarcacao.FRAGATA_ID));
        embarc3.setText(TabuleiroService.getEmbarcacoesNome(Embarcacao.LANCHA_ID));
        embarc4.setText(TabuleiroService.getEmbarcacoesNome(Embarcacao.PORTAAVIAO_ID));
        embarc5.setText(TabuleiroService.getEmbarcacoesNome(Embarcacao.SUBMARINO_ID));
        
        tabuleiro = new TabuleiroService();
        
        buttons = new JButton[linhas][colunas];
        GridLayout g = new GridLayout(LINHAS, COLUNAS);
        matriz.setLayout(g);
        
        for (int i=0; i<linhas; i++) {
            for (int j=0; j<colunas; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setText("");
                buttons[i][j].setName(i +"_"+j);
                buttons[i][j].setSize(20, 30);
                buttons[i][j].addMouseListener(listener);
                matriz.add(buttons[i][j]);
                allButtons.add(buttons[i][j]);
            }
        }
        obterJogadores();
        nomeJogador1.setText(tabuleiro.getNomeJogador1());
        nomeJogador2.setText(tabuleiro.getNomeJogador2());
        atualizaDados();
    }
    
    private void obterJogadores()  throws Throwable {
        String jogador1 = JOptionPane.showInputDialog(null, "Entre com seu nome", "Jogador 1", JOptionPane.QUESTION_MESSAGE);
        String jogador2 = JOptionPane.showInputDialog(null, "Entre com seu nome", "Jogador 2", JOptionPane.QUESTION_MESSAGE);
        tabuleiro.createJogador(jogador1);
        tabuleiro.createJogador(jogador2);
    }
    /**
     * Este método retorna um vetor de duas posições no padrão {linha, coluna}.
     * Este método espera uma String no padrão "1_1" onde o primeiro número representa a linha
     *  e o segundo representa a coluna.
     */
    private int[] getColunaELinha(String t) {
        int result[] = new int[2];
        try {
            result[0] = Integer.parseInt(t.substring(0, 1));
            result[1] = Integer.parseInt(t.substring(2, 3));
        } catch(NumberFormatException nfe) {
            
        }
        return result;
    }
    
    public void atualizaPontuacao() {
        pontosJogador1.setText(String.valueOf(tabuleiro.pontosJogador1()));
        pontosJogador2.setText(String.valueOf(tabuleiro.pontosJogador2()));
    }
    
    public void atualizaEmbarcacoes() {
        int[] embarcacao = tabuleiro.embarcacoesRestantes();
        totalEmbarc1.setText(String.valueOf(embarcacao[0]));
        totalEmbarc2.setText(String.valueOf(embarcacao[1]));
        totalEmbarc3.setText(String.valueOf(embarcacao[2]));
        totalEmbarc4.setText(String.valueOf(embarcacao[3]));
        totalEmbarc5.setText(String.valueOf(embarcacao[4]));
    }
    
    private void atualizaDados() throws Throwable {
        atualizaEmbarcacoes();
        atualizaPontuacao();
        atualizaTentativas();
        if((rodada%2) == 1) {
            panelJogador1.setBackground(Color.GREEN);
            panelJogador2.setBackground(Color.GRAY);
        } else {
            panelJogador2.setBackground(Color.GREEN);
            panelJogador1.setBackground(Color.GRAY);
        }
        finalizaJogo();
    }
    
    private void finalizaJogo() throws Throwable {
        Jogador vencedor = tabuleiro.vencedor();
        if (vencedor != null) {
            apresentaVencedor(vencedor);
        }
    }
    
    private void apresentaVencedor(Jogador vencedor) throws Throwable {
        JOptionPane.showMessageDialog(null, "O vencedor da rodada é " + vencedor.getNome(), "Vencedor", JOptionPane.INFORMATION_MESSAGE);
        new TelaInicial().setVisible(true);
    }
    
    private void atualizaTentativas() {
        tentativasRestantes.setText(String.valueOf(tentativas));
    }
    private void atribuirJogadas(ArrayList<String> jogadas) {
        for (String a : jogadas) {
            System.out.println(a);
        }
    }
    
    MouseListener listener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JButton btn = (JButton) e.getComponent();
            if (btn.isEnabled()) {
                int[] pos = getColunaELinha(btn.getName());
                int result = 0;
                
                if ((rodada%2) == 1) {
                    result = tabuleiro.acertaBarco(pos[0], pos[1], true);
                } else {
                    result = tabuleiro.acertaBarco(pos[0], pos[1], false);
                }
                tentativas--;
                if (result == TabuleiroService.AGUA) {
                    btn.setText("A");
                } else {
                    btn.setText(String.valueOf(result));
                }
                if (tentativas == 0) {
                    rodada++;
                    tentativas = TelaInicial.TENTATIVAS_TOTAL;
                }
                btn.setEnabled(false);
                try {
                    atualizaDados();
                } catch (Throwable ex) {
                    Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelJogador1 = new javax.swing.JPanel();
        nomeJogador1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pontosJogador1 = new javax.swing.JTextField();
        panelJogador2 = new javax.swing.JPanel();
        nomeJogador2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pontosJogador2 = new javax.swing.JTextField();
        matriz = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        embarc1 = new javax.swing.JLabel();
        totalEmbarc1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        embarc2 = new javax.swing.JLabel();
        totalEmbarc2 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        embarc3 = new javax.swing.JLabel();
        totalEmbarc3 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        embarc4 = new javax.swing.JLabel();
        totalEmbarc4 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        embarc5 = new javax.swing.JLabel();
        totalEmbarc5 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tentativasRestantes = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuMultiPlayer = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Batalha Naval");
        setBackground(new java.awt.Color(102, 153, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(140, 235, 190));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panelJogador1.setBackground(javax.swing.UIManager.getDefaults().getColor("TabRenderer.selectedActivatedBackground"));
        panelJogador1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        nomeJogador1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeJogador1.setText("Jogador 1");

        jLabel3.setText("Pontuação");

        pontosJogador1.setEnabled(false);

        javax.swing.GroupLayout panelJogador1Layout = new javax.swing.GroupLayout(panelJogador1);
        panelJogador1.setLayout(panelJogador1Layout);
        panelJogador1Layout.setHorizontalGroup(
            panelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogador1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pontosJogador1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
            .addComponent(nomeJogador1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelJogador1Layout.setVerticalGroup(
            panelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogador1Layout.createSequentialGroup()
                .addComponent(nomeJogador1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pontosJogador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        panelJogador2.setBackground(javax.swing.UIManager.getDefaults().getColor("TabRenderer.selectedActivatedBackground"));
        panelJogador2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        nomeJogador2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeJogador2.setText("Jogador 2(000.000.000.000)");

        jLabel4.setText("Pontuação");

        pontosJogador2.setEnabled(false);

        javax.swing.GroupLayout panelJogador2Layout = new javax.swing.GroupLayout(panelJogador2);
        panelJogador2.setLayout(panelJogador2Layout);
        panelJogador2Layout.setHorizontalGroup(
            panelJogador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogador2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pontosJogador2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
            .addComponent(nomeJogador2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelJogador2Layout.setVerticalGroup(
            panelJogador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogador2Layout.createSequentialGroup()
                .addComponent(nomeJogador2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelJogador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pontosJogador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelJogador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelJogador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelJogador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelJogador2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        matriz.setBackground(new java.awt.Color(61, 176, 255));
        matriz.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        matriz.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        matriz.setMinimumSize(new java.awt.Dimension(744, 314));
        matriz.setPreferredSize(new java.awt.Dimension(744, 314));
        matriz.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(149, 178, 206));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBackground(new java.awt.Color(149, 178, 206));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Embarcações");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(149, 178, 206));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        embarc1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        embarc1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        embarc1.setText("Embarc1: ");

        totalEmbarc1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        totalEmbarc1.setEnabled(false);
        totalEmbarc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalEmbarc1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(embarc1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalEmbarc1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalEmbarc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(embarc1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(149, 178, 206));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        embarc2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        embarc2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        embarc2.setText("Embarc1: ");

        totalEmbarc2.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        totalEmbarc2.setEnabled(false);
        totalEmbarc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalEmbarc2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(embarc2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalEmbarc2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalEmbarc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(embarc2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(149, 178, 206));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        embarc3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        embarc3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        embarc3.setText("Embarc1: ");

        totalEmbarc3.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        totalEmbarc3.setEnabled(false);
        totalEmbarc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalEmbarc3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(embarc3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalEmbarc3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalEmbarc3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(embarc3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(149, 178, 206));
        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        embarc4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        embarc4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        embarc4.setText("Embarc1: ");

        totalEmbarc4.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        totalEmbarc4.setEnabled(false);
        totalEmbarc4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalEmbarc4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(embarc4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalEmbarc4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalEmbarc4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(embarc4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(149, 178, 206));
        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        embarc5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        embarc5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        embarc5.setText("Embarc1: ");

        totalEmbarc5.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        totalEmbarc5.setEnabled(false);
        totalEmbarc5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalEmbarc5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(embarc5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalEmbarc5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalEmbarc5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(embarc5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(149, 178, 206));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Tentativas");

        tentativasRestantes.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        tentativasRestantes.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tentativasRestantes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addComponent(tentativasRestantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(matriz, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(matriz, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 480));

        jMenu1.setText("Nova Partida");

        jMenuItem1.setText("Single Player");
        jMenu1.add(jMenuItem1);

        menuMultiPlayer.setText("Multi Player");
        menuMultiPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMultiPlayerActionPerformed(evt);
            }
        });
        jMenu1.add(menuMultiPlayer);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sobre");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void totalEmbarc5ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void totalEmbarc4ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void totalEmbarc3ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void totalEmbarc2ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void totalEmbarc1ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void menuMultiPlayerActionPerformed(java.awt.event.ActionEvent evt) {                                                
        
    }                                               

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaInicial().setVisible(true);
                } catch (Throwable ex) {
                    Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel embarc1;
    private javax.swing.JLabel embarc2;
    private javax.swing.JLabel embarc3;
    private javax.swing.JLabel embarc4;
    private javax.swing.JLabel embarc5;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel matriz;
    private javax.swing.JMenuItem menuMultiPlayer;
    private javax.swing.JLabel nomeJogador1;
    private javax.swing.JLabel nomeJogador2;
    private javax.swing.JPanel panelJogador1;
    private javax.swing.JPanel panelJogador2;
    private javax.swing.JTextField pontosJogador1;
    private javax.swing.JTextField pontosJogador2;
    private javax.swing.JTextField tentativasRestantes;
    private javax.swing.JTextField totalEmbarc1;
    private javax.swing.JTextField totalEmbarc2;
    private javax.swing.JTextField totalEmbarc3;
    private javax.swing.JTextField totalEmbarc4;
    private javax.swing.JTextField totalEmbarc5;
    // End of variables declaration//GEN-END:variables
}
