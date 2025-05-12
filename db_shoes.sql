-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:8888
-- Thời gian đã tạo: Th4 16, 2025 lúc 06:17 PM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `db_shoes`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `ID` bigint(20) NOT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL,
  `IDPARENT` bigint(20) DEFAULT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `META_DESCRIPTION` varchar(255) DEFAULT NULL,
  `META_KEYWORD` varchar(255) DEFAULT NULL,
  `META_TITLE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOTES` text DEFAULT NULL,
  `SLUG` varchar(255) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`ID`, `CREATED_BY`, `CREATED_DATE`, `ICON`, `IDPARENT`, `ISACTIVE`, `ISDELETE`, `META_DESCRIPTION`, `META_KEYWORD`, `META_TITLE`, `NAME`, `NOTES`, `SLUG`, `UPDATED_BY`, `UPDATED_DATE`) VALUES
(1, 1, '2025-04-16 15:52:41.000000', 'fa-running', NULL, b'1', b'0', 'Tất cả các loại giày thể thao chất lượng cao', 'giày thể thao, sneaker, giày chạy bộ', 'Giày thể thao', 'Giày thể thao', 'Các loại giày thể thao chất lượng cao', 'giay-the-thao', 1, '2025-04-16 15:52:41.000000'),
(2, 1, '2025-04-16 15:52:41.000000', 'fa-briefcase', NULL, b'1', b'0', 'Giày da sang trọng cho nam giới công sở', 'giày công sở, giày da, giày tây', 'Giày công sở', 'Giày công sở', 'Giày da sang trọng cho nam giới công sở', 'giay-cong-so', 1, '2025-04-16 15:52:41.000000'),
(3, 1, '2025-04-16 15:52:41.000000', 'fa-female', NULL, b'1', b'0', 'Giày cao gót thanh lịch cho phụ nữ', 'giày cao gót, giày nữ, giày công sở nữ', 'Giày cao gót', 'Giày cao gót', 'Giày cao gót thanh lịch cho phụ nữ', 'giay-cao-got', 1, '2025-04-16 15:52:41.000000'),
(4, 1, '2025-04-16 15:52:41.000000', 'fa-sun', NULL, b'1', b'0', 'Giày sandal thoáng mát cho mùa hè', 'giày sandal, dép, giày mùa hè', 'Giày sandal', 'Giày sandal', 'Giày sandal thoáng mát cho mùa hè', 'giay-sandal', 1, '2025-04-16 15:52:41.000000'),
(5, 1, '2025-04-16 15:52:41.000000', 'fa-check', 1, b'1', b'0', 'Các mẫu giày Nike chính hãng', 'Nike, giày Nike, giày thể thao Nike', 'Giày Nike', 'Giày Nike', 'Các mẫu giày Nike chính hãng', 'giay-nike', 1, '2025-04-16 15:52:41.000000'),
(6, 1, '2025-04-16 15:52:41.000000', 'fa-check', 1, b'1', b'0', 'Các mẫu giày Adidas chính hãng', 'Adidas, giày Adidas, giày thể thao Adidas', 'Giày Adidas', 'Giày Adidas', 'Các mẫu giày Adidas chính hãng', 'giay-adidas', 1, '2025-04-16 15:52:41.000000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `configurations`
--

CREATE TABLE `configurations` (
  `ID` bigint(20) NOT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOTES` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `configurations`
--

INSERT INTO `configurations` (`ID`, `ISACTIVE`, `ISDELETE`, `NAME`, `NOTES`) VALUES
(1, b'1', b'0', 'Size', 'Kích thước giày'),
(2, b'1', b'0', 'Màu sắc', 'Màu sắc giày'),
(3, b'1', b'0', 'Chất liệu', 'Chất liệu giày'),
(4, b'1', b'0', 'Xuất xứ', 'Xuất xứ giày');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer`
--

CREATE TABLE `customer` (
  `ID` bigint(20) NOT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `AVATAR` varchar(255) DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  `USERNAME` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customer`
--

INSERT INTO `customer` (`ID`, `ADDRESS`, `AVATAR`, `CREATED_BY`, `CREATED_DATE`, `EMAIL`, `ISACTIVE`, `ISDELETE`, `NAME`, `PASSWORD`, `PHONE`, `UPDATED_BY`, `UPDATED_DATE`, `USERNAME`) VALUES
(1, 'Số 123 Đường ABC, Quận 1, TP.HCM', 'avatar1.jpg', 1, '2025-04-16 15:52:41.000000', 'nguyenvana@example.com', b'1', b'0', 'Nguyễn Văn A', '482c811da5d5b4bc6d497ffa98491e38', '0901234567', 1, '2025-04-16 15:52:41.000000', 'nguyenvana'),
(2, 'Số 456 Đường XYZ, Quận 2, TP.HCM', 'avatar2.jpg', 1, '2025-04-16 15:52:41.000000', 'tranthib@example.com', b'1', b'0', 'Trần Thị B', '482c811da5d5b4bc6d497ffa98491e38', '0912345678', 1, '2025-04-16 15:52:41.000000', 'tranthib'),
(3, 'Số 789 Đường DEF, Quận 3, TP.HCM', 'avatar3.jpg', 1, '2025-04-16 15:52:41.000000', 'levanc@example.com', b'1', b'0', 'Lê Văn C', '482c811da5d5b4bc6d497ffa98491e38', '0923456789', 1, '2025-04-16 15:52:41.000000', 'levanc'),
(4, 'Số 101 Đường GHI, Quận 4, TP.HCM', 'avatar4.jpg', 1, '2025-04-16 15:52:41.000000', 'phamthid@example.com', b'1', b'0', 'Phạm Thị D', '482c811da5d5b4bc6d497ffa98491e38', '0934567890', 1, '2025-04-16 15:52:41.000000', 'phamthid'),
(5, 'Số 202 Đường JKL, Quận 5, TP.HCM', 'avatar5.jpg', 1, '2025-04-16 15:52:41.000000', 'hoangvane@example.com', b'1', b'0', 'Hoàng Văn E', '482c811da5d5b4bc6d497ffa98491e38', '0945678901', 1, '2025-04-16 15:52:41.000000', 'hoangvane');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `news`
--

CREATE TABLE `news` (
  `ID` bigint(20) NOT NULL,
  `CONTENTS` text DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `IDNEWSCATEGORY` bigint(20) NOT NULL,
  `IMAGE` varchar(255) DEFAULT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `META_DESCRIPTION` varchar(255) DEFAULT NULL,
  `META_KEYWORD` varchar(255) DEFAULT NULL,
  `META_TITLE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SLUG` varchar(255) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `news`
--

INSERT INTO `news` (`ID`, `CONTENTS`, `CREATED_BY`, `CREATED_DATE`, `DESCRIPTION`, `IDNEWSCATEGORY`, `IMAGE`, `ISACTIVE`, `ISDELETE`, `META_DESCRIPTION`, `META_KEYWORD`, `META_TITLE`, `NAME`, `SLUG`, `UPDATED_BY`, `UPDATED_DATE`) VALUES
(1, '<p>Nội dung chi tiết về top 10 mẫu giày thể thao hot nhất năm 2023...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Những mẫu giày thể thao được yêu thích nhất trong năm 2023', 1, 'news1.jpg', b'1', b'0', 'Những mẫu giày thể thao được yêu thích nhất trong năm 2023', 'giày thể thao, xu hướng giày 2023, giày hot', 'Top 10 mẫu giày thể thao hot 2023', 'Top 10 mẫu giày thể thao hot nhất năm 2023', 'top-10-mau-giay-the-thao-hot-nhat-nam-2023', 1, '2025-04-16 15:52:41.000000'),
(2, '<p>Nội dung chi tiết về cách chọn giày phù hợp với dáng chân...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Hướng dẫn chi tiết cách chọn giày phù hợp với từng dáng chân', 2, 'news2.jpg', b'1', b'0', 'Hướng dẫn chi tiết cách chọn giày phù hợp với từng dáng chân', 'chọn giày, dáng chân, tip chọn giày', 'Cách chọn giày phù hợp với dáng chân', 'Cách chọn giày phù hợp với dáng chân', 'cach-chon-giay-phu-hop-voi-dang-chan', 1, '2025-04-16 15:52:41.000000'),
(3, '<p>Nội dung chi tiết về 5 bước vệ sinh giày thể thao hiệu quả...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Hướng dẫn 5 bước đơn giản để vệ sinh giày thể thao hiệu quả', 3, 'news3.jpg', b'1', b'0', 'Hướng dẫn 5 bước đơn giản để vệ sinh giày thể thao hiệu quả', 'vệ sinh giày, làm sạch giày, bảo quản giày', '5 bước vệ sinh giày thể thao hiệu quả', '5 bước vệ sinh giày thể thao hiệu quả', '5-buoc-ve-sinh-giay-the-thao-hieu-qua', 1, '2025-04-16 15:52:41.000000'),
(4, '<p>Nội dung chi tiết về xu hướng giày công sở 2023...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Những mẫu giày công sở đang thịnh hành trong năm 2023', 1, 'news4.jpg', b'1', b'0', 'Những mẫu giày công sở đang thịnh hành trong năm 2023', 'giày công sở, xu hướng 2023, giày da', 'Xu hướng giày công sở 2023', 'Xu hướng giày công sở 2023', 'xu-huong-giay-cong-so-2023', 1, '2025-04-16 15:52:41.000000'),
(5, '<p>Nội dung chi tiết về bí quyết chọn giày cao gót thoải mái...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Những lưu ý khi chọn giày cao gót để đi thoải mái cả ngày', 2, 'news5.jpg', b'1', b'0', 'Những lưu ý khi chọn giày cao gót để đi thoải mái cả ngày', 'giày cao gót, thoải mái, chọn giày', 'Bí quyết chọn giày cao gót thoải mái', 'Bí quyết chọn giày cao gót thoải mái', 'bi-quyet-chon-giay-cao-got-thoai-mai', 1, '2025-04-16 15:52:41.000000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `news_category`
--

CREATE TABLE `news_category` (
  `ID` bigint(20) NOT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL,
  `IDPARENT` bigint(20) DEFAULT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `META_DESCRIPTION` varchar(255) DEFAULT NULL,
  `META_KEYWORD` varchar(255) DEFAULT NULL,
  `META_TITLE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOTES` text DEFAULT NULL,
  `SLUG` varchar(255) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `news_category`
--

INSERT INTO `news_category` (`ID`, `CREATED_BY`, `CREATED_DATE`, `ICON`, `IDPARENT`, `ISACTIVE`, `ISDELETE`, `META_DESCRIPTION`, `META_KEYWORD`, `META_TITLE`, `NAME`, `NOTES`, `SLUG`, `UPDATED_BY`, `UPDATED_DATE`) VALUES
(1, 1, '2025-04-16 15:52:41.000000', 'fa-newspaper', NULL, b'1', b'0', 'Các tin tức về giày mới', 'tin tức giày, giày mới, xu hướng giày', 'Tin tức giày', 'Tin tức giày', 'Các tin tức về giày mới', 'tin-tuc-giay', 1, '2025-04-16 15:52:41.000000'),
(2, 1, '2025-04-16 15:52:41.000000', 'fa-info-circle', NULL, b'1', b'0', 'Hướng dẫn chọn giày phù hợp', 'hướng dẫn, chọn giày, tips', 'Hướng dẫn chọn giày', 'Hướng dẫn chọn giày', 'Hướng dẫn chọn giày phù hợp', 'huong-dan-chon-giay', 1, '2025-04-16 15:52:41.000000'),
(3, 1, '2025-04-16 15:52:41.000000', 'fa-lightbulb', NULL, b'1', b'0', 'Các mẹo bảo quản giày hiệu quả', 'bảo quản giày, làm sạch giày, vệ sinh giày', 'Mẹo bảo quản giày', 'Mẹo bảo quản giày', 'Các mẹo bảo quản giày hiệu quả', 'meo-bao-quan-giay', 1, '2025-04-16 15:52:41.000000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `ID` bigint(20) NOT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `IORDERS` varchar(10) NOT NULL,
  `IDCUSTOMER` bigint(20) NOT NULL,
  `IDPAYMENT` bigint(20) NOT NULL,
  `IDTRANSPORT` bigint(20) NOT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `NAME_RECEIVER` varchar(255) DEFAULT NULL,
  `NOTES` text DEFAULT NULL,
  `ORDERS_DATE` datetime(6) NOT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `TOTAL_MONEY` decimal(38,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`ID`, `ADDRESS`, `EMAIL`, `IORDERS`, `IDCUSTOMER`, `IDPAYMENT`, `IDTRANSPORT`, `ISACTIVE`, `ISDELETE`, `NAME_RECEIVER`, `NOTES`, `ORDERS_DATE`, `PHONE`, `TOTAL_MONEY`) VALUES
(1, 'Số 123 Đường ABC, Quận 1, TP.HCM', 'nguyenvana@example.com', 'ORD001', 1, 1, 1, b'1', b'0', 'Nguyễn Văn A', 'Giao giờ hành chính', '2023-04-15 10:30:00.000000', '0901234567', '2800000.00'),
(2, 'Số 456 Đường XYZ, Quận 2, TP.HCM', 'tranthib@example.com', 'ORD002', 2, 2, 2, b'1', b'0', 'Trần Thị B', 'Giao buổi tối sau 18h', '2023-04-14 14:20:00.000000', '0912345678', '1800000.00'),
(3, 'Số 789 Đường DEF, Quận 3, TP.HCM', 'levanc@example.com', 'ORD003', 3, 1, 1, b'1', b'0', 'Lê Văn C', 'Không có ghi chú', '2023-04-13 09:15:00.000000', '0923456789', '4200000.00'),
(4, 'Số 101 Đường GHI, Quận 4, TP.HCM', 'phamthid@example.com', 'ORD004', 4, 3, 3, b'1', b'0', 'Phạm Thị D', 'Gọi trước khi giao', '2023-04-12 16:45:00.000000', '0934567890', '1200000.00'),
(5, 'Số 202 Đường JKL, Quận 5, TP.HCM', 'hoangvane@example.com', 'ORD005', 5, 2, 2, b'1', b'0', 'Hoàng Văn E', 'Không có ghi chú', '2023-04-11 11:30:00.000000', '0945678901', '3500000.00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders_details`
--

CREATE TABLE `orders_details` (
  `ID` bigint(20) NOT NULL,
  `IDORD` bigint(20) NOT NULL,
  `IDPRODUCT` bigint(20) NOT NULL,
  `PRICE` decimal(38,2) NOT NULL,
  `QTY` int(11) NOT NULL,
  `RETURN_QTY` int(11) DEFAULT NULL,
  `TOTAL` decimal(38,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orders_details`
--

INSERT INTO `orders_details` (`ID`, `IDORD`, `IDPRODUCT`, `PRICE`, `QTY`, `RETURN_QTY`, `TOTAL`) VALUES
(1, 1, 1, '2800000.00', 1, 0, '2800000.00'),
(2, 2, 3, '1500000.00', 1, 0, '1500000.00'),
(3, 2, 7, '300000.00', 1, 0, '300000.00'),
(4, 3, 4, '4200000.00', 1, 0, '4200000.00'),
(5, 4, 3, '1200000.00', 1, 0, '1200000.00'),
(6, 5, 6, '3500000.00', 1, 0, '3500000.00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payment_method`
--

CREATE TABLE `payment_method` (
  `ID` bigint(20) NOT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `NOTES` text DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `ID` bigint(20) NOT NULL,
  `CONTENTS` text DEFAULT NULL,
  `CREATED_BY` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DESCRIPTION` text DEFAULT NULL,
  `IDCATEGORY` bigint(20) NOT NULL,
  `IMAGE` varchar(255) DEFAULT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `META_DESCRIPTION` varchar(255) DEFAULT NULL,
  `META_KEYWORD` varchar(255) DEFAULT NULL,
  `META_TITLE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOTES` text DEFAULT NULL,
  `PRICE` decimal(38,2) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `SLUG` varchar(255) DEFAULT NULL,
  `UPDATED_BY` bigint(20) DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`ID`, `CONTENTS`, `CREATED_BY`, `CREATED_DATE`, `DESCRIPTION`, `IDCATEGORY`, `IMAGE`, `ISACTIVE`, `ISDELETE`, `META_DESCRIPTION`, `META_KEYWORD`, `META_TITLE`, `NAME`, `NOTES`, `PRICE`, `QUANTITY`, `SLUG`, `UPDATED_BY`, `UPDATED_DATE`) VALUES
(1, '<p>Nội dung chi tiết về giày Nike Air Max...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày thể thao Nike Air Max phiên bản mới nhất, thoáng khí và êm ái', 5, 'nike-air-max.jpg', b'1', b'0', 'Giày thể thao Nike Air Max phiên bản mới nhất, thoáng khí và êm ái', 'Nike Air Max, giày thể thao, giày Nike', 'Giày Nike Air Max', 'Giày Nike Air Max', 'Nhập khẩu chính hãng', '2800000.00', 50, 'giay-nike-air-max', 1, '2025-04-16 15:52:41.000000'),
(2, '<p>Nội dung chi tiết về giày Adidas Ultra Boost...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày thể thao Adidas Ultra Boost với công nghệ đệm Boost', 6, 'adidas-ultra-boost.jpg', b'1', b'0', 'Giày thể thao Adidas Ultra Boost với công nghệ đệm Boost', 'Adidas Ultra Boost, giày thể thao, giày Adidas', 'Giày Adidas Ultra Boost', 'Giày Adidas Ultra Boost', 'Nhập khẩu chính hãng', '3200000.00', 45, 'giay-adidas-ultra-boost', 1, '2025-04-16 15:52:41.000000'),
(3, '<p>Nội dung chi tiết về giày Converse Classic...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày Converse Classic phong cách cổ điển', 1, 'converse-classic.jpg', b'1', b'0', 'Giày Converse Classic phong cách cổ điển', 'Converse, giày classic, giày thể thao', 'Giày Converse Classic', 'Giày Converse Classic', 'Nhập khẩu chính hãng', '1500000.00', 65, 'giay-converse-classic', 1, '2025-04-16 15:52:41.000000'),
(4, '<p>Nội dung chi tiết về giày cao gót Gucci...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày cao gót Gucci phong cách sang trọng', 3, 'gucci-heels.jpg', b'1', b'0', 'Giày cao gót Gucci phong cách sang trọng', 'Gucci, giày cao gót, giày nữ', 'Giày cao gót Gucci', 'Giày cao gót Gucci', 'Nhập khẩu chính hãng', '4500000.00', 30, 'giay-cao-got-gucci', 1, '2025-04-16 15:52:41.000000'),
(5, '<p>Nội dung chi tiết về giày tây nam Clarks...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày tây nam Clarks sang trọng cho doanh nhân', 2, 'clarks-oxford.jpg', b'1', b'0', 'Giày tây nam Clarks sang trọng cho doanh nhân', 'Clarks, giày tây, giày công sở', 'Giày tây nam Clarks', 'Giày tây nam Clarks', 'Nhập khẩu chính hãng', '2300000.00', 40, 'giay-tay-nam-clarks', 1, '2025-04-16 15:52:41.000000'),
(6, '<p>Nội dung chi tiết về giày Nike Air Jordan...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày thể thao Nike Air Jordan phong cách bóng rổ', 5, 'nike-air-jordan.jpg', b'1', b'0', 'Giày thể thao Nike Air Jordan phong cách bóng rổ', 'Nike Air Jordan, giày bóng rổ, giày thể thao', 'Giày Nike Air Jordan', 'Giày Nike Air Jordan', 'Nhập khẩu chính hãng', '3500000.00', 35, 'giay-nike-air-jordan', 1, '2025-04-16 15:52:41.000000'),
(7, '<p>Nội dung chi tiết về giày sandal nữ Birkenstock...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày sandal nữ Birkenstock thoáng mát và êm ái', 4, 'birkenstock-sandal.jpg', b'1', b'0', 'Giày sandal nữ Birkenstock thoáng mát và êm ái', 'Birkenstock, giày sandal, giày nữ', 'Giày sandal nữ Birkenstock', 'Giày sandal nữ Birkenstock', 'Nhập khẩu chính hãng', '1800000.00', 55, 'giay-sandal-nu-birkenstock', 1, '2025-04-16 15:52:41.000000'),
(8, '<p>Nội dung chi tiết về giày Adidas Yeezy...</p><p>Nội dung chi tiết tiếp theo...</p>', 1, '2025-04-16 15:52:41.000000', 'Giày thể thao Adidas Yeezy thiết kế độc đáo', 6, 'adidas-yeezy.jpg', b'1', b'0', 'Giày thể thao Adidas Yeezy thiết kế độc đáo', 'Adidas Yeezy, giày thể thao, giày Adidas', 'Giày Adidas Yeezy', 'Giày Adidas Yeezy', 'Nhập khẩu chính hãng', '4200000.00', 25, 'giay-adidas-yeezy', 1, '2025-04-16 15:52:41.000000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_config`
--

CREATE TABLE `product_config` (
  `ID` bigint(20) NOT NULL,
  `IDCONFIG` bigint(20) NOT NULL,
  `IDPRODUCT` bigint(20) NOT NULL,
  `VALUE` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_config`
--

INSERT INTO `product_config` (`ID`, `IDCONFIG`, `IDPRODUCT`, `VALUE`) VALUES
(1, 1, 1, '39, 40, 41, 42, 43'),
(2, 2, 1, 'Đen, Trắng, Xám'),
(3, 3, 1, 'Vải mesh, cao su'),
(4, 4, 1, 'Mỹ'),
(5, 1, 2, '39, 40, 41, 42, 43, 44'),
(6, 2, 2, 'Đen, Trắng, Xanh navy'),
(7, 3, 2, 'Vải Primeknit, cao su'),
(8, 4, 2, 'Đức'),
(9, 1, 3, '36, 37, 38, 39, 40, 41, 42, 43'),
(10, 2, 3, 'Đen, Trắng, Đỏ'),
(11, 3, 3, 'Vải canvas, cao su'),
(12, 4, 3, 'Mỹ'),
(13, 1, 4, '35, 36, 37, 38, 39'),
(14, 2, 4, 'Đen, Đỏ, Be'),
(15, 3, 4, 'Da thật'),
(16, 4, 4, 'Ý'),
(17, 1, 5, '39, 40, 41, 42, 43, 44'),
(18, 2, 5, 'Đen, Nâu'),
(19, 3, 5, 'Da thật'),
(20, 4, 5, 'Anh');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_images`
--

CREATE TABLE `product_images` (
  `ID` bigint(20) NOT NULL,
  `IDPRODUCT` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `URLIMG` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_images`
--

INSERT INTO `product_images` (`ID`, `IDPRODUCT`, `NAME`, `URLIMG`) VALUES
(1, 1, 'Nike Air Max - Hình 1', 'nike-air-max-1.jpg'),
(2, 1, 'Nike Air Max - Hình 2', 'nike-air-max-2.jpg'),
(3, 1, 'Nike Air Max - Hình 3', 'nike-air-max-3.jpg'),
(4, 2, 'Adidas Ultra Boost - Hình 1', 'adidas-ultra-boost-1.jpg'),
(5, 2, 'Adidas Ultra Boost - Hình 2', 'adidas-ultra-boost-2.jpg'),
(6, 3, 'Converse Classic - Hình 1', 'converse-classic-1.jpg'),
(7, 3, 'Converse Classic - Hình 2', 'converse-classic-2.jpg'),
(8, 4, 'Gucci cao gót - Hình 1', 'gucci-heels-1.jpg'),
(9, 4, 'Gucci cao gót - Hình 2', 'gucci-heels-2.jpg'),
(10, 5, 'Clarks tây nam - Hình 1', 'clarks-oxford-1.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `transport_method`
--

CREATE TABLE `transport_method` (
  `ID` bigint(20) NOT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `ISACTIVE` bit(1) DEFAULT NULL,
  `ISDELETE` bit(1) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `NOTES` text DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `transport_method`
--

INSERT INTO `transport_method` (`ID`, `CREATED_DATE`, `ISACTIVE`, `ISDELETE`, `NAME`, `NOTES`, `UPDATED_DATE`) VALUES
(1, '2025-04-16 15:52:41.000000', b'1', b'0', 'Giao hàng tiêu chuẩn', 'Giao hàng trong 3-5 ngày làm việc', '2025-04-16 15:52:41.000000'),
(2, '2025-04-16 15:52:41.000000', b'1', b'0', 'Giao hàng nhanh', 'Giao hàng trong 1-2 ngày làm việc', '2025-04-16 15:52:41.000000'),
(3, '2025-04-16 15:52:41.000000', b'1', b'0', 'Giao hàng hỏa tốc', 'Giao hàng trong ngày (áp dụng nội thành)', '2025-04-16 15:52:41.000000');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `configurations`
--
ALTER TABLE `configurations`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FKicnl5jwgo9c2vf9uufatockxe` (`IDNEWSCATEGORY`);

--
-- Chỉ mục cho bảng `news_category`
--
ALTER TABLE `news_category`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FKag8c1ckqlopmv58ojaa4bbu7t` (`IDCUSTOMER`),
  ADD KEY `FKj4c2ir09mcugg272a4uk1nyyv` (`IDTRANSPORT`);

--
-- Chỉ mục cho bảng `orders_details`
--
ALTER TABLE `orders_details`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FKrfhjtljevdacbe4e0tp8fllyl` (`IDORD`),
  ADD KEY `FKlkxl1b9afvqyf3vnl3fq8991l` (`IDPRODUCT`);

--
-- Chỉ mục cho bảng `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK21nhy3a2rc58r7sqymfg0clx9` (`IDCATEGORY`);

--
-- Chỉ mục cho bảng `product_config`
--
ALTER TABLE `product_config`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK87t3chkuwv7eq8i1t0vpaqgbj` (`IDCONFIG`),
  ADD KEY `FKhehpyi8fgav5lv8l4qw8uewsh` (`IDPRODUCT`);

--
-- Chỉ mục cho bảng `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FKoqlw773n6228v26174aqwbwkj` (`IDPRODUCT`);

--
-- Chỉ mục cho bảng `transport_method`
--
ALTER TABLE `transport_method`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `configurations`
--
ALTER TABLE `configurations`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `customer`
--
ALTER TABLE `customer`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `news`
--
ALTER TABLE `news`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `news_category`
--
ALTER TABLE `news_category`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `orders_details`
--
ALTER TABLE `orders_details`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `payment_method`
--
ALTER TABLE `payment_method`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `product_config`
--
ALTER TABLE `product_config`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `product_images`
--
ALTER TABLE `product_images`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `transport_method`
--
ALTER TABLE `transport_method`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `FKicnl5jwgo9c2vf9uufatockxe` FOREIGN KEY (`IDNEWSCATEGORY`) REFERENCES `news_category` (`ID`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FKag8c1ckqlopmv58ojaa4bbu7t` FOREIGN KEY (`IDCUSTOMER`) REFERENCES `customer` (`ID`),
  ADD CONSTRAINT `FKj4c2ir09mcugg272a4uk1nyyv` FOREIGN KEY (`IDTRANSPORT`) REFERENCES `transport_method` (`ID`);

--
-- Các ràng buộc cho bảng `orders_details`
--
ALTER TABLE `orders_details`
  ADD CONSTRAINT `FKlkxl1b9afvqyf3vnl3fq8991l` FOREIGN KEY (`IDPRODUCT`) REFERENCES `product` (`ID`),
  ADD CONSTRAINT `FKrfhjtljevdacbe4e0tp8fllyl` FOREIGN KEY (`IDORD`) REFERENCES `orders` (`ID`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK21nhy3a2rc58r7sqymfg0clx9` FOREIGN KEY (`IDCATEGORY`) REFERENCES `category` (`ID`);

--
-- Các ràng buộc cho bảng `product_config`
--
ALTER TABLE `product_config`
  ADD CONSTRAINT `FK87t3chkuwv7eq8i1t0vpaqgbj` FOREIGN KEY (`IDCONFIG`) REFERENCES `configurations` (`ID`),
  ADD CONSTRAINT `FKhehpyi8fgav5lv8l4qw8uewsh` FOREIGN KEY (`IDPRODUCT`) REFERENCES `product` (`ID`);

--
-- Các ràng buộc cho bảng `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `FKoqlw773n6228v26174aqwbwkj` FOREIGN KEY (`IDPRODUCT`) REFERENCES `product` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- Tạo bảng admin_users
CREATE TABLE IF NOT EXISTS `admin_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm tài khoản admin mặc định
INSERT INTO `admin_users` (`username`, `password`, `name`, `email`, `is_active`) 
VALUES ('admin', '$2a$10$3YGP.cZ/TRmqwqVjxNUQXevYWZnuQEEUWIQs.T73RLWvJc/p/IjL6', 'Administrator', 'admin@example.com', b'1');

-- Mật khẩu mặc định là: admin123
