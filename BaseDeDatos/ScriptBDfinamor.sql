-- creacion estructuras 
-- Conectarse a BD finamor  y ejecutar el siguiente script
--  
create database finamor;
use finamor;
-- --------------------------------------------------------------
-- --------------------------BORRADO INICIAL DE TABLAS----------------------
-- --------------------------------------------------------------

-- DROP TABLE IF EXISTS contacto;
-- DROP TABLE IF EXISTS cliente;
-- DROP TABLE IF EXISTS historico_pagos;
-- DROP TABLE IF EXISTS credito;


-- --------------------------------------------------------------
-- --------------------    cliente    ---------------------------
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS cliente(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(45) NOT NULL,
    numero_cliente VARCHAR(16) NOT NULL unique,
	paterno VARCHAR(45) NOT NULL,
    materno VARCHAR(45) NOT NULL,
    edad INT NOT NULL,
    ingreso_semanal INT NULL,
    telefono_1 VARCHAR(12) NOT NULL,
    telefono_2 VARCHAR(12) NULL,
	correo VARCHAR(80),
    domicilio VARCHAR(200) NOT NULL,
    domicilio_detalle VARCHAR(200) NULL
);

-- --------------------------------------------------------------
-- --------------------    contacto   ---------------------------
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS contacto(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(90) NOT NULL,    
    telefono_1 VARCHAR(12) NOT NULL,
    telefono_2 VARCHAR(12) NULL,
    parentezco VARCHAR(12) NULL,
    cliente INT NOT NULL,
    FOREIGN KEY (cliente) REFERENCES cliente(id)
);

-- --------------------------------------------------------------
-- --------------------    credito    ---------------------------
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS credito(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    contrato VARCHAR(14) NOT NULL unique,
	dia_pago VARCHAR(10) NOT NULL,
	folio VARCHAR(15) NOT NULL unique,
    monto INT NOT NULL,
    pago_inicial INT NOT NULL,
    pago_semanal INT NOT NULL,
    cliente INT NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY (cliente) REFERENCES cliente(id)
);

-- --------------------------------------------------------------
-- ---------------- historico_pagos   ---------------------------
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS historico_pagos(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	fecha DATE NOT NULL,
	monto INT NOT NULL,
	folio VARCHAR(250)NOT NULL,  
    credito INT NOT NULL,
    FOREIGN KEY (credito) REFERENCES credito(id)
);

CREATE TABLE IF NOT EXISTS cortepagos(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	idUltimo int NOT NULL,
	fecha DATE NOT NULL,
	monto DECIMAL(10,0)NOT NULL
);

CREATE TABLE IF NOT EXISTS cortepagos_detalles(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	idcortepago int NOT NULL,
	id_hisotrico int NOT NULL,
	idcredito int,
    pagado int,
    FOREIGN KEY (`idcortepago`)
    REFERENCES `finamor`.`cortepagos` (`id`),
    FOREIGN KEY (`id_hisotrico`)
    REFERENCES `finamor`.`historico_pagos` (`id`)   ,
    FOREIGN KEY (`idcredito`)
    REFERENCES `finamor`.`credito` (`id`)
);


CREATE TABLE IF NOT EXISTS person (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `edad` INT NOT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `financiera`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS rol (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `acronym` VARCHAR(20) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_s1os9yfo1bacbqovq9v3ha860` (`acronym` ASC) VISIBLE,
  UNIQUE INDEX `UK_dr7bbrycaed8pcag9i0h36ncl` (`description` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `financiera`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `status` INT NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `person_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username` ASC) VISIBLE,
  INDEX `FKir5g7yucydevmmc84i788jp79` (`person_id` ASC) VISIBLE,
  CONSTRAINT `FKir5g7yucydevmmc84i788jp79`
    FOREIGN KEY (`person_id`)
    REFERENCES `finamor`.`person` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;



-- -----------------------------------------------------
-- Table `financiera`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role(
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `FKp1cxcodysgjk7jkfwdc8hhor9` (`role_id` ASC) VISIBLE,
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o`
    FOREIGN KEY (`user_id`)
    REFERENCES `finamor`.`user` (`id`),
  CONSTRAINT `FKp1cxcodysgjk7jkfwdc8hhor9`
    FOREIGN KEY (`role_id`)
    REFERENCES `finamor`.`rol` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


 ALTER TABLE credito
 ADD semanas int(90);
 
 ALTER TABLE credito 
ADD COLUMN promotora BIGINT,
ADD CONSTRAINT fk_credito_promotora
    FOREIGN KEY (promotora)
    REFERENCES user(id);
    
ALTER TABLE cortepagos
ADD COLUMN promotora BIGINT,
ADD CONSTRAINT fk_cortepagos_promotora FOREIGN KEY (promotora) REFERENCES user(id);

ALTER TABLE credito
MODIFY COLUMN folio VARCHAR(100);