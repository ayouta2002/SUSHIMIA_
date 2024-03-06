-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 06, 2024 at 06:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `esprit`
--

-- --------------------------------------------------------

--
-- Table structure for table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `id` int(20) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `mot_de_passe` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utilisateurs`
--

INSERT INTO `utilisateurs` (`id`, `nom`, `prenom`, `mot_de_passe`, `email`, `role`, `image`) VALUES
(78, 'oumaima', 'ouma', 't', 't', 'Admin', ''),
(79, 'Saadi ', 'eyajnpi', 'lina', 'lalou@esprit.tn', 'Livreur', ''),
(80, 'amdouni', 'ouma', '158', 'aaa@esprit.tn', 'Client', ''),
(81, 'zameli', 'houssem', 'houssem', 'houssem@esprit.tn', 'Client', ''),
(83, 'bens', 'eya', 'eya', 'dsd', 'Admin', ''),
(84, 'amd', 'skander', 'skander', 'skander@esprit.tn', 'Client', ''),
(85, 'amdouni', 'hamza', 'hamza', 'hamza@esprit.tn', 'Client', ''),
(86, 'fersi', 'mouadh', 'mouadh', 'mouadh@esprit.tn', 'Client', ''),
(87, 'math', 'ahlem', 'ahlem125', 'ahlem@esprit.tn', 'Client', ''),
(88, 'Amdouni', 'hamza', 'hamza12', 'hamza@esprit.tn', 'Client', ''),
(89, 'yaakoubi', 'malek', 'malek', 'malek@esprit.tn', 'Client', ''),
(90, 'benrom', 'aziz', 'aziz', 'aziz@esprit.tn', 'Admin', ''),
(91, 'zaaa', 'nouunouu', 'nounou', 'nounou@esprit.tn', 'Admin', ''),
(92, 'irog', 'lakreg', 'the3', 'ieajf@esprit.tn', 'Admin', ''),
(93, 'firas', 'firzs', 'rze', 'firas@esprit.tn', 'Client', ''),
(94, 'mou', 'mou', 'mou', 'mou@esprit.tn', 'Admin', ''),
(95, 'eyaa', 'eyaa', 'eyaa', 'eyaa@esprit.tn', 'Admin', ''),
(96, 'lina', 'lina', 'lina', 'lina@esprit.tn', 'Client', ''),
(98, 'test', 'test', 'test', 'test@est.com', 'CLIENT', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
