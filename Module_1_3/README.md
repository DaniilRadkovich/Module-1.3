# Module 1.3 - Java I/O

Программа представляет собой консольное CRUD-приложение. В ней реализованы следующие сущности:
- Writer (id, firstName, lastName, List<Post> posts);
- Post (id, content, created, updated, List<Label> labels);
- Label (id, name);
- Enum Status (ACTIVE, UNDER_REVIEW, DELETED).

В качестве хранилища данных использованы текстовые файлы:
writers.json, posts.json, labels.json

Пользователь в консоли имеет возможность создания, получения, редактирования и удаления данных.

Слои:
- Model (POJO-классы);
- Repository (классы, реализующие доступ к текстовым файлам);
- Controller (обработка запросов от пользователя);
- View (все данные, необходимые для работы с консолью).

Запуск приложения из класса "Runner".

Условие к данному модулю находится в файле "Theory".