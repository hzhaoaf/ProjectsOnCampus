import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;
public class FlipGameMIDlet extends MIDlet implements CommandListener{
	FGCanvas          gamecanvas;
//	welcomeCanvas     welcomecanvas;
	
	public FlipGameMIDlet() {
		super();
		// TODO Auto-generated constructor stub
		if(gamecanvas == null)
		{
			gamecanvas = new FGCanvas(Display.getDisplay(this));
//			Command exitcmd = new Command("Exit", Command.EXIT, 0);
//			Command tipcmd = new Command("tips", Command.SCREEN, 0);
			
//			gamecanvas.addCommand(exitcmd);
//			gamecanvas.addCommand(tipcmd);
			
//			gamecanvas.setCommandListener(this);					
		}
	}
	

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
		
		gamecanvas.start();
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		if(c.getLabel() == "Exit"){
			destroyApp(true);
			notifyDestroyed();			
		}		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void destroyApp(boolean arg0)  {
		// TODO Auto-generated method stub

	}

}
