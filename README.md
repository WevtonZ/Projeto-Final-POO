# Projeto-Final-POO
Repositório para o projeto final de POO. Matéria ministrada pelo professor Fábio Moreira Costa, no Instituto de Informática - UFG.

# Propósito do Projeto Final
Este é um simulador de Cidades Inteligentes, onde cada grupo estará criando apenas um módulo do projeto, visto que o projeto inteiro em si seria muito longo. No módulo selecionado para o nosso grupo, estaremos apresentando sobre um sistema de lixeiras inteligentes. O propósito desse módulo é otimizar o sistema de coleta de lixo da cidade: ir atrás apenas das lixeiras que estão cheias para otimizar a rota do caminhão de lixo.

# Padrões de projeto utilizados até o momento:
Padrão Singleton: Usado na Central de Controle do código, para gerarmos apenas uma instância da Central de Controle. Não faz sentido termos várias instâncias da central fazendo coisas diferentes, então é usado esse padrão para que não haja duas centrais executando comandos diferentes, podendo até entrar em conflito.

Padrão Factory Method: Usado na criação de instâncias da lixeira. Cada lixeira tem um ID, e deixar isso para o usuário controlar quais IDs já foram usados pode ser um problema. Então, delegamos a tarefa de criar a lixeira com o ID correto para um método "fábrica", que controla os IDs já gerados e garante que, sempre que uma lixeira for criada, ela tenha um valor diferente das outras lixeiras.

Padrão Iterator: Utilizado na hora de percorrer as listas de lixeiras. Utilizado de forma implícita pela linguagem, na utilização de ArrayList de Lixeiras Inteligentes.

Padrão Observer: Utilizado na Central de Controle como uma observadora e na Lixeira Inteligente como um observado. A ideia é que a lixeira notifica a central que ela está cheia para tirar o trabalho da central de verificar todas as lixeiras para saber, uma por uma, se elas estão cheias ou não. Ou seja, a tarefa de ver se a lixeira está cheia foi delegada para a própria lixeira e não é algo que a central de controle vai fazer.
