package com.example.BarrierKU.utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class TestGeometryUtils {
    private static final GeometryFactory GF = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point point(double lon, double lat) {
        return GF.createPoint(new Coordinate(lon, lat));
    }
}
