package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_WEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private final double windowWidth;
    private final double windowWeight;
    private final Point windowCoordinates;

    /**
     * Constructs a {@code GuiSettings} with the default weight, width and position.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowWeight = DEFAULT_WEIGHT;
        windowCoordinates = null; // null represent no coordinates
    }

    /**
     * Constructs a {@code GuiSettings} with the specified weight, width and position.
     */
    public GuiSettings(double windowWidth, double windowWeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowWeight = windowWeight;
        windowCoordinates = new Point(xPosition, yPosition);
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowWeight() {
        return windowWeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuiSettings)) {
            return false;
        }

        GuiSettings otherGuiSettings = (GuiSettings) other;
        return windowWidth == otherGuiSettings.windowWidth
                && windowWeight == otherGuiSettings.windowWeight
                && Objects.equals(windowCoordinates, otherGuiSettings.windowCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowWeight, windowCoordinates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("windowWidth", windowWidth)
                .add("windowWeight", windowWeight)
                .add("windowCoordinates", windowCoordinates)
                .toString();
    }
}
