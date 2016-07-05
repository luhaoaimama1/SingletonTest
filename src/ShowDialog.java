import javax.swing.*;
import java.awt.event.*;

public class ShowDialog extends JDialog {
    private  Callback callback;
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton buttonOK;
    private JTextField textField1;
    private JComboBox comboBox1;


    public ShowDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        for (GenerateType type : GenerateType.values()) {
            comboBox1.addItem(type.name());
        }
        comboBox1.setSelectedIndex(0);
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here
        callback.showDialogResult(textField1.getText().toString(),GenerateType.valueOf((String)comboBox1.getSelectedItem()));
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ShowDialog dialog = new ShowDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
    public void addCallback(Callback callback){
        this.callback=callback;
    }
    public interface Callback{
        void showDialogResult(String name, GenerateType type);
    }
}
