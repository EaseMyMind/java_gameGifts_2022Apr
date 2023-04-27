// Подключения необходимых библиотек
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

// Класс панели, которая является игровым полем
class pole extends JPanel
{
	  private Image shapka; // Закрытая Переменная класса, в которую загружается шапка
	  private Image fon; // Закрытая Переменная класса, в которую загружается фон
	  public int x = screen.width/2; // Открытая Переменная класса, в которую загружается шапка
	  private int slogn; // Переменная сложности игры
	  private podar[] gamePodar; // Массив подарков
	  private Image end_game; // Изображение Окончания игры
	  public Timer timerUpdate,timerDraw; // Два таймера: первый для 
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
	  
	  
	   // Конструктор класса 
	   public pole(int slogn)
	   {		   
		   audio("start.wav");
		   this.slogn = slogn;			  
		   // Загрузка изображения шапки из файла
		   try
		   {
		     shapka = ImageIO.read(new File("./shapka.png"));
		   }
		   catch(IOException ex) {}
		   
		   // Загрузка изображения фона из файла
		   try
		   {
		     fon = ImageIO.read(new File("./fon.png"));
		   }
		   catch(IOException ex) {}
		   
    	   // Загрузка изображения Окончания игры
		   try
		   {
		     end_game = ImageIO.read(new File("./end_game.png"));
		   }
		   catch(IOException ex) {}		   
		
    		//  Загрузка семи изображений подарков
		   gamePodar = new podar[7];
		   for (int i=0;i<7;i++)
		   {
			   try
			   {
				   gamePodar[i] = new podar(ImageIO.read(new File("./s"+i+".png")));
			   }
			   catch (IOException ex) {}
		   }

		   // Создание таймера, который будет раз в три секунды проверять и добавлять подарки на игровое поле 		   
		   timerUpdate = new Timer(2000,new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
		           updateStart(); // Метод для проверки и добавление подарков на игровое поле
				}
			});		    
		   timerUpdate.start(); // Запуск таймера timerUpdate 		   		   
		   
		   // Создание таймера, который будет перерисовывать игровое поле 20 раз в секунду 	   
		   timerDraw = new Timer(50,new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
		           repaint(); // Запуск метода перерисовки поля (public void paintComponent(Graphics gr))
				}
			});		    
		   timerDraw.start(); // Запуск таймера для перерисовки
		    			    		    		    		    		    
	   }
	   	   
	   // Метод, который отрисовывает графические объекты на панели
	   public void paintComponent(Graphics gr)
	   {
		   // Выполнить отрисовку сначала самого окна
		   super.paintComponent(gr);
		   gr.drawImage(fon, 0, 0, screen.width, screen.height, null); // Рисование фона
		   
		   gr.setColor(Color.RED);
		   gr.setFont(new Font("arial",0,40));
		   gr.drawString("Score: "+score, 50, 50);
		   
		   for (int i=0; i<lives; i++)
		   {
		       gr.drawImage(shapka,50+60*i,100,50,50,null);
		   }
		   
		    // Рисование шапки
		   // Цикл, который отображает подарки на игровом поле и проверяет пропущенные подарки
           for (int i=0;i<7;i++)
           {
        	       gamePodar[i].draw(gr); // Отображение подарка
        	       if (gamePodar[i].act==true) // Если подарок из массива подарков активен
        	       {
        		   if ((gamePodar[i].y+gamePodar[i].img.getHeight(null))>=y+5) // Если подарок достиг нижней границы
        		   {
        			    if (Math.abs(gamePodar[i].x - x) > 75) // Если подарок пропущен
        			    {
        			    	lives--;
        			    	if (lives==0)
        			    	{
        			    	  audio("end.wav");
        			          gr.drawImage(end_game, 300, 300, null); // Вывод картинки Окончания игры
        			    	  timerDraw.stop(); // Оставнока таймера timerDraw 
        			    	  timerUpdate.stop(); // Оставнока таймера timerUpdate
        			          break; // Прерывание цикла
        			    	}
        			    	gamePodar[i].act=false;
        			    	audio("bad.wav");
        			    }
        			    else
        			    {
        			    	score+=10;
        			    	gamePodar[i].act=false; // Снятие подарка с игрового поля, если он пойман шапкой
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
    	// Метод для проверки и добавление подарков на игровое поле
	   private void updateStart()
	   {
           int kol=0; // Переменная для подсчета подарков на игровом поле
           for (int i=0;i<7;i++) // Цикл перебора всех подарков массива
           {
              if (gamePodar[i].act==false) // Если подарок не на игровом поле
              {
                 if (kol<slogn) // Если текущее количество менее номера сложности (от 1 до 7)
                 {
                   gamePodar[i].start(); // Активизация подарка на игровом поле, вывод его сверху игрового поля
                   break; // Прерывание цикла
                 }
              }
              else kol++; // Если подарок на игровом поле
           }
	   }	   
}