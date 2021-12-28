# Trader
Install maven.

Execute mvn clean install.

Install MySql

Here there will be a user model which will have user's detail as well as equities(one to many relation) each user has.

Equity table will contain information about current available stock with given id and how many quantity of equity is available with amount of equity per single quantity.

UserStock table will contain information about user and equity that user own with quantity and amount(one to one relation).

Fund table refer to fund which user owns.

######
User can buy equity (POST /user/{userId}/buyEquity) if:

1. User have enough funds.
2. Request qunatity of equity is available (i.e user can only buy equity if qty is >= requested qty).
3. Time check for 9 to 5

On buying equity, user fund value will be decreased and there will be new entry in UserStock for given user and purchased equity. 

Also, Equity quantity will be decreased in equity table for purchased equity.

User can sell equity (POST /user/{userId}/sellEquity):

If user holds that equity i.e there in entry in UserStock for given equity and user.

On selling, amount will be added to funds, as well as equity quantity will be increased in equity table.

Time check for 9 to 5

User can add funds anytime => POST /user/{userId}/updateFund

User can also check available funds anytime => GET /user/{userId}/checkFund


#####
There are different layers for logic for test cases also:

• Resource/Presentation/Controller Layer
• Service/Business/Handler Layer
• Persistence Layer 
Persistence layer works with in-memory db as well as MySql (Please refer application.properties where sql portion can be un-commented to execute)


Both unit as well as integration tests cases are added.


/ebroker/target/coverage-reports/jacoco-ut.exec will be generated to check code coverage.
