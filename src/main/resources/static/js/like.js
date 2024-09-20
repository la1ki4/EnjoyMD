document.querySelectorAll('.like-form').forEach(form => {
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Предотвращаем стандартное отправление формы

        const postId = this.dataset.postId; // Получаем ID поста
        const actionUrl = this.getAttribute('action'); // Получаем URL для отправки

        fetch(actionUrl, {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
            .then(response => response.json()) // Ожидаем JSON в ответе
            .then(data => {
                // Обновляем количество лайков
                const likeCountSpan = document.getElementById(`like-count-${postId}`);
                likeCountSpan.textContent = data.likeCount; // Обновляем количество лайков
            })
            .catch(error => {
                console.error('Ошибка при отправке лайка:', error);
            });
    });
});