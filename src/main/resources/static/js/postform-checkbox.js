// Добавим возможность выбора категории с эффектом активации
document.querySelectorAll('.category-option').forEach(option => {
    option.addEventListener('click', function () {
        const checkbox = this.querySelector('input[type="checkbox"]');
        checkbox.checked = !checkbox.checked;
        this.classList.toggle('active', checkbox.checked);
    });
});