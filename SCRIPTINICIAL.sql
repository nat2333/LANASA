use lanasa;

CREATE TABLE Tipo_Usuario (
  id_tipo_usuario TINYINT AUTO_INCREMENT PRIMARY KEY,
  tipo    VARCHAR(50) UNIQUE NOT NULL
) ENGINE=InnoDB;


CREATE TABLE Usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  login     VARCHAR(100) UNIQUE NOT NULL,      
  clave      VARCHAR(255) NOT NULL,     
  id_tipo_usuario     TINYINT NOT NULL,
  estado TINYINT(1) NOT NULL,
  
  FOREIGN KEY (id_tipo_usuario) REFERENCES Tipo_Usuario(id_tipo_usuario)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;


CREATE TABLE Departamento (
  id_departamento   INT AUTO_INCREMENT PRIMARY KEY,
  nombre            VARCHAR(100) NOT NULL,
  codigo            VARCHAR(20)  UNIQUE NOT NULL,      
  fecha_creacion      DATETIME    NOT NULL, 
  presupuesto_anual DECIMAL(15,2) NOT NULL DEFAULT 0.00,
  estado tinyint(1) NOT NULL
)ENGINE=InnoDB;

CREATE TABLE Cargo (
  id_cargo     SMALLINT AUTO_INCREMENT PRIMARY KEY,
  nombre_cargo VARCHAR(100) UNIQUE NOT NULL,
  salario	   DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  estado tinyint(1) NOT NULL
)ENGINE=InnoDB;  

CREATE TABLE Tipo_Contrato (
  id_tipo_contrato     TINYINT AUTO_INCREMENT PRIMARY KEY,
  nombre_tipocontrato  VARCHAR(100) UNIQUE NOT NULL,
  estado tinyint(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Empleado (
  id_empleado        INT AUTO_INCREMENT PRIMARY KEY,
  cedula             VARCHAR(20) UNIQUE NOT NULL,          
  primer_nombre      VARCHAR(60)  NOT NULL,
  segundo_nombre     VARCHAR(60)  NULL,
  primer_apellido    VARCHAR(60)  NOT NULL,
  segundo_apellido   VARCHAR(60)  NULL,
  fecha_nacimiento   DATE   NOT NULL,
  direccion          VARCHAR(150) NULL,
  ciudad             VARCHAR(80)  NULL,
  pais               VARCHAR(80)  NULL,
  fecha_ingreso      DATETIME  NOT NULL,
  id_cargo           SMALLINT NOT NULL,
  salario            DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  id_tipo_contrato   TINYINT NOT NULL,
  id_departamento    INT NOT NULL,
  estado 			 TINYINT(1) NOT NULL, 
  
  FOREIGN KEY (id_cargo) REFERENCES Cargo(id_cargo)
    ON UPDATE CASCADE ON DELETE RESTRICT,

  FOREIGN KEY (id_tipo_contrato) REFERENCES Tipo_Contrato(id_tipo_contrato)
    ON UPDATE CASCADE ON DELETE RESTRICT,

  FOREIGN KEY (id_departamento) REFERENCES Departamento(id_departamento)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Historial (
  id_historial     INT AUTO_INCREMENT PRIMARY KEY,
  id_empleado      INT NOT NULL,
  id_departamento  INT NOT NULL,
  id_cargo         SMALLINT NOT NULL,
  fecha_inicio     DATE NOT NULL,
  fecha_fin        DATE NULL,    
  estado 			 TINYINT(1) NOT NULL,
  
  FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado)
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_departamento) REFERENCES Departamento(id_departamento)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_cargo) REFERENCES Cargo(id_cargo)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Tipo_cliente (
  id_tipo_cliente TINYINT AUTO_INCREMENT PRIMARY KEY,
  tipo            VARCHAR(40) UNIQUE NOT NULL,
  estado 		  TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Cliente (
  id_cliente      INT AUTO_INCREMENT PRIMARY KEY,
  id_tipo_cliente TINYINT NOT NULL,
  direccion       VARCHAR(150),
  pais            VARCHAR(80),
  ciudad          VARCHAR(80),
  telefono		  VARCHAR(40) UNIQUE NOT NULL,
  correo          VARCHAR(150) UNIQUE NOT NULL,
  estado 		  TINYINT(1) NOT NULL, 
  FOREIGN KEY (id_tipo_cliente) REFERENCES Tipo_cliente(id_tipo_cliente)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Persona_Natural (
  id_cliente       INT PRIMARY KEY,
  cedula           VARCHAR(30) UNIQUE,       
  primer_nombre    VARCHAR(60) NOT NULL,
  segundo_nombre   VARCHAR(60),
  primer_apellido  VARCHAR(60) NOT NULL,
  segundo_apellido VARCHAR(60),
  estado 		   TINYINT(1) NOT NULL, 
  FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Empresa (
  id_cliente   INT PRIMARY KEY,
  nombre       VARCHAR(150),                   
  rut          VARCHAR(40) UNIQUE,            
  razon_social VARCHAR(150) NOT NULL,
  estado 	   TINYINT(1) NOT NULL, 
  FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Estado_Compra (
  id_estado_compra TINYINT AUTO_INCREMENT PRIMARY KEY,
  estado_compra    VARCHAR(50) UNIQUE NOT NULL,
  estado 	   TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Metodo_Pago (
  id_metodo_pago TINYINT AUTO_INCREMENT PRIMARY KEY,
  metodo_pago    VARCHAR(50) UNIQUE NOT NULL,
  estado 	   TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Proveedor (
  id_proveedor     INT AUTO_INCREMENT PRIMARY KEY,
  rut              VARCHAR(40) UNIQUE NOT NULL,   
  nombre_comercial VARCHAR(150) NOT NULL,
  telefono         VARCHAR(40),
  correo           VARCHAR(150),
  direccion        VARCHAR(150),
  ciudad           VARCHAR(80),
  pais             VARCHAR(80),
  categoria        VARCHAR(80),
  calificacion     DECIMAL(3,2),
  estado 	       TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Producto (
  id_producto           INT AUTO_INCREMENT PRIMARY KEY,
  sku                   VARCHAR(60) UNIQUE NOT NULL,
  nombre                VARCHAR(150) NOT NULL,
  descripcion           TEXT,
  categoria             VARCHAR(80),
  precio_compra         DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  precio_venta_sugerido DECIMAL(12,2),        
  stock_minimo          INT UNSIGNED DEFAULT 0,
  stock_actual          INT UNSIGNED DEFAULT 0,
  stock_maximo          INT UNSIGNED DEFAULT 0,
  estado 	            TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Proveedor_Producto (
  id_proveedor_producto INT AUTO_INCREMENT PRIMARY KEY,
  id_producto           INT NOT NULL,
  id_proveedor          INT NOT NULL,
  calificacion          DECIMAL(3,2),
  estado 	   			TINYINT(1) NOT NULL,
  FOREIGN KEY (id_producto)  REFERENCES Producto(id_producto)
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Orden_Compra (
  id_orden_compra     INT AUTO_INCREMENT PRIMARY KEY,
  id_proveedor        INT NOT NULL,
  id_proyecto       INT NULL,                  
  numero              VARCHAR(40) UNIQUE NOT NULL,       
  fecha_orden         DATE NOT NULL,
  fecha_entrega_esperada DATE NULL,
  fecha_entrega_real     DATE NULL,
  id_estado_compra    TINYINT NOT NULL,
  estado 	   		TINYINT(1) NOT NULL,
  FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_estado_compra) REFERENCES Estado_Compra(id_estado_compra)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Detalle_Orden_Compra (
  id_detalle_orden_compra INT AUTO_INCREMENT PRIMARY KEY,
  id_orden_compra         INT NOT NULL,
  id_producto             INT NOT NULL,
  cantidad                INT UNSIGNED NOT NULL,
  precio_unitario         DECIMAL(12,2) NOT NULL,
  estado 	   			  TINYINT(1) NOT NULL,
  FOREIGN KEY (id_orden_compra) REFERENCES Orden_Compra(id_orden_compra)
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Estado_Factura (
  id_estado_factura TINYINT AUTO_INCREMENT PRIMARY KEY,
  nombre            VARCHAR(40) UNIQUE NOT NULL,
  estado 	   			  TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Factura_Compra (
  id_factura_compra INT AUTO_INCREMENT PRIMARY KEY,
  id_orden_compra   INT NOT NULL,
  numero            VARCHAR(60) UNIQUE NOT NULL,     
  fecha_factura     DATETIME NOT NULL,
  monto_total       DECIMAL(12,2) NOT NULL,
  id_estado_factura    TINYINT NOT NULL,
  estado 	   			  TINYINT(1) NOT NULL,
  FOREIGN KEY (id_estado_factura ) REFERENCES Estado_Factura(id_estado_factura)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_orden_compra) REFERENCES Orden_Compra(id_orden_compra)
    ON UPDATE CASCADE ON DELETE RESTRICT 
) ENGINE=InnoDB; 

CREATE TABLE Pago (
  id_pago          INT AUTO_INCREMENT PRIMARY KEY,
  id_factura_compra INT NOT NULL,
  id_metodo_pago    TINYINT NOT NULL,
  fecha_pago        DATETIME NOT NULL,
  monto             DECIMAL(12,2) NOT NULL,
  estado 	   			  TINYINT(1) NOT NULL,
  FOREIGN KEY (id_factura_compra) REFERENCES Factura_Compra(id_factura_compra)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_metodo_pago) REFERENCES Metodo_Pago(id_metodo_pago)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Tipo_Proyecto (
  id_tipo_proyecto TINYINT AUTO_INCREMENT PRIMARY KEY,
  tipo_proyecto VARCHAR(80) UNIQUE NOT NULL,
  estado 	   			  TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Rol_empleado (
  id_rol_empleado SMALLINT AUTO_INCREMENT PRIMARY KEY,
  rol_empleado VARCHAR(80) UNIQUE NOT NULL,
  tarifa_hora DECIMAL(10,2) NULL,
  estado 	  TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Proyecto (
  id_proyecto      INT AUTO_INCREMENT PRIMARY KEY,
  id_tipo_proyecto TINYINT NOT NULL,
  id_cliente       INT NOT NULL,
  id_departamento  INT NOT NULL,
  codigo           VARCHAR(40) UNIQUE NOT NULL,
  nombre           VARCHAR(150) NOT NULL,
  descripcion      TEXT,
  fecha_inicio         DATE NOT NULL,
  fecha_fin_estimada   DATE NULL,
  fecha_fin_real       DATE NULL,
  presupuesto_aprobado DECIMAL(14,2) NULL,
  presupuesto_utilizado DECIMAL(14,2) NULL,
  estado 	   			  TINYINT(1) NOT NULL,

  FOREIGN KEY (id_tipo_proyecto) REFERENCES Tipo_Proyecto(id_tipo_proyecto)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_departamento) REFERENCES Departamento(id_departamento)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Factura_Venta (
  id_factura_venta INT AUTO_INCREMENT PRIMARY KEY,
  id_proyecto      INT NULL,            
  id_cliente       INT NOT NULL,
  numero           VARCHAR(60) UNIQUE NOT NULL,  
  fecha_factura_venta DATETIME NOT NULL,
  subtotal         DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  impuestos        DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  total            DECIMAL(14,2) NOT NULL,
  id_estado_factura           TINYINT(40) NULL,      -- pendiente, pagada, anulada, etc.
  estado 	   			  TINYINT(1) NOT NULL,
  
  FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto)
    ON UPDATE CASCADE ON DELETE SET NULL,
  FOREIGN KEY (id_estado_factura ) REFERENCES Estado_Factura(id_estado_factura)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Detalle_Factura_Venta (
  id_detalle_factura_venta INT AUTO_INCREMENT PRIMARY KEY,
  id_factura_venta INT NOT NULL,
  id_producto      INT NOT NULL,
  cantidad         INT UNSIGNED NOT NULL,
  precio_unitario  DECIMAL(14,2) NOT NULL,
  tipo             VARCHAR(40) NULL,     
 
  FOREIGN KEY (id_factura_venta)
    REFERENCES Factura_Venta(id_factura_venta)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_dfv_producto FOREIGN KEY (id_producto)
    REFERENCES Producto(id_producto)
    ON UPDATE CASCADE ON DELETE RESTRICT,

  UNIQUE (id_factura_venta, id_producto)
) ENGINE=InnoDB;

CREATE TABLE Estado_Transaccion (
  id_estado_transaccion TINYINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(40) UNIQUE NOT NULL,
  estado 	   	TINYINT(1) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Transaccion (
  id_transaccion      INT AUTO_INCREMENT PRIMARY KEY,
  id_factura_venta    INT NOT NULL,
  id_metodo_pago      TINYINT NOT NULL,
  id_estado_transaccion TINYINT NOT NULL,
  valor               DECIMAL(14,2) NOT NULL,
  fecha_hora          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado 	   	      TINYINT(1) NOT NULL,

  FOREIGN KEY (id_factura_venta) REFERENCES Factura_Venta(id_factura_venta)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_metodo_pago) REFERENCES Metodo_Pago(id_metodo_pago)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_estado_transaccion) REFERENCES Estado_Transaccion(id_estado_transaccion)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Empleado_Proyecto (
  id_empleado_proyecto INT AUTO_INCREMENT PRIMARY KEY,
  id_empleado   INT NOT NULL,
  id_proyecto   INT NOT NULL,
  id_rol        SMALLINT NOT NULL,
  fecha_inicio  DATE NOT NULL,
  fecha_fin     DATE NULL,
  horas_trabajadas DECIMAL(10,2) NULL,
  estado 	   	TINYINT(1) NOT NULL,

  FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto)
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (id_rol) REFERENCES Rol_empleado(id_rol_empleado)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;
