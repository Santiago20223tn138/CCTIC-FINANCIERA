-- Person
-- INSERT INTO `person` (`address`, `edad`, `email`, `last_name`, `name`, `phone`) VALUES ('dirrección', 'edad', 'correo', 'apellidos', 'nombre', 'teléfono');
INSERT INTO `person` VALUES (1,'JOSE MARIA MORELOS Y PAVON N° 6, PUEBLO VIEJO, TEMIXCO, MORELOS, MÉXICO, C.P. 62588',20,'admin@gmail.com','Valladares Borjes','Alan Yahir','7778456952'),(2,'JOSE MARIA MORELOS Y PAVON N° 6, PUEBLO VIEJO, TEMIXCO, MORELOS, MÉXICO, C.P. 62588',20,'JoseRicardo@gmail.com','Flores Martinez','Kevin','778521023');

-- Rol
-- INSERT INTO `rol` (`acronym`, `description`) VALUES ('acronym', 'description');
INSERT INTO `rol` VALUES (1,'Admin','Administrador'),(2,'Promotora','Promotora');

-- User
-- INSERT INTO `user` (`password`, `status`, `username`, `person_id`) VALUES ('contraseña', 'status', 'username', 'person');
INSERT INTO `user` VALUES (1,'$2a$10$RDpqBVCuEKw7sljTFjAtWOkZSBdUPfifLtymp/CwDrWRqjQdAqXeW',1,'admin@gmail.com',1),(2,'$2a$10$RDpqBVCuEKw7sljTFjAtWOkZSBdUPfifLtymp/CwDrWRqjQdAqXeW',1,'JoseRicardo@gmail.com',2);

-- User_role
-- INSERT INTO `user_role` (`user_id`, `role_id`) VALUES ('user_id', 'role_id');
INSERT INTO `user_role` VALUES (1,1),(2,2);

-- Cliente
-- INSERT INTO `cliente` (`nombre`, `numero_cliente`, `paterno`, `materno`, `edad`, `ingreso_semanal`, `telefono_1`, `telefono_2`, `correo`, `domicilio`, `domicilio_detalle`) VALUES ('nombre', 'numero de cliente', 'apellido paterno', 'apellido materno', 'edad', 'ingreso semanal', 'telefono 1', 'telefono 2', 'correo', 'domicilio', 'detalle del domicilio');
INSERT INTO `cliente` VALUES (1,'Ricardo','5321','Hernandez','Flores',35,8000,'7776548165','7776548165','ricardo32@gmail.com','Col. amp.San juanes de la lomas','Se encuentra enfrente de la tienda azul');

-- Contacto
-- INSERT INTO `contacto` (`nombre`, `telefono_1`, `telefono_2`, `parentezco`, `cliente`) VALUES ('nombre', 'telefono 1', 'telefono 2', 'parentezco', 'cliente_id');
INSERT INTO `contacto` VALUES (1,'Jessica Fernandez','7779652132','777542301','Hermana',1);

-- Crédito
-- INSERT INTO `credito` (`contrato`, `dia_pago`, `folio`, `monto`, `pago_inicial`, `pago_semanal`, `cliente`, `fecha`, `semanas`, `promotora`) VALUES ('numero contrato', 'dia de pago', 'folio contrato', 'monto', 'pago inicial', 'pago semanal', 'id cliente', 'fecha', 'semanas', 'id promotora');
INSERT INTO `credito` VALUES (1,'NA2317326540','DOMINGO','MOR2317326540',500,15,15,1,'2024-02-07',33,2);

-- Historico_pagos
-- INSERT INTO `historico_pagos` (`fecha`, `monto`, `folio`, `credito`) VALUES ('fecha', 'monto pago', 'folio pago', 'credito id');
INSERT INTO `historico_pagos` VALUES (1,'2024-02-19',168,'NA20240221054733622',1);

-- Cortepagos
-- INSERT INTO `cortepagos` (`idUltimo`, `fecha`, `monto`, `promotora`) VALUES ('id ultimo pago', 'fecha corte', 'monto total corte', 'id promotora');
INSERT INTO `cortepagos` VALUES (1,1,'2024-02-10',168,1);

-- Cortepagos_detalles
-- INSERT INTO `cortepagos_detalles` (`idcortepago`, `id_hisotrico`, `idcredito`, `pagado`) VALUES ('id cortepagos', 'id pago', 'id credito', 'pagado o no');
INSERT INTO `cortepagos_detalles` VALUES (1,1,1,1,1)