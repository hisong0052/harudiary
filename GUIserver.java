package com.hi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.processing.FilerException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIserver extends Frame implements ActionListener, KeyListener{
   
	String date = new SimpleDateFormat("yy-MM-dd").format(new Date());
	String todaytitle = "�����߰�";
	String title=date+" ["+todaytitle+"]";
   
	JFrame Fr,subF;
	Button btnli;
	TextArea txtdia;
	Label Cnt, latxt ,latoti;
	int count = 0;
	
	static File dir= new File("C:/ohsong/project");; //�ϱ���������
	static FileReader filereader;
   
   String[] filenames;
   Button[] btns;
   Button[] btns2;
   
   public GUIserver(){
       JFrame subF = new JFrame("�Ϸ� �ϱ���"); //������ ���� ����â
       subF.setBounds(105, 100, 405, 480);
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
          }
          for (int i = filenames.length; i < (16-filenames.length); i++){ //�� ����� �߰�
             btns2[i]=new Button("");
             btns2[i].addActionListener(this); //>���Ŀ� Ŭ���ҽ� �ϱ⸦ �ۼ��ش޶�� ���� ���
             subF.add(btns2[i]);
             btns2[i].revalidate();
             btns2[i].repaint();
          }
       }else if(filenames.length>16){ //16�� �̻�
          for (int i = 0; i < filenames.length; i++) {
             btns[i]=new Button("�Ϸ� �ϱ��� " + filenames[i]);
             btns[i].addActionListener(this);
             subF.add(btns[i]);
          }
       }

   }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new GUIserver();
      
      ServerSocket server = null;
      Socket socket = null;
      BufferedReader br = null;
      BufferedWriter bw = null;
      InputStream is=null;
      OutputStream os=null;
      InputStreamReader isr=null;
      OutputStreamWriter osw=null;
      
      try {
  		server = new ServerSocket(9999);
  		System.out.println("������");
  		
  		socket=server.accept();
  		System.out.println("����");
  		
  	  		is=socket.getInputStream();
  	  		isr=new InputStreamReader(is);
  	  		br=new BufferedReader(isr); //in
  	  		
  	  		os=socket.getOutputStream();
  	  		osw=new OutputStreamWriter(os);
  	  		bw=new BufferedWriter(osw); //out
  		
  		while(true){
  			String clitxt=br.readLine();
  			bw.write(clitxt);
  			System.out.println(clitxt);
  		}
  		
  	} catch (IOException e) {
  		e.printStackTrace();
  	} finally{
  		try {
  			br.close();
  			bw.close();
  			socket.close();
  			server.close();
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  	}
      
   }

   private static void sayAll(String msg) {
	// TODO Auto-generated method stub
	
}

@Override
   public void keyPressed(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void keyReleased(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void keyTyped(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
	   Object obj=e.getSource(); //�̺�Ʈ �߻� �ҽ�
	      System.out.println(e.getSource());
	      
	   for(int i=0; i<filenames.length; i++){
		   if(obj==btns[i]){
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
				
				latoti =new Label(filenames[i]); //�� title - ������Է�
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
		   }for(int i=0; i<16-filenames.length; i++){
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