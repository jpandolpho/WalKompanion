# WalKompanion
Este projeto foi feito em função de uma atividade avaliativa da disciplina ARQDMO2 do IFSP - Campus Araraquara no semestre 2025/1, ministrada pelo professor Henrique Buzeto Galati.

A atividade requeria o desenvolvimente de um aplicativo que fizesse uso de ao menos dois tipos diferentes de sensores, com o tema para o aplicativo sendo livre.

## Descrição
A proposta do aplicativo é ser um "companheiro" em suas caminhadas, marcando a quantidade de passos, altitude máxima e altitude mínimas atingidas durante a caminhada, duração e, através de um cálculo aproximado, a distância percorrida durante esta caminhada.

O nome do aplicativo, WalKompanion, vem de uma mistura entre as palavras Walk (do inglês, caminhada) e Companion (do inglês, companheiro), fazendo a remoção da primeira letra da segunda palavra, a junção das duas palavras e, então, a capitalização da letra "k".

## Recursos utilizados
Para este projeto, foram utilizados os sensores de passos (sendo mais específico, o contador de passos) e o sensor de pressão atmosférica. O sensor de passos foi pensado com o principal instrumento do aplicativo, sendo utilizado para, como o nome sugere, contar os passos.
O sensor de pressão atmosférica foi utilizado para se medir a altitude relativa durante a caminhada.

Em termos de recursos externos ao celular, foi utilizado o Google Firebase para duas coisas:
- Autenticação de usuários através de email e senha, utilizando a função do Authentication;
- Banco de dados para a aplicação, utilizando a função Firestore Database.

Para desenvolvimente desta aplicação, foi utilizado:
- Android Studio 2024.2.2 (Ladybug Feature Drop)

## Vídeo demonstrativo
No vídeo a seguir, está uma demonstração do aplicativo gravado com o software Open Broadcaster Software, gravando o emulador Android do Android Studio e os controles avançados, mostrando os sensores do dispositivo virtual.

É de suma importância ressaltar que, no vídeo, é possível ver um botão com o texto "ANDAR" no Fragment onde está sendo exibida uma caminhada em andamento. Este botão **NÃO ESTÁ PRESENTE** no apk disponível neste repositório, nem em versões posteriores a publicação 
deste README do código disponível no repositório.
O botão existe para fins de teste pois o aparelho utilizado no emulador não possuía o sensor de contagem de passos necessário para simular um uso real do aplicativo. É importante ressaltar que o aplicativo foi também testado em um dispositivo real que possuía o sensor, e
que ele funciona normalmente sob estas circunstâncias.



https://github.com/user-attachments/assets/4087aefb-37e2-45b9-aece-d92cb580996b


## APK
Como citado no item anterior, existe um .apk deste aplicativo disponível dentro do diretório apk, e possui uma versão do aplicativo feita até [este commit](https://github.com/jpandolpho/WalKompanion/tree/7d8bb3e53bd4897bbfe5e9985e9103248416a816).

## Possíveis melhorias no aplicativo
Uma das possíveis melhorias para o aplicativo seria a implementação de uma integração com a API do Google Maps, de forma que seja possível ter uma informação mais precisa da distância caminhada pelo usuário, tal como também o percurso em si da caminhada.

Outra possível melhoria seria a melhoria da tela de histórico, que no momento utiliza um botão para carregar mais caminhadas, para passar a ser um "scroll infinito", parecido com o feed de diversos aplicativos de redes sociais.
Foi pesquisado como era feita essa implementação, porém, por falta de claridade e explicações nos materiais encontrados demonstrando essa funcionalidade, ela não foi implementada.
