SET REFERENTIAL_INTEGRITY FALSE;

DELETE FROM "order";
DELETE FROM `order_item`;
DELETE FROM `product`;

SET REFERENTIAL_INTEGRITY TRUE;
