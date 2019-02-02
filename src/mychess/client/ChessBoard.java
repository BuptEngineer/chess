package mychess.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mychess.entity.Code;
import mychess.entity.DataMessage;
import mychess.entity.Message;
import mychess.entity.NormalMessage;
import mychess.util.Common;
import mychess.util.HasFinished;
import mychess.util.Internet;
import mychess.util.JudgeMove;

public class ChessBoard extends JPanel implements MouseListener,Runnable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	protected DataMessage message;//ÿ��׼��һ����Ϣ���ģ�׼����Ϣͨ��
	protected boolean tip;//�Ƿ��ػ��ʱ�������ʾ��Ҳ���������ʱ���б�ǣ�
	protected boolean isSelected;//�����Ƿ�ѡ�У�������һ��������ƶ����ӻ�����ѡ������
	protected int[][] data;//��ǰ��ֵ�״̬����,�����ػ�
	protected Image[] pics;//���������ͼƬ
	private Internet internet;//���ݷ���������
	private String result="";
	public ChessBoard() {
		// TODO Auto-generated constructor stub
	}
	
	public ChessBoard(Image[] pictures) {
		// TODO Auto-generated constructor stub
		pics=pictures;
		init();
		addMouseListener(this);//����������
		Thread t=new Thread(this);//���ݷ��񽻻�
		t.start();
	}
	
	public void init() {
		internet=new Internet();//�������ݷ�������������Ϣ��������Ҫ�ֶ���ǰ����
		message= (DataMessage) internet.readMessage();
		data=Common.String_to_Array(message.getData());
	}
	
	//��ͼ
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
		g.setFont(new Font("�����п�", Font.BOLD, 25));
		g.drawString("��        ��", (int)(width*1.5/11), (int)(height*(4.6+1)/12));
		g.drawString("��        ��", (int)(width*7.1/11), (int)(height*(4.6+1)/12));
		g.setColor(Color.RED);
		g.drawString(result,(int)(width*4/11), (int)(height*(4.6+1)/12));
		g.setColor(Color.BLACK);
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
				if(message.getRole()==2)
					baseY=height*(11-i)/12;
				else
					baseY=height*i/12;
				g.drawImage(pics[data[i-1][j-1]], baseX-width/22, baseY-height/24, width/11, height/12, this);
			}
		}
		
		int precol=message.getPrecol();
		int prerow=message.getPrerow();
		int col=message.getCol();
		int row=message.getRow();
		if((message.isYourTurn() || message.getRole()>2) && message.getCode()==Code.Run && row!=0){//�������Թ���
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
		if(!message.isYourTurn() || message.getRole()>2 || message.code.getDes()=="����") return;//��������Լ��غϻ��߽�ɫΪ�Թ��߻��ߵ�ǰ��Ϸ״̬�Ѿ�����
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
				//col=i;
				message.setCol(i);
				break;
			}
		}
		for(int i=1;i<=10;i++){
			if(Math.abs(height*i/12-y)<height/24){
				//row=i;
				message.setRow(i);
				break;
			}
		}
		
		if(isSelected){//����Ѿ�ѡ�������ӣ������������ƶ����Ӳ�������ʱ�ĸ������Ѿ�ȷ���ˣ�
			//�ж��ܲ��ܵ�
			//���ж��Ƿ񱻽���
			int[] aixs=new int[4];
			if(message.getRole()==1){//�췽
				aixs=new int[]{message.getPrerow(),message.getPrecol(),message.getRow(),message.getCol()};
			}else{
				aixs=new int[]{11-message.getPrerow(),message.getPrecol(),11-message.getRow(),message.getCol()};
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
			
			message.setChess(data[message.getPrerow()-1][message.getPrecol()-1]);
			
			JudgeMove jm=new JudgeMove(this,data,message.getRole()==1);//�ƶ������ж϶���
			
			switch (message.getChess()) {
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
			message.setEatedChess(datasub[message.getRow()-1][message.getCol()-1]);//���ñ��Ե�����
			datasub[message.getRow()-1][message.getCol()-1]=datasub[message.getPrerow()-1][message.getPrecol()-1];
			datasub[message.getPrerow()-1][message.getPrecol()-1]=0;
			if(!HasFinished.jiangTip(datasub, message.getRole()==1)){//��ǰ�������ˣ������ͽ�
				if(message.getRole()==2)
					message.setPrerow(11-message.getPrerow());
				isSelected=true;
				return;
			}//��ǰ��������

			//���ˣ������ƶ��Ĳ�����������򡢶���û���ͽ����Լ���Ϸû�н���
			data=datasub;
			try {
				message.setStep(message.getStep()+1);
				internet.writeMessage(message.clone());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//���͹������Ϣ
			
			//�ж���̽�������Ƿ񵽴���Ϸ����״̬
			if(new HasFinished(data).isFinished(message.getRole()==1)){//��Ϸ����
				NormalMessage myNormalMessage=new NormalMessage();//����һ����Ϣ
				myNormalMessage.setRole(message.getRole());
				myNormalMessage.setAttach("��Ϸ����");
				internet.writeMessage(myNormalMessage);
				message.setCode(Code.Over);
			}
			
			isSelected=false;//��ѡ��Ϊfalse�������½׶���ѡ�����ӣ��������ƶ�����
			message.setYourTurn(!message.isYourTurn());//�غ��л�
			tip=false;
		}else{//ѡ�ӽ׶Σ�����isSelectedΪfalse�����			
			message.setPrecol(message.getCol());
			message.setPrerow(message.getRow());
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
			if(message.getPrecol()==0 || message.getPrerow()==0)
				return;

			if(data[message.getPrerow()-1][message.getPrecol()-1]==0){
				return;
			}else if((message.getRole()==1 && data[message.getPrerow()-1][message.getPrecol()-1]>=8) || 
					(message.getRole()==2 && data[message.getPrerow()-1][message.getPrecol()-1]<=7)){
				return;
			}
			
			//���˺���ѡ��������
			tip=true;//������ʾ����
			isSelected=true;//����isSelectedΪ�棬��һ�׶����ƶ����Ӷ�����ѡ������
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
		}
		repaint();
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	/**
	 * �����ݷ���������Ϣ���������н���
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			Message myMessage=internet.readMessage();
			message.setCode(Code.Run);
			if(myMessage instanceof NormalMessage){
				//�������Ϣ
				if(((NormalMessage) myMessage).getAttach().equals("����")){
					int showConfirmDialog = JOptionPane.showConfirmDialog(null, "�Է�������壬�Ƿ�ͬ��");
					if(message.getRole()>2) continue;//�Թ�����
					if(showConfirmDialog==JOptionPane.YES_OPTION){
						//����ͬ�����Ϣ
						NormalMessage temp=new NormalMessage();
						temp.setAttach("ͬ�����");
						internet.writeMessage(temp);
					}else if(showConfirmDialog==JOptionPane.NO_OPTION){
						//��ͬ�����
						NormalMessage temp=new NormalMessage();
						temp.setAttach("�Է���ͬ�����");
						internet.writeMessage(temp);
					}
				}else if(((NormalMessage) myMessage).getAttach().equals("��Ϸ����")){
					String role=myMessage.getRole()==1?"�췽":"�ڷ�";
					result=role+"ʤ��";
					message.setCode(Code.Over);
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().equals("���¿���")){
					int showConfirmDialog = JOptionPane.showConfirmDialog(null, "�Է���������һ�֣��Ƿ�ͬ��");
					if(message.getRole()>2) continue;//ͬ�����¿�ʼ
					if(showConfirmDialog==JOptionPane.YES_OPTION){
						//����ͬ�����Ϣ
						NormalMessage temp=new NormalMessage();
						temp.setAttach("ͬ�⿪��");
						internet.writeMessage(temp);
					}else if(showConfirmDialog==JOptionPane.NO_OPTION){
						//��ͬ�����
						NormalMessage temp=new NormalMessage();
						temp.setAttach("�Է���ͬ�⿪��");
						internet.writeMessage(temp);
					}
				}else if(((NormalMessage)myMessage).getAttach().equals("ͬ�⿪��")){
					//���Լ���������ݰ������Է�
					init();
					result="";
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().contains("����")){
					result=message.getRole()==1?"�췽��ʤ":"�ڷ���ʤ";
					message.setCode(Code.Over);
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().equals("���")){
					int showConfirmDialog = JOptionPane.showConfirmDialog(null, "�Է�������ͣ��Ƿ�ͬ��");
					if(showConfirmDialog==JOptionPane.YES_OPTION){
						//����ͬ�����Ϣ
						NormalMessage temp=new NormalMessage();
						temp.setAttach("ͬ�����");
						internet.writeMessage(temp);
					}else if(showConfirmDialog==JOptionPane.NO_OPTION){
						//��ͬ�����
						NormalMessage temp=new NormalMessage();
						temp.setAttach("�Է���ͬ�����");
						internet.writeMessage(temp);
					}
				}else if(((NormalMessage)myMessage).getAttach().equals("ͬ�����")){
					result="˫�����";
					message.setCode(Code.Over);
					repaint();
				}else if(((NormalMessage)myMessage).getAttach().equals("�뿪")){
					JOptionPane.showMessageDialog(null, "�Է��Ѿ��뿪,�����˳�");
					System.exit(1);
				}
				if(filterMessage(myMessage))
					JOptionPane.showMessageDialog(null, ((NormalMessage) myMessage).getAttach());
				continue;
			}
			//���������
			message.setPrerow(((DataMessage) myMessage).getPrerow());
			message.setPrecol(((DataMessage) myMessage).getPrecol());
			message.setRow(((DataMessage) myMessage).getRow());
			message.setCol(((DataMessage) myMessage).getCol());
			message.setStep(myMessage.getStep());
			
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
			
			data=Common.String_to_Array(((DataMessage)myMessage).data);
			tip=false;
			repaint();
			message.setYourTurn(!message.isYourTurn());
		}
	}
	
	/**
	 * ���幦�ܵ�ʵ��
	 */
	public void Redo() {
		//ִ�л���
		//�������д������������
		if(message.isYourTurn()){
			JOptionPane.showMessageDialog(null, "����������,���ܻ���");
			return;
		}
		if(message.getStep()==1){
			JOptionPane.showMessageDialog(null, "��ǰ�ǵ�һ�������ܻ���");
			return;
		}
		NormalMessage message=new NormalMessage();
		message.setAttach("����");
		internet.writeMessage(message);
	}
	
	/**
	 * ���¿�ʼ��ʵ��
	 */
	public void restart() {
		if(message.getCode().getDes().equals("����")){
			//��Ϸ�Ѿ��������������¿�ʼ
			NormalMessage message=new NormalMessage();
			message.setAttach("���¿���");
			message.setRole(message.getRole());
			internet.writeMessage(message);
		}else{
			JOptionPane.showMessageDialog(null, "��Ϸû�н���,���������");
			return;
		}
	}
	
	public void lose() {
		if(message.getStep()<20){
			JOptionPane.showMessageDialog(null, "��ʮ��֮�ڲ�������");
			return;
		}
		result=message.getRole()==1?"�ڷ�ʤ��":"�췽ʤ��";
		NormalMessage myMessage=new NormalMessage();
		myMessage.setAttach((message.getRole()==1?"�췽":"�ڷ�")+"����");
		internet.writeMessage(myMessage);
		message.setCode(Code.Over);
		repaint();
	}
	
	public void peace() {
		if(message.getStep()<50){
			JOptionPane.showMessageDialog(null, "��ʮ��֮�ڲ������");
			return;
		}
		NormalMessage message=new NormalMessage();
		message.setAttach("���");
		internet.writeMessage(message);
	}
	
	public void leave() {
		NormalMessage message=new NormalMessage();
		message.setAttach("�뿪");
		internet.writeMessage(message);
	}
	
	//��ͼ�Ĺ�������,���������е�����
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
	
	//�����ڲ�������ƶ�����ʱ���ָܻ����ƶ�ǰ������״̬
	protected void common_op(int[] aixs) {
		if(message.getRole()==1){
			message.setPrerow(aixs[0]);
			message.setRow(aixs[0]);
		}else{
			message.setPrerow(11-aixs[0]);
			message.setRow(11-aixs[0]);
		}
		message.setPrecol(aixs[1]);
		message.setCol(aixs[1]);
		repaint();
	}
	
	//����һЩ��Ϣ��ʹ�䲻��ʾ
	private boolean filterMessage(Message message) {
		//������Ϣ
		if(((NormalMessage)message).getAttach().equals("����") || 
				((NormalMessage)message).getAttach().equals("��Ϸ����") || ((NormalMessage)message).getAttach().equals("ͬ�⿪��")
				|| ((NormalMessage)message).getAttach().equals("���") || ((NormalMessage)message).getAttach().equals("ͬ�����"))
			return false;
		return true;
	}
}
