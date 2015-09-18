package sd.programas_sd;

import java.applet.Applet;
import java.awt.*;

public class HelloWorld extends Applet
{ Font f = new Font("TimesRoman", Font.BOLD, 32);

  public void paint(Graphics g)
  { g.setFont(f);
    g.setColor(Color.red);
    g.drawString("Hello World!", 5, 40);
  }

}

