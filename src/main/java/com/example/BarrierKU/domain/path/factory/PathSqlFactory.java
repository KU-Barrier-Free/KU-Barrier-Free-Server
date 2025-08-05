package com.example.BarrierKU.domain.path.factory;

import com.example.BarrierKU.domain.path.model.PathType;

public class PathSqlFactory {

    public static String getSql(PathType pathType) {
        return switch (pathType) {
            case SHORTEST -> getShortestPathSql();
            case BARRIER_FREE -> getBarrierFreePathSql();
        };
    }

    private static String getShortestPathSql() {
        return """
            SELECT r.seq, r.node, r.edge, r.cost, n.uid, ST_Y(n.location) AS lat, ST_X(n.location) AS lng,
                   e.length AS distance
            FROM (
                SELECT * FROM pgr_dijkstra(
                    $$
                    SELECT id,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER) AS source,
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER) AS target,
                           length AS cost,
                           length AS reverse_cost
                    FROM way
                    UNION ALL
                    SELECT id + 100000,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER),
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER),
                           length, length
                    FROM crossing
                    UNION ALL
                    SELECT id + 200000,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER),
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER),
                           length, length
                    FROM street
                    $$,
                    ?, ?, false
                )
            ) r
            JOIN node n ON CAST(SPLIT_PART(n.uid, '#', 2) AS INTEGER) = r.node
            LEFT JOIN (
                SELECT id, length FROM way
                UNION ALL
                SELECT id + 100000 AS id, length FROM crossing
                UNION ALL
                SELECT id + 200000 AS id, length FROM street
            ) e ON r.edge = e.id
            ORDER BY r.seq
        """;
    };

    private static String getBarrierFreePathSql() {
        return """
        SELECT r.seq, r.node, r.edge, r.cost, n.uid, ST_Y(n.location) AS lat, ST_X(n.location) AS lng,
               e.length AS distance
        FROM (
            SELECT * FROM pgr_dijkstra(
                $$
                SELECT * FROM (
                    SELECT id,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER) AS source,
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER) AS target,
                           CASE
                               WHEN type = 90 THEN 10000
                               ELSE (
                                   (CASE WHEN degree > 4.8 THEN 100 WHEN degree >= 2.4 THEN 50 ELSE 0 END) * 0.5 +
                                   (CASE WHEN width < 1 THEN 100 WHEN width < 2 THEN 50 ELSE 0 END) * 0.5
                               )
                           END AS cost,
                           CASE
                               WHEN type = 90 THEN 10000
                               ELSE (
                                   (CASE WHEN degree > 4.8 THEN 100 WHEN degree >= 2.4 THEN 50 ELSE 0 END) * 0.5 +
                                   (CASE WHEN width < 1 THEN 100 WHEN width < 2 THEN 50 ELSE 0 END) * 0.5
                               )
                           END AS reverse_cost
                    FROM way
                    UNION ALL
                    SELECT id + 100000,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER),
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER),
                           CASE
                               WHEN type = 90 THEN 10000
                               ELSE (
                                   (CASE WHEN degree > 4.8 THEN 100 WHEN degree >= 2.4 THEN 50 ELSE 0 END) * 0.5 +
                                   (CASE WHEN width < 1 THEN 100 WHEN width < 2 THEN 50 ELSE 0 END) * 0.5
                               )
                           END,
                           CASE
                               WHEN type = 90 THEN 10000
                               ELSE (
                                   (CASE WHEN degree > 4.8 THEN 100 WHEN degree >= 2.4 THEN 50 ELSE 0 END) * 0.5 +
                                   (CASE WHEN width < 1 THEN 100 WHEN width < 2 THEN 50 ELSE 0 END) * 0.5
                               )
                           END
                    FROM crossing
                    UNION ALL
                    SELECT id + 200000,
                           CAST(SPLIT_PART(f_node, '#', 2) AS INTEGER),
                           CAST(SPLIT_PART(t_node, '#', 2) AS INTEGER),
                           CASE
                               WHEN type = 90 THEN 10000
                               ELSE (
                                   (CASE WHEN degree > 4.8 THEN 100 WHEN degree >= 2.4 THEN 50 ELSE 0 END) * 0.5 +
                                   (CASE WHEN width < 1 THEN 100 WHEN width < 2 THEN 50 ELSE 0 END) * 0.5
                               )
                           END,
                           CASE
                               WHEN type = 90 THEN 10000
                               ELSE (
                                   (CASE WHEN degree > 4.8 THEN 100 WHEN degree >= 2.4 THEN 50 ELSE 0 END) * 0.5 +
                                   (CASE WHEN width < 1 THEN 100 WHEN width < 2 THEN 50 ELSE 0 END) * 0.5
                               )
                           END
                    FROM street
                ) AS sub
                $$,
                ?, ?, false
            )
        ) r
        JOIN node n ON CAST(SPLIT_PART(n.uid, '#', 2) AS INTEGER) = r.node
        LEFT JOIN (
            SELECT id, length FROM way
            UNION ALL
            SELECT id + 100000 AS id, length FROM crossing
            UNION ALL
            SELECT id + 200000 AS id, length FROM street
        ) e ON r.edge = e.id
        ORDER BY r.seq
    """;
    }

}