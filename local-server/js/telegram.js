// Инициализация Telegram Web App
let tg = window.Telegram.WebApp;
tg.expand();
tg.MainButton.hide();

// Получение данных пользователя из Telegram
const user = tg.initDataUnsafe?.user;

// Функция для отправки данных обратно в Telegram бот
function sendDataToTelegram(formData) {
    const telegramId = user?.id || tg.initDataUnsafe?.user?.id;

    tg.sendData(JSON.stringify({
        action: 'register',
        data: formData,
        telegramId
    }));

    // Закрываем Web App
    setTimeout(() => {
        tg.close();
    }, 200);
}

// Заполнение формы данными из Telegram если доступны
document.addEventListener('DOMContentLoaded', () => {
    const firstNameInput = document.getElementById('firstName');
    const lastNameInput = document.getElementById('lastName');

    if (user) {
        firstNameInput.value = user.first_name || '';
        lastNameInput.value = user.last_name || '';
    }
});