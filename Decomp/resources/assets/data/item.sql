drop table item;

CREATE TABLE item
(
    id INTEGER PRIMARY KEY NOT NULL,
    domain TEXT,
    crystal INTEGER,
    jpy INTEGER,
    flag INTEGER DEFAULT 0 NOT NULL
);


INSERT INTO item (id, domain, crystal, jpy, flag) VALUES (1, 'jp.newgate.game.android.dw.crystal100', 1000, 120, 0);
INSERT INTO item (id, domain, crystal, jpy, flag) VALUES (2, 'jp.newgate.game.android.dw.crystal630', 6300, 600, 0);
INSERT INTO item (id, domain, crystal, jpy, flag) VALUES (3, 'jp.newgate.game.android.dw.crystal1300', 13000, 1200, 0);
INSERT INTO item (id, domain, crystal, jpy, flag) VALUES (4, 'jp.newgate.game.android.dw.crystal2700', 27000, 2400, 0);
INSERT INTO item (id, domain, crystal, jpy, flag) VALUES (5, 'jp.newgate.game.android.dw.crystal7000', 70000, 6000, 0);
INSERT INTO item (id, domain, crystal, jpy, flag) VALUES (6, 'jp.newgate.game.android.dw.crystal12000', 120000, 9800, 0);