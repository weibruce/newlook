
ALTER TABLE t_biz_accessory ADD event_oid BIGINT AFTER `uid`;

UPDATE t_biz_accessory SET event_oid = 1 WHERE oid <= 21

