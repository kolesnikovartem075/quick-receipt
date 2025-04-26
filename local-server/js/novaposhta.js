// Переменные для геолокации
let latitude = '';
let longitude = '';

// Переменные для отделения Новой Почты
let selectedDepartmentId = null;
let selectedDepartmentData = null;

// Функция получения геолокации
function getLocation() {
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
}

// Получаем геолокацию при загрузке страницы
document.addEventListener('DOMContentLoaded', getLocation);

// Функция открытия виджета Новой Почты
function openNovaPoshtaWidget() {
    const modalOverlay = document.getElementById('modal-overlay');
    modalOverlay.style.display = 'flex';
    const iframe = document.getElementById('modal-iframe');
    iframe.src = 'https://widget.novapost.com/division/index.html';

    const data = {
        placeName: 'Київ', // Дефолтное значение, можно настроить
        latitude: latitude,
        longitude: longitude,
        domain: window.location.hostname,
        id: selectedDepartmentId
    };

    iframe.onload = () => {
        iframe.contentWindow.postMessage(data, '*');
    };

    window.addEventListener('message', handleNovaPoshtaMessage);
}

// Функция закрытия виджета Новой Почты
function closeNovaPoshtaWidget() {
    const modalOverlay = document.getElementById('modal-overlay');
    modalOverlay.style.display = 'none';
    const iframe = document.getElementById('modal-iframe');
    iframe.src = '';

    window.removeEventListener('message', handleNovaPoshtaMessage);
}

// Обработчик сообщений от виджета Новой Почты
function handleNovaPoshtaMessage(event) {
    if (event.origin !== 'https://widget.novapost.com') {
        console.warn('Повідомлення з невідомого джерела:', event.origin);
        return;
    }

    if (event.data && typeof event.data === 'object') {
        selectedDepartmentData = event.data;
        selectedDepartmentId = event.data.id;

        const selectedPlaceText = event.data.shortName || 'Відділення';
        const cityName = event.data.addressParts?.city || event.data.settlement?.name || '';
        const streetName = event.data.addressParts?.street || '';
        const buildingNumber = event.data.addressParts?.building || '';
        const selectedDescriptionText = `${cityName}, ${streetName ? 'вул. ' + streetName : ''} ${buildingNumber}`.trim();

        const textElement = document.querySelector('.nova-poshta-button .text');
        const textDescriptionElement = document.querySelector('.nova-poshta-button .text-description');

        if (textElement) {
            textElement.textContent = selectedPlaceText;
            textElement.style.marginBottom = '5px';
        }

        if (textDescriptionElement) {
            textDescriptionElement.textContent = selectedDescriptionText;
        }

        // Заполняем скрытые поля для формы
        document.getElementById('city').value = cityName;
        document.getElementById('postOffice').value = `${selectedPlaceText}, ${selectedDescriptionText}`;

        // Скрываем сообщение об ошибке, если оно было
        document.getElementById('postOffice-error').style.display = 'none';

        closeNovaPoshtaWidget();
        return;
    }

    if (event.data === 'closeFrame') {
        closeNovaPoshtaWidget();
        return;
    }

    closeNovaPoshtaWidget();
}