package mychess.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mychess.function.Commication;
import mychess.function.Internet;
import mychess.function.Redo;
import mychess.function.Replay;
import mychess.util.Common;
import mychess.util.HasFinished;
import mychess.util.JudgeMove;


public class ChessBoard extends JPanel implements MouseListener,Runnable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	private int col;//����������������
	private int row;//����������������
	private boolean tip;//�Ƿ��ػ��ʱ�������ʾ��Ҳ���������ʱ���б�ǣ�
	private boolean isSelected;//�����Ƿ�ѡ�У�������һ��������ƶ����ӻ�����ѡ������
	private int precol;//��һ��������������
	private int prerow;//��һ��������������
	private boolean yourTurn;//�ǲ��ǵ���Ļغ�
	private int[][] data;//��ǰ��ֵ�״̬����,�����ػ�
	private Image[] pics;//���������ͼƬ
	private Redo rd;//�������������б�
	private Internet internet;//���ݷ���������
	private boolean observer;//�Ƿ��ǹ۲��߽�ɫ
	private boolean isRed;//�Ƿ��Ǻ췽
	private boolean isRedo;//�Ƿ����
	private Commication cc;//��Ϣ����������
	private byte state=0;//״̬0��ʾ����, 1��ʾ׼��,2��ʾ��Ϸ��ʼ,3��ʾ��Ϸ������,4��ʾ��Ϸ����
	
	public ChessBoard(Image[] pics,Redo rd) {
		// TODO Auto-generated constructor stub
		this.pics=pics;
		this.rd=rd;
		internet=new Internet();//�������ݷ�������������Ϣ��������Ҫ�ֶ���ǰ����
		//�жϽ�ɫ�ͻ�ȡ��ֳ�ʼ״̬
		String message=internet.readMessage();
		String message_array=internet.readMessage();
		data=Common.String_to_Array(message_array);
		rd.add_one_step(data);
		if(message.endsWith("true")){
			isRed=true;
			state=1;
			yourTurn=true;
		}
		else if(message.endsWith("false")) state=2;
		else observer=true;
		
		addMouseListener(this);//����������
		
		Thread t=new Thread(this);//���ݷ��񽻻�
		t.start();
		cc=new Commication(this);
		Thread t2=new Thread(cc);//��Ϣ���񽻻�
		t2.start();
	}
	
	private void drawLines(int row,int col,int width,int height,Graphics g) {
		//1/2 2/3 1/3
		int baseX=width*col/11;
		int baseY=height*row/12;
		int wlen=width/110;
		int hlen=height/120;
		int w4len=width/44;
		int h4len=height/48;
		
		if(col!=9){
			g.drawLine(baseX+wlen, baseY-hlen, baseX+wlen, baseY-h4len);
			g.drawLine(baseX+wlen, baseY-hlen, baseX+w4len, baseY-hlen);
		
			g.drawLine(baseX+wlen, baseY+hlen, baseX+w4len, baseY+hlen);
			g.drawLine(baseX+wlen, baseY+hlen,baseX+wlen,baseY+h4len);
		}
		
		if(col!=1){
			g.drawLine(baseX-wlen, baseY-hlen, baseX-wlen,baseY-h4len);
			g.drawLine(baseX-wlen, baseY-hlen, baseX-w4len, baseY-hlen);
		
			g.drawLine(baseX-wlen, baseY+hlen, baseX-w4len, baseY+hlen);
			g.drawLine(baseX-wlen, baseY+hlen, baseX-wlen, baseY+h4len);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		int height=getHeight();
		int width=getWidth();
		//�Ȼ�����9��������10
		g.drawLine(width/11, height/12, width/11, height*10/12);
		g.drawLine(width*9/11, height/12, width*9/11, height*10/12);
		for(int i=0;i<7;i++){
			g.drawLine(width*(i+2)/11, height/12, width*(i+2)/11, height*5/12);
			g.drawLine(width*(i+2)/11, height/2, width*(i+2)/11, height*10/12);
		}
		for(int i=0;i<10;i++){
			g.drawLine(width/11, height*(i+1)/12, width*9/11, height*(i+1)/12);
		}
		
		//�����Ӻ���
		g.setFont(new Font("�����п�", Font.BOLD, 30));
		g.drawString("��        ��", 200, 330);
		g.drawString("��        ��", 900, 330 );
		
		//��ʿ����
		g.drawLine(width*4/11, height/12, width*6/11, height/4);
		g.drawLine(width*6/11, height/12, width*4/11, height/4);
		g.drawLine(width*4/11, height*10/12, width*6/11, height*8/12);
		g.drawLine(width*6/11, height*10/12, width*4/11, height*8/12);
		
		drawLines(3, 2, width, height, g);
		drawLines(3, 8, width, height, g);
		drawLines(8, 2, width, height, g);
		drawLines(8, 8, width, height, g);//��
		
		drawLines(4, 1, width, height, g);
		drawLines(4, 3, width, height, g);
		drawLines(4, 5, width, height, g);
		drawLines(4, 7, width, height, g);
		drawLines(4, 9, width, height, g);
		drawLines(7, 1, width, height, g);
		drawLines(7, 3, width, height, g);
		drawLines(7, 5, width, height, g);
		drawLines(7, 7, width, height, g);
		drawLines(7, 9, width, height, g);//��
		
		//��ͼ��
		for(int i=1;i<=10;i++){
			for(int j=1;j<=9;j++){
				if(data[i-1][j-1]==0) continue;
				int baseX=width*j/11;
				int baseY;
				if(!isRed)
					baseY=height*(11-i)/12;
				else
					baseY=height*i/12;
				g.drawImage(pics[data[i-1][j-1]], baseX-width/22, baseY-height/24, width/11, height/12, this);
			}
		}
		
		if(yourTurn && !isRedo && state==3){
			//���Է�����ʾ
			g.setColor(Color.GREEN);
			g.drawLine(precol*width/11-width/22, prerow*height/12-height/24, precol*width/11-width/44, prerow*height/12-height/24);
			g.drawLine(precol*width/11-width/22, prerow*height/12-height/24, precol*width/11-width/22, prerow*height/12-height/48);
			g.drawLine(precol*width/11+width/22, prerow*height/12-height/24, precol*width/11+width/44, prerow*height/12-height/24);
			g.drawLine(precol*width/11+width/22, prerow*height/12-height/24, precol*width/11+width/22, prerow*height/12-height/48);
			g.drawLine(precol*width/11+width/22, prerow*height/12+height/24, precol*width/11+width/44, prerow*height/12+height/24);
			g.drawLine(precol*width/11+width/22, prerow*height/12+height/24, precol*width/11+width/22, prerow*height/12+height/48);
			g.drawLine(precol*width/11-width/22, prerow*height/12+height/24, precol*width/11-width/44, prerow*height/12+height/24);
			g.drawLine(precol*width/11-width/22, prerow*height/12+height/24, precol*width/11-width/22, prerow*height/12+height/48);
			g.setColor(Color.black);
			g.setColor(Color.BLUE);
			g.drawLine(col*width/11-width/22, row*height/12-height/24, col*width/11-width/44, row*height/12-height/24);
			g.drawLine(col*width/11-width/22, row*height/12-height/24, col*width/11-width/22, row*height/12-height/48);
			g.drawLine(col*width/11+width/22, row*height/12-height/24, col*width/11+width/44, row*height/12-height/24);
			g.drawLine(col*width/11+width/22, row*height/12-height/24, col*width/11+width/22, row*height/12-height/48);
			g.drawLine(col*width/11+width/22, row*height/12+height/24, col*width/11+width/44, row*height/12+height/24);
			g.drawLine(col*width/11+width/22, row*height/12+height/24, col*width/11+width/22, row*height/12+height/48);
			g.drawLine(col*width/11-width/22, row*height/12+height/24, col*width/11-width/44, row*height/12+height/24);
			g.drawLine(col*width/11-width/22, row*height/12+height/24, col*width/11-width/22, row*height/12+height/48);
			g.setColor(Color.black);
		}
		//��һ����ʾ
		if(tip){
			g.setColor(Color.green);
			g.drawLine(col*width/11-width/22, row*height/12-height/24, col*width/11-width/44, row*height/12-height/24);
			g.drawLine(col*width/11-width/22, row*height/12-height/24, col*width/11-width/22, row*height/12-height/48);
			g.drawLine(col*width/11+width/22, row*height/12-height/24, col*width/11+width/44, row*height/12-height/24);
			g.drawLine(col*width/11+width/22, row*height/12-height/24, col*width/11+width/22, row*height/12-height/48);
			g.drawLine(col*width/11+width/22, row*height/12+height/24, col*width/11+width/44, row*height/12+height/24);
			g.drawLine(col*width/11+width/22, row*height/12+height/24, col*width/11+width/22, row*height/12+height/48);
			g.drawLine(col*width/11-width/22, row*height/12+height/24, col*width/11-width/44, row*height/12+height/24);
			g.drawLine(col*width/11-width/22, row*height/12+height/24, col*width/11-width/22, row*height/12+height/48);
			g.setColor(Color.black);
		}
	}
	
	//����¼�
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!yourTurn || observer || state==4) return;//��������Լ��غϻ��߽�ɫΪ�Թ��߻��ߵ�ǰ��Ϸ״̬�Ѿ�����
													//���޷��������
		//ȷ��λ�ã���ȡ�������ӵ��к���
		//���isSelectedΪ�٣���ô�����col����row�����´�isSelectedΪ���ʱ��֮ǰ��preCol��preRow
		//���isSelectedΪ�棬��ô�����col����row�������ڵ�col��row
		int width=getWidth();
		int height=getHeight();
		int x=e.getX();
		int y=e.getY();
		for(int i=1;i<=9;i++){
			if(Math.abs(width*i/11-x)<width/22){
				col=i;
				break;
			}
		}
		for(int i=1;i<=10;i++){
			if(Math.abs(height*i/12-y)<height/24){
				row=i;
				break;
			}
		}
		
		if(isSelected){//����Ѿ�ѡ�������ӣ������������ƶ����Ӳ�������ʱ�ĸ������Ѿ�ȷ���ˣ�
			//�ж��ܲ��ܵ�
			//���ж��Ƿ񱻽���
			int[] aixs=new int[4];
			if(isRed){//��������
				aixs=new int[]{prerow,precol,row,col};
			}else{
				aixs=new int[]{11-prerow,precol,11-row,col};
				prerow=11-prerow;
				row=11-row;
			}
			
			int label=data[prerow-1][precol-1];//ѡ�е���ʲô��
			
			JudgeMove jm=new JudgeMove(this,data,isRed);//�ƶ������ж϶���
			switch (label) {
				case 1:
				case 8:
					//�ǳ�
					if(!jm.move_che(aixs)){//�ƶ����Ĺ��򲻺���
						common_op(aixs);//�������ָ�����
						return;
					}
					break;
				case 2:
				case 9:
					//����
					if(!jm.move_ma(aixs)){//�ƶ���Ĺ��򲻺���
						common_op(aixs);
						return;
					}
					break;
				case 3:
				case 10:
					//����
					if(!jm.move_xiang(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 4:
				case 11:
					//ʿ
					if(!jm.move_shi(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 5:
				case 12:
					//�ǽ�
					if(!jm.move_jiang(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 6:
				case 13:
					//����
					if(!jm.move_pao(aixs)){
						common_op(aixs);
						return;
					}
					break;
				case 7:
				case 14:
					//�Ǳ�
					if(!jm.move_bing(aixs)){
						common_op(aixs);
						return;
					}
					break;
				default:
					break;
			}
			
			//���Խ����ƶ�
			int[][] datasub=Common.Backup(data);//�ȱ�����֣����������̽������
			datasub[row-1][col-1]=datasub[prerow-1][precol-1];
			datasub[prerow-1][precol-1]=0;
			//�ж���̽�������Ƿ񵽴���Ϸ����״̬
			if(new HasFinished(datasub).isFinished(isRed)){//��Ϸ����
				data=datasub;//����ǰ�����Ϊ��̽�����������
				tip=false;
				isRedo=true;
				repaint();
				internet.writeMessage("Congratulate,you win.");
				//д������б���
				rd.add_one_step(Common.Backup(datasub));
				SimpleDateFormat sf=new SimpleDateFormat("YYYYMMDD_HHmmss");
				//д���Զ����ŵ�¼����
				Replay.WriteFile(rd.getMove_down(), "file/"+sf.format(new Date())+".chess");
				state=4;//����Ϸ״̬��Ϊ����
				return;
			}
			
			if(!HasFinished.jiangTip(datasub, isRed)){//��ǰ�������ˣ������ͽ�
				if(!isRed)
					prerow=11-prerow;//�ָ���������������ƶ�����������
				isSelected=true;
				return;
			}//��ǰ��������

			//���ˣ������ƶ��Ĳ�����������򡢶���û���ͽ����Լ���Ϸû�н���
			internet.writeMessage(prerow+" "+precol+" "+row+" "+col);//�������д��Ϣ
			isSelected=false;//��ѡ��Ϊfalse�������½׶���ѡ�����ӣ��������ƶ�����
			isRedo=false;//�����壬�����ǻ��嵽��������״̬
		}else{//ѡ�ӽ׶Σ�����isSelectedΪfalse�����
			precol=col;
			prerow=row;
			if(!isRed){//�û��ӽ�
				prerow=11-prerow;
				row=11-row;
			}
			if(precol==0 || prerow==0)//û��ѡ������
				return;

			if(data[prerow-1][precol-1]==0){//û��ѡ������
				return;
			}else if((isRed && data[prerow-1][precol-1]>=8) || 
					(!isRed && data[prerow-1][precol-1]<=7)){//ѡ��Է���������
				return;
			}
			//���˺���ѡ��������
			tip=true;//������ʾ����
			isSelected=true;//����isSelectedΪ�棬��һ�׶����ƶ����Ӷ�����ѡ������
			//�ָ��ӽ�
			if(!isRed){
				prerow=11-prerow;
				row=11-row;
			}
			repaint();
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	//������set��get����
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isYourTurn() {
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	public Internet getInternet() {
		return internet;
	}

	public Redo getRd() {
		return rd;
	}

	public void setRedo(boolean isRedo) {
		this.isRedo = isRedo;
	}

	public Commication getCc() {
		return cc;
	}

	/**
	 * �����ݷ���������Ϣ���������н���
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			String message_array=internet.readMessage();
			state=3;//������Ϸ״̬Ϊ��Ϸ��
			if(message_array.indexOf(",")>0){//���ǽ��յ�����Ϣ
				//������Ϣ
				JOptionPane.showMessageDialog(null, message_array);
				continue;
			}
			//������յ�������
		    int[][]	datasub=Common.String_to_Array(message_array);
		    int[] aixs=Common.FindDiff(data, datasub,isRed);
		    //��ȡ��Ե��ĸ����꣨��Ϊ����ӽ����⣬�����е����ò�ͬ����������ԣ�
		    prerow=aixs[0];
		    if(!isRed) prerow=11-prerow;
		    precol=aixs[1];
		    row=aixs[2];
		    if(!isRed)
		    	row=11-row;
		    col=aixs[3];
		    
		    data=datasub;//�����״̬��Ϊ�ӷ������л�ȡ�����µ����״̬����
		    rd.add_one_step(Common.Backup(data));//������ּ���Ļ����б���
			tip=false;
			repaint();
			yourTurn=!yourTurn;//�غ��л�
		}
	}
	
	/**
	 * ������ȡ�Ĺ��������������ڲ�������ƶ�����ʱ���ָܻ����ƶ�ǰ������״̬
	 * @param aixs������λ�õ���������
	 */
	private void common_op(int[] aixs) {
		if(isRed){
			prerow=row=aixs[0];
		}else{
			prerow=row=11-aixs[0];
		}
		precol=col=aixs[1];
		repaint();
	}
}
