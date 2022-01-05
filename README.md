# merchant-service


API Documentation

## Header for all API calls
Http Headers required for all API calls \
Key : Authorization \
Value : Bearer $accessToken
tenantId:client1
siteId:2

## API : Create Merchant \
URL : api/tenants/merchants/2/1 \
Http Method : POST \
Request Body : { "merchantId": 115, "siteId": 1, "tenantId": 2, "externalMerchantId": 5, "merchantName": "Cosco121", "merchantRank":38, "merchantDescription": "Cosco", "merchantLogo1": "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAp1JREFUeNqEU21IU1EYfu7unW5Ty6aBszYs6MeUjGVYokHYyH5E1B9rZWFEFPQnAwmy6Hc/oqhfJsRKSSZGH1JIIX3MNCsqLTD9o1Oj6ebnnDfvvefezrnbdCHhCw/n433P8z7nPe/hBEEAtX0U7hc164uwuvVSXKwZLoOmaRDim+7m9vZa0WiEKSUFFpNpCWlmMyypqTDRuYn6t3k8vmQ2gRDCxs0t9fW45F52aBTROJLtZl7nEZad2m+KtoQCQ0FBARyOCGRZ/q92I1WgqqXlfdd95VsrK8/pChIEqqpCkiQsiCII0aBQZZoWl8lzFDwsFjMl0DBLY8Lj41hBwK4jSQrWOIphL6xYyhwJDWGo6wFSaH1Y3PTCAsITE1oyAa8flhWkbSiCLX8vun11eiGIpiJ/z2nYdx5HqLdVV7elrOzsuqysL3rmBIGiKPizKCHHWY4PLVeQbnXAdegqdhy+hu8dDTBnbqQJZJ1A7u+vz7RaiymWCZgCRSF6Edk8b9cx+B/W6WuVxPaZnyiqXoPpyUmVYvkKTIFClHigEieKjYuSvETUllaF4GAUM1NT6ooaJDKx+aDfC9fByxj90REb+9ppmIoAscH/6leg8MS9DJXPAM9xHCM443K57C6biMjcHDaVVCHw9RmCA2/RGC5C00AqXk/m4p20HZK4CM/J3Zk9n0ecMBhDQnJHcrTisyMfdQXOilrdMfxcwoHq/fg5R59TiQV3hYGKo6X2J/c7LyQIjOx9GXhOw/zoJ8wEevRGyp53o/lGMNYsBgPtEwLecwov7/jGDKa1twT6o3KpL4MdZgGsWZLtfPr7f1q58k1JNHy7YYaM+J+K3Y2PmAIbRavX66229hrGVvvL5uzsHDEUvUu+NT1my78CDAAMK1a8/QaZCgAAAABJRU5ErkJggg", "merchantImage1": "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAp1JREFUeNqEU21IU1EYfu7unW5Ty6aBszYs6MeUjGVYokHYyH5E1B9rZWFEFPQnAwmy6Hc/oqhfJsRKSSZGH1JIIX3MNCsqLTD9o1Oj6ebnnDfvvefezrnbdCHhCw/n433P8z7nPe/hBEEAtX0U7hc164uwuvVSXKwZLoOmaRDim+7m9vZa0WiEKSUFFpNpCWlmMyypqTDRuYn6t3k8vmQ2gRDCxs0t9fW45F52aBTROJLtZl7nEZad2m+KtoQCQ0FBARyOCGRZ/q92I1WgqqXlfdd95VsrK8/pChIEqqpCkiQsiCII0aBQZZoWl8lzFDwsFjMl0DBLY8Lj41hBwK4jSQrWOIphL6xYyhwJDWGo6wFSaH1Y3PTCAsITE1oyAa8flhWkbSiCLX8vun11eiGIpiJ/z2nYdx5HqLdVV7elrOzsuqysL3rmBIGiKPizKCHHWY4PLVeQbnXAdegqdhy+hu8dDTBnbqQJZJ1A7u+vz7RaiymWCZgCRSF6Edk8b9cx+B/W6WuVxPaZnyiqXoPpyUmVYvkKTIFClHigEieKjYuSvETUllaF4GAUM1NT6ooaJDKx+aDfC9fByxj90REb+9ppmIoAscH/6leg8MS9DJXPAM9xHCM443K57C6biMjcHDaVVCHw9RmCA2/RGC5C00AqXk/m4p20HZK4CM/J3Zk9n0ecMBhDQnJHcrTisyMfdQXOilrdMfxcwoHq/fg5R59TiQV3hYGKo6X2J/c7LyQIjOx9GXhOw/zoJ8wEevRGyp53o/lGMNYsBgPtEwLecwov7/jGDKa1twT6o3KpL4MdZgGsWZLtfPr7f1q58k1JNHy7YYaM+J+K3Y2PmAIbRavX66229hrGVvvL5uzsHDEUvUu+NT1my78CDAAMK1a8/QaZCgAAAABJRU5ErkJggg","merchantUrl": "http://www.cafecoffeeday.com", "merchantGoogleMapUrl": "https://www.google.com/maps/dir/18.5540118,73.9937775/Cafe+Coffee+Day,+2nd+floor,+East+block+Amanora+Park+Town,+Hadapsar+Amanora+Town+Centre,+Mundhwa+-+Kharadi+Rd,+Pune,+Maharashtra+411028/@18.5400198,73.925535,13z/data=!3m2!4b1!5s0x3bc2c18a3ca7b099:0x9cc40d055985ac77!4m9!4m8!1m1!4e1!1m5!1m1!1s0x3bc2c18a1801e745:0x7196a5e78b2f38a7!2m2!1d73.934272!2d18.5188749", "supplierId": 1, "supplierName": "TEMP company", "provider": "manual", "externalMerchantCategory": "Sample ext category", "category": "FOOD", "subCategory": "Coffee", "status": "Active", "createdDate": "2021-12-08 08:07:17.16864", "modifiedDate": "2021-12-08 08:07:17.16864", "modifiedBy": "admin", "externalMaxRebate": 5, "minRebate": 2, "customerTouchPoint": "N", "customerRebatePercentage": 3, "customerMaxRebate": 5 } \
Response : { "success": true, "status": 201, "message": "Merchant Created" } 

## API : Update Merchant \
URL : /api/tenants/merchants/2/1 \
Http Method : PUT \
Request Body : { "merchantId": 115, "siteId": 1, "tenantId": 2, "externalMerchantId": 5, "merchantName": "Cosco1", "merchantRank":38, "merchantDescription": "Test1213", "merchantUrl": "http://www.cafecoffeeday.com", "merchantGoogleMapUrl": "https://www.google.com/maps/dir/18.5540118,73.9937775/Cafe+Coffee+Day,+2nd+floor,+East+block+Amanora+Park+Town,+Hadapsar+Amanora+Town+Centre,+Mundhwa+-+Kharadi+Rd,+Pune,+Maharashtra+411028/@18.5400198,73.925535,13z/data=!3m2!4b1!5s0x3bc2c18a3ca7b099:0x9cc40d055985ac77!4m9!4m8!1m1!4e1!1m5!1m1!1s0x3bc2c18a1801e745:0x7196a5e78b2f38a7!2m2!1d73.934272!2d18.5188749", "supplierId": 1, "supplierName": "TEMP company", "provider": "manual", "externalMerchantCategory": "Sample ext category", "category": "FOOD", "subCategory": "Coffee", "status": "Active", "createdDate": "2021-12-08 08:07:17.16864", "modifiedDate": "2021-12-08 08:07:17.16864", "modifiedBy": "admin", "externalMaxRebate": 5, "minRebate": 2, "customerTouchPoint": "N", "customerRebatePercentage": 3, "customerMaxRebate": 5 } \
Response : { "success": true, "status": 200, "message": "Merchant Updated" }

## API : Update Merchant status
URL : /api/tenants/carousels/byTemplate \
Http Method : PATCH \
Request Body : { "merchantId": 114, "siteId": 1, "tenantId": 2, "externalMerchantId": 5, "merchantName": "Cosco1", "status":"Approve"} \
Response : { "success": true, "status": 200, "message": "Merchant status updated" }