let currentPage = 0;
const pageSize = 3;
let isLoading = false;

function loadPosts() {

    if (isLoading) return;

    isLoading = true;
    fetch(`/posts/load?page=${currentPage}&size=${pageSize}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки постов');
            }
            return response.json();
        })
        .then(posts => {
            console.log(posts); // Проверьте, что данные приходят правильно
            if (posts.length === 0) {
                window.removeEventListener('scroll', handleScroll);
                return;
            }
            const postsContainer = document.getElementById('posts-container');
            posts.forEach(post => {
                const postElement = createPostElement(post);
                postsContainer.appendChild(postElement);
            });
            currentPage++;
            isLoading = false;
        })
        .catch(error => {
            console.error('Ошибка загрузки постов:', error);
            isLoading = false;
        });
}


function handleScroll() {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) { // Загрузка, когда почти достигли низа страницы
        loadPosts(); // Загружаем посты
    }
}

function createPostElement(post) {
    const div = document.createElement('div');
    div.className = 'post-block';

    // Создаем HTML для категорий
    const categoriesHtml = post.categories && post.categories.length > 0
        ? post.categories.map(category => `<span class="post-category">${category.name}</span>`).join(', ')
        : 'No categories';

    div.innerHTML = `
    <div class="post-wrapper">
        <div class="post-header">
            <div class="post-owner">
                <div class="post-avatar">
                    <img src="img/account-avatar-profile-user-6.svg" alt="">
                </div>
                <div class="post-username">${post.authorUsername || 'Unknown'}</div>
            </div>
            <div class="post-place">${post.location || 'Unknown location'}</div>
        </div>
        <div class="post-categories">
            <div class="categoryy">
                 ${categoriesHtml}
            </div>
        </div>
        <div class="post-image">
            <img src="${post.fileName}" alt="">
        </div>
        <div class="post-description">${post.description || 'No description'}</div>
        <div class="post-feedback">
            <div class="post-activity">
                <div class="activity-icon">
                    <form class="like-form" data-post-id="${post.id}">
                        <button type="button" class="like-button">
                            <img src="img/heart.svg" alt="" class="like">
                        </button>
                    </form>
                    <span class="count" id="like-count-${post.id}">${post.likeCount}</span>
                </div>
                <div class="activity-icon">
                    <img src="img/comment-1.svg" alt="" class="comment">
                    <div class="count" id="comment-count-${post.id}">${post.comments.length}</div>
                </div>
            </div>
        </div>
        <div class="post-add-comment-block">
            <div class="avatar-post">
                <img src="img/account-avatar-profile-user-6.svg" alt="">
            </div>
            <form action="/comments/add" method="post" class="comment-input" data-post-id="${post.id}">
                <input type="hidden" name="postId" value="${post.id}">
                <input type="text" name="content" class="post-add-comment" placeholder="Write a comment" required>
            </form>
        </div>
        <div class="comments">
            <h3>Comments</h3>
            <div class="comments-list" id="comments-list-${post.id}">
                ${post.comments ? post.comments.map(comment => `
                    <p class="comment">${comment.authorUsername}: ${comment.content}</p>
                    <small class="comment-date">${comment.createdAtFormatted}</small>
                `).join('') : 'No comments available.'}
            </div>
        </div>
    </div>
`;

    const likeForm = div.querySelector('.like-form');
    likeForm.addEventListener('click', event => {
        event.preventDefault();
        const postId = likeForm.getAttribute('data-post-id');
        toggleLike(postId);
    });

    // Обработчик для формы комментариев
    const commentForm = div.querySelector('.comment-input');
    commentForm.addEventListener('submit', event => {
        event.preventDefault();
        const postId = commentForm.getAttribute('data-post-id');
        const formData = new FormData(commentForm);
        addComment(postId, formData);
    });

    return div;
}


function toggleLike(postId) {
    fetch(`/main/toggleLike/${postId}`, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при переключении лайка');
            }
            return response.json();
        })
        .then(data => {
            // Обновляем счетчик лайков на странице
            const likeCountElement = document.getElementById(`like-count-${postId}`);
            likeCountElement.textContent = data.likesCount;
        })
        .catch(error => console.error('Ошибка при переключении лайка:', error));
}

function addComment(postId, formData) {
    fetch(`/comments/add`, {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при добавлении комментария');
            }
            return response.json();
        })
        .then(comment => {
            // Обновляем список комментариев
            const commentsList = document.getElementById(`comments-list-${postId}`);

            // Создаём новый элемент для комментария
            const newComment = document.createElement('div');
            newComment.classList.add('comment-block'); // новый класс для контейнера комментария
            newComment.innerHTML = `
            <p class="comment">${comment.authorUsername}: ${comment.content}</p>
            <small class="comment-date">${comment.createdAtFormatted || formatDate(comment.createdAt)}</small>
        `;
            commentsList.appendChild(newComment);

            // Обновляем счётчик комментариев
            const commentCountElement = document.getElementById(`comment-count-${postId}`);
            commentCountElement.textContent = parseInt(commentCountElement.textContent) + 1;

            // Очищаем поле ввода комментария
            const commentInput = document.querySelector(`.comment-input[data-post-id="${postId}"] .post-add-comment`);
            commentInput.value = '';
        })
        .catch(error => console.error('Ошибка при добавлении комментария:', error));
}

// Функция для локального форматирования даты
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString("ru-RU", {
        day: "numeric",
        month: "long",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit"
    });
}

// Изначально загружаем посты и добавляем обработчик скролла
window.addEventListener('scroll', handleScroll);
loadPosts();
