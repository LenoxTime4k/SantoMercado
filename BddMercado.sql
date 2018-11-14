-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         5.7.23 - MySQL Community Server (GPL)
-- SO del servidor:              Win32
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para mercadosanto
CREATE DATABASE IF NOT EXISTS `mercadosanto` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mercadosanto`;

-- Volcando estructura para tabla mercadosanto.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_categoria` varchar(45) NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.categoria: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.compra
CREATE TABLE IF NOT EXISTS `compra` (
  `id_compra` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `Usuario_rut_usuario` int(11) NOT NULL,
  `Producto_id_producto` int(11) NOT NULL,
  PRIMARY KEY (`id_compra`),
  KEY `fk_Compra_Usuario1` (`Usuario_rut_usuario`),
  KEY `fk_Compra_Producto1` (`Producto_id_producto`),
  CONSTRAINT `fk_Compra_Producto1` FOREIGN KEY (`Producto_id_producto`) REFERENCES `producto` (`id_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Compra_Usuario1` FOREIGN KEY (`Usuario_rut_usuario`) REFERENCES `usuario` (`rut_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.compra: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.comuna
CREATE TABLE IF NOT EXISTS `comuna` (
  `idComuna` int(11) NOT NULL AUTO_INCREMENT,
  `comu_nombre` varchar(45) NOT NULL,
  `comu_region` varchar(45) NOT NULL,
  PRIMARY KEY (`idComuna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.comuna: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `comuna` DISABLE KEYS */;
/*!40000 ALTER TABLE `comuna` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.estadointercambio
CREATE TABLE IF NOT EXISTS `estadointercambio` (
  `id_Estado` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_estado` varchar(45) NOT NULL,
  PRIMARY KEY (`id_Estado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.estadointercambio: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `estadointercambio` DISABLE KEYS */;
/*!40000 ALTER TABLE `estadointercambio` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.intercambio
CREATE TABLE IF NOT EXISTS `intercambio` (
  `id_intercambio` int(11) NOT NULL AUTO_INCREMENT,
  `producto1_id` varchar(45) NOT NULL,
  `producto2_id` varchar(45) NOT NULL,
  `EstadoIntercambio_id_Estado` int(11) NOT NULL,
  PRIMARY KEY (`id_intercambio`),
  KEY `fk_Intercambio_EstadoIntercambio1` (`EstadoIntercambio_id_Estado`),
  CONSTRAINT `fk_Intercambio_EstadoIntercambio1` FOREIGN KEY (`EstadoIntercambio_id_Estado`) REFERENCES `estadointercambio` (`id_Estado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.intercambio: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `intercambio` DISABLE KEYS */;
/*!40000 ALTER TABLE `intercambio` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `precio` int(5) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `Categoria_id_categoria` int(11) NOT NULL,
  `Intercambio_id_intercambio1` int(11) NOT NULL,
  `Intercambio_id_intercambio` int(11) NOT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `fk_Producto_Categoria1` (`Categoria_id_categoria`),
  KEY `fk_Producto_Intercambio2` (`Intercambio_id_intercambio1`),
  KEY `fk_Producto_Intercambio1` (`Intercambio_id_intercambio`),
  CONSTRAINT `fk_Producto_Categoria1` FOREIGN KEY (`Categoria_id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Producto_Intercambio1` FOREIGN KEY (`Intercambio_id_intercambio`) REFERENCES `intercambio` (`id_intercambio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Producto_Intercambio2` FOREIGN KEY (`Intercambio_id_intercambio1`) REFERENCES `intercambio` (`id_intercambio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.producto: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.reporte
CREATE TABLE IF NOT EXISTS `reporte` (
  `id_reporte` int(11) NOT NULL AUTO_INCREMENT,
  `puntuacion_numerica` int(11) NOT NULL,
  `Usuario_rut_usuario` int(11) NOT NULL,
  `Compra_id_compra` int(11) NOT NULL,
  PRIMARY KEY (`id_reporte`),
  KEY `fk_Reporte_Usuario1` (`Usuario_rut_usuario`),
  KEY `fk_Reporte_Compra1` (`Compra_id_compra`),
  CONSTRAINT `fk_Reporte_Compra1` FOREIGN KEY (`Compra_id_compra`) REFERENCES `compra` (`id_compra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reporte_Usuario1` FOREIGN KEY (`Usuario_rut_usuario`) REFERENCES `usuario` (`rut_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.reporte: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `reporte` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.resgistrousuario
CREATE TABLE IF NOT EXISTS `resgistrousuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(50) NOT NULL,
  `contraseña` varchar(50) NOT NULL,
  `nombre_completo` varchar(50) NOT NULL,
  `rut_reg` varchar(12) NOT NULL,
  `Comuna_residencia` varchar(50) NOT NULL,
  `sede` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.resgistrousuario: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `resgistrousuario` DISABLE KEYS */;
INSERT INTO `resgistrousuario` (`id`, `nombre_usuario`, `contraseña`, `nombre_completo`, `rut_reg`, `Comuna_residencia`, `sede`) VALUES
	(1, '', '', 'Kevin A soto Z', '19266481-0', 'puerto montt', 'Puerto montt'),
	(2, '', '', 'Don francisco', '19266485-1', 'osorno', 'puerto montt');
/*!40000 ALTER TABLE `resgistrousuario` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.tareas
CREATE TABLE IF NOT EXISTS `tareas` (
  `id_tarea` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `nivel_de_prioridad` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_tarea`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.tareas: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `tareas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tareas` ENABLE KEYS */;

-- Volcando estructura para tabla mercadosanto.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `rut_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usu` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `fecha_nac` datetime NOT NULL,
  `correo_usu` varchar(45) NOT NULL,
  `Comuna_idComuna` int(11) NOT NULL,
  `Producto_id_producto` int(11) NOT NULL,
  PRIMARY KEY (`rut_usuario`),
  KEY `fk_Usuario_Comuna` (`Comuna_idComuna`),
  KEY `fk_Usuario_Producto1` (`Producto_id_producto`),
  CONSTRAINT `fk_Usuario_Comuna` FOREIGN KEY (`Comuna_idComuna`) REFERENCES `comuna` (`idComuna`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Producto1` FOREIGN KEY (`Producto_id_producto`) REFERENCES `producto` (`id_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla mercadosanto.usuario: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
