import utils.Observer;
import utils.QueryType;
import utils.Record;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class View extends JFrame implements Observer<Record> {
    private static final String TITLE = "Movie Ontology";
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 500;
    private static final int ROWS = 5;
    private static final int COLS = 2;
    private static final int HGAP = 4;
    private static final int VGAP = 4;
    private static final int CONTROL_PANEL_PADDING_TOP = 5;
    private static final int CONTROL_PANEL_PADDING_BOTTOM = 5;
    private static final int CONTROL_PANEL_PADDING_RIGHT = 20;
    private static final int CONTROL_PANEL_PADDING_LEFT = 20;
    private static final String JBUTTON_LABEL = "Query selezionabili:";
    private static final String SUBMIT_BUTTON = "OK";
    private final Controller controller;
    private final JRadioButton query2;
    private final JRadioButton query1;
    private final JRadioButton query3;
    private final JRadioButton query4;
    private final JTextArea textArea;
    final JPanel visualArea;

    public View(final Controller controller) {
        this.controller = controller;
        setTitle(TITLE);
        final Dimension size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setSize(size);
        setResizable(false);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent ev){
                System.exit(-1);
            }
            public void windowClosed(WindowEvent ev){
                System.exit(0);
            }
        });

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);

        final JPanel queryArea = new JPanel();
        queryArea.setLayout(new GridLayout(ROWS, COLS, HGAP, VGAP));
        mainPanel.add(queryArea, BorderLayout.WEST);
        queryArea.setBorder(
                new EmptyBorder(
                        CONTROL_PANEL_PADDING_TOP,
                        CONTROL_PANEL_PADDING_LEFT,
                        CONTROL_PANEL_PADDING_BOTTOM,
                        CONTROL_PANEL_PADDING_RIGHT
                ));

        queryArea.add(new JLabel(JBUTTON_LABEL));
        ButtonGroup group = new ButtonGroup();
        query1 = new JRadioButton(QueryType.QUERY1.getTitle());
        query2 = new JRadioButton(QueryType.QUERY2.getTitle());
        query3 = new JRadioButton(QueryType.QUERY3.getTitle());
        query4 = new JRadioButton(QueryType.QUERY4.getTitle());
        group.add(query1);
        group.add(query2);
        group.add(query3);
        group.add(query4);
        queryArea.add(query1);
        queryArea.add(query2);
        queryArea.add(query3);
        queryArea.add(query4);
        query1.setSelected(true);

        visualArea = new JPanel();
        mainPanel.add(visualArea, BorderLayout.CENTER);
        visualArea.setLayout(new BorderLayout());
        visualArea.setBorder(
                new EmptyBorder(
                        CONTROL_PANEL_PADDING_TOP,
                        CONTROL_PANEL_PADDING_LEFT,
                        CONTROL_PANEL_PADDING_BOTTOM,
                        CONTROL_PANEL_PADDING_RIGHT
                )
        );
        textArea = new JTextArea();
        visualArea.add(textArea);

        JButton submit = new JButton(SUBMIT_BUTTON);
        submit.addActionListener(e -> startSimulation());
        mainPanel.add(submit, BorderLayout.SOUTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startSimulation() {
        QueryType queryType =  QueryType.QUERY1;
        if (query2.isSelected()) {
            queryType = QueryType.QUERY2;
        } else if (query3.isSelected()) {
            queryType = QueryType.QUERY3;
        } else if (query4.isSelected()){
            queryType = QueryType.QUERY4;
        }
        setLabelInTextArea(queryType);
        this.controller.startSimulation(queryType);
    }


    @Override
    public void updateResult(List<Record> list) {
        SwingUtilities.invokeLater(() -> {
            for (Record r:list) {
                textArea.append(r.toString());
            }
        });
    }

    @Override
    public void notifyError(String error) {

    }

    private void setLabelInTextArea(QueryType queryType){
        this.textArea.setText("");
        textArea.setColumns(queryType.getNumParameters());
        textArea.setTabSize(20);
        StringBuilder str = null;
        for (String s: queryType.getResult().getLabel()) {
            if(str != null){
                str.append("\t").append(s);
            } else {
                str = new StringBuilder(s);
            }
        }
        this.textArea.setText(str + "\n");
    }
}
