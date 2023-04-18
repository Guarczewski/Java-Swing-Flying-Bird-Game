package Custom_Frames;

import Custom_Elements.CButton;
import Custom_Elements.CLabel;
import Custom_Objects.SavedObject;
import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CScoreboardFrame extends JFrame {

    public CScoreboardFrame() {
        super("Full Scoreboard Frame");
        setBounds(Main.WINDOW_WIDTH * 2, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        setResizable(false);

        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(Color.DARK_GRAY);

        JPanel ScoreboardListPanel = new JPanel();
        ScoreboardListPanel.setLayout(new BoxLayout(ScoreboardListPanel, BoxLayout.Y_AXIS));
        ScoreboardListPanel.setBackground(Color.DARK_GRAY);

        JScrollPane scoreboardScroll = new JScrollPane(ScoreboardListPanel);
        scoreboardScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        List<SavedObject> savedObjectList = SavedObject.Read();

        for (int i = 0; i < savedObjectList.size(); i++) {
            ScoreboardListPanel.add(new CLabel(savedObjectList.get(i).toScoreboardString(i), 18,Color.ORANGE));
        }

        CButton closePortListFrameButton = new CButton("Close", 16, Color.RED, Color.WHITE);
        closePortListFrameButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dispose();
            }
        });

        jPanel.add(new CLabel("TOP PLAYER LIST", 32, Color.WHITE), BorderLayout.NORTH);
        jPanel.add(scoreboardScroll, BorderLayout.CENTER);
        jPanel.add(closePortListFrameButton, BorderLayout.SOUTH);

        setContentPane(jPanel);
        setVisible(true);

    }

    private void Dispose() {
        this.dispose();
    }
}