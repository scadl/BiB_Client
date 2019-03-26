-- phpMyAdmin SQL Dump
-- version 4.2.3
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Jun 17, 2017 at 01:20 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `_personal`
--

-- --------------------------------------------------------

--
-- Table structure for table `xzclf_ads`
--

CREATE TABLE IF NOT EXISTS `xzclf_ads` (
`adid` int(10) unsigned NOT NULL,
  `adtitle` varchar(100) NOT NULL DEFAULT '',
  `addesc` longtext NOT NULL,
  `email` varchar(50) NOT NULL DEFAULT '',
  `subcatid` smallint(5) unsigned NOT NULL DEFAULT '0',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `enabled` enum('0','1') NOT NULL DEFAULT '0',
  `createdon` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `expireson` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `picfile` varchar(300) NOT NULL DEFAULT 'http://scadsdnd.ddns.net/bib/empty.png'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=32 ;

--
-- Dumping data for table `xzclf_ads`
--

INSERT INTO `xzclf_ads` (`adid`, `adtitle`, `addesc`, `email`, `subcatid`, `price`, `enabled`, `createdon`, `expireson`, `picfile`) VALUES
(1, 'прогулки на быстром катере Балаклава', '**прогулки на быстром катере Балаклава**\r\nт. 0663324995', 'ukrcommerce@gmail.com', 4513, '0.00', '1', '2014-07-15 14:19:47', '2017-06-17 01:41:03', 'http://scadsdnd.ddns.net/bib/adpics/53c51c633a9b5487cbe0bfd39.jpg'),
(2, 'кирпич отсев рукушняка 7 р. шт', 'кирпич отсев рукушняка 7 р. шт\r\nтел. 0958576301', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:23:24', '2017-06-17 01:41:11', 'http://scadsdnd.ddns.net/bib/adpics/53c51d3cc21ade268eebe96e0.jpg'),
(3, 'ракушняк кирпич (строительный материал) 7 р. шт, доставка по Севастополю', '**продажа ракушняк Севастополь кирпич** (строительный материал) 7 р. шт, доставка по Севастополю', 'ukrcommerce@gmail.com', 4509, '7.00', '1', '2014-07-15 14:31:08', '2017-06-17 01:41:16', 'http://scadsdnd.ddns.net/bib/adpics/53c51f0c35bd6e5c1392bf143.jpg'),
(4, 'камень песчаник продажа и Доставка по Севастополю', '**камень песчаник продажа и Доставка по Севастополю**\r\n\r\nцена песчаник Севастополь\r\nпесчаник единица - 15 р. \r\nпесчаник двойка - 180 руб.\r\nпесчаник желтый единица 1 - 200 р. \r\nпесчание желтый двойка 2 - 250 р.\r\n0958576301 Евгений', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:33:29', '2017-06-17 01:41:21', 'http://scadsdnd.ddns.net/bib/adpics/53c51f998fd141adfeab3c1cd.jpg'),
(5, 'дрова Севастополь доставка ', 'дрова Севастополь доставка дуб, ясень, бук, граб, сосна\r\n', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:35:41', '2017-06-17 01:41:35', 'http://scadsdnd.ddns.net/bib/adpics/53c5201dbef4ba17e08827ed2.jpg'),
(6, 'продажа солома в тюках Севастополь цена', '**продажа солома в тюках Севастополь цена**\r\n----------------------------------------\r\n\r\n**продажа соломы в тюках** по 50 р. размер 90см на 35см и 40см доставка  Севастополю тюкованой - доставка по городу \r\nцена 50 рублей за тюк', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:41:57', '2017-06-17 01:41:46', 'http://scadsdnd.ddns.net/bib/adpics/53c521968ca70ec8ec77d1016.jpg'),
(7, 'мука грубого помола кукурузная, пшеничная, гороховая, продажа доставка Севастополь цена', 'Продажа и доставка муки грубого помола по г. Севастополю - цена наилучшая\r\n----------------------------------------\r\n\r\n**мука кукурузная  крупного помола \r\nмука пшеничная грубого помола помола\r\nмука гороховая  крупного помола\r\nячневая  крупного помола\r\nовсяная крупного помола**\r\n\r\n0958576301', 'ukrcommerce@gmail.com', 4514, '0.00', '1', '2014-07-15 14:43:31', '2017-06-17 01:42:57', 'http://scadsdnd.ddns.net/bib/adpics/53c521f3e5445e5cc0b1bb5ab.jpg'),
(8, 'продажа пиломатериалов доски по Севастополю доставк цена', 'продажа пиломатериалов доски по Севастополю доставк цена\r\n----------------------------------------\r\nНаилучшие цена на лесо-пило-материалы по Севастополю доска, брус, доска для пола, доска отрезна, неотрезная доска - доставка по Севастополю\r\n\r\n\r\n', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:46:12', '2017-06-17 01:43:06', 'http://scadsdnd.ddns.net/bib/adpics/53c5229442a9ce3e5eeb81561.jpg'),
(9, 'металлопрокат продажа доставка по Севастополю - цена низкая', 'металлопрокат продажа доставка по Севастополю - цена низкая\r\n----------------------------------------\r\nПродажа металлопроката по Севастополю : квадрат, катанка, кругляк, уголок, поковки, труба - цены низкие - доставка по городу Севастополю\r\n0958576301', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:49:10', '2017-06-17 01:43:12', 'http://scadsdnd.ddns.net/bib/adpics/53c52346ec5978a77542779da.jpg'),
(10, 'продажа цемент ПЦ-500 по Севастополю Д0 шлаков нет - Турция 250 р. мешок', 'продажа цемент ПЦ-500 по Севастополю Д0 шлаков нет - Турция цена 250 р. мешок\r\n----------------------------------------\r\n0958576301', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:51:19', '2017-06-17 01:43:16', 'http://scadsdnd.ddns.net/bib/adpics/53c523c7c74266717790b5f4a.jpg'),
(11, 'продажа кирпич Симферополь 7 р. шт', 'продажа кирпич Симферополь 7 р. шт\r\n----------------------------------\r\n0958576301', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:53:34', '2017-06-17 01:43:21', 'http://scadsdnd.ddns.net/bib/adpics/53c5244ed910ac107819a9690.jpg'),
(12, 'продажа кирпича керченского 7,2 р./шт', 'продажа кирпича керченского 7,2 р./шт\r\n-------------------------------------\r\nпродажа кирпича керченского по Севастополю доставка\r\n0958576301', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:55:05', '2017-06-17 01:43:25', 'http://scadsdnd.ddns.net/bib/adpics/53c524a9eb60e2a2090759d84.jpg'),
(13, 'цемент ШПЦ -400 А III продажа цена Севастополь 105 р. Бахчисарай, 105 Криворожский', 'цемент ШПЦ -400 А III продажа цена Севастополь 105 р. Бахчисарай, 105 Криворожский\r\n----------------------------------------\r\nдоставка и продажа цемента в мешках ШПЦ III А 400 \r\n0958576301', 'ukrcommerce@gmail.com', 4509, '0.00', '1', '2014-07-15 14:57:14', '2017-06-17 01:43:28', 'http://scadsdnd.ddns.net/bib/adpics/3c5252a9a10d0f55c397723b.jpg'),
(18, 'продам ракетка для игры в большой теннис графитовая wilson stingv', 'продам ракетка для игры в большой теннис графитовая wilson sting покупалась за 180 USD в отличном состоянии\r\n4 1\\2 ручка, 319 грам, 31.5+-1 см баланс, размер головы 645 sq.cm \r\n\r\n(066) 3324995', 'gogison@gmail.com', 4515, '50.00', '1', '2014-07-19 13:44:41', '2017-06-17 01:44:21', 'http://scadsdnd.ddns.net/bib/adpics/53ca5a29cd97c11d855570a28.jpg'),
(19, 'Продам электрогитару Cort CR-200 Gold ', 'Продам электрогитару Cort CR-200 Gold в корпусе Gibson красное древо как новая Брал за 3200 грн! Отдаю за 2799\r\n\r\nОтдельно к гитаре продается комбоусилитель Cort 10 Ватт 500 грн, шнур 3 метра за 110 грн, ремень 160 грн зеленый - все новое в эксплуатации не был\r\n\r\nТип конструкции Вклеенный гриф\r\nКорпус Красное дерево\r\nМензура 24,75&quot;\r\nГриф Красное дерево, профиль &quot;Vintage C&quot;\r\nНакладка грифа Палисандр, радиус 305мм\r\nИнкрустация Перламутровые блоки - маркеры ладов\r\nОкантовка Белая по корпусу и грифу\r\nБридж Tune-o-Matic, струнодержатель Stop Bar\r\nЗвукосниматели ClassicRocker-II CR2NS-F &amp; CR2NS-R (H-H)\r\nЛады 22 / Large (2.7мм)\r\nМеханика Никелированная, колки типа Vintage\r\nУправление 2 громкости, 2 тембра, 3-позиционный переключатель\r\nЦвет Gold Top (GT)\r\nКомплектация Гитара, ключи\r\nПроизводство Индонезия\r\nДополнительно Струны D''addario \r\n\r\nтел. (066) 3324995', 'ukrcommerce@gmail.com', 4515, '0.00', '1', '2014-07-19 13:48:21', '2017-06-17 01:44:26', 'http://scadsdnd.ddns.net/bib/adpics/53ca5b057f387942dde4204e6.jpg'),
(20, 'обучение игре на гитаре в Севастополе', 'обучение игре на гитаре в Севастополе\r\nвсего за 1 неделю Вы научитесь играть на гитаре и петь песенки\r\n(066) 332-49-95', 'ukrcommerce@gmail.com', 4512, '20.00', '1', '2014-07-19 13:50:35', '2017-06-17 01:44:33', 'http://scadsdnd.ddns.net/bib/adpics/53ca5b8be25ac8c7208a61def.jpg'),
(21, 'такси Севастополь срочно, заказать быстро', 'такси Севастополь срочно, заказать быстро\r\n----------------------------------------\r\nуслуга трезвый водитель Севастополь - **такси вызвать быстро**\r\n\r\n(066) 332-49-95', 'ukrcommerce@gmail.com', 4513, '0.00', '1', '2014-07-19 13:54:31', '2017-06-17 01:44:42', 'http://scadsdnd.ddns.net/bib/adpics/53ca5c77b4b06ae1dcadaa073.jpg'),
(22, 'работа', 'Подработка со свободным графиком. Подойдет для всех, кто может без ошибок писать по-русски. Нужно писать тексты для сайтов разных тематик. Оплата сдельная, высокая! Деньги выплачиваются ежедневно. Заявки направляйте на email:  emailtextwrite@gmail.com', 'emailtextwrite@gmail.com', 4516, '0.00', '1', '2014-07-25 21:19:29', '2017-06-17 01:46:12', 'http://scadsdnd.ddns.net/bib/empty.png'),
(23, 'Работа модератором', 'Ольга Жукова\r\nНужен визуальный модератор юмористического контента. Задача будет состоять в просмотре юмористических картинок, публикуемых авторами (поток ~10 элементов в минуту), и оперативном отстреливании деструктивных элементов (обнаженка, спам и т.д.). Требования: чувство юмора. Пишите на email: servisrabota@gmail.com\r\n', 'servisrabota@gmail.com', 4516, '0.00', '1', '2014-07-26 17:16:16', '2017-06-17 01:46:17', 'http://scadsdnd.ddns.net/bib/empty.png'),
(24, 'САДОВНИК', 'ОКАЗЫВАЮ УСЛУГИ ПО ОЗЕЛЕНЕНИЮ И УХОДУ ЗА ЗЕЛЕНЫМИ НАСАЖДЕНИЯМИ:\r\n ДЕКОРАТИВНЫЕ И ПЛОДОВО-ЯГОДНЫЕ КУЛЬТУРЫ,-ПО ДОСТУПНОЙ ЦЕНЕ.\r\n\r\n\r\nТЛФ. (050) 86-52-175\r\n\r\nvk.com/id38337712', 'michexplorer@mail.ru', 4516, '0.00', '1', '2014-07-27 13:45:16', '2017-06-17 01:46:23', 'http://scadsdnd.ddns.net/bib/adpics/53d4e64c999c6ddbda713438a.jpg'),
(25, 'Купить сельхозтехнику в Воронеже', '&lt;a href=http://avist-vrn.ru/catalog/pochvoobrabatyvajushhaja-tekhnika/cultivators/bdm-agro/kultivator-sploshnojj-obrabotki-kso-9/&gt;культиватор кпк 4 02 цена&lt;/a&gt;\r\n \r\nВесна начинается с многочисленных работ на полях, осенью – горячая уборочная страда. Хорошо, что сегодня большим облегчением в сельхозработах является специальная сельхозтехника, позволяющая проводить посев и уборку различных сельскохозяйственных культур быстро и качественно. \r\n \r\n&lt;a href=http://avist-vrn.ru/catalog/pochvoobrabatyvajushhaja-tekhnika/borony/borony-hatzenbichler/tjazhelaja-borona-shtrigel/&gt;какую работу выполняет стригильная борона&lt;/a&gt;\r\n \r\nНаша компания дает возможность купить как отечественную, так и зарубежную технику, используемую для полевых работ. Постоянные поставки любой сельскохозяйственной техники по заказу покупателей по самым приемлемым ценам сделали отличную рекомендацию компании. Помимо реализации компания также осуществляют доставку всех необходимых запчастей к технике и ремонт любой сельхозтехники. По просьбе заказчика специалисты могут выехать на место и настроить агрегат. \r\n \r\n&lt;a href=http://avist-vrn.ru/catalog/pochvoobrabatyvajushhaja-tekhnika/borony/borony-hatzenbichler/setchataja-borona-shtrigel/&gt;штригельная борона это&lt;/a&gt;\r\n \r\nВ связи с растущим интересом к зарубежной технике «АгроЗапчасть» выступает посредником, тесно сотрудничая с мировыми производителями. Проводятся все пусконаладочные мероприятия, а также гарантийное обслуживание. \r\n \r\n&lt;a href=http://avist-vrn.ru/catalog/kormouborochnaja-tekhnika/rulonnye-press-podborshhiki/&gt;пресподборщик тюковый б у купить&lt;/a&gt;\r\n \r\nЕсли вы затрудняетесь с выбором, наши консультанты помогут разобраться с товарами и в технических характеристиках каждого наименования, предоставив вам варианты, которые соответствуют его требованиям.', 'avist@wegas.ru', 4510, '0.00', '0', '2014-07-30 01:37:07', '2017-06-17 01:46:37', 'http://scadsdnd.ddns.net/bib/adpics/53d83023e48ca1a010ab30f1b.gif'),
(29, 'Контент-менеджер (удалённо)', 'Найму контент-менеджера в интрнет-магазин', 'user@mail.cn', 4517, '478.00', '0', '2017-06-16 00:00:00', '2017-06-17 01:47:33', 'http://scadsdnd.ddns.net/bib/empty.png'),
(30, 'Нужен логотип', 'Нужно нарисовать логотип и дизайн хедера на сайт', 'user@mail.cn', 4517, '478.00', '0', '2017-06-16 00:00:00', '2017-06-17 01:49:44', 'http://scadsdnd.ddns.net/bib/empty.png');

-- --------------------------------------------------------

--
-- Table structure for table `xzclf_cats`
--

CREATE TABLE IF NOT EXISTS `xzclf_cats` (
`catid` smallint(5) unsigned NOT NULL,
  `catname` varchar(50) NOT NULL DEFAULT '',
  `enabled` enum('0','1') NOT NULL DEFAULT '0'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=132 ;

--
-- Dumping data for table `xzclf_cats`
--

INSERT INTO `xzclf_cats` (`catid`, `catname`, `enabled`) VALUES
(129, 'Работа', '0'),
(128, 'Услуги', '0'),
(127, 'Продажи', '0');

-- --------------------------------------------------------

--
-- Table structure for table `xzclf_subcats`
--

CREATE TABLE IF NOT EXISTS `xzclf_subcats` (
`subcatid` smallint(5) unsigned NOT NULL,
  `subcatname` varchar(50) NOT NULL DEFAULT '',
  `catid` smallint(5) unsigned NOT NULL DEFAULT '0',
  `enabled` enum('0','1') NOT NULL DEFAULT '0'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4520 ;

--
-- Dumping data for table `xzclf_subcats`
--

INSERT INTO `xzclf_subcats` (`subcatid`, `subcatname`, `catid`, `enabled`) VALUES
(4514, 'Фермерство', 127, '0'),
(4515, 'Инструменты', 127, '0'),
(4516, 'Найму', 129, '0'),
(4512, 'Обучение', 128, '0'),
(4513, 'Туризм', 128, '0'),
(4509, 'Строительство', 127, '0'),
(4510, 'Транспорт', 127, '0'),
(4517, 'Фриланс', 129, '0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `xzclf_ads`
--
ALTER TABLE `xzclf_ads`
 ADD PRIMARY KEY (`adid`), ADD KEY `subcatid` (`subcatid`), ADD KEY `enabled` (`enabled`);

--
-- Indexes for table `xzclf_cats`
--
ALTER TABLE `xzclf_cats`
 ADD PRIMARY KEY (`catid`), ADD KEY `enabled` (`enabled`);

--
-- Indexes for table `xzclf_subcats`
--
ALTER TABLE `xzclf_subcats`
 ADD PRIMARY KEY (`subcatid`), ADD KEY `catid` (`catid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `xzclf_ads`
--
ALTER TABLE `xzclf_ads`
MODIFY `adid` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `xzclf_cats`
--
ALTER TABLE `xzclf_cats`
MODIFY `catid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=132;
--
-- AUTO_INCREMENT for table `xzclf_subcats`
--
ALTER TABLE `xzclf_subcats`
MODIFY `subcatid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4520;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
