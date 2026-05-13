### HTTP example requests for browser's console:

1. #### Create/update apartment:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/apartments/', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ id: 6, price: { value: 150, currency: 'BYN' }, reservationStatus: 'RESERVED' })
}).then(r => console.log(r.status, r.statusText));
```

2. #### Get apartments:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/apartments/')
    .then(r => r.json())
    .then(data => console.table(data));
```

3. #### Delete apartment:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/apartments/1', {
    method: 'DELETE'
}).then(r => console.log(r.status));
```

4. #### Create client:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/clients/', {
method: 'POST',
headers: { 'Content-Type': 'application/json' },
body: JSON.stringify({ name: 'user-test' })
}).then(r => console.log(r.status, r.statusText));
```

5. #### Create client and add to apartment:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/clients/', {
method: 'POST',
headers: { 'Content-Type': 'application/json' },
body: JSON.stringify({ name: 'user3', apartment: {id: 4} })
}).then(r => console.log(r.status, r.statusText));
```

6. #### Update client:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/clients/', {
method: 'POST',
headers: { 'Content-Type': 'application/json' },
body: JSON.stringify({ id: 1, name: 'user-test1' })
}).then(r => console.log(r.status, r.statusText));
```

7. #### Get clients:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/clients/')
    .then(r => r.json())
    .then(data => console.table(data));
```

8. #### Delete client:
```angular2html
fetch('http://localhost:8080/and_project1_war_exploded/api/clients/1', {
    method: 'DELETE'
}).then(r => console.log(r.status));
```