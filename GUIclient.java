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
	
	static File dir= new File("C:/ohsong/project");; //일기장저장경로
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
		
		JFrame Fr = new JFrame("하루 일기장"); //메인접속창
		Fr.setBounds(100, 100, 405, 480);
		Fr.setLayout(null);
		Fr.setResizable(false);
		Fr.setVisible(true);
      
		Panel p1=new Panel();
		Fr.add(p1);
		p1.setLayout(null);
		p1.setBounds(0, 0, 500, 100);
   
			Label lbday =new Label("일자"); //라벨 - 일자
			lbday.setBounds(40, 50, 100, 20);
			p1.add(lbday);
			Label ladate =new Label(date); //라벨 - date : 자동기입
			ladate.setBounds(140, 50, 250, 20);
			p1.add(ladate);
         
		Label lbtit=new Label("제목"); //라벨 - 제목
		lbtit.setBounds(40, 80, 100, 20);
		p1.add(lbtit);
		Label latoti =new Label(todaytitle); //라벨 title - 사용자입력
		latoti.setBounds(140, 80, 250, 20);
		p1.add(latoti);
         
		Panel p2=new Panel();
		Fr.add(p2);
		p2.setLayout(null);
		p2.setBounds(0, 100, 500, 300);
			txtdia= new TextArea("",0,0, TextArea.SCROLLBARS_VERTICAL_ONLY); //txt영역 - 사용자입력
			txtdia.setBounds(30, 20, 350, 250);
			txtdia.addKeyListener(this);
			p2.add(txtdia);
			Cnt = new Label("글자수 : " + txtdia.getText().length());
			Cnt.setBounds(320, 265, 150, 50);
			p2.add(Cnt);
      
		Panel p3= new Panel();
			Fr.add(p3);
			p3.setLayout(new FlowLayout());
			p3.setBounds(0, 400, 400,100);
			p3.revalidate();
			p3.repaint();
		btnli = new Button("목록");
		p3.add(btnli);
		btnli.addActionListener(this);
		btnli.revalidate();
		btnli.repaint();
				
        btnSm = new Button("작성완료");
        p3.add(btnSm);
        btnSm.addActionListener(this);
        btnSm.revalidate();
        btnSm.repaint();
	}

   public static void main(String[] args) {
      // TODO Auto-generated method stub
	   
	   Scanner scanner = new Scanner(System.in);
	   System.out.print("오늘의 다이어리 제목을 정해주세요.");
	   todaytitle = scanner.nextLine();
     
	   new GUIclient();
      
      try {
		socket = new Socket("localhost",9999);
		System.out.println("서버에 연결");
		
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
      // 버튼 클릭 이벤트
      Object obj=e.getSource(); //이벤트 발생 소스
      System.out.println(e.getSource());
      
      if(obj==btnSm){ //작성완료 > 저장
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
            FileOutputStream fos=new FileOutputStream(file); //타이틀 파일명으로 저장
            fos.write(txtdia.getText().getBytes()); //입력 내용 저장
            fos.close();
            title=(file.getName());
            setTitle(title);
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }else if(obj==btnli){ //목록 버튼 > 목록 창 출력
         JFrame subF = new JFrame("하루 일기장");
         subF.setBounds(505, 100, 405, 480);
         subF.setLayout(new GridLayout(0,1));
         subF.setResizable(false);
         subF.setVisible(true);
         
         filenames = dir.list((f,name)->name.endsWith(".txt")); //저장된 일기장 목록 > 배열
         btns=new Button[filenames.length];
         btns2=new Button[16-filenames.length]; //16개 출력
         if(filenames.length<=16){ //일기장 길이가 16개 이하
            for (int i = 0; i < filenames.length; i++) { //일기장 목록을 다 불러낸 후,
               btns[i]=new Button("하루 일기장 " + filenames[i]);
               btns[i].addActionListener(this);
               subF.add(btns[i]);

               btns[i].revalidate();
               btns[i].repaint();
               
            }
            for (int i = filenames.length; i < (16-filenames.length); i++){ //빈 목록을 추가
               btns2[i]=new Button("");
               btns2[i].addActionListener(this); //>추후에 클릭할시 일기를 작성해달라는 문장 출략
               subF.add(btns2[i]);
               
               btns2[i].revalidate();
               btns2[i].repaint();
            }
         }else if(filenames.length>16){ //16개 이상
            for (int i = 0; i < filenames.length; i++) {
               btns[i]=new Button("하루 일기장 " + filenames[i]);
               btns[i].addActionListener(this);
               subF.add(btns[i]);

               btns2[i].revalidate();
               btns2[i].repaint();
            }
         }
      }else{
    	  for(int i=0; i<filenames.length; i++){
    		  if(obj==btns[i]){
    			  JFrame Fr = new JFrame("하루 일기장"); //메인접속창
    				Fr.setBounds(910, 100, 405, 480);
    				Fr.setLayout(null);
    				Fr.setResizable(false);
    				Fr.setVisible(true);
    		      
    				Panel p1=new Panel();
    				Fr.add(p1);
    				p1.setLayout(null);
    				p1.setBounds(0, 0, 500, 100);
    		   
    					Label lbday =new Label("일자"); //라벨 - 일자
    					lbday.setBounds(40, 50, 100, 20);
    					p1.add(lbday);
    					Label ladate =new Label(date); //라벨 - date : 자동기입
    					ladate.setBounds(140, 50, 250, 20);
    					p1.add(ladate);
    		         
    				Label lbtit=new Label("제목"); //라벨 - 제목
    				lbtit.setBounds(40, 80, 100, 20);
    				p1.add(lbtit);
    				Label latoti =new Label("하루 일기장 " + filenames[i]); //라벨 title - 사용자입력
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
    					
    					Cnt = new Label("글자수 : " + latxt.getText().length());
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
	    		JFrame Fr = new JFrame("하루 일기장"); //메인접속창
				Fr.setBounds(505, 100, 405, 200);
				Fr.setLayout(new BorderLayout());
				Fr.setResizable(false);
				Fr.setVisible(true);
				
				Label lbcon =new Label("입력되어 있는 내용이 없습니다.");
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
      Cnt.setText("글자수 : " + count);
      
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
      Cnt.setText("글자수 : " + count);
   }

   @Override
   public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      count = txtdia.getText().length();
      Cnt.setText("글자수 : " + count);

   }

}