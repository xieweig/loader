INSERT INTO oauth_database.`jurisdiction` (`jurisdiction_id`, create_time, update_time, version, `depth`, `jurisdiction_code`, `jurisdiction_name`, `path`, `url_address`, `parent_id`, `need_token`)
   VALUES ( 4000, now(), now(), '0', '1', 'STOCK', '库存系统', 'STOCK', 'app.stock', null, '\0');

-- 二级菜单-stock 4000
-- STOCK 4000<库存查询-STOCK001 4001>
INSERT INTO oauth_database.`jurisdiction` (`jurisdiction_id`, create_time, update_time, version, `depth`, `jurisdiction_code`, `jurisdiction_name`, `path`, `url_address`, `parent_id`, `need_token`)
   VALUES (4001, now(), now(),  '0', '2', 'STOCK001', '库存查询', 'STOCK,STOCK001', 'app.stock.stock', 4000, '\0');03, '\0');

-- 三级菜单
INSERT INTO oauth_database.`jurisdiction` (`jurisdiction_id`, create_time, update_time, version, `depth`, `jurisdiction_code`, `jurisdiction_name`, `path`, `url_address`, `parent_id`, `need_token`)
   VALUES (4002, now(), now(), '0', '3', 'STOCK001001', '实时库存查询', 'STOCK,STOCK001,STOCK001001', 'app.stock.stock.list', 4001, '\0');

INSERT INTO oauth_database.`jurisdiction` (`jurisdiction_id`, create_time, update_time, version, `depth`, `jurisdiction_code`, `jurisdiction_name`, `path`, `url_address`, `parent_id`, `need_token`)
   VALUES (4003, now(), now(), '0', '3', 'STOCK001002', '历史库存查询', 'STOCK,STOCK001,STOCK001002', 'app.stock.stock.logList', 4001, '\0');

-- 四级（操作）
INSERT INTO oauth_database.`jurisdiction` (`jurisdiction_id`, create_time, update_time, version, `depth`, `jurisdiction_code`, `jurisdiction_name`, `path`, `url_address`, `parent_id`, `need_token`)
   VALUES (4004, now(), now(), '0', '4', 'STOCK001001001', '实时库存多条件查询', 'STOCK,STOCK001,STOCK001001,STOCK001001001', '/coffeeStock/stock/storage/findNowByCondition', 4002, '\0');

INSERT INTO oauth_database.`jurisdiction` (`jurisdiction_id`, create_time, update_time, version, `depth`, `jurisdiction_code`, `jurisdiction_name`, `path`, `url_address`, `parent_id`, `need_token`)
   VALUES (4005, now(), now(), '0', '4', 'STOCK001002001', '历史库存多条件查询', STOCK,STOCK001,STOCK001002,STOCK001002001', '/coffeeStock/stock/storage/findOldByCondition', 4003, \0');