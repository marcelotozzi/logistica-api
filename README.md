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

Usei o MongoDB para persistência dos mapas, Neo4j para montar as rotas e fazer a busca.

No começo usei o Redis para persistir o mapa e depois alterei para MongoDB. O Redis, sendo apenas um chave-valor, caso eu precisasse buscar algo dentro do json ou atualizá-lo teria que reescrever por completo. No MongoDB isso seria mais fácil, por isso a mudança. Usei o Neo4j pois ele já tem nativo o algoritmo de Dijkstra, fazendo com que apenas precise mapear as rotas.
Claro, levando em consideração uma aplicação real existem dezenas de coisas para fazer, mas para uma POC acredito que funcione.


Neo4j
-------------
Crie o diretório onde o Neo4j Embedded vai guardar os dados do grafo:
```
mkdir /logistica-db
```

MongoDB
-------------
Coloque a url do seu MongoDB na classe MongoDBFactory
```
private String uri = "mongodb://<usuario>:<senha>@<seu-mongo-db>:<porta>/logistica"
```

Agora é só rodar no diretório que você clonou:
```
mvn clean install
```

Pegar o war gerado e fazer o deploy no seu tomcat.

Teste - Enviando mapa
-------------
Envie o json do mapa:
```
curl -X POST 
    -H 'Content-Type:application/json' 
    --data @deliveryMap.json 
    localhost:8080/api/mapa
    -v
```

O retorno do header deve conter o Location do mapa registrado:
```
* Connected to localhost (::1) port 8080 (#0)
> POST /api/mapa HTTP/1.1
> User-Agent: curl/7.30.0
> Host: localhost:8080
> Accept: */*
> Content-Type:application/json
> Content-Length: 589
< HTTP/1.1 201 Created
* Server Apache-Coyote/1.1 is not blacklisted
< Server: Apache-Coyote/1.1
< Location: http://localhost:8080/api/mapa/KZMEFWXJC5Y1LGHP7U9N
< Content-Length: 0
< Date: Thu, 04 Sep 2014 19:49:19 GMT
< 
```

Teste - Buscando a rota
-------------
```
curl -X POST 
    -d '{"mapa": "SP","origem": "C","destino": "B","autonomia": 15,"litro": 3.00}' 
    -H "Content-Type: application/json"
    http://localhost:8080/api/rota 
```

Retorno:
```
{"rota":["C","A","B"],"custo":6.00}
```
