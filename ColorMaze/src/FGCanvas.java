import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.ref.*;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStore;

public class FGCanvas extends GameCanvas implements Runnable, CommandListener{
	/****************设置界面********************/
	private Form      modelform;    
    private Form      soundform;
    private Form      levelform;
    private Form      rankform;
	private boolean   modelstate;
	private boolean   soundstate;
	//private boolean   levelstate;
//	private boolean   rankstate;
	Command cmdBack = new Command("Back", Command.BACK, 0);
	Command cmdNext = new Command("Next", Command.SCREEN, 0);
	
	Command cmdBackModel = new Command("BackModel", Command.BACK, 0);
	Command cmdBackSound = new Command("BackSound", Command.BACK, 0);
	Command cmdBackLevel = new Command("BackLevel", Command.BACK, 0);
	Command cmdBackRank = new Command("BackRank", Command.BACK, 0);
	
	
	
	private int       gametime;
	private boolean   chmodel;//挑战模式
	private int       frametime;
	
	private ChoiceGroup modelcg = new ChoiceGroup("选择一种游戏模式",ChoiceGroup.EXCLUSIVE);
	private ChoiceGroup soundcg = new ChoiceGroup("是否打开音乐",ChoiceGroup.EXCLUSIVE);
	private ChoiceGroup levelcg = new ChoiceGroup("选择游戏难度",ChoiceGroup.EXCLUSIVE);
	//private ChoiceGroup rankcg = new ChoiceGroup("最高分纪录",ChoiceGroup.EXCLUSIVE);
	
	
	/****************游戏初始界面********************/	
	
	private Sprite    initial_bgSprite;
	private Sprite    startSprite;
	private Sprite    modelSprite;
	private Sprite    soundSprite;
	private Sprite    levelSprite;
	private Sprite    rankSprite;
	private Sprite    quitSprite;
	private Sprite    strawSprite;
	private int       choicenum;
//	private int strawx;
//	private int strawy;
	/****************游戏界面********************/
	private Image     gamebg;
	private Sprite    dropSprite[] = new Sprite[16];
	private Sprite    gstrawSprite;//控制滴管的精灵
	private Image     dropImg;
//	private Sprite    control;
	private int[]     state = new int[16];
	private int       row;
	private int       col;
	private int       stateValue;
	private int[]     translate = {0xc800, 0xe400, 0x7200, 0x3100, 0x8c80, 0x4e40, 0x2720, 0x1310,
			 					0x08c8, 0x04e4, 0x0272, 0x0131, 0x008c, 0x004e, 0x0027, 0x0013};
	private Random    strnd;
	private int       lcount;
	//打表列举好100种可能的迷宫数
	private int[]     nextlevel = {56922, 56852, 56725, 38948, 38989,
			                       54025, 54409, 54774, 54814, 54903, 54965, 55004, 
			                       55203, 55242, 55899, 56294, 56984, 57073, 57125, 
			                       57164, 57362, 57709, 57902, 57927, 58168, 58193, 
			                       58259, 58362, 59012, 59193, 60072, 60097, 60181, 
			                       60284, 61035, 61398, 61455, 61542, 61721, 61808, 
			                       62042, 62245, 62629, 63625, 63712, 28349, 28372, 
			                       28384, 28468, 28509, 28587, 28610, 28804, 29742, 
			                       29767, 30008, 30033, 30226, 30573, 30827, 31900, 
			                       31912, 31937, 31989, 32138, 32227, 32457, 32694, 
			                       32841, 33078, 33308, 33397, 33546, 33598, 33623, 
			                       33635, 34708, 34962, 35309, 35502, 35527, 35768, 
			                       35793, 36731, 36925, 36948, 37026, 37067, 37151, 37163};
	
	
	private Graphics  gra;
//	private int       flag;//控制进程进行时间
	
	private boolean   initial_state;
	private boolean   game_state;
//	private int       composition;//初始化界面的状态
//	private int       rear;//控制queue的元素读入；
//	private int       front;
	
	private Display   dis;


	
	private boolean gameover;
	private boolean victory;
	private boolean sleeping;
	
	//背景音乐
    private Player        musicPlayer;
	private Player        kickPlayer;
    private Player        victoryPlayer;
    private Player        gameoverPlayer;
    
    //纪录最高分
    private int[]         hiScores = new int[5];
    private int           score;
	
	public FGCanvas(Display d){
		super(true);
		try{
			gamebg = Image.createImage("/game/gameMainUI.png");
			dropImg = Image.createImage("/game/drop.png");
			gstrawSprite = new Sprite(Image.createImage("/game/straw.png"));
	//		welcomeSprite = new Sprite(Image.createImage("/game/welcome.png"));
			
			initial_bgSprite = new Sprite(Image.createImage("/game/menu.png"));
			
			startSprite = new Sprite(Image.createImage("/game/start.png"), 37, 47);
			modelSprite = new Sprite(Image.createImage("/game/model.png"), 46, 40);
			soundSprite = new Sprite(Image.createImage("/game/sound.png"), 46, 36);
			levelSprite = new Sprite(Image.createImage("/game/level.png"), 46, 40);
			rankSprite = new Sprite(Image.createImage("/game/rank.png"), 40, 47);
			quitSprite = new Sprite(Image.createImage("/game/quit.png"));
			strawSprite = new Sprite(Image.createImage("/game/straw.png"));
		}
		catch(IOException ie){
			System.out.println("背景加载错误！");
		}
		/****************欢迎界面初始化********************/
		
		/****************游戏初始界面初始化********************/
		startSprite.setPosition(this.getWidth() * 1 / 6, this.getHeight() * 1 / 8 );
		modelSprite.setPosition(this.getWidth() * 1 / 3, this.getHeight() * 1 / 4 );
		soundSprite.setPosition(this.getWidth() * 13 / 36, (int)this.getHeight() * 9 / 20 );
		levelSprite.setPosition(this.getWidth() * 5 / 24, this.getHeight() * 17 / 29 );
		rankSprite.setPosition(this.getWidth() * 1 / 30, this.getHeight() * 7 / 10 );
		quitSprite.setPosition(this.getWidth() * 5 / 6, this.getHeight() * 6 / 7 );
		strawSprite.setPosition(startSprite.getX() + strawSprite.getWidth() / 2,  startSprite.getY() - strawSprite.getHeight() * 5 / 6);
		
		choicenum = 1;
		
		/****************游戏界面初始化********************/
		
		for(int i = 0; i < 16; i++)
			dropSprite[i] = new Sprite(dropImg, 40, 40);
		strnd = new Random(System.currentTimeMillis());
		lcount = 0;
		stateValue = nextlevel[lcount];

		int tempstate = stateValue;
	//	System.out.println(Integer.toString(stateValue));
		for(int i = 0; i < 16; i++){
			
			state[15 - i] = tempstate % 2;
			tempstate /= 2;
		//	System.out.println("state" + "[" + Integer.toString(15 - i) +  "]" + Integer.toString(state[15 - i]));
			}
		
		row = 0;
		col = 0;
	//	get_answer(stateValue);

		initial_state = true;//开始时只显示初始化界面，而不显示游戏界面
		game_state = false;
		
		gstrawSprite.setPosition(51, 17 - gstrawSprite.getHeight() / 2);
		
		modelform = new Form("");
		soundform = new Form("");
		levelform = new Form("");
		rankform  = new Form("最高分纪录：");
		modelstate = false;
		
		gameover = false;
		victory = false;
		
		chmodel = false;
		frametime = 0;
		gametime = 20;
		
//		 初始化音乐播放器
	    try {
	      InputStream is = getClass().getResourceAsStream("/music.mid");
	      musicPlayer = Manager.createPlayer(is, "audio/midi");
	      musicPlayer.prefetch();
	      musicPlayer.setLoopCount(-1);
	      is = getClass().getResourceAsStream("/kick.wav");
	      kickPlayer = Manager.createPlayer(is, "audio/X-wav");
	      kickPlayer.prefetch();
	      is = getClass().getResourceAsStream("victory.wav");
	      victoryPlayer = Manager.createPlayer(is, "audio/X-wav");
	      victoryPlayer.prefetch();
	      is = getClass().getResourceAsStream("/gameover.wav");
	      gameoverPlayer = Manager.createPlayer(is, "audio/X-wav");
	      gameoverPlayer.prefetch();
	    }catch (IOException ioe) {
	    }catch (MediaException me) {}
		
		sleeping = false;
		soundstate = false;
		dis = d;
		
		for(int i = 0; i < 5; i++)
			hiScores[i] = 0;
		
			
	}
	public void start(){
			dis.setCurrent(this);
			gra = this.getGraphics();
			initial_ui();
			this.flushGraphics();
			new Thread(this).start();		
	
		
//				播放音乐
				try {
				      musicPlayer.setMediaTime(0);
				      musicPlayer.start();
				    }catch (MediaException me) {}
			
			
			//读取最高分
			readHiScores();
	}
			
	
	public void stop(){
		
		musicPlayer.close();
	    kickPlayer.close();
	    victoryPlayer.close();
	    gameoverPlayer.close();
	    sleeping = true;
	    
	    //写最高分
	    writeHiScores();
	}
	

	public void run(){
		while(!sleeping){
				update();				
				if(initial_state == true)
					initial_ui();
				if(game_state == true)
					draw();
			try
			{
				Thread.currentThread().sleep(100);
			}
			catch(Exception ie){				
				}	
		}		
	}
	public void initial_ui(){
			
		//	initial_state = true;
			gameover = false;
			chmodel = false;
			victory = false;
			initial_bgSprite.paint(gra);
			startSprite.paint(gra);
			modelSprite.paint(gra);
			soundSprite.paint(gra);
			levelSprite.paint(gra);
			rankSprite.paint(gra);
			quitSprite.paint(gra);
			strawSprite.paint(gra);
	
		    soundstate = true;
		
//		等级控制
		if(levelcg.getSelectedIndex() == 0)
			gametime = 30;
		else if(levelcg.getSelectedIndex() == 1)
			gametime = 25;
		else if(levelcg.getSelectedIndex() == 2)
			gametime = 15;
		
			
			this.flushGraphics();		
		}


	public void update(){
		if(gameover)
		{
			int keystate = this.getKeyStates();
			if((keystate & this.FIRE_PRESSED) != 0)
				{
			    initial_ui();	
			    gameover = false;
			    game_state = false;
			    initial_state = true;
			    gametime = 25;
				}			
			return;		
		}
		//声音控制
		if(soundcg.getSelectedIndex() == 0)
			soundstate = true;
		else if(soundcg.getSelectedIndex() == 1)
			{
			musicPlayer.close();
			soundstate = false;
			}
		
		int keystate = this.getKeyStates();
		if(modelcg.getSelectedIndex() == 1)
		{
		//	stateValue = nextlevel[lcount];
			chmodel = true;
			if(frametime >= 10)
				{
				frametime = 0;
				gametime--;
				}
			frametime++;
			if(gametime == 0)
				{
				
				if(soundstate && gameover == false){
				 try {
				      gameoverPlayer.setMediaTime(0);
				      gameoverPlayer.start();
				    }catch (MediaException me) {}
				    }
				gameover = true;
			//	game_state = false;
				//initial_state = true;
				}
		
			if(stateValue == 65535)
			{
				if(soundstate){
				 try {
				      victoryPlayer.setMediaTime(0);
				      victoryPlayer.start();
				    }catch (MediaException me) {}
				}
				
				score = gametime * 100;
				//更新最高分
				updateHiScores();
				lcount++;
							if(lcount == nextlevel.length)
								lcount = 0;
							stateValue = nextlevel[lcount];
				gameover = true;
				victory = true;
				
				System.out.println("Victory!!");
         }
		}
		if(modelcg.getSelectedIndex() == 0)
		{
			if(stateValue == 65535)
			{
				lcount++;
				if(lcount == nextlevel.length)
					lcount = 0;
				stateValue = nextlevel[lcount];
				
			}
			}
		if((keystate & this.LEFT_PRESSED) != 0){
			col--;
			if(col < 0)
				col = 0;
			else
				gstrawSprite.move(-50, 0);

			
		}
		else if((keystate & this.RIGHT_PRESSED) != 0){
			col++;
			if(col > 3)
				col = 3;
			else
				gstrawSprite.move(50, 0);

		}
		else if((keystate & this.UP_PRESSED) != 0){
			if(initial_state == true){
				choicenum--;
				if(choicenum <= 1)
					choicenum = 1;
				setstrawposition(choicenum);
			}
			if(game_state == true){
				row--;
				if(row < 0)
					row = 0;
				else
					gstrawSprite.move(0, -50);
				}
		}
		else if((keystate & this.DOWN_PRESSED) != 0){
			if(initial_state == true){
				choicenum++;
				if(choicenum >= 5)
					choicenum = 5;
				setstrawposition(choicenum);
			}
			if(game_state == true){
				row++;
				if(row > 3)
					row = 3;
				else
					gstrawSprite.move(0, 50);
			}
		}
		else if((keystate & this.FIRE_PRESSED) != 0){
			int num = row * 4 + col;
			if(soundstate && game_state){ 
			try {
			      kickPlayer.setMediaTime(0);
			      kickPlayer.start();
			    }catch (MediaException me) {}
			}
			stateValue = stateValue ^ translate[num];
		//	System.out.println(Integer.toString(stateValue));

			if(initial_state == true && choicenum == 1){
				game_state = true;
				initial_state = false;
	//			dis.setCurrent(cmdForm);
				this.addCommand(cmdBack);
				this.addCommand(cmdNext);
				
				this.setCommandListener(this);						
			}
			if(initial_state == true && choicenum == 2){
				initial_state = false;
				modelstate = true;
				dis.setCurrent(modelform);
				modelform.addCommand(cmdBackModel);
				
				modelform.append(modelcg);	
				if(modelcg.size() == 0)
				{
					modelcg.append("Training", null);					
					modelcg.append("Challenge", null);
				}
				
				modelform.setCommandListener(this);						
			}		
			if(initial_state == true && choicenum == 3){
				initial_state = false;
				dis.setCurrent(soundform);
				soundform.addCommand(cmdBackSound);				
				soundform.append(soundcg);	
				if(soundcg.size() == 0)
				{
					soundcg.append("YES", null);					
					soundcg.append("No", null);
				}
				
				soundform.setCommandListener(this);						
			}		
			if(initial_state == true && choicenum == 4){
				initial_state = false;
				dis.setCurrent(levelform);
				levelform.addCommand(cmdBackLevel);				
				levelform.append(levelcg);	
				if(levelcg.size() == 0)
				{
					levelcg.append("Easy", null);					
					levelcg.append("Medium", null);
					levelcg.append("Hard", null);
				}
				
				levelform.setCommandListener(this);						
			}	
			if(initial_state == true && choicenum == 5){
				initial_state = false;
				dis.setCurrent(rankform);
				rankform.addCommand(cmdBackRank);				
					
				for(int i = 0; i < 5; i++)
					rankform.append(Integer.toString(hiScores[i]));
				
				rankform.setCommandListener(this);						
			}	
		}	
	
	}
	public void setstrawposition(int num){
		int strawx = 0;
		int strawy = 0;
//		switch(num){
	//	case 1:
		if(num <= 1){
			num = 1;
			strawx = startSprite.getX() + strawSprite.getWidth() / 2;
			strawy = startSprite.getY() - strawSprite.getHeight() * 5 / 6;
		}
		//	break;
	//	case 2:
		if(num == 2){
			strawx = modelSprite.getX() + strawSprite.getWidth() / 2;
			strawy = modelSprite.getY() - strawSprite.getHeight() * 5 / 6;
			//break;
		}
	//	case 3:
		if(num == 3)
			{strawx = soundSprite.getX() + strawSprite.getWidth() / 2;
			strawy = soundSprite.getY() - strawSprite.getHeight() * 5 / 6;
			}
		if(num == 4){
			strawx = levelSprite.getX() + strawSprite.getWidth() / 2;
			strawy = levelSprite.getY() - strawSprite.getHeight() * 5 / 6;
		}
		if(num >= 5){
			num = 5;
			strawx = rankSprite.getX() + strawSprite.getWidth() / 2;
			strawy = rankSprite.getY() - strawSprite.getHeight() * 5 / 6;
		}
		strawSprite.setPosition(strawx, strawy);
		
	}
	public void draw(){
		gra.setColor(255, 255, 255);
		gra.fillRect(0, 0, this.getWidth(), this.getHeight());
		gra.drawImage(gamebg, 0, 0, Graphics.TOP | Graphics.LEFT);
		
	
		int tempstate = stateValue;
		for(int i = 0; i < 16; i++){	
			state[15 - i] = tempstate % 2;
			tempstate /= 2;
			}
		for(int i = 0; i < 16; i++){
			int r = i / 4;
			int c = i % 4;
//			System.out.println("state" + "[" + Integer.toString(i) +  "]" + Integer.toString(state[i]));
			
			dropSprite[i].setFrame(state[i]);
			dropSprite[i].setPosition(30 + c * 48, 56 + r * 45);
			dropSprite[i].paint(gra);
		}
			
		gstrawSprite.paint(gra);
		
		if(chmodel)//挑战模式下的界面显示
		{
			gra.setColor(0, 0, 255);
			gra.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
			gra.drawString("挑战时间：" + Integer.toString(gametime), 180, 20, Graphics.TOP | Graphics.HCENTER);
			gra.setColor(255, 255, 255);
			if(gameover && gametime == 0)
			{
				gra.drawString("挑战失败", 140, 140, Graphics.TOP | Graphics.HCENTER);
			}	
			if(gameover && victory == true)
			{
				gra.drawString("恭喜你挑战成功", 140, 140, Graphics.TOP | Graphics.HCENTER);
				gra.drawString("您的分数"+ Integer.toString(score), 160, 160, Graphics.TOP | Graphics.HCENTER);
			}
		}
		this.flushGraphics();
		
	}
	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub
		if(c.getLabel() == "Exit"){
		}
		
		if(c.getLabel() == "Back"){
			game_state = false;
			initial_state = true;
	//		modelform.deleteAll();
			dis.setCurrent(this);
		//	initial_ui();
			this.removeCommand(cmdBack);
			this.removeCommand(cmdNext);
		}
		if(c.getLabel() == "BackModel"){
			game_state = false;
			initial_state = true;
			modelform.deleteAll();
			dis.setCurrent(this);
		//	initial_ui();
			this.removeCommand(cmdBackModel);
		}
		
		if(c.getLabel() == "BackSound"){
			game_state = false;
			initial_state = true;
			soundform.deleteAll();
			dis.setCurrent(this);
		//	initial_ui();
			this.removeCommand(cmdBackSound);
		}
		if(c.getLabel() == "BackLevel"){
			game_state = false;
			initial_state = true;
			levelform.deleteAll();
			dis.setCurrent(this);
		//	initial_ui();
			this.removeCommand(cmdBackLevel);
		}
		
		if(c.getLabel() == "BackRank"){
			game_state = false;
			initial_state = true;
			rankform.deleteAll();
			dis.setCurrent(this);
		//	initial_ui();
			this.removeCommand(cmdBackRank);
		}
		if(c.getLabel() == "Next"){
			lcount++;
			if(lcount == nextlevel.length)
				lcount = 0;
			stateValue = nextlevel[lcount];
		}		
	}
	
	private void updateHiScores() {
	    // See whether the current score made the hi score list
	    int i;
	    for (i = 0; i < 5; i++)
	      if (score > hiScores[i])
	        break;

	    // Insert the current score into the hi score list
	    if (i < 5) {
	      for (int j = 4; j > i; j--) {
	        hiScores[j] = hiScores[j - 1];
	      }
	      hiScores[i] = score;
	    }
	  }

	  private void readHiScores()
	  {
	    // Open the hi scores record store
	    RecordStore rs = null;
	    try {
	      rs = RecordStore.openRecordStore("HiScores", false);
	    }
	    catch (Exception e) {
	    }

	    if (rs != null) {
	      // Read the hi score records
	      try {
	        int    len;
	        byte[] recordData = new byte[8];

	        for (int i = 1; i <= rs.getNumRecords(); i++) {
	          // Re-allocate record holder if necessary
	          if (rs.getRecordSize(i) > recordData.length)
	            recordData = new byte[rs.getRecordSize(i)];

	          // Read the score and store it in the hi score array
	          len = rs.getRecord(i, recordData, 0);
	          hiScores[i - 1] = (Integer.parseInt(new String(recordData, 0, len)));
	        }
	      }
	      catch (Exception e) {
	        System.err.println("Failed reading hi scores!");
	      }

	      // Close the record store
	      try {
	        rs.closeRecordStore();
	      }
	      catch (Exception e) {
	        System.err.println("Failed closing hi score record store!");
	      }
	    }
	    else {
	      // The record store doesn't exist, so initialize the scores to 0
	      for (int i = 0; i < 5; i++)
	        hiScores[i] = 0;
	    }
	  }

	  private void writeHiScores()
	  {
	    // Delete the previous hi scores record store
	    try {
	      RecordStore.deleteRecordStore("HiScores");
	    }
	    catch (Exception e) {
	    }

	    // Create the new hi scores record store
	    RecordStore rs = null;
	    try {
	      rs = RecordStore.openRecordStore("HiScores", true);
	    }
	    catch (Exception e) {
	      System.err.println("Failed creating hi score record store!");
	    }

	    // Write the scores
	    for (int i = 0; i < 5; i++) {
	      // Format each score for writing
	      byte[] recordData = Integer.toString(hiScores[i]).getBytes();

	      try {
	        // Write the score as a record
	        rs.addRecord(recordData, 0, recordData.length);
	      }
	      catch (Exception e) {
	        System.err.println("Failed writing hi scores!");
	      }
	    }

	    // Close the record store
	    try {
	      rs.closeRecordStore();
	    }
	    catch (Exception e) {
	      System.err.println("Failed closing hi score record store!");
	    }
	  }
}
