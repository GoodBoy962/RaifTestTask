**Реализация хэш-таблицы**

В задании указано "Хэш-таблица должна позволять переопределять компаратор и хэширующую функцию."
Поэтому я сделал методы hash и keysEquals не приватными и не final^ чтобы их можно бло переопределить.
Если бы было указано, что их можно переопределять динамически, то в полях класса были бы два поля интерфейса Functional, которые бы задавали эти две функции. 

Так как в задании указано "при добавлении элементов к которой в большинстве случаев не происходит динамическое создание новых объектов (не используется оператор new)", то я решил хранить отдельно хранить ключи и значения в двух разных связных списках.
Конечно это приводит к тому, что при поиске значения по ключу сначала находится bucket, а потом в нем за линейное время находится ключ и его индекс в bucket. После этого в соотвествующем бакете для значений приходится итерироваться "индекс" количество раз, что найти нужное.
Перед профилированием я попробовал запустить добавление, получение и удаление элементов в цикле:

```
long start = System.currentTimeMillis();
for (int i = 0; i < 100000; i++) {
	key = "s" + i;
	value = i;
	hashTable.method(...)
}
System.out.println(System.currentTimeMillis() - start);
```

И тоже самое для HashMap из Java Collections.
Результаты отличались на 3 порядка, поэтому я решил добавить loadFactor и делать resize, если текущее количество корзин больше или равно capacity * loadFactor.  
После нового запуска порядки стали одинаковыми, а именно:

|           | put | get | delete |
|-----------|-----|-----|--------|
| HashTable |  91 | 4   | 4      |
| HashMap   | 38  | 4   | 3      |

Компилятор:
```
java 13.0.1 2019-10-15
Java(TM) SE Runtime Environment (build 13.0.1+9)
Java HotSpot(TM) 64-Bit Server VM (build 13.0.1+9, mixed mode, sharing)
```
Mac OS
default JVM settings