// Функции для интеграции с Новой Почтой
let latitude = '';
let longitude = '';

// Получение геолокации пользователя
if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
        (position) => {
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
        },
        (error) => {
            console.error("Помилка отримання геолокації:", error);
        }
    );
} else {
    console.error("Ваш браузер не підтримує геолокацію.");
}

// Получение параметров запроса
function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    const queryParams = {};

    params.forEach((value, key) => {
        queryParams[key] = value;
    });

    return queryParams;
}

// Открытие фрейма с виджетом Новой Почты
function openNPFrame() {
    const modalOverlay = document.getElementById('modal-overlay');
    modalOverlay.style.display = 'flex';

    const iframe = document.getElementById('modal-iframe');
    iframe.src = 'https://widget.novapost.com/division/index.html';

    const queryParams = getQueryParams();
    const domain = window.location.hostname || 'telegram.org';
    const button = document.getElementById('novaPoshta');
    const id = button.dataset.selectedDepartmentId || null;

    // Определяем город из поля ввода или по умолчанию
    const cityInput = document.getElementById('city');
    const cityName = cityInput.value || 'Київ';

    const data = {
        placeName: cityName,
        latitude: latitude,
        longitude: longitude,
        domain: domain,
        id,
        ...queryParams,
    };

    iframe.onload = () => {
        iframe.contentWindow.postMessage(data, '*');
    };

    window.addEventListener('message', handleNPFrameMessage);
}

// Закрытие фрейма
function closeNPFrame() {
    const modalOverlay = document.getElementById('modal-overlay');
    modalOverlay.style.display = 'none';

    const iframe = document.getElementById('modal-iframe');
    iframe.src = '';

    window.removeEventListener('message', handleNPFrameMessage);
}

// Обработка сообщений от фрейма Новой Почты
function handleNPFrameMessage(event) {
    if (event.origin !== 'https://widget.novapost.com') {
        console.warn('Повідомлення з невідомого джерела:', event.origin);
        return;
    }

    if (event.data && typeof event.data === 'object') {
        // Обновляем отображение выбранного отделения
        const selectedPlaceText = event.data.shortName || 'Обрати відділення або поштомат';
        const selectedDescriptionText = `${event.data.addressParts?.city || ''} вул. ${event.data.addressParts?.street || ''}, ${event.data.addressParts?.building || ''}`;

        const textDiv = document.querySelector('.nova-poshta-button .text');
        const textDescription = document.querySelector('.nova-poshta-button .text-description');

        if (textDiv) {
            textDiv.textContent = selectedPlaceText;
            textDiv.style.marginBottom = '5px';
        }

        if (textDescription) {
            textDescription.textContent = selectedDescriptionText;
        }

        // Сохраняем информацию о выбранном отделении
        const button = document.getElementById('novaPoshta');
        button.dataset.selectedDepartmentId = event.data.id;

        // Заполняем скрытые поля для формы
        document.getElementById('city').value = event.data.addressParts?.city || '';
        document.getElementById('novaPost').value =
            `${event.data.shortName} (${event.data.addressParts?.street || ''}, ${event.data.addressParts?.building || ''})`;

        // Сохраняем полные данные о выбранном отделении
        document.getElementById('novaPostData').value = JSON.stringify(event.data);

        // Сбрасываем ошибки, если они были
        document.getElementById('cityError').textContent = '';
        document.getElementById('novaPostError').textContent = '';

        closeNPFrame();
        return;
    }

    if (event.data === 'closeFrame') {
        closeNPFrame();
        return;
    }

    closeNPFrame();
}

// Экспортируем функции для доступа из других файлов
window.openNPFrame = openNPFrame;
window.closeNPFrame = closeNPFrame;