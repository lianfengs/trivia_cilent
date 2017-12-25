package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class JFrame_Test extends JFrame {

	private JPanel contentPane;
	JLabel[] ready=new JLabel[4];
	JLabel[] nickname=new JLabel[4];
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame_Test frame = new JFrame_Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrame_Test() {
		setResizable(false);
		setTitle("\u51C6\u5907\u5BA4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Color border=new Color(0,88,169);
		Color back=new Color(199,229,248);
		JLabel panel_1 = new JLabel("New label");
		panel_1.setOpaque(true);
		panel_1.setBackground(back);
		panel_1.setBounds(90, 220, 40, 40);
		panel_1.setBorder(BorderFactory.createLineBorder(border));
		contentPane.add(panel_1);
		
		JLabel panel_2 = new JLabel("New label");
		panel_2.setBounds(90, 330, 40, 40);
		contentPane.add(panel_2);
		
		JLabel panel_3 = new JLabel("New label");
		panel_3.setBounds(90, 440, 40, 40);
		contentPane.add(panel_3);
		
		JLabel panel_4 = new JLabel("New label");
		panel_4.setBounds(170, 110, 311, 40);
		contentPane.add(panel_4);
		
		JLabel panel_5 = new JLabel("New label");
		panel_5.setBounds(170, 220, 311, 40);
		contentPane.add(panel_5);
		
		JLabel panel_6 = new JLabel("New label");
		panel_6.setBounds(170, 330, 311, 40);
		contentPane.add(panel_6);
		
		JLabel panel_7 = new JLabel("New label");
		panel_7.setBounds(170, 440, 311, 40);
		contentPane.add(panel_7);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(549, 388, 151, 40);
		contentPane.add(btnNewButton);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(549, 173, 151, 40);
		contentPane.add(button_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 10, 79, 45);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(90, 110, 40, 40);
		contentPane.add(lblNewLabel_1);
	}
}
