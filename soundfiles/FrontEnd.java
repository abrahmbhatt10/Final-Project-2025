import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    I took the source file FrontEnd.java from last year's final project as a starting point.
 */

public class FrontEnd extends JFrame implements ActionListener {
    // Instance Variables
    public static int SCREEN_MAX = 1000;
    public static int SCREEN_WIDTH = SCREEN_MAX;
    public static int SCREEN_HEIGHT = SCREEN_MAX;
    public static int SCREEN_XOFFSET = 100;
    public static int SCREEN_YOFFSET = 100;
    MusicSelection gsWindow;
    double dx;
    double x;
    int originX;
    int originY;

    // Constructor
    public FrontEnd() {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setTitle("Graph ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.dx = 0.0001;
        this.originX = (int) SCREEN_WIDTH/2;
        this.originY = (int) SCREEN_HEIGHT/2;
        JButton bGoBack = new JButton("Back To Graph Selection");
        bGoBack.setBounds(10,10,200,30);
        bGoBack.addActionListener(this);
        add(bGoBack);
    }

    // Returns SCREEN_MAX value used to set start and end range for MathFunctions
    public static int getScreenMax() {
        return SCREEN_MAX;
    }

    // Sets the Gs window
    public void setGsWindow(MusicSelection gsWindow) {
        this.gsWindow = gsWindow;
    }

    // Paints a function on the front end
    public void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.drawRect(0,0,SCREEN_HEIGHT,SCREEN_WIDTH);
        g.fillRect(0,0,SCREEN_HEIGHT,SCREEN_WIDTH);
        g.setColor(Color.BLACK);
        /*
        if(f == null)
        {
            return;
        }
        // Drawing Y-axis
        f.drawYAxis(g);

        // Drawing X-axis
        f.drawXAxis(g);

        //Drawing Units
        f.drawUnits(g);



        // Displaying function value and derivative value for x input value given by user
        g.setColor(Color.BLACK);
        g.drawString("       For x value = " + f.getInputX(),SCREEN_WIDTH - 250, SCREEN_YOFFSET);
        g.setColor(Color.BLUE);
        g.drawString("  Function value = " + f.calcFunction(f.getInputX()),SCREEN_WIDTH - 250, SCREEN_YOFFSET+30);
        g.setColor(Color.RED);
        g.drawString("Derivative value = " + f.getDerivative(f.getInputX()), SCREEN_WIDTH - 250, SCREEN_YOFFSET + 60);

        // Displays area of fucntion f
        if(f.isDisplayArea())
        {
            f.paintArea(g);
        }
        for(int j = 0; j < 2; j++)
        {
            x = 0;
            if(j == 1)
            {
                // Drawing derivative
                f.paintDerivative(g);
            }
            else
            {
                // Drawing function
                f.paintFunction(g);
            }
        }

         */
        g.setColor(Color.BLACK);
    }
/*

    // Sets function f to a function inputted
    public void setF(MathFunction f) {
        this.f = f;
    }
 */

    // Gets Screen width
    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    // Gets the screen hiehgt
    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    // Gets the screen x offset value
    public static int getScreenXoffset() {
        return SCREEN_XOFFSET;
    }

    // Gets the screen y offset value
    public static int getScreenYoffset() {
        return SCREEN_YOFFSET;
    }

    // Gets the origin of x
    public int getOriginX() {
        return originX;
    }

    // Gets the origin of y
    public int getOriginY() {
        return originY;
    }

    // Performs action of action event e
    public void actionPerformed(ActionEvent e){
        if(gsWindow == null)
        {
            gsWindow = new MusicSelection();
        }
        gsWindow.setVisible(true);
        setVisible(false);
    }
}
