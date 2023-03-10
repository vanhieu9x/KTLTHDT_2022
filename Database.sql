USE [master]
GO
/****** Object:  Database [PerfumeShop]    Script Date: 12/11/2021 6:05:12 PM ******/
CREATE DATABASE [PerfumeShop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PerfumeShopv3', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\PerfumeShopv3.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'PerfumeShopv3_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\PerfumeShopv3_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [PerfumeShop] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PerfumeShop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [PerfumeShop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PerfumeShop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PerfumeShop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PerfumeShop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PerfumeShop] SET ARITHABORT OFF 
GO
ALTER DATABASE [PerfumeShop] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PerfumeShop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PerfumeShop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PerfumeShop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PerfumeShop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PerfumeShop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PerfumeShop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PerfumeShop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PerfumeShop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PerfumeShop] SET  DISABLE_BROKER 
GO
ALTER DATABASE [PerfumeShop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PerfumeShop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PerfumeShop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PerfumeShop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PerfumeShop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PerfumeShop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PerfumeShop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PerfumeShop] SET RECOVERY FULL 
GO
ALTER DATABASE [PerfumeShop] SET  MULTI_USER 
GO
ALTER DATABASE [PerfumeShop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PerfumeShop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [PerfumeShop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PerfumeShop] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [PerfumeShop] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'PerfumeShop', N'ON'
GO
USE [PerfumeShop]
GO
/****** Object:  Table [dbo].[Accounts]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Accounts](
	[email] [nvarchar](50) NOT NULL,
	[password] [varchar](150) NULL,
	[roleid] [int] NULL,
 CONSTRAINT [PK_Tai_khoan] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Cart]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cart](
	[customerid] [int] NOT NULL,
	[productid] [int] NOT NULL,
	[number] [int] NULL,
 CONSTRAINT [PK_Cart] PRIMARY KEY CLUSTERED 
(
	[customerid] ASC,
	[productid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Categories]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[description] [nvarchar](100) NULL,
 CONSTRAINT [PK_Loai_SP] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Customers]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customers](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[gender] [bit] NULL,
	[email] [nvarchar](50) NOT NULL,
	[address] [nvarchar](50) NULL,
	[phone] [nchar](10) NULL,
 CONSTRAINT [PK_Customers] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Employees]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employees](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[gender] [bit] NULL,
	[email] [nvarchar](50) NOT NULL,
	[address] [nvarchar](50) NULL,
	[phone] [nchar](10) NULL,
	[image] [nvarchar](20) NULL,
 CONSTRAINT [PK_Nhan_vien] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Fragrances]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Fragrances](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[description] [nvarchar](max) NULL,
 CONSTRAINT [PK_Huong] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Images]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Images](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[productid] [int] NULL,
	[path] [nvarchar](max) NULL,
 CONSTRAINT [PK_Images_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Import_Note]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Import_Note](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[createday] [date] NULL,
	[employeeid] [int] NULL,
	[totalprice] [float] NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_Phieu_Nhap] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Import_Note_Detail]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Import_Note_Detail](
	[importnoteid] [int] NOT NULL,
	[productid] [int] NOT NULL,
	[number] [int] NULL,
	[price] [float] NULL,
 CONSTRAINT [PK_CT_PhieuNhap] PRIMARY KEY CLUSTERED 
(
	[importnoteid] ASC,
	[productid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Manufactures]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Manufactures](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[email] [nvarchar](50) NULL,
	[phone] [nchar](10) NULL,
	[address] [nvarchar](50) NULL,
	[description] [nvarchar](100) NULL,
 CONSTRAINT [PK_Nha_CC] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Order_Detail]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order_Detail](
	[orderid] [int] NOT NULL,
	[productid] [int] NOT NULL,
	[number] [int] NULL,
	[price] [float] NULL,
 CONSTRAINT [PK_Order_Detail] PRIMARY KEY CLUSTERED 
(
	[orderid] ASC,
	[productid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Orders]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[totalprice] [float] NULL,
	[customerid] [int] NULL,
	[employeeid] [int] NULL,
	[createday] [date] NULL,
	[address] [nvarchar](100) NULL,
	[status] [int] NULL,
	[namecustomer] [nvarchar](100) NULL,
	[phonenumber] [nchar](10) NULL,
	[ispaid] [int] NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Product_Fragrance]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product_Fragrance](
	[productid] [int] NOT NULL,
	[fragranceid] [int] NOT NULL,
 CONSTRAINT [PK_FragrancesDetail] PRIMARY KEY CLUSTERED 
(
	[productid] ASC,
	[fragranceid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Products]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[categoryid] [int] NULL,
	[number] [int] NULL,
	[price] [float] NULL,
	[status] [int] NULL,
	[description] [nvarchar](max) NULL,
	[manufactureid] [int] NULL,
	[capacity] [nvarchar](10) NULL,
	[cost] [float] NULL,
 CONSTRAINT [PK_San_Pham] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Promotion]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotion](
	[id] [int] NOT NULL,
	[name] [nvarchar](50) NULL,
	[description] [nvarchar](100) NULL,
 CONSTRAINT [PK_Promotion] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Promotion_Detail]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotion_Detail](
	[promotionid] [int] NULL,
	[productid] [int] NULL,
	[code] [nchar](10) NULL,
	[percent] [int] NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Role]    Script Date: 12/11/2021 6:05:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[id] [int] NOT NULL,
	[name] [nvarchar](50) NULL,
 CONSTRAINT [PK_Quyen] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
INSERT [dbo].[Accounts] ([email], [password], [roleid]) VALUES (N'haobh123@gmail.com', N'$2a$10$XTOkjA2mtX04KhtXbnfSyuXyJkUr3cbo3VNW.ocVG42mKozVGih0G', 2)
INSERT [dbo].[Accounts] ([email], [password], [roleid]) VALUES (N'hoanghao@yopmail.com', N'$2a$10$8FMuIjV3sQqyBTn0Ws0Im.WInXqWNk5OJ5O7dQjxW1Vfhk2Fpq4rK', 1)
INSERT [dbo].[Accounts] ([email], [password], [roleid]) VALUES (N'nh@gmail.com', N'$2a$10$Daa4pj9cp/79cUZ7z6PCNu2M3LcR4D6/1DM/tpND9Y3jeMgwWcX0O', 2)
SET IDENTITY_INSERT [dbo].[Categories] ON 

INSERT [dbo].[Categories] ([id], [name], [description]) VALUES (1, N'Perfume Oil', N'lưu hương lâu ')
INSERT [dbo].[Categories] ([id], [name], [description]) VALUES (2, N'Body Mist', N'cho nữ')
INSERT [dbo].[Categories] ([id], [name], [description]) VALUES (3, N'Aromatherapy', N'desc')
INSERT [dbo].[Categories] ([id], [name], [description]) VALUES (4, N'Perfumes', N'kkj')
INSERT [dbo].[Categories] ([id], [name], [description]) VALUES (6, N'Oil Mist', NULL)
INSERT [dbo].[Categories] ([id], [name], [description]) VALUES (7, N'Body Spray', NULL)
SET IDENTITY_INSERT [dbo].[Categories] OFF
SET IDENTITY_INSERT [dbo].[Customers] ON 

INSERT [dbo].[Customers] ([id], [name], [gender], [email], [address], [phone]) VALUES (1009, N'Hao Customer', 1, N'hoanghao@yopmail.com', N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', N'0388315303')
SET IDENTITY_INSERT [dbo].[Customers] OFF
SET IDENTITY_INSERT [dbo].[Employees] ON 

INSERT [dbo].[Employees] ([id], [name], [gender], [email], [address], [phone], [image]) VALUES (2, N'Hảo', 0, N'haobh123@gmail.com', N'Q9', N'123456789 ', NULL)
INSERT [dbo].[Employees] ([id], [name], [gender], [email], [address], [phone], [image]) VALUES (2004, N'mnh', 0, N'nh@gmail.com', N'125, Phường Chính Gián, Quận Thanh Khê, Đà Nẵng', N'0234581235', NULL)
SET IDENTITY_INSERT [dbo].[Employees] OFF
SET IDENTITY_INSERT [dbo].[Fragrances] ON 

INSERT [dbo].[Fragrances] ([id], [name], [description]) VALUES (1, N'Aromatic', N'Nhóm hương thơm thường sử dụng tổ hợp của cây xô thơm, hương thảo, thì là, hoa oải hương và một số loài cây có sở hữu mùi cỏ thảo mộc nồng nàn. Chúng thường được kết hợp với nhóm hương cam chanh và nhóm hương cay nồng. Nhóm hương thơm này thường hay xuất hiện trong các loại nước hoa dành cho nam giới')
INSERT [dbo].[Fragrances] ([id], [name], [description]) VALUES (3, N'Chypre', N'Nhóm hương này được đặt theo tên của nước hoa Coty Chypre được sáng tạo ra năm 1917. Chypre cũng có nghĩa là Cyprus (đảo Síp) trong tiếng Pháp. Mùi hương sắc lạnh này dựa trên sự kết hợp hài hòa giữa rêu sồi (oak moss), labdanum (một loại gỗ hồng đặc biệt được tìm thấy ở đảo Síp và vùng Địa Trung Hải), hoắc hương (patchouli) và cam bergamot. Chypre Floral – các loại hoa như hoa hồng, linh lan (lily of the valley), hay hoa nhài chính là những hương liệu truyền thống của nhánh hương này.Chypre Fruity – sự kết hợp hoàn hảo này đến từ những mùi hương trong nhóm chypre cùng với các mùi trái cây của đào, mơ, dâu rừng hay những loại quả lạ, tạo nên mùi hương chung dày và ấm áp.')
INSERT [dbo].[Fragrances] ([id], [name], [description]) VALUES (4, N'Citrus', N'Nước hoa mang notes citrus đã xuất hiện từ rất lâu đời và phong phú. Nhóm hương này dựa trên các thành phần như cam, chanh, cam bergamot, bưởi hoặc quýt cùng với các mùi aromatic và vị chua (tart notes) cho nam và mùi hương hoa (flora notes) cho nữ. Citrus Aromatic – mùi hương cam chanh cộng với các loại thảo mộc như oải hương, hương thảo, thì là, bạc hà. Citrus Gourmand – nhánh này kết hợp các mùi citrus ngọt dịu hơn như cam ngọt với vani, caramel hay các mùi hoa thơm ngọt.')
INSERT [dbo].[Fragrances] ([id], [name], [description]) VALUES (5, N'Oriental', N'Nước hoa phương Đông với mùi hổ phách chủ đạo cũng được phân loại như một nhóm mùi riêng nhờ vào nét ấm nồng và xác thịt của nó. Nhóm nước hoa phương Đông mang đến các mùi hương mạnh mẽ, lấn át với notes xạ hương (musk), vanilla, gỗ, các loại nhựa (resins) đặc biệt kết hợp với gia vị và các mùi hoa lạ.')
INSERT [dbo].[Fragrances] ([id], [name], [description]) VALUES (6, N'Woody', N'Rất dễ để nhận ra nhóm hương này với mùi hương ấp áp, bí ẩn của sandalwood, mùi khô sắc lạnh của cedar hay vetiver, mùi nhựa gỗ dịu nhẹ kết hợp cùng notes aromatic hay citrus cam chanh.')
INSERT [dbo].[Fragrances] ([id], [name], [description]) VALUES (7, N'Floral', N'Đây chính là nhóm hương chiếm phần lớn nhất trong các loại nước hoa, được tạo nên dựa trên rất nhiều loại kết hợp với tâm điểm chính là hương hoa: hương hoa tươi mới hái, hương hoa với mùi tươi mát như biển cả, hương xanh hoặc phấn, hương hoa trái kết hợp, hay hương hoa ngọt gourmand.')
INSERT [dbo].[Fragrances] ([id], [name], [description]) VALUES (8, N'Aquatic', N'Mùi hương tạo cảm giác mát mẻ , trong lành cùng vị mặn của gió biển , thường phù hợp sử dụng trong tiết trời nóng bức')
SET IDENTITY_INSERT [dbo].[Fragrances] OFF
SET IDENTITY_INSERT [dbo].[Images] ON 

INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (3003, 2007, N'images\product\imagek6rg9ep4instagram7.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (3004, 2007, N'images\product\imagevzj2zq373215b77bc67e28ca87df303b1b8e516a.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5003, 3007, N'images\product\image3irwws3v19ae1bef5aaab74ba1a9bd241b1a2873.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5004, 3008, N'images\product\imagefg1kseusBody-mist-the-body-shop-e1605069474300.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5005, 1, N'images\product\image4m13bplwpinkerberryclode.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5006, 2, N'images\product\imageb35vmw29sunkissed.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5007, 3, N'images\product\imageld66bm7raquakiss.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5008, 1008, N'images\product\imagesq9ohilbbombshell.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5009, 1009, N'images\product\imagejahzr1nestressrelife.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5010, 1007, N'images\product\imageb8r25qvksteel.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5011, 7, N'images\product\imagenbmvt3u9lotusdesite.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5012, 6, N'images\product\imagedatim8r8catuswater.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5013, 5, N'images\product\imaged3ghpzslcleanslate.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5014, 4, N'images\product\imagekgbqk2qclovespell2.jpg')
INSERT [dbo].[Images] ([id], [productid], [path]) VALUES (5015, 3009, N'images\product\imageibrogfo7bombshell.jpg')
SET IDENTITY_INSERT [dbo].[Images] OFF
SET IDENTITY_INSERT [dbo].[Import_Note] ON 

INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (6005, CAST(N'2021-12-11' AS Date), 2, 1000000, 2)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (6006, CAST(N'2021-12-11' AS Date), 2, 1000000, 2)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (6007, CAST(N'2021-12-11' AS Date), 2, 2000000, 2)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (6008, CAST(N'2021-12-10' AS Date), 2, 5000000, 0)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (6009, CAST(N'2021-12-11' AS Date), 2, 2500000, 2)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (6010, CAST(N'2021-12-11' AS Date), 2, 6000000, 2)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (7005, CAST(N'2021-12-11' AS Date), 2, 6000000, 2)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (7006, CAST(N'2021-12-11' AS Date), 2, 15000000, 2)
INSERT [dbo].[Import_Note] ([id], [createday], [employeeid], [totalprice], [status]) VALUES (7007, CAST(N'2021-12-11' AS Date), 2, 2000000, 2)
SET IDENTITY_INSERT [dbo].[Import_Note] OFF
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (6005, 1007, 10, 100000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (6006, 1007, 5, 200000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (6007, 2, 10, 200000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (6008, 7, 20, 250000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (6009, 6, 10, 250000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (6010, 5, 20, 300000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (7005, 3008, 20, 300000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (7006, 1009, 50, 300000)
INSERT [dbo].[Import_Note_Detail] ([importnoteid], [productid], [number], [price]) VALUES (7007, 3007, 10, 200000)
SET IDENTITY_INSERT [dbo].[Manufactures] ON 

INSERT [dbo].[Manufactures] ([id], [name], [email], [phone], [address], [description]) VALUES (1, N'Channel', N'channel@gmail.com', N'0388315304', N'TP Hồ Chí Minh', NULL)
INSERT [dbo].[Manufactures] ([id], [name], [email], [phone], [address], [description]) VALUES (3, N'Bath&BodyWord', N'bathbodyword@gmail.com', N'0388315303', N'TP Hồ Chí Minh', NULL)
INSERT [dbo].[Manufactures] ([id], [name], [email], [phone], [address], [description]) VALUES (4, N'Victoria''S secret', N'test2@gmail.com', N'0396906925', N'TP Hồ Chí Minh', NULL)
INSERT [dbo].[Manufactures] ([id], [name], [email], [phone], [address], [description]) VALUES (6, N'T&T', N'tandt@gmail.com', N'0388315303', N'TP Hồ Chí Minh', N'desc')
SET IDENTITY_INSERT [dbo].[Manufactures] OFF
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (2005, 1007, 2, 250000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3005, 2, 1, 300000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3006, 2, 1, 300000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3006, 1007, 1, 250000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3007, 2, 1, 300000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3008, 1007, 2, 250000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3009, 2, 1, 300000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3010, 1007, 2, 250000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3011, 3008, 10, 300000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3012, 5, 5, 700000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3013, 3007, 5, 400000)
INSERT [dbo].[Order_Detail] ([orderid], [productid], [number], [price]) VALUES (3013, 3008, 5, 300000)
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (2005, 500000, 1009, 2, CAST(N'2021-12-10' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 4, N'Hao Customer', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3005, 300000, 1009, 2, CAST(N'2021-12-09' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh B', 4, N'Dee Dee', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3006, 550000, 1009, NULL, CAST(N'2021-12-09' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 1, N'Hao Customer', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3007, 300000, 1009, NULL, CAST(N'2021-12-10' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 1, N'Hao Customer', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3008, 500000, 1009, NULL, CAST(N'2021-12-08' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 1, N'Hao Customer', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3009, 300000, 1009, NULL, CAST(N'2021-12-07' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 1, N'Hao Customer', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3010, 500000, 1009, NULL, CAST(N'2021-12-06' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 1, N'Hao Customer', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3011, 3000000, 1009, 2, CAST(N'2021-12-10' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 3, N'Hao Customer', N'0388315303', 1)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3012, 3500000, 1009, 2, CAST(N'2021-12-10' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 2, N'Hao Customer', N'0388315303', 0)
INSERT [dbo].[Orders] ([id], [totalprice], [customerid], [employeeid], [createday], [address], [status], [namecustomer], [phonenumber], [ispaid]) VALUES (3013, 3500000, 1009, 2, CAST(N'2021-12-11' AS Date), N'145, Phường Bến Nghé, Quận 1, Hồ Chí Minh', 4, N'Hao Customer', N'0388315303', 1)
SET IDENTITY_INSERT [dbo].[Orders] OFF
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (1, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (2, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (3, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (4, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (4, 3)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (5, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (6, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (7, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (1007, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (1008, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (1009, 3)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (2007, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (2007, 3)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (3007, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (3008, 1)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (3009, 5)
INSERT [dbo].[Product_Fragrance] ([productid], [fragranceid]) VALUES (3009, 7)
SET IDENTITY_INSERT [dbo].[Products] ON 

INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (1, N'PINKBERRY CLOUDS ', 2, 0, 300000, 1, N'Chời ơi nó dễ huông gì đâuuuu, ngọt ngào, mềm mại mà cute phô mai que lúmm, hông bị ngọt quá mà chỉ lướt lướt ở trên thui, tông ngọt ngọt mỏng nhẹ như không á kiểu Sweet & Airy nè babe. Mùi thảnh thơi, nhẹ nhàng mà vui lắm nhenn, em là sự hoà quyện của kẹo bông dâu tây, marshmallo với fresh air? haha bảo sao Xóm thấy mùi ngọt cute mà kiểu uyển chuyển thông thoáng lắm luônn. gửi thoáng thoáng có nốt dứa, xong lát sau lại ẩn nhẹ mùi hoa nữa mà trong thành phần hỏng có ghi nhen, creamy lúm luônn, hỏng có bị ngọt lố nheeee. Cái sự "fresh air" hãng ghi trong thành phần làm Xóm yêu bạn này hơn nhiều, không quá trẻ con cho một mùi hương ngỡ là tuổi teen như này', 1, N'236', 0)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (2, N'SUNKISSED', 2, 9, 300000, 1, N'Một buổi dạo chơi trên biển với hoa cam trong nắng, muối biển lấp lánh dưới nắng vàng và làn gió biển như hương vanilla thơm mềm mại, ui thiệt sự kiểu một cô gái khoẻ khoắn, ngọt ngào mà phóng khoáng đó mọi ngườiiii. Mùi hoa cam quyện chút vị sữa thấy thơm kiểu chếch-chy ghê lun áaa, bạn này tuy mùi hơi ấm nhưng lại cực kỳ hợp mùa hè luôn á, mềm mại dịu dàng mà phê quá trờiiiiii, ngửi phần vòi xịt còn có nghe mùi rượu nữa á mọi người không biết lên da có nghe được mùi này không nữa, hy vọng là có.', 1, N'236', 200000)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (3, N'AQUA KISS', 2, 0, 800000, 1, N'Cho cái mùa nắng nóng nói chung và các bae hỏi "mùi nào mát thanh dễ dùng đi học đi làm hằng ngày" đây nhaaa. Bé này tông mát rười rượi nha bae, bán biết bao nhiêu lâu nay rùi mà nay có ảnh xinh mới đăng lại nè. Kiểu em vẫn là tông nước biển nhưng có mix thêm hoa cúc, hoa lan nam phi nữa nên hương có quyện một xíu hương hoa làm nữ tính nhẹ nhàng vừa fresh vừa cool. Bé này hương dễ chịu lại còn tươi mới nên dùng dịp nào cũng xịn luôn nhưng mà như Xóm nói đóo, xài trời nóng hay đi học đi làm là xịn nhấttt, kiểu thời tiết oi bức bực bội mà người mình vẫn mát mẻ thơm tho "tự nhiên" là thấy hơn người rồi.', 1, N'250', 0)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (4, N'LOVE SPELL', 3, 0, 200000, 1, N'hương hoa đào nhẹ nhẹ dịu dịu cùng với gỗ xạ hương sang ngọt êm ái, bạn này sexy lắmmm, quyến rũ ít ngọt thôi, kiểu hơi trầm nhưng mùi lôi cuốn say đắm lắmmmm. Mùi đẹp lắm, sang mà thu hút lắm mọi người ơii, vừa đủ êm ái để đi học đi làm không qua sexy kiểu trưởng thành nhưng vẫn mang nét lôi cuốn lắmmmm. Mùi trái cây thanh thanh phá một chuý ấm nhẹ dịu dàng, nữ tính mà mềm mại.', 1, N'250', 0)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (5, N'CLEAN SLATE', 7, 15, 700000, 1, N'Tui tâm đắc mùi này quá quý zị ơiii, bé này mới ngửi cảm giác kiểu như mùi thuộc dòng aroma á babe, thơm mát nhẹ dịu, cực kỳ dễ chịu mà kiểu thấy thư giãn nhẹ nhàng nữaaaa. Xong xem key notes thì đúng là như vậy luôn á babe haha Marine Citrus, Blue Sage (mùi cây xô thơm huyền thoại Xóm mê xĩu nèee, tui trồng cây này luôn á) và nốt cuối là gỗ đàn hương nghenn, em này làm bên line Aromatherapy cũng chuẩn luôn á mọi người bị zì mùi kiểu dễ chịu, gần gũi mà thư giãn lắm, muốn ôm hít miếtt. Nghe vậy thui là mọi người biết bạn này dễ dùng thư thái cỡ nào rùi nghenn, có gỗ đàn hương mang đến cảm giác vô cùng nhẹ nhàng lại rất gần với mùi da thịt, nên cũng khá sexy nhưng hông bị nồng á babe. Tuy nhiên bạn này vẫn thiên về tông mát hơnn, chùi uii mùi thảo mộc này kia là gu tui gu tui, unisex nam nữ xài được hết nhen babee. Mùi sạch sẽ thơm tho fresh lắm bae ơii, cái chai cũng đẹp nữa chồi ôiiiii, bae nào kiếm mùi xịt xong thấy thoải mái phấn chấn vui tươi thì ẻm đỉnh lắm óoo', 4, N'100', 300000)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (6, N'CACTUS WATER', 3, 10, 300000, 1, N'Và cho bae nào mê tông nước trong trẻo, mát lành mà bbw hết rùi thì hãy nghía bé Cactus Water nghen babe ơiiii, mùi hương nhẹ nhàng thoang thoảng của hoa xương rồng và nước mưa, vâng chính là nước mưa lạnh lạnh trong truyền thuyết mà mấy bà hay hỏi nè :)))
Mùi trầm nhẹ, trong vắt nhưng khá là sang nghen kiểu giống giống như Desert Wildflowers mà hơi đằm hơn chút xíu và ít hương hoa hơn nhen. Yêu kiều, thanh thoát, nhẹ nhàng, mong manh như một giấc mơ vậyyy, kiểu lúc thấy em on web là bà Xóm đã mê lắm ùi, mấy bé vỏ màu xanh xanh trong trong như này là lấy cảm tình Xóm liền luôn á. Mùi hương mọng nước, lạnh lạnh đã ơi là đã, màu xanh của cỏ cây và màu trắng trong của nước mát đã khiến tâm hồn này nhẹ nhõm rồi, thêm cả làn hương của lá cỏ dưới mưa nữa, mát lành và yên ả lắm. Mềm mại và êm ái, khi càng dành thời gian để tận hưởng mùi hương nhẹ dịu này, Xóm cành cảm thấy sự thanh tịnh và yên bình xung quanh. ', 4, N'250', 250000)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (7, N'LOTUS DISIRE', 3, 0, 300000, 1, N'Nốt chai cuối của collection Untamed Flora để mai up mùi giáng sinh cho hợp tình hợp lý hehe, collection này xóm còn sẵn Forbidden Rose, đợt sau về thêm Exotic Lily với Jasmine Allure nữa, hết trên web luôn rồi á, mấy chai này Xóm review đợt trước hết rùi nghen. uhu đây là cái chai tui trông ngóng nhất bộ sưu tập từ lúc đặt về mà lại về sau cùng tức ghê hôngggg, nhưng mà không sao mùi xịn thì tha thứ hếtt
Rồi okie zô, Trời ơi nó thơmmm, mùi hoa sen trắng và gỗ hoà quyện vào nhau kiểu vừa thuần khiết lại vừa quyến rũ, thu hút mà mê mẩn lắm quý zị ơii, đắm đuối luôn á. Thanh thoát, yêu kiều, quyến rũ ý nhị mà nhẹ nhàng dịu dàng lắm nghen, hông nồng nàn nhưng cái mùi này cuốn thì thôi áaa, xịt mấy ngày trời lạnh lạnh vầy muốn ôm xĩu luôn á, thấy iu thương liền', 3, N'250', 0)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (1007, N'STEEL', 7, 13, 250000, 1, N'Em bé cùng với Forest là mẫu mist nam mới nhất lúc về hàng Xóm có nói rùi á, nay chính thức review nhen babeee
Thôi rồi, bị dính mùi này rồi mấy bà ơiiii huhu em Steel mùi hơi đỉnh đó mấy bàaa thơm ngọt nhẹ, sang mà nó sexy má ơi luônn
Mùi của cam bergamot lạnh, gỗ nghiền và đậu Tonka nha babe, bé đậu Tonka thường hay có trong những mùi kiểu sang chảnh, ngọt ngào, quyến rũ và mạnh mẽ. Mùi hương thơm ngọt dịu, đơn giản của đậu tonka giúp cân bằng mùi hương tươi mát, mơn mởn cam bergamot giúp tổng thể hoà hợp ngọt ngào, thuần khiết, lôi cuốn, thu hút mà vẫn mạnh mẽ nam tính nhen bae.', 6, N'100', 133333.328125)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (1008, N'BOMBSHELL INTENSE', 4, 0, 500000, 1, N'Trời ơi mọi người ơiii, mùi này đỉnh lắm lắm đó mọi ngườiii, trầm mà sang dã man luôn đóoo. Em này có cherry tươi, mẫu đơn đỏ và vanilla đóo, trời quơii mùi đỉnh lắm quý zị ơiii. Xóm thề mùi này sang dã man luôn, không ngọt hẳn nhưng lại kiểu thu hút, quyến rũ, bí ẩn cực kỳ luônn
Mùi nóng bỏng, sexy, hư hỏng lắm á :))) Em này phê dã man luôn đóooo huhu không biết bà Xóm phải thốt lên bao nhiêu lần là em này xịn như thế nào luôn áaaa
Gu của bà Xóm đây nèeee, mùi này không nồng không đậm hay bị xộc mũi gì hết trơn, xịt lên da nó hoà cùng mùi cơ thể thật sự rất phê luôn đó mọi ngườii, cá nhân bà Xóm thấy đỉnh vãi luôn ròi đóoooo
Em này có vanilla nên sẽ có tông ngọt nè, nhưng không phải ngọt kiểu trẻ con hay ngọt đường mà là ngọt nhẹ dịu, trầm lắng và sang xịn như nước hoa lun áa.', 3, N'75', 0)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (1009, N'STRESS RELIEF', 3, 50, 400000, 1, N'Chắc từ lúc mình mới bán đến giờ mình cứ nói đi nói lại việc mình ghiền stress relief thế nào và có cả bộ này nhiều lần lắm rồi phải honggggg
Thiệt tình là mình là 1 người rất mau chán luôn, dùng cái gì cũng 1 thời gian là bỏ lửng liền mà riêng Stress Relief và Focus là không bao giờ mình chán luôn đó, mấy năm trời luôn rồi á. tress Relief có tông bạc hà lạnh cùng với Eucalyptus (khuynh diệp) sẽ luôn giúp mình cảm nhận sự thư thái, mát mẻ và xoa dịu tinh thần lại nữa . Em này mỗi lần mở chai để ngửi thôi cũng cảm thấy được xoa dịu nữa á, bà Xóm dùng cả bộ luôn nè, tắm, kem dưỡng, handgel, xịt gối và nến mùi này luôn đóooo. Mùi như được đi spa vậy, êm ái mà kiểu thoải mái cực luônnn, bạn nào hông tin cứ thử mua chai handgel để thử mùi nha huhu Em này làm nên tên tuổi của bath&body works luôn đóoo, kinh điển lắm nghennn.', 4, N'295', 300000)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (2007, N'RED RUBY', 2, 0, 400000, 1, N'desc', 3, N'50', 0)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (3007, N'Sun flower', 2, 5, 400000, 1, N'desc', 3, N'100', 200000)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (3008, N'Orange Flower', 2, 5, 300000, 1, N'ds', 4, N'250', 300000)
INSERT [dbo].[Products] ([id], [name], [categoryid], [number], [price], [status], [description], [manufactureid], [capacity], [cost]) VALUES (3009, N'New', 7, 0, 250000, 1, N'', 3, N'250', 0)
SET IDENTITY_INSERT [dbo].[Products] OFF
INSERT [dbo].[Role] ([id], [name]) VALUES (0, N'Out')
INSERT [dbo].[Role] ([id], [name]) VALUES (1, N'Customer')
INSERT [dbo].[Role] ([id], [name]) VALUES (2, N'Saler')
INSERT [dbo].[Role] ([id], [name]) VALUES (3, N'Admin')
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_Customers]    Script Date: 12/11/2021 6:05:12 PM ******/
ALTER TABLE [dbo].[Customers] ADD  CONSTRAINT [IX_Customers] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_employees]    Script Date: 12/11/2021 6:05:12 PM ******/
ALTER TABLE [dbo].[Employees] ADD  CONSTRAINT [IX_employees] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Accounts]  WITH NOCHECK ADD  CONSTRAINT [FK_Accounts_Customers] FOREIGN KEY([email])
REFERENCES [dbo].[Customers] ([email])
NOT FOR REPLICATION 
GO
ALTER TABLE [dbo].[Accounts] NOCHECK CONSTRAINT [FK_Accounts_Customers]
GO
ALTER TABLE [dbo].[Accounts]  WITH NOCHECK ADD  CONSTRAINT [FK_Accounts_Employees] FOREIGN KEY([email])
REFERENCES [dbo].[Employees] ([email])
NOT FOR REPLICATION 
GO
ALTER TABLE [dbo].[Accounts] NOCHECK CONSTRAINT [FK_Accounts_Employees]
GO
ALTER TABLE [dbo].[Accounts]  WITH CHECK ADD  CONSTRAINT [FK_Tai_khoan_Quyen] FOREIGN KEY([roleid])
REFERENCES [dbo].[Role] ([id])
GO
ALTER TABLE [dbo].[Accounts] CHECK CONSTRAINT [FK_Tai_khoan_Quyen]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK_Cart_Customers] FOREIGN KEY([customerid])
REFERENCES [dbo].[Customers] ([id])
GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK_Cart_Customers]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK_Cart_Products] FOREIGN KEY([productid])
REFERENCES [dbo].[Products] ([id])
GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK_Cart_Products]
GO
ALTER TABLE [dbo].[Images]  WITH CHECK ADD  CONSTRAINT [FK_Images_Products] FOREIGN KEY([productid])
REFERENCES [dbo].[Products] ([id])
GO
ALTER TABLE [dbo].[Images] CHECK CONSTRAINT [FK_Images_Products]
GO
ALTER TABLE [dbo].[Import_Note]  WITH CHECK ADD  CONSTRAINT [FK_Phieu_Nhap_Nhan_vien] FOREIGN KEY([employeeid])
REFERENCES [dbo].[Employees] ([id])
GO
ALTER TABLE [dbo].[Import_Note] CHECK CONSTRAINT [FK_Phieu_Nhap_Nhan_vien]
GO
ALTER TABLE [dbo].[Import_Note_Detail]  WITH CHECK ADD  CONSTRAINT [FK_CT_PhieuNhap_Phieu_Nhap] FOREIGN KEY([importnoteid])
REFERENCES [dbo].[Import_Note] ([id])
GO
ALTER TABLE [dbo].[Import_Note_Detail] CHECK CONSTRAINT [FK_CT_PhieuNhap_Phieu_Nhap]
GO
ALTER TABLE [dbo].[Import_Note_Detail]  WITH CHECK ADD  CONSTRAINT [FK_CT_PhieuNhap_San_Pham] FOREIGN KEY([productid])
REFERENCES [dbo].[Products] ([id])
GO
ALTER TABLE [dbo].[Import_Note_Detail] CHECK CONSTRAINT [FK_CT_PhieuNhap_San_Pham]
GO
ALTER TABLE [dbo].[Order_Detail]  WITH CHECK ADD  CONSTRAINT [FK_Order_Detail_Order] FOREIGN KEY([orderid])
REFERENCES [dbo].[Orders] ([id])
GO
ALTER TABLE [dbo].[Order_Detail] CHECK CONSTRAINT [FK_Order_Detail_Order]
GO
ALTER TABLE [dbo].[Order_Detail]  WITH CHECK ADD  CONSTRAINT [FK_Order_Detail_Products] FOREIGN KEY([productid])
REFERENCES [dbo].[Products] ([id])
GO
ALTER TABLE [dbo].[Order_Detail] CHECK CONSTRAINT [FK_Order_Detail_Products]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Order_Customers] FOREIGN KEY([customerid])
REFERENCES [dbo].[Customers] ([id])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Order_Customers]
GO
ALTER TABLE [dbo].[Product_Fragrance]  WITH CHECK ADD  CONSTRAINT [FK_FragrancesDetail_Fragrances] FOREIGN KEY([fragranceid])
REFERENCES [dbo].[Fragrances] ([id])
GO
ALTER TABLE [dbo].[Product_Fragrance] CHECK CONSTRAINT [FK_FragrancesDetail_Fragrances]
GO
ALTER TABLE [dbo].[Product_Fragrance]  WITH CHECK ADD  CONSTRAINT [FK_FragrancesDetail_Products] FOREIGN KEY([productid])
REFERENCES [dbo].[Products] ([id])
GO
ALTER TABLE [dbo].[Product_Fragrance] CHECK CONSTRAINT [FK_FragrancesDetail_Products]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_San_Pham_Loai_SP] FOREIGN KEY([categoryid])
REFERENCES [dbo].[Categories] ([id])
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_San_Pham_Loai_SP]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_San_Pham_Nha_CC] FOREIGN KEY([manufactureid])
REFERENCES [dbo].[Manufactures] ([id])
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_San_Pham_Nha_CC]
GO
ALTER TABLE [dbo].[Promotion_Detail]  WITH CHECK ADD  CONSTRAINT [FK_Promotion_Detail_Promotion] FOREIGN KEY([promotionid])
REFERENCES [dbo].[Promotion] ([id])
GO
ALTER TABLE [dbo].[Promotion_Detail] CHECK CONSTRAINT [FK_Promotion_Detail_Promotion]
GO
USE [master]
GO
ALTER DATABASE [PerfumeShop] SET  READ_WRITE 
GO
