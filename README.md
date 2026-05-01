# Stock Market Service

A simplified stock market simulation built with **Java 21 + Spring Boot**,
database in **PostgreSQL**, load-balanced by **nginx**, and orchestrated with **Docker Compose**. Two application instances run simultaneously — killing one does not take down the service.


## Requirements

- Docker

## Starting the service

### Linux / macOS

```bash
./start.sh <PORT>
```

Example — start on port 8080:

```bash
./start.sh 8080
```

### Windows

```bat
start.bat <PORT>
```

Example:

```bat
start.bat 8080
```

If no port is given, defaults to **8080**.

The script runs `docker compose up --build -d` and the service becomes available at `http://localhost:<PORT>`.

## Architecture

```
Client
  │
  ▼
nginx (gateway)          ← single entry point, port <PORT>
  ├── app1 (Spring Boot, port 8080 internal)
  └── app2 (Spring Boot, port 8080 internal)
        │
        ▼
  PostgreSQL (db)
```

- nginx round-robins requests between `app1` and `app2`
- Both instances share the same PostgreSQL database
- If one instance crashes (e.g. via `/chaos`), nginx routes all traffic to the surviving instance
- Crashed instances restart automatically (`restart: always`)

## API Documentation

All endpoints are available at `http://localhost:<PORT>`.

### `POST /wallets/{wallet_id}/stocks/{stock_name}`

Buy or sell a single unit of a stock.

**Body:**
```json
{ "type": "buy" }
```
or
```json
{ "type": "sell" }
```

| Condition | Response |
|---|---|
| Success | `200 OK` |
| Stock does not exist in the bank | `404 Not Found` |
| Buy with 0 stock in bank | `400 Bad Request` |
| Sell with 0 stock in wallet | `400 Bad Request` |

- Wallet is created automatically on first operation.
- Each operation moves exactly 1 unit between bank and wallet.
- Only successful operations are recorded in the audit log.


### `GET /wallets/{wallet_id}`

Returns the current state of a wallet.

**Response:**
```json
{
  "id": "user1",
  "stocks": [
    { "name": "AAPL", "quantity": 3 },
    { "name": "TSLA", "quantity": 1 }
  ]
}
```

### `GET /wallets/{wallet_id}/stocks/{stock_name}`

Returns the quantity of a single stock in a wallet.

**Response:**
```
3
```

Returns `0` if the wallet or stock has no holdings.

### `GET /stocks`

Returns the current state of the bank.

**Response:**
```json
{
  "stocks": [
    { "name": "AAPL", "quantity": 97 },
    { "name": "TSLA", "quantity": 10 }
  ]
}
```

### `POST /stocks`

Sets the state of the bank (replaces existing state).

**Body:**
```json
{
  "stocks": [
    { "name": "AAPL", "quantity": 100 },
    { "name": "TSLA", "quantity": 50 }
  ]
}
```

**Response:** `200 OK`

> The bank starts empty. Use this endpoint to add stocks before any trades.


### `GET /log`

Returns the full audit log in chronological order. Only successful wallet operations are logged — bank `POST /stocks` calls are excluded.

**Response:**
```json
{
  "log": [
    { "type": "buy",  "wallet_id": "user1", "stock_name": "AAPL" },
    { "type": "sell", "wallet_id": "user1", "stock_name": "AAPL" }
  ]
}
```

### `POST /chaos`

Kills the instance that served the request. The other instance continues to handle traffic. The killed instance restarts automatically.

```bash
curl -X POST http://localhost:8080/chaos
```



