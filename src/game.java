// Указываем имя пакета, если необходимо!
// package game4;


// Подключения необходимых библиотек
// Подключения необходимых библиотек
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

class screen
{
	public static int width, height;
}

// Главный класс игры
public class game {

	// Главный метод, который запускает игру
	public static void main(String[] args) {
		
		Dimension d = 
				Toolkit.getDefaultToolkit().getScreenSize();
		screen.width = d.width;
		screen.height = d.height;
		
		   // Вызов диалогового окна для ввода сложности игры
		   String rez = JOptionPane.showInputDialog(null,"Введите сложность игры от 1 до 7:","Сложность игры",1);
		   
		   // Помещение результата выбора в переменную целого типа
		   int slogn = rez.charAt(0)-'0';
		   
		   // Проверка, что введена цифра от 1 до 7
		   if ((slogn>=1)&&(slogn<=7))
		   {
    	      // Создание окна, в котором находится игровое поле
		      okno window = new okno(slogn);
		   }
	}

}
