-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 28-Mar-2025 às 20:25
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `hoteldb`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `NIF` int(11) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `Ativo` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`NIF`, `nome`, `email`, `telefone`, `Ativo`) VALUES
(2, 'Joana Cigana', 'a.aurdaneta25472@aeestarreja.pt', '910736711', 'Sim'),
(100707155, 'Jacinto Pinto', 'jacintoainpinto@hotmail.com', '913696969', 'Não'),
(256937841, 'Diogo Oliveira Cabilhas', 'diogocabilhas1234@gmail.com', '963258741', 'Sim'),
(273051571, 'André Antão da Rocha', 'freddy33344@gmail.com', '965234701', 'Sim'),
(273691458, 'Gabriel Silva', 'gabrielsilva@gmail.com', '985263571', 'Sim'),
(274589635, 'José Carvalho', 'antoniodepinho0611@gmail.com', '910736741', 'Sim');

-- --------------------------------------------------------

--
-- Estrutura da tabela `quartos`
--

CREATE TABLE `quartos` (
  `numero` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  `telefone` varchar(9) NOT NULL,
  `reservado` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `quartos`
--

INSERT INTO `quartos` (`numero`, `tipo`, `telefone`, `reservado`) VALUES
(1, 1, '963258741', 'Sim'),
(2, 1, '936825714', 'Sim'),
(3, 1, '956238741', 'Sim'),
(4, 1, '926385147', 'Sim'),
(5, 1, '910785236', 'Não'),
(6, 1, '925863174', 'Sim'),
(7, 5, '987654321', 'Sim'),
(8, 5, '945632187', 'Não');

-- --------------------------------------------------------

--
-- Estrutura da tabela `reservas`
--

CREATE TABLE `reservas` (
  `ID` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `nr_quarto` int(11) NOT NULL,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipos_quartos`
--

CREATE TABLE `tipos_quartos` (
  `ID` int(11) NOT NULL,
  `tipo_quarto` varchar(50) NOT NULL,
  `preco` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tipos_quartos`
--

INSERT INTO `tipos_quartos` (`ID`, `tipo_quarto`, `preco`) VALUES
(1, 'Individual', 50.99),
(2, 'Duplo', 60.99),
(3, 'Triplo', 70.99),
(4, 'Quádruplo', 80.99),
(5, 'Deluxe', 130),
(6, 'Suíte', 150);

-- --------------------------------------------------------

--
-- Estrutura da tabela `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `users`
--

INSERT INTO `users` (`ID`, `Username`, `Password`) VALUES
(1, 'Antonio Urdaneta', '12345');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`NIF`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Índices para tabela `quartos`
--
ALTER TABLE `quartos`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `fk_quartoTipo` (`tipo`);

--
-- Índices para tabela `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_idcliente` (`id_cliente`),
  ADD KEY `fk_nrquarto` (`nr_quarto`);

--
-- Índices para tabela `tipos_quartos`
--
ALTER TABLE `tipos_quartos`
  ADD PRIMARY KEY (`ID`);

--
-- Índices para tabela `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `reservas`
--
ALTER TABLE `reservas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de tabela `tipos_quartos`
--
ALTER TABLE `tipos_quartos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `quartos`
--
ALTER TABLE `quartos`
  ADD CONSTRAINT `fk_quartoTipo` FOREIGN KEY (`tipo`) REFERENCES `tipos_quartos` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `fk_idcliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`NIF`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_nrquarto` FOREIGN KEY (`nr_quarto`) REFERENCES `quartos` (`numero`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
