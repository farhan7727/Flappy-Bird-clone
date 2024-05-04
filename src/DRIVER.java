import javax.swing.JFrame;

public class DRIVER 
{
    public static void main(String[] args) throws Exception
    {
        int HEIGHT=640,WIDTH=360;

        JFrame frame = new JFrame("Flappy Bird Game");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(WIDTH,HEIGHT);
        // frame.setVisible(true);
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);

        frame.pack();  //removes the tile bar from account
        frame.setLocationRelativeTo(null);
        flappyBird.requestFocus(true);
        frame.setVisible(true);
    }
}
