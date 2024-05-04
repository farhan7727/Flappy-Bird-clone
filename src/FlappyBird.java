import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class FlappyBird extends JPanel implements ActionListener,KeyListener
{
    static int HEIGHT=640,WIDTH=360;

    Image background;
    Image DnP;
    Image UpP;
    Image bird;

    int birdX=WIDTH/8;
    int birdY=HEIGHT/2;
    int bheight=70;
    int bwidth=70;
    Image img;

    //BIRD PLACEMENT
    class Bird
    {
        int X=birdX;
        int Y=birdY;
        int height=bheight;
        int width=bwidth;
        Image img;

        Bird(Image img)
        {
            this.img=img;
        }
    }
    
    // LOGIC
    Bird brd;
    Timer gloop;
    int velocityX=-4; //for pipe
    int velocityY=0; //for bird
    int gravity=1;


    //PiPES
    Timer addpipe;
    int pipex=WIDTH;
    int pipey=0;
    int pwidth=70;
    int pheigth=600;

    class Pipe
    {
        int x =pipex;
        int y=pipey;
        int width=pwidth;
        int height=pheigth;
        Image img;
        boolean pass=false;

        Pipe(Image img)
        {
            this.img=img;
        }
    }

    ArrayList<Pipe>pipes;
    Random random = new Random();
    boolean gameover=false;
    double score=0;

    FlappyBird()
    {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.BLUE);
        addKeyListener(this);

        background = new ImageIcon(getClass().getResource("./assets/BG.png")).getImage();
        UpP=new ImageIcon(getClass().getResource("./assets/UpPipeFin.png")).getImage();
        DnP=new ImageIcon(getClass().getResource("./assets/DownPipeFin.png")).getImage();
        bird=new ImageIcon(getClass().getResource("./assets/bird.png")).getImage();
    
        brd = new Bird(bird);
        pipes = new ArrayList<Pipe>();

        JOptionPane.showMessageDialog(null, "START"); 
            

        addpipe = new Timer(1600,new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {addPipes();}
        });
        addpipe.start();

        //timer
        gloop=new Timer(1000/60, this);
        gloop.start(); 
    }

    public void addPipes()
    {
        int randomPipeY=(int)(pipey-pheigth/4-(Math.random()*(pheigth/2)));
        int openSpace= HEIGHT/4;

        Pipe downPipe =new Pipe(DnP);
        downPipe.y=randomPipeY;
        pipes.add(downPipe);
        
        Pipe upPipe=new Pipe(UpP);
        upPipe.y= downPipe.y+openSpace+pheigth;
        pipes.add(upPipe); 

    }

    public boolean collision(Bird a, Pipe b)
    {
         return a.X < b.x + b.width &&   
               a.X + a.width-17 > b.x &&   // fine tuning the bird assets due to the unaccurate collisions
               a.Y < b.y + b.height-20 &&  
               a.Y + a.height-20 > b.y;   
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        g.drawImage(background, 0, 0, null);
        g.drawImage(brd.img, brd.X , brd.Y, brd.height,brd.width,null);
        
        for (int i = 0; i < pipes.size(); i++) 
        {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x ,pipe.y,pipe.width,pipe.height,null);
        }
    }
    public void move()
    {
        velocityY+=gravity;

        brd.Y+=velocityY;
        brd.Y=Math.max(brd.Y, 0);
        for (int i = 0; i < pipes.size(); i++) 
        {
            Pipe pipe = pipes.get(i);
            pipe.x+=velocityX;

            if (!pipe.pass && brd.X>pipe.x+pipe.width) 
            {
                pipe.pass=true;
                score+=0.5; //adding 0.5 for both the pipes   
            }

            if (collision(brd, pipe)) 
            {
                gameover=true;    
            }
        }
        if (brd.Y>HEIGHT) 
        {
            gameover=true;
        }
    }
    public void actionPerformed(ActionEvent e) 
    {
        move();
        repaint(); //it calls the paintComponent
        if (gameover==true)
        {
            gloop.stop();
            addpipe.stop();
            JOptionPane.showMessageDialog(null, "GAMEOVER!\nYour score is = "+(int)score); 

            System.exit(ABORT);
        }
    
    }
    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            velocityY=-9;
        }
    }

    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    
}