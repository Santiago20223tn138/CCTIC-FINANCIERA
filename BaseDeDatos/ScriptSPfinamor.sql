drop procedure IF EXISTS consultaCliente; 
DELIMITER $$
CREATE PROCEDURE consultaCliente ()
       BEGIN
			SELECT * FROM cliente;
       END$$
DELIMITER ;


drop procedure IF EXISTS registraCliente; 
DELIMITER $$
CREATE PROCEDURE registraCliente (
IN	nombre VARCHAR(45) ,
IN   numero_cliente VARCHAR(16),
IN	paterno VARCHAR(45) ,
IN   materno VARCHAR(45) ,
IN   edad INT,
IN  ingreso_semanal INT,
IN telefono_1 VARCHAR(12) ,
IN telefono_2 VARCHAR(12) ,
IN	correo VARCHAR(80),
IN  domicilio VARCHAR(200) ,
IN domicilio_detalle VARCHAR(200) ,
IN	cn_nombre VARCHAR(90) ,    
IN    cn_telefono_1 VARCHAR(12) ,
 IN   cn_telefono_2 VARCHAR(12) ,
 IN   parentezco VARCHAR(12) 
)
       BEGIN
       
       DECLARE nuevo_cte INT;
       DECLARE count INT;
       
       SET nuevo_cte =  (SELECT (max(id) + 1) from cliente);
       
       insert into 
cliente(nombre,numero_cliente,paterno,materno,edad,ingreso_semanal,telefono_1,telefono_2,correo,domicilio,domicilio_detalle)
values(nombre,numero_cliente,paterno,materno,edad,ingreso_semanal,telefono_1,telefono_2,correo,domicilio,domicilio_detalle);
 SET count = (SELECT ROW_COUNT());

SELECT count;

 IF count > 0 THEN
 SET nuevo_cte =  (SELECT max(id) from cliente);

insert into 
contacto(nombre,telefono_1,telefono_2,parentezco,cliente)
values(cn_nombre,cn_telefono_1,cn_telefono_2,parentezco,nuevo_cte);  
     
SELECT nuevo_cte id;

END IF; 

       END$$
DELIMITER ;

-- CALL registraCliente('nombre','nuWWmewse','paterno', 'materno',0,'telefono_1','telefono_2','correo','domicilio','domicilio_detalle','cn_nombre','cn_telefon','cn_telefo','parentezco');
-- call registraCliente('nombre','nuWWmewse','paterno', 'materno',1,0,'telefono_1','telefono_2','correo','domicilio','domicilio_detalle','cn_nombre','cn_telefon','cn_telefo','parentezco');


--
-- registra credito
--
drop procedure IF EXISTS registraCredito; 
DELIMITER $$ 

CREATE PROCEDURE `registraCredito`(
IN	contrato VARCHAR(18) ,
IN   diaPago VARCHAR(10),
IN	folio VARCHAR(19) ,
IN   monto INT,
IN  inicial INT,
IN semanal INT,
IN cliente INT ,
IN	fecha date,
IN semanas INT,
IN promotora INT 
)
BEGIN
       
insert into 
credito(contrato,dia_pago,folio,monto,pago_inicial,pago_semanal,cliente,fecha,semanas, promotora)
values(contrato,diaPago,folio,monto,inicial,semanal,cliente,fecha,semanas, promotora);

SELECT  ROW_COUNT() registros;
END $$ 
DELIMITER ;

--
-- corte pagos
--



 drop procedure IF EXISTS CorteDePago;
-- Obtiene lista de pagos para la siguiente semana. 
DELIMITER $$
CREATE PROCEDURE CorteDePago(IN idPromotora INT)
BEGIN
    DECLARE montoTotal INT DEFAULT ( SELECT SUM(hp.monto) FROM credito c INNER JOIN historico_pagos hp ON c.id = hp.credito WHERE c.promotora = idPromotora AND hp.id > COALESCE((SELECT MAX(idUltimo) FROM cortepagos WHERE promotora = idPromotora), 0));
    DECLARE IDMaximo INT DEFAULT (SELECT max(hp.id) FROM credito c INNER JOIN historico_pagos hp ON c.id = hp.credito WHERE c.promotora = idPromotora);
    DECLARE IDMaximo2 INT DEFAULT (SELECT MAX(idUltimo) FROM cortepagos WHERE promotora = idPromotora);
    DECLARE nuevoID INT;
    DECLARE id INT;

CREATE TEMPORARY TABLE listaPagos 
   SELECT  c.id AS id, h.id as historicodi,
            SUM(h.monto) AS pagado,
            (SELECT monto FROM historico_pagos 
             WHERE fecha >= (SELECT MAX(fecha) FROM cortepagos) 
               AND fecha < NOW() AND credito = c.id) AS Monto,
            (c.monto * 2) AS total, 
            COUNT(h.id) AS pagos_realizados,
            MAX(h.fecha) AS fecha_maxima  
    FROM credito c 
    JOIN historico_pagos h ON c.id = h.credito
    GROUP BY c.contrato, c.id, h.id;

    SELECT c.id AS contrato_id, 
			lc.historicodi as historico,
           CONCAT(c.nombre,' ',c.paterno,' ',c.materno) AS Cliente,
           c.telefono_1, 
           cr.pago_semanal,
           cr.contrato, 
           cr.folio, 
           lc.fecha_maxima, 
           lc.Monto,
           CASE 
               WHEN cr.id < (SELECT MAX(idultimo) FROM cortepagos) THEN FALSE
               ELSE TRUE
           END AS es_fecha_valida
    FROM cliente c 
    JOIN credito cr ON c.id = cr.cliente 
    JOIN listaPagos lc ON lc.id = cr.id
    GROUP BY c.nombre, c.materno, c.paterno,
             c.telefono_1, c.domicilio,
             cr.id, cr.contrato, cr.folio, cr.dia_pago, cr.pago_semanal, lc.total, lc.pagado, lc.pagos_realizados, cr.fecha, lc.fecha_maxima, lc.Monto, lc.historicodi
    HAVING lc.total > lc.pagado;

  DROP TABLE listaPagos;


    IF IDMaximo <> IDMaximo2 OR IDMaximo2 IS NULL THEN
		INSERT INTO cortepagos (idultimo, fecha, monto, promotora) VALUES (IDMaximo, CURDATE(), montoTotal, idPromotora);
        SET nuevoID = (SELECT MAX(id) FROM cortepagos) ;
		CALL CortesDePagoDetalles(IDMaximo2, IDMaximo, idPromotora);
	END IF;
    
    
END$$
DELIMITER ;
--
-- cortepago detalles
--

drop procedure IF EXISTS CortesDePagoDetalles;

DELIMITER $$

CREATE PROCEDURE CortesDePagoDetalles(IN maximo1 INT,IN maximo2 INT, IN idPromotora INT)
BEGIN
		CREATE TEMPORARY TABLE listapagados 
			SELECT  c.id AS id,
					SUM(h.monto) AS pagado,
					(c.monto * 2) AS total, 
					COUNT(h.id) AS pagos_realizados,
					MAX(h.fecha) AS fecha_maxima  
			FROM credito c 
			JOIN historico_pagos h ON c.id = h.credito
            WHERE c.promotora = idPromotora
			GROUP BY c.contrato, c.id;
			
    INSERT INTO cortepagos_detalles (idcredito, idcortepago, pagado, id_hisotrico )
		SELECT
		lp.id AS idCredito,
		(SELECT MAX(id) FROM cortepagos) AS idCorte,
		CASE
			WHEN hp.id BETWEEN (COALESCE((SELECT idultimo FROM cortepagos WHERE promotora = idPromotora ORDER BY idultimo DESC LIMIT 1, 1) + 1 , 1))
							AND (SELECT idultimo FROM cortepagos where promotora = idPromotora ORDER BY idultimo DESC LIMIT 1)
			THEN 1
			ELSE 0
		END AS Pagado,
		CASE
			WHEN hp.id BETWEEN (COALESCE((SELECT idultimo FROM cortepagos WHERE promotora = idPromotora ORDER BY idultimo DESC LIMIT 1, 1) + 1 , 1))
							AND (SELECT idultimo FROM cortepagos where promotora = idPromotora ORDER BY idultimo DESC LIMIT 1)
			THEN hp.id
			ELSE NULL
		END AS idPago
	FROM
		listapagados lp
	LEFT JOIN (
		SELECT
			hp.credito,
			MAX(hp.id) AS ultimo_pago_id
		FROM
			historico_pagos hp
		GROUP BY
			hp.credito
	) AS max_hp ON lp.id = max_hp.credito
	LEFT JOIN cortepagos cp ON max_hp.ultimo_pago_id = cp.idultimo
	LEFT JOIN historico_pagos hp ON max_hp.ultimo_pago_id = hp.id;
        
		DROP TABLE listapagados;

END$$

DELIMITER ;

--
-- consultaListaPorCobrar
--
 drop procedure IF EXISTS consultaListaPorCobrar;
DELIMITER $$
CREATE PROCEDURE consultaListaPorCobrar (IN idPromotora INT)
       BEGIN
          
       
        CREATE temporary TABLE listaContratos SELECT  c.id id,SUM(h.monto) pagado, (c.monto * 2) total , count(h.id) pagos_realizados,MAX(h.id) AS fecha_maxima  from 
        credito c join  historico_pagos h on c.id = h.credito
        group by c.contrato;
              
       
SELECT cr.id contrato_id, concat(c.nombre,' ',c.paterno,' ',c.materno ) Cliente,(lc.total - lc.pagado) pendiente,
 c.telefono_1, cr.dia_pago,cr.pago_semanal,c.domicilio,
 cr.contrato, cr.folio, lc.total , lc.pagado, lc.pagos_realizados pagos_realizados ,lc.fecha_maxima,
        CASE 
            WHEN lc.fecha_maxima <= (SELECT MAX(idUltimo) FROM cortepagos where promotora = idPromotora) THEN false
            ELSE true
        END AS es_fecha_valida
FROM cliente c join credito cr on c.id = cr.cliente join listaContratos lc on lc.id = cr.id
WHERE cr.promotora = idPromotora
group by c.nombre,c.materno, c.paterno ,
 c.telefono_1, c.domicilio,
 cr.id,cr.contrato, cr.folio, cr.dia_pago, cr.pago_semanal,lc.total , lc.pagado,lc.pagos_realizados,cr.fecha, lc.fecha_maxima
 having lc.total > lc.pagado;       
       
       DROP TABLE listaContratos;
       
       END$$
DELIMITER ;