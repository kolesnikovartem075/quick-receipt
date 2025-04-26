import argparse
import os
import ssl
import sys
from http.server import HTTPServer, SimpleHTTPRequestHandler


class TelegramWebAppHandler(SimpleHTTPRequestHandler):
    def end_headers(self):
        # Добавляем необходимые заголовки для Telegram Web App
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
        super().end_headers()


def run_server(use_https=False, port=8000, cert_file=None, key_file=None, directory=None):
    handler = TelegramWebAppHandler

    # Устанавливаем директорию для обслуживания файлов
    if directory:
        if sys.version_info >= (3, 7):  # Python 3.7+
            handler.directory = directory
        else:
            os.chdir(directory)

    server_address = ('', port)
    httpd = HTTPServer(server_address, handler)

    if use_https:
        if not cert_file or not key_file:
            # Если сертификаты не указаны, создаем самоподписанные
            if not os.path.exists('cert.pem') or not os.path.exists('key.pem'):
                print("Создание самоподписанных сертификатов...")
                os.system(
                    'openssl req -x509 -newkey rsa:4096 -nodes -out cert.pem -keyout key.pem -days 365 -subj "/CN=localhost"')
            cert_file = 'cert.pem'
            key_file = 'key.pem'

        httpd.socket = ssl.wrap_socket(httpd.socket,
                                       keyfile=key_file,
                                       certfile=cert_file,
                                       server_side=True)
        protocol = "https"
    else:
        protocol = "http"

    print(f"Сервер запущен на {protocol}://localhost:{port}")
    print("Для остановки нажмите Ctrl+C")

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        print("\nСервер остановлен.")
        httpd.server_close()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Запуск локального сервера для Telegram Web App')
    parser.add_argument('--https', action='store_true', help='Использовать HTTPS вместо HTTP')
    parser.add_argument('--port', type=int, default=8000, help='Порт сервера (по умолчанию 8000)')
    parser.add_argument('--cert', help='Путь к SSL сертификату для HTTPS')
    parser.add_argument('--key', help='Путь к приватному ключу для HTTPS')
    parser.add_argument('--dir', help='Директория с файлами для обслуживания')

    args = parser.parse_args()

    run_server(use_https=args.https, port=args.port, cert_file=args.cert, key_file=args.key, directory=args.dir)
