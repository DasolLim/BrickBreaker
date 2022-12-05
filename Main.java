//import JFrame components
import javax.swing.JFrame;

public class Main{
    public static void main(String args[]) {
        //JFrame object instantiation
        JFrame obj = new JFrame();
        //Gameplay class instantiation
        Gameplay gamePlay = new Gameplay();

        /** Display setting for the game JFrame Panel **/
        obj.setBounds(10,10,700,600);
        obj.setTitle("Breakout Ball");
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setResizable(false);
        obj.setVisible(true);
        //allowing users to move around the game panel
        obj.setLocationRelativeTo(null);
        //adding gamePlay
        obj.add(gamePlay);
    }

}
