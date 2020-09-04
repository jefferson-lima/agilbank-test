O projeto foi desenvolvido em Java 8 utilizando Spring Batch e Maven.

O diretório de entrada padrão é o `HOMEPATH/data/in`, sendo `HOMEPATH` uma variável
de ambiente.

A saída é escrita em `HOMEPATH/data/out/output.done.dat`

O arquivo de saída só é gerado se houver arquivos `*.dat` no diretório de entrada.

## Executando com o Maven

Para iniciar a execução deve ser usado o seguinte comando:

`./mvnw clean spring-boot:run`

O processo ficará rodando indefinidamente, executando o job a cada minuto. Esse tempo 
pode ser configurado no `application.properties`

Para executar os testes deve ser rodado o seguinte comando:

`./mvnw clean test`

Para executar no Windows, trocar `mvnw` por `mvnw.cmd`

## Executando com Docker

Criando a imagem:

`docker build -t agilbank .`

Executando:

`docker run --mount type=bind,source="HOMEPATH,target=/homepath agilbank`

Onde HOMEPATH é o diretório no host que contém os diretórios de entrada e saída `data/in` e `data/out`

