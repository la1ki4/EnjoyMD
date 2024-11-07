const buttons = document.querySelectorAll('.lightbulbButton');

// Применяем эффект свечения для каждой кнопки
buttons.forEach(button => {
    button.addEventListener('click', () => {
        button.classList.toggle('glow');
    });
});
