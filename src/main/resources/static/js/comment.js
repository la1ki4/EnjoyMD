document.querySelectorAll('.comment-input').forEach(form => {
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Предотвращаем стандартное поведение формы

        const postId = form.querySelector('input[name="postId"]').value; // Получаем ID поста
        const content = form.querySelector('input[name="content"]').value.trim(); // Получаем содержание комментария

        // Проверяем, что поле для комментария не пустое
        if (!content) {
            alert('Комментарий не может быть пустым!'); // Уведомляем пользователя
            return; // Выходим из функции, если комментарий пустой
        }

        const actionUrl = form.getAttribute('action'); // Получаем URL для отправки

        fetch(actionUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: new URLSearchParams({
                postId: postId,
                content: content
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json(); // Теперь ожидаем JSON
            })
            .then(data => {
                const commentsContainer = form.closest('.post-block').querySelector('.comments');
                const newComment = `<div><p class="comment">${data.authorUsername}: ${data.content}</p>
                        <small class="comment-date">${data.createdAtFormatted}</small></div>`;
                commentsContainer.insertAdjacentHTML('beforeend', newComment);
                form.querySelector('input[name="content"]').value = ''; // Очищаем поле ввода

                // Обновляем количество комментариев
                const commentCountSpan = document.getElementById(`comment-count-${postId}`);
                commentCountSpan.textContent = data.commentCount;
            })
            .catch(error => console.error('Ошибка при добавлении комментария:', error));
    });
})
