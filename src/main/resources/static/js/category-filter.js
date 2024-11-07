function loadPostsByCategories(categories) {
    const queryParams = categories.map(category => `category=${encodeURIComponent(category)}`).join('&');
    fetch(`/posts/categories?${queryParams}`)
        .then(response => response.json())
        .then(posts => {
            // Логика для отображения постов
            console.log(posts);
        })
        .catch(error => console.error('Ошибка при загрузке постов по категориям:', error));
}