--
-- product

INSERT INTO `product` (`id`, `created_at`, `updated_at`, `deleted`, `name`, `price`) VALUES
(1, now(), now(), false, 'name1', 1.00),
(2, now(), now(), false, 'name2', 2.00),
(3, now(), now(), false, 'name3', 3.00);

--
-- order

INSERT INTO "order" (`id`, `created_at`, `updated_at`, `buyer_email`, `price`) VALUES
(1, '2019-12-01', now(), 'buyer1@test.com', 1.00),
(2, '2019-12-02', now(), 'buyer2@test.com', 2.00),
(3, '2019-12-03', now(), 'buyer3@test.com', 3.00);

--
-- order_item

INSERT INTO `order_item` (`id`, `created_at`, `updated_at`, `order_id`, `price`, `product_id`, `quantity`) VALUES
(1, '2019-12-01', now(), 1, 10, 1, 1),
(2, '2019-12-01', now(), 2, 20, 2, 2),
(3, '2019-12-01', now(), 3, 30, 2, 3);
