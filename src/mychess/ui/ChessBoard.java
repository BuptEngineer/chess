package mychess.ui;

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
import mychess.function.Internet;
import mychess.util.Common;
import mychess.util.HasFinished;
import mychess.util.JudgeMove;


public class ChessBoard extends JPanel implements MouseListener,Runnable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	private DataMessage message;//ÿ��׼��һ����Ϣ���ģ�׼����Ϣͨ��
	
	private int col;//����������������
	private int row;//����������������
	private boolean tip;//�Ƿ��ػ��ʱ�������ʾ��Ҳ���������ʱ���б�ǣ�
	private boolean isSelected;//�����Ƿ�ѡ�У�������һ��������ƶ����ӻ�����ѡ������
	private int precol;//��һ��������������
	private int prerow;//��һ��������������
	private boolean yourTurn;//�ǲ��ǵ���Ļغ�
	private int[][] data;//��ǰ��ֵ�״̬����,�����ػ�
	private Image[] pics;//���������ͼƬ
	private Internet internet;//���ݷ���������
	//private boolean observer;//�Ƿ��ǹ۲��߽�ɫ
	private boolean isRed;//�Ƿ��Ǻ췽
	private boolean isRedo;//�Ƿ����
	private byte state=0;//״̬0��ʾ����, 1��ʾ׼��,2��ʾ��Ϸ��ʼ,3��ʾ��Ϸ������,4��ʾ��Ϸ����
	
	public ChessBoard(Image[] pictures) {
		// TODO Auto-generated constructor stub
		pics=pictures;
		internet=new Internet();//�������ݷ�������������Ϣ��������Ҫ�ֶ���ǰ����
		//�жϽ�ɫ�ͻ�ȡ��ֳ�ʼ״̬
		//message=new DataMessage();//ʵ����������Ϣ��һ���汣�汾���ȫ�����ԣ�ͬʱ�ֿ�����Ϊ��Ϣ���ͳ�ȥ
		message= (DataMessage) internet.readMessage();//����״̬
		data=message.getData();
//		message.setCode(Code.Free);//״̬�ǿ���
//		message.setRole(normalMessage.getRole());//���õ�ǰ��ɫ���Ǻ췽���Ǻڷ��������Թ���
//		message.setYourTurn(normalMessage.isYourTurn());//�����Ⱥ���
		
//		String message_array=internet.readMessage();
//		data=Common.String_to_Array(message_array);//�ͻ��˽��г�ʼ��
//		rd.add_one_step(data);
//		if(message.endsWith("true")){
//			isRed=true;
//			state=1;
//			yourTurn=true;
//		}
//		else if(message.endsWith("false")) state=2;
//		else observer=true;

		addMouseListener(this);//����������
		
		Thread t=new Thread(this);//���ݷ��񽻻�
		t.start();
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
//				if(!isRed)
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
//		if(yourTurn && !isRedo && state==3){
		if(message.isYourTurn()){
			
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
			//g.setColor(Color.black);
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
			//if(isRed){//��������
			if(message.getRole()==1){//�췽
				//aixs=new int[]{prerow,precol,row,col};
				aixs=new int[]{message.getPrerow(),message.getPrecol(),message.getRow(),message.getCol()};
			}else{
				//aixs=new int[]{11-prerow,precol,11-row,col};
				aixs=new int[]{11-message.getPrerow(),message.getPrecol(),11-message.getRow(),message.getCol()};
				//prerow=11-prerow;
				message.setPrerow(11-message.getPrerow());
				//row=11-row;
				message.setRow(11-message.getRow());
			}
			
			//int label=data[prerow-1][precol-1];//ѡ�е���ʲô��
			message.setChess(data[message.getPrerow()-1][message.getPrecol()-1]);
			
			//JudgeMove jm=new JudgeMove(this,data,isRed);//�ƶ������ж϶���
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
			//datasub[row-1][col-1]=datasub[prerow-1][precol-1];
			//datasub[prerow-1][precol-1]=0;
			message.setEatedChess(datasub[message.getRow()-1][message.getCol()-1]);//���ñ��Ե�����
			datasub[message.getRow()-1][message.getCol()-1]=datasub[message.getPrerow()-1][message.getPrecol()-1];
			datasub[message.getPrerow()-1][message.getPrecol()-1]=0;
			
			//�ж���̽�������Ƿ񵽴���Ϸ����״̬
			//if(new HasFinished(datasub).isFinished(isRed)){//��Ϸ����
			if(new HasFinished(datasub).isFinished(message.getRole()==1)){//��Ϸ����
				data=datasub;//����ǰ�����Ϊ��̽�����������
				tip=false;
//				isRedo=true;
				repaint();
				//internet.writeMessage("Congratulate,you win.");
				NormalMessage myNormalMessage=new NormalMessage();//����һ����Ϣ
				myNormalMessage.setAttach("���ź�������");
				internet.writeMessage(myNormalMessage);
				
				//д������б���
				//rd.add_one_step(Common.Backup(datasub));
				//SimpleDateFormat sf=new SimpleDateFormat("YYYYMMDD_HHmmss");
				//д���Զ����ŵ�¼����
				//Replay.WriteFile(rd.getMove_down(), "file/"+sf.format(new Date())+".chess");
				//state=4;//����Ϸ״̬��Ϊ����
				message.setCode(Code.Over);
				System.out.println("��Ϸ����");
				return;
			}
			
//			if(!HasFinished.jiangTip(datasub, isRed)){//��ǰ�������ˣ������ͽ�
			if(!HasFinished.jiangTip(datasub, message.getRole()==1)){//��ǰ�������ˣ������ͽ�
				//if(!isRed){
				if(message.getRole()==2)
//					prerow=11-prerow;//�ָ���������������ƶ�����������
					message.setPrerow(11-message.getPrerow());
				isSelected=true;
				return;
			}//��ǰ��������

			//���ˣ������ƶ��Ĳ�����������򡢶���û���ͽ����Լ���Ϸû�н���
			//internet.writeMessage(prerow+" "+precol+" "+row+" "+col);//�������д��Ϣ
			data=datasub;
			try {
				internet.writeMessage(message.clone());
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//���͹������Ϣ
			isSelected=false;//��ѡ��Ϊfalse�������½׶���ѡ�����ӣ��������ƶ�����
			message.setYourTurn(!message.isYourTurn());//�غ��л�
			tip=false;
			//isRedo=false;//�����壬�����ǻ��嵽��������״̬
		}else{//ѡ�ӽ׶Σ�����isSelectedΪfalse�����			
//			precol=col;
//			prerow=row;
			message.setPrecol(message.getCol());
			message.setPrerow(message.getRow());
//			if(!isRed){//�û��ӽ�
			if(message.getRole()==2){
//				prerow=11-prerow;
//				row=11-row;
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
//			if(precol==0 || prerow==0)//û��ѡ������
			if(message.getPrecol()==0 || message.getPrerow()==0)
				return;

//			if(data[prerow-1][precol-1]==0){//û��ѡ������
			if(data[message.getPrerow()-1][message.getPrecol()-1]==0){
				return;
//			}else if((isRed && data[prerow-1][precol-1]>=8) || 
//					(!isRed && data[prerow-1][precol-1]<=7)){//ѡ��Է���������
			}else if((message.getRole()==1 && data[message.getPrerow()-1][message.getPrecol()-1]>=8) || 
					(message.getRole()==2 && data[message.getPrerow()-1][message.getPrecol()-1]<=7)){
				return;
			}
			//���˺���ѡ��������
			tip=true;//������ʾ����
			isSelected=true;//����isSelectedΪ�棬��һ�׶����ƶ����Ӷ�����ѡ������
			//�ָ��ӽ�
//			if(!isRed){
//				prerow=11-prerow;
//				row=11-row;
//			}
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

	//������set��get����
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Internet getInternet() {
		return internet;
	}

	/**
	 * �����ݷ���������Ϣ���������н���
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
//			String message_array=internet.readMessage();
			Message myMessage=internet.readMessage();
//			state=3;//������Ϸ״̬Ϊ��Ϸ��
			message.setCode(Code.Run);
//			if(message_array.indexOf(",")>0){//���ǽ��յ�����Ϣ
//				//������Ϣ
//				JOptionPane.showMessageDialog(null, message_array);
//				continue;
//			}
			if(myMessage instanceof NormalMessage){
				//�������Ϣ
				JOptionPane.showMessageDialog(null, ((NormalMessage) myMessage).getAttach());
				continue;
			}
			
			//������յ�������
//		    int[][]	datasub=Common.String_to_Array(message_array);
//		    int[] aixs=Common.FindDiff(data, datasub,isRed);
		    //��ȡ��Ե��ĸ����꣨��Ϊ����ӽ����⣬�����е����ò�ͬ����������ԣ�
//		    prerow=aixs[0];
//		    if(!isRed) prerow=11-prerow;
//		    precol=aixs[1];
//		    row=aixs[2];
//		    if(!isRed)
//		    	row=11-row;
//		    col=aixs[3];
			message.setPrerow(((DataMessage) myMessage).getPrerow());
			message.setPrecol(((DataMessage) myMessage).getPrecol());
			message.setRow(((DataMessage) myMessage).getRow());
			message.setCol(((DataMessage) myMessage).getCol());
			System.out.println(message.getPrerow()+","+message.getPrecol()+","+message.getRow()+","+message.getCol());
			if(message.getRole()==2){
				message.setPrerow(11-message.getPrerow());
				message.setRow(11-message.getRow());
			}
			
			data=((DataMessage)myMessage).data;
//		    data[message.getRow()-1][message.getCol()-1]=data[message.getPrerow()-1][message.getPrecol()-1];
//		    data[message.getPrerow()-1][message.getPrecol()-1]=0;//����
		    
		    //data=datasub;//�����״̬��Ϊ�ӷ������л�ȡ�����µ����״̬����
		    //rd.add_one_step(Common.Backup(data));//������ּ���Ļ����б���
			tip=false;
			repaint();
			//yourTurn=!yourTurn;//�غ��л�
			message.setYourTurn(!message.isYourTurn());
		}
	}
	
	/**
	 * ������ȡ�Ĺ��������������ڲ�������ƶ�����ʱ���ָܻ����ƶ�ǰ������״̬
	 * @param aixs������λ�õ���������
	 */
	private void common_op(int[] aixs) {
//		if(isRed){
//			prerow=row=aixs[0];
//		}else{
//			prerow=row=11-aixs[0];
//		}
//		precol=col=aixs[1];
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
}
