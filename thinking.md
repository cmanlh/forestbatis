#### process procedure
* prepare sql statement
  * prepare parameter
    * query parameter
    * sql generate parameter #1
  * generate sql
    * new sql
      * select
        * select stream
          * select with order
            * select all
              * select from single table
                * select with full column
                * select with picked column
              * select from multiple table
                * inner join
                * left join
                * right join
                * where exists
                * where not exists
            * select with query condition
              * and
                * equal
                * bigger than, less than, bigger equals, less equals
                * like, not like
                * in, not in
              * or
          * select without order
        * select with page splitting
      * update
        * update with query condition
          * update with full column
            * update null value
            * update without null value
          * update with picked column
        * update with primary key
      * delete
        * delete with query condition
        * delete with primary key
      * insert
        * insert with full column
          * insert null value
          * insert without null value
        * insert with picked column
      * truncate
    * fetch from cache based on #1
* connection to database and execute sql
  * setting parameter
    * select
    * update
    * delete
    * insert
    * truncate
  * logging
  * fetch size
  * batch
* process result
  * result
    * single
    * list
    * stream
  * orm
    * result mapper
      * predefined
      * custom handler
    * interceptor

#### process strategy
* transaction with spring, reference to JdbcTemplate DataSourceUtils & DataSourceTransactionManager & TransactionAspectSupport