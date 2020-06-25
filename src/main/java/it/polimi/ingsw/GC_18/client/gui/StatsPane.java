package it.polimi.ingsw.GC_18.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.polimi.ingsw.GC_18.client.Controller;
import it.polimi.ingsw.GC_18.client.State;
import it.polimi.ingsw.GC_18.client.View;

/**
 * Handles the display of the statistics to the user using a JTable
 */
public class StatsPane extends JPanel {

    private static final long serialVersionUID = -6380439184746267156L;
    private static final String[] headers = new String[] { "Username", "Games Played", "Games won", "Games lost", "Total victory points" };

    /**
     * Displays the global statistics of all players in a JTable that can be sorted
     * @param globalStats - the global statistics array.
     * The outer array contains the statistics divided by clients
     * The inner array contains the user name, the games player, the games won,
     * the games lost and the total victory points for that player
     */
    private StatsPane(String[][] globalStats) {
        setBackground(new Color(238, 238, 238));// a nice grey background
        setLayout(new BorderLayout());
        JPanel statsPane = new JPanel();
        statsPane.add(createRankPane(globalStats));
        statsPane.setBorder(BorderFactory.createTitledBorder("GLOBAL RANK"));
        JButton btnMenu = new JButton("RETURN TO MENU");
        btnMenu.addActionListener(e -> View.enterMenu());
        add(statsPane, BorderLayout.CENTER);
        add(btnMenu, BorderLayout.SOUTH);
    }

    @SuppressWarnings("serial")
    private JScrollPane createRankPane(String[][] globalStats) {
        JTable rankTable = new JTable(globalStats, headers) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        // Adding sort functionality to the table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(rankTable.getModel());
        rankTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        return new JScrollPane(rankTable);
    }

    /**
     * Displays to GUI and outputs to CLI the global statistics 
     * of all players in a JTable that can be sorted
     * @param statsFile - the global statistics array.
     * The outer array contains the statistics divided by clients
     * The inner array contains the user name, the games player, the games won,
     * the games lost and the total victory points for that player
     */
    public static void updateAndShowStats(String statsFile) {
        Controller.setState(State.VIEWING_OPTIONS);
        System.out.println("USERS STATS:" + "\nUsername - games played - games won - games lost - total victory points"
                + "\n" + statsFile);
        String[] rows = statsFile.split("\n");
        String[][] stats = new String[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            stats[i] = rows[i].split(" - ");
        }
        View.changeMainFrameContent(new StatsPane(stats));
    }

}
