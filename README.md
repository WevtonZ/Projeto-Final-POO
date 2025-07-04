# Projeto-Final-POO
Repositório para o projeto final de POO. Matéria ministrada pelo professor Fábio Moreira Costa, no Instituto de Informática - UFG.

Membros: 

**Henrique Mendes**

**Wevton Junior F. Santana**

## Propósito do Projeto Final
Este é um simulador de Cidades Inteligentes, onde cada grupo estará criando apenas um módulo do projeto, visto que o projeto inteiro em si seria muito longo. No módulo selecionado para o nosso grupo, estaremos apresentando sobre um sistema de lixeiras inteligentes. O propósito desse módulo é otimizar o sistema de coleta de lixo da cidade: ir atrás apenas das lixeiras que estão cheias para otimizar a rota do caminhão de lixo.

## Padrões de projeto utilizados até o momento:
Padrão Singleton: Usado na Central de Controle do código, para gerarmos apenas uma instância da Central de Controle. Não faz sentido termos várias instâncias da central fazendo coisas diferentes, então é usado esse padrão para que não haja duas centrais executando comandos diferentes, podendo até entrar em conflito.

Padrão Factory Method: Usado na criação de instâncias da lixeira. Cada lixeira tem um ID, e deixar isso para o usuário controlar quais IDs já foram usados pode ser um problema. Então, delegamos a tarefa de criar a lixeira com o ID correto para um método "fábrica", que controla os IDs já gerados e garante que, sempre que uma lixeira for criada, ela tenha um valor diferente das outras lixeiras.

Padrão Iterator: Utilizado na hora de percorrer a lista de caminhões.

Padrão Observer: Utilizado na Central de Controle como uma observadora e na Lixeira Inteligente como um observado. A ideia é que a lixeira notifica a central que ela está cheia para tirar o trabalho da central de verificar todas as lixeiras para saber, uma por uma, se elas estão cheias ou não. Ou seja, a tarefa de ver se a lixeira está cheia foi delegada para a própria lixeira e não é algo que a central de controle vai fazer.

## Como executar:

Primeiro clone o repositório usando **git clone https://github.com/WevtonZ/Projeto-Final-POO/edit/main/README.md** .

É preferível que seja usado o Intellij para rodar o Simulador, que é onde se encontra o programa principal. Com o Intellij em mãos, devemos adicionar uma configuração nova. Vamos ao passo a passo disso:

1. No topo do programa aberto, teremos a seguinte imagem:

   ![image](https://github.com/user-attachments/assets/b53a5fff-ab0a-410e-a60a-748fbe791236)
   
  Vamos em Edit Configurations...

3. Nela, veremos a seguinte guia:
   
     ![image](https://github.com/user-attachments/assets/24ec05d3-2a17-4070-9e86-ca3b224eeded)

Aqui, apertaremos o botao **+**, encontrado no canto superior esquerdo, e escolheremos Application. Com isso, aparecerá a seguinte tela:

![image](https://github.com/user-attachments/assets/6efd5e2c-38ac-4ca4-bcab-6c402f0d7fec)
   
   Aonde está o campo escrito "Main class", aperte no botão do papel dentro dessa caixa que ele buscará uma instância da Main no seu código. Como só existe uma, ele achará uma instância na classe Simulador, que é a classe que queremos. Selecione-a, e agora, teremos a seguinte imagem.
  
   ![image](https://github.com/user-attachments/assets/8b8ad5db-970d-4195-8eb4-0225dbed9795)
   
   Abaixo, teremos um outro campo escrito "Program Arguments", e nele, colocaremos o nome do nosso arquivo .txt. Eu escolhi o nome **simulacao.txt**.
  
   ![image](https://github.com/user-attachments/assets/002a6c3d-b72d-4842-bace-d25929187c97)
  
  Agora, cliquemos em Apply no canto inferior direito e depois em OK.

  Agora, todos os casos de teste que quisermos rodar, sairá do arquivo simulacao.txt.


## UML

[![](https://mermaid.ink/img/pako:eNqVVttu2zgQ_RWBfbFjW7DS2LGJIECRoECAFC3aYh9a92EiTRS2EmlQVOA0Tf9nv2N_bEe3mJKo1NWDRA7neuZCPbJQRcg4CxPIsksBsYZ0Iz16Sor3D4owT5T3WBGL55WIuCeksSiJCt-YHBLuXasQEvETQlD78wnuMMwN6M-5lmo0PqqOnmxDF5AKeQfqEi9UggZsi7MQtu9gJ1LgXqTymwRbZzqG2nrvMDNg8ox7n8pvY8NiMGKruPeZ3pd4LXa200KKUID-qAyMNL24VyzHw2E5oiKVKDRcSYOJiJE-rbiGrc96IBcgXAujVeaIkxIwgP0sIb2HwVM7awUIUVS4NbpXSZ5io2Dc5nh_k6G-Rz2iUJq1zaIxVff4Jy6pjLh9aE6yl9B8C6FR-qGF5Far3VUXskmoKYG11KiL0dQjRJuYpl43F2PuyJ6rcImuISnqlhZUuy2_hCSMJdUR7zNabLcigabyC7uZ-d03_tsWKCqSJMqKVpjVQt0uskQmMZqrxpvR-EV_KGcxqdN78KS6h3rtwsXOZL6NwOCoTKgIIVKaCiy_-Y6hsdkizLYQ3oF-W4TizHfRb-0sk6cqOxSodu3sE99SuXO0xUOfNolEDd0HIDhUbkog9krHbZmW7U8izZMCCNvyJAUhRzS8yv7TQsZfv7lAqKGzRc_OqMxR30KI5-dDzVgvDuvJF5kPac3m9BA36wLJqsC6xVGrRZmnvclYEh3DyqLvZ3x11FxgZ79ms94lU7E0CJ_98v3Bnn8OsOQa6J3q3Z8IGxZsmHdEHmzY3PePaNO777gXo8aiwv5Ci-N-4V6qpKABWetxsFSKlK3oOTzuNY1bu9H1syNcbMo-5V6GcV4DUVJsf4MX_A0pwP_-TW0IO5Pe988HRHUD177HLOZa3sUxkMFeuMOmq_OonXqXuOvnw8nYLXeH3b2-VgMMcHYVuqPr_jYMKLP42JTFWkSMG53jlKWoaZjRlpXdv2HmDlPcME7LCPSPDdvIJ5LZgvyiVNqIaZXHd4zfQpLRrpoK9S_oMwvKCPWFyqVhfFFqYPyR7Wiz8Benr4NgNV8t18erYD1lD4zP_eP5yetVEMxXwenx6fpkdfo0ZT9Lo3N_fbJcBotgsV4EwWK5PJ4yjIpOeVf_Axefp_8BO7mHGg?type=png)](https://mermaid.live/edit#pako:eNqVVttu2zgQ_RWBfbFjW7DS2LGJIECRoECAFC3aYh9a92EiTRS2EmlQVOA0Tf9nv2N_bEe3mJKo1NWDRA7neuZCPbJQRcg4CxPIsksBsYZ0Iz16Sor3D4owT5T3WBGL55WIuCeksSiJCt-YHBLuXasQEvETQlD78wnuMMwN6M-5lmo0PqqOnmxDF5AKeQfqEi9UggZsi7MQtu9gJ1LgXqTymwRbZzqG2nrvMDNg8ox7n8pvY8NiMGKruPeZ3pd4LXa200KKUID-qAyMNL24VyzHw2E5oiKVKDRcSYOJiJE-rbiGrc96IBcgXAujVeaIkxIwgP0sIb2HwVM7awUIUVS4NbpXSZ5io2Dc5nh_k6G-Rz2iUJq1zaIxVff4Jy6pjLh9aE6yl9B8C6FR-qGF5Far3VUXskmoKYG11KiL0dQjRJuYpl43F2PuyJ6rcImuISnqlhZUuy2_hCSMJdUR7zNabLcigabyC7uZ-d03_tsWKCqSJMqKVpjVQt0uskQmMZqrxpvR-EV_KGcxqdN78KS6h3rtwsXOZL6NwOCoTKgIIVKaCiy_-Y6hsdkizLYQ3oF-W4TizHfRb-0sk6cqOxSodu3sE99SuXO0xUOfNolEDd0HIDhUbkog9krHbZmW7U8izZMCCNvyJAUhRzS8yv7TQsZfv7lAqKGzRc_OqMxR30KI5-dDzVgvDuvJF5kPac3m9BA36wLJqsC6xVGrRZmnvclYEh3DyqLvZ3x11FxgZ79ms94lU7E0CJ_98v3Bnn8OsOQa6J3q3Z8IGxZsmHdEHmzY3PePaNO777gXo8aiwv5Ci-N-4V6qpKABWetxsFSKlK3oOTzuNY1bu9H1syNcbMo-5V6GcV4DUVJsf4MX_A0pwP_-TW0IO5Pe988HRHUD177HLOZa3sUxkMFeuMOmq_OonXqXuOvnw8nYLXeH3b2-VgMMcHYVuqPr_jYMKLP42JTFWkSMG53jlKWoaZjRlpXdv2HmDlPcME7LCPSPDdvIJ5LZgvyiVNqIaZXHd4zfQpLRrpoK9S_oMwvKCPWFyqVhfFFqYPyR7Wiz8Benr4NgNV8t18erYD1lD4zP_eP5yetVEMxXwenx6fpkdfo0ZT9Lo3N_fbJcBotgsV4EwWK5PJ4yjIpOeVf_Axefp_8BO7mHGg)
