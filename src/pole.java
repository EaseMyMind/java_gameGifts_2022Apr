// ����������� ����������� ���������
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

// ����� ������, ������� �������� ������� �����
class pole extends JPanel
{
	  private Image shapka; // �������� ���������� ������, � ������� ����������� �����
	  private Image fon; // �������� ���������� ������, � ������� ����������� ���
	  public int x = screen.width/2; // �������� ���������� ������, � ������� ����������� �����
	  private int slogn; // ���������� ��������� ����
	  private podar[] gamePodar; // ������ ��������
	  private Image end_game; // ����������� ��������� ����
	  public Timer timerUpdate,timerDraw; // ��� �������: ������ ��� 
	  int score=0;
	  int lives=5;
	  int y=screen.height-135;
	  public void audio(String name) { //ZVUK 1
		  try {
			  File muz = new File("./"+name);
			  AudioInputStream ais = AudioSystem.getAudioInputStream(muz);
			  Clip clip = AudioSystem.getClip();
			  clip.open(ais);
			  clip.setFramePosition(0);
			  clip.start();
		  }
		  catch (Exception e) {}
	  }
	  
	  
	   // ����������� ������ 
	   public pole(int slogn)
	   {		   
		   audio("start.wav");
		   this.slogn = slogn;			  
		   // �������� ����������� ����� �� �����
		   try
		   {
		     shapka = ImageIO.read(new File("./shapka.png"));
		   }
		   catch(IOException ex) {}
		   
		   // �������� ����������� ���� �� �����
		   try
		   {
		     fon = ImageIO.read(new File("./fon.png"));
		   }
		   catch(IOException ex) {}
		   
    	   // �������� ����������� ��������� ����
		   try
		   {
		     end_game = ImageIO.read(new File("./end_game.png"));
		   }
		   catch(IOException ex) {}		   
		
    		//  �������� ���� ����������� ��������
		   gamePodar = new podar[7];
		   for (int i=0;i<7;i++)
		   {
			   try
			   {
				   gamePodar[i] = new podar(ImageIO.read(new File("./s"+i+".png")));
			   }
			   catch (IOException ex) {}
		   }

		   // �������� �������, ������� ����� ��� � ��� ������� ��������� � ��������� ������� �� ������� ���� 		   
		   timerUpdate = new Timer(2000,new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
		           updateStart(); // ����� ��� �������� � ���������� �������� �� ������� ����
				}
			});		    
		   timerUpdate.start(); // ������ ������� timerUpdate 		   		   
		   
		   // �������� �������, ������� ����� �������������� ������� ���� 20 ��� � ������� 	   
		   timerDraw = new Timer(50,new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
		           repaint(); // ������ ������ ����������� ���� (public void paintComponent(Graphics gr))
				}
			});		    
		   timerDraw.start(); // ������ ������� ��� �����������
		    			    		    		    		    		    
	   }
	   	   
	   // �����, ������� ������������ ����������� ������� �� ������
	   public void paintComponent(Graphics gr)
	   {
		   // ��������� ��������� ������� ������ ����
		   super.paintComponent(gr);
		   gr.drawImage(fon, 0, 0, screen.width, screen.height, null); // ��������� ����
		   
		   gr.setColor(Color.RED);
		   gr.setFont(new Font("arial",0,40));
		   gr.drawString("Score: "+score, 50, 50);
		   
		   for (int i=0; i<lives; i++)
		   {
		       gr.drawImage(shapka,50+60*i,100,50,50,null);
		   }
		   
		    // ��������� �����
		   // ����, ������� ���������� ������� �� ������� ���� � ��������� ����������� �������
           for (int i=0;i<7;i++)
           {
        	       gamePodar[i].draw(gr); // ����������� �������
        	       if (gamePodar[i].act==true) // ���� ������� �� ������� �������� �������
        	       {
        		   if ((gamePodar[i].y+gamePodar[i].img.getHeight(null))>=y+5) // ���� ������� ������ ������ �������
        		   {
        			    if (Math.abs(gamePodar[i].x - x) > 75) // ���� ������� ��������
        			    {
        			    	lives--;
        			    	if (lives==0)
        			    	{
        			    	  audio("end.wav");
        			          gr.drawImage(end_game, 300, 300, null); // ����� �������� ��������� ����
        			    	  timerDraw.stop(); // ��������� ������� timerDraw 
        			    	  timerUpdate.stop(); // ��������� ������� timerUpdate
        			          break; // ���������� �����
        			    	}
        			    	gamePodar[i].act=false;
        			    	audio("bad.wav");
        			    }
        			    else
        			    {
        			    	score+=10;
        			    	gamePodar[i].act=false; // ������ ������� � �������� ����, ���� �� ������ ������
        			    	audio("good.wav");
        			    }
        			 }
        	       }
           }
           //LINII
           gr.setColor(Color.RED);
           gr.drawLine(0, y, screen.width, y);
           gr.drawLine(x+50, 0, x+50, screen.height);
           gr.drawImage(shapka, x, y, null);
	   }
    	// ����� ��� �������� � ���������� �������� �� ������� ����
	   private void updateStart()
	   {
           int kol=0; // ���������� ��� �������� �������� �� ������� ����
           for (int i=0;i<7;i++) // ���� �������� ���� �������� �������
           {
              if (gamePodar[i].act==false) // ���� ������� �� �� ������� ����
              {
                 if (kol<slogn) // ���� ������� ���������� ����� ������ ��������� (�� 1 �� 7)
                 {
                   gamePodar[i].start(); // ����������� ������� �� ������� ����, ����� ��� ������ �������� ����
                   break; // ���������� �����
                 }
              }
              else kol++; // ���� ������� �� ������� ����
           }
	   }	   
}