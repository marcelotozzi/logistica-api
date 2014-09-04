.: Entregando mercadorias
=========

O supermercado esta desenvolvendo um novo sistema de logistica e sua ajuda é muito importante neste momento. Sua tarefa será desenvolver o novo sistema de entregas visando sempre o menor custo. Para popular sua base de dados o sistema precisa expor um Webservices que aceite o formato de malha logística (exemplo abaixo), nesta mesma requisição o requisitante deverá informar um nome para este mapa. É importante que os mapas sejam persistidos para evitar que a cada novo deploy todas as informações desapareçam. O formato de malha logística é bastante simples, cada linha mostra uma rota: ponto de origem, ponto de destino e distância entre os pontos em quilômetros.

- A B 10
- B D 15
- A C 20
- C D 30
- B E 50
- D E 30

Com os mapas carregados o requisitante irá procurar o menor valor de entrega e seu caminho, para isso ele passará o mapa, nome do ponto de origem, nome do ponto de destino, autonomia do caminhão (km/l) e o valor do litro do combustivel, agora sua tarefa é criar este Webservices. Um exemplo de entrada seria, mapa SP, origem A, destino D, autonomia 10, valor do litro 2,50; a resposta seria a rota A B D com custo de 6,25.

Voce está livre para definir a melhor arquitetura e tecnologias para solucionar este desafio, mas não se esqueça de contar sua motivação no arquivo README que deve acompanhar sua solução, junto com os detalhes de como executar seu programa. Documentação e testes serão avaliados também =) Lembre-se de que iremos executar seu código com malhas beeemm mais complexas, por isso é importante pensar em requisitos não funcionais também!!

Também gostariamos de acompanhar o desenvolvimento da sua aplicação através do código fonte. Por isso, solicitamos a criação de um repositório que seja compartilhado com a gente. Para o desenvolvimento desse sistema, nós solicitamos que você utilize a sua (ou as suas) linguagem de programação principal. Pode ser Java, JavaScript ou Ruby.

-------------

Diretório para o Neo4j
-------------
Crie o diretório onde o Neo4j Embedded vai guardar os dados do grafo:
```
mkdir /logistica-db
```

Enviando mapa
-------------
```
curl -X POST 
    -H 'Content-Type:application/json' 
    --data @deliveryMap.json 
    http://ec2-54-80-62-123.compute-1.amazonaws.com/logistica-api/api/mapa
    -v
```

O retorno do header deve conter o Location do mapa registrado:
```
* Connected to http://ec2-54-80-62-123.compute-1.amazonaws.com/logistica-api (::1) port 80 (#0)
> POST /api/mapa HTTP/1.1
> User-Agent: curl/7.30.0
> Host: http://ec2-54-80-62-123.compute-1.amazonaws.com/logistica-api:80
> Accept: */*
> Content-Type:application/json
> Content-Length: 589
< HTTP/1.1 201 Created
* Server Apache-Coyote/1.1 is not blacklisted
< Server: Apache-Coyote/1.1
< Location: http://ec2-54-80-62-123.compute-1.amazonaws.com/logistica-api/api/mapa/KZMEFWXJC5Y1LGHP7U9N
< Content-Length: 0
< Date: Thu, 04 Sep 2014 19:49:19 GMT
< 
```

Buscando a rota
-------------
```
curl -X POST 
    -d '{"mapa": "SP","origem": "C","destino": "B","autonomia": 15,"litro": 3.00}' 
    -H "Content-Type: application/json"
    http://ec2-54-80-62-123.compute-1.amazonaws.com/logistica-api/api/rota 
```

Retorno:
```
{"rota":["C","A","B"],"custo":6.00}
```
