package com.hi;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIclient extends Frame implements ActionListener, KeyListener{
   
	String date = new SimpleDateFormat("yy-MM-dd").format(new Date());
	static String todaytitle = null;
	String title=date+" ["+todaytitle+"]";
   
	Button btnli,btnSm;
	TextArea txtdia;
	Label Cnt, latxt;
	int count = 0;
	
	static File dir= new File("C:/ohsong/project");; //�ϱ���������
	static FileReader filereader;
	
	String[] filenames;
	Button[] btns;
	Button[] btns2;
	
	 static Socket socket = null;
	 static BufferedReader br = null;
	 static BufferedWriter bw = null;
	 static InputStream is=null;
	 static OutputStream os=null;
	 static InputStreamReader isr=null;
	 static OutputStreamWriter osw=null;
   
	public GUIclient(){
		
		JFrame Fr = new JFrame("�Ϸ� �ϱ���"); //��������â
		Fr.setBounds(100, 100, 405, 480);
		Fr.setLayout(null);
		Fr.setResizable(false);
		Fr.setVisible(true);
      
		Panel p1=new Panel();
		Fr.add(p1);
		p1.setLayout(null);
		p1.setBounds(0, 0, 500, 100);
   
			Label lbday =new Label("����"); //�� - ����
			lbday.setBounds(40, 50, 100, 20);
			p1.add(lbday);
			Label ladate =new Label(date); //�� - date : �ڵ�����
			ladate.setBounds(140, 50, 250, 20);
			p1.add(ladate);
         
		Label lbtit=new Label("����"); //�� - ����
		lbtit.setBounds(40, 80, 100, 20);
		p1.add(lbtit);
		Label latoti =new Label(todaytitle); //�� title - ������Է�
		latoti.setBounds(140, 80, 250, 20);
		p1.add(latoti);
         
		Panel p2=new Panel();
		Fr.add(p2);
		p2.setLayout(null);
		p2.setBounds(0, 100, 500, 300);
			txtdia= new TextArea("",0,0, TextArea.SCROLLBARS_VERTICAL_ONLY); //txt���� - ������Է�
			txtdia.setBounds(30, 20, 350, 250);
			txtdia.addKeyListener(this);
			p2.add(txtdia);
			Cnt = new Label("���ڼ� : " + txtdia.getText().length());
			Cnt.setBounds(320, 265, 150, 50);
			p2.add(Cnt);
      
		Panel p3= new Panel();
			Fr.add(p3);
			p3.setLayout(new FlowLayout());
			p3.setBounds(0, 400, 400,100);
			p3.revalidate();
			p3.repaint();
		btnli = new Button("���");
		p3.add(btnli);
		btnli.addActionListener(this);
		btnli.revalidate();
		btnli.repaint();
				
        btnSm = new Button("�ۼ��Ϸ�");
        p3.add(btnSm);
        btnSm.addActionListener(this);
        btnSm.revalidate();
        btnSm.repaint();
	}

   public static void main(String[] args) {
      // TODO Auto-generated method stub
	   
	   Scanner scanner = new Scanner(System.in);
	   System.out.print("������ ���̾ ������ �����ּ���.");
	   todaytitle = scanner.nextLine();
     
	   new GUIclient();
      
      try {
		socket = new Socket("localhost",9999);
		System.out.println("������ ����");
		
		is=socket.getInputStream();
		isr=new InputStreamReader(is);
		br=new BufferedReader(isr); //in
		
		os=socket.getOutputStream();
		osw=new OutputStreamWriter(os);
		bw=new BufferedWriter(osw); //out
		
	} catch (IOException e) {
		e.printStackTrace();
	} finally{
	}
   }

   private static void sayAll(String msg) {
	// TODO Auto-generated method stub
	
}

@Override
   public void actionPerformed(ActionEvent e) {
      // ��ư Ŭ�� �̺�Ʈ
      Object obj=e.getSource(); //�̺�Ʈ �߻� �ҽ�
      System.out.println(e.getSource());
      
      if(obj==btnSm){ //�ۼ��Ϸ� > ����
    	  String wrtxt = txtdia.getText();
    	  
			try {
				bw.write(wrtxt);
		    	bw.flush();
		    	
		    	if(bw!=null){
		    		System.out.println(wrtxt);
		    	}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
         File file = new File(title+".txt");
         try {
            if(!file.exists()){
               file.createNewFile();
            }
            FileOutputStream fos=new FileOutputStream(file); //Ÿ��Ʋ ���ϸ����� ����
            fos.write(txtdia.getText().getBytes()); //�Է� ���� ����
            fos.close();
            title=(file.getName());
            setTitle(title);
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }else if(obj==btnli){ //��� ��ư > ��� â ���
         JFrame subF = new JFrame("�Ϸ� �ϱ���");
         subF.setBounds(505, 100, 405, 480);
         subF.setLayout(new GridLayout(0,1));
         subF.setResizable(false);
         subF.setVisible(true);
         
         filenames = dir.list((f,name)->name.endsWith(".txt")); //����� �ϱ��� ��� > �迭
         btns=new Button[filenames.length];
         btns2=new Button[16-filenames.length]; //16�� ���
         if(filenames.length<=16){ //�ϱ��� ���̰� 16�� ����
            for (int i = 0; i < filenames.length; i++) { //�ϱ��� ����� �� �ҷ��� ��,
               btns[i]=new Button("�Ϸ� �ϱ��� " + filenames[i]);
               btns[i].addActionListener(this);
               subF.add(btns[i]);

               btns[i].revalidate();
               btns[i].repaint();
               
            }
            for (int i = filenames.length; i < (16-filenames.length); i++){ //�� ����� �߰�
               btns2[i]=new Button("");
               btns2[i].addActionListener(this); //>���Ŀ� Ŭ���ҽ� �ϱ⸦ �ۼ��ش޶�� ���� �ⷫ
               subF.add(btns2[i]);
               
               btns2[i].revalidate();
               btns2[i].repaint();
            }
         }else if(filenames.length>16){ //16�� �̻�
            for (int i = 0; i < filenames.length; i++) {
               btns[i]=new Button("�Ϸ� �ϱ��� " + filenames[i]);
               btns[i].addActionListener(this);
               subF.add(btns[i]);

               btns2[i].revalidate();
               btns2[i].repaint();
            }
         }
      }else{
    	  for(int i=0; i<filenames.length; i++){
    		  if(obj==btns[i]){
    			  JFrame Fr = new JFrame("�Ϸ� �ϱ���"); //��������â
    				Fr.setBounds(910, 100, 405, 480);
    				Fr.setLayout(null);
    				Fr.setResizable(false);
    				Fr.setVisible(true);
    		      
    				Panel p1=new Panel();
    				Fr.add(p1);
    				p1.setLayout(null);
    				p1.setBounds(0, 0, 500, 100);
    		   
    					Label lbday =new Label("����"); //�� - ����
    					lbday.setBounds(40, 50, 100, 20);
    					p1.add(lbday);
    					Label ladate =new Label(date); //�� - date : �ڵ�����
    					ladate.setBounds(140, 50, 250, 20);
    					p1.add(ladate);
    		         
    				Label lbtit=new Label("����"); //�� - ����
    				lbtit.setBounds(40, 80, 100, 20);
    				p1.add(lbtit);
    				Label latoti =new Label("�Ϸ� �ϱ��� " + filenames[i]); //�� title - ������Է�
    				latoti.setBounds(140, 80, 250, 20);
    				p1.add(latoti);
    				
    				Panel p2=new Panel();
    				Fr.add(p2);
    				p2.setLayout(null);
    				p2.setBounds(0, 100, 500, 300);
    				
    				Panel p20=new Panel();
    				p2.add(p20);
    				p20.setLayout(new GridLayout(17,1));
    				p20.setBounds(30, 20, 350, 250);
    				
		            String line = "";
		            int liCnt=0;
    				try {
						File file = new File(dir+"/"+filenames[i]);
						filereader = new FileReader(file);
						BufferedReader bufReader = new BufferedReader(filereader);

			            while((line = bufReader.readLine()) != null){
			            	if(liCnt<=15){
			            		latxt= new Label(line);
		    					latxt.addKeyListener(this);
		    					p20.add(latxt);
			            	}else if(liCnt>15){
			            		break;
			            	}
			            	liCnt++;
			            	
			            }
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
    					
    					Cnt = new Label("���ڼ� : " + latxt.getText().length());
    					Cnt.setBounds(320, 265, 150, 50);
    					p2.add(Cnt);
    					
    				Panel p3= new Panel();
    					Fr.add(p3);
    					p3.setLayout(null);
    					p3.setBounds(0, 400, 400,100);
    					p3.revalidate();
    					p3.repaint();
    		  }
    	  }
    	  for(int i=0; i<16-filenames.length; i++){
    		  if(obj==btns2[i]){
	    		JFrame Fr = new JFrame("�Ϸ� �ϱ���"); //��������â
				Fr.setBounds(505, 100, 405, 200);
				Fr.setLayout(new BorderLayout());
				Fr.setResizable(false);
				Fr.setVisible(true);
				
				Label lbcon =new Label("�ԷµǾ� �ִ� ������ �����ϴ�.");
				Fr.add(lbcon,BorderLayout.CENTER);
				lbcon.setAlignment(Label.CENTER);
	    	  }
    	  }
    	  
      }
   }

   private void addActionListener(ActionListener actionListener) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
      count = txtdia.getText().length();
      Cnt.setText("���ڼ� : " + count);
      
      int max = 500;
      if(txtdia.getText().length() > max+1) {
         e.consume();
         String lim = txtdia.getText().substring(0, max);
         txtdia.setText(lim);
      }else if(txtdia.getText().length() > max) {
         e.consume();
      }
   }

   @Override
   public void keyPressed(KeyEvent e) {
      // TODO Auto-generated method stub
      count = txtdia.getText().length();
      Cnt.setText("���ڼ� : " + count);
   }

   @Override
   public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      count = txtdia.getText().length();
      Cnt.setText("���ڼ� : " + count);

   }

}