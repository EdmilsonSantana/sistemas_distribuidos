
DROP DATABASE PROJETOSUPERMERCADO;
CREATE DATABASE PROJETOSUPERMERCADO;
USE PROJETOSUPERMERCADO;


create table tb_fornecedor
(
 cnpj_fornecedor varchar(30),
 razao_social varchar(100),
 nome_fantasia varchar(50),
 PRIMARY KEY(cnpj_fornecedor)
);


create table tb_produto
(
    codigo_produto varchar(20),
    produto_descricao varchar(50),
    produto_categoria varchar(20),
    quantidade_estoque int,
    preco_atual double default 0.0,
    PRIMARY KEY(codigo_produto)
);


create table tb_compra_fornecedor
(
	data_compra timestamp default current_timestamp(),
	codigo_compra varchar(20),
	cnpj_fornecedor varchar(30),
	preco_compra double default 0.0,
        PRIMARY KEY(codigo_compra, cnpj_fornecedor),
  	FOREIGN KEY (cnpj_fornecedor) REFERENCES tb_fornecedor(cnpj_fornecedor)
	
);



create table tb_compras_produtos_fornecedor
(

	codigo_compra varchar(20),
	codigo_produto varchar(20),
	quantidade_fornecida int,
	preco_total_produto double,
	preco_unitario_produto double,
	PRIMARY KEY(codigo_compra, codigo_produto),
        FOREIGN KEY (codigo_compra) REFERENCES 	tb_compra_fornecedor(codigo_compra)
        ON DELETE CASCADE,
	FOREIGN KEY (codigo_produto) REFERENCES tb_produto(codigo_produto)
);


create table tb_setor 
(
	setor_id smallint(5),
	setor_nome varchar(30),
	setor_descr varchar(50),
        cpf_gerente_setor varchar(20),
	PRIMARY KEY (setor_id)
	
);

create table tb_funcionario
(
   setor_id smallint(5),
   cpf_func varchar(20),
   nome varchar(50),
   salario double,
   PRIMARY KEY(cpf_func), 
   FOREIGN KEY (setor_id) REFERENCES tb_setor(setor_id)
   ON DELETE SET NULL
);
   
ALTER TABLE tb_setor
ADD FOREIGN KEY (cpf_gerente_setor) 
REFERENCES tb_funcionario(cpf_func)
ON DELETE SET NULL;


create table tb_cliente 
(
    cpf_cliente varchar(20),
    nome_cliente varchar(40),
    endereco_cliente varchar(40),
    telefone_cliente varchar(40),
    PRIMARY KEY (cpf_cliente) 
);


create table tb_vendas_clientes
(
    data_entrada timestamp default current_timestamp(),
    codigo_venda varchar(20),
    cpf_cliente varchar(20),
    cpf_func varchar(20),
    preco_venda double default 0.0,
    PRIMARY KEY(codigo_venda),
    FOREIGN KEY (cpf_cliente) REFERENCES tb_cliente(cpf_cliente)
    ON DELETE NO ACTION,
    FOREIGN KEY (cpf_func) REFERENCES tb_funcionario(cpf_func)
);
	
create table tb_vendas_produtos_clientes
(
	codigo_venda varchar(20),
	codigo_produto varchar(20),
	quantidade_venda int,
	preco_total_produto double default 0.0,
	preco_unitario_produto double,
	PRIMARY KEY(codigo_venda, codigo_produto),
        FOREIGN KEY (codigo_venda) REFERENCES tb_vendas_clientes(codigo_venda)
        ON DELETE CASCADE,
	FOREIGN KEY (codigo_produto) REFERENCES tb_produto(codigo_produto)
);
    
	

INSERT INTO TB_CLIENTE values("286.588.217-90", "Edmilson Santana",
"Candeias", "95246671");

INSERT INTO TB_CLIENTE values("492.546.858-87", "Clarice Queiroz",
"Setúbal", "95247765");

INSERT INTO TB_CLIENTE values("588.472.471-36", "Douglas Albuquerque",
"Olinda", "98672565");

INSERT INTO TB_CLIENTE values("054.788.504-03", "Laura Regina",
"Candeias", "88762564");

INSERT INTO tb_fornecedor
values("54.521.703/0001-94", "AÇUCAREIRA BOA VISTA LTDA", "");

INSERT INTO tb_fornecedor
values("94.669.611/0001-70", "FARINHAS INTEGRAIS CISBRA LTDA", "CISBRA");

INSERT INTO tb_fornecedor
values("64.904.295/0001-03", "CAMIL ALIMENTOS S/A", "Camil");

INSERT INTO tb_fornecedor
values("54.516.661/0001-01", "JOHNSON & JOHNSON DO BRASIL IND. E COM. DE PRODUTOS PARA SAÚDE LTDA", "JOHNSON & JOHNSON");

INSERT INTO tb_produto
values("983445234234",  "Açúcar Cristal 1 kg", "Alimentos", 100, 3.50);

INSERT INTO tb_produto
values("783445234234", "Antisséptico Bucal", "Higiene Pessoal", 50, 3.00);

INSERT INTO tb_setor (setor_id, setor_nome, setor_descr)
values(1, "Estoque", "Setor de gerenciamento e organização de produtos");

INSERT INTO tb_setor (setor_id , setor_nome, setor_descr)
values(2, "Vendas", "Setor de vendas");

INSERT INTO tb_setor (setor_id, setor_nome, setor_descr)
values(3, "Administração", "Setor de administração e gerenciamento");

INSERT INTO tb_funcionario
values(2, "436.354.384-13", "Flávio Carmo", 1000.00);

INSERT INTO tb_funcionario
values(2, "072.611.719-64", "Lucas Santana", 2000.00);

INSERT INTO tb_funcionario
values(1, "373.566.744-91", "Luana Dias", 3000.00);

INSERT INTO tb_funcionario
values(1, "567.710.641-07", "Pedro Carlos", 1000.00);

INSERT INTO tb_funcionario
values(3, "876.957.538-07", "Julio Iglesias", 1000.00);

INSERT INTO tb_funcionario
values(3, "795.775.245-40", "Renata Campos", 3000.00);

UPDATE tb_setor
SET cpf_gerente_setor = "373.566.744-91"
WHERE setor_id = 1;

UPDATE tb_setor
SET cpf_gerente_setor = "072.611.719-64"
WHERE setor_id = 2;

UPDATE tb_setor
SET cpf_gerente_setor = "795.775.245-40"
WHERE setor_id = 3;

INSERT INTO tb_compra_fornecedor
(codigo_compra, cnpj_fornecedor)
VALUES("1456","54.516.661/0001-01");

INSERT INTO tb_compra_fornecedor
(codigo_compra, cnpj_fornecedor)
VALUES("2365","94.669.611/0001-70");

INSERT INTO tb_compra_fornecedor
(codigo_compra, cnpj_fornecedor)
VALUES("2453","64.904.295/0001-03");

INSERT INTO tb_compra_fornecedor
(codigo_compra, cnpj_fornecedor)
VALUES("3287", "54.521.703/0001-94");

INSERT INTO tb_vendas_clientes(codigo_venda, cpf_cliente, cpf_func)
values("3892", "286.588.217-90", "072.611.719-64");
INSERT INTO tb_vendas_clientes(codigo_venda, cpf_cliente, cpf_func)
values("234","588.472.471-36", "373.566.744-91");
INSERT INTO tb_vendas_clientes(codigo_venda, cpf_cliente, cpf_func)
values("456", "054.788.504-03", "567.710.641-07");


DELIMITER $$
CREATE TRIGGER incrementar_preco_compra AFTER INSERT
ON tb_compras_produtos_fornecedor
FOR EACH ROW
BEGIN

    UPDATE tb_compra_fornecedor
    SET preco_compra = preco_compra + (NEW.quantidade_fornecida * NEW.preco_unitario_produto)
    WHERE (codigo_compra = NEW.codigo_compra);  

END$$

DELIMITER ;


DELIMITER $$
CREATE TRIGGER incrementar_preco__venda AFTER INSERT
ON tb_vendas_produtos_clientes
FOR EACH ROW
BEGIN
   UPDATE tb_vendas_clientes
   SET preco_venda = preco_venda + (NEW.quantidade_venda * NEW.preco_unitario_produto)
   WHERE (codigo_venda = NEW.codigo_venda);  
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER atualizar_venda BEFORE INSERT
ON tb_vendas_produtos_clientes
FOR EACH ROW
BEGIN
	DECLARE quantidade int;
	DECLARE preco double;
    
    SELECT quantidade_estoque
    FROM tb_produto
    WHERE codigo_produto = NEW.codigo_produto
    INTO quantidade;
    
    IF(quantidade - NEW.quantidade_venda >= 0) THEN
		UPDATE tb_produto
		SET quantidade_estoque = quantidade_estoque - NEW.quantidade_venda
		WHERE codigo_produto = NEW.codigo_produto;
    ELSE
		SET NEW.codigo_produto = null;
    END IF;
    
    IF(NEW.preco_unitario_produto <= 0) THEN
		SELECT preco_atual
		FROM tb_produtos
		WHERE codigo_produto = NEW.codigo_produto
		INTO preco;
		
		SET NEW.preco_unitario_produto = preco;
	END IF;
     
END$$

DELIMITER ;

INSERT INTO tb_vendas_produtos_clientes
(codigo_venda, codigo_produto, quantidade_venda, preco_unitario_produto)
values("3892", "983445234234", 4, 7.00);
INSERT INTO tb_vendas_produtos_clientes 
(codigo_venda, codigo_produto, quantidade_venda, preco_unitario_produto)
values("234", "983445234234", 2, 7.00);
INSERT INTO tb_vendas_produtos_clientes 
(codigo_venda, codigo_produto, quantidade_venda, preco_unitario_produto)
values("456", "983445234234", 1, 7.00);
INSERT INTO tb_vendas_produtos_clientes 
(codigo_venda, codigo_produto, quantidade_venda, preco_unitario_produto)
values("3892", "783445234234", 6, 8.00);
INSERT INTO tb_vendas_produtos_clientes 
(codigo_venda, codigo_produto, quantidade_venda, preco_unitario_produto)
values("234", "783445234234", 5, 8.00);
INSERT INTO tb_vendas_produtos_clientes 
(codigo_venda, codigo_produto, quantidade_venda, preco_unitario_produto)
values("456", "783445234234", 2, 8.00);



INSERT INTO tb_compras_produtos_fornecedor
( codigo_compra, codigo_produto, quantidade_fornecida, preco_unitario_produto)
VALUES("3287", "983445234234", 50, 5.00);

INSERT INTO tb_compras_produtos_fornecedor 
( codigo_compra, codigo_produto, quantidade_fornecida, preco_unitario_produto)
VALUES("1456", "783445234234", 50, 2.50);

DELIMITER $$

CREATE TRIGGER novo_produto 
BEFORE INSERT
ON tb_compras_produtos_fornecedor
FOR EACH ROW
BEGIN
    DECLARE cont smallint(5);
    SELECT count(*) FROM tb_produto
    WHERE codigo_produto = NEW.codigo_produto
    INTO cont;

    IF(cont <= 0) THEN
        INSERT tb_produto 
        (codigo_produto, quantidade_estoque)
        values(NEW.codigo_produto, NEW.quantidade_fornecida);
    ELSE
        UPDATE tb_produto
        SET quantidade_estoque = quantidade_estoque + NEW.quantidade_fornecida
        WHERE codigo_produto = NEW.codigo_produto;
    END IF;
END$$

DELIMITER ;


INSERT INTO tb_compras_produtos_fornecedor
(codigo_compra, codigo_produto, quantidade_fornecida, preco_unitario_produto)
values("2453", "883445134234", 20, 2.50);


DELIMITER $$

CREATE TRIGGER verificar_funcionario
BEFORE INSERT
ON tb_vendas_clientes
FOR EACH ROW
BEGIN
    DECLARE cont smallint(5);
    SELECT count(*) FROM tb_funcionario f
    WHERE (f.cpf_func = NEW.cpf_func) AND (setor_id IN 
    (SELECT setor_id FROM tb_setor    
     WHERE (setor_nome = "Vendas") 
     AND (cpf_gerente_setor != NEW.cpf_func)))
    INTO cont;              

  
     IF(cont >= 0) THEN
        SET new.codigo_venda = null;
     END IF;
END$$

DELIMITER ;

insert into tb_vendas_clientes
(data_entrada, codigo_venda, cpf_cliente, cpf_func)
values(now(),"4", "286.588.217-90", "373.566.744-91");


CREATE VIEW 