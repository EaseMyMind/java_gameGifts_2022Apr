// ��������� ��� ������, ���� ����������!
// package game4;


// ����������� ����������� ���������
// ����������� ����������� ���������
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

class screen
{
	public static int width, height;
}

// ������� ����� ����
public class game {

	// ������� �����, ������� ��������� ����
	public static void main(String[] args) {
		
		Dimension d = 
				Toolkit.getDefaultToolkit().getScreenSize();
		screen.width = d.width;
		screen.height = d.height;
		
		   // ����� ����������� ���� ��� ����� ��������� ����
		   String rez = JOptionPane.showInputDialog(null,"������� ��������� ���� �� 1 �� 7:","��������� ����",1);
		   
		   // ��������� ���������� ������ � ���������� ������ ����
		   int slogn = rez.charAt(0)-'0';
		   
		   // ��������, ��� ������� ����� �� 1 �� 7
		   if ((slogn>=1)&&(slogn<=7))
		   {
    	      // �������� ����, � ������� ��������� ������� ����
		      okno window = new okno(slogn);
		   }
	}

}
