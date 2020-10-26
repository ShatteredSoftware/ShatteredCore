package com.github.shatteredsuite.core.util;

import org.bukkit.Location;

public class CoordinateUtil {
    private CoordinateUtil() {}

    /**
     * Returns the 2D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @return The distance between the two points.
     */
    public static int distance2D(int x1, int y1, int x2, int y2) {
        return Math.round(distance2D(x1, y1, x2, (float) y2));
    }

    /**
     * Returns the 2D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @return The distance between the two points.
     */
    public static float distance2D(float x1, float y1, float x2, float y2) {
        return (float) distance2D(x1, y1, x2, (double) y2);
    }

    /**
     * Returns the 2D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @return The distance between the two points.
     */
    public static long distance2D(long x1, long y1, long x2, long y2) {
        return Math.round(distance2D(x1, y1, x2, (double) y2));
    }

    /**
     * Returns the 2D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @return The distance between the two points.
     */
    public static double distance2D(double x1, double y1, double x2, double y2) {
        return Math.sqrt((Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }

    /**
     * Returns the 2D distance between two points based on their coordinates.
     *
     * @param a The first point.
     * @param b The second point.
     * @return The distance between the two points.
     */
    public static double distance2D(Location a, Location b) {
        return distance2D(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * Returns the 3D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param z1 The z coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @param z2 The z coordinate of the second point.
     * @return The distance between the two points.
     */
    public static int distance3D(int x1, int y1, int z1, int x2, int y2, int z2) {
        return Math.round(distance3D(x1, y1, z1, x2, y2, (float) z2));
    }

    /**
     * Returns the 3D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param z1 The z coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @param z2 The z coordinate of the second point.
     * @return The distance between the two points.
     */
    public static long distance3D(long x1, long y1, long z1, long x2, long y2, long z2) {
        return Math.round(distance3D(x1, y1, z1, x2, y2, (float) z2));
    }

    /**
     * Returns the 3D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param z1 The z coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @param z2 The z coordinate of the second point.
     * @return The distance between the two points.
     */
    public static float distance3D(float x1, float y1, float z1, float x2, float y2, float z2) {
        return (float) distance3D(x1, y1, z1, x2, y2, (double) z2);
    }

    /**
     * Returns the 3D distance between two points based on their coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param z1 The z coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @param z2 The z coordinate of the second point.
     * @return The distance between the two points.
     */
    public static double distance3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt((Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2)));
    }

    /**
     * Returns the 3D distance between two points based on their coordinates.
     *
     * @param a The first point.
     * @param b The second point.
     * @return The 3D distance between two points.
     */
    public static double distance3D(Location a, Location b) {
        return distance3D(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
    }
}
