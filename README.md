# JavaCore5_project-group-3_telegram-bot
## Как уснатовить локально
### Как скопировать проект локально
2. Создаем через Idea новый проект с помощью VCS. 
3. Ищем проект `avaCore5_project-group-3_telegram-bot`. Нажимаем `Ok`

*Или*
1. Идем в репозиторий по ссылке https://github.com/Vadim-Havrylitse/JavaCore5_project-group-3_telegram-bot.
2. Нажимаем кнопку `Code`
3. Выбираем `Https` (или `SSH`, если вы добавляли в Гитхам свой ключ ССШ).
4. Копируем ссылку.
5. Идем в Idea и выбираем `File > New > Project from Version Control`.
6. Вставляем, ранее скопированную ссылку, и жмем `Clone`

### Установка пакетов Gradle
1. Ищем в проекте через Idea файл `build.gradle`. Открываем его.
2. Ищем строку, где будет написано `dependencies`.
3. С лева от этой строки у вас должен быть зеленый треугольник - https://i.imgur.com/uOW6d7Q.png. Жмем его.

Если треугольника нету - вам нужно установить `Gradle` вручную и запустить с установку пакетов вручную.
1. Устанавливаем Gradle по инструкции - https://docs.gradle.org/current/userguide/installation.html.
2. Открыть терминал в Idea, в папке с проектом - `Alt+F12`.
3. Запустить команду в терминале- `gradle dependencies`. Если пишет, что нету такой команды, то иногда помогает перезагрузка компьютера. Если все еще не помоглоа, значить, что-то упустили в пункте 1.

### Настройка локальных параметров
1. Копируем файл `src/main/resources/application.properties.dist` в `src/main/resources/application.properties`.
2. Открываем файл `src/main/resources/application.properties`.
3. Подсавляем свои настройки Телеграмм Бота.
