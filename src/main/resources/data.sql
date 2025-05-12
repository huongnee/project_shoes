-- Dữ liệu mẫu cho payment_method
INSERT INTO payment_method (NAME, ISACTIVE, ISDELETE, CREATED_DATE, NOTES, UPDATED_DATE)
SELECT 'Thanh toán khi nhận hàng (COD)', true, false, NOW(), 'Thanh toán tiền mặt khi nhận hàng', NULL
WHERE NOT EXISTS (SELECT 1 FROM payment_method WHERE NAME = 'Thanh toán khi nhận hàng (COD)');

INSERT INTO payment_method (NAME, ISACTIVE, ISDELETE, CREATED_DATE, NOTES, UPDATED_DATE)
SELECT 'Chuyển khoản ngân hàng', true, false, NOW(), 'Thanh toán bằng chuyển khoản ngân hàng', NULL
WHERE NOT EXISTS (SELECT 1 FROM payment_method WHERE NAME = 'Chuyển khoản ngân hàng');

INSERT INTO payment_method (NAME, ISACTIVE, ISDELETE, CREATED_DATE, NOTES, UPDATED_DATE)
SELECT 'Ví điện tử (Momo, ZaloPay)', true, false, NOW(), 'Thanh toán qua ví điện tử', NULL
WHERE NOT EXISTS (SELECT 1 FROM payment_method WHERE NAME = 'Ví điện tử (Momo, ZaloPay)');

-- Dữ liệu mẫu cho transport_method
INSERT INTO transport_method (NAME, ISACTIVE, ISDELETE, CREATED_DATE, NOTES, UPDATED_DATE)
SELECT 'Giao hàng tiêu chuẩn', true, false, NOW(), 'Giao hàng trong 3-5 ngày', NULL
WHERE NOT EXISTS (SELECT 1 FROM transport_method WHERE NAME = 'Giao hàng tiêu chuẩn');

INSERT INTO transport_method (NAME, ISACTIVE, ISDELETE, CREATED_DATE, NOTES, UPDATED_DATE)
SELECT 'Giao hàng nhanh', true, false, NOW(), 'Giao hàng trong 1-2 ngày', NULL
WHERE NOT EXISTS (SELECT 1 FROM transport_method WHERE NAME = 'Giao hàng nhanh');

INSERT INTO transport_method (NAME, ISACTIVE, ISDELETE, CREATED_DATE, NOTES, UPDATED_DATE)
SELECT 'Nhận tại cửa hàng', true, false, NOW(), 'Khách hàng đến nhận tại cửa hàng', NULL
WHERE NOT EXISTS (SELECT 1 FROM transport_method WHERE NAME = 'Nhận tại cửa hàng');

-- Tạo bảng cart nếu chưa tồn tại
CREATE TABLE IF NOT EXISTS cart (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_ID BIGINT NOT NULL,
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
    UPDATED_DATE DATETIME ON UPDATE CURRENT_TIMESTAMP,
    ISDELETE BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (CUSTOMER_ID) REFERENCES customer(ID)
);

-- Tạo bảng cart_item nếu chưa tồn tại
CREATE TABLE IF NOT EXISTS cart_item (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    CART_ID BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL,
    QUANTITY INT NOT NULL DEFAULT 1,
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
    UPDATED_DATE DATETIME ON UPDATE CURRENT_TIMESTAMP,
    ISDELETE BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (CART_ID) REFERENCES cart(ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES product(ID)
);

-- Thêm cột STATUS vào bảng orders nếu chưa tồn tại
ALTER TABLE orders ADD COLUMN IF NOT EXISTS STATUS INT NOT NULL DEFAULT 0 AFTER PHONE;

-- Thêm tài khoản admin mặc định
INSERT INTO `admin_users` (`id`, `username`, `password`, `name`, `email`, `is_active`) 
VALUES (1, 'admin', '$2a$10$3YGP.cZ/TRmqwqVjxNUQXevYWZnuQEEUWIQs.T73RLWvJc/p/IjL6', 'Administrator', 'admin@example.com', 1);

-- Mật khẩu mặc định là: admin123 