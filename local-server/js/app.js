// Текущий шаг формы
let currentStep = 1;

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registration-form');

    // Обработчики кнопок навигации
    document.getElementById('nextToStep2').addEventListener('click', () => {
        if (validateStep(1)) {
            goToStep(2);
        }
    });

    document.getElementById('backToStep1').addEventListener('click', () => {
        goToStep(1);
    });

    document.getElementById('nextToStep3').addEventListener('click', () => {
        if (validateStep(2)) {
            goToStep(3);
        }
    });

    document.getElementById('backToStep2').addEventListener('click', () => {
        goToStep(2);
    });

    // Обработчик отправки формы
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        // Собираем данные для отправки
        const formData = {
            contactCreateEditDto: {
                firstName: document.getElementById('firstName').value.trim(),
                lastName: document.getElementById('lastName').value.trim(),
                phoneNumber: document.getElementById('phoneNumber').value.trim(),
                city: document.getElementById('city').value.trim(),
                postOffice: document.getElementById('postOffice').value.trim()
            }
        };

        // Отправляем данные в Telegram
        sendDataToTelegram(formData);
    });
});

// Функция для переключения шагов
function goToStep(step) {
    // Скрываем все шаги
    document.querySelectorAll('.step').forEach(el => {
        el.classList.remove('active');
    });

    // Показываем нужный шаг
    document.getElementById(`step${step}`).classList.add('active');

    // Обновляем прогресс-бар
    document.querySelectorAll('.progress-step').forEach((el, index) => {
        if (index < step) {
            el.classList.add('active');
        } else {
            el.classList.remove('active');
        }
    });

    // Обновляем текущий шаг
    currentStep = step;

    // Если это последний шаг, заполняем сводку
    if (step === 3) {
        updateSummary();
    }
}