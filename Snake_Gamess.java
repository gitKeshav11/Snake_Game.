import javax.swing.*;

public class Snake_Gamess extends JFrame {


    Snake_Gamess(){
         super("@Snake_Game");
         add(new Snakess ());
         pack();
//         setSize(600,600);
         setLocationRelativeTo(null);
         setResizable(false);
         setVisible(true);
    }

    public static void main(String[] args){
    new Snake_Gamess();
    }

}
