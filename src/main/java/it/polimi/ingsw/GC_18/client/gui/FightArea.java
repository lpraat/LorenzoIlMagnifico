package it.polimi.ingsw.GC_18.client.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polimi.ingsw.GC_18.model.Game;
import it.polimi.ingsw.GC_18.model.Player;
import it.polimi.ingsw.GC_18.model.gamepieces.FightSpot;
import it.polimi.ingsw.GC_18.utils.AssetsLoader;
import it.polimi.ingsw.GC_18.utils.Utils;

/**
 * This class displays and handles the fight area seen on GUI.
 */
public class FightArea extends GameGuiComponent {

    private static final long serialVersionUID = 5959361264873780907L;
    private static final String FIGHT_AREA_IMG = "fightScreen.png";

    private String prizeType = "money";
    private String prizeValue = "0";

    private transient List<Ellipse2D> circles = new ArrayList<>();
    private Map<Player, FightSpot> pawnsPlaced = new HashMap<>();

    /**
     * Sets up a JPanel containing the fight area screen.
     * The screen has a back ground image on the top of which there's a shape of a regular pentagon
     * that in each angle has a circle that contains the pawns places by the players (if there are any)
     * If this panel is clicked a place action is triggered
     */
    FightArea() {
        super(FIGHT_AREA_IMG);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ActionSender.sendPlaceCommand("FIGHT_SPOT");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // nothing to do here!
            }
        });
    }

    /**
     * Updates the GUI of this screen accordingly to model changes
     * @param game -  the game instance from which this method takes data to update in GUI
     */
    public void update(Game game) {
        prizeType = game.getBoard().getFightSpace().getPriceType(game.getTurnInfo()[0]-1);
        prizeValue = String.valueOf(game.getPlayers().stream().mapToInt(Player::getMilitaryPoints).sum());
        pawnsPlaced = new HashMap<>();
        for (Player player : game.getPlayers()) {
            pawnsPlaced.put(player, game.getBoard().getFightSpace().getPlayerSpot(player));
        }
        repaint();
    }

    @Override
    protected void fillImageHandler() {
        // the images for this class are handled manually
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // drawing a shape similar to a regular polygon where the pawns can be set
        drawPolygonalShape(g, GameGui.getNumPlayersForFightMode());
        // drawing prize in the center
        drawPrizeType(g);
        drawPrizeInfo(g);
        // drawing pawns and points for each player
        drawPawnsAndPoints(g);
    }

    /**
     * Draws the pawns that each player has placed in the fight spot and the battle point he has
     * @param g - the Graphics of this JPanel
     */
    private void drawPawnsAndPoints(Graphics g) {
        int i = 0;
        for (Entry<Player, FightSpot> e : pawnsPlaced.entrySet()) {
            int centerx = (int) circles.get(i).getCenterX();
            int centery = (int) circles.get(i).getCenterY();
            int polygonRadius = (int) circles.get(i).getHeight();
            int n = e.getValue().getPawns().size();
            for (int j = 0; j < n; j++) {
                // drawing pawns
                double angle = j * Math.PI / 2 + Math.PI / 4;
                Point p = new Point((int) (centerx + polygonRadius / 2 * Math.cos(angle)),
                        (int) (centery + polygonRadius / 2 * Math.sin(angle)));
                String pawnImageName = GameInfoGui.getPawnColor(e.getValue().getPawns().get(j))
                        + GameInfoGui.getPlayerColor(e.getKey()) + "Pawn.png";
                g.drawImage(AssetsLoader.getAssets().get(pawnImageName), p.x - polygonRadius / 6,
                        p.y - polygonRadius / 6, polygonRadius / 3, polygonRadius / 3, null);
                // drawing points
                Utils.drawCenteredTextInRectangle(g, circles.get(i).getBounds(),
                        String.valueOf(e.getValue().getBattlePoints()));
            }
            i++;
            if (i >= GameGui.getNumPlayersForFightMode())
                break;
        }
    }

    /**
     * Draws the image associated to the prize type in the center of the screen
     * @param g - the Graphics to use to draw the image
     */
    private void drawPrizeType(Graphics g) {
        int prizeWidth = (int) (getWidth() / 6 * getScaleX());
        int prizeHeight = (int) (getHeight() / 6 * getScaleY());
        int x = getWidth() / 2 - prizeWidth / 2;
        int y = getHeight() / 2 - prizeHeight / 2;
        g.drawImage(AssetsLoader.getAssets().get(prizeType + ".png"), x, y, prizeWidth, prizeHeight, null);
    }

    /**
     * Draws in the top of the screen a String representing the value and the type of the battle prize
     * @param g - the Graphics to use to draw the String of information
     */
    private void drawPrizeInfo(Graphics g) {
        String text = "CURRENT PRIZE: " + prizeValue + " " + prizeType.toUpperCase();
        Utils.drawCenteredTextInRectangle(g, new Rectangle(0, 0, getWidth(), 64), text);
    }

    /**
     * Draws a regular polygon of n sides with filled circles in each angle
     * that represents the spots where the players can place the pawns
     * @param g - the Graphics to use to draw the image
     * @param n - the number of angles of the polygon to draw
     */
    private void drawPolygonalShape(Graphics g, int n) {
        int centerx = getWidth() / 2;
        int centery = getHeight() / 2;
        int polygonRadius = Math.max(getWidth(), getHeight()) / 4;
        g.setColor(new Color(168, 134, 115));// orange
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(polygonRadius / 5));
        circles.clear();
        for (int i = 0; i < n; i++) {
            circles.add(new Ellipse2D.Double(
                    centerx - polygonRadius / 2
                    + polygonRadius * (Math.cos(2 * Math.PI * i / n + Math.PI / 2 - Math.PI / n)),
                    centery - polygonRadius / 2
                    + polygonRadius * (Math.sin(2 * Math.PI * i / n + Math.PI / 2 - Math.PI / n)),
                    polygonRadius, polygonRadius));
            g2.fill(circles.get(i));
        }
    }

}
