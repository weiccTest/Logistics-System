package presentation.centerclerkui;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import presentation.img.Img;
import presentation.mainui.CurrentUser;

public class CenterClerkFrame extends JFrame{
	
	private static final long serialVersionUID = 4881080784503653011L;
	public static final int w = 1024;
	public static final int h = 768;
	
	
	private CurrentUser currentUser;
	private int state;
	private int stated;
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

	//面板对象
	TransferCenterReceive transferCenterReceive;//中转接收
	TransShipment transShipment;//装运管理
	InputRepertory inputRepertory;//入库管理
	
	

//	AccountBLService accountBLService;
//	BaseDataSettingBLService baseDataSettingBLService;
//	CostManagementBLService costManagementBLService;
//	SettlementManageBLService settlementManageBLService;

	private boolean isDraging;//是否被拖住
	private int xx;
	private int yy;
	
	public CenterClerkFrame(){
		//this.currentUser=currentUser;
		this.currentUser=new CurrentUser("王大锤");
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
		state=1;
		stated=1;
		changed=false;
		isDraging=false;
		
		card=new CardLayout();
		j = new JPanel();
        j.setLayout(card);
        add(j);
		
		this.setIconImage(Img.CenterClerkICON);
		

//		accountBLService=null;
//		baseDataSettingBLService=null;
//		costManagementBLService=null;
//		settlementManageBLService=null;

		transferCenterReceive=new TransferCenterReceive(this, currentUser);
		transShipment=new TransShipment(this, currentUser);
		inputRepertory=new InputRepertory(this, currentUser);

		j.add(transferCenterReceive);
		j.add(transShipment);
		j.add(inputRepertory);
		
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