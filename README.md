## Пропозиції оптимізації швидкості

1. Прибрати garbage collector
2. Прибрати очищення масиву слів
3. StringBuilder для очищення тексту - швидше ніж regex
4. Ручна обробка кожного символу в loadAndCleanText()

До оптимізації - Execution time: 130 ms
Після оптимізації - Execution time: 91 ms