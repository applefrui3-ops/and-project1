### HTTP example requests for browser's console

**Base URL:** `http://localhost:8080`

---

## Authentication

### 1. Login (get JWT token):
```js
fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ username: 'admin', password: 'admin' })
})
  .then(r => r.json())
  .then(data => {
    console.log('Token:', data.token);
    localStorage.setItem('token', data.token);
  });
```

#### Save token for later:
```angular2html
const TOKEN = localStorage.getItem('token');
const AUTH_HEADER = { 'Authorization': `Bearer ${TOKEN}`, 'Content-Type': 'application/json' };
```

---

## Apartments

### 2. Get all apartments:
```angular2html
fetch('http://localhost:8080/api/apartments', { headers: AUTH_HEADER })
  .then(r => r.json())
  .then(data => console.table(data));
```

### 3. Get apartment by ID:
```angular2html
fetch('http://localhost:8080/api/apartments/1', { headers: AUTH_HEADER })
  .then(r => r.json())
  .then(data => console.log(data));
```

### 4. Create apartment:
```angular2html
fetch('http://localhost:8080/api/apartments', {
method: 'POST',
headers: AUTH_HEADER,
body: JSON.stringify({
price: { value: 150, currency: 'BYN' },
reservationStatus: 'FREE'
})
}).then(r => console.log(r.status, r.statusText));
```

### 5. Update apartment:
```angular2html
fetch('http://localhost:8080/api/apartments/1', {
  method: 'PUT',
  headers: AUTH_HEADER,
  body: JSON.stringify({
    price: { value: 200, currency: 'USD' },
    reservationStatus: 'RESERVED'
  })
}).then(r => console.log(r.status, r.statusText));
```

### 6. Delete apartment:
```angular2html
fetch('http://localhost:8080/api/apartments/1', {
  method: 'DELETE',
  headers: AUTH_HEADER
}).then(r => console.log(r.status));
```

---

## Clients

### 7. Get all clients:
```angular2html
fetch('http://localhost:8080/api/clients', { headers: AUTH_HEADER })
  .then(r => r.json())
  .then(data => console.table(data));
```

### 8. Get client by ID:
```angular2html
fetch('http://localhost:8080/api/clients/1', { headers: AUTH_HEADER })
  .then(r => r.json())
  .then(data => console.log(data));
```

### 9. Create client:
```angular2html
fetch('http://localhost:8080/api/clients', {
  method: 'POST',
  headers: AUTH_HEADER,
  body: JSON.stringify({ name: 'user-test' })
}).then(r => console.log(r.status, r.statusText));
```

### 10. Create client and assign to apartment:
```angular2html
fetch('http://localhost:8080/api/clients', {
  method: 'POST',
  headers: AUTH_HEADER,
  body: JSON.stringify({ name: 'usernew', apartment: { id: 4 } })
}).then(r => console.log(r.status, r.statusText));
```

### 11. Update client:
```angular2html
fetch('http://localhost:8080/api/clients/1', {
  method: 'PUT',
  headers: AUTH_HEADER,
  body: JSON.stringify({ name: 'user-updated', apartment: { id: 2 } })
}).then(r => console.log(r.status, r.statusText));
```

### 12. Delete client:
```angular2html
fetch('http://localhost:8080/api/clients/1', {
  method: 'DELETE',
  headers: AUTH_HEADER
}).then(r => console.log(r.status));
```

