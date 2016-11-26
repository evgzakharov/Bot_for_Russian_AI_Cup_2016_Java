import model.Game;
import model.Move;
import model.Wizard;
import model.World;

import java.util.List;

import static java.lang.StrictMath.*;

public class MoveHelper {

    private Wizard self;
    private World world;
    private Game game;
    private Move move;

    public MoveHelper(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
    }

    public void goTo(Point2D point) {
        Point2D correctedPoint = correctPoint(point);

        double angle = self.getAngleTo(correctedPoint.getX(), correctedPoint.getY());
        move.setTurn(angle);

        goWithoutTurn(point);
    }

    public void goWithoutTurn(Point2D point) {
        Point2D correctedPoint = correctPoint(point);

        double diffAngle = self.getAngleTo(correctedPoint.getX(), correctedPoint.getY());

        double backCoef = cos(diffAngle);
        double strickCoef = sin(diffAngle);

        if (abs(diffAngle) > PI / 2) {
            move.setSpeed(game.getWizardBackwardSpeed() * backCoef);
        } else {
            move.setSpeed(game.getWizardForwardSpeed() * backCoef);
        }
        move.setStrafeSpeed(game.getWizardStrafeSpeed() * strickCoef);
    }

    public Point2D correctPoint(Point2D point2D) {
        WayFinder wayFinder = new WayFinder(self, world, game);
        List<Point2D> way = wayFinder.findWay(point2D);

        if (way != null && way.size() > 0) {
            return way.get(0);
        }

        return point2D;
    }


}
