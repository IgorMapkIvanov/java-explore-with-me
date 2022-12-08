# java-explore-with-me

## ExploreWithMe project.
### [Pull Request](https://github.com/IgorMapkIvanov/java-explore-with-me/pull/1 "more info")

# **Идея**

Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить. Сложнее всего в таком 
планировании поиск информации и переговоры. <br>Какие намечаются мероприятия, свободны ли в этот момент друзья, как всех 
пригласить и где собраться. Приложение, которое вы будете создавать, — афиша, где можно предложить какое-либо событие от 
выставки до похода в кино и набрать компанию для участия в нём.

Два сервиса 
- Основной сервис — содержит всё необходимое для работы продукта.
  API основного сервиса разделите на три части. Первая — публичная, доступна без регистрации любому пользователю сети.
  Вторая — закрытая, доступна только авторизованным пользователям. Третья — административная, для администраторов сервиса.
  К каждой из частей свои требования.
- Сервис статистики — хранит количество просмотров и позволяет делать различные выборки для анализа работы приложения.
  Второй сервис, статистики, призван собирать информацию. Во-первых, о количестве обращений пользователей к спискам событий
  и, во-вторых, о количестве запросов к подробной информации о событии. На основе этой информации должна формироваться
  статистика о работе приложения.

Дополнительная функциональность - комментарии