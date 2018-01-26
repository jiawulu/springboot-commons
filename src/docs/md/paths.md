
<a name="paths"></a>
## Paths

<a name="failusingget"></a>
### test fail
```
GET /api/hellos/fail
```


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* hello


<a name="successusingget"></a>
### test success
```
GET /api/hellos/success
```


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* hello


<a name="demousingget"></a>
### test success
```
GET /api/swaggers/success
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Query**|**query1**  <br>*required*|query1|integer (int64)|
|**Query**|**query2**  <br>*optional*|query2|integer (int64)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[demo response](#demo-response)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* swagger



