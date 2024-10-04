# controlecaixa-api
Teste omega sistemas

## Tecnologias

| Tecnologia  | Versão                |
|-------------|-----------------------|
| Docker      | 26.0.0, build 2ae903e |
| Java        | OpenJDK 21            |
| Maven       | 4.0.0                 |
| Spring Boot | 3.3.4                 |
| MySql       | 16.2-1                |

## MySql
As credenciais de acesso e portas podem ser alteradas antes de subir o docker através das variáveis de arquivo
`application.properties` e `compose.yml`.

|       Porta        |        Nome           |  Usuário  |    Senha    | 
|:------------------:|:---------------------:|:---------:|:-----------:|
|        3306        |     db_controlecaixa   |   admin  | ThePassword |


## Deploy
O projeto está dockerizado, caso não tenha instalado, clickar no link e seguir os passos a baixo.

* [Docker](https://docs.docker.com/desktop/)
  * Para verificar se o docker foi instalado corretamente execute o comando `docker --version`
* [Docker Compose](https://docs.docker.com/compose/install/)
  * Para verificar se o docker-compose foi instalado corretamente execute o comando `docker-compose --version`

O projeto está dockerizado e pode ser executado com o comando abaixo:

```bash
docker-compose up -d
```

### Endpoints

#### POST /caixa

Endpoint para criação de um caixa.

- Request Payload

```
{
  "descricao": "Banco Itau"
}

```
- Response Payload

```json
{
    "success": true,
    "message": null,
    "data": {
        "id": 10,
        "descricao": "Banco Itau",
        "saldoInicial": 0
    }
}
```
----------------------------------------------------------------------------

#### POST /movimentacao

Endpoint para criação de uma movimentacao.

- Request Payload

```
{
  "tipo": "ENTRADA",
  "caixa": {
    "id": 3
  },
  "descricao": "transferencia de 500",
  "valor": 500
}

```
- Response Payload

```json
{
    "data": "04/10/2024",
    "tipo": "ENTRADA",
    "caixa": {
        "id": 3,
        "descricao": "caixa variavel 2",
        "saldoInicial": 2000.00
    },
    "descricao": "transferencia de 500",
    "valor": 500
}
```
----------------------------------------------------------------------------
#### GET /caixa

Endpoint para retornar todos os caixas.

- Response Payload

```json
{
    "success": true,
    "message": null,
    "data": [
        {
            "id": 1,
            "descricao": "novo_caixa",
            "saldoInicial": 0.00
        },
        {
            "id": 10,
            "descricao": "Banco Itau",
            "saldoInicial": 0.00
        }
    ]
}
```
----------------------------------------------------------------------------
#### GET /movimentacao

Endpoint para retornar todas movimentações.

- Response Payload

```json
"success": true,
    "message": null,
    "data": {
        "content": [
            {
                "data": "04/10/2024",
                "tipo": "ENTRADA",
                "caixa": {
                    "id": 10,
                    "descricao": "Banco Itau",
                    "saldoInicial": 500.00
                },
                "descricao": "transferencia de 500",
                "valor": 500.00
            }
```
----------------------------------------------------------------------------
#### GET /movimentacao/intervalo?caixaId=3&dtInicial=2024-10-01&dtFinal=2024-12-31

Endpoint para retornar todas as movimentações do periodo 01/10/2024 até 31/12/2024 de acordo com o Id do Caixa.

- Response Payload

```json
{
    "success": true,
    "message": null,
    "data": {
        "content": [
            {
                "descricao": "transferencia de 500",
                "valor": 500.00
            },
            {
                "descricao": "transferencia de 500",
                "valor": 500.00
            },
            {
                "descricao": "Retirada de 100",
                "valor": 100.00
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 20,
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalElements": 3,
        "totalPages": 1,
        "size": 20,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 3,
        "empty": false
    }
}
```
----------------------------------------------------------------------------

#### GET /balanco?caixaId=10&dtInicial=2024-01-01&dtFinal=2024-12-31

Endpoint para retornar o balanço do ano de acordo com o Id do Caixa.

- Response Payload

```json
{
    "success": true,
    "message": null,
    "data": {
        "entrada": 1000.00,
        "saida": 100.00,
        "saldo": 900.00
    }
}
```
----------------------------------------------------------------------------



