package presentation.mainui;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import businesslogic.userbl.UserManageBLImpl;
import businesslogicservice.logisticsblservice.SearchPkgInformationBLService;
import businesslogicservice.userblservice.UserManageBLService;
import presentation.img.Img;

/**
 * @author 这菜咸了
 * 系统总main函数
 */
public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = -9098438670455829981L;
	public static final int w = 512;
	public static final int h = 384;
	
	
	private int state;//0表示主，1表示查询订单，2表示登陆
	private int stated;//以前的状态
	boolean changed;
	public void setState(int x){
		state=x;
	}
	public int getState(){
		return state;
	}
	public void setStated(int x){
		stated=x;
	}
	public void setChanged(boolean x){
		changed=x;
	}
	JPanel j;
	CardLayout card;
	
	MainPanel mainpanel;
	SearchPanel searchpanel;
	LoginPanel loginpanel;
	
	

	SearchPkgInformationBLService bl1;
	UserManageBLService bl2;

	private boolean isDraging;//是否被拖住
	private int xx;
	private int yy;
	
	public MainFrame(){
		this.setUndecorated(true);
		this.addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent e) { 
				 isDraging = true; 
				 xx = e.getX(); 
				 yy = e.getY(); 
			}

			public void mouseReleased(MouseEvent e) { 
				 isDraging = false; 
			}
		});
		
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) { 
			if (isDraging) { 
				 int left = getLocation().x; 
				 int top = getLocation().y; 
				 setLocation(left + e.getX() - xx, top + e.getY() - yy); 
			}
			}
		});
		this.setSize(w,h);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		state=0;
		stated=0;
		changed=false;
		isDraging=false;
		
		card=new CardLayout();
		j = new JPanel();
        j.setLayout(card);
        add(j);
		
		this.setIconImage(Img.MainICON);
		

		//bl1 = new SearchPkgInformationBLImpl();
		bl1 = null;
		//bl2 = new UserManageBLImpl();
		bl2 = null;
		mainpanel = new MainPanel(this);
		searchpanel = new SearchPanel(this, bl1);
		loginpanel = new LoginPanel(this, bl2);
		j.add(mainpanel);
		j.add(searchpanel);
		j.add(loginpanel);
		
		new Thread(new Runnable(){
			public void run() {
				while(true){
					if(changed){
						changed=false;
						int a;
						if(state-stated>0)
							a=state-stated;
						else
							a=state+3-stated;
						for(int i=0;i<a;i++)
							card.next(j);
						
					}
				}
			}
		}).start();
	}
}