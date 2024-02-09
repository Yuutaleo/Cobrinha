import javax.swing.*;

public class CobrinhaFrame extends JFrame {

    CobrinhaFrame(){

        this.add(new CobrinhaPainel());
        this.setTitle("Cobrinha");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
