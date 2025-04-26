// Инициализация Telegram WebApp
document.addEventListener('DOMContentLoaded', function () {
    // Инициализация Telegram WebApp
    if (window.Telegram && window.Telegram.WebApp) {
        const webApp = window.Telegram.WebApp;

        // Устанавливаем настройки WebApp
        webApp.ready();
        webApp.expand();

        // Адаптируем темы
        applyTelegramTheme(webApp);

        // Обработчик кнопки отправки формы
        const submitBtn = document.getElementById('submitRegistrationBtn');
        if (submitBtn) {
            submitBtn.addEventListener('click', function () {
                submitRegistration();
            });
        }

        // Если есть главная кнопка в WebApp
        if (webApp.MainButton) {
            webApp.MainButton.setText('Зареєструватися');
            webApp.MainButton.onClick(submitRegistration);
            webApp.MainButton.show();
        }
    } else {
        // Если приложение открыто не в Telegram
        document.body.innerHTML = '<div class="container"><div class="card"><div class="card-content"><h2>Помилка</h2><p>Це додаток працює тільки в Telegram.</p></div></div></div>';
    }
});

// Применяем тему Telegram к нашему приложению
function applyTelegramTheme(webApp) {
    if (!webApp || !webApp.themeParams) return;

    // Получаем переменные темы
    const root = document.documentElement;
    const themeParams = webApp.themeParams;

    // Устанавливаем CSS переменные
    root.style.setProperty('--tg-theme-bg-color', themeParams.bg_color);
    root.style.setProperty('--tg-theme-text-color', themeParams.text_color);
    root.style.setProperty('--tg-theme-hint-color', themeParams.hint_color);
    root.style.setProperty('--tg-theme-link-color', themeParams.link_color);
    root.style.setProperty('--tg-theme-button-color', themeParams.button_color);
    root.style.setProperty('--tg-theme-button-text-color', themeParams.button_text_color);
    root.style.setProperty('--tg-theme-secondary-bg-color', themeParams.secondary_bg_color);
}

// Функция отправки формы регистрации
function submitRegistration() {
    // Валидация формы
    if (!validateRegistrationForm()) {
        return;
    }

    // Получаем данные формы
    const formData = {
        firstName: document.getElementById('firstName').value.trim(),
        lastName: document.getElementById('lastName').value.trim(),
        city: document.getElementById('city').value.trim(),
        novaPost: document.getElementById('novaPost').value.trim(),
        phoneNumber: document.getElementById('phoneNumber').value.trim()
    };

    // Получаем Telegram ID пользователя
    const webApp = window.Telegram.WebApp;
    const telegramId = webApp.initDataUnsafe.user.id;

    // Получаем данные о выбранном отделении
    const novaPostDataStr = document.getElementById('novaPostData').value;
    let novaPostData = {};

    try {
        if (novaPostDataStr) {
            novaPostData = JSON.parse(novaPostDataStr);
        }
    } catch (e) {
        console.error('Ошибка при парсинге данных отделения:', e);
    }

    // Подготавливаем данные для API
    const contactData = {
        contactCreateEditDto: {
            firstName: formData.firstName,
            lastName: formData.lastName,
            phoneNumber: formData.phoneNumber,
            city: formData.city,
            postOffice: formData.novaPost,
            warehouseRef: novaPostData.ref || '',
            warehouseAddress: novaPostData.address || ''
        }
    };

    // Передаем данные обратно в Telegram Bot
    webApp.sendData(JSON.stringify({
        action: 'register',
        telegramId: telegramId,
        contactData: contactData
    }));

    // Показываем сообщение об успехе и закрываем WebApp
    webApp.showPopup({
        title: 'Успіх!',
        message: 'Ваші дані успішно відправлені. Дякуємо за реєстрацію!',
        buttons: [{text: 'OK'}]
    });

    // Закрываем WebApp через 2 секунды
    setTimeout(() => {
        webApp.close();
    }, 2000);
}

// Функция валидации формы
function validateRegistrationForm() {
    let isValid = true;

    // Валидация имени
    const firstName = document.getElementById('firstName');
    const firstNameError = document.getElementById('firstNameError');
    if (!firstName.value.trim()) {
        firstNameError.textContent = "Будь ласка, введіть ім'я";
        isValid = false;
    } else {
        firstNameError.textContent = '';
    }

    // Валидация фамилии
    const lastName = document.getElementById('lastName');
    const lastNameError = document.getElementById('lastNameError');
    if (!lastName.value.trim()) {
        lastNameError.textContent = 'Будь ласка, введіть прізвище';
        isValid = false;
    } else {
        lastNameError.textContent = '';
    }

    // Валидация города и отделения Новой Почты
    const city = document.getElementById('city');
    const cityError = document.getElementById('cityError');
    if (!city.value.trim()) {
        cityError.textContent = 'Будь ласка, оберіть відділення Нової Пошти';
        isValid = false;
    } else {
        cityError.textContent = '';
    }

    // Проверяем, выбрано ли отделение
    const novaPost = document.getElementById('novaPost');
    const novaPostError = document.getElementById('novaPostError');
    if (!novaPost.value.trim()) {
        novaPostError.textContent = 'Будь ласка, оберіть відділення';
        isValid = false;
    } else {
        novaPostError.textContent = '';
    }

    // Валидация телефона
    const phoneNumber = document.getElementById('phoneNumber');
    const phoneNumberError = document.getElementById('phoneNumberError');
    const phoneRegex = /^\+380\d{9}$/;
    if (!phoneNumber.value.trim()) {
        phoneNumberError.textContent = 'Будь ласка, введіть номер телефону';
        isValid = false;
    } else if (!phoneRegex.test(phoneNumber.value.trim())) {
        phoneNumberError.textContent = 'Номер телефону повинен бути у форматі +380xxxxxxxxx';
        isValid = false;
    } else {
        phoneNumberError.textContent = '';
    }

    return isValid;
}