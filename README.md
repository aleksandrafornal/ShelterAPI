# Shelter API

### 
Shelter API is a specialized application designed to streamline shelter management processes.

This application serves as a comprehensive tool for shelter operators. Its primary functions include the addition of 
animals and adopters to the shelter database, where validation checks are conducted to ensure data accuracy. Once 
checked and approved, the information is stored within the repository.

Moreover, Shelter API enables training of potential adopters and supports adoption process. All adoption details are 
preserved within the repository for future reference.

Furthermore, the system offers data retrieval capabilities, enabling users to access information on individual pets, 
adopters, and historical adoption transactions, which ensure a comprehensive overview of shelter activities.

#### DOCS

POST /pets

Add pet to shelter

Request
```
{
    "name": string
    "species": string
    "age": int
    "size": string {small, medium, large}
}
```
Response

SUCCESS
```
201 Created
{
    "petId": UUID
}
```
ERROR
```
400 Bad Request (Validation went wrong, bad data)
{
    "validatorList": [ValidatorError]
}
```

POST /adopters

Add adopters to shelter

Request
```
{
    "name": string
    "surname": string
    "age": int
    "fulfilledRequirements": [string] {HOUSE_WITH_GARDEN, GOOD_SALARY, IS_TRAINED, NETTED_BALCONY}
}
```
Response

SUCCESS
```
201 Created
{
    "id": UUID
}
```
ERROR
```
400 Bad Request (Validation went wrong, bad data)
{
    "validatorList": [ValidationError]
}
```

GET /adopters/{id}

Get adopter from repository by id

Response

SUCCESS
```
200 OK
{
    "id": UUID
    "name": string
    "surname": string
    "age": int
    "fulfilledRequirements": [string] {HOUSE_WITH_GARDEN, GOOD_SALARY, IS_TRAINED, NETTED_BALCONY}
}
```
ERROR
```
404 Not Found (Adopter not found)
```

GET /adopters

Get all adopters from repository

Response

SUCCESS
```
{
    "adopters": [Adopter]
}
```

PUT /pets/{petId}/adoption/{adopterId}

Adopt pet from shelter

Response

SUCCESS
```
201 Created
{
    "adopter": 
    {
        "id": UUID,
        "name": string,
        "surname": string,
        "age": int,
        "fulfilledRequirements": [string] {HOUSE_WITH_GARDEN, GOOD_SALARY, IS_TRAINED, NETTED_BALCONY}
    },
    "pet": {
        "name": string,
        "species": string,
        "age": int,
        "size": string {small, medium, large}
        "petId": UUID,
        "status": string {AVAILABLE, NOT_AVAILABLE}
    },
    "dateOfAdoption": instant,
    "adoptionId": UUID
}
```
ERROR
```
404 Not Found (Adopter or pet not found)
```

GET /pets/{id}

Get pet from repository by id

Response

SUCCESS
```
200 OK
{
    "name": string,
    "species": string,
    "age": int,
    "size": string {small, medium, large}
    "petId": UUID,
    "status": string {AVAILABLE, NOT_AVAILABLE}
}
```
ERROR
```
404 Not Found (Pet not found)
```

GET /pets

Get all pets from repository

Response

SUCCESS
```
{
    "pets": [
        {
            "name": string,
            "species": string,
            "age": int,
            "size": string {small, medium, large}
            "petId": UUID,
            "status": string {AVAILABLE, NOT_AVAILABLE}
        }
    ]
}
```

GET /adoption-data/{id}
Get adoption data from repository by id

Response

SUCCESS
```
200 OK
{
    "adopter": 
    {
        "id": UUID,
        "name": string,
        "surname": string,
        "age": int,
        "fulfilledRequirements": [string] {HOUSE_WITH_GARDEN, GOOD_SALARY, IS_TRAINED, NETTED_BALCONY}
    },
    "pet": {
        "name": string,
        "species": string,
        "age": int,
        "size": string {small, medium, large}
        "petId": UUID,
        "status": string {AVAILABLE, NOT_AVAILABLE}
    },
    "dateOfAdoption": instant,
    "adoptionId": UUID
}
```
ERROR
```
404 Not Found (Adoption data not found)
```

GET /adoption-data

Get all adoption data from repository

Response

SUCCESS
```
{
    adoptionData: [AdoptionData]
}
```

PATCH /adopters/{id}

Train adopter

Response

SUCCESS
```
200 OK
```
ERROR
```
404 Not Found (Adopter not found)
```
