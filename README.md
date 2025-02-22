# 🚀 Álcool ou Gasolina

Aplicativo mobile para Android que ajuda os usuários a decidir qual combustível é mais vantajoso para abastecer, com base nos preços do álcool e da gasolina. O projeto utiliza **Jetpack Compose** para a interface e **Firebase Realtime Database** para armazenamento dos postos de combustível.
[Link para o vídeo](https://drive.google.com/file/d/1CVZfWFpF7jtlmutJQSgBW_VfhNREjeYe/view?usp=sharing)

## 📌 Tecnologias Utilizadas

- **Kotlin** → Linguagem principal do projeto.
- **Jetpack Compose** → Construção da interface do usuário declarativa.
- **Firebase Realtime Database** → Armazenamento e sincronização dos postos cadastrados.
- **Navigation Component** → Gerenciamento de navegação entre as telas.
- **Material Design 3** → Interface moderna e responsiva.
- **Android Studio** → Ambiente de desenvolvimento.

## ⚡ Funcionalidades

✅ **Cadastro de Postos** → Permite adicionar postos com preços de álcool e gasolina.\
✅ **Cálculo do Melhor Combustível** → Baseado na regra de 70% ou 75% do preço da gasolina.\
✅ **Listagem dos Postos** → Exibição de todos os postos cadastrados no Firebase.\
✅ **Exclusão de Postos** → Botão para remover postos da lista e do banco de dados.\
✅ **Abrir Localização no Google Maps** → Abre um posto fixo de Fortaleza - CE.\
✅ **Internacionalização (i18n)** → Suporte para português e inglês.\
✅ **Armazenamento de Estado** → Switch de 70% ou 75% mantido após fechamento do app.

## 📂 Estrutura do Projeto

```
📦 alcoolougasolina
 ┣ 📂 app/src/main/java/com/example/alcoolougasolina
 ┃ ┣ 📂 view
 ┃ ┃ ┣ 📜 FormScreen.kt  → Tela principal do cálculo do melhor combustível.
 ┃ ┃ ┣ 📜 FormPosto.kt   → Tela de cadastro de postos.
 ┃ ┃ ┣ 📜 ListPosto.kt   → Tela de listagem dos postos.
 ┃ ┣ 📜 MainActivity.kt  → Gerenciamento de navegação.
 ┣ 📂 res/values
 ┃ ┣ 📜 strings.xml      → Strings em português.
 ┣ 📂 res/values-en
 ┃ ┣ 📜 strings.xml      → Strings em inglês.
 ┣ 📜 google-services.json → Configuração do Firebase.
```

## 🚀 Como Rodar o Projeto

### 🔧 1. Pré-requisitos

- Android Studio **Flamingo ou superior**
- Conta no Firebase com um projeto criado

### 📥 2. Clonar o repositório

```sh
git clone https://github.com/mykaeull/AlcoolOuGasolina.git
cd alcool-ou-gasolina
```

### 🔥 3. Configurar Firebase

1. No Firebase Console, baixe o `google-services.json` e coloque na pasta `app/`.
2. No `build.gradle.kts (Module: app)`, adicione:
   ```kotlin
   plugins {
       id("com.google.gms.google-services")
   }
   ```
3. Sincronize o projeto no Android Studio.

### ▶️ 4. Rodar o aplicativo

No Android Studio, clique em **Run ▶** para compilar e rodar no emulador ou dispositivo físico.
